/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lancedb.lance.namespace.util;

import com.lancedb.lance.namespace.model.JsonArrowSchema;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Schema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

public class ArrowIpcUtil {

  /**
   * Extract Arrow schema from an Arrow IPC stream.
   *
   * @param ipcData The Arrow IPC stream data
   * @return The extracted Arrow schema as JsonArrowSchema
   * @throws IOException if the data is not valid Arrow IPC format
   */
  public static JsonArrowSchema extractSchemaFromIpc(byte[] ipcData) throws IOException {
    if (ipcData == null || ipcData.length == 0) {
      throw new IOException("Arrow IPC data cannot be null or empty");
    }

    try (BufferAllocator allocator = new RootAllocator()) {
      try (ByteArrayInputStream inputStream = new ByteArrayInputStream(ipcData);
          ArrowStreamReader reader = new ArrowStreamReader(inputStream, allocator)) {

        // Get the schema from the reader
        VectorSchemaRoot root = reader.getVectorSchemaRoot();
        Schema schema = root.getSchema();

        if (schema == null) {
          throw new IOException("No schema found in Arrow IPC stream");
        }

        // Convert Arrow Schema to JsonArrowSchema
        return JsonArrowSchemaConverter.convertToJsonArrowSchema(schema);
      }
    } catch (Exception e) {
      throw new IOException("Failed to extract schema from Arrow IPC stream: " + e.getMessage(), e);
    }
  }

  /**
   * Validate that the given data is a valid Arrow IPC stream.
   *
   * @param ipcData The data to validate
   * @return true if valid Arrow IPC stream, false otherwise
   */
  public static boolean isValidArrowIpc(byte[] ipcData) {
    if (ipcData == null || ipcData.length == 0) {
      return false;
    }

    try (BufferAllocator allocator = new RootAllocator()) {
      try (ByteArrayInputStream inputStream = new ByteArrayInputStream(ipcData);
          ArrowStreamReader reader = new ArrowStreamReader(inputStream, allocator)) {

        // Try to get the schema - if this succeeds, it's valid Arrow IPC
        VectorSchemaRoot root = reader.getVectorSchemaRoot();
        return root != null && root.getSchema() != null;
      }
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Create an empty Arrow IPC stream from a JsonArrowSchema.
   *
   * @param jsonSchema The schema to use for the empty stream
   * @return Byte array containing the Arrow IPC stream with no data
   * @throws IOException if failed to create the stream
   */
  public static byte[] createEmptyArrowIpcStream(JsonArrowSchema jsonSchema) throws IOException {
    if (jsonSchema == null) {
      throw new IOException("Schema cannot be null");
    }

    try (BufferAllocator allocator = new RootAllocator()) {
      // Convert JsonArrowSchema to Arrow Schema
      Schema arrowSchema = JsonArrowSchemaConverter.convertToArrowSchema(jsonSchema);

      // Create an empty VectorSchemaRoot with the schema
      try (VectorSchemaRoot root = VectorSchemaRoot.create(arrowSchema, allocator)) {
        // Allocate empty vectors (0 rows)
        root.allocateNew();
        root.setRowCount(0);

        // Write to IPC stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ArrowStreamWriter writer =
            new ArrowStreamWriter(root, null, Channels.newChannel(outputStream))) {
          writer.start();
          writer.writeBatch();
          writer.end();
        }

        return outputStream.toByteArray();
      }
    } catch (Exception e) {
      throw new IOException("Failed to create empty Arrow IPC stream: " + e.getMessage(), e);
    }
  }
}

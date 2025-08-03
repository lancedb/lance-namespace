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
package com.lancedb.lance.namespace.hive.base;

import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BaseHiveTestHelper {

  public static JsonArrowSchema createTestSchema() {
    JsonArrowSchema schema = new JsonArrowSchema();

    JsonArrowDataType intType = new JsonArrowDataType();
    intType.setType("int32");

    JsonArrowDataType stringType = new JsonArrowDataType();
    stringType.setType("utf8");

    JsonArrowField idField = new JsonArrowField();
    idField.setName("id");
    idField.setType(intType);
    idField.setNullable(false);

    JsonArrowField nameField = new JsonArrowField();
    nameField.setName("name");
    nameField.setType(stringType);
    nameField.setNullable(true);

    schema.setFields(Arrays.asList(idField, nameField));
    return schema;
  }

  public static byte[] createTestArrowData(BufferAllocator allocator) throws IOException {
    Schema arrowSchema =
        new Schema(
            Arrays.asList(
                new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                new Field("name", FieldType.nullable(new ArrowType.Utf8()), null)));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(arrowSchema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");

      // Add some test data
      root.setRowCount(3);
      idVector.setSafe(0, 1);
      idVector.setSafe(1, 2);
      idVector.setSafe(2, 3);

      nameVector.setSafe(0, "Alice".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(1, "Bob".getBytes(StandardCharsets.UTF_8));
      nameVector.setSafe(2, "Charlie".getBytes(StandardCharsets.UTF_8));

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      return out.toByteArray();
    }
  }

  public static byte[] createEmptyArrowData(BufferAllocator allocator) throws IOException {
    Schema arrowSchema =
        new Schema(
            Arrays.asList(
                new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                new Field("name", FieldType.nullable(new ArrowType.Utf8()), null)));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(arrowSchema, allocator)) {
      // Set row count to 0 for empty data
      root.setRowCount(0);

      // Serialize to Arrow IPC format
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, Channels.newChannel(out))) {
        writer.start();
        writer.writeBatch();
        writer.end();
      }

      return out.toByteArray();
    }
  }
}

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

import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;

import org.apache.arrow.vector.types.DateUnit;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.types.IntervalUnit;
import org.apache.arrow.vector.types.TimeUnit;
import org.apache.arrow.vector.types.UnionMode;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonArrowSchemaConverter {

  public static Schema convertToArrowSchema(JsonArrowSchema jsonSchema) {
    if (jsonSchema == null) {
      return null;
    }

    List<Field> fields = new ArrayList<>();
    if (jsonSchema.getFields() != null) {
      for (JsonArrowField jsonField : jsonSchema.getFields()) {
        fields.add(convertToArrowField(jsonField));
      }
    }

    Map<String, String> metadata =
        jsonSchema.getMetadata() != null ? jsonSchema.getMetadata() : new HashMap<>();

    return new Schema(fields, metadata);
  }

  private static Field convertToArrowField(JsonArrowField jsonField) {
    if (jsonField == null) {
      throw new IllegalArgumentException("JsonArrowField cannot be null");
    }

    String name = jsonField.getName();
    boolean nullable = jsonField.getNullable() != null ? jsonField.getNullable() : true;
    ArrowType arrowType = convertToArrowType(jsonField.getType());

    Map<String, String> metadata =
        jsonField.getMetadata() != null ? jsonField.getMetadata() : new HashMap<>();

    FieldType fieldType = new FieldType(nullable, arrowType, null, metadata);
    return new Field(name, fieldType, null);
  }

  private static ArrowType convertToArrowType(JsonArrowDataType jsonType) {
    if (jsonType == null) {
      throw new IllegalArgumentException("JsonArrowDataType cannot be null");
    }

    String typeName = jsonType.getType();
    if (typeName == null) {
      throw new IllegalArgumentException("Type name cannot be null");
    }

    switch (typeName.toLowerCase()) {
      case "null":
        return ArrowType.Null.INSTANCE;
      case "bool":
      case "boolean":
        return ArrowType.Bool.INSTANCE;
      case "int8":
        return new ArrowType.Int(8, true);
      case "uint8":
        return new ArrowType.Int(8, false);
      case "int16":
        return new ArrowType.Int(16, true);
      case "uint16":
        return new ArrowType.Int(16, false);
      case "int32":
        return new ArrowType.Int(32, true);
      case "uint32":
        return new ArrowType.Int(32, false);
      case "int64":
        return new ArrowType.Int(64, true);
      case "uint64":
        return new ArrowType.Int(64, false);
      case "float16":
        return new ArrowType.FloatingPoint(FloatingPointPrecision.HALF);
      case "float32":
        return new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE);
      case "float64":
        return new ArrowType.FloatingPoint(FloatingPointPrecision.DOUBLE);
      case "utf8":
      case "string":
        return ArrowType.Utf8.INSTANCE;
      case "binary":
        return ArrowType.Binary.INSTANCE;
      case "fixedsizebinary":
        Long length = jsonType.getLength();
        if (length == null) {
          throw new IllegalArgumentException("FixedSizeBinary type requires length field");
        }
        return new ArrowType.FixedSizeBinary(length.intValue());
      case "decimal128":
        // Default precision and scale for decimal, should be enhanced based on actual requirements
        return new ArrowType.Decimal(38, 18, 128);
      case "decimal256":
        return new ArrowType.Decimal(76, 18, 256);
      case "date32":
        return new ArrowType.Date(DateUnit.DAY);
      case "date64":
        return new ArrowType.Date(DateUnit.MILLISECOND);
      case "time32":
        return new ArrowType.Time(TimeUnit.SECOND, 32);
      case "time64":
        return new ArrowType.Time(TimeUnit.MICROSECOND, 64);
      case "timestamp":
        return new ArrowType.Timestamp(TimeUnit.MICROSECOND, null);
      case "interval":
        return new ArrowType.Interval(IntervalUnit.DAY_TIME);
      case "duration":
        return new ArrowType.Duration(TimeUnit.MICROSECOND);
      case "list":
        if (jsonType.getFields() == null || jsonType.getFields().isEmpty()) {
          throw new IllegalArgumentException("List type requires field definition");
        }
        return ArrowType.List.INSTANCE;
      case "struct":
        return ArrowType.Struct.INSTANCE;
      case "union":
        return new ArrowType.Union(UnionMode.Sparse, new int[0]);
      case "fixedsizelist":
        Long listSize = jsonType.getLength();
        if (listSize == null) {
          throw new IllegalArgumentException("FixedSizeList type requires length field");
        }
        return new ArrowType.FixedSizeList(listSize.intValue());
      case "map":
        return new ArrowType.Map(false);
      default:
        throw new IllegalArgumentException("Unsupported Arrow type: " + typeName);
    }
  }
}

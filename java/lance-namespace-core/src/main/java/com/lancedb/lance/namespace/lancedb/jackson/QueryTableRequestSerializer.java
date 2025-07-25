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
package com.lancedb.lance.namespace.lancedb.jackson;

import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery;
import com.lancedb.lance.namespace.model.QueryTableRequestVector;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

/**
 * Custom serializer for QueryTableRequest that handles the vector field as an untagged union. The
 * server expects the vector field as a direct array, not as an object with properties.
 */
public class QueryTableRequestSerializer extends JsonSerializer<QueryTableRequest> {

  @Override
  public void serialize(QueryTableRequest value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeStartObject();

    // Write required fields
    gen.writeNumberField("k", value.getK());

    // Write id field if exists
    if (value.getId() != null) {
      gen.writeFieldName("id");
      gen.writeStartArray();
      for (String idPart : value.getId()) {
        gen.writeString(idPart);
      }
      gen.writeEndArray();
    }

    // Handle the vector field - server expects empty array, not null
    gen.writeFieldName("vector");

    if (value.getVector() != null) {
      QueryTableRequestVector vectorObj = value.getVector();

      // Check if it's a single vector or multiple vectors
      // Since both fields are initialized as empty lists, we need to check which one has content
      if (vectorObj.getMultiVector() != null && !vectorObj.getMultiVector().isEmpty()) {
        // Multiple vectors - write as array of arrays
        gen.writeStartArray();
        for (List<Float> vec : vectorObj.getMultiVector()) {
          gen.writeStartArray();
          for (Float elem : vec) {
            gen.writeNumber(elem);
          }
          gen.writeEndArray();
        }
        gen.writeEndArray();
      } else if (vectorObj.getSingleVector() != null && !vectorObj.getSingleVector().isEmpty()) {
        // Single vector - write as direct array
        gen.writeStartArray();
        for (Float elem : vectorObj.getSingleVector()) {
          gen.writeNumber(elem);
        }
        gen.writeEndArray();
      } else {
        // If both are empty or null, write empty array as default
        gen.writeStartArray();
        gen.writeEndArray();
      }
    } else {
      // Vector is null, write empty array
      gen.writeStartArray();
      gen.writeEndArray();
    }

    // Write optional fields
    if (value.getBypassVectorIndex() != null) {
      gen.writeBooleanField("bypass_vector_index", value.getBypassVectorIndex());
    }
    if (value.getColumns() != null) {
      gen.writeObjectField("columns", value.getColumns());
    }
    if (value.getDistanceType() != null) {
      gen.writeStringField("distance_type", value.getDistanceType());
    }
    if (value.getEf() != null) {
      gen.writeNumberField("ef", value.getEf());
    }
    if (value.getFastSearch() != null) {
      gen.writeBooleanField("fast_search", value.getFastSearch());
    }
    if (value.getFilter() != null) {
      gen.writeStringField("filter", value.getFilter());
    }
    if (value.getFullTextQuery() != null) {
      gen.writeFieldName("full_text_query");

      QueryTableRequestFullTextQuery ftq = value.getFullTextQuery();

      // Check if it's a string query or structured query and serialize the inner object directly
      if (ftq.getStringQuery() != null) {
        // Write the StringFtsQuery directly
        gen.writeObject(ftq.getStringQuery());
      } else if (ftq.getStructuredQuery() != null) {
        // Write the StructuredFtsQuery directly
        gen.writeObject(ftq.getStructuredQuery());
      } else {
        gen.writeNull();
      }
    }
    if (value.getLowerBound() != null) {
      gen.writeNumberField("lower_bound", value.getLowerBound());
    }
    if (value.getNprobes() != null) {
      gen.writeNumberField("nprobes", value.getNprobes());
    }
    if (value.getOffset() != null) {
      gen.writeNumberField("offset", value.getOffset());
    }
    if (value.getPrefilter() != null) {
      gen.writeBooleanField("prefilter", value.getPrefilter());
    }
    if (value.getRefineFactor() != null) {
      gen.writeNumberField("refine_factor", value.getRefineFactor());
    }
    if (value.getUpperBound() != null) {
      gen.writeNumberField("upper_bound", value.getUpperBound());
    }
    if (value.getVectorColumn() != null) {
      gen.writeStringField("vector_column", value.getVectorColumn());
    }
    if (value.getVersion() != null) {
      gen.writeNumberField("version", value.getVersion());
    }
    if (value.getWithRowId() != null) {
      gen.writeBooleanField("with_row_id", value.getWithRowId());
    }

    gen.writeEndObject();
  }
}

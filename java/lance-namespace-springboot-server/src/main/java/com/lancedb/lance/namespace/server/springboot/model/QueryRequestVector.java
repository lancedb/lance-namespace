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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.
 */
@Schema(
    name = "QueryRequest_vector",
    description =
        "Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.")
@JsonTypeName("QueryRequest_vector")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryRequestVector {

  @Valid private List<Float> singleVector = new ArrayList<>();

  @Valid private List<List<Float>> multiVector = new ArrayList<>();

  public QueryRequestVector singleVector(List<Float> singleVector) {
    this.singleVector = singleVector;
    return this;
  }

  public QueryRequestVector addSingleVectorItem(Float singleVectorItem) {
    if (this.singleVector == null) {
      this.singleVector = new ArrayList<>();
    }
    this.singleVector.add(singleVectorItem);
    return this;
  }

  /**
   * Single query vector
   *
   * @return singleVector
   */
  @Schema(
      name = "single_vector",
      description = "Single query vector",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("single_vector")
  public List<Float> getSingleVector() {
    return singleVector;
  }

  public void setSingleVector(List<Float> singleVector) {
    this.singleVector = singleVector;
  }

  public QueryRequestVector multiVector(List<List<Float>> multiVector) {
    this.multiVector = multiVector;
    return this;
  }

  public QueryRequestVector addMultiVectorItem(List<Float> multiVectorItem) {
    if (this.multiVector == null) {
      this.multiVector = new ArrayList<>();
    }
    this.multiVector.add(multiVectorItem);
    return this;
  }

  /**
   * Multiple query vectors for batch search
   *
   * @return multiVector
   */
  @Valid
  @Schema(
      name = "multi_vector",
      description = "Multiple query vectors for batch search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("multi_vector")
  public List<List<Float>> getMultiVector() {
    return multiVector;
  }

  public void setMultiVector(List<List<Float>> multiVector) {
    this.multiVector = multiVector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryRequestVector queryRequestVector = (QueryRequestVector) o;
    return Objects.equals(this.singleVector, queryRequestVector.singleVector)
        && Objects.equals(this.multiVector, queryRequestVector.multiVector);
  }

  @Override
  public int hashCode() {
    return Objects.hash(singleVector, multiVector);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryRequestVector {\n");
    sb.append("    singleVector: ").append(toIndentedString(singleVector)).append("\n");
    sb.append("    multiVector: ").append(toIndentedString(multiVector)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.
 */

@Schema(name = "QueryTableRequest_vector", description = "Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.")
@JsonTypeName("QueryTableRequest_vector")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class QueryTableRequestVector {

  @Valid
  private List<Float> singleVector = new ArrayList<>();

  @Valid
  private List<List<Float>> multiVector = new ArrayList<>();

  public QueryTableRequestVector singleVector(List<Float> singleVector) {
    this.singleVector = singleVector;
    return this;
  }

  public QueryTableRequestVector addSingleVectorItem(Float singleVectorItem) {
    if (this.singleVector == null) {
      this.singleVector = new ArrayList<>();
    }
    this.singleVector.add(singleVectorItem);
    return this;
  }

  /**
   * Single query vector
   * @return singleVector
   */
  
  @Schema(name = "single_vector", description = "Single query vector", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("single_vector")
  public List<Float> getSingleVector() {
    return singleVector;
  }

  public void setSingleVector(List<Float> singleVector) {
    this.singleVector = singleVector;
  }

  public QueryTableRequestVector multiVector(List<List<Float>> multiVector) {
    this.multiVector = multiVector;
    return this;
  }

  public QueryTableRequestVector addMultiVectorItem(List<Float> multiVectorItem) {
    if (this.multiVector == null) {
      this.multiVector = new ArrayList<>();
    }
    this.multiVector.add(multiVectorItem);
    return this;
  }

  /**
   * Multiple query vectors for batch search
   * @return multiVector
   */
  @Valid 
  @Schema(name = "multi_vector", description = "Multiple query vectors for batch search", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    QueryTableRequestVector queryTableRequestVector = (QueryTableRequestVector) o;
    return Objects.equals(this.singleVector, queryTableRequestVector.singleVector) &&
        Objects.equals(this.multiVector, queryTableRequestVector.multiVector);
  }

  @Override
  public int hashCode() {
    return Objects.hash(singleVector, multiVector);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableRequestVector {\n");
    sb.append("    singleVector: ").append(toIndentedString(singleVector)).append("\n");
    sb.append("    multiVector: ").append(toIndentedString(multiVector)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.IndexContent;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ListTableIndicesResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class ListTableIndicesResponse {

  @Valid
  private List<@Valid IndexContent> indexes = new ArrayList<>();

  private String pageToken;

  public ListTableIndicesResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListTableIndicesResponse(List<@Valid IndexContent> indexes) {
    this.indexes = indexes;
  }

  public ListTableIndicesResponse indexes(List<@Valid IndexContent> indexes) {
    this.indexes = indexes;
    return this;
  }

  public ListTableIndicesResponse addIndexesItem(IndexContent indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * List of indexes on the table
   * @return indexes
   */
  @NotNull @Valid 
  @Schema(name = "indexes", description = "List of indexes on the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("indexes")
  public List<@Valid IndexContent> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<@Valid IndexContent> indexes) {
    this.indexes = indexes;
  }

  public ListTableIndicesResponse pageToken(String pageToken) {
    this.pageToken = pageToken;
    return this;
  }

  /**
   * An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation, if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request, the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either `null`, missing value or empty string value of the page token from the implementation's response as the end of the listing results. 
   * @return pageToken
   */
  
  @Schema(name = "page_token", description = "An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation, if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request, the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either `null`, missing value or empty string value of the page token from the implementation's response as the end of the listing results. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page_token")
  public String getPageToken() {
    return pageToken;
  }

  public void setPageToken(String pageToken) {
    this.pageToken = pageToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListTableIndicesResponse listTableIndicesResponse = (ListTableIndicesResponse) o;
    return Objects.equals(this.indexes, listTableIndicesResponse.indexes) &&
        Objects.equals(this.pageToken, listTableIndicesResponse.pageToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexes, pageToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTableIndicesResponse {\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
    sb.append("    pageToken: ").append(toIndentedString(pageToken)).append("\n");
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


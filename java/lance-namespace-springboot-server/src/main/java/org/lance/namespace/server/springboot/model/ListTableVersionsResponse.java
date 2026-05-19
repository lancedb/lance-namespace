package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.TableVersion;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ListTableVersionsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class ListTableVersionsResponse {

  @Valid
  private List<@Valid TableVersion> versions = new ArrayList<>();

  private String pageToken;

  public ListTableVersionsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListTableVersionsResponse(List<@Valid TableVersion> versions) {
    this.versions = versions;
  }

  public ListTableVersionsResponse versions(List<@Valid TableVersion> versions) {
    this.versions = versions;
    return this;
  }

  public ListTableVersionsResponse addVersionsItem(TableVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * List of table versions. When `descending=true`, guaranteed to be ordered from latest to oldest. Otherwise, ordering is implementation-defined. 
   * @return versions
   */
  @NotNull @Valid 
  @Schema(name = "versions", description = "List of table versions. When `descending=true`, guaranteed to be ordered from latest to oldest. Otherwise, ordering is implementation-defined. ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("versions")
  public List<@Valid TableVersion> getVersions() {
    return versions;
  }

  public void setVersions(List<@Valid TableVersion> versions) {
    this.versions = versions;
  }

  public ListTableVersionsResponse pageToken(String pageToken) {
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
    ListTableVersionsResponse listTableVersionsResponse = (ListTableVersionsResponse) o;
    return Objects.equals(this.versions, listTableVersionsResponse.versions) &&
        Objects.equals(this.pageToken, listTableVersionsResponse.pageToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(versions, pageToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTableVersionsResponse {\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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


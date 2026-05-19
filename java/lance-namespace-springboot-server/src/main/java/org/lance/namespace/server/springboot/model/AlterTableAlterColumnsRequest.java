package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.AlterColumnsEntry;
import org.lance.namespace.server.springboot.model.Identity;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AlterTableAlterColumnsRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AlterTableAlterColumnsRequest {

  private Identity identity;

  @Valid
  private List<String> id = new ArrayList<>();

  @Valid
  private List<@Valid AlterColumnsEntry> alterations = new ArrayList<>();

  public AlterTableAlterColumnsRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AlterTableAlterColumnsRequest(List<@Valid AlterColumnsEntry> alterations) {
    this.alterations = alterations;
  }

  public AlterTableAlterColumnsRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   * @return identity
   */
  @Valid 
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public AlterTableAlterColumnsRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableAlterColumnsRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Table identifier path (namespace + table name)
   * @return id
   */
  
  @Schema(name = "id", description = "Table identifier path (namespace + table name)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public AlterTableAlterColumnsRequest alterations(List<@Valid AlterColumnsEntry> alterations) {
    this.alterations = alterations;
    return this;
  }

  public AlterTableAlterColumnsRequest addAlterationsItem(AlterColumnsEntry alterationsItem) {
    if (this.alterations == null) {
      this.alterations = new ArrayList<>();
    }
    this.alterations.add(alterationsItem);
    return this;
  }

  /**
   * List of column alterations to apply to the table
   * @return alterations
   */
  @NotNull @Valid 
  @Schema(name = "alterations", description = "List of column alterations to apply to the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("alterations")
  public List<@Valid AlterColumnsEntry> getAlterations() {
    return alterations;
  }

  public void setAlterations(List<@Valid AlterColumnsEntry> alterations) {
    this.alterations = alterations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableAlterColumnsRequest alterTableAlterColumnsRequest = (AlterTableAlterColumnsRequest) o;
    return Objects.equals(this.identity, alterTableAlterColumnsRequest.identity) &&
        Objects.equals(this.id, alterTableAlterColumnsRequest.id) &&
        Objects.equals(this.alterations, alterTableAlterColumnsRequest.alterations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, id, alterations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAlterColumnsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    alterations: ").append(toIndentedString(alterations)).append("\n");
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


package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.lance.namespace.server.springboot.model.AddVirtualColumnEntry;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddColumnsEntry
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AddColumnsEntry {

  private String name;

  private String expression = null;

  private AddVirtualColumnEntry virtualColumn = null;

  public AddColumnsEntry() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddColumnsEntry(String name) {
    this.name = name;
  }

  public AddColumnsEntry name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the new column
   * @return name
   */
  @NotNull 
  @Schema(name = "name", description = "Name of the new column", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AddColumnsEntry expression(String expression) {
    this.expression = expression;
    return this;
  }

  /**
   * SQL expression for the column (optional if virtual_column is specified)
   * @return expression
   */
  
  @Schema(name = "expression", description = "SQL expression for the column (optional if virtual_column is specified)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expression")
  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public AddColumnsEntry virtualColumn(AddVirtualColumnEntry virtualColumn) {
    this.virtualColumn = virtualColumn;
    return this;
  }

  /**
   * Get virtualColumn
   * @return virtualColumn
   */
  @Valid 
  @Schema(name = "virtual_column", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("virtual_column")
  public AddVirtualColumnEntry getVirtualColumn() {
    return virtualColumn;
  }

  public void setVirtualColumn(AddVirtualColumnEntry virtualColumn) {
    this.virtualColumn = virtualColumn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddColumnsEntry addColumnsEntry = (AddColumnsEntry) o;
    return Objects.equals(this.name, addColumnsEntry.name) &&
        Objects.equals(this.expression, addColumnsEntry.expression) &&
        Objects.equals(this.virtualColumn, addColumnsEntry.virtualColumn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, expression, virtualColumn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddColumnsEntry {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
    sb.append("    virtualColumn: ").append(toIndentedString(virtualColumn)).append("\n");
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


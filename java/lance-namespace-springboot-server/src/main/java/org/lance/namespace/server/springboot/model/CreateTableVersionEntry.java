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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An entry for creating a new table version in a batch operation. This supports
 * &#x60;put_if_not_exists&#x60; semantics, where the operation fails if the version already exists.
 */
@Schema(
    name = "CreateTableVersionEntry",
    description =
        "An entry for creating a new table version in a batch operation. This supports `put_if_not_exists` semantics, where the operation fails if the version already exists. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableVersionEntry {

  @Valid private List<String> id = new ArrayList<>();

  private Long version;

  private String manifestPath;

  private Long manifestSize;

  private String eTag;

  @Valid private Map<String, String> metadata = new HashMap<>();

  private String namingScheme;

  public CreateTableVersionEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTableVersionEntry(List<String> id, Long version, String manifestPath) {
    this.id = id;
    this.version = version;
    this.manifestPath = manifestPath;
  }

  public CreateTableVersionEntry id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableVersionEntry addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * The table identifier
   *
   * @return id
   */
  @NotNull
  @Schema(
      name = "id",
      description = "The table identifier",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public CreateTableVersionEntry version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version number to create minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "Version number to create",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public CreateTableVersionEntry manifestPath(String manifestPath) {
    this.manifestPath = manifestPath;
    return this;
  }

  /**
   * Path to the manifest file for this version
   *
   * @return manifestPath
   */
  @NotNull
  @Schema(
      name = "manifest_path",
      description = "Path to the manifest file for this version",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("manifest_path")
  public String getManifestPath() {
    return manifestPath;
  }

  public void setManifestPath(String manifestPath) {
    this.manifestPath = manifestPath;
  }

  public CreateTableVersionEntry manifestSize(Long manifestSize) {
    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the manifest file in bytes minimum: 0
   *
   * @return manifestSize
   */
  @Min(0L)
  @Schema(
      name = "manifest_size",
      description = "Size of the manifest file in bytes",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("manifest_size")
  public Long getManifestSize() {
    return manifestSize;
  }

  public void setManifestSize(Long manifestSize) {
    this.manifestSize = manifestSize;
  }

  public CreateTableVersionEntry eTag(String eTag) {
    this.eTag = eTag;
    return this;
  }

  /**
   * Optional ETag for the manifest file
   *
   * @return eTag
   */
  @Schema(
      name = "e_tag",
      description = "Optional ETag for the manifest file",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("e_tag")
  public String geteTag() {
    return eTag;
  }

  public void seteTag(String eTag) {
    this.eTag = eTag;
  }

  public CreateTableVersionEntry metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public CreateTableVersionEntry putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Optional metadata for the version
   *
   * @return metadata
   */
  @Schema(
      name = "metadata",
      description = "Optional metadata for the version",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public CreateTableVersionEntry namingScheme(String namingScheme) {
    this.namingScheme = namingScheme;
    return this;
  }

  /**
   * The naming scheme used for manifest files in the `_versions/` directory. Known values: - `V1`:
   * `_versions/{version}.manifest` - Simple version-based naming - `V2`:
   * `_versions/{inverted_version}.manifest` - Zero-padded, reversed version number (uses `u64::MAX
   * - version`) for O(1) lookup of latest version on object stores V2 is preferred for new tables
   * as it enables efficient latest-version discovery without needing to list all versions.
   *
   * @return namingScheme
   */
  @Schema(
      name = "naming_scheme",
      example = "V2",
      description =
          "The naming scheme used for manifest files in the `_versions/` directory.  Known values: - `V1`: `_versions/{version}.manifest` - Simple version-based naming - `V2`: `_versions/{inverted_version}.manifest` - Zero-padded, reversed version number   (uses `u64::MAX - version`) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("naming_scheme")
  public String getNamingScheme() {
    return namingScheme;
  }

  public void setNamingScheme(String namingScheme) {
    this.namingScheme = namingScheme;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableVersionEntry createTableVersionEntry = (CreateTableVersionEntry) o;
    return Objects.equals(this.id, createTableVersionEntry.id)
        && Objects.equals(this.version, createTableVersionEntry.version)
        && Objects.equals(this.manifestPath, createTableVersionEntry.manifestPath)
        && Objects.equals(this.manifestSize, createTableVersionEntry.manifestSize)
        && Objects.equals(this.eTag, createTableVersionEntry.eTag)
        && Objects.equals(this.metadata, createTableVersionEntry.metadata)
        && Objects.equals(this.namingScheme, createTableVersionEntry.namingScheme);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, manifestPath, manifestSize, eTag, metadata, namingScheme);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableVersionEntry {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    manifestPath: ").append(toIndentedString(manifestPath)).append("\n");
    sb.append("    manifestSize: ").append(toIndentedString(manifestSize)).append("\n");
    sb.append("    eTag: ").append(toIndentedString(eTag)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    namingScheme: ").append(toIndentedString(namingScheme)).append("\n");
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

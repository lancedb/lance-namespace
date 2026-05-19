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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * An entry for creating a new table version in a batch operation. This supports
 * &#x60;put_if_not_exists&#x60; semantics, where the operation fails if the version already exists.
 */
@JsonPropertyOrder({
  CreateTableVersionEntry.JSON_PROPERTY_ID,
  CreateTableVersionEntry.JSON_PROPERTY_VERSION,
  CreateTableVersionEntry.JSON_PROPERTY_MANIFEST_PATH,
  CreateTableVersionEntry.JSON_PROPERTY_MANIFEST_SIZE,
  CreateTableVersionEntry.JSON_PROPERTY_E_TAG,
  CreateTableVersionEntry.JSON_PROPERTY_METADATA,
  CreateTableVersionEntry.JSON_PROPERTY_NAMING_SCHEME
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableVersionEntry {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nonnull private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public static final String JSON_PROPERTY_MANIFEST_PATH = "manifest_path";
  @javax.annotation.Nonnull private String manifestPath;

  public static final String JSON_PROPERTY_MANIFEST_SIZE = "manifest_size";
  @javax.annotation.Nullable private Long manifestSize;

  public static final String JSON_PROPERTY_E_TAG = "e_tag";
  @javax.annotation.Nullable private String eTag;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public static final String JSON_PROPERTY_NAMING_SCHEME = "naming_scheme";
  @javax.annotation.Nullable private String namingScheme;

  public CreateTableVersionEntry() {}

  public CreateTableVersionEntry id(@javax.annotation.Nonnull List<String> id) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@javax.annotation.Nonnull List<String> id) {
    this.id = id;
  }

  public CreateTableVersionEntry version(@javax.annotation.Nonnull Long version) {

    this.version = version;
    return this;
  }

  /**
   * Version number to create minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(@javax.annotation.Nonnull Long version) {
    this.version = version;
  }

  public CreateTableVersionEntry manifestPath(@javax.annotation.Nonnull String manifestPath) {

    this.manifestPath = manifestPath;
    return this;
  }

  /**
   * Path to the manifest file for this version
   *
   * @return manifestPath
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MANIFEST_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getManifestPath() {
    return manifestPath;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setManifestPath(@javax.annotation.Nonnull String manifestPath) {
    this.manifestPath = manifestPath;
  }

  public CreateTableVersionEntry manifestSize(@javax.annotation.Nullable Long manifestSize) {

    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the manifest file in bytes minimum: 0
   *
   * @return manifestSize
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MANIFEST_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getManifestSize() {
    return manifestSize;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setManifestSize(@javax.annotation.Nullable Long manifestSize) {
    this.manifestSize = manifestSize;
  }

  public CreateTableVersionEntry eTag(@javax.annotation.Nullable String eTag) {

    this.eTag = eTag;
    return this;
  }

  /**
   * Optional ETag for the manifest file
   *
   * @return eTag
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_E_TAG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String geteTag() {
    return eTag;
  }

  @JsonProperty(JSON_PROPERTY_E_TAG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void seteTag(@javax.annotation.Nullable String eTag) {
    this.eTag = eTag;
  }

  public CreateTableVersionEntry metadata(@javax.annotation.Nullable Map<String, String> metadata) {

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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(@javax.annotation.Nullable Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public CreateTableVersionEntry namingScheme(@javax.annotation.Nullable String namingScheme) {

    this.namingScheme = namingScheme;
    return this;
  }

  /**
   * The naming scheme used for manifest files in the &#x60;_versions/&#x60; directory. Known
   * values: - &#x60;V1&#x60;: &#x60;_versions/{version}.manifest&#x60; - Simple version-based
   * naming - &#x60;V2&#x60;: &#x60;_versions/{inverted_version}.manifest&#x60; - Zero-padded,
   * reversed version number (uses &#x60;u64::MAX - version&#x60;) for O(1) lookup of latest version
   * on object stores V2 is preferred for new tables as it enables efficient latest-version
   * discovery without needing to list all versions.
   *
   * @return namingScheme
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAMING_SCHEME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getNamingScheme() {
    return namingScheme;
  }

  @JsonProperty(JSON_PROPERTY_NAMING_SCHEME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNamingScheme(@javax.annotation.Nullable String namingScheme) {
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `manifest_path` to the URL query string
    if (getManifestPath() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifest_path%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifestPath()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `manifest_size` to the URL query string
    if (getManifestSize() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifest_size%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifestSize()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `e_tag` to the URL query string
    if (geteTag() != null) {
      try {
        joiner.add(
            String.format(
                "%se_tag%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(geteTag()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%smetadata%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getMetadata().get(_key),
                  URLEncoder.encode(String.valueOf(getMetadata().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `naming_scheme` to the URL query string
    if (getNamingScheme() != null) {
      try {
        joiner.add(
            String.format(
                "%snaming_scheme%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNamingScheme()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}

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

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** AddVirtualColumnEntry */
@JsonPropertyOrder({
  AddVirtualColumnEntry.JSON_PROPERTY_INPUT_COLUMNS,
  AddVirtualColumnEntry.JSON_PROPERTY_DATA_TYPE,
  AddVirtualColumnEntry.JSON_PROPERTY_IMAGE,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF_NAME,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF_VERSION,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF_BACKEND,
  AddVirtualColumnEntry.JSON_PROPERTY_AUTO_BACKFILL,
  AddVirtualColumnEntry.JSON_PROPERTY_MANIFEST,
  AddVirtualColumnEntry.JSON_PROPERTY_MANIFEST_CHECKSUM,
  AddVirtualColumnEntry.JSON_PROPERTY_FIELD_METADATA
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AddVirtualColumnEntry {
  public static final String JSON_PROPERTY_INPUT_COLUMNS = "input_columns";
  @javax.annotation.Nonnull private List<String> inputColumns = new ArrayList<>();

  public static final String JSON_PROPERTY_DATA_TYPE = "data_type";
  @javax.annotation.Nonnull private Object dataType;

  public static final String JSON_PROPERTY_IMAGE = "image";
  @javax.annotation.Nonnull private String image;

  public static final String JSON_PROPERTY_UDF = "udf";
  @javax.annotation.Nonnull private String udf;

  public static final String JSON_PROPERTY_UDF_NAME = "udf_name";
  @javax.annotation.Nonnull private String udfName;

  public static final String JSON_PROPERTY_UDF_VERSION = "udf_version";
  @javax.annotation.Nonnull private String udfVersion;

  public static final String JSON_PROPERTY_UDF_BACKEND = "udf_backend";
  private JsonNullable<String> udfBackend = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_AUTO_BACKFILL = "auto_backfill";
  private JsonNullable<Boolean> autoBackfill = JsonNullable.<Boolean>undefined();

  public static final String JSON_PROPERTY_MANIFEST = "manifest";
  private JsonNullable<String> manifest = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_MANIFEST_CHECKSUM = "manifest_checksum";
  private JsonNullable<String> manifestChecksum = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_FIELD_METADATA = "field_metadata";
  @javax.annotation.Nullable private Map<String, String> fieldMetadata = new HashMap<>();

  public AddVirtualColumnEntry() {}

  public AddVirtualColumnEntry inputColumns(@javax.annotation.Nonnull List<String> inputColumns) {
    this.inputColumns = inputColumns;
    return this;
  }

  public AddVirtualColumnEntry addInputColumnsItem(String inputColumnsItem) {
    if (this.inputColumns == null) {
      this.inputColumns = new ArrayList<>();
    }
    this.inputColumns.add(inputColumnsItem);
    return this;
  }

  /**
   * List of input column names for the virtual column
   *
   * @return inputColumns
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getInputColumns() {
    return inputColumns;
  }

  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setInputColumns(@javax.annotation.Nonnull List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public AddVirtualColumnEntry dataType(@javax.annotation.Nonnull Object dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the virtual column using JSON representation
   *
   * @return dataType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DATA_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Object getDataType() {
    return dataType;
  }

  @JsonProperty(JSON_PROPERTY_DATA_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDataType(@javax.annotation.Nonnull Object dataType) {
    this.dataType = dataType;
  }

  public AddVirtualColumnEntry image(@javax.annotation.Nonnull String image) {
    this.image = image;
    return this;
  }

  /**
   * Docker image to use for the UDF
   *
   * @return image
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getImage() {
    return image;
  }

  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setImage(@javax.annotation.Nonnull String image) {
    this.image = image;
  }

  public AddVirtualColumnEntry udf(@javax.annotation.Nonnull String udf) {
    this.udf = udf;
    return this;
  }

  /**
   * Base64 encoded pickled UDF
   *
   * @return udf
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdf() {
    return udf;
  }

  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdf(@javax.annotation.Nonnull String udf) {
    this.udf = udf;
  }

  public AddVirtualColumnEntry udfName(@javax.annotation.Nonnull String udfName) {
    this.udfName = udfName;
    return this;
  }

  /**
   * Name of the UDF
   *
   * @return udfName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdfName() {
    return udfName;
  }

  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdfName(@javax.annotation.Nonnull String udfName) {
    this.udfName = udfName;
  }

  public AddVirtualColumnEntry udfVersion(@javax.annotation.Nonnull String udfVersion) {
    this.udfVersion = udfVersion;
    return this;
  }

  /**
   * Version of the UDF
   *
   * @return udfVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdfVersion() {
    return udfVersion;
  }

  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdfVersion(@javax.annotation.Nonnull String udfVersion) {
    this.udfVersion = udfVersion;
  }

  public AddVirtualColumnEntry udfBackend(@javax.annotation.Nullable String udfBackend) {
    this.udfBackend = JsonNullable.<String>of(udfBackend);
    return this;
  }

  /**
   * UDF backend type (e.g. DockerUDFSpecV1)
   *
   * @return udfBackend
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getUdfBackend() {
    return udfBackend.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_UDF_BACKEND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getUdfBackend_JsonNullable() {
    return udfBackend;
  }

  @JsonProperty(JSON_PROPERTY_UDF_BACKEND)
  public void setUdfBackend_JsonNullable(JsonNullable<String> udfBackend) {
    this.udfBackend = udfBackend;
  }

  public void setUdfBackend(@javax.annotation.Nullable String udfBackend) {
    this.udfBackend = JsonNullable.<String>of(udfBackend);
  }

  public AddVirtualColumnEntry autoBackfill(@javax.annotation.Nullable Boolean autoBackfill) {
    this.autoBackfill = JsonNullable.<Boolean>of(autoBackfill);
    return this;
  }

  /**
   * Whether to automatically backfill the column after creation
   *
   * @return autoBackfill
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getAutoBackfill() {
    return autoBackfill.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_AUTO_BACKFILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getAutoBackfill_JsonNullable() {
    return autoBackfill;
  }

  @JsonProperty(JSON_PROPERTY_AUTO_BACKFILL)
  public void setAutoBackfill_JsonNullable(JsonNullable<Boolean> autoBackfill) {
    this.autoBackfill = autoBackfill;
  }

  public void setAutoBackfill(@javax.annotation.Nullable Boolean autoBackfill) {
    this.autoBackfill = JsonNullable.<Boolean>of(autoBackfill);
  }

  public AddVirtualColumnEntry manifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);
    return this;
  }

  /**
   * JSON-serialized manifest for the UDF environment
   *
   * @return manifest
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getManifest() {
    return manifest.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getManifest_JsonNullable() {
    return manifest;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST)
  public void setManifest_JsonNullable(JsonNullable<String> manifest) {
    this.manifest = manifest;
  }

  public void setManifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);
  }

  public AddVirtualColumnEntry manifestChecksum(
      @javax.annotation.Nullable String manifestChecksum) {
    this.manifestChecksum = JsonNullable.<String>of(manifestChecksum);
    return this;
  }

  /**
   * SHA-256 checksum of the manifest content
   *
   * @return manifestChecksum
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getManifestChecksum() {
    return manifestChecksum.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST_CHECKSUM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getManifestChecksum_JsonNullable() {
    return manifestChecksum;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST_CHECKSUM)
  public void setManifestChecksum_JsonNullable(JsonNullable<String> manifestChecksum) {
    this.manifestChecksum = manifestChecksum;
  }

  public void setManifestChecksum(@javax.annotation.Nullable String manifestChecksum) {
    this.manifestChecksum = JsonNullable.<String>of(manifestChecksum);
  }

  public AddVirtualColumnEntry fieldMetadata(
      @javax.annotation.Nullable Map<String, String> fieldMetadata) {
    this.fieldMetadata = fieldMetadata;
    return this;
  }

  public AddVirtualColumnEntry putFieldMetadataItem(String key, String fieldMetadataItem) {
    if (this.fieldMetadata == null) {
      this.fieldMetadata = new HashMap<>();
    }
    this.fieldMetadata.put(key, fieldMetadataItem);
    return this;
  }

  /**
   * User-supplied field metadata (string key-value pairs)
   *
   * @return fieldMetadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FIELD_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getFieldMetadata() {
    return fieldMetadata;
  }

  @JsonProperty(JSON_PROPERTY_FIELD_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFieldMetadata(@javax.annotation.Nullable Map<String, String> fieldMetadata) {
    this.fieldMetadata = fieldMetadata;
  }

  /** Return true if this AddVirtualColumnEntry object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddVirtualColumnEntry addVirtualColumnEntry = (AddVirtualColumnEntry) o;
    return Objects.equals(this.inputColumns, addVirtualColumnEntry.inputColumns)
        && Objects.equals(this.dataType, addVirtualColumnEntry.dataType)
        && Objects.equals(this.image, addVirtualColumnEntry.image)
        && Objects.equals(this.udf, addVirtualColumnEntry.udf)
        && Objects.equals(this.udfName, addVirtualColumnEntry.udfName)
        && Objects.equals(this.udfVersion, addVirtualColumnEntry.udfVersion)
        && equalsNullable(this.udfBackend, addVirtualColumnEntry.udfBackend)
        && equalsNullable(this.autoBackfill, addVirtualColumnEntry.autoBackfill)
        && equalsNullable(this.manifest, addVirtualColumnEntry.manifest)
        && equalsNullable(this.manifestChecksum, addVirtualColumnEntry.manifestChecksum)
        && Objects.equals(this.fieldMetadata, addVirtualColumnEntry.fieldMetadata);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        inputColumns,
        dataType,
        image,
        udf,
        udfName,
        udfVersion,
        hashCodeNullable(udfBackend),
        hashCodeNullable(autoBackfill),
        hashCodeNullable(manifest),
        hashCodeNullable(manifestChecksum),
        fieldMetadata);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddVirtualColumnEntry {\n");
    sb.append("    inputColumns: ").append(toIndentedString(inputColumns)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    udf: ").append(toIndentedString(udf)).append("\n");
    sb.append("    udfName: ").append(toIndentedString(udfName)).append("\n");
    sb.append("    udfVersion: ").append(toIndentedString(udfVersion)).append("\n");
    sb.append("    udfBackend: ").append(toIndentedString(udfBackend)).append("\n");
    sb.append("    autoBackfill: ").append(toIndentedString(autoBackfill)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
    sb.append("    manifestChecksum: ").append(toIndentedString(manifestChecksum)).append("\n");
    sb.append("    fieldMetadata: ").append(toIndentedString(fieldMetadata)).append("\n");
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

    // add `input_columns` to the URL query string
    if (getInputColumns() != null) {
      for (int i = 0; i < getInputColumns().size(); i++) {
        joiner.add(
            String.format(
                "%sinput_columns%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getInputColumns().get(i)))));
      }
    }

    // add `data_type` to the URL query string
    if (getDataType() != null) {
      joiner.add(
          String.format(
              "%sdata_type%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getDataType()))));
    }

    // add `image` to the URL query string
    if (getImage() != null) {
      joiner.add(
          String.format(
              "%simage%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getImage()))));
    }

    // add `udf` to the URL query string
    if (getUdf() != null) {
      joiner.add(
          String.format(
              "%sudf%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getUdf()))));
    }

    // add `udf_name` to the URL query string
    if (getUdfName() != null) {
      joiner.add(
          String.format(
              "%sudf_name%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getUdfName()))));
    }

    // add `udf_version` to the URL query string
    if (getUdfVersion() != null) {
      joiner.add(
          String.format(
              "%sudf_version%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getUdfVersion()))));
    }

    // add `udf_backend` to the URL query string
    if (getUdfBackend() != null) {
      joiner.add(
          String.format(
              "%sudf_backend%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getUdfBackend()))));
    }

    // add `auto_backfill` to the URL query string
    if (getAutoBackfill() != null) {
      joiner.add(
          String.format(
              "%sauto_backfill%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getAutoBackfill()))));
    }

    // add `manifest` to the URL query string
    if (getManifest() != null) {
      joiner.add(
          String.format(
              "%smanifest%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getManifest()))));
    }

    // add `manifest_checksum` to the URL query string
    if (getManifestChecksum() != null) {
      joiner.add(
          String.format(
              "%smanifest_checksum%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getManifestChecksum()))));
    }

    // add `field_metadata` to the URL query string
    if (getFieldMetadata() != null) {
      for (String _key : getFieldMetadata().keySet()) {
        joiner.add(
            String.format(
                "%sfield_metadata%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getFieldMetadata().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getFieldMetadata().get(_key)))));
      }
    }

    return joiner.toString();
  }
}

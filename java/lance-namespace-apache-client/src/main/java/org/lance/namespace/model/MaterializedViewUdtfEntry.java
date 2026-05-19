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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** MaterializedViewUdtfEntry */
@JsonPropertyOrder({
  MaterializedViewUdtfEntry.JSON_PROPERTY_KIND,
  MaterializedViewUdtfEntry.JSON_PROPERTY_UDTF,
  MaterializedViewUdtfEntry.JSON_PROPERTY_UDTF_SHA,
  MaterializedViewUdtfEntry.JSON_PROPERTY_UDTF_NAME,
  MaterializedViewUdtfEntry.JSON_PROPERTY_UDTF_VERSION,
  MaterializedViewUdtfEntry.JSON_PROPERTY_INPUT_COLUMNS,
  MaterializedViewUdtfEntry.JSON_PROPERTY_PARTITION_BY,
  MaterializedViewUdtfEntry.JSON_PROPERTY_PARTITION_BY_INDEXED_COLUMN,
  MaterializedViewUdtfEntry.JSON_PROPERTY_NUM_CPUS,
  MaterializedViewUdtfEntry.JSON_PROPERTY_NUM_GPUS,
  MaterializedViewUdtfEntry.JSON_PROPERTY_MEMORY,
  MaterializedViewUdtfEntry.JSON_PROPERTY_ERROR_HANDLING,
  MaterializedViewUdtfEntry.JSON_PROPERTY_BATCH,
  MaterializedViewUdtfEntry.JSON_PROPERTY_MANIFEST,
  MaterializedViewUdtfEntry.JSON_PROPERTY_MANIFEST_CHECKSUM
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MaterializedViewUdtfEntry {
  /**
   * Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker
   * (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s
   * &#x60;kind&#x60;.
   */
  public enum KindEnum {
    UDTF(String.valueOf("udtf")),

    CHUNKER(String.valueOf("chunker"));

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_KIND = "kind";
  @javax.annotation.Nonnull private KindEnum kind;

  public static final String JSON_PROPERTY_UDTF = "udtf";
  @javax.annotation.Nonnull private String udtf;

  public static final String JSON_PROPERTY_UDTF_SHA = "udtf_sha";
  @javax.annotation.Nonnull private String udtfSha;

  public static final String JSON_PROPERTY_UDTF_NAME = "udtf_name";
  @javax.annotation.Nonnull private String udtfName;

  public static final String JSON_PROPERTY_UDTF_VERSION = "udtf_version";
  @javax.annotation.Nonnull private String udtfVersion;

  public static final String JSON_PROPERTY_INPUT_COLUMNS = "input_columns";

  @javax.annotation.Nullable
  private JsonNullable<List<String>> inputColumns = JsonNullable.<List<String>>undefined();

  public static final String JSON_PROPERTY_PARTITION_BY = "partition_by";

  @javax.annotation.Nullable
  private JsonNullable<String> partitionBy = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_PARTITION_BY_INDEXED_COLUMN =
      "partition_by_indexed_column";

  @javax.annotation.Nullable
  private JsonNullable<String> partitionByIndexedColumn = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_NUM_CPUS = "num_cpus";

  @javax.annotation.Nullable
  private JsonNullable<BigDecimal> numCpus = JsonNullable.<BigDecimal>undefined();

  public static final String JSON_PROPERTY_NUM_GPUS = "num_gpus";

  @javax.annotation.Nullable
  private JsonNullable<BigDecimal> numGpus = JsonNullable.<BigDecimal>undefined();

  public static final String JSON_PROPERTY_MEMORY = "memory";

  @javax.annotation.Nullable
  private JsonNullable<Integer> memory = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_ERROR_HANDLING = "error_handling";

  @javax.annotation.Nullable
  private JsonNullable<Object> errorHandling = JsonNullable.<Object>undefined();

  public static final String JSON_PROPERTY_BATCH = "batch";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> batch = JsonNullable.<Boolean>undefined();

  public static final String JSON_PROPERTY_MANIFEST = "manifest";

  @javax.annotation.Nullable
  private JsonNullable<String> manifest = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_MANIFEST_CHECKSUM = "manifest_checksum";

  @javax.annotation.Nullable
  private JsonNullable<String> manifestChecksum = JsonNullable.<String>undefined();

  public MaterializedViewUdtfEntry() {}

  public MaterializedViewUdtfEntry kind(@javax.annotation.Nonnull KindEnum kind) {

    this.kind = kind;
    return this;
  }

  /**
   * Discriminates a batch UDTF (&#x60;udtf&#x60;, full-overwrite refresh) from a chunker
   * (&#x60;chunker&#x60;, incremental 1:N refresh). Must match the enclosing request&#39;s
   * &#x60;kind&#x60;.
   *
   * @return kind
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_KIND)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public KindEnum getKind() {
    return kind;
  }

  @JsonProperty(JSON_PROPERTY_KIND)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKind(@javax.annotation.Nonnull KindEnum kind) {
    this.kind = kind;
  }

  public MaterializedViewUdtfEntry udtf(@javax.annotation.Nonnull String udtf) {

    this.udtf = udtf;
    return this;
  }

  /**
   * Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).
   *
   * @return udtf
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDTF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdtf() {
    return udtf;
  }

  @JsonProperty(JSON_PROPERTY_UDTF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdtf(@javax.annotation.Nonnull String udtf) {
    this.udtf = udtf;
  }

  public MaterializedViewUdtfEntry udtfSha(@javax.annotation.Nonnull String udtfSha) {

    this.udtfSha = udtfSha;
    return this;
  }

  /**
   * SHA-256 checksum of the envelope; server validates.
   *
   * @return udtfSha
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDTF_SHA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdtfSha() {
    return udtfSha;
  }

  @JsonProperty(JSON_PROPERTY_UDTF_SHA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdtfSha(@javax.annotation.Nonnull String udtfSha) {
    this.udtfSha = udtfSha;
  }

  public MaterializedViewUdtfEntry udtfName(@javax.annotation.Nonnull String udtfName) {

    this.udtfName = udtfName;
    return this;
  }

  /**
   * Name of the UDTF
   *
   * @return udtfName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDTF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdtfName() {
    return udtfName;
  }

  @JsonProperty(JSON_PROPERTY_UDTF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdtfName(@javax.annotation.Nonnull String udtfName) {
    this.udtfName = udtfName;
  }

  public MaterializedViewUdtfEntry udtfVersion(@javax.annotation.Nonnull String udtfVersion) {

    this.udtfVersion = udtfVersion;
    return this;
  }

  /**
   * Version of the UDTF
   *
   * @return udtfVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDTF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdtfVersion() {
    return udtfVersion;
  }

  @JsonProperty(JSON_PROPERTY_UDTF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdtfVersion(@javax.annotation.Nonnull String udtfVersion) {
    this.udtfVersion = udtfVersion;
  }

  public MaterializedViewUdtfEntry inputColumns(
      @javax.annotation.Nullable List<String> inputColumns) {
    this.inputColumns = JsonNullable.<List<String>>of(inputColumns);

    return this;
  }

  public MaterializedViewUdtfEntry addInputColumnsItem(String inputColumnsItem) {
    if (this.inputColumns == null || !this.inputColumns.isPresent()) {
      this.inputColumns = JsonNullable.<List<String>>of(new ArrayList<>());
    }
    try {
      this.inputColumns.get().add(inputColumnsItem);
    } catch (java.util.NoSuchElementException e) {
      // this can never happen, as we make sure above that the value is present
    }
    return this;
  }

  /**
   * Source columns the UDTF reads. Null means all columns (batch UDTF only).
   *
   * @return inputColumns
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public List<String> getInputColumns() {
    return inputColumns.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<List<String>> getInputColumns_JsonNullable() {
    return inputColumns;
  }

  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  public void setInputColumns_JsonNullable(JsonNullable<List<String>> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public void setInputColumns(@javax.annotation.Nullable List<String> inputColumns) {
    this.inputColumns = JsonNullable.<List<String>>of(inputColumns);
  }

  public MaterializedViewUdtfEntry partitionBy(@javax.annotation.Nullable String partitionBy) {
    this.partitionBy = JsonNullable.<String>of(partitionBy);

    return this;
  }

  /**
   * Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually
   * exclusive with &#x60;partition_by_indexed_column&#x60;.
   *
   * @return partitionBy
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getPartitionBy() {
    return partitionBy.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_PARTITION_BY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getPartitionBy_JsonNullable() {
    return partitionBy;
  }

  @JsonProperty(JSON_PROPERTY_PARTITION_BY)
  public void setPartitionBy_JsonNullable(JsonNullable<String> partitionBy) {
    this.partitionBy = partitionBy;
  }

  public void setPartitionBy(@javax.annotation.Nullable String partitionBy) {
    this.partitionBy = JsonNullable.<String>of(partitionBy);
  }

  public MaterializedViewUdtfEntry partitionByIndexedColumn(
      @javax.annotation.Nullable String partitionByIndexedColumn) {
    this.partitionByIndexedColumn = JsonNullable.<String>of(partitionByIndexedColumn);

    return this;
  }

  /**
   * Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The
   * server validates the index exists at create time.
   *
   * @return partitionByIndexedColumn
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getPartitionByIndexedColumn() {
    return partitionByIndexedColumn.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_PARTITION_BY_INDEXED_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getPartitionByIndexedColumn_JsonNullable() {
    return partitionByIndexedColumn;
  }

  @JsonProperty(JSON_PROPERTY_PARTITION_BY_INDEXED_COLUMN)
  public void setPartitionByIndexedColumn_JsonNullable(
      JsonNullable<String> partitionByIndexedColumn) {
    this.partitionByIndexedColumn = partitionByIndexedColumn;
  }

  public void setPartitionByIndexedColumn(
      @javax.annotation.Nullable String partitionByIndexedColumn) {
    this.partitionByIndexedColumn = JsonNullable.<String>of(partitionByIndexedColumn);
  }

  public MaterializedViewUdtfEntry numCpus(@javax.annotation.Nullable BigDecimal numCpus) {
    this.numCpus = JsonNullable.<BigDecimal>of(numCpus);

    return this;
  }

  /**
   * Ray actor CPU request.
   *
   * @return numCpus
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public BigDecimal getNumCpus() {
    return numCpus.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_NUM_CPUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<BigDecimal> getNumCpus_JsonNullable() {
    return numCpus;
  }

  @JsonProperty(JSON_PROPERTY_NUM_CPUS)
  public void setNumCpus_JsonNullable(JsonNullable<BigDecimal> numCpus) {
    this.numCpus = numCpus;
  }

  public void setNumCpus(@javax.annotation.Nullable BigDecimal numCpus) {
    this.numCpus = JsonNullable.<BigDecimal>of(numCpus);
  }

  public MaterializedViewUdtfEntry numGpus(@javax.annotation.Nullable BigDecimal numGpus) {
    this.numGpus = JsonNullable.<BigDecimal>of(numGpus);

    return this;
  }

  /**
   * Ray actor GPU request.
   *
   * @return numGpus
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public BigDecimal getNumGpus() {
    return numGpus.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_NUM_GPUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<BigDecimal> getNumGpus_JsonNullable() {
    return numGpus;
  }

  @JsonProperty(JSON_PROPERTY_NUM_GPUS)
  public void setNumGpus_JsonNullable(JsonNullable<BigDecimal> numGpus) {
    this.numGpus = numGpus;
  }

  public void setNumGpus(@javax.annotation.Nullable BigDecimal numGpus) {
    this.numGpus = JsonNullable.<BigDecimal>of(numGpus);
  }

  public MaterializedViewUdtfEntry memory(@javax.annotation.Nullable Integer memory) {
    this.memory = JsonNullable.<Integer>of(memory);

    return this;
  }

  /**
   * Ray actor memory request, in bytes.
   *
   * @return memory
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getMemory() {
    return memory.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MEMORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getMemory_JsonNullable() {
    return memory;
  }

  @JsonProperty(JSON_PROPERTY_MEMORY)
  public void setMemory_JsonNullable(JsonNullable<Integer> memory) {
    this.memory = memory;
  }

  public void setMemory(@javax.annotation.Nullable Integer memory) {
    this.memory = JsonNullable.<Integer>of(memory);
  }

  public MaterializedViewUdtfEntry errorHandling(@javax.annotation.Nullable Object errorHandling) {
    this.errorHandling = JsonNullable.<Object>of(errorHandling);

    return this;
  }

  /**
   * Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip
   * behavior.
   *
   * @return errorHandling
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Object getErrorHandling() {
    return errorHandling.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_ERROR_HANDLING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Object> getErrorHandling_JsonNullable() {
    return errorHandling;
  }

  @JsonProperty(JSON_PROPERTY_ERROR_HANDLING)
  public void setErrorHandling_JsonNullable(JsonNullable<Object> errorHandling) {
    this.errorHandling = errorHandling;
  }

  public void setErrorHandling(@javax.annotation.Nullable Object errorHandling) {
    this.errorHandling = JsonNullable.<Object>of(errorHandling);
  }

  public MaterializedViewUdtfEntry batch(@javax.annotation.Nullable Boolean batch) {
    this.batch = JsonNullable.<Boolean>of(batch);

    return this;
  }

  /**
   * Chunker only. True for a batched chunker; affects how the worker dispatches input rows.
   *
   * @return batch
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getBatch() {
    return batch.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_BATCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getBatch_JsonNullable() {
    return batch;
  }

  @JsonProperty(JSON_PROPERTY_BATCH)
  public void setBatch_JsonNullable(JsonNullable<Boolean> batch) {
    this.batch = batch;
  }

  public void setBatch(@javax.annotation.Nullable Boolean batch) {
    this.batch = JsonNullable.<Boolean>of(batch);
  }

  public MaterializedViewUdtfEntry manifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);

    return this;
  }

  /**
   * JSON-serialized GenevaManifest for the UDTF environment.
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

  public MaterializedViewUdtfEntry manifestChecksum(
      @javax.annotation.Nullable String manifestChecksum) {
    this.manifestChecksum = JsonNullable.<String>of(manifestChecksum);

    return this;
  }

  /**
   * SHA-256 checksum of the manifest content.
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaterializedViewUdtfEntry materializedViewUdtfEntry = (MaterializedViewUdtfEntry) o;
    return Objects.equals(this.kind, materializedViewUdtfEntry.kind)
        && Objects.equals(this.udtf, materializedViewUdtfEntry.udtf)
        && Objects.equals(this.udtfSha, materializedViewUdtfEntry.udtfSha)
        && Objects.equals(this.udtfName, materializedViewUdtfEntry.udtfName)
        && Objects.equals(this.udtfVersion, materializedViewUdtfEntry.udtfVersion)
        && equalsNullable(this.inputColumns, materializedViewUdtfEntry.inputColumns)
        && equalsNullable(this.partitionBy, materializedViewUdtfEntry.partitionBy)
        && equalsNullable(
            this.partitionByIndexedColumn, materializedViewUdtfEntry.partitionByIndexedColumn)
        && equalsNullable(this.numCpus, materializedViewUdtfEntry.numCpus)
        && equalsNullable(this.numGpus, materializedViewUdtfEntry.numGpus)
        && equalsNullable(this.memory, materializedViewUdtfEntry.memory)
        && equalsNullable(this.errorHandling, materializedViewUdtfEntry.errorHandling)
        && equalsNullable(this.batch, materializedViewUdtfEntry.batch)
        && equalsNullable(this.manifest, materializedViewUdtfEntry.manifest)
        && equalsNullable(this.manifestChecksum, materializedViewUdtfEntry.manifestChecksum);
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
        kind,
        udtf,
        udtfSha,
        udtfName,
        udtfVersion,
        hashCodeNullable(inputColumns),
        hashCodeNullable(partitionBy),
        hashCodeNullable(partitionByIndexedColumn),
        hashCodeNullable(numCpus),
        hashCodeNullable(numGpus),
        hashCodeNullable(memory),
        hashCodeNullable(errorHandling),
        hashCodeNullable(batch),
        hashCodeNullable(manifest),
        hashCodeNullable(manifestChecksum));
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
    sb.append("class MaterializedViewUdtfEntry {\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    udtf: ").append(toIndentedString(udtf)).append("\n");
    sb.append("    udtfSha: ").append(toIndentedString(udtfSha)).append("\n");
    sb.append("    udtfName: ").append(toIndentedString(udtfName)).append("\n");
    sb.append("    udtfVersion: ").append(toIndentedString(udtfVersion)).append("\n");
    sb.append("    inputColumns: ").append(toIndentedString(inputColumns)).append("\n");
    sb.append("    partitionBy: ").append(toIndentedString(partitionBy)).append("\n");
    sb.append("    partitionByIndexedColumn: ")
        .append(toIndentedString(partitionByIndexedColumn))
        .append("\n");
    sb.append("    numCpus: ").append(toIndentedString(numCpus)).append("\n");
    sb.append("    numGpus: ").append(toIndentedString(numGpus)).append("\n");
    sb.append("    memory: ").append(toIndentedString(memory)).append("\n");
    sb.append("    errorHandling: ").append(toIndentedString(errorHandling)).append("\n");
    sb.append("    batch: ").append(toIndentedString(batch)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
    sb.append("    manifestChecksum: ").append(toIndentedString(manifestChecksum)).append("\n");
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

    // add `kind` to the URL query string
    if (getKind() != null) {
      try {
        joiner.add(
            String.format(
                "%skind%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getKind()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udtf` to the URL query string
    if (getUdtf() != null) {
      try {
        joiner.add(
            String.format(
                "%sudtf%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdtf()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udtf_sha` to the URL query string
    if (getUdtfSha() != null) {
      try {
        joiner.add(
            String.format(
                "%sudtf_sha%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdtfSha()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udtf_name` to the URL query string
    if (getUdtfName() != null) {
      try {
        joiner.add(
            String.format(
                "%sudtf_name%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdtfName()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udtf_version` to the URL query string
    if (getUdtfVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sudtf_version%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdtfVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `input_columns` to the URL query string
    if (getInputColumns() != null) {
      for (int i = 0; i < getInputColumns().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sinput_columns%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getInputColumns().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `partition_by` to the URL query string
    if (getPartitionBy() != null) {
      try {
        joiner.add(
            String.format(
                "%spartition_by%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPartitionBy()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `partition_by_indexed_column` to the URL query string
    if (getPartitionByIndexedColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%spartition_by_indexed_column%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPartitionByIndexedColumn()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_cpus` to the URL query string
    if (getNumCpus() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_cpus%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumCpus()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_gpus` to the URL query string
    if (getNumGpus() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_gpus%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumGpus()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `memory` to the URL query string
    if (getMemory() != null) {
      try {
        joiner.add(
            String.format(
                "%smemory%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMemory()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `error_handling` to the URL query string
    if (getErrorHandling() != null) {
      try {
        joiner.add(
            String.format(
                "%serror_handling%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getErrorHandling()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `batch` to the URL query string
    if (getBatch() != null) {
      try {
        joiner.add(
            String.format(
                "%sbatch%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getBatch()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `manifest` to the URL query string
    if (getManifest() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifest%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifest()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `manifest_checksum` to the URL query string
    if (getManifestChecksum() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifest_checksum%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifestChecksum()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}

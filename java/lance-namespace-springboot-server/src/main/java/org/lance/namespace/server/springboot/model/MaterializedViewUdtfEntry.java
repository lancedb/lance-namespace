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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** MaterializedViewUdtfEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class MaterializedViewUdtfEntry {

  /**
   * Discriminates a batch UDTF (`udtf`, full-overwrite refresh) from a chunker (`chunker`,
   * incremental 1:N refresh). Must match the enclosing request's `kind`.
   */
  public enum KindEnum {
    UDTF("udtf"),

    CHUNKER("chunker");

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

  private KindEnum kind;

  private String udtf;

  private String udtfSha;

  private String udtfName;

  private String udtfVersion;

  @Valid private List<String> inputColumns;

  private String partitionBy = null;

  private String partitionByIndexedColumn = null;

  private BigDecimal numCpus = null;

  private BigDecimal numGpus = null;

  private Integer memory = null;

  private Object errorHandling = null;

  private Boolean batch = null;

  private String manifest = null;

  private String manifestChecksum = null;

  public MaterializedViewUdtfEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public MaterializedViewUdtfEntry(
      KindEnum kind, String udtf, String udtfSha, String udtfName, String udtfVersion) {
    this.kind = kind;
    this.udtf = udtf;
    this.udtfSha = udtfSha;
    this.udtfName = udtfName;
    this.udtfVersion = udtfVersion;
  }

  public MaterializedViewUdtfEntry kind(KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Discriminates a batch UDTF (`udtf`, full-overwrite refresh) from a chunker (`chunker`,
   * incremental 1:N refresh). Must match the enclosing request's `kind`.
   *
   * @return kind
   */
  @NotNull
  @Schema(
      name = "kind",
      description =
          "Discriminates a batch UDTF (`udtf`, full-overwrite refresh) from a chunker (`chunker`, incremental 1:N refresh). Must match the enclosing request's `kind`. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("kind")
  public KindEnum getKind() {
    return kind;
  }

  public void setKind(KindEnum kind) {
    this.kind = kind;
  }

  public MaterializedViewUdtfEntry udtf(String udtf) {
    this.udtf = udtf;
    return this;
  }

  /**
   * Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind).
   *
   * @return udtf
   */
  @NotNull
  @Schema(
      name = "udtf",
      description = "Base64-encoded UDTFSpec / ChunkerSpec JSON envelope (per kind). ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udtf")
  public String getUdtf() {
    return udtf;
  }

  public void setUdtf(String udtf) {
    this.udtf = udtf;
  }

  public MaterializedViewUdtfEntry udtfSha(String udtfSha) {
    this.udtfSha = udtfSha;
    return this;
  }

  /**
   * SHA-256 checksum of the envelope; server validates.
   *
   * @return udtfSha
   */
  @NotNull
  @Schema(
      name = "udtf_sha",
      description = "SHA-256 checksum of the envelope; server validates.",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udtf_sha")
  public String getUdtfSha() {
    return udtfSha;
  }

  public void setUdtfSha(String udtfSha) {
    this.udtfSha = udtfSha;
  }

  public MaterializedViewUdtfEntry udtfName(String udtfName) {
    this.udtfName = udtfName;
    return this;
  }

  /**
   * Name of the UDTF
   *
   * @return udtfName
   */
  @NotNull
  @Schema(
      name = "udtf_name",
      description = "Name of the UDTF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udtf_name")
  public String getUdtfName() {
    return udtfName;
  }

  public void setUdtfName(String udtfName) {
    this.udtfName = udtfName;
  }

  public MaterializedViewUdtfEntry udtfVersion(String udtfVersion) {
    this.udtfVersion = udtfVersion;
    return this;
  }

  /**
   * Version of the UDTF
   *
   * @return udtfVersion
   */
  @NotNull
  @Schema(
      name = "udtf_version",
      description = "Version of the UDTF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udtf_version")
  public String getUdtfVersion() {
    return udtfVersion;
  }

  public void setUdtfVersion(String udtfVersion) {
    this.udtfVersion = udtfVersion;
  }

  public MaterializedViewUdtfEntry inputColumns(List<String> inputColumns) {
    this.inputColumns = inputColumns;
    return this;
  }

  public MaterializedViewUdtfEntry addInputColumnsItem(String inputColumnsItem) {
    if (this.inputColumns == null) {
      this.inputColumns = new ArrayList<>();
    }
    this.inputColumns.add(inputColumnsItem);
    return this;
  }

  /**
   * Source columns the UDTF reads. Null means all columns (batch UDTF only).
   *
   * @return inputColumns
   */
  @Schema(
      name = "input_columns",
      description = "Source columns the UDTF reads. Null means all columns (batch UDTF only). ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("input_columns")
  public List<String> getInputColumns() {
    return inputColumns;
  }

  public void setInputColumns(List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public MaterializedViewUdtfEntry partitionBy(String partitionBy) {
    this.partitionBy = partitionBy;
    return this;
  }

  /**
   * Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually
   * exclusive with `partition_by_indexed_column`.
   *
   * @return partitionBy
   */
  @Schema(
      name = "partition_by",
      description =
          "Batch UDTF only. Column-value partition key for partition-parallel execution. Mutually exclusive with `partition_by_indexed_column`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partition_by")
  public String getPartitionBy() {
    return partitionBy;
  }

  public void setPartitionBy(String partitionBy) {
    this.partitionBy = partitionBy;
  }

  public MaterializedViewUdtfEntry partitionByIndexedColumn(String partitionByIndexedColumn) {
    this.partitionByIndexedColumn = partitionByIndexedColumn;
    return this;
  }

  /**
   * Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The
   * server validates the index exists at create time.
   *
   * @return partitionByIndexedColumn
   */
  @Schema(
      name = "partition_by_indexed_column",
      description =
          "Batch UDTF only. Source column with an IVF-family index used for index-based partitioning. The server validates the index exists at create time. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("partition_by_indexed_column")
  public String getPartitionByIndexedColumn() {
    return partitionByIndexedColumn;
  }

  public void setPartitionByIndexedColumn(String partitionByIndexedColumn) {
    this.partitionByIndexedColumn = partitionByIndexedColumn;
  }

  public MaterializedViewUdtfEntry numCpus(BigDecimal numCpus) {
    this.numCpus = numCpus;
    return this;
  }

  /**
   * Ray actor CPU request.
   *
   * @return numCpus
   */
  @Valid
  @Schema(
      name = "num_cpus",
      description = "Ray actor CPU request.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_cpus")
  public BigDecimal getNumCpus() {
    return numCpus;
  }

  public void setNumCpus(BigDecimal numCpus) {
    this.numCpus = numCpus;
  }

  public MaterializedViewUdtfEntry numGpus(BigDecimal numGpus) {
    this.numGpus = numGpus;
    return this;
  }

  /**
   * Ray actor GPU request.
   *
   * @return numGpus
   */
  @Valid
  @Schema(
      name = "num_gpus",
      description = "Ray actor GPU request.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_gpus")
  public BigDecimal getNumGpus() {
    return numGpus;
  }

  public void setNumGpus(BigDecimal numGpus) {
    this.numGpus = numGpus;
  }

  public MaterializedViewUdtfEntry memory(Integer memory) {
    this.memory = memory;
    return this;
  }

  /**
   * Ray actor memory request, in bytes.
   *
   * @return memory
   */
  @Schema(
      name = "memory",
      description = "Ray actor memory request, in bytes.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("memory")
  public Integer getMemory() {
    return memory;
  }

  public void setMemory(Integer memory) {
    this.memory = memory;
  }

  public MaterializedViewUdtfEntry errorHandling(Object errorHandling) {
    this.errorHandling = errorHandling;
    return this;
  }

  /**
   * Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip
   * behavior.
   *
   * @return errorHandling
   */
  @Schema(
      name = "error_handling",
      description =
          "Batch UDTF only. Serialized ErrorHandlingConfig controlling partition-grain fail/retry/skip behavior. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error_handling")
  public Object getErrorHandling() {
    return errorHandling;
  }

  public void setErrorHandling(Object errorHandling) {
    this.errorHandling = errorHandling;
  }

  public MaterializedViewUdtfEntry batch(Boolean batch) {
    this.batch = batch;
    return this;
  }

  /**
   * Chunker only. True for a batched chunker; affects how the worker dispatches input rows.
   *
   * @return batch
   */
  @Schema(
      name = "batch",
      description =
          "Chunker only. True for a batched chunker; affects how the worker dispatches input rows. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("batch")
  public Boolean getBatch() {
    return batch;
  }

  public void setBatch(Boolean batch) {
    this.batch = batch;
  }

  public MaterializedViewUdtfEntry manifest(String manifest) {
    this.manifest = manifest;
    return this;
  }

  /**
   * JSON-serialized GenevaManifest for the UDTF environment.
   *
   * @return manifest
   */
  @Schema(
      name = "manifest",
      description = "JSON-serialized GenevaManifest for the UDTF environment.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("manifest")
  public String getManifest() {
    return manifest;
  }

  public void setManifest(String manifest) {
    this.manifest = manifest;
  }

  public MaterializedViewUdtfEntry manifestChecksum(String manifestChecksum) {
    this.manifestChecksum = manifestChecksum;
    return this;
  }

  /**
   * SHA-256 checksum of the manifest content.
   *
   * @return manifestChecksum
   */
  @Schema(
      name = "manifest_checksum",
      description = "SHA-256 checksum of the manifest content.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("manifest_checksum")
  public String getManifestChecksum() {
    return manifestChecksum;
  }

  public void setManifestChecksum(String manifestChecksum) {
    this.manifestChecksum = manifestChecksum;
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
        && Objects.equals(this.inputColumns, materializedViewUdtfEntry.inputColumns)
        && Objects.equals(this.partitionBy, materializedViewUdtfEntry.partitionBy)
        && Objects.equals(
            this.partitionByIndexedColumn, materializedViewUdtfEntry.partitionByIndexedColumn)
        && Objects.equals(this.numCpus, materializedViewUdtfEntry.numCpus)
        && Objects.equals(this.numGpus, materializedViewUdtfEntry.numGpus)
        && Objects.equals(this.memory, materializedViewUdtfEntry.memory)
        && Objects.equals(this.errorHandling, materializedViewUdtfEntry.errorHandling)
        && Objects.equals(this.batch, materializedViewUdtfEntry.batch)
        && Objects.equals(this.manifest, materializedViewUdtfEntry.manifest)
        && Objects.equals(this.manifestChecksum, materializedViewUdtfEntry.manifestChecksum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        kind,
        udtf,
        udtfSha,
        udtfName,
        udtfVersion,
        inputColumns,
        partitionBy,
        partitionByIndexedColumn,
        numCpus,
        numGpus,
        memory,
        errorHandling,
        batch,
        manifest,
        manifestChecksum);
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
}

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** AlterTableBackfillColumnsRequest */
@JsonPropertyOrder({
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_IDENTITY,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_ID,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_COLUMN,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_WHERE,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_CONCURRENCY,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_MIN_CHECKPOINT_SIZE,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_MAX_CHECKPOINT_SIZE,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_BATCH_CHECKPOINT_FLUSH_INTERVAL_SECONDS,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_READ_VERSION,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_TASK_SIZE,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_NUM_FRAGS,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_CHECKPOINT_SIZE,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_COMMIT_GRANULARITY,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_CLUSTER,
  AlterTableBackfillColumnsRequest.JSON_PROPERTY_MANIFEST
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableBackfillColumnsRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nonnull private String column;

  public static final String JSON_PROPERTY_WHERE = "where";
  private JsonNullable<String> where = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_CONCURRENCY = "concurrency";
  private JsonNullable<Integer> concurrency = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY = "intra_applier_concurrency";
  private JsonNullable<Integer> intraApplierConcurrency = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_MIN_CHECKPOINT_SIZE = "min_checkpoint_size";
  private JsonNullable<Integer> minCheckpointSize = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_MAX_CHECKPOINT_SIZE = "max_checkpoint_size";
  private JsonNullable<Integer> maxCheckpointSize = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_BATCH_CHECKPOINT_FLUSH_INTERVAL_SECONDS =
      "batch_checkpoint_flush_interval_seconds";
  private JsonNullable<BigDecimal> batchCheckpointFlushIntervalSeconds =
      JsonNullable.<BigDecimal>undefined();

  public static final String JSON_PROPERTY_READ_VERSION = "read_version";
  private JsonNullable<Integer> readVersion = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_TASK_SIZE = "task_size";
  private JsonNullable<Integer> taskSize = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_NUM_FRAGS = "num_frags";
  private JsonNullable<Integer> numFrags = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_CHECKPOINT_SIZE = "checkpoint_size";
  private JsonNullable<Integer> checkpointSize = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_COMMIT_GRANULARITY = "commit_granularity";
  private JsonNullable<Integer> commitGranularity = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_CLUSTER = "cluster";
  private JsonNullable<String> cluster = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_MANIFEST = "manifest";
  private JsonNullable<String> manifest = JsonNullable.<String>undefined();

  public AlterTableBackfillColumnsRequest() {}

  public AlterTableBackfillColumnsRequest identity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public AlterTableBackfillColumnsRequest id(@javax.annotation.Nullable List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableBackfillColumnsRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Table identifier path (namespace + table name)
   *
   * @return id
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public AlterTableBackfillColumnsRequest column(@javax.annotation.Nonnull String column) {
    this.column = column;
    return this;
  }

  /**
   * Column name to backfill
   *
   * @return column
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getColumn() {
    return column;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setColumn(@javax.annotation.Nonnull String column) {
    this.column = column;
  }

  public AlterTableBackfillColumnsRequest where(@javax.annotation.Nullable String where) {
    this.where = JsonNullable.<String>of(where);
    return this;
  }

  /**
   * Optional WHERE clause filter
   *
   * @return where
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getWhere() {
    return where.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_WHERE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getWhere_JsonNullable() {
    return where;
  }

  @JsonProperty(JSON_PROPERTY_WHERE)
  public void setWhere_JsonNullable(JsonNullable<String> where) {
    this.where = where;
  }

  public void setWhere(@javax.annotation.Nullable String where) {
    this.where = JsonNullable.<String>of(where);
  }

  public AlterTableBackfillColumnsRequest concurrency(
      @javax.annotation.Nullable Integer concurrency) {
    this.concurrency = JsonNullable.<Integer>of(concurrency);
    return this;
  }

  /**
   * Optional concurrency override
   *
   * @return concurrency
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getConcurrency() {
    return concurrency.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_CONCURRENCY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getConcurrency_JsonNullable() {
    return concurrency;
  }

  @JsonProperty(JSON_PROPERTY_CONCURRENCY)
  public void setConcurrency_JsonNullable(JsonNullable<Integer> concurrency) {
    this.concurrency = concurrency;
  }

  public void setConcurrency(@javax.annotation.Nullable Integer concurrency) {
    this.concurrency = JsonNullable.<Integer>of(concurrency);
  }

  public AlterTableBackfillColumnsRequest intraApplierConcurrency(
      @javax.annotation.Nullable Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = JsonNullable.<Integer>of(intraApplierConcurrency);
    return this;
  }

  /**
   * Optional intra-applier concurrency override
   *
   * @return intraApplierConcurrency
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getIntraApplierConcurrency() {
    return intraApplierConcurrency.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getIntraApplierConcurrency_JsonNullable() {
    return intraApplierConcurrency;
  }

  @JsonProperty(JSON_PROPERTY_INTRA_APPLIER_CONCURRENCY)
  public void setIntraApplierConcurrency_JsonNullable(
      JsonNullable<Integer> intraApplierConcurrency) {
    this.intraApplierConcurrency = intraApplierConcurrency;
  }

  public void setIntraApplierConcurrency(
      @javax.annotation.Nullable Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = JsonNullable.<Integer>of(intraApplierConcurrency);
  }

  public AlterTableBackfillColumnsRequest minCheckpointSize(
      @javax.annotation.Nullable Integer minCheckpointSize) {
    this.minCheckpointSize = JsonNullable.<Integer>of(minCheckpointSize);
    return this;
  }

  /**
   * Optional minimum checkpoint size
   *
   * @return minCheckpointSize
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getMinCheckpointSize() {
    return minCheckpointSize.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MIN_CHECKPOINT_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getMinCheckpointSize_JsonNullable() {
    return minCheckpointSize;
  }

  @JsonProperty(JSON_PROPERTY_MIN_CHECKPOINT_SIZE)
  public void setMinCheckpointSize_JsonNullable(JsonNullable<Integer> minCheckpointSize) {
    this.minCheckpointSize = minCheckpointSize;
  }

  public void setMinCheckpointSize(@javax.annotation.Nullable Integer minCheckpointSize) {
    this.minCheckpointSize = JsonNullable.<Integer>of(minCheckpointSize);
  }

  public AlterTableBackfillColumnsRequest maxCheckpointSize(
      @javax.annotation.Nullable Integer maxCheckpointSize) {
    this.maxCheckpointSize = JsonNullable.<Integer>of(maxCheckpointSize);
    return this;
  }

  /**
   * Optional maximum checkpoint size
   *
   * @return maxCheckpointSize
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getMaxCheckpointSize() {
    return maxCheckpointSize.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_MAX_CHECKPOINT_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getMaxCheckpointSize_JsonNullable() {
    return maxCheckpointSize;
  }

  @JsonProperty(JSON_PROPERTY_MAX_CHECKPOINT_SIZE)
  public void setMaxCheckpointSize_JsonNullable(JsonNullable<Integer> maxCheckpointSize) {
    this.maxCheckpointSize = maxCheckpointSize;
  }

  public void setMaxCheckpointSize(@javax.annotation.Nullable Integer maxCheckpointSize) {
    this.maxCheckpointSize = JsonNullable.<Integer>of(maxCheckpointSize);
  }

  public AlterTableBackfillColumnsRequest batchCheckpointFlushIntervalSeconds(
      @javax.annotation.Nullable BigDecimal batchCheckpointFlushIntervalSeconds) {
    this.batchCheckpointFlushIntervalSeconds =
        JsonNullable.<BigDecimal>of(batchCheckpointFlushIntervalSeconds);
    return this;
  }

  /**
   * Optional batch checkpoint flush interval in seconds
   *
   * @return batchCheckpointFlushIntervalSeconds
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public BigDecimal getBatchCheckpointFlushIntervalSeconds() {
    return batchCheckpointFlushIntervalSeconds.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_BATCH_CHECKPOINT_FLUSH_INTERVAL_SECONDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<BigDecimal> getBatchCheckpointFlushIntervalSeconds_JsonNullable() {
    return batchCheckpointFlushIntervalSeconds;
  }

  @JsonProperty(JSON_PROPERTY_BATCH_CHECKPOINT_FLUSH_INTERVAL_SECONDS)
  public void setBatchCheckpointFlushIntervalSeconds_JsonNullable(
      JsonNullable<BigDecimal> batchCheckpointFlushIntervalSeconds) {
    this.batchCheckpointFlushIntervalSeconds = batchCheckpointFlushIntervalSeconds;
  }

  public void setBatchCheckpointFlushIntervalSeconds(
      @javax.annotation.Nullable BigDecimal batchCheckpointFlushIntervalSeconds) {
    this.batchCheckpointFlushIntervalSeconds =
        JsonNullable.<BigDecimal>of(batchCheckpointFlushIntervalSeconds);
  }

  public AlterTableBackfillColumnsRequest readVersion(
      @javax.annotation.Nullable Integer readVersion) {
    this.readVersion = JsonNullable.<Integer>of(readVersion);
    return this;
  }

  /**
   * Optional table version to read from
   *
   * @return readVersion
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getReadVersion() {
    return readVersion.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_READ_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getReadVersion_JsonNullable() {
    return readVersion;
  }

  @JsonProperty(JSON_PROPERTY_READ_VERSION)
  public void setReadVersion_JsonNullable(JsonNullable<Integer> readVersion) {
    this.readVersion = readVersion;
  }

  public void setReadVersion(@javax.annotation.Nullable Integer readVersion) {
    this.readVersion = JsonNullable.<Integer>of(readVersion);
  }

  public AlterTableBackfillColumnsRequest taskSize(@javax.annotation.Nullable Integer taskSize) {
    this.taskSize = JsonNullable.<Integer>of(taskSize);
    return this;
  }

  /**
   * Optional task size
   *
   * @return taskSize
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getTaskSize() {
    return taskSize.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_TASK_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getTaskSize_JsonNullable() {
    return taskSize;
  }

  @JsonProperty(JSON_PROPERTY_TASK_SIZE)
  public void setTaskSize_JsonNullable(JsonNullable<Integer> taskSize) {
    this.taskSize = taskSize;
  }

  public void setTaskSize(@javax.annotation.Nullable Integer taskSize) {
    this.taskSize = JsonNullable.<Integer>of(taskSize);
  }

  public AlterTableBackfillColumnsRequest numFrags(@javax.annotation.Nullable Integer numFrags) {
    this.numFrags = JsonNullable.<Integer>of(numFrags);
    return this;
  }

  /**
   * Optional number of fragments
   *
   * @return numFrags
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getNumFrags() {
    return numFrags.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_NUM_FRAGS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getNumFrags_JsonNullable() {
    return numFrags;
  }

  @JsonProperty(JSON_PROPERTY_NUM_FRAGS)
  public void setNumFrags_JsonNullable(JsonNullable<Integer> numFrags) {
    this.numFrags = numFrags;
  }

  public void setNumFrags(@javax.annotation.Nullable Integer numFrags) {
    this.numFrags = JsonNullable.<Integer>of(numFrags);
  }

  public AlterTableBackfillColumnsRequest checkpointSize(
      @javax.annotation.Nullable Integer checkpointSize) {
    this.checkpointSize = JsonNullable.<Integer>of(checkpointSize);
    return this;
  }

  /**
   * Optional checkpoint size
   *
   * @return checkpointSize
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getCheckpointSize() {
    return checkpointSize.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_CHECKPOINT_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getCheckpointSize_JsonNullable() {
    return checkpointSize;
  }

  @JsonProperty(JSON_PROPERTY_CHECKPOINT_SIZE)
  public void setCheckpointSize_JsonNullable(JsonNullable<Integer> checkpointSize) {
    this.checkpointSize = checkpointSize;
  }

  public void setCheckpointSize(@javax.annotation.Nullable Integer checkpointSize) {
    this.checkpointSize = JsonNullable.<Integer>of(checkpointSize);
  }

  public AlterTableBackfillColumnsRequest commitGranularity(
      @javax.annotation.Nullable Integer commitGranularity) {
    this.commitGranularity = JsonNullable.<Integer>of(commitGranularity);
    return this;
  }

  /**
   * Optional commit granularity
   *
   * @return commitGranularity
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getCommitGranularity() {
    return commitGranularity.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_COMMIT_GRANULARITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getCommitGranularity_JsonNullable() {
    return commitGranularity;
  }

  @JsonProperty(JSON_PROPERTY_COMMIT_GRANULARITY)
  public void setCommitGranularity_JsonNullable(JsonNullable<Integer> commitGranularity) {
    this.commitGranularity = commitGranularity;
  }

  public void setCommitGranularity(@javax.annotation.Nullable Integer commitGranularity) {
    this.commitGranularity = JsonNullable.<Integer>of(commitGranularity);
  }

  public AlterTableBackfillColumnsRequest cluster(@javax.annotation.Nullable String cluster) {
    this.cluster = JsonNullable.<String>of(cluster);
    return this;
  }

  /**
   * Optional cluster name
   *
   * @return cluster
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getCluster() {
    return cluster.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_CLUSTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getCluster_JsonNullable() {
    return cluster;
  }

  @JsonProperty(JSON_PROPERTY_CLUSTER)
  public void setCluster_JsonNullable(JsonNullable<String> cluster) {
    this.cluster = cluster;
  }

  public void setCluster(@javax.annotation.Nullable String cluster) {
    this.cluster = JsonNullable.<String>of(cluster);
  }

  public AlterTableBackfillColumnsRequest manifest(@javax.annotation.Nullable String manifest) {
    this.manifest = JsonNullable.<String>of(manifest);
    return this;
  }

  /**
   * Optional manifest name
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

  /** Return true if this AlterTableBackfillColumnsRequest object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableBackfillColumnsRequest alterTableBackfillColumnsRequest =
        (AlterTableBackfillColumnsRequest) o;
    return Objects.equals(this.identity, alterTableBackfillColumnsRequest.identity)
        && Objects.equals(this.id, alterTableBackfillColumnsRequest.id)
        && Objects.equals(this.column, alterTableBackfillColumnsRequest.column)
        && equalsNullable(this.where, alterTableBackfillColumnsRequest.where)
        && equalsNullable(this.concurrency, alterTableBackfillColumnsRequest.concurrency)
        && equalsNullable(
            this.intraApplierConcurrency, alterTableBackfillColumnsRequest.intraApplierConcurrency)
        && equalsNullable(
            this.minCheckpointSize, alterTableBackfillColumnsRequest.minCheckpointSize)
        && equalsNullable(
            this.maxCheckpointSize, alterTableBackfillColumnsRequest.maxCheckpointSize)
        && equalsNullable(
            this.batchCheckpointFlushIntervalSeconds,
            alterTableBackfillColumnsRequest.batchCheckpointFlushIntervalSeconds)
        && equalsNullable(this.readVersion, alterTableBackfillColumnsRequest.readVersion)
        && equalsNullable(this.taskSize, alterTableBackfillColumnsRequest.taskSize)
        && equalsNullable(this.numFrags, alterTableBackfillColumnsRequest.numFrags)
        && equalsNullable(this.checkpointSize, alterTableBackfillColumnsRequest.checkpointSize)
        && equalsNullable(
            this.commitGranularity, alterTableBackfillColumnsRequest.commitGranularity)
        && equalsNullable(this.cluster, alterTableBackfillColumnsRequest.cluster)
        && equalsNullable(this.manifest, alterTableBackfillColumnsRequest.manifest);
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
        identity,
        id,
        column,
        hashCodeNullable(where),
        hashCodeNullable(concurrency),
        hashCodeNullable(intraApplierConcurrency),
        hashCodeNullable(minCheckpointSize),
        hashCodeNullable(maxCheckpointSize),
        hashCodeNullable(batchCheckpointFlushIntervalSeconds),
        hashCodeNullable(readVersion),
        hashCodeNullable(taskSize),
        hashCodeNullable(numFrags),
        hashCodeNullable(checkpointSize),
        hashCodeNullable(commitGranularity),
        hashCodeNullable(cluster),
        hashCodeNullable(manifest));
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
    sb.append("class AlterTableBackfillColumnsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    where: ").append(toIndentedString(where)).append("\n");
    sb.append("    concurrency: ").append(toIndentedString(concurrency)).append("\n");
    sb.append("    intraApplierConcurrency: ")
        .append(toIndentedString(intraApplierConcurrency))
        .append("\n");
    sb.append("    minCheckpointSize: ").append(toIndentedString(minCheckpointSize)).append("\n");
    sb.append("    maxCheckpointSize: ").append(toIndentedString(maxCheckpointSize)).append("\n");
    sb.append("    batchCheckpointFlushIntervalSeconds: ")
        .append(toIndentedString(batchCheckpointFlushIntervalSeconds))
        .append("\n");
    sb.append("    readVersion: ").append(toIndentedString(readVersion)).append("\n");
    sb.append("    taskSize: ").append(toIndentedString(taskSize)).append("\n");
    sb.append("    numFrags: ").append(toIndentedString(numFrags)).append("\n");
    sb.append("    checkpointSize: ").append(toIndentedString(checkpointSize)).append("\n");
    sb.append("    commitGranularity: ").append(toIndentedString(commitGranularity)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        joiner.add(
            String.format(
                "%sid%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getId().get(i)))));
      }
    }

    // add `column` to the URL query string
    if (getColumn() != null) {
      joiner.add(
          String.format(
              "%scolumn%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getColumn()))));
    }

    // add `where` to the URL query string
    if (getWhere() != null) {
      joiner.add(
          String.format(
              "%swhere%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getWhere()))));
    }

    // add `concurrency` to the URL query string
    if (getConcurrency() != null) {
      joiner.add(
          String.format(
              "%sconcurrency%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getConcurrency()))));
    }

    // add `intra_applier_concurrency` to the URL query string
    if (getIntraApplierConcurrency() != null) {
      joiner.add(
          String.format(
              "%sintra_applier_concurrency%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getIntraApplierConcurrency()))));
    }

    // add `min_checkpoint_size` to the URL query string
    if (getMinCheckpointSize() != null) {
      joiner.add(
          String.format(
              "%smin_checkpoint_size%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getMinCheckpointSize()))));
    }

    // add `max_checkpoint_size` to the URL query string
    if (getMaxCheckpointSize() != null) {
      joiner.add(
          String.format(
              "%smax_checkpoint_size%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getMaxCheckpointSize()))));
    }

    // add `batch_checkpoint_flush_interval_seconds` to the URL query string
    if (getBatchCheckpointFlushIntervalSeconds() != null) {
      joiner.add(
          String.format(
              "%sbatch_checkpoint_flush_interval_seconds%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(
                  ApiClient.valueToString(getBatchCheckpointFlushIntervalSeconds()))));
    }

    // add `read_version` to the URL query string
    if (getReadVersion() != null) {
      joiner.add(
          String.format(
              "%sread_version%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getReadVersion()))));
    }

    // add `task_size` to the URL query string
    if (getTaskSize() != null) {
      joiner.add(
          String.format(
              "%stask_size%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTaskSize()))));
    }

    // add `num_frags` to the URL query string
    if (getNumFrags() != null) {
      joiner.add(
          String.format(
              "%snum_frags%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumFrags()))));
    }

    // add `checkpoint_size` to the URL query string
    if (getCheckpointSize() != null) {
      joiner.add(
          String.format(
              "%scheckpoint_size%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getCheckpointSize()))));
    }

    // add `commit_granularity` to the URL query string
    if (getCommitGranularity() != null) {
      joiner.add(
          String.format(
              "%scommit_granularity%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getCommitGranularity()))));
    }

    // add `cluster` to the URL query string
    if (getCluster() != null) {
      joiner.add(
          String.format(
              "%scluster%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getCluster()))));
    }

    // add `manifest` to the URL query string
    if (getManifest() != null) {
      joiner.add(
          String.format(
              "%smanifest%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getManifest()))));
    }

    return joiner.toString();
  }
}

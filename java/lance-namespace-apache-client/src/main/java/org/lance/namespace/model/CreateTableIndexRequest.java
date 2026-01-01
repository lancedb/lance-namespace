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
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateTableIndexRequest */
@JsonPropertyOrder({
  CreateTableIndexRequest.JSON_PROPERTY_IDENTITY,
  CreateTableIndexRequest.JSON_PROPERTY_ID,
  CreateTableIndexRequest.JSON_PROPERTY_COLUMN,
  CreateTableIndexRequest.JSON_PROPERTY_INDEX_TYPE,
  CreateTableIndexRequest.JSON_PROPERTY_NAME,
  CreateTableIndexRequest.JSON_PROPERTY_DISTANCE_TYPE,
  CreateTableIndexRequest.JSON_PROPERTY_WITH_POSITION,
  CreateTableIndexRequest.JSON_PROPERTY_BASE_TOKENIZER,
  CreateTableIndexRequest.JSON_PROPERTY_LANGUAGE,
  CreateTableIndexRequest.JSON_PROPERTY_MAX_TOKEN_LENGTH,
  CreateTableIndexRequest.JSON_PROPERTY_LOWER_CASE,
  CreateTableIndexRequest.JSON_PROPERTY_STEM,
  CreateTableIndexRequest.JSON_PROPERTY_REMOVE_STOP_WORDS,
  CreateTableIndexRequest.JSON_PROPERTY_ASCII_FOLDING
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableIndexRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nonnull private String column;

  public static final String JSON_PROPERTY_INDEX_TYPE = "index_type";
  @javax.annotation.Nonnull private String indexType;

  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nullable private String name;

  public static final String JSON_PROPERTY_DISTANCE_TYPE = "distance_type";
  @javax.annotation.Nullable private String distanceType;

  public static final String JSON_PROPERTY_WITH_POSITION = "with_position";
  @javax.annotation.Nullable private Boolean withPosition;

  public static final String JSON_PROPERTY_BASE_TOKENIZER = "base_tokenizer";
  @javax.annotation.Nullable private String baseTokenizer;

  public static final String JSON_PROPERTY_LANGUAGE = "language";
  @javax.annotation.Nullable private String language;

  public static final String JSON_PROPERTY_MAX_TOKEN_LENGTH = "max_token_length";
  @javax.annotation.Nullable private Integer maxTokenLength;

  public static final String JSON_PROPERTY_LOWER_CASE = "lower_case";
  @javax.annotation.Nullable private Boolean lowerCase;

  public static final String JSON_PROPERTY_STEM = "stem";
  @javax.annotation.Nullable private Boolean stem;

  public static final String JSON_PROPERTY_REMOVE_STOP_WORDS = "remove_stop_words";
  @javax.annotation.Nullable private Boolean removeStopWords;

  public static final String JSON_PROPERTY_ASCII_FOLDING = "ascii_folding";
  @javax.annotation.Nullable private Boolean asciiFolding;

  public CreateTableIndexRequest() {}

  public CreateTableIndexRequest identity(@javax.annotation.Nullable Identity identity) {

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

  public CreateTableIndexRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public CreateTableIndexRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
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

  public CreateTableIndexRequest column(@javax.annotation.Nonnull String column) {

    this.column = column;
    return this;
  }

  /**
   * Name of the column to create index on
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

  public CreateTableIndexRequest indexType(@javax.annotation.Nonnull String indexType) {

    this.indexType = indexType;
    return this;
  }

  /**
   * Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS)
   *
   * @return indexType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getIndexType() {
    return indexType;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexType(@javax.annotation.Nonnull String indexType) {
    this.indexType = indexType;
  }

  public CreateTableIndexRequest name(@javax.annotation.Nullable String name) {

    this.name = name;
    return this;
  }

  /**
   * Optional name for the index. If not provided, a name will be auto-generated.
   *
   * @return name
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(@javax.annotation.Nullable String name) {
    this.name = name;
  }

  public CreateTableIndexRequest distanceType(@javax.annotation.Nullable String distanceType) {

    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance metric type for vector indexes (e.g., l2, cosine, dot)
   *
   * @return distanceType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getDistanceType() {
    return distanceType;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDistanceType(@javax.annotation.Nullable String distanceType) {
    this.distanceType = distanceType;
  }

  public CreateTableIndexRequest withPosition(@javax.annotation.Nullable Boolean withPosition) {

    this.withPosition = withPosition;
    return this;
  }

  /**
   * Optional FTS parameter for position tracking
   *
   * @return withPosition
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WITH_POSITION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWithPosition() {
    return withPosition;
  }

  @JsonProperty(JSON_PROPERTY_WITH_POSITION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWithPosition(@javax.annotation.Nullable Boolean withPosition) {
    this.withPosition = withPosition;
  }

  public CreateTableIndexRequest baseTokenizer(@javax.annotation.Nullable String baseTokenizer) {

    this.baseTokenizer = baseTokenizer;
    return this;
  }

  /**
   * Optional FTS parameter for base tokenizer
   *
   * @return baseTokenizer
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BASE_TOKENIZER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getBaseTokenizer() {
    return baseTokenizer;
  }

  @JsonProperty(JSON_PROPERTY_BASE_TOKENIZER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBaseTokenizer(@javax.annotation.Nullable String baseTokenizer) {
    this.baseTokenizer = baseTokenizer;
  }

  public CreateTableIndexRequest language(@javax.annotation.Nullable String language) {

    this.language = language;
    return this;
  }

  /**
   * Optional FTS parameter for language
   *
   * @return language
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LANGUAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getLanguage() {
    return language;
  }

  @JsonProperty(JSON_PROPERTY_LANGUAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLanguage(@javax.annotation.Nullable String language) {
    this.language = language;
  }

  public CreateTableIndexRequest maxTokenLength(@javax.annotation.Nullable Integer maxTokenLength) {

    this.maxTokenLength = maxTokenLength;
    return this;
  }

  /**
   * Optional FTS parameter for maximum token length minimum: 0
   *
   * @return maxTokenLength
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MAX_TOKEN_LENGTH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getMaxTokenLength() {
    return maxTokenLength;
  }

  @JsonProperty(JSON_PROPERTY_MAX_TOKEN_LENGTH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMaxTokenLength(@javax.annotation.Nullable Integer maxTokenLength) {
    this.maxTokenLength = maxTokenLength;
  }

  public CreateTableIndexRequest lowerCase(@javax.annotation.Nullable Boolean lowerCase) {

    this.lowerCase = lowerCase;
    return this;
  }

  /**
   * Optional FTS parameter for lowercase conversion
   *
   * @return lowerCase
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOWER_CASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getLowerCase() {
    return lowerCase;
  }

  @JsonProperty(JSON_PROPERTY_LOWER_CASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLowerCase(@javax.annotation.Nullable Boolean lowerCase) {
    this.lowerCase = lowerCase;
  }

  public CreateTableIndexRequest stem(@javax.annotation.Nullable Boolean stem) {

    this.stem = stem;
    return this;
  }

  /**
   * Optional FTS parameter for stemming
   *
   * @return stem
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STEM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getStem() {
    return stem;
  }

  @JsonProperty(JSON_PROPERTY_STEM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStem(@javax.annotation.Nullable Boolean stem) {
    this.stem = stem;
  }

  public CreateTableIndexRequest removeStopWords(
      @javax.annotation.Nullable Boolean removeStopWords) {

    this.removeStopWords = removeStopWords;
    return this;
  }

  /**
   * Optional FTS parameter for stop word removal
   *
   * @return removeStopWords
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REMOVE_STOP_WORDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getRemoveStopWords() {
    return removeStopWords;
  }

  @JsonProperty(JSON_PROPERTY_REMOVE_STOP_WORDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRemoveStopWords(@javax.annotation.Nullable Boolean removeStopWords) {
    this.removeStopWords = removeStopWords;
  }

  public CreateTableIndexRequest asciiFolding(@javax.annotation.Nullable Boolean asciiFolding) {

    this.asciiFolding = asciiFolding;
    return this;
  }

  /**
   * Optional FTS parameter for ASCII folding
   *
   * @return asciiFolding
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ASCII_FOLDING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getAsciiFolding() {
    return asciiFolding;
  }

  @JsonProperty(JSON_PROPERTY_ASCII_FOLDING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAsciiFolding(@javax.annotation.Nullable Boolean asciiFolding) {
    this.asciiFolding = asciiFolding;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableIndexRequest createTableIndexRequest = (CreateTableIndexRequest) o;
    return Objects.equals(this.identity, createTableIndexRequest.identity)
        && Objects.equals(this.id, createTableIndexRequest.id)
        && Objects.equals(this.column, createTableIndexRequest.column)
        && Objects.equals(this.indexType, createTableIndexRequest.indexType)
        && Objects.equals(this.name, createTableIndexRequest.name)
        && Objects.equals(this.distanceType, createTableIndexRequest.distanceType)
        && Objects.equals(this.withPosition, createTableIndexRequest.withPosition)
        && Objects.equals(this.baseTokenizer, createTableIndexRequest.baseTokenizer)
        && Objects.equals(this.language, createTableIndexRequest.language)
        && Objects.equals(this.maxTokenLength, createTableIndexRequest.maxTokenLength)
        && Objects.equals(this.lowerCase, createTableIndexRequest.lowerCase)
        && Objects.equals(this.stem, createTableIndexRequest.stem)
        && Objects.equals(this.removeStopWords, createTableIndexRequest.removeStopWords)
        && Objects.equals(this.asciiFolding, createTableIndexRequest.asciiFolding);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity,
        id,
        column,
        indexType,
        name,
        distanceType,
        withPosition,
        baseTokenizer,
        language,
        maxTokenLength,
        lowerCase,
        stem,
        removeStopWords,
        asciiFolding);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableIndexRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    withPosition: ").append(toIndentedString(withPosition)).append("\n");
    sb.append("    baseTokenizer: ").append(toIndentedString(baseTokenizer)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    maxTokenLength: ").append(toIndentedString(maxTokenLength)).append("\n");
    sb.append("    lowerCase: ").append(toIndentedString(lowerCase)).append("\n");
    sb.append("    stem: ").append(toIndentedString(stem)).append("\n");
    sb.append("    removeStopWords: ").append(toIndentedString(removeStopWords)).append("\n");
    sb.append("    asciiFolding: ").append(toIndentedString(asciiFolding)).append("\n");
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

    // add `column` to the URL query string
    if (getColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%scolumn%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getColumn()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `index_type` to the URL query string
    if (getIndexType() != null) {
      try {
        joiner.add(
            String.format(
                "%sindex_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIndexType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `name` to the URL query string
    if (getName() != null) {
      try {
        joiner.add(
            String.format(
                "%sname%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `distance_type` to the URL query string
    if (getDistanceType() != null) {
      try {
        joiner.add(
            String.format(
                "%sdistance_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDistanceType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `with_position` to the URL query string
    if (getWithPosition() != null) {
      try {
        joiner.add(
            String.format(
                "%swith_position%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWithPosition()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `base_tokenizer` to the URL query string
    if (getBaseTokenizer() != null) {
      try {
        joiner.add(
            String.format(
                "%sbase_tokenizer%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getBaseTokenizer()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `language` to the URL query string
    if (getLanguage() != null) {
      try {
        joiner.add(
            String.format(
                "%slanguage%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLanguage()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `max_token_length` to the URL query string
    if (getMaxTokenLength() != null) {
      try {
        joiner.add(
            String.format(
                "%smax_token_length%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMaxTokenLength()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `lower_case` to the URL query string
    if (getLowerCase() != null) {
      try {
        joiner.add(
            String.format(
                "%slower_case%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLowerCase()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `stem` to the URL query string
    if (getStem() != null) {
      try {
        joiner.add(
            String.format(
                "%sstem%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getStem()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `remove_stop_words` to the URL query string
    if (getRemoveStopWords() != null) {
      try {
        joiner.add(
            String.format(
                "%sremove_stop_words%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getRemoveStopWords()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `ascii_folding` to the URL query string
    if (getAsciiFolding() != null) {
      try {
        joiner.add(
            String.format(
                "%sascii_folding%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getAsciiFolding()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}

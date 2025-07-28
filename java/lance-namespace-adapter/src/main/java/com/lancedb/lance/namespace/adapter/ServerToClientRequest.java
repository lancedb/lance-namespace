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
package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.AlterTransactionAction;
import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionSetProperty;
import com.lancedb.lance.namespace.model.AlterTransactionSetStatus;
import com.lancedb.lance.namespace.model.AlterTransactionUnsetProperty;
import com.lancedb.lance.namespace.model.CountTableRowsRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.InsertIntoTableRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.SetPropertyMode;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.TransactionStatus;
import com.lancedb.lance.namespace.model.UnsetPropertyMode;
import com.lancedb.lance.namespace.model.UpdateTableRequest;

import java.util.Optional;

public class ServerToClientRequest {

  public static CreateNamespaceRequest createNamespace(
      com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest request) {
    CreateNamespaceRequest converted = new CreateNamespaceRequest();
    converted.setMode(CreateNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setId(request.getId());
    converted.setProperties(request.getProperties());
    return converted;
  }

  public static DropNamespaceRequest dropNamespace(
      com.lancedb.lance.namespace.server.springboot.model.DropNamespaceRequest request) {
    DropNamespaceRequest converted = new DropNamespaceRequest();
    converted.setId(request.getId());
    converted.setMode(DropNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setBehavior(DropNamespaceRequest.BehaviorEnum.valueOf(request.getBehavior().name()));
    return converted;
  }

  public static DescribeNamespaceRequest describeNamespace(
      com.lancedb.lance.namespace.server.springboot.model.DescribeNamespaceRequest request) {
    DescribeNamespaceRequest converted = new DescribeNamespaceRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static ListNamespacesRequest listNamespaces(
      String id, Optional<String> delimiter, Optional<String> pageToken, Optional<Integer> limit) {
    ListNamespacesRequest converted = new ListNamespacesRequest();
    converted.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).idListStyle());
    converted.setPageToken(pageToken.orElse(null));
    converted.setLimit(limit.orElse(null));
    return converted;
  }

  public static ListTablesRequest listTables(
      String id, Optional<String> delimiter, Optional<String> pageToken, Optional<Integer> limit) {
    ListTablesRequest converted = new ListTablesRequest();
    converted.setId(ObjectIdentifier.of(id, delimiter.orElse(null)).idListStyle());
    converted.setPageToken(pageToken.orElse(null));
    converted.setLimit(limit.orElse(null));
    return converted;
  }

  public static NamespaceExistsRequest namespaceExists(
      com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsRequest request) {
    NamespaceExistsRequest converted = new NamespaceExistsRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static DescribeTableRequest describeTable(
      com.lancedb.lance.namespace.server.springboot.model.DescribeTableRequest request) {
    DescribeTableRequest converted = new DescribeTableRequest();
    converted.setId(request.getId());
    converted.setVersion(request.getVersion());
    return converted;
  }

  public static RegisterTableRequest registerTable(
      com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest request) {
    RegisterTableRequest converted = new RegisterTableRequest();
    converted.setId(request.getId());
    converted.setLocation(request.getLocation());
    converted.setProperties(request.getProperties());
    return converted;
  }

  public static TableExistsRequest tableExists(
      com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest request) {
    TableExistsRequest converted = new TableExistsRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static DropTableRequest dropTable(
      com.lancedb.lance.namespace.server.springboot.model.DropTableRequest request) {
    DropTableRequest converted = new DropTableRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static DeregisterTableRequest deregisterTable(
      com.lancedb.lance.namespace.server.springboot.model.DeregisterTableRequest request) {
    DeregisterTableRequest converted = new DeregisterTableRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static DescribeTransactionRequest describeTransaction(
      com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionRequest request) {
    DescribeTransactionRequest converted = new DescribeTransactionRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static AlterTransactionRequest alterTransaction(
      com.lancedb.lance.namespace.server.springboot.model.AlterTransactionRequest request) {
    AlterTransactionRequest converted = new AlterTransactionRequest();
    converted.setId(request.getId());
    converted.setActions(
        request.getActions().stream()
            .map(ServerToClientRequest::transactionAction)
            .collect(java.util.stream.Collectors.toList()));
    return converted;
  }

  public static AlterTransactionAction transactionAction(
      com.lancedb.lance.namespace.server.springboot.model.AlterTransactionAction action) {

    AlterTransactionAction converted = new AlterTransactionAction();

    if (action.getSetStatusAction() != null) {
      AlterTransactionSetStatus setStatus = new AlterTransactionSetStatus();
      setStatus.setStatus(
          TransactionStatus.valueOf(action.getSetStatusAction().getStatus().name()));
      converted.setSetStatusAction(setStatus);
      return converted;
    }

    if (action.getSetPropertyAction() != null) {
      AlterTransactionSetProperty setProperty = new AlterTransactionSetProperty();
      setProperty.setKey(action.getSetPropertyAction().getKey());
      setProperty.setValue(action.getSetPropertyAction().getValue());
      setProperty.setMode(SetPropertyMode.valueOf(action.getSetPropertyAction().getMode().name()));
      converted.setSetPropertyAction(setProperty);
      return converted;
    }

    if (action.getUnsetPropertyAction() != null) {
      AlterTransactionUnsetProperty unsetProperty = new AlterTransactionUnsetProperty();
      unsetProperty.setKey(action.getUnsetPropertyAction().getKey());
      unsetProperty.setMode(
          UnsetPropertyMode.valueOf(action.getUnsetPropertyAction().getMode().name()));
      converted.setUnsetPropertyAction(unsetProperty);
      return converted;
    }

    throw new IllegalArgumentException("Unexpected action: " + action);
  }

  public static CountTableRowsRequest countTableRows(
      com.lancedb.lance.namespace.server.springboot.model.CountTableRowsRequest request) {
    CountTableRowsRequest converted = new CountTableRowsRequest();
    converted.setId(request.getId());
    converted.setVersion(request.getVersion());
    converted.setFilter(request.getFilter());
    return converted;
  }

  public static CreateTableRequest createTable(
      com.lancedb.lance.namespace.server.springboot.model.CreateTableRequest request) {
    CreateTableRequest converted = new CreateTableRequest();
    converted.setId(request.getId());
    converted.setLocation(request.getLocation());
    converted.setProperties(request.getProperties());
    return converted;
  }

  public static InsertIntoTableRequest insertIntoTable(
      com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableRequest request) {
    InsertIntoTableRequest converted = new InsertIntoTableRequest();
    converted.setId(request.getId());
    converted.setMode(InsertIntoTableRequest.ModeEnum.valueOf(request.getMode().name()));
    return converted;
  }

  public static MergeInsertIntoTableRequest mergeInsertIntoTable(
      com.lancedb.lance.namespace.server.springboot.model.MergeInsertIntoTableRequest request) {
    MergeInsertIntoTableRequest converted = new MergeInsertIntoTableRequest();
    converted.setId(request.getId());
    converted.setOn(request.getOn());
    converted.setWhenMatchedUpdateAll(request.getWhenMatchedUpdateAll());
    converted.setWhenMatchedUpdateAllFilt(request.getWhenMatchedUpdateAllFilt());
    converted.setWhenNotMatchedInsertAll(request.getWhenNotMatchedInsertAll());
    converted.setWhenNotMatchedBySourceDelete(request.getWhenNotMatchedBySourceDelete());
    converted.setWhenNotMatchedBySourceDeleteFilt(request.getWhenNotMatchedBySourceDeleteFilt());
    return converted;
  }

  public static UpdateTableRequest updateTable(
      com.lancedb.lance.namespace.server.springboot.model.UpdateTableRequest request) {
    UpdateTableRequest converted = new UpdateTableRequest();
    converted.setId(request.getId());
    converted.setPredicate(request.getPredicate());
    converted.setUpdates(request.getUpdates());
    return converted;
  }

  public static DeleteFromTableRequest deleteFromTable(
      com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableRequest request) {
    DeleteFromTableRequest converted = new DeleteFromTableRequest();
    converted.setId(request.getId());
    converted.setPredicate(request.getPredicate());
    return converted;
  }

  public static QueryTableRequest queryTable(
      com.lancedb.lance.namespace.server.springboot.model.QueryTableRequest request) {
    QueryTableRequest converted = new QueryTableRequest();
    converted.setId(request.getId());
    converted.setBypassVectorIndex(request.getBypassVectorIndex());
    converted.setColumns(request.getColumns());
    converted.setDistanceType(request.getDistanceType());
    converted.setEf(request.getEf());
    converted.setFastSearch(request.getFastSearch());
    converted.setFilter(request.getFilter());
    converted.setFullTextQuery(convertFullTextQuery(request.getFullTextQuery()));
    converted.setK(request.getK());
    converted.setLowerBound(request.getLowerBound());
    converted.setNprobes(request.getNprobes());
    converted.setOffset(request.getOffset());
    converted.setPrefilter(request.getPrefilter());
    converted.setRefineFactor(request.getRefineFactor());
    converted.setUpperBound(request.getUpperBound());
    converted.setVector(convertVector(request.getVector()));
    converted.setVectorColumn(request.getVectorColumn());
    converted.setVersion(request.getVersion());
    converted.setWithRowId(request.getWithRowId());
    return converted;
  }

  public static CreateTableIndexRequest createTableIndex(
      com.lancedb.lance.namespace.server.springboot.model.CreateTableIndexRequest request) {
    CreateTableIndexRequest converted = new CreateTableIndexRequest();
    converted.setId(request.getId());
    converted.setColumn(request.getColumn());
    converted.setIndexType(
        CreateTableIndexRequest.IndexTypeEnum.valueOf(request.getIndexType().name()));
    converted.setMetricType(
        request.getMetricType() != null
            ? CreateTableIndexRequest.MetricTypeEnum.valueOf(request.getMetricType().name())
            : null);
    converted.setWithPosition(request.getWithPosition());
    converted.setBaseTokenizer(request.getBaseTokenizer());
    converted.setLanguage(request.getLanguage());
    converted.setMaxTokenLength(request.getMaxTokenLength());
    converted.setLowerCase(request.getLowerCase());
    converted.setStem(request.getStem());
    converted.setRemoveStopWords(request.getRemoveStopWords());
    converted.setAsciiFolding(request.getAsciiFolding());
    return converted;
  }

  public static ListTableIndicesRequest listTableIndices(
      com.lancedb.lance.namespace.server.springboot.model.ListTableIndicesRequest request) {
    ListTableIndicesRequest converted = new ListTableIndicesRequest();
    converted.setId(request.getId());
    converted.setVersion(request.getVersion());
    return converted;
  }

  public static DescribeTableIndexStatsRequest describeTableIndexStats(
      com.lancedb.lance.namespace.server.springboot.model.DescribeTableIndexStatsRequest request) {
    DescribeTableIndexStatsRequest converted = new DescribeTableIndexStatsRequest();
    converted.setId(request.getId());
    converted.setVersion(request.getVersion());
    converted.setIndexName(request.getIndexName());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery
      convertFullTextQuery(
          com.lancedb.lance.namespace.server.springboot.model.QueryTableRequestFullTextQuery
              query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery converted =
        new com.lancedb.lance.namespace.model.QueryTableRequestFullTextQuery();
    converted.setStringQuery(convertStringFtsQuery(query.getStringQuery()));
    converted.setStructuredQuery(convertStructuredFtsQuery(query.getStructuredQuery()));
    return converted;
  }

  private static com.lancedb.lance.namespace.model.StringFtsQuery convertStringFtsQuery(
      com.lancedb.lance.namespace.server.springboot.model.StringFtsQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.StringFtsQuery converted =
        new com.lancedb.lance.namespace.model.StringFtsQuery();
    converted.setQuery(query.getQuery());
    converted.setColumns(query.getColumns());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.StructuredFtsQuery convertStructuredFtsQuery(
      com.lancedb.lance.namespace.server.springboot.model.StructuredFtsQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.StructuredFtsQuery converted =
        new com.lancedb.lance.namespace.model.StructuredFtsQuery();
    converted.setQuery(convertFtsQuery(query.getQuery()));
    return converted;
  }

  private static com.lancedb.lance.namespace.model.QueryTableRequestVector convertVector(
      com.lancedb.lance.namespace.server.springboot.model.QueryTableRequestVector vector) {
    if (vector == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.QueryTableRequestVector converted =
        new com.lancedb.lance.namespace.model.QueryTableRequestVector();
    converted.setSingleVector(vector.getSingleVector());
    converted.setMultiVector(vector.getMultiVector());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.FtsQuery convertFtsQuery(
      com.lancedb.lance.namespace.server.springboot.model.FtsQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.FtsQuery converted =
        new com.lancedb.lance.namespace.model.FtsQuery();

    converted.setMatch(convertMatchQuery(query.getMatch()));
    converted.setPhrase(convertPhraseQuery(query.getPhrase()));
    converted.setBoost(convertBoostQuery(query.getBoost()));
    converted.setMultiMatch(convertMultiMatchQuery(query.getMultiMatch()));
    converted.setBoolean(convertBooleanQuery(query.getBoolean()));

    return converted;
  }

  private static com.lancedb.lance.namespace.model.MatchQuery convertMatchQuery(
      com.lancedb.lance.namespace.server.springboot.model.MatchQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.MatchQuery converted =
        new com.lancedb.lance.namespace.model.MatchQuery();
    converted.setBoost(query.getBoost());
    converted.setColumn(query.getColumn());
    converted.setFuzziness(query.getFuzziness());
    converted.setMaxExpansions(query.getMaxExpansions());
    converted.setOperator(
        query.getOperator() != null
            ? com.lancedb.lance.namespace.model.Operator.valueOf(query.getOperator().name())
            : null);
    converted.setPrefixLength(query.getPrefixLength());
    converted.setTerms(query.getTerms());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.PhraseQuery convertPhraseQuery(
      com.lancedb.lance.namespace.server.springboot.model.PhraseQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.PhraseQuery converted =
        new com.lancedb.lance.namespace.model.PhraseQuery();
    converted.setColumn(query.getColumn());
    converted.setSlop(query.getSlop());
    converted.setTerms(query.getTerms());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.BoostQuery convertBoostQuery(
      com.lancedb.lance.namespace.server.springboot.model.BoostQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.BoostQuery converted =
        new com.lancedb.lance.namespace.model.BoostQuery();
    converted.setPositive(convertFtsQuery(query.getPositive()));
    converted.setNegative(convertFtsQuery(query.getNegative()));
    converted.setNegativeBoost(query.getNegativeBoost());
    return converted;
  }

  private static com.lancedb.lance.namespace.model.MultiMatchQuery convertMultiMatchQuery(
      com.lancedb.lance.namespace.server.springboot.model.MultiMatchQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.MultiMatchQuery converted =
        new com.lancedb.lance.namespace.model.MultiMatchQuery();
    if (query.getMatchQueries() != null) {
      converted.setMatchQueries(
          query.getMatchQueries().stream()
              .map(ServerToClientRequest::convertMatchQuery)
              .collect(java.util.stream.Collectors.toList()));
    }
    return converted;
  }

  private static com.lancedb.lance.namespace.model.BooleanQuery convertBooleanQuery(
      com.lancedb.lance.namespace.server.springboot.model.BooleanQuery query) {
    if (query == null) {
      return null;
    }
    com.lancedb.lance.namespace.model.BooleanQuery converted =
        new com.lancedb.lance.namespace.model.BooleanQuery();

    if (query.getMust() != null) {
      converted.setMust(
          query.getMust().stream()
              .map(ServerToClientRequest::convertFtsQuery)
              .collect(java.util.stream.Collectors.toList()));
    }

    if (query.getMustNot() != null) {
      converted.setMustNot(
          query.getMustNot().stream()
              .map(ServerToClientRequest::convertFtsQuery)
              .collect(java.util.stream.Collectors.toList()));
    }

    if (query.getShould() != null) {
      converted.setShould(
          query.getShould().stream()
              .map(ServerToClientRequest::convertFtsQuery)
              .collect(java.util.stream.Collectors.toList()));
    }

    return converted;
  }
}

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
package com.lancedb.lance.namespace.server.springboot.api;

import com.lancedb.lance.namespace.server.springboot.model.CountRowsRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateIndexRequest;
import com.lancedb.lance.namespace.server.springboot.model.CreateIndexResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableRequestV2;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponseV2;
import com.lancedb.lance.namespace.server.springboot.model.DropTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ErrorResponse;
import com.lancedb.lance.namespace.server.springboot.model.IndexListRequest;
import com.lancedb.lance.namespace.server.springboot.model.IndexListResponse;
import com.lancedb.lance.namespace.server.springboot.model.IndexStatsRequest;
import com.lancedb.lance.namespace.server.springboot.model.IndexStatsResponse;
import com.lancedb.lance.namespace.server.springboot.model.InsertTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTablesRequest;
import com.lancedb.lance.namespace.server.springboot.model.ListTablesResponse;
import com.lancedb.lance.namespace.server.springboot.model.MergeInsertTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.QueryRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsResponse;
import com.lancedb.lance.namespace.server.springboot.model.UpdateTableRequest;
import com.lancedb.lance.namespace.server.springboot.model.UpdateTableResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.Optional;

@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
@Validated
@Tag(name = "Table", description = "the Table API")
public interface TableApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /v1/table/{id}/count_rows : Count rows in a table Count the number of rows in a table.
   * Supports both lance-namespace format (with namespace in body) and LanceDB format (with database
   * in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Result of counting rows in a table (status code 200) or Indicates a bad request error.
   *     It could be caused by an unexpected request body format or other forms of request
   *     validation failure, such as invalid json. Usually serves application/json content, although
   *     in some cases simple text/plain content might be returned by the server&#39;s middleware.
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or A server-side problem that means can not find
   *     the specified resource. (status code 404) or The service is not ready to handle the
   *     request. The client should wait and retry. The service may additionally send a Retry-After
   *     header to indicate when to retry. (status code 503) or A server-side problem that might not
   *     be addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "countRows",
      summary = "Count rows in a table",
      description =
          "Count the number of rows in a table. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of counting rows in a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Long.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/count_rows",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<Long> countRows(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CountRowsRequest", description = "", required = true) @Valid @RequestBody
          CountRowsRequest countRowsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create_index : Create an index on a table Create an index on a table column
   * for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ) and
   * scalar indexes.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createIndexRequest Index creation request (required)
   * @return Index created successfully (status code 200) or Indicates a bad request error. It could
   *     be caused by an unexpected request body format or other forms of request validation
   *     failure, such as invalid json. Usually serves application/json content, although in some
   *     cases simple text/plain content might be returned by the server&#39;s middleware. (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or A server-side problem that means can not find the
   *     specified resource. (status code 404) or The service is not ready to handle the request.
   *     The client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "createIndex",
      summary = "Create an index on a table",
      description =
          "Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ) and scalar indexes. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Index created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateIndexResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create_index",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateIndexResponse> createIndex(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "CreateIndexRequest",
              description = "Index creation request",
              required = true)
          @Valid
          @RequestBody
          CreateIndexRequest createIndexRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create_scalar_index : Create a scalar index on a table Create a scalar
   * index on a table column for faster search operations. Supports scalar indexes (BTREE, BITMAP,
   * LABEL_LIST).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createIndexRequest Scalar index creation request (required)
   * @return Scalar index created successfully (status code 200) or Indicates a bad request error.
   *     It could be caused by an unexpected request body format or other forms of request
   *     validation failure, such as invalid json. Usually serves application/json content, although
   *     in some cases simple text/plain content might be returned by the server&#39;s middleware.
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or A server-side problem that means can not find
   *     the specified resource. (status code 404) or The service is not ready to handle the
   *     request. The client should wait and retry. The service may additionally send a Retry-After
   *     header to indicate when to retry. (status code 503) or A server-side problem that might not
   *     be addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "createScalarIndex",
      summary = "Create a scalar index on a table",
      description =
          "Create a scalar index on a table column for faster search operations. Supports scalar indexes (BTREE, BITMAP, LABEL_LIST). ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Scalar index created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateIndexResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create_scalar_index",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateIndexResponse> createScalarIndex(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "CreateIndexRequest",
              description = "Scalar index creation request",
              required = true)
          @Valid
          @RequestBody
          CreateIndexRequest createIndexRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create : Create a table with the given name Create a new table in the
   * namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format
   * (with database in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @return Table properties result when creating a table (status code 200) or Indicates a bad
   *     request error. It could be caused by an unexpected request body format or other forms of
   *     request validation failure, such as invalid json. Usually serves application/json content,
   *     although in some cases simple text/plain content might be returned by the server&#39;s
   *     middleware. (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or A server-side problem that means can
   *     not find the specified resource. (status code 404) or The service is not ready to handle
   *     the request. The client should wait and retry. The service may additionally send a
   *     Retry-After header to indicate when to retry. (status code 503) or A server-side problem
   *     that might not be addressable from the client side. Used for server 5xx errors without more
   *     specific documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "createTable",
      summary = "Create a table with the given name",
      description =
          "Create a new table in the namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when creating a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create",
      produces = {"application/json"},
      consumes = {"application/x-arrow-ipc"})
  default ResponseEntity<CreateTableResponse> createTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "body", description = "Arrow IPC data", required = true) @Valid @RequestBody
          org.springframework.core.io.Resource body) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/delete : Delete rows from a table Delete rows from a table based on a SQL
   * predicate. Returns the number of rows that were deleted.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @return Delete successful (status code 200) or Indicates a bad request error. It could be
   *     caused by an unexpected request body format or other forms of request validation failure,
   *     such as invalid json. Usually serves application/json content, although in some cases
   *     simple text/plain content might be returned by the server&#39;s middleware. (status code
   *     400) or Unauthorized. The request lacks valid authentication credentials for the operation.
   *     (status code 401) or Forbidden. Authenticated user does not have the necessary permissions.
   *     (status code 403) or A server-side problem that means can not find the specified resource.
   *     (status code 404) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or A server-side problem that might not be addressable from the
   *     client side. Used for server 5xx errors without more specific documentation in individual
   *     routes. (status code 5XX)
   */
  @Operation(
      operationId = "deleteFromTable",
      summary = "Delete rows from a table",
      description =
          "Delete rows from a table based on a SQL predicate. Returns the number of rows that were deleted. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Delete successful",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DeleteFromTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/delete",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DeleteFromTableResponse> deleteFromTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DeleteFromTableRequest", description = "Delete request", required = true)
          @Valid
          @RequestBody
          DeleteFromTableRequest deleteFromTableRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/deregister : Deregister a table from its namespace Deregister a table from
   * its namespace. The table content remains available in the storage.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deregisterTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Response of DeregisterTable (status code 200) or Indicates a bad request error. It
   *     could be caused by an unexpected request body format or other forms of request validation
   *     failure, such as invalid json. Usually serves application/json content, although in some
   *     cases simple text/plain content might be returned by the server&#39;s middleware. (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or A server-side problem that means can not find the
   *     specified resource. (status code 404) or The service is not ready to handle the request.
   *     The client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "deregisterTable",
      summary = "Deregister a table from its namespace",
      description =
          "Deregister a table from its namespace. The table content remains available in the storage. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Response of DeregisterTable",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DeregisterTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/deregister",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DeregisterTableResponse> deregisterTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DeregisterTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          DeregisterTableRequest deregisterTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/describe : Describe a table from the namespace Get a table&#39;s detailed
   * information under a specified namespace. Supports both lance-namespace format (with namespace
   * in body) and LanceDB format (with database in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Table properties result when loading a table (status code 200) or Indicates a bad
   *     request error. It could be caused by an unexpected request body format or other forms of
   *     request validation failure, such as invalid json. Usually serves application/json content,
   *     although in some cases simple text/plain content might be returned by the server&#39;s
   *     middleware. (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or A server-side problem that means can
   *     not find the specified resource. (status code 404) or The service is not ready to handle
   *     the request. The client should wait and retry. The service may additionally send a
   *     Retry-After header to indicate when to retry. (status code 503) or A server-side problem
   *     that might not be addressable from the client side. Used for server 5xx errors without more
   *     specific documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "describeTable",
      summary = "Describe a table from the namespace",
      description =
          "Get a table's detailed information under a specified namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when loading a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DescribeTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/describe",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DescribeTableResponse> describeTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DescribeTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          DescribeTableRequest describeTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"schema\" : { \"metadata\" : { \"key\" : \"metadata\" }, \"fields\" : [ { \"metadata\" : { \"key\" : \"metadata\" }, \"nullable\" : true, \"name\" : \"name\", \"type\" : { \"length\" : 0, \"fields\" : \"{}\", \"type\" : \"type\" } }, { \"metadata\" : { \"key\" : \"metadata\" }, \"nullable\" : true, \"name\" : \"name\", \"type\" : { \"length\" : 0, \"fields\" : \"{}\", \"type\" : \"type\" } } ] }, \"stats\" : { \"num_deleted_rows\" : 0, \"num_fragments\" : 0 }, \"table_uri\" : \"table_uri\", \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"version\" : 0, \"properties\" : { \"key\" : \"properties\" }, \"table\" : \"table\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v2/table/{id}/describe : Describe a table from the namespace Get a table&#39;s detailed
   * information under a specified namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeTableRequestV2 (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Table properties result when loading a table (status code 200) or Indicates a bad
   *     request error. It could be caused by an unexpected request body format or other forms of
   *     request validation failure, such as invalid json. Usually serves application/json content,
   *     although in some cases simple text/plain content might be returned by the server&#39;s
   *     middleware. (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or A server-side problem that means can
   *     not find the specified resource. (status code 404) or The service is not ready to handle
   *     the request. The client should wait and retry. The service may additionally send a
   *     Retry-After header to indicate when to retry. (status code 503) or A server-side problem
   *     that might not be addressable from the client side. Used for server 5xx errors without more
   *     specific documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "describeTableV2",
      summary = "Describe a table from the namespace",
      description = "Get a table's detailed information under a specified namespace. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when loading a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DescribeTableResponseV2.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v2/table/{id}/describe",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DescribeTableResponseV2> describeTableV2(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DescribeTableRequestV2", description = "", required = true)
          @Valid
          @RequestBody
          DescribeTableRequestV2 describeTableRequestV2,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/drop : Drop a table from its namespace Drop a table from its namespace and
   * delete its data. If the table and its data can be immediately deleted, return information of
   * the deleted table. Otherwise, return a transaction ID that client can use to track deletion
   * progress.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Response of DropTable (status code 200) or Indicates a bad request error. It could be
   *     caused by an unexpected request body format or other forms of request validation failure,
   *     such as invalid json. Usually serves application/json content, although in some cases
   *     simple text/plain content might be returned by the server&#39;s middleware. (status code
   *     400) or Unauthorized. The request lacks valid authentication credentials for the operation.
   *     (status code 401) or Forbidden. Authenticated user does not have the necessary permissions.
   *     (status code 403) or A server-side problem that means can not find the specified resource.
   *     (status code 404) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or A server-side problem that might not be addressable from the
   *     client side. Used for server 5xx errors without more specific documentation in individual
   *     routes. (status code 5XX)
   */
  @Operation(
      operationId = "dropTable",
      summary = "Drop a table from its namespace",
      description =
          "Drop a table from its namespace and delete its data. If the table and its data can be immediately deleted, return information of the deleted table. Otherwise, return a transaction ID that client can use to track deletion progress. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Response of DropTable",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DropTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/drop",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DropTableResponse> dropTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DropTableRequest", description = "", required = true) @Valid @RequestBody
          DropTableRequest dropTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" }, \"transactionId\" : \"transactionId\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/index/{index_name}/stats : Get index statistics Get statistics for a
   * specific index on a table. Returns information about the index type, distance type (for vector
   * indices), and row counts.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to get stats for (required)
   * @param indexStatsRequest Index stats request (required)
   * @return Index statistics (status code 200) or Indicates a bad request error. It could be caused
   *     by an unexpected request body format or other forms of request validation failure, such as
   *     invalid json. Usually serves application/json content, although in some cases simple
   *     text/plain content might be returned by the server&#39;s middleware. (status code 400) or
   *     Unauthorized. The request lacks valid authentication credentials for the operation. (status
   *     code 401) or Forbidden. Authenticated user does not have the necessary permissions. (status
   *     code 403) or A server-side problem that means can not find the specified resource. (status
   *     code 404) or The service is not ready to handle the request. The client should wait and
   *     retry. The service may additionally send a Retry-After header to indicate when to retry.
   *     (status code 503) or A server-side problem that might not be addressable from the client
   *     side. Used for server 5xx errors without more specific documentation in individual routes.
   *     (status code 5XX)
   */
  @Operation(
      operationId = "getIndexStats",
      summary = "Get index statistics",
      description =
          "Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Index statistics",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = IndexStatsResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/index/{index_name}/stats",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<IndexStatsResponse> getIndexStats(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "index_name",
              description = "Name of the index to get stats for",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("index_name")
          String indexName,
      @Parameter(name = "IndexStatsRequest", description = "Index stats request", required = true)
          @Valid
          @RequestBody
          IndexStatsRequest indexStatsRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"distance_type\" : \"distance_type\", \"num_unindexed_rows\" : 0, \"num_indexed_rows\" : 0, \"index_type\" : \"index_type\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/insert : Insert records into a table Insert new records into an existing
   * table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and
   * LanceDB format (with database in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param mode Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; (optional,
   *     default to append)
   * @return Result of inserting records into a table (status code 200) or Indicates a bad request
   *     error. It could be caused by an unexpected request body format or other forms of request
   *     validation failure, such as invalid json. Usually serves application/json content, although
   *     in some cases simple text/plain content might be returned by the server&#39;s middleware.
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or A server-side problem that means can not find
   *     the specified resource. (status code 404) or The service is not ready to handle the
   *     request. The client should wait and retry. The service may additionally send a Retry-After
   *     header to indicate when to retry. (status code 503) or A server-side problem that might not
   *     be addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "insertTable",
      summary = "Insert records into a table",
      description =
          "Insert new records into an existing table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of inserting records into a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InsertTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/insert",
      produces = {"application/json"},
      consumes = {"application/x-arrow-ipc"})
  default ResponseEntity<InsertTableResponse> insertTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "body", description = "Arrow IPC data", required = true) @Valid @RequestBody
          org.springframework.core.io.Resource body,
      @Parameter(
              name = "mode",
              description = "Insert mode: \"append\" (default) or \"overwrite\"",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "mode", required = false, defaultValue = "append")
          Optional<String> mode) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/index/list : List indices on a table List all indices created on a table.
   * Returns information about each index including name, columns, status, and UUID.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexListRequest Index list request (required)
   * @return List of indices on the table (status code 200) or Indicates a bad request error. It
   *     could be caused by an unexpected request body format or other forms of request validation
   *     failure, such as invalid json. Usually serves application/json content, although in some
   *     cases simple text/plain content might be returned by the server&#39;s middleware. (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or A server-side problem that means can not find the
   *     specified resource. (status code 404) or The service is not ready to handle the request.
   *     The client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "listIndices",
      summary = "List indices on a table",
      description =
          "List all indices created on a table. Returns information about each index including name, columns, status, and UUID. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "List of indices on the table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = IndexListResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/index/list",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<IndexListResponse> listIndices(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "IndexListRequest", description = "Index list request", required = true)
          @Valid
          @RequestBody
          IndexListRequest indexListRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"indexes\" : [ { \"index_uuid\" : \"index_uuid\", \"columns\" : [ \"columns\", \"columns\" ], \"index_name\" : \"index_name\", \"status\" : \"status\" }, { \"index_uuid\" : \"index_uuid\", \"columns\" : [ \"columns\", \"columns\" ], \"index_name\" : \"index_name\", \"status\" : \"status\" } ], \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/list : List tables List all child table names of the root namespace or a
   * given parent namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTablesRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return A list of tables (status code 200) or Indicates a bad request error. It could be caused
   *     by an unexpected request body format or other forms of request validation failure, such as
   *     invalid json. Usually serves application/json content, although in some cases simple
   *     text/plain content might be returned by the server&#39;s middleware. (status code 400) or
   *     Unauthorized. The request lacks valid authentication credentials for the operation. (status
   *     code 401) or Forbidden. Authenticated user does not have the necessary permissions. (status
   *     code 403) or Not Acceptable / Unsupported Operation. The server does not support this
   *     operation. (status code 406) or The service is not ready to handle the request. The client
   *     should wait and retry. The service may additionally send a Retry-After header to indicate
   *     when to retry. (status code 503) or A server-side problem that might not be addressable
   *     from the client side. Used for server 5xx errors without more specific documentation in
   *     individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "listTables",
      summary = "List tables",
      description =
          "List all child table names of the root namespace or a given parent namespace. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "A list of tables",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ListTablesResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "406",
            description =
                "Not Acceptable / Unsupported Operation. The server does not support this operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/list",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<ListTablesResponse> listTables(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "ListTablesRequest", description = "", required = true) @Valid @RequestBody
          ListTablesRequest listTablesRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"tables\" : [ \"cart\", \"cart\" ], \"nextPageToken\" : \"nextPageToken\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/merge_insert : Merge insert (upsert) records into a table Performs a merge
   * insert (upsert) operation on a table. This operation updates existing rows based on a matching
   * column and inserts new rows that don&#39;t match. Returns the number of rows inserted and
   * updated.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC data containing the records to merge (required)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @return Result of merge insert operation (status code 200) or Indicates a bad request error. It
   *     could be caused by an unexpected request body format or other forms of request validation
   *     failure, such as invalid json. Usually serves application/json content, although in some
   *     cases simple text/plain content might be returned by the server&#39;s middleware. (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or A server-side problem that means can not find the
   *     specified resource. (status code 404) or The service is not ready to handle the request.
   *     The client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "mergeInsertTable",
      summary = "Merge insert (upsert) records into a table",
      description =
          "Performs a merge insert (upsert) operation on a table. This operation updates existing rows based on a matching column and inserts new rows that don't match. Returns the number of rows inserted and updated. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of merge insert operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = MergeInsertTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/merge_insert",
      produces = {"application/json"},
      consumes = {"application/x-arrow-ipc"})
  default ResponseEntity<MergeInsertTableResponse> mergeInsertTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @NotNull
          @Parameter(
              name = "on",
              description = "Column name to use for matching rows (required)",
              required = true,
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "on", required = true)
          String on,
      @Parameter(
              name = "body",
              description = "Arrow IPC data containing the records to merge",
              required = true)
          @Valid
          @RequestBody
          org.springframework.core.io.Resource body,
      @Parameter(
              name = "when_matched_update_all",
              description = "Update all columns when rows match",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "when_matched_update_all", required = false, defaultValue = "false")
          Optional<Boolean> whenMatchedUpdateAll,
      @Parameter(
              name = "when_not_matched_insert_all",
              description = "Insert all columns when rows don't match",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(
              value = "when_not_matched_insert_all",
              required = false,
              defaultValue = "false")
          Optional<Boolean> whenNotMatchedInsertAll) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"num_inserted_rows\" : 0, \"num_updated_rows\" : 0, \"num_deleted_rows\" : 0, \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/query : Query a table Query a table with vector search and optional
   * filtering. Returns results in Arrow IPC stream format.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryRequest Query request (required)
   * @return Query results in Arrow IPC stream format (status code 200) or Indicates a bad request
   *     error. It could be caused by an unexpected request body format or other forms of request
   *     validation failure, such as invalid json. Usually serves application/json content, although
   *     in some cases simple text/plain content might be returned by the server&#39;s middleware.
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or A server-side problem that means can not find
   *     the specified resource. (status code 404) or The service is not ready to handle the
   *     request. The client should wait and retry. The service may additionally send a Retry-After
   *     header to indicate when to retry. (status code 503) or A server-side problem that might not
   *     be addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "queryTable",
      summary = "Query a table",
      description =
          "Query a table with vector search and optional filtering. Returns results in Arrow IPC stream format. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Query results in Arrow IPC stream format",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = org.springframework.core.io.Resource.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = org.springframework.core.io.Resource.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.stream",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/query",
      produces = {"application/vnd.apache.arrow.stream", "application/json"},
      consumes = {"application/json"})
  default ResponseEntity<org.springframework.core.io.Resource> queryTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "QueryRequest", description = "Query request", required = true)
          @Valid
          @RequestBody
          QueryRequest queryRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/register : Register a table to a namespace Register an existing table at a
   * given storage location to a namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param registerTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Table properties result when registering a table (status code 200) or Indicates a bad
   *     request error. It could be caused by an unexpected request body format or other forms of
   *     request validation failure, such as invalid json. Usually serves application/json content,
   *     although in some cases simple text/plain content might be returned by the server&#39;s
   *     middleware. (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or Not Acceptable / Unsupported
   *     Operation. The server does not support this operation. (status code 406) or The request
   *     conflicts with the current state of the target resource. (status code 409) or The service
   *     is not ready to handle the request. The client should wait and retry. The service may
   *     additionally send a Retry-After header to indicate when to retry. (status code 503) or A
   *     server-side problem that might not be addressable from the client side. Used for server 5xx
   *     errors without more specific documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "registerTable",
      summary = "Register a table to a namespace",
      description = "Register an existing table at a given storage location to a namespace. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when registering a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RegisterTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "406",
            description =
                "Not Acceptable / Unsupported Operation. The server does not support this operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "The request conflicts with the current state of the target resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/register",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<RegisterTableResponse> registerTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "RegisterTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          RegisterTableRequest registerTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/exists : Check if a table exists Check if a table exists. This API should
   * behave exactly like the GetTable API, except it does not contain a body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param tableExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return Result of checking if a table exists (status code 200) or Indicates a bad request
   *     error. It could be caused by an unexpected request body format or other forms of request
   *     validation failure, such as invalid json. Usually serves application/json content, although
   *     in some cases simple text/plain content might be returned by the server&#39;s middleware.
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or A server-side problem that means can not find
   *     the specified resource. (status code 404) or The service is not ready to handle the
   *     request. The client should wait and retry. The service may additionally send a Retry-After
   *     header to indicate when to retry. (status code 503) or A server-side problem that might not
   *     be addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "tableExists",
      summary = "Check if a table exists",
      description =
          "Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of checking if a table exists",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TableExistsResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/exists",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<TableExistsResponse> tableExists(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "TableExistsRequest", description = "", required = true) @Valid @RequestBody
          TableExistsRequest tableExistsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"exists\" : true }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/update : Update rows in a table Update existing rows in a table using SQL
   * expressions. Each update consists of a column name and an SQL expression that will be evaluated
   * against the current row&#39;s value. Optionally, a predicate can be provided to filter which
   * rows to update.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @return Update successful (status code 200) or Indicates a bad request error. It could be
   *     caused by an unexpected request body format or other forms of request validation failure,
   *     such as invalid json. Usually serves application/json content, although in some cases
   *     simple text/plain content might be returned by the server&#39;s middleware. (status code
   *     400) or Unauthorized. The request lacks valid authentication credentials for the operation.
   *     (status code 401) or Forbidden. Authenticated user does not have the necessary permissions.
   *     (status code 403) or A server-side problem that means can not find the specified resource.
   *     (status code 404) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or A server-side problem that might not be addressable from the
   *     client side. Used for server 5xx errors without more specific documentation in individual
   *     routes. (status code 5XX)
   */
  @Operation(
      operationId = "updateTable",
      summary = "Update rows in a table",
      description =
          "Update existing rows in a table using SQL expressions. Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Update successful",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateTableResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/update",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<UpdateTableResponse> updateTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "UpdateTableRequest", description = "Update request", required = true)
          @Valid
          @RequestBody
          UpdateTableRequest updateTableRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"updated_rows\" : 0, \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\", \"title\" : \"Incorrect username or password\", \"status\" : 404 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}

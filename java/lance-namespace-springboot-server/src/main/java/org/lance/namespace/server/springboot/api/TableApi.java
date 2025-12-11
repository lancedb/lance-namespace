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
package org.lance.namespace.server.springboot.api;

import org.lance.namespace.server.springboot.model.AlterTableAddColumnsRequest;
import org.lance.namespace.server.springboot.model.AlterTableAddColumnsResponse;
import org.lance.namespace.server.springboot.model.AlterTableAlterColumnsRequest;
import org.lance.namespace.server.springboot.model.AlterTableAlterColumnsResponse;
import org.lance.namespace.server.springboot.model.AlterTableDropColumnsRequest;
import org.lance.namespace.server.springboot.model.AlterTableDropColumnsResponse;
import org.lance.namespace.server.springboot.model.AnalyzeTableQueryPlanRequest;
import org.lance.namespace.server.springboot.model.CountTableRowsRequest;
import org.lance.namespace.server.springboot.model.CreateEmptyTableRequest;
import org.lance.namespace.server.springboot.model.CreateEmptyTableResponse;
import org.lance.namespace.server.springboot.model.CreateTableIndexRequest;
import org.lance.namespace.server.springboot.model.CreateTableIndexResponse;
import org.lance.namespace.server.springboot.model.CreateTableResponse;
import org.lance.namespace.server.springboot.model.CreateTableScalarIndexResponse;
import org.lance.namespace.server.springboot.model.CreateTableTagRequest;
import org.lance.namespace.server.springboot.model.CreateTableTagResponse;
import org.lance.namespace.server.springboot.model.DeleteFromTableRequest;
import org.lance.namespace.server.springboot.model.DeleteFromTableResponse;
import org.lance.namespace.server.springboot.model.DeleteTableTagRequest;
import org.lance.namespace.server.springboot.model.DeleteTableTagResponse;
import org.lance.namespace.server.springboot.model.DeregisterTableRequest;
import org.lance.namespace.server.springboot.model.DeregisterTableResponse;
import org.lance.namespace.server.springboot.model.DescribeTableIndexStatsRequest;
import org.lance.namespace.server.springboot.model.DescribeTableIndexStatsResponse;
import org.lance.namespace.server.springboot.model.DescribeTableRequest;
import org.lance.namespace.server.springboot.model.DescribeTableResponse;
import org.lance.namespace.server.springboot.model.DropTableIndexResponse;
import org.lance.namespace.server.springboot.model.DropTableResponse;
import org.lance.namespace.server.springboot.model.ErrorResponse;
import org.lance.namespace.server.springboot.model.ExplainTableQueryPlanRequest;
import org.lance.namespace.server.springboot.model.GetTableStatsRequest;
import org.lance.namespace.server.springboot.model.GetTableStatsResponse;
import org.lance.namespace.server.springboot.model.GetTableTagVersionRequest;
import org.lance.namespace.server.springboot.model.GetTableTagVersionResponse;
import org.lance.namespace.server.springboot.model.InsertIntoTableResponse;
import org.lance.namespace.server.springboot.model.ListTableIndicesRequest;
import org.lance.namespace.server.springboot.model.ListTableIndicesResponse;
import org.lance.namespace.server.springboot.model.ListTableTagsResponse;
import org.lance.namespace.server.springboot.model.ListTableVersionsResponse;
import org.lance.namespace.server.springboot.model.ListTablesResponse;
import org.lance.namespace.server.springboot.model.MergeInsertIntoTableResponse;
import org.lance.namespace.server.springboot.model.QueryTableRequest;
import org.lance.namespace.server.springboot.model.RegisterTableRequest;
import org.lance.namespace.server.springboot.model.RegisterTableResponse;
import org.lance.namespace.server.springboot.model.RenameTableRequest;
import org.lance.namespace.server.springboot.model.RenameTableResponse;
import org.lance.namespace.server.springboot.model.RestoreTableRequest;
import org.lance.namespace.server.springboot.model.RestoreTableResponse;
import org.lance.namespace.server.springboot.model.TableExistsRequest;
import org.lance.namespace.server.springboot.model.UpdateTableRequest;
import org.lance.namespace.server.springboot.model.UpdateTableResponse;
import org.lance.namespace.server.springboot.model.UpdateTableTagRequest;
import org.lance.namespace.server.springboot.model.UpdateTableTagResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Map;
import java.util.Optional;

@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
@Validated
@Tag(name = "Table", description = "Operations that are related to a table ")
public interface TableApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /v1/table/{id}/add_columns : Add new columns to table schema Add new columns to table
   * &#x60;id&#x60; using SQL expressions or default values.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAddColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Add columns operation result (status code 200) or Indicates a bad request error. It
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
      operationId = "alterTableAddColumns",
      summary = "Add new columns to table schema",
      description = "Add new columns to table `id` using SQL expressions or default values. ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Add columns operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AlterTableAddColumnsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/add_columns",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<AlterTableAddColumnsResponse> alterTableAddColumns(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "AlterTableAddColumnsRequest", description = "", required = true)
          @Valid
          @RequestBody
          AlterTableAddColumnsRequest alterTableAddColumnsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/alter_columns : Modify existing columns Modify existing columns in table
   * &#x60;id&#x60;, such as renaming or changing data types.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAlterColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Alter columns operation result (status code 200) or Indicates a bad request error. It
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
      operationId = "alterTableAlterColumns",
      summary = "Modify existing columns",
      description =
          "Modify existing columns in table `id`, such as renaming or changing data types. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Alter columns operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AlterTableAlterColumnsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/alter_columns",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<AlterTableAlterColumnsResponse> alterTableAlterColumns(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "AlterTableAlterColumnsRequest", description = "", required = true)
          @Valid
          @RequestBody
          AlterTableAlterColumnsRequest alterTableAlterColumnsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/drop_columns : Remove columns from table Remove specified columns from
   * table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableDropColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Drop columns operation result (status code 200) or Indicates a bad request error. It
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
      operationId = "alterTableDropColumns",
      summary = "Remove columns from table",
      description = "Remove specified columns from table `id`. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Drop columns operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = AlterTableDropColumnsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/drop_columns",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<AlterTableDropColumnsResponse> alterTableDropColumns(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "AlterTableDropColumnsRequest", description = "", required = true)
          @Valid
          @RequestBody
          AlterTableDropColumnsRequest alterTableDropColumnsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/analyze_plan : Analyze query execution plan Analyze the query execution
   * plan for a query against table &#x60;id&#x60;. Returns detailed statistics and analysis of the
   * query execution plan. REST NAMESPACE ONLY REST namespace returns the response as a plain string
   * instead of the &#x60;AnalyzeTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param analyzeTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Query execution plan analysis (status code 200) or Indicates a bad request error. It
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
      operationId = "analyzeTableQueryPlan",
      summary = "Analyze query execution plan",
      description =
          "Analyze the query execution plan for a query against table `id`. Returns detailed statistics and analysis of the query execution plan.  REST NAMESPACE ONLY REST namespace returns the response as a plain string instead of the `AnalyzeTableQueryPlanResponse` JSON object. ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Query execution plan analysis",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/analyze_plan",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<String> analyzeTableQueryPlan(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "AnalyzeTableQueryPlanRequest", description = "", required = true)
          @Valid
          @RequestBody
          AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/count_rows : Count rows in a table Count the number of rows in table
   * &#x60;id&#x60; REST NAMESPACE ONLY REST namespace returns the response as a plain integer
   * instead of the &#x60;CountTableRowsResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countTableRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
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
      operationId = "countTableRows",
      summary = "Count rows in a table",
      description =
          "Count the number of rows in table `id`  REST NAMESPACE ONLY REST namespace returns the response as a plain integer instead of the `CountTableRowsResponse` JSON object. ",
      tags = {"Table", "Data"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/count_rows",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<Long> countTableRows(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CountTableRowsRequest", description = "", required = true)
          @Valid
          @RequestBody
          CountTableRowsRequest countTableRowsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create-empty : Create an empty table Create an empty table with the given
   * name without touching storage. This is a metadata-only operation that records the table
   * existence and sets up aspects like access control. For DirectoryNamespace implementation, this
   * creates a &#x60;.lance-reserved&#x60; file in the table directory to mark the table&#39;s
   * existence without creating actual Lance data files.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createEmptyTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Table properties result when creating an empty table (status code 200) or Indicates a
   *     bad request error. It could be caused by an unexpected request body format or other forms
   *     of request validation failure, such as invalid json. Usually serves application/json
   *     content, although in some cases simple text/plain content might be returned by the
   *     server&#39;s middleware. (status code 400) or Unauthorized. The request lacks valid
   *     authentication credentials for the operation. (status code 401) or Forbidden. Authenticated
   *     user does not have the necessary permissions. (status code 403) or A server-side problem
   *     that means can not find the specified resource. (status code 404) or The request conflicts
   *     with the current state of the target resource. (status code 409) or The service is not
   *     ready to handle the request. The client should wait and retry. The service may additionally
   *     send a Retry-After header to indicate when to retry. (status code 503) or A server-side
   *     problem that might not be addressable from the client side. Used for server 5xx errors
   *     without more specific documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "createEmptyTable",
      summary = "Create an empty table",
      description =
          "Create an empty table with the given name without touching storage. This is a metadata-only operation that records the table existence and sets up aspects like access control.  For DirectoryNamespace implementation, this creates a `.lance-reserved` file in the table directory to mark the table's existence without creating actual Lance data files. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when creating an empty table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateEmptyTableResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create-empty",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateEmptyTableResponse> createEmptyTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CreateEmptyTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          CreateEmptyTableRequest createEmptyTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" }, \"storage_options\" : { \"key\" : \"storage_options\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create : Create a table with the given name Create table &#x60;id&#x60; in
   * the namespace with the given data in Arrow IPC stream. The schema of the Arrow IPC stream is
   * used as the table schema. If the stream is empty, the API creates a new empty table. REST
   * NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the
   * &#x60;CreateTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through
   * path parameter of the same name - &#x60;mode&#x60;: pass through query parameter of the same
   * name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode (optional)
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
          "Create table `id` in the namespace with the given data in Arrow IPC stream.  The schema of the Arrow IPC stream is used as the table schema. If the stream is empty, the API creates a new empty table.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `CreateTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `mode`: pass through query parameter of the same name ",
      tags = {"Table", "Data"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create",
      produces = {"application/json"},
      consumes = {"application/vnd.apache.arrow.stream"})
  default ResponseEntity<CreateTableResponse> createTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "body", description = "Arrow IPC data", required = true) @Valid @RequestBody
          org.springframework.core.io.Resource body,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(name = "mode", description = "", in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "mode", required = false)
          Optional<String> mode) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"transaction_id\" : \"transaction_id\", \"location\" : \"location\", \"version\" : 0, \"storage_options\" : { \"key\" : \"storage_options\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create_index : Create an index on a table Create an index on a table column
   * for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and
   * scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously. Use the
   * &#x60;ListTableIndices&#x60; and &#x60;DescribeTableIndexStats&#x60; operations to monitor
   * index creation progress.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableIndexRequest Index creation request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      operationId = "createTableIndex",
      summary = "Create an index on a table",
      description =
          "Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.  Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress. ",
      tags = {"Table", "Index", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Index created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateTableIndexResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create_index",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateTableIndexResponse> createTableIndex(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "CreateTableIndexRequest",
              description = "Index creation request",
              required = true)
          @Valid
          @RequestBody
          CreateTableIndexRequest createTableIndexRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/create_scalar_index : Create a scalar index on a table Create a scalar
   * index on a table column for faster filtering operations. Supports scalar indexes (BTREE,
   * BITMAP, LABEL_LIST, FTS, etc.). This is an alias for CreateTableIndex specifically for scalar
   * indexes. Index creation is handled asynchronously. Use the &#x60;ListTableIndices&#x60; and
   * &#x60;DescribeTableIndexStats&#x60; operations to monitor index creation progress.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableIndexRequest Scalar index creation request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      operationId = "createTableScalarIndex",
      summary = "Create a scalar index on a table",
      description =
          "Create a scalar index on a table column for faster filtering operations. Supports scalar indexes (BTREE, BITMAP, LABEL_LIST, FTS, etc.). This is an alias for CreateTableIndex specifically for scalar indexes. Index creation is handled asynchronously. Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress. ",
      tags = {"Table", "Index", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Scalar index created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateTableScalarIndexResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/create_scalar_index",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateTableScalarIndexResponse> createTableScalarIndex(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "CreateTableIndexRequest",
              description = "Scalar index creation request",
              required = true)
          @Valid
          @RequestBody
          CreateTableIndexRequest createTableIndexRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/tags/create : Create a new tag Create a new tag for table &#x60;id&#x60;
   * that points to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Create tag response (status code 200) or Indicates a bad request error. It could be
   *     caused by an unexpected request body format or other forms of request validation failure,
   *     such as invalid json. Usually serves application/json content, although in some cases
   *     simple text/plain content might be returned by the server&#39;s middleware. (status code
   *     400) or Unauthorized. The request lacks valid authentication credentials for the operation.
   *     (status code 401) or Forbidden. Authenticated user does not have the necessary permissions.
   *     (status code 403) or A server-side problem that means can not find the specified resource.
   *     (status code 404) or The request conflicts with the current state of the target resource.
   *     (status code 409) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or A server-side problem that might not be addressable from the
   *     client side. Used for server 5xx errors without more specific documentation in individual
   *     routes. (status code 5XX)
   */
  @Operation(
      operationId = "createTableTag",
      summary = "Create a new tag",
      description = "Create a new tag for table `id` that points to a specific version. ",
      tags = {"Table", "Tag", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Create tag response",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateTableTagResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/tags/create",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateTableTagResponse> createTableTag(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CreateTableTagRequest", description = "", required = true)
          @Valid
          @RequestBody
          CreateTableTagRequest createTableTagRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/delete : Delete rows from a table Delete rows from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      description = "Delete rows from table `id`. ",
      tags = {"Table", "Data"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
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
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DeleteFromTableRequest", description = "Delete request", required = true)
          @Valid
          @RequestBody
          DeleteFromTableRequest deleteFromTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/tags/delete : Delete a tag Delete an existing tag from table
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Delete tag response (status code 200) or Indicates a bad request error. It could be
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
      operationId = "deleteTableTag",
      summary = "Delete a tag",
      description = "Delete an existing tag from table `id`. ",
      tags = {"Table", "Tag", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Delete tag response",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DeleteTableTagResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/tags/delete",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DeleteTableTagResponse> deleteTableTag(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DeleteTableTagRequest", description = "", required = true)
          @Valid
          @RequestBody
          DeleteTableTagRequest deleteTableTagRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/deregister : Deregister a table Deregister table &#x60;id&#x60; from its
   * namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deregisterTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
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
      summary = "Deregister a table",
      description = "Deregister table `id` from its namespace. ",
      tags = {"Table", "Metadata"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
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
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
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
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"location\" : \"location\", \"id\" : [ \"id\", \"id\" ], \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/describe : Describe information of a table Describe the detailed
   * information for table &#x60;id&#x60;. REST NAMESPACE ONLY REST namespace passes
   * &#x60;with_table_uri&#x60; as a query parameter instead of in the request body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param withTableUri Whether to include the table URI in the response (optional, default to
   *     false)
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
      summary = "Describe information of a table",
      description =
          "Describe the detailed information for table `id`.  REST NAMESPACE ONLY REST namespace passes `with_table_uri` as a query parameter instead of in the request body. ",
      tags = {"Table", "Metadata"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
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
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
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
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "with_table_uri",
              description = "Whether to include the table URI in the response",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "with_table_uri", required = false, defaultValue = "false")
          Optional<Boolean> withTableUri) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"schema\" : { \"metadata\" : { \"key\" : \"metadata\" }, \"fields\" : [ { \"metadata\" : { \"key\" : \"metadata\" }, \"nullable\" : true, \"name\" : \"name\", \"type\" : { \"length\" : 0, \"fields\" : [ null, null ], \"type\" : \"type\" } }, { \"metadata\" : { \"key\" : \"metadata\" }, \"nullable\" : true, \"name\" : \"name\", \"type\" : { \"length\" : 0, \"fields\" : [ null, null ], \"type\" : \"type\" } } ] }, \"table_uri\" : \"table_uri\", \"stats\" : { \"num_deleted_rows\" : 0, \"num_fragments\" : 0 }, \"namespace\" : [ \"namespace\", \"namespace\" ], \"location\" : \"location\", \"version\" : 0, \"table\" : \"table\", \"storage_options\" : { \"key\" : \"storage_options\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/index/{index_name}/stats : Get table index statistics Get statistics for a
   * specific index on a table. Returns information about the index type, distance type (for vector
   * indices), and row counts.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to get stats for (required)
   * @param describeTableIndexStatsRequest Index stats request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      operationId = "describeTableIndexStats",
      summary = "Get table index statistics",
      description =
          "Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. ",
      tags = {"Table", "Index", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Index statistics",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DescribeTableIndexStatsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/index/{index_name}/stats",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DescribeTableIndexStatsResponse> describeTableIndexStats(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
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
      @Parameter(
              name = "DescribeTableIndexStatsRequest",
              description = "Index stats request",
              required = true)
          @Valid
          @RequestBody
          DescribeTableIndexStatsRequest describeTableIndexStatsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"num_indices\" : 0, \"distance_type\" : \"distance_type\", \"num_unindexed_rows\" : 0, \"num_indexed_rows\" : 0, \"index_type\" : \"index_type\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/drop : Drop a table Drop table &#x60;id&#x60; and delete its data. REST
   * NAMESPACE ONLY REST namespace does not use a request body for this operation. The
   * &#x60;DropTableRequest&#x60; information is passed in the following way: - &#x60;id&#x60;: pass
   * through path parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
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
      summary = "Drop a table",
      description =
          "Drop table `id` and delete its data.  REST NAMESPACE ONLY REST namespace does not use a request body for this operation. The `DropTableRequest` information is passed in the following way: - `id`: pass through path parameter of the same name ",
      tags = {"Table", "Metadata"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/drop",
      produces = {"application/json"})
  default ResponseEntity<DropTableResponse> dropTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"location\" : \"location\", \"id\" : [ \"id\", \"id\" ], \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/index/{index_name}/drop : Drop a specific index Drop the specified index
   * from table &#x60;id&#x60;. REST NAMESPACE ONLY REST namespace does not use a request body for
   * this operation. The &#x60;DropTableIndexRequest&#x60; information is passed in the following
   * way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;index_name&#x60;:
   * pass through path parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to drop (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Index drop operation result (status code 200) or Indicates a bad request error. It
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
      operationId = "dropTableIndex",
      summary = "Drop a specific index",
      description =
          "Drop the specified index from table `id`.  REST NAMESPACE ONLY REST namespace does not use a request body for this operation. The `DropTableIndexRequest` information is passed in the following way: - `id`: pass through path parameter of the same name - `index_name`: pass through path parameter of the same name ",
      tags = {"Table", "Index", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Index drop operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DropTableIndexResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/index/{index_name}/drop",
      produces = {"application/json"})
  default ResponseEntity<DropTableIndexResponse> dropTableIndex(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "index_name",
              description = "Name of the index to drop",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("index_name")
          String indexName,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/explain_plan : Get query execution plan explanation Get the query execution
   * plan for a query against table &#x60;id&#x60;. Returns a human-readable explanation of how the
   * query will be executed. REST NAMESPACE ONLY REST namespace returns the response as a plain
   * string instead of the &#x60;ExplainTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param explainTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Query execution plan explanation (status code 200) or Indicates a bad request error. It
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
      operationId = "explainTableQueryPlan",
      summary = "Get query execution plan explanation",
      description =
          "Get the query execution plan for a query against table `id`. Returns a human-readable explanation of how the query will be executed.  REST NAMESPACE ONLY REST namespace returns the response as a plain string instead of the `ExplainTableQueryPlanResponse` JSON object. ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Query execution plan explanation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = String.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/explain_plan",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<String> explainTableQueryPlan(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "ExplainTableQueryPlanRequest", description = "", required = true)
          @Valid
          @RequestBody
          ExplainTableQueryPlanRequest explainTableQueryPlanRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/stats : Get table statistics Get statistics for table &#x60;id&#x60;,
   * including row counts, data sizes, and column statistics.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableStatsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Table statistics (status code 200) or Indicates a bad request error. It could be caused
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
      operationId = "getTableStats",
      summary = "Get table statistics",
      description =
          "Get statistics for table `id`, including row counts, data sizes, and column statistics. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table statistics",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetTableStatsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/stats",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<GetTableStatsResponse> getTableStats(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "GetTableStatsRequest", description = "", required = true)
          @Valid
          @RequestBody
          GetTableStatsRequest getTableStatsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"num_indices\" : 0, \"total_bytes\" : 0, \"num_rows\" : 0, \"fragment_stats\" : { \"num_small_fragments\" : 0, \"lengths\" : { \"p99\" : 0, \"p25\" : 0, \"min\" : 0, \"max\" : 0, \"mean\" : 0, \"p50\" : 0, \"p75\" : 0 }, \"num_fragments\" : 0 } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/tags/version : Get version for a specific tag Get the version number that a
   * specific tag points to for table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableTagVersionRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Tag version information (status code 200) or Indicates a bad request error. It could be
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
      operationId = "getTableTagVersion",
      summary = "Get version for a specific tag",
      description = "Get the version number that a specific tag points to for table `id`. ",
      tags = {"Table", "Tag", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Tag version information",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetTableTagVersionResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/tags/version",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<GetTableTagVersionResponse> getTableTagVersion(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "GetTableTagVersionRequest", description = "", required = true)
          @Valid
          @RequestBody
          GetTableTagVersionRequest getTableTagVersionRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/insert : Insert records into a table Insert new records into table
   * &#x60;id&#x60;. REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body.
   * It passes in the &#x60;InsertIntoTableRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;mode&#x60;: pass through
   * query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC stream containing the records to insert (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode How the insert should behave. Case insensitive, supports both PascalCase and
   *     snake_case. Valid values are: - Create: create new table, fail if table already exists -
   *     Append (default): insert data to the existing table - Overwrite: remove all data in the
   *     table and then insert data to it (optional, default to append)
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
      operationId = "insertIntoTable",
      summary = "Insert records into a table",
      description =
          "Insert new records into table `id`.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `InsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `mode`: pass through query parameter of the same name ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of inserting records into a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InsertIntoTableResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/insert",
      produces = {"application/json"},
      consumes = {"application/vnd.apache.arrow.stream"})
  default ResponseEntity<InsertIntoTableResponse> insertIntoTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "body",
              description = "Arrow IPC stream containing the records to insert",
              required = true)
          @Valid
          @RequestBody
          org.springframework.core.io.Resource body,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "mode",
              description =
                  "How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Create: create new table, fail if table already exists - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "mode", required = false, defaultValue = "append")
          Optional<String> mode) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * GET /v1/table : List all tables List all tables across all namespaces. REST NAMESPACE ONLY REST
   * namespace uses GET to perform this operation without a request body. It passes in the
   * &#x60;ListAllTablesRequest&#x60; information in the following way: - &#x60;page_token&#x60;:
   * pass through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter
   * of the same name - &#x60;delimiter&#x60;: pass through query parameter of the same name
   *
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @return A list of tables (status code 200) or Indicates a bad request error. It could be caused
   *     by an unexpected request body format or other forms of request validation failure, such as
   *     invalid json. Usually serves application/json content, although in some cases simple
   *     text/plain content might be returned by the server&#39;s middleware. (status code 400) or
   *     Unauthorized. The request lacks valid authentication credentials for the operation. (status
   *     code 401) or Forbidden. Authenticated user does not have the necessary permissions. (status
   *     code 403) or The service is not ready to handle the request. The client should wait and
   *     retry. The service may additionally send a Retry-After header to indicate when to retry.
   *     (status code 503) or A server-side problem that might not be addressable from the client
   *     side. Used for server 5xx errors without more specific documentation in individual routes.
   *     (status code 5XX)
   */
  @Operation(
      operationId = "listAllTables",
      summary = "List all tables",
      description =
          "List all tables across all namespaces.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the `ListAllTablesRequest` information in the following way: - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name - `delimiter`: pass through query parameter of the same name ",
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/table",
      produces = {"application/json"})
  default ResponseEntity<ListTablesResponse> listAllTables(
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "page_token",
              description = "Pagination token from a previous request",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "page_token", required = false)
          Optional<String> pageToken,
      @Parameter(
              name = "limit",
              description = "Maximum number of items to return",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "limit", required = false)
          Optional<Integer> limit) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"tables\" : [ \"tables\", \"tables\" ], \"page_token\" : \"page_token\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/index/list : List indexes on a table List all indices created on a table.
   * Returns information about each index including name, columns, status, and UUID.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTableIndicesRequest Index list request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      operationId = "listTableIndices",
      summary = "List indexes on a table",
      description =
          "List all indices created on a table. Returns information about each index including name, columns, status, and UUID. ",
      tags = {"Table", "Index", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "List of indices on the table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ListTableIndicesResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/index/list",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<ListTableIndicesResponse> listTableIndices(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "ListTableIndicesRequest",
              description = "Index list request",
              required = true)
          @Valid
          @RequestBody
          ListTableIndicesRequest listTableIndicesRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"indexes\" : [ { \"index_uuid\" : \"index_uuid\", \"columns\" : [ \"columns\", \"columns\" ], \"index_name\" : \"index_name\", \"status\" : \"status\" }, { \"index_uuid\" : \"index_uuid\", \"columns\" : [ \"columns\", \"columns\" ], \"index_name\" : \"index_name\", \"status\" : \"status\" } ], \"page_token\" : \"page_token\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/tags/list : List all tags for a table List all tags that have been created
   * for table &#x60;id&#x60;. Returns a map of tag names to their corresponding version numbers and
   * metadata. REST NAMESPACE ONLY REST namespace does not use a request body for this operation.
   * The &#x60;ListTableTagsRequest&#x60; information is passed in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @return List of table tags (status code 200) or Indicates a bad request error. It could be
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
      operationId = "listTableTags",
      summary = "List all tags for a table",
      description =
          "List all tags that have been created for table `id`. Returns a map of tag names to their corresponding version numbers and metadata.  REST NAMESPACE ONLY REST namespace does not use a request body for this operation. The `ListTableTagsRequest` information is passed in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name ",
      tags = {"Table", "Tag", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "List of table tags",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ListTableTagsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/tags/list",
      produces = {"application/json"})
  default ResponseEntity<ListTableTagsResponse> listTableTags(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "page_token",
              description = "Pagination token from a previous request",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "page_token", required = false)
          Optional<String> pageToken,
      @Parameter(
              name = "limit",
              description = "Maximum number of items to return",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "limit", required = false)
          Optional<Integer> limit) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"page_token\" : \"page_token\", \"tags\" : { \"key\" : { \"branch\" : \"branch\", \"version\" : 0, \"manifestSize\" : 0 } } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/version/list : List all versions of a table List all versions (commits) of
   * table &#x60;id&#x60; with their metadata. REST NAMESPACE ONLY REST namespace does not use a
   * request body for this operation. The &#x60;ListTableVersionsRequest&#x60; information is passed
   * in the following way: - &#x60;id&#x60;: pass through path parameter of the same name -
   * &#x60;page_token&#x60;: pass through query parameter of the same name - &#x60;limit&#x60;: pass
   * through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @return List of table versions (status code 200) or Indicates a bad request error. It could be
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
      operationId = "listTableVersions",
      summary = "List all versions of a table",
      description =
          "List all versions (commits) of table `id` with their metadata.  REST NAMESPACE ONLY REST namespace does not use a request body for this operation. The `ListTableVersionsRequest` information is passed in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "List of table versions",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ListTableVersionsResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/version/list",
      produces = {"application/json"})
  default ResponseEntity<ListTableVersionsResponse> listTableVersions(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "page_token",
              description = "Pagination token from a previous request",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "page_token", required = false)
          Optional<String> pageToken,
      @Parameter(
              name = "limit",
              description = "Maximum number of items to return",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "limit", required = false)
          Optional<Integer> limit) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"versions\" : [ { \"metadata\" : { \"key\" : \"metadata\" }, \"version\" : 0, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"metadata\" : { \"key\" : \"metadata\" }, \"version\" : 0, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" } ], \"page_token\" : \"page_token\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/merge_insert : Merge insert (upsert) records into a table Performs a merge
   * insert (upsert) operation on table &#x60;id&#x60;. This operation updates existing rows based
   * on a matching column and inserts new rows that don&#39;t match. It returns the number of rows
   * inserted and updated. REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request
   * body. It passes in the &#x60;MergeInsertIntoTableRequest&#x60; information in the following
   * way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;on&#x60;: pass
   * through query parameter of the same name - &#x60;when_matched_update_all&#x60;: pass through
   * query parameter of the same name - &#x60;when_matched_update_all_filt&#x60;: pass through query
   * parameter of the same name - &#x60;when_not_matched_insert_all&#x60;: pass through query
   * parameter of the same name - &#x60;when_not_matched_by_source_delete&#x60;: pass through query
   * parameter of the same name - &#x60;when_not_matched_by_source_delete_filt&#x60;: pass through
   * query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC stream containing the records to merge (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenMatchedUpdateAllFilt The row is updated (similar to UpdateAll) only for rows where
   *     the SQL expression evaluates to true (optional)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param whenNotMatchedBySourceDelete Delete all rows from target table that don&#39;t match a
   *     row in the source table (optional, default to false)
   * @param whenNotMatchedBySourceDeleteFilt Delete rows from the target table if there is no match
   *     AND the SQL expression evaluates to true (optional)
   * @param timeout Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) (optional)
   * @param useIndex Whether to use index for matching rows (optional, default to false)
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
      operationId = "mergeInsertIntoTable",
      summary = "Merge insert (upsert) records into a table",
      description =
          "Performs a merge insert (upsert) operation on table `id`. This operation updates existing rows based on a matching column and inserts new rows that don't match. It returns the number of rows inserted and updated.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `MergeInsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `on`: pass through query parameter of the same name - `when_matched_update_all`: pass through query parameter of the same name - `when_matched_update_all_filt`: pass through query parameter of the same name - `when_not_matched_insert_all`: pass through query parameter of the same name - `when_not_matched_by_source_delete`: pass through query parameter of the same name - `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of merge insert operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = MergeInsertIntoTableResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/merge_insert",
      produces = {"application/json"},
      consumes = {"application/vnd.apache.arrow.stream"})
  default ResponseEntity<MergeInsertIntoTableResponse> mergeInsertIntoTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
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
              description = "Arrow IPC stream containing the records to merge",
              required = true)
          @Valid
          @RequestBody
          org.springframework.core.io.Resource body,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter,
      @Parameter(
              name = "when_matched_update_all",
              description = "Update all columns when rows match",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "when_matched_update_all", required = false, defaultValue = "false")
          Optional<Boolean> whenMatchedUpdateAll,
      @Parameter(
              name = "when_matched_update_all_filt",
              description =
                  "The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "when_matched_update_all_filt", required = false)
          Optional<String> whenMatchedUpdateAllFilt,
      @Parameter(
              name = "when_not_matched_insert_all",
              description = "Insert all columns when rows don't match",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(
              value = "when_not_matched_insert_all",
              required = false,
              defaultValue = "false")
          Optional<Boolean> whenNotMatchedInsertAll,
      @Parameter(
              name = "when_not_matched_by_source_delete",
              description =
                  "Delete all rows from target table that don't match a row in the source table",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(
              value = "when_not_matched_by_source_delete",
              required = false,
              defaultValue = "false")
          Optional<Boolean> whenNotMatchedBySourceDelete,
      @Parameter(
              name = "when_not_matched_by_source_delete_filt",
              description =
                  "Delete rows from the target table if there is no match AND the SQL expression evaluates to true",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "when_not_matched_by_source_delete_filt", required = false)
          Optional<String> whenNotMatchedBySourceDeleteFilt,
      @Parameter(
              name = "timeout",
              description = "Timeout for the operation (e.g., \"30s\", \"5m\")",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "timeout", required = false)
          Optional<String> timeout,
      @Parameter(
              name = "use_index",
              description = "Whether to use index for matching rows",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "use_index", required = false, defaultValue = "false")
          Optional<Boolean> useIndex) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"transaction_id\" : \"transaction_id\", \"num_inserted_rows\" : 0, \"num_updated_rows\" : 0, \"num_deleted_rows\" : 0, \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/query : Query a table Query table &#x60;id&#x60; with vector search, full
   * text search and optional SQL filtering. Returns results in Arrow IPC file or stream format.
   * REST NAMESPACE ONLY REST namespace returns the response as Arrow IPC file binary data instead
   * of the &#x60;QueryTableResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Query results in Arrow IPC file format (status code 200) or Indicates a bad request
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
          "Query table `id` with vector search, full text search and optional SQL filtering. Returns results in Arrow IPC file or stream format.  REST NAMESPACE ONLY REST namespace returns the response as Arrow IPC file binary data instead of the `QueryTableResponse` JSON object. ",
      tags = {"Table", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Query results in Arrow IPC file format",
            content = {
              @Content(
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
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
                  mediaType = "application/vnd.apache.arrow.file",
                  schema = @Schema(implementation = ErrorResponse.class)),
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/query",
      produces = {"application/vnd.apache.arrow.file", "application/json"},
      consumes = {"application/json"})
  default ResponseEntity<org.springframework.core.io.Resource> queryTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "QueryTableRequest", description = "Query request", required = true)
          @Valid
          @RequestBody
          QueryTableRequest queryTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/register : Register a table to a namespace Register an existing table at a
   * given storage location as &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param registerTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Table properties result when registering a table (status code 200) or Indicates a bad
   *     request error. It could be caused by an unexpected request body format or other forms of
   *     request validation failure, such as invalid json. Usually serves application/json content,
   *     although in some cases simple text/plain content might be returned by the server&#39;s
   *     middleware. (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or A server-side problem that means can
   *     not find the specified resource. (status code 404) or Not Acceptable / Unsupported
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
      description = "Register an existing table at a given storage location as `id`. ",
      tags = {"Table", "Metadata"},
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
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
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
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
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
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/rename : Rename a table Rename table &#x60;id&#x60; to a new name.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param renameTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Table rename operation result (status code 200) or Indicates a bad request error. It
   *     could be caused by an unexpected request body format or other forms of request validation
   *     failure, such as invalid json. Usually serves application/json content, although in some
   *     cases simple text/plain content might be returned by the server&#39;s middleware. (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or A server-side problem that means can not find the
   *     specified resource. (status code 404) or The request conflicts with the current state of
   *     the target resource. (status code 409) or The service is not ready to handle the request.
   *     The client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "renameTable",
      summary = "Rename a table",
      description = "Rename table `id` to a new name. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table rename operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RenameTableResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/rename",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<RenameTableResponse> renameTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "RenameTableRequest", description = "", required = true) @Valid @RequestBody
          RenameTableRequest renameTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/restore : Restore table to a specific version Restore table &#x60;id&#x60;
   * to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param restoreTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Table restore operation result (status code 200) or Indicates a bad request error. It
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
      operationId = "restoreTable",
      summary = "Restore table to a specific version",
      description = "Restore table `id` to a specific version. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table restore operation result",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RestoreTableResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/restore",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<RestoreTableResponse> restoreTable(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "RestoreTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          RestoreTableRequest restoreTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/exists : Check if a table exists Check if table &#x60;id&#x60; exists. This
   * operation should behave exactly like DescribeTable, except it does not contain a response body.
   * For DirectoryNamespace implementation, a table exists if either: - The table has Lance data
   * versions (regular table created with CreateTable) - A &#x60;.lance-reserved&#x60; file exists
   * in the table directory (empty table created with CreateEmptyTable)
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param tableExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Success, no content (status code 200) or Indicates a bad request error. It could be
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
      operationId = "tableExists",
      summary = "Check if a table exists",
      description =
          "Check if table `id` exists.  This operation should behave exactly like DescribeTable,  except it does not contain a response body.  For DirectoryNamespace implementation, a table exists if either: - The table has Lance data versions (regular table created with CreateTable) - A `.lance-reserved` file exists in the table directory (empty table created with CreateEmptyTable) ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(responseCode = "200", description = "Success, no content"),
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/exists",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<Void> tableExists(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "TableExistsRequest", description = "", required = true) @Valid @RequestBody
          TableExistsRequest tableExistsRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/update : Update rows in a table Update existing rows in table
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
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
      description = "Update existing rows in table `id`. ",
      tags = {"Table", "Data"},
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
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
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "UpdateTableRequest", description = "Update request", required = true)
          @Valid
          @RequestBody
          UpdateTableRequest updateTableRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"transaction_id\" : \"transaction_id\", \"updated_rows\" : 0, \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/schema_metadata/update : Update table schema metadata Replace the schema
   * metadata for table &#x60;id&#x60; with the provided key-value pairs. REST NAMESPACE ONLY REST
   * namespace uses a direct object (map of string to string) as both request and response body
   * instead of the wrapped &#x60;UpdateTableSchemaMetadataRequest&#x60; and
   * &#x60;UpdateTableSchemaMetadataResponse&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param requestBody (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Schema metadata update result (status code 200) or Indicates a bad request error. It
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
      operationId = "updateTableSchemaMetadata",
      summary = "Update table schema metadata",
      description =
          "Replace the schema metadata for table `id` with the provided key-value pairs.  REST NAMESPACE ONLY REST namespace uses a direct object (map of string to string) as both request and response body instead of the wrapped `UpdateTableSchemaMetadataRequest` and `UpdateTableSchemaMetadataResponse`. ",
      tags = {"Table", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Schema metadata update result",
            content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/schema_metadata/update",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<Map<String, String>> updateTableSchemaMetadata(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "request_body", description = "", required = true) @Valid @RequestBody
          Map<String, String> requestBody,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
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
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/table/{id}/tags/update : Update a tag to point to a different version Update an
   * existing tag for table &#x60;id&#x60; to point to a different version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Update tag response (status code 200) or Indicates a bad request error. It could be
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
      operationId = "updateTableTag",
      summary = "Update a tag to point to a different version",
      description = "Update an existing tag for table `id` to point to a different version. ",
      tags = {"Table", "Tag", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Update tag response",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateTableTagResponse.class))
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
      },
      security = {
        @SecurityRequirement(name = "OAuth2"),
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/table/{id}/tags/update",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<UpdateTableTagResponse> updateTableTag(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "UpdateTableTagRequest", description = "", required = true)
          @Valid
          @RequestBody
          UpdateTableTagRequest updateTableTagRequest,
      @Parameter(
              name = "delimiter",
              description =
                  "An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. ",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "delimiter", required = false)
          Optional<String> delimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"transaction_id\" : \"transaction_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 404, \"instance\" : \"/login/log/abc123\", \"detail\" : \"Authentication failed due to incorrect username or password\", \"error\" : \"Incorrect username or password\", \"type\" : \"/errors/incorrect-user-pass\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}

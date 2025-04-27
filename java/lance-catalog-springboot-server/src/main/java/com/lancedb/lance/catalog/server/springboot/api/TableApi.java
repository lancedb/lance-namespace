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
package com.lancedb.lance.catalog.server.springboot.api;

import com.lancedb.lance.catalog.server.springboot.model.ErrorResponse;
import com.lancedb.lance.catalog.server.springboot.model.GetTableResponse;
import com.lancedb.lance.catalog.server.springboot.model.RegisterTableRequest;

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
   * GET /v1/tables/{table} : Get a table from the catalog Get a table&#39;s detailed information.
   *
   * @param table An identifier of the table (required)
   * @param tableDelimiter The delimiter used by the table identifier (optional, default to .)
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
      operationId = "getTable",
      summary = "Get a table from the catalog",
      description = "Get a table's detailed information. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when loading a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetTableResponse.class))
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
      method = RequestMethod.GET,
      value = "/v1/tables/{table}",
      produces = {"application/json"})
  default ResponseEntity<GetTableResponse> getTable(
      @Parameter(
              name = "table",
              description = "An identifier of the table",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("table")
          String table,
      @Parameter(
              name = "tableDelimiter",
              description = "The delimiter used by the table identifier",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "tableDelimiter", required = false, defaultValue = ".")
          Optional<String> tableDelimiter) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
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
   * POST /v1/catalogs/{catalog}/register : Register an existing table in the given catalog.
   *
   * @param catalog An identifier of the catalog. (required)
   * @param registerTableRequest (required)
   * @return Table properties result when loading a table (status code 200) or Indicates a bad
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
      summary = "Register an existing table in the given catalog. ",
      tags = {"Table"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Table properties result when loading a table",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetTableResponse.class))
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
      value = "/v1/catalogs/{catalog}/register",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<GetTableResponse> registerTable(
      @Parameter(
              name = "catalog",
              description = "An identifier of the catalog.",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("catalog")
          String catalog,
      @Parameter(name = "RegisterTableRequest", description = "", required = true)
          @Valid
          @RequestBody
          RegisterTableRequest registerTableRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"location\" : \"location\", \"properties\" : { \"key\" : \"properties\" } }";
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
   * HEAD /v1/tables/{table} : Check if a table exists Check if a table exists.
   *
   * @param table An identifier of the table (required)
   * @param tableDelimiter The delimiter used by the table identifier (optional, default to .)
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
      description = "Check if a table exists.",
      tags = {"Table"},
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
      })
  @RequestMapping(
      method = RequestMethod.HEAD,
      value = "/v1/tables/{table}",
      produces = {"application/json"})
  default ResponseEntity<Void> tableExists(
      @Parameter(
              name = "table",
              description = "An identifier of the table",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("table")
          String table,
      @Parameter(
              name = "tableDelimiter",
              description = "The delimiter used by the table identifier",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "tableDelimiter", required = false, defaultValue = ".")
          Optional<String> tableDelimiter) {
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
}

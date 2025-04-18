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

import com.lancedb.lance.catalog.server.springboot.model.CreateNamespaceRequest;
import com.lancedb.lance.catalog.server.springboot.model.CreateNamespaceResponse;
import com.lancedb.lance.catalog.server.springboot.model.ErrorModel;
import com.lancedb.lance.catalog.server.springboot.model.GetNamespaceResponse;
import com.lancedb.lance.catalog.server.springboot.model.ListNamespacesResponse;

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
@Tag(name = "Namespace", description = "the Namespace API")
public interface NamespaceApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /v1/namespaces : Create a new namespace. A catalog can manage one or more namespaces. A
   * namespace is used to manage one or more tables. There are three modes when trying to create a
   * namespace: * CREATE: Create the namespace if it does not exist. If a namespace of the same name
   * already exists, the operation fails with 400. * EXIST_OK: Create the namespace if it does not
   * exist. If a namespace of the same name already exists, the operation succeeds and the existing
   * namespace is kept. * OVERWRITE: Create the namespace if it does not exist. If a namespace of
   * the same name already exists, the existing namespace is dropped and a new namespace with this
   * name with no table is created.
   *
   * @param createNamespaceRequest (required)
   * @return Represents a successful call to create a namespace. Returns the namespace created, as
   *     well as any properties that were stored for the namespace, including those the server might
   *     have added. Implementations are not required to support namespace properties. (status code
   *     200) or Indicates a bad request error. It could be caused by an unexpected request body
   *     format or other forms of request validation failure, such as invalid json. Usually serves
   *     application/json content, although in some cases simple text/plain content might be
   *     returned by the server&#39;s middleware. (status code 400) or Unauthorized. The request
   *     lacks valid authentication credentials for the operation. (status code 401) or Forbidden.
   *     Authenticated user does not have the necessary permissions. (status code 403) or Not
   *     Acceptable / Unsupported Operation. The server does not support this operation. (status
   *     code 406) or The service is not ready to handle the request. The client should wait and
   *     retry. The service may additionally send a Retry-After header to indicate when to retry.
   *     (status code 503) or A server-side problem that might not be addressable from the client
   *     side. Used for server 5xx errors without more specific documentation in individual routes.
   *     (status code 5XX)
   */
  @Operation(
      operationId = "createNamespace",
      summary =
          "Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. ",
      tags = {"Namespace"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Represents a successful call to create a namespace. Returns the namespace created, as well as any properties that were stored for the namespace, including those the server might have added. Implementations are not required to support namespace properties.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateNamespaceResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "406",
            description =
                "Not Acceptable / Unsupported Operation. The server does not support this operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/namespaces",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateNamespaceResponse> createNamespace(
      @Parameter(name = "CreateNamespaceRequest", description = "", required = true)
          @Valid
          @RequestBody
          CreateNamespaceRequest createNamespaceRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"name\" : \"name\", \"properties\" : { \"created_at\" : \"1452120468\" } }";
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
   * DELETE /v1/namespaces/{ns} : Drop a namespace from the catalog. Namespace must be empty.
   *
   * @param ns The name of the namespace. (required)
   * @return Success, no content (status code 204) or Indicates a bad request error. It could be
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
      operationId = "dropNamespace",
      summary = "Drop a namespace from the catalog. Namespace must be empty.",
      tags = {"Namespace"},
      responses = {
        @ApiResponse(responseCode = "204", description = "Success, no content"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.DELETE,
      value = "/v1/namespaces/{ns}",
      produces = {"application/json"})
  default ResponseEntity<Void> dropNamespace(
      @Parameter(
              name = "ns",
              description = "The name of the namespace.",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("ns")
          String ns) {
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
   * GET /v1/namespaces/{ns} : Get information about a namespace Return a detailed information for a
   * given namespace
   *
   * @param ns The name of the namespace. (required)
   * @return Returns a namespace, as well as any properties stored on the namespace if namespace
   *     properties are supported by the server. (status code 200) or Indicates a bad request error.
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
      operationId = "getNamespace",
      summary = "Get information about a namespace",
      description = "Return a detailed information for a given namespace",
      tags = {"Namespace"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetNamespaceResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/namespaces/{ns}",
      produces = {"application/json"})
  default ResponseEntity<GetNamespaceResponse> getNamespace(
      @Parameter(
              name = "ns",
              description = "The name of the namespace.",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("ns")
          String ns) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"namespace\" : \"namespace\", \"properties\" : { \"owner\" : \"Ralph\", \"created_at\" : \"1452120468\" } }";
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
   * GET /v1/namespaces : List all namespaces in the catalog.
   *
   * @param pageToken (optional)
   * @param pageSize An inclusive upper bound of the number of results that a client will receive.
   *     (optional)
   * @return A list of namespaces (status code 200) or Indicates a bad request error. It could be
   *     caused by an unexpected request body format or other forms of request validation failure,
   *     such as invalid json. Usually serves application/json content, although in some cases
   *     simple text/plain content might be returned by the server&#39;s middleware. (status code
   *     400) or Unauthorized. The request lacks valid authentication credentials for the operation.
   *     (status code 401) or Forbidden. Authenticated user does not have the necessary permissions.
   *     (status code 403) or Not Acceptable / Unsupported Operation. The server does not support
   *     this operation. (status code 406) or The service is not ready to handle the request. The
   *     client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or A server-side problem that might not be
   *     addressable from the client side. Used for server 5xx errors without more specific
   *     documentation in individual routes. (status code 5XX)
   */
  @Operation(
      operationId = "listNamespaces",
      summary = "List all namespaces in the catalog. ",
      tags = {"Namespace"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "A list of namespaces",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ListNamespacesResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "406",
            description =
                "Not Acceptable / Unsupported Operation. The server does not support this operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/namespaces",
      produces = {"application/json"})
  default ResponseEntity<ListNamespacesResponse> listNamespaces(
      @Parameter(name = "pageToken", description = "", in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "pageToken", required = false)
          Optional<String> pageToken,
      @Parameter(
              name = "pageSize",
              description =
                  "An inclusive upper bound of the number of results that a client will receive.",
              in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "pageSize", required = false)
          Optional<@Min(1) Integer> pageSize) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"nextPageToken\" : \"nextPageToken\", \"namespaces\" : [ \"accounting\", \"accounting\" ] }";
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
   * HEAD /v1/namespaces/{ns} : Check if a namespace exists Check if a namespace exists. The
   * response does not contain a body.
   *
   * @param ns The name of the namespace. (required)
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
      operationId = "namespaceExists",
      summary = "Check if a namespace exists",
      description = "Check if a namespace exists. The response does not contain a body.",
      tags = {"Namespace"},
      responses = {
        @ApiResponse(responseCode = "200", description = "Success, no content"),
        @ApiResponse(
            responseCode = "400",
            description =
                "Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server's middleware.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description =
                "Unauthorized. The request lacks valid authentication credentials for the operation.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "403",
            description = "Forbidden. Authenticated user does not have the necessary permissions.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "A server-side problem that means can not find the specified resource.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "503",
            description =
                "The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            }),
        @ApiResponse(
            responseCode = "5XX",
            description =
                "A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorModel.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.HEAD,
      value = "/v1/namespaces/{ns}",
      produces = {"application/json"})
  default ResponseEntity<Void> namespaceExists(
      @Parameter(
              name = "ns",
              description = "The name of the namespace.",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("ns")
          String ns) {
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

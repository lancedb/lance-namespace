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

import org.lance.namespace.server.springboot.model.CreateNamespace400Response;
import org.lance.namespace.server.springboot.model.CreateNamespaceRequest;
import org.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import org.lance.namespace.server.springboot.model.DescribeNamespaceRequest;
import org.lance.namespace.server.springboot.model.DescribeNamespaceResponse;
import org.lance.namespace.server.springboot.model.DropNamespaceRequest;
import org.lance.namespace.server.springboot.model.DropNamespaceResponse;
import org.lance.namespace.server.springboot.model.ErrorResponse;
import org.lance.namespace.server.springboot.model.InvalidRequestError;
import org.lance.namespace.server.springboot.model.ListNamespacesResponse;
import org.lance.namespace.server.springboot.model.ListTablesResponse;
import org.lance.namespace.server.springboot.model.NamespaceAlreadyExistsError;
import org.lance.namespace.server.springboot.model.NamespaceExistsRequest;
import org.lance.namespace.server.springboot.model.NamespaceNotEmptyError;
import org.lance.namespace.server.springboot.model.NamespaceNotFoundError;
import org.lance.namespace.server.springboot.model.ParentNamespaceNotFoundError;
import org.lance.namespace.server.springboot.model.UnknownError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

import java.util.Optional;

@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
@Validated
@Tag(name = "Namespace", description = "Operations that are related to a namespace ")
public interface NamespaceApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /v1/namespace/{id}/create : Create a new namespace Create new namespace &#x60;id&#x60;.
   * During the creation process, the implementation may modify user-provided
   * &#x60;properties&#x60;, such as adding additional properties like &#x60;created_at&#x60; to
   * user-provided properties, omitting any specific property, or performing actions based on any
   * property value.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Result of creating a namespace (status code 200) or Bad request - invalid request
   *     format or identifier (status code 400) or Unauthorized. The request lacks valid
   *     authentication credentials for the operation. (status code 401) or Forbidden. Authenticated
   *     user does not have the necessary permissions. (status code 403) or The parent namespace
   *     does not exist (status code 404) or Not Acceptable / Unsupported Operation. The server does
   *     not support this operation. (status code 406) or A namespace with the same name already
   *     exists (status code 409) or The service is not ready to handle the request. The client
   *     should wait and retry. The service may additionally send a Retry-After header to indicate
   *     when to retry. (status code 503) or Unknown or unclassified error (status code 5XX)
   */
  @Operation(
      operationId = "createNamespace",
      summary = "Create a new namespace",
      description =
          "Create new namespace `id`.  During the creation process, the implementation may modify user-provided `properties`,  such as adding additional properties like `created_at` to user-provided properties,  omitting any specific property, or performing actions based on any property value. ",
      tags = {"Namespace", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of creating a namespace",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateNamespaceResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - invalid request format or identifier",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateNamespace400Response.class))
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
            description = "The parent namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ParentNamespaceNotFoundError.class))
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
            description = "A namespace with the same name already exists",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceAlreadyExistsError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/namespace/{id}/create",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateNamespaceResponse> createNamespace(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CreateNamespaceRequest", description = "", required = true)
          @Valid
          @RequestBody
          CreateNamespaceRequest createNamespaceRequest,
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
                  String exampleString = "{ \"properties\" : { \"key\" : \"properties\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 6, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Parent namespace not found\", \"type\" : \"lance-namespace:103\" }";
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
                      "{ \"code\" : 1, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace already exists\", \"type\" : \"lance-namespace:102\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/namespace/{id}/describe : Describe a namespace Describe the detailed information for
   * namespace &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Returns a namespace, as well as any properties stored on the namespace if namespace
   *     properties are supported by the server. (status code 200) or Malformed request or
   *     validation failure (status code 400) or Unauthorized. The request lacks valid
   *     authentication credentials for the operation. (status code 401) or Forbidden. Authenticated
   *     user does not have the necessary permissions. (status code 403) or The requested namespace
   *     does not exist (status code 404) or The service is not ready to handle the request. The
   *     client should wait and retry. The service may additionally send a Retry-After header to
   *     indicate when to retry. (status code 503) or Unknown or unclassified error (status code
   *     5XX)
   */
  @Operation(
      operationId = "describeNamespace",
      summary = "Describe a namespace",
      description = "Describe the detailed information for namespace `id`. ",
      tags = {"Namespace", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DescribeNamespaceResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Malformed request or validation failure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InvalidRequestError.class))
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
            description = "The requested namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotFoundError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/namespace/{id}/describe",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DescribeNamespaceResponse> describeNamespace(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DescribeNamespaceRequest", description = "", required = true)
          @Valid
          @RequestBody
          DescribeNamespaceRequest describeNamespaceRequest,
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
                      "{ \"properties\" : { \"owner\" : \"Ralph\", \"created_at\" : \"1452120468\" } }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not found\", \"type\" : \"lance-namespace:101\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/namespace/{id}/drop : Drop a namespace Drop namespace &#x60;id&#x60; from its parent
   * namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Result of dropping a namespace (status code 200) or Malformed request or validation
   *     failure (status code 400) or Unauthorized. The request lacks valid authentication
   *     credentials for the operation. (status code 401) or Forbidden. Authenticated user does not
   *     have the necessary permissions. (status code 403) or The requested namespace does not exist
   *     (status code 404) or Cannot drop namespace because it contains tables or child namespaces
   *     (status code 409) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or Unknown or unclassified error (status code 5XX)
   */
  @Operation(
      operationId = "dropNamespace",
      summary = "Drop a namespace",
      description = "Drop namespace `id` from its parent namespace. ",
      tags = {"Namespace", "Metadata"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Result of dropping a namespace",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = DropNamespaceResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Malformed request or validation failure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InvalidRequestError.class))
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
            description = "The requested namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotFoundError.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Cannot drop namespace because it contains tables or child namespaces",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotEmptyError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/namespace/{id}/drop",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<DropNamespaceResponse> dropNamespace(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "DropNamespaceRequest", description = "", required = true)
          @Valid
          @RequestBody
          DropNamespaceRequest dropNamespaceRequest,
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
                      "{ \"properties\" : { \"key\" : \"properties\" }, \"transactionId\" : [ \"transactionId\", \"transactionId\" ] }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not found\", \"type\" : \"lance-namespace:101\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not empty\", \"type\" : \"lance-namespace:104\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * GET /v1/namespace/{id}/list : List namespaces List all child namespace names of the parent
   * namespace &#x60;id&#x60;. REST NAMESPACE ONLY REST namespace uses GET to perform this operation
   * without a request body. It passes in the &#x60;ListNamespacesRequest&#x60; information in the
   * following way: - &#x60;id&#x60;: pass through path parameter of the same name -
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
   * @param pageToken (optional)
   * @param limit (optional)
   * @return A list of namespaces (status code 200) or Malformed request or validation failure
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or The requested namespace does not exist (status
   *     code 404) or Not Acceptable / Unsupported Operation. The server does not support this
   *     operation. (status code 406) or The service is not ready to handle the request. The client
   *     should wait and retry. The service may additionally send a Retry-After header to indicate
   *     when to retry. (status code 503) or Unknown or unclassified error (status code 5XX)
   */
  @Operation(
      operationId = "listNamespaces",
      summary = "List namespaces",
      description =
          "List all child namespace names of the parent namespace `id`.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the `ListNamespacesRequest` information in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name ",
      tags = {"Namespace", "Metadata"},
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
            description = "Malformed request or validation failure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InvalidRequestError.class))
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
            description = "The requested namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotFoundError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/namespace/{id}/list",
      produces = {"application/json"})
  default ResponseEntity<ListNamespacesResponse> listNamespaces(
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
      @Parameter(name = "page_token", description = "", in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "page_token", required = false)
          Optional<String> pageToken,
      @Parameter(name = "limit", description = "", in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "limit", required = false)
          Optional<Integer> limit) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"page_token\" : \"page_token\", \"namespaces\" : [ \"namespaces\", \"namespaces\" ] }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not found\", \"type\" : \"lance-namespace:101\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * GET /v1/namespace/{id}/table/list : List tables in a namespace List all child table names of
   * the parent namespace &#x60;id&#x60;. REST NAMESPACE ONLY REST namespace uses GET to perform
   * this operation without a request body. It passes in the &#x60;ListTablesRequest&#x60;
   * information in the following way: - &#x60;id&#x60;: pass through path parameter of the same
   * name - &#x60;page_token&#x60;: pass through query parameter of the same name -
   * &#x60;limit&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @return A list of tables (status code 200) or Malformed request or validation failure (status
   *     code 400) or Unauthorized. The request lacks valid authentication credentials for the
   *     operation. (status code 401) or Forbidden. Authenticated user does not have the necessary
   *     permissions. (status code 403) or The requested namespace does not exist (status code 404)
   *     or Not Acceptable / Unsupported Operation. The server does not support this operation.
   *     (status code 406) or The service is not ready to handle the request. The client should wait
   *     and retry. The service may additionally send a Retry-After header to indicate when to
   *     retry. (status code 503) or Unknown or unclassified error (status code 5XX)
   */
  @Operation(
      operationId = "listTables",
      summary = "List tables in a namespace",
      description =
          "List all child table names of the parent namespace `id`.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the `ListTablesRequest` information in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name ",
      tags = {"Namespace", "Table", "Metadata"},
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
            description = "Malformed request or validation failure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InvalidRequestError.class))
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
            description = "The requested namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotFoundError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/namespace/{id}/table/list",
      produces = {"application/json"})
  default ResponseEntity<ListTablesResponse> listTables(
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
      @Parameter(name = "page_token", description = "", in = ParameterIn.QUERY)
          @Valid
          @RequestParam(value = "page_token", required = false)
          Optional<String> pageToken,
      @Parameter(name = "limit", description = "", in = ParameterIn.QUERY)
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not found\", \"type\" : \"lance-namespace:101\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/namespace/{id}/exists : Check if a namespace exists Check if namespace &#x60;id&#x60;
   * exists. This operation must behave exactly like the DescribeNamespace API, except it does not
   * contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param namespaceExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Success, no content (status code 200) or Malformed request or validation failure
   *     (status code 400) or Unauthorized. The request lacks valid authentication credentials for
   *     the operation. (status code 401) or Forbidden. Authenticated user does not have the
   *     necessary permissions. (status code 403) or The requested namespace does not exist (status
   *     code 404) or The service is not ready to handle the request. The client should wait and
   *     retry. The service may additionally send a Retry-After header to indicate when to retry.
   *     (status code 503) or Unknown or unclassified error (status code 5XX)
   */
  @Operation(
      operationId = "namespaceExists",
      summary = "Check if a namespace exists",
      description =
          "Check if namespace `id` exists.  This operation must behave exactly like the DescribeNamespace API,  except it does not contain a response body. ",
      tags = {"Namespace", "Metadata"},
      responses = {
        @ApiResponse(responseCode = "200", description = "Success, no content"),
        @ApiResponse(
            responseCode = "400",
            description = "Malformed request or validation failure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InvalidRequestError.class))
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
            description = "The requested namespace does not exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NamespaceNotFoundError.class))
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
            description = "Unknown or unclassified error",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UnknownError.class))
            })
      })
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/namespace/{id}/exists",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<Void> namespaceExists(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "NamespaceExistsRequest", description = "", required = true)
          @Valid
          @RequestBody
          NamespaceExistsRequest namespaceExistsRequest,
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Invalid request\", \"type\" : \"lance-namespace:601\" }";
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
                      "{ \"code\" : 0, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Namespace not found\", \"type\" : \"lance-namespace:101\" }";
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
                      "{ \"code\" : 5, \"instance\" : \"instance\", \"detail\" : \"detail\", \"error\" : \"Unknown error\", \"type\" : \"lance-namespace:0\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}

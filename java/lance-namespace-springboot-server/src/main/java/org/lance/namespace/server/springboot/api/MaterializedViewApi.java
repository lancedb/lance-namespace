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

import org.lance.namespace.server.springboot.model.CreateMaterializedViewRequest;
import org.lance.namespace.server.springboot.model.CreateMaterializedViewResponse;
import org.lance.namespace.server.springboot.model.ErrorResponse;
import org.lance.namespace.server.springboot.model.RefreshMaterializedViewRequest;
import org.lance.namespace.server.springboot.model.RefreshMaterializedViewResponse;

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

import java.util.Optional;

@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
@Validated
@Tag(name = "MaterializedView", description = "the MaterializedView API")
public interface MaterializedViewApi {

  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  /**
   * POST /v1/materialized_view/{id}/create : Create a materialized view Create a materialized view
   * at identifier &#x60;id&#x60;. The view may be query-backed, UDTF-backed, or chunker-backed,
   * controlled by the &#x60;kind&#x60; discriminator.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createMaterializedViewRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Materialized view created (status code 201) or Indicates a bad request error. It could
   *     be caused by an unexpected request body format or other forms of request validation
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
      operationId = "createMaterializedView",
      summary = "Create a materialized view",
      description =
          "Create a materialized view at identifier `id`. The view may be query-backed, UDTF-backed, or chunker-backed, controlled by the `kind` discriminator. ",
      tags = {"MaterializedView", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Materialized view created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateMaterializedViewResponse.class))
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
      value = "/v1/materialized_view/{id}/create",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<CreateMaterializedViewResponse> createMaterializedView(
      @Parameter(
              name = "id",
              description =
                  "`string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. ",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("id")
          String id,
      @Parameter(name = "CreateMaterializedViewRequest", description = "", required = true)
          @Valid
          @RequestBody
          CreateMaterializedViewRequest createMaterializedViewRequest,
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
                  String exampleString = "{ \"job_id\" : \"job_id\", \"version\" : 0 }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * POST /v1/materialized_view/{id}/refresh : Trigger an async materialized view refresh Trigger an
   * asynchronous refresh job for materialized view &#x60;id&#x60;. Returns a job ID for tracking.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param refreshMaterializedViewRequest (optional)
   * @return Refresh job accepted (status code 202) or Indicates a bad request error. It could be
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
      operationId = "refreshMaterializedView",
      summary = "Trigger an async materialized view refresh",
      description =
          "Trigger an asynchronous refresh job for materialized view `id`. Returns a job ID for tracking. ",
      tags = {"MaterializedView", "Data"},
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "Refresh job accepted",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RefreshMaterializedViewResponse.class))
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
      value = "/v1/materialized_view/{id}/refresh",
      produces = {"application/json"},
      consumes = {"application/json"})
  default ResponseEntity<RefreshMaterializedViewResponse> refreshMaterializedView(
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
      @Parameter(name = "RefreshMaterializedViewRequest", description = "")
          @Valid
          @RequestBody(required = false)
          Optional<RefreshMaterializedViewRequest> refreshMaterializedViewRequest) {
    getRequest()
        .ifPresent(
            request -> {
              for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString = "{ \"job_id\" : \"job_id\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                  String exampleString =
                      "{ \"code\" : 4, \"instance\" : \"/v1/table/production$users/describe\", \"detail\" : \"The table may have been dropped or renamed\", \"error\" : \"Table 'users' not found in namespace 'production'\" }";
                  ApiUtil.setExampleResponse(request, "application/json", exampleString);
                  break;
                }
              }
            });
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}

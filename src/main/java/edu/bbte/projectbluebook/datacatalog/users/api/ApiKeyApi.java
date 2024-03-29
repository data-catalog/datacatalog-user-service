/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.3.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package edu.bbte.projectbluebook.datacatalog.users.api;

import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationRequest;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyCreationResponse;
import edu.bbte.projectbluebook.datacatalog.users.model.dto.ApiKeyResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@Api(value = "ApiKey", description = "the ApiKey API")
public interface ApiKeyApi {

    /**
     * POST /user/keys : Create an API Key for the Authenticated User
     * Generates a new API key for the authenticated user.  Requires authentication.  The key title must be unique for the user.   The key will be returned only with this request. It cannot be viewed again.
     *
     * @param apiKeyCreationRequest  (optional)
     * @return Created (status code 201)
     *         or Unprocessable Entity (WebDAV) (status code 422)
     */
    @ApiOperation(value = "Create an API Key for the Authenticated User", nickname = "createUserApiKey", notes = "Generates a new API key for the authenticated user.  Requires authentication.  The key title must be unique for the user.   The key will be returned only with this request. It cannot be viewed again.", response = ApiKeyCreationResponse.class, authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "ApiKey", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = ApiKeyCreationResponse.class),
        @ApiResponse(code = 422, message = "Unprocessable Entity (WebDAV)") })
    @RequestMapping(value = "/user/keys",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default Mono<ResponseEntity<ApiKeyCreationResponse>> createUserApiKey(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Mono<ApiKeyCreationRequest> apiKeyCreationRequest, ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"id\", \"title\" : \"title\", \"key\" : \"key\" }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * DELETE /user/keys/{keyId} : Delete an API Key
     * Removes an API key from the authenticated user.
     *
     * @param keyId The ID of the API key. (required)
     * @return No Content (status code 204)
     *         or Not Found (status code 404)
     */
    @ApiOperation(value = "Delete an API Key", nickname = "deleteUserApiKey", notes = "Removes an API key from the authenticated user.", authorizations = {
        @Authorization(value = "ApiKey"),
        @Authorization(value = "JWT")
    }, tags={ "ApiKey", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/user/keys/{keyId}",
        method = RequestMethod.DELETE)
    default Mono<ResponseEntity<Void>> deleteUserApiKey(@ApiParam(value = "The ID of the API key.",required=true) @PathVariable("keyId") String keyId, ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        return result.then(Mono.empty());

    }


    /**
     * GET /user/keys/{keyId} : Get an API Key
     * View a single API key of the authenticated user. For security reasons, it only returns the name and ID, not the key itself.
     *
     * @param keyId The ID of the API key. (required)
     * @return OK (status code 200)
     *         or Not Found (status code 404)
     */
    @ApiOperation(value = "Get an API Key", nickname = "getUserApiKey", notes = "View a single API key of the authenticated user. For security reasons, it only returns the name and ID, not the key itself.", response = ApiKeyResponse.class, authorizations = {
        @Authorization(value = "ApiKey"),
        @Authorization(value = "JWT")
    }, tags={ "ApiKey", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiKeyResponse.class),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/user/keys/{keyId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default Mono<ResponseEntity<ApiKeyResponse>> getUserApiKey(@ApiParam(value = "The ID of the API key.",required=true) @PathVariable("keyId") String keyId, ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"id\", \"title\" : \"title\" }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * GET /user/keys : Get the API Keys of the Authenticated User
     * Lists the API keys for the authenticated user. For security reasons, it only returns the name and ID, not the key itself.  Requires authentication.
     *
     * @return OK (status code 200)
     */
    @ApiOperation(value = "Get the API Keys of the Authenticated User", nickname = "getUserApiKeys", notes = "Lists the API keys for the authenticated user. For security reasons, it only returns the name and ID, not the key itself.  Requires authentication.", response = ApiKeyResponse.class, responseContainer = "List", authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "ApiKey", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiKeyResponse.class, responseContainer = "List") })
    @RequestMapping(value = "/user/keys",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default Mono<ResponseEntity<Flux<ApiKeyResponse>>> getUserApiKeys(ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"id\", \"title\" : \"title\" }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

}

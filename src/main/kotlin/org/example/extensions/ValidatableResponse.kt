package org.example.extensions

import io.restassured.response.ValidatableResponse
import org.apache.http.HttpStatus
import org.hamcrest.CoreMatchers

/**
 * Keep all common methods to a response
 * @author: Dilshan Fernando
 * @since: 04/12/2021
 */

/**
 * Verify the successful response headers
 */
fun ValidatableResponse.validateSuccessResponseHeaders(): ValidatableResponse =
    statusCode(HttpStatus.SC_OK)
        .headers(
            mapOf(
                "Connection" to "keep-alive",
                "content-length" to CoreMatchers.notNullValue(),
                "Content-Type" to "application/json; charset=utf-8",
                "date" to CoreMatchers.notNullValue(),
                "etag" to CoreMatchers.notNullValue(),
                "Keep-Alive" to "timeout=5",
                "x-powered-by" to "Express"
            )
        )

/**
 * Verify the error response headers and body
 */
fun ValidatableResponse.validateErrorResponse(
    httpStatusCode: Int,
    error: String,
    errorDescription: String
): ValidatableResponse = statusCode(httpStatusCode)
    .body("error", CoreMatchers.`is`(error))
    .body("error_description", CoreMatchers.`is`(errorDescription))
    .headers(
        mapOf(
            "Connection" to "keep-alive",
            "content-length" to CoreMatchers.notNullValue(),
            "Content-Type" to "application/json; charset=utf-8",
            "date" to CoreMatchers.notNullValue(),
            "Keep-Alive" to "timeout=5",
            "x-powered-by" to "Express"
        )
    )
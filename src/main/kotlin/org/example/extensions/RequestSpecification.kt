package org.example.extensions

import io.restassured.specification.RequestSpecification

/**
 * Keep all common methods to a request
 * @author: Dilshan Fernando
 * @since: 15/09/2021
 */


/**
 * Including a content json type body in the request
 */
fun RequestSpecification.setJSONBody(jsonString: String): RequestSpecification =
        body(jsonString)
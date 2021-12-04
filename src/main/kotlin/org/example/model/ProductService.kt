package org.example.model

import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.example.util.CommonConstants
import org.example.util.TestBase
import org.hamcrest.CoreMatchers

open class ProductService: TestBase()  {
    //can access properties or methods even without any object creation
    companion object {

        const val GET_PRODUCT_RESOURCE_PATH = "/product"

        /**
         * Set Content-Type header to request
         */
        fun RequestSpecification.setContentTypeHeader(value: String): RequestSpecification =
            header(CommonConstants.Header.CONTENT_TYPE_HEADER, value)

        /**
         * Verify the response of post details
         */
        fun ValidatableResponse.validateResponseProductDetails(): ValidatableResponse =
            statusCode(HttpStatus.SC_OK)
                .header(CommonConstants.Header.CONTENT_TYPE_HEADER, CoreMatchers.notNullValue())
                .body("currency", CoreMatchers.notNullValue())

    }
}
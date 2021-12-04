package org.example.model

import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.example.util.CommonConstants.Header.CONTENT_TYPE_HEADER
import org.example.util.TestBase
import org.hamcrest.CoreMatchers

open class ProductService : TestBase() {
    //can access properties or methods even without any object creation
    companion object {

        const val GET_PRODUCT_RESOURCE_PATH = "/product"
        const val PRODUCT_ID = "589"
        const val PRODUCT_NAME = "TEST2"
        const val PRODUCT_DESCRIPTION = "description2"

        /**
         * Set Content-Type header to request
         */
        fun RequestSpecification.setContentTypeHeader(value: String): RequestSpecification =
            header(CONTENT_TYPE_HEADER, value)

        /**
         * Verify the response of Product details
         */
        fun ValidatableResponse.validateResponseProductDetails(): ValidatableResponse =
            statusCode(HttpStatus.SC_OK)
                .body("findAll { it.currency }.currency", CoreMatchers.notNullValue())
                .body("findAll { it.currency }.price", CoreMatchers.notNullValue())
                .body("findAll { it.currency }.id", CoreMatchers.notNullValue())
                .body("findAll { it.currency }.name", CoreMatchers.notNullValue())
                .body("findAll { it.currency }.description", CoreMatchers.notNullValue())

        /**
         * Verify the response of Product creation
         */
        fun ValidatableResponse.validateResponseProductCreation(
            productID: String,
            productName: String,
            productDescription: String
        ): ValidatableResponse =
            statusCode(HttpStatus.SC_OK)
                .body("id", CoreMatchers.`is`(productID))
                .body("name", CoreMatchers.`is`(productName))
                .body("description", CoreMatchers.`is`(productDescription))
                .body("_id", CoreMatchers.notNullValue())

        /**
         * Verify the response of Product detail
         */
        fun ValidatableResponse.validateResponseSpecificProductDetail(
            productID: String,
            productName: String,
            productDescription: String
        ): ValidatableResponse =
            statusCode(HttpStatus.SC_OK)
                .body("currency", CoreMatchers.notNullValue())
                .body("id", CoreMatchers.`is`(productID))
                .body("name", CoreMatchers.`is`(productName))
                .body("description", CoreMatchers.`is`(productDescription))

        /**
         * Verify the response of Product update
         */
        fun ValidatableResponse.validateResponseProductUpdate(
            productID: String,
            productName: String,
            productDescription: String
        ): ValidatableResponse =
            statusCode(HttpStatus.SC_OK)
                .body("id", CoreMatchers.`is`(productID))
                .body("name", CoreMatchers.`is`(productName))
                .body("description", CoreMatchers.`is`(productDescription))

    }
}
package org.example.tests.e2e

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.example.model.ProductService
import org.example.util.CommonConstants.TestTag.PIPELINE_1
import org.example.util.CommonConstants.TestTag.REGRESSION
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test

class ProductServiceTest : ProductService() {

    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1111 - Verify the success response of Product Detail`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            get(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateResponseProductDetails()
        }
    }
}
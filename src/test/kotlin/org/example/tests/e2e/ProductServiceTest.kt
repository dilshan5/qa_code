package org.example.tests.e2e

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.example.extensions.setJSONBody
import org.example.extensions.validateErrorResponse
import org.example.extensions.validateSuccessResponseHeaders
import org.example.model.ProductService
import org.example.util.CommonConstants.TestTag.PIPELINE_1
import org.example.util.CommonConstants.TestTag.REGRESSION
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductServiceTest : ProductService() {

    @Order(1)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1111 - Verify the success response of Creating a Product`() {
        Given {
            setContentTypeHeader("application/json")
            setJSONBody(
                """
                {
                  "id": "$PRODUCT_ID",
                  "name": "$PRODUCT_NAME",
                  "description": "$PRODUCT_DESCRIPTION"
                }
                """.trimIndent()
            )
        }  When {
            post(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateSuccessResponseHeaders()
            validateResponseProductCreation(PRODUCT_ID,PRODUCT_NAME,PRODUCT_DESCRIPTION)
        }
    }

    @Order(1)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1111 - Verify the HTTP 409 response for create duplicate product IDs`() {
        Given {
            setContentTypeHeader("application/json")
            setJSONBody(
                """
                {
                  "id": "$PRODUCT_ID",
                  "name": "$PRODUCT_NAME",
                  "description": "$PRODUCT_DESCRIPTION"
                }
                """.trimIndent()
            )
        }  When {
            post(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateErrorResponse(409,"Conflict product IDs found","Please check the product ID")
        }
    }

    @Order(2)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1112 - Verify the HTTP 400 response for invalid request body`() {
        Given {
            setContentTypeHeader("application/json")
            setJSONBody(
                """
                {
                  "id": "$PRODUCT_ID",
                  "name": "$PRODUCT_NAME",
                  "description": "$PRODUCT_DESCRIPTION",
                  "newKey": "value"
                }
                """.trimIndent()
            )
        }  When {
            post(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateErrorResponse(400,"Invalid Request Body","Please check the request body")
        }
    }

    @Order(3)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1113 - Verify the HTTP 415 response for invalid content type`() {
        Given {
            contentType(ContentType.URLENC)
            setJSONBody(
                """
                {
                  "id": "$PRODUCT_ID",
                  "name": "$PRODUCT_NAME",
                  "description": "$PRODUCT_DESCRIPTION"
                }
                """.trimIndent()
            )
        }  When {
            post(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateErrorResponse(415," Unsupported Media Type","Please check the request content type")
        }
    }

    @Order(4)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1114 - Verify the success response of Product Detail`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            get(GET_PRODUCT_RESOURCE_PATH)
        } Then {
            validateSuccessResponseHeaders()
            validateResponseProductDetails()
        }
    }

    @Order(5)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1115 - Verify the success response for get a single product by id`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            get("$GET_PRODUCT_RESOURCE_PATH/$PRODUCT_ID")
        } Then {
            validateSuccessResponseHeaders()
            validateResponseSpecificProductDetail(PRODUCT_ID,PRODUCT_NAME,PRODUCT_DESCRIPTION)
        }
    }

    @Order(6)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1116 - Verify the error response for get a invalid product by id`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            get("$GET_PRODUCT_RESOURCE_PATH/invalid")
        } Then {
            validateErrorResponse(400," Bad Request","Please check the product ID")
        }
    }

    @Order(7)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1117 - Verify the error response for invalid resource endpoint for product`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            get("/invalid")
        } Then {
            validateErrorResponse(404," Resource not found","Please check the URL endpoint")
        }
    }

    @Order(8)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1118 - Verify the success response for update a product by id`() {
        Given {
            setContentTypeHeader("application/json")
            setJSONBody(
                """
                {
                  "id": "$PRODUCT_ID",
                  "name": "$PRODUCT_NAME/updated",
                  "description": "$PRODUCT_DESCRIPTION"
                }
                """.trimIndent()
            )
        } When {
            put("$GET_PRODUCT_RESOURCE_PATH/$PRODUCT_ID")
        } Then {
            validateSuccessResponseHeaders()
            validateResponseProductUpdate(PRODUCT_ID, "$PRODUCT_NAME/updated",PRODUCT_DESCRIPTION)
        }
    }

    @Order(9)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1119 - Verify the error response for update a invalid product by id`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            put("$GET_PRODUCT_RESOURCE_PATH/invalid")
        } Then {
            validateErrorResponse(400," Bad Request","Please check the product ID")
        }
    }

    @Order(10)
    @Test
    @Tags(Tag(PIPELINE_1), Tag(REGRESSION))
    fun `IDE-1110 - Verify the error response for delete a invalid product by id`() {
        Given {
            setContentTypeHeader("application/json")
        } When {
            delete("$GET_PRODUCT_RESOURCE_PATH/invalid")
        } Then {
            validateErrorResponse(400," Bad Request","Please check the product ID")
        }
    }

    @AfterAll
    fun cleanUp(){
        Given {
            setContentTypeHeader("application/json")
        } When {
            delete("$GET_PRODUCT_RESOURCE_PATH/$PRODUCT_ID")
        } Then {
            validateSuccessResponseHeaders()
        }
    }
}
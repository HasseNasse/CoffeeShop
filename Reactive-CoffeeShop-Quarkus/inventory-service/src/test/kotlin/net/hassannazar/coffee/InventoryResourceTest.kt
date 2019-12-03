package net.hassannazar.coffee

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
open class InventoryResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/inventory")
          .then()
             .statusCode(200)
             .body(`is`("hello"))
    }

}
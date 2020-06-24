package io.hreem.coffeeshop.monolith.ordering.boundary.rest;

import io.hreem.coffeeshop.monolith.ordering.controller.internal.commands.OrderCommands;
import io.hreem.coffeeshop.monolith.ordering.domain.readobjects.Order;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class OrderResourceTest {

    final URI baseURI = new URI("http://localhost:8081/orders/");

    @InjectMock
    OrderCommands orderCommands;

    OrderResourceTest() throws URISyntaxException {
    }

    @Test
    public void postOrderEndpoint_created() {
        final var order = new Order();
        order.productCode = 1L;
        order.quantity = 1;
        Mockito.when(this.orderCommands.createOrder(Mockito.any(Order.class))).thenReturn(1L);

        given()
                .contentType(ContentType.JSON)
                .when().body(order)
                .post("/orders")
                .then()
                .statusCode(201)
                .header("Location", equalTo(this.baseURI
                        .resolve(String.valueOf(1L)).toString())
                );
    }

    @Test
    public void postOrderEndpoint_contraintViolated() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/orders")
                .then()
                .statusCode(400);

        given()
                .body("{}")
                .contentType(ContentType.JSON)
                .when()
                .post("/orders")
                .then()
                .statusCode(400);

        final var nonNullId = new Order();
        nonNullId.id = 1L;
        nonNullId.productCode = null;
        nonNullId.quantity = 0;

        given()
                .body(nonNullId)
                .contentType(ContentType.JSON)
                .when()
                .post("/orders")
                .then()
                .statusCode(400);
    }

}

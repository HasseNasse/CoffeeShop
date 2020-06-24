package io.hreem.coffeeshop.monolith;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="CoffeeShop API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Hreem IT",
                        url = "https://hreem.io",
                        email = "hassan.nazar@hreem.io"),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class CoffeeShopApplication extends Application {
}

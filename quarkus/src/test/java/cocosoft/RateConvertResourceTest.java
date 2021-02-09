package cocosoft;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RateConvertResourceTest {

    //@Test
    public void testHelloEndpoint() {
        given()
          .when().get("/rateconv?p1=1")
          .then()
             .statusCode(200)
             .body(is("[]"));
    }

}
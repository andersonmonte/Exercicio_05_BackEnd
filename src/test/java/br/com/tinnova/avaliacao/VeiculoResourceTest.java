package br.com.tinnova.avaliacao;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class VeiculoResourceTest {
    
	@Test
    public void testGetEndpoint() {
        given()
          .when().get("/veiculos")
          .then()
             .statusCode(200)
             .contentType(is("application/json"));
    }
	
	@Test
    public void testGetFindEndpoint() {
        given()
          .when().get("/veiculos/find")
          .then()
             .statusCode(200)
             .contentType(is("application/json"));
    }
}

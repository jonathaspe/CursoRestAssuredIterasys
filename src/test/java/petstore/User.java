package petstore;

import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class User {

    String uri = "https://petstore.swagger.io/v2/user";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @DisplayName("Cenário de criação de um usuário")
    @Test(priority = 1)
    public void incluirUsuario() throws IOException {
        String jsonBody = lerJson("src/test/resources/db/user1.json");

        String userId =
                given()
                        .contentType("application/json") //comum em API REST
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .statusCode(200)
                        .body("type", is("unknown"))
                        .body("code", is(200))
                        .extract()
                        .path("message");

        System.out.println("O User ID é " + userId);;
    }
}

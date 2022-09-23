//1 - Pacote
package petstore;

//2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

//3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço do endpoint "Pet"

    //3.2 - Métodos e Funções
    //Função para ler o Json que está no resources/db
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir novo registro - Create - Post
    @DisplayName("Cenário de criação de um pet")
    @Test(priority = 1) //Identifica o método ou a função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("src/test/resources/db/pet01.json");

        //Sintaxe Gherkin
        //Dado - Quando - Então (Given - When - Then)
        Integer token =
        given()
                .contentType("application/json") //comum em API REST
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .statusCode(200)
                .body("name", is("Buddy"))
                .body("category.name", is("cachorro"))
                .body("tags.name", contains("dog")) //o contains é usado para listas
                .body("status", is("available"))
            .extract()
                .path("id");

        System.out.println("O token é " + token);;
    }

    @DisplayName("Cenário de consulta de um pet")
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "2020";

        given()
                .contentType("application/json")
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200);
    }

}

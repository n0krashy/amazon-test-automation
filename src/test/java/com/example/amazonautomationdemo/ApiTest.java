package com.example.amazonautomationdemo;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @Test
    public void basicAuth(){
        String s = "{\n" +
                "  \"email\": \"merchant@foodics.com\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"token\": \"Lyz22cfYKMetFhKQybx5HAmVimF1i0xO\" \n}";
        given().contentType(ContentType.JSON)
                .body(s)
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login")
                .then().assertThat().statusCode(200)
                .and().extract().response().prettyPrint();
    }

    @Test
    public void wrongPasswordAuth(){
        String s = "{\n" +
                "  \"email\": \"merchant@foodics.com\",\n" +
                "  \"password\": \"13456\",\n" +
                "  \"token\": \"Lyz22cfYKMetFhKQybx5HAmVimF1i0xO\" \n}";
        given().contentType(ContentType.JSON)
                .body(s)
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login")
                .then().assertThat().statusCode(401);

    }

    @Test
    public void wrongEmailAuth(){
        String s = "{\n" +
                "  \"email\": \"merchant@fosssodics.com\",\n" +
                "  \"password\": \"13456\",\n" +
                "  \"token\": \"Lyz22cfYKMetFhKQybx5HAmVimF1i0xO\" \n}";
        given().contentType(ContentType.JSON)
                .body(s)
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login")
                .then().assertThat().statusCode(401);
    }

    @Test
    public void noContentType(){
        String s = "{\n" +
                "  \"email\": \"merchant@fosssodics.com\",\n" +
                "  \"password\": \"13456\",\n" +
                "  \"token\": \"Lyz22cfYKMetFhKQybx5HAmVimF1i0xO\" \n}";
        given()
                .body(s)
                .when()
                .post("https://pay2.foodics.dev/cp_internal/login")
                .then().assertThat().statusCode(415);
    }

}

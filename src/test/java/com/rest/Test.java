package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Test {

    @org.testng.annotations.Test
    public void validate_get_status_code(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8").
        when().
                    get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @org.testng.annotations.Test
    public void validate_response_body(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-645c1951c8092459ed5c6ac3-5f1328f28379a3b5aa9f78bd95f5915ec1").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace","gulev"),
                "workspaces.type", hasItems("personal","personal"),
                        "workspaces[0].name", equalTo("My Workspace"),
                        "workspaces[0].name", is(equalTo("My Workspace")),
                        "workspaces.size()", equalTo(2),
                        "workspaces.name", hasItem("gulev")
                );

    }

    @org.testng.annotations.Test
    public void extract_response(){
        Response res = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response();

        System.out.println("response: " + res.asString());
    }


    @org.testng.annotations.Test
    public void extract_single_value_from_response(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
        System.out.println("workspace name: " + name);
        //System.out.println("workspace name: " + JsonPath.from(res).getString("workspaces[0].name"));
        //System.out.println("workspace name: " + jsonPath.getString("workspaces[0].name"));
        //System.out.println("workspace name: " + res.path("workspaces[0].name"));

    }
}

package com.rest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Test {

    @org.testng.annotations.Test
    public void validate_get_status_code(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-645c1951c8092459ed5c6ac3-5f1328f28379a3b5aa9f78bd95f5915ec1").
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
                body("workspaces.name",hasItems("My Workspace","gulev"),
                "workspaces.type",hasItems("personal","personal"),
                        "workspaces[0].name",equalTo("My Workspace"),
                        "workspaces[0].name",is(equalTo("My Workspace")),
                        "workspaces.size()",equalTo(2),
                        "workspace.name",hasItem("gulev")
                );

    }

    public void extract_response(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-645c1951c8092459ed5c6ac3-5f1328f28379a3b5aa9f78bd95f5915ec1").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }
}

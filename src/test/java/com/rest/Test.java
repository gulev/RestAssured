package com.rest;

import com.sun.org.apache.xerces.internal.util.PropertyState;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
                        "workspaces[0].name", PropertyState.is(equalTo("My Workspace")),
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

    @org.testng.annotations.Test
    public void hamcrest_assert_on_extracted_response(){
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
        //assertThat(name, equalTo("My Workspace"));
        Assert.assertEquals(name, "My Workspace");
    }

    @org.testng.annotations.Test
    public void validate_response_body_hamcrest_learning(){
         given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8").
         when().
                get("/workspaces").
         then().
                log().all().
                assertThat().
                statusCode(200).
                body(
                        "workspaces.name", contains("My Workspace","gulev"),
                        "workspaces.name",is(not(empty())),
                        "workspaces.name", hasSize(2),
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry( "id", "e573297e-8f37-4cae-808d-381cc0372d25"),
                        "workspaces[0].name",allOf(startsWith("My"), containsString("Workspace"))
                );
    }
}

package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AutomatePost {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8");
        requestSpecBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validate_post_request_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myFirstWorkspace1\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
        when().
                post("/workspaces").
        then().assertThat().body("workspace.name",equalTo("myFirstWorkspace1"));
    }

    @org.testng.annotations.Test
    public void validate_post_request_not_bdd_style() {
        //if you want this to pass change the name to "something-that-you-want" because we already have
        //myFirstWorkspace3
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"myFirstWorkspace3\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Rest Assured created this\"\n" +
                "    }\n" +
                "}";

        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.path("workspace.name"),equalTo("myFirstWorkspace3"));
    }
}

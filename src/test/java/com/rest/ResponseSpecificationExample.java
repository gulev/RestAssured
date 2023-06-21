package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationExample {

    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8");
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void validate_status_code() {
        get("/workspaces").
                then().spec(responseSpecification).
                log().all();
    }

    @org.testng.annotations.Test
    public void validate_response_body() {
        Response response = get("/workspaces").
                then().spec(responseSpecification).
                log().all().
                extract().response();
        assertThat(response.path("workspaces[0].name"),equalTo("My Workspace"));
    }
}

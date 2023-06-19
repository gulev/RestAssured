package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestSpecificationExample {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        requestSpecification = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-6484d8fa58115f25366d9653-5cb2599c32b0c5f0c889823ee47aa8bcf8").
                log().all();
    }

    @org.testng.annotations.Test
    public void validate_status_code() {
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
    }

    @org.testng.annotations.Test
    public void validate_response_body() {
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"),equalTo("My Workspace"));
    }
}

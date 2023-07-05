package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestParameters {

    @org.testng.annotations.Test
    public void single_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                param("foo","bar").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @org.testng.annotations.Test
    public void multi_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                queryParam("foo","bar").
                queryParam("foo1","bar1").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @org.testng.annotations.Test
    public void multi_value_query_parameter(){
        given().
                baseUri("https://postman-echo.com").
                queryParam("foo","bar").
                queryParam("foo1","bar1,bar2,bar3").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @org.testng.annotations.Test
    public void path_parameter(){
        given().
                baseUri("https://reqres.in").
                pathParam("userId","2").
                log().all().
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }
}

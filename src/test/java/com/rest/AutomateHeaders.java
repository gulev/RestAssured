package com.rest;

import io.restassured.config.LogConfig;
import org.apache.http.Header;

import java.util.HashMap;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class AutomateHeaders {

    @org.testng.annotations.Test
    public void multiple_headers(){
        given().
                baseUri("https://a481d962-4054-4a8a-9c69-3e71848369d4.mock.pstmn.io/").
                header("header","value1").
                header("x-mock-match-request-headers","header").
        when().
                get("/get").
        then().
                log().all().
                assertThat().statusCode(200);
    }

    @org.testng.annotations.Test
    public void assert_response_headers() {
        given().
                baseUri("https://a481d962-4054-4a8a-9c69-3e71848369d4.mock.pstmn.io/").
                header("header", "value1").
                header("x-mock-match-request-headers", "header").
        when().
                get("/get").
        then().
                log().all().
                assertThat().statusCode(200).
                header("responseHeader","resValue1");

    }

    @org.testng.annotations.Test
    public void extract_response_headers() {
        given().
                baseUri("https://a481d962-4054-4a8a-9c69-3e71848369d4.mock.pstmn.io/").
                header("header", "value1").
                header("x-mock-match-request-headers", "header").
                when().
                get("/get").
                then().
                log().all().
                assertThat().statusCode(200).
                extract().
                headers();

    }
}

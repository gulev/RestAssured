package com.rest;

import com.rest.pojo.simple.SimplePojo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimplePOJOTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://a481d962-4054-4a8a-9c69-3e71848369d4.mock.pstmn.io")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @org.testng.annotations.Test
    public void sample_pojo_example() {
        SimplePojo simplePojo = new SimplePojo("value1","value2");

        given().
                body(simplePojo).
        when().
                post("/post").
        then().spec(responseSpecification).
                assertThat().
                body("key1", equalTo(simplePojo.getKey1()),
                "key2", equalTo(simplePojo.getKey2()));

    }
}

package com.rest;

import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
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

public class AutomatePost {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key",
                "PMAK-64c04b1f255e89003f0d653c-560d649cebc43700750efcb30f0276fe87");
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
        File file = new File("src/main/resources/CreateWorkspacePayload.json");

        Response response = with().
                body(file).
                post("/workspaces");
        assertThat(response.path("workspace.name"),equalTo("mySecondWorkspace"));
    }



    @org.testng.annotations.Test
    public void create_workspace_using_pojo() {
        Workspace workspace = new Workspace("jijibiji","personal","desc");
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);
        WorkspaceRoot deserializedWorkspaceRoot =  given().
                body(workspaceRoot).
        when().
                post("/workspaces").
        then().
                spec(responseSpecification).
                extract().
                response().
                as(WorkspaceRoot.class);

        assertThat(deserializedWorkspaceRoot.getWorkspace().getName(),
                equalTo(workspaceRoot.getWorkspace().getName()));
    }
}

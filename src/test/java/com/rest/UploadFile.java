package com.rest;

import java.io.*;

import static io.restassured.RestAssured.given;

public class UploadFile {

    @org.testng.annotations.Test
    public void upload_file_multipart_form_data(){
        String attrubutes = "{\"name\": \"temp.txt\", \"parent:{\"id\":\"123456\"}\"}";

        given().
                baseUri("https://postman-echo.com").
                multiPart("file", new File("temp.txt")).
                multiPart("attributes", attrubutes,"application/json").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }


    @org.testng.annotations.Test
    public void download_file() throws IOException {
        byte[] bytes =
        given().
                baseUri("https://raw.githubusercontent.com").
                multiPart("file", new File("temp.txt")).
                log().all().
        when().
                get("/appium-boneyard/sample-code/master/sample-code/apps/ApiDemos/bin/ApiDemos-debug.apk").
                then().
                log().all().
                extract().response().asByteArray();

        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk")) ;
        os.write(bytes);
        os.close();
    }
}

package com.OAuthPractice.tests;

import com.OAuthPractice.pojo.AddPlace;
import com.OAuthPractice.pojo.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    @Test
    public void specBuilderTest() {
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        ResponseSpecification res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        AddPlace place = new AddPlace();

        List<String> types = new ArrayList<>();
        types.add("shoe");
        types.add("shoe park");

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        place.setAccuracy(50);
        place.setAddress("29, side layout, cohen 09");
        place.setLanguage("French-IN");
        place.setPhone_number("(+91) 983 893 3937");
        place.setWebsite("www.google.com");
        place.setName("House of Death");
        place.setTypes(types);
        place.setLocation(location);

        RequestSpecification request =
                given().log().all().spec(req)
                        .body(place);

        Response response = request.when().post("/maps/api/place/add/json")
        .then().assertThat().spec(res).extract().response();

        String resp = response.asString();
        System.out.println(resp);
    }
}

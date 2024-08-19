package com.OAuthPractice.tests;

import com.OAuthPractice.pojo.AddPlace;
import com.OAuthPractice.pojo.Location;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {
    @Test
    public void serializationTest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
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

        Response response =
                given().log().all().queryParam("key", "qaclick123")
                        .body(place)
                        .when().post("/maps/api/place/add/json")
                        .then().assertThat().statusCode(200).extract().response();
        String res = response.asString();
        System.out.println(res);
    }
}

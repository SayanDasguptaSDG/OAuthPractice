package com.OAuthPractice.tests;

import com.OAuthPractice.pojo.GetCourse;
import com.OAuthPractice.pojo.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    @Test
    public void OAuth_Test() {
        String[] courses = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        String response =
        given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
        .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String access_token = js.get("access_token");

        GetCourse result =
                given()
                .queryParams("access_token", access_token).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);


        System.out.println(result.getLinkedIn());
        List<WebAutomation> webAutomationCourses = result.getCourses().getWebAutomation();
        for (WebAutomation webAutomationCourse : webAutomationCourses) {
            if (webAutomationCourse.getCourseTitle().equalsIgnoreCase("Cypress")) {
                System.out.println(webAutomationCourse.getPrice());
                break;
            }
        }
        List<String> expectedListOfWebAutomationCourses = Arrays.asList(courses);
        ArrayList<String> actualListOfWebAutomationCourses = new ArrayList<String>();
        for (WebAutomation webAutomationCourse : webAutomationCourses) {
            actualListOfWebAutomationCourses.add(webAutomationCourse.getCourseTitle());
        }
        Assert.assertTrue(actualListOfWebAutomationCourses.equals(expectedListOfWebAutomationCourses));


    }
}

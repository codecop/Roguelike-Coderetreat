package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.codecop.rogue.template.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import spark.Spark;

class HelloAppTest {

    @BeforeAll
    public static void startApplication() {
        Main.createApp();
    }

    @AfterAll
    public static void stopApplication() {
        Spark.stop();
    }

    @Test
    public void firstHello() {
        JsonPath json = getHello();
        String name = json.get("name");
        assertEquals("World!", name);
    }

//    @Test
//    public void firstLayout() {
//        JsonPath json = getRoomLayout();
//        String layout = json.get("layout");
//        assertEquals(
//                "#######\\n" +
//                "#@    #\\n" +
//                "|     #\\n" +
//                "#     #\\n" +
//                "#######\\n",
//                layout);
//    }

    private JsonPath getHello() {
        return given().
                // params(paramName, paramValue, otherParamPairs).
            when(). //
                get("/hello"). //
            then(). //
                assertThat().statusCode(200). //
                assertThat().contentType("application/json"). //
                extract().jsonPath();
    }

    private RequestSpecification given() {
        return RestAssured.given(). //
                accept("application/json"). //
                port(Main.PORT);
    }

    @Test
    public void zupdate() {
        given().
            contentType(ContentType.JSON).body("{ \"name\":\"Peter\" }"). //
        when(). //
            post("/hello"). //
        then(). //
            assertThat().statusCode(201);

        JsonPath json = getHello();
        String name = json.get("name");
        assertEquals("Peter", name);
    }

//    @Test
//    public void updatePlayerPosition() {
//        given().
//                contentType(ContentType.JSON). //
//                when(). //
//                post("/ohgod/walk?row=3&column=5"). //
//                then(). //
//                assertThat().statusCode(201);
//
//        JsonPath json = getRoomLayout();
//        String layout = json.get("layout");
//        assertEquals(
//                        "#######\\n" +
//                        "#     #\\n" +
//                        "|     #\\n" +
//                        "#    @#\\n" +
//                        "#######\\n",
//                layout);
//    }

    private JsonPath getRoomLayout() {
        return given().
                // params(paramName, paramValue, otherParamPairs).
                when(). //
                        get("/ohgod"). //
                then(). //
                        assertThat().statusCode(200). //
                        assertThat().contentType("application/json"). //
                        extract().jsonPath();
    }

}

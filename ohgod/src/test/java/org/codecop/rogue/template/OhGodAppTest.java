package org.codecop.rogue.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import spark.Spark;

class OhGodAppTest {

    @BeforeAll
    public static void startApplication() {
        Main.createApp();
    }

    @AfterAll
    public static void stopApplication() {
        Spark.stop();
    }

    @Test
    public void firstLayout() {
        JsonPath json = getRoomLayout();
        String layout = json.get("layout");
        assertEquals(
                "#######\n" +
                "#@    #\n" +
                "|     #\n" +
                "#     #\n" +
                "#######\n",
                layout);
    }

    private RequestSpecification given() {
        return RestAssured.given(). //
                accept("application/json"). //
                port(Main.PORT);
    }


    @Test
    public void updatePlayerPosition() {
        given().
                contentType(ContentType.JSON). //
                when(). //
                post("/ohgod/walk?row=3&column=5"). //
                then(). //
                assertThat().statusCode(201);

        JsonPath json = getRoomLayout();
        String layout = json.get("layout");
        assertEquals("#######\n#     #\n|     #\n#    @#\n#######\n", layout);
    }

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

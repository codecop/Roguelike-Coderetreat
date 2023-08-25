package org.codecop.rogue.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import spark.Spark;

public class InventoryAppTest {

    @BeforeAll
    public static void startApplication() {
        Main.createApp();
    }

    @AfterAll
    public static void stopApplication() {
        Spark.stop();
    }

    @Test
    public void allTestsInOne() {
        testEmptyInventory();
        
        testAddToInventory();
        
        testGetBow();
        assertNowGetSword();
        
        testRemove();
    }

    private void testEmptyInventory() {
        JsonPath json = getInventory();
        assertJsonEmptyList(json);
    }

    private JsonPath getInventory() {
        return given().
                // params(paramName, paramValue, otherParamPairs).
            when(). //
                get("/inventory"). //
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

    private void assertJsonEmptyList(JsonPath json) {
        assertTrue(json.getList("").isEmpty());
    }
    
    private void testAddToInventory() {
        createBow();
        JsonPath json = getInventory();
        assertJsonBowInInventory(json);
    }
    
    private void createBow() {
        given().
            contentType(ContentType.JSON).
            body("{ \"item\":\"(\", \"description\":\"a bow\" }"). //
        when(). //
            post("/inventory"). //
        then(). //
            assertThat().statusCode(201);
    }
    
    private void assertJsonBowInInventory(JsonPath json) {
        List<Object> items = json.getList("");
        assertEquals(1, items.size());
        
        String item = json.getString("[0].item");
        assertEquals("(", item);
        
        String description = json.getString("[0].description");
        assertEquals("a bow", description);
    }

    private void testGetBow() {
        JsonPath json = getBow();
        assertJsonBow(json);
    }

    private void assertJsonBow(JsonPath json) {
        String item = json.getString("item");
        assertEquals("(", item);
        String description = json.getString("description");
        assertEquals("a bow", description);
    }
    
    private JsonPath getBow() {
        return getItem("(").
                assertThat().statusCode(200). //
                extract().jsonPath();
    }
    
    private ValidatableResponse getItem(String item) {
        return given().
            when(). //
                get("/inventory/"+item). //
            then(). // 
                assertThat().contentType("application/json");
    }

    private void assertNowGetSword() {
        getItem("x").
            assertThat().statusCode(404);
    }

    private void testRemove() {
        given().
        when(). //
            delete("/inventory/"+"("). //
        then(). //
            assertThat().statusCode(202);
        
        getItem("(").
            assertThat().statusCode(404);
    }
    
}

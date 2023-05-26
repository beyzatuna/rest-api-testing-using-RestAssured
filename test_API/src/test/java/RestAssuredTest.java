import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";
    private static final String NEW_USER_NAME = "John Doe";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetAllUsers() {
        Response response = RestAssured.get(USERS_ENDPOINT);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

    }

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"" + NEW_USER_NAME + "\" }";

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(USERS_ENDPOINT);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Status code is not 201");

    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testGetNewUserDetails() {
        Response response = RestAssured.get(USERS_ENDPOINT + "/1");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

    }

    @Test(dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        Response response = RestAssured.delete(USERS_ENDPOINT + "/1");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code is not 200");

    }
}

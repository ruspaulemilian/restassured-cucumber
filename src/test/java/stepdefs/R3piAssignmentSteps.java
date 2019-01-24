package stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.Post;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import utils.EmailValidator;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsEqual.equalTo;


public class R3piAssignmentSteps {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;
    private static String ENDPOINT_MAIN_APPLICATION = "https://jsonplaceholder.typicode.com";
    private static String ENDPOINT_POSTS_APPLICATION = ENDPOINT_MAIN_APPLICATION + "/posts";


    @Given("^The application is up and running$")
    public void verifyApplicationIsUp() {
        request = given().request();
        response = request.when().get(ENDPOINT_MAIN_APPLICATION);
        json = response.then().statusCode(200);
    }

    @When("^I get an existing user '([^']*)'$")
    public void iGetAnExistingUserUsers(String user) {
        response = request.when().get(ENDPOINT_MAIN_APPLICATION + user);
    }

    @When("^I print the user address from the response$")
    public void iPrintASpecificDetailFromTheResponse() {
        Map<String, String> address = response.jsonPath().getMap("address");
        for (Map.Entry<String, String> entry : address.entrySet()) {
            address.put(entry.getKey(), entry.getValue());
            System.out.println("The desired details about the user " + entry);

        }
    }

    @When("^I get all the associated posts of the user '([^']*)'$")
    public void iGetAllTheAssociatedPostsOfTheUserUsers(String post) {
        response = request.when().get(ENDPOINT_MAIN_APPLICATION + post);
    }

    @When("^I perform a post using the same userID with a valid title and body$")
    public void iPerformAPostUsingSameUserIDUsersWithAValidTitleAndBody() {
        Post post = new Post();
        post.setUserId(1);
        post.setId(1);
        post.setTitle("My test");
        post.setBody("This is a test");
        response = given() .contentType(ContentType.JSON)
                .body(post).
                when().post(ENDPOINT_POSTS_APPLICATION);
    }

    @Then("the status code is (\\d+)")
    public void verify_status_code(int statusCode) {
        json = response.then().statusCode(statusCode);
    }

    @Then("^The email address it`s in the right format$")
    public void theEmailAddressItSInTheRightFormat() {
        String emailAddress = response.jsonPath().get("email");
        EmailValidator emailValidator = new EmailValidator();
        assertTrue(emailValidator.validate(emailAddress));
    }


    @Then("The response includes the following$")
    public void response_equals(Map<String, String> responseFields) {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            } else {
                json.body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }


}




import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NegativeTests {
    public static Response response;

    @Test
    public void noAccessKeyTest() {
        response = given().get(Constans.URL + Constans.LIST);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(101));
        response.then().body("error.type", containsString("missing_access_key"));
    }
    @Test
    public void notSupportedCurrentSubscriptionPlanTest() {
        response = given().get(Constans.URL + Constans.CONVERT + Constans.TOKEN + "&from=EUR&to=RUB&amount=100");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(105));
        response.then().body("error.info", containsString("Your current Subscription Plan does not support this API Function."));
    }

    @Test
    public void historicalNoDateTest() {

        response = given().get(Constans.URL + Constans.HISTORICAL + Constans.TOKEN + "&date=");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(301));
        response.then().body("error.info", containsString("You have not specified a date"));
    }
    @Test
    public void historicalInvalidDateTest() {

        response = given().get(Constans.URL + Constans.HISTORICAL + Constans.TOKEN + "&date=2023-02-20");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(302));
        response.then().body("error.info", containsString("You have entered an invalid date"));
    }

    @Test
    public void invalidCurrencyCodeTest() {
        String currencies = "BUB";
        response = given().get(Constans.URL + Constans.LIVE + Constans.TOKEN + "&currencies=" + currencies);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", containsString("You have provided one or more invalid Currency Codes"));
    }
    @Test
    public void APINotExistErrorTest() {
        String currencies = "RUB";
        response = given().get(Constans.URL + "/stock" + Constans.TOKEN + "&currencies=" + currencies);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(103));
        response.then().body("error.info", containsString("This API Function does not exist"));
    }
}



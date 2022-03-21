import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class NegativeTests {
    public static Response response;

//    No Valid Access Key Tests
    @Test
    public void noAccessKeyListTest() {
        response = given().get(Constans.URL + Constans.LIST);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(101));
        response.then().body("error.type", containsString("missing_access_key"));
    }

    @Test
    public void noAccessKeyHistoryTest() {
        response = given().get(Constans.URL + Constans.HISTORICAL + "&date=2022-02-20");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(101));
        response.then().body("error.type", containsString("missing_access_key"));
    }

    @Test
    public void noAccessKeySpecCurrencyRatesTest() {
        response = given().get(Constans.URL + Constans.LIVE + "&currencies=CAD,EUR,NIS,RUB");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(101));
        response.then().body("error.type", containsString("missing_access_key"));
    }

    //    Error 105 Test
    @Test
    public void notSupportedCurrentSubscriptionPlanTest() {
        response = given().get(Constans.URL + Constans.CONVERT + Constans.TOKEN + "&from=EUR&to=RUB&amount=100");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(105));
        response.then().body("error.info", containsString("Your current Subscription Plan does not support this API Function."));
    }

    //    Error 301 Test
    @Test
    public void historicalNoDateTest() {

        response = given().get(Constans.URL + Constans.HISTORICAL + Constans.TOKEN + "&date=");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(301));
        response.then().body("error.info", containsString("You have not specified a date"));
    }

    //    Error 302 Test
    @Test
    public void historicalInvalidDateTest() {

        response = given().get(Constans.URL + Constans.HISTORICAL + Constans.TOKEN + "&date=2023-02-20");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(302));
        response.then().body("error.info", containsString("You have entered an invalid date"));
    }

    //    Error 202 Test
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

    //    Error 103 Test
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



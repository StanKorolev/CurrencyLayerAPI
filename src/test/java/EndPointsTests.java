import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class EndPointsTests {

    public static Response response;

    @Test
    public void availableCurrencyListTest() {

        response = given().get(Constans.URL + Constans.LIST + Constans.TOKEN);
        System.out.println(response.asString());
        response.then().statusCode(200);
    }

    @Test
    public void liveRatesTest() {

        response = given().get(Constans.URL + Constans.LIVE + Constans.TOKEN);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", containsString("https:"));
        response.then().body("privacy", containsString("https:"));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().statusCode(200);
    }


    @Test
    public void liveRateSpecificCurrencyTest() {
        String currencies = "CAD,EUR,NIS,RUB";
        response = given().get(Constans.URL + Constans.LIVE + Constans.TOKEN + "&currencies=" + currencies);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().statusCode(200);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-02-12", "2022-02-20", "2021-12-12"})
//    Free plan limitation doesn't allow creating multi requests simultaneously but did it for future.
    public void historicalRatesTest(String date) {

        response = given().get(Constans.URL + Constans.HISTORICAL + Constans.TOKEN + "&date=" + date);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("date", equalTo(date));
    }

    @Test
    public void convertCurrencyTest() {
        HashMap currency = new HashMap();
        currency.put(Constans.EUR, Constans.RUB);
        currency.put(Constans.USD, Constans.RUB);
        currency.put(Constans.AUD, Constans.RUB);

        Response response = given().contentType("application/json").body(currency + "&amount=100").get(Constans.URL + Constans.CONVERT + Constans.TOKEN);
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
    }

}

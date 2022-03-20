public class Constans {

//    MAIN SETUp
    public static final String URL = "http://api.currencylayer.com";
    public static final String TOKEN = "?access_key=1fc537023d04cc7329a999dadf5bff16";

// ENDPOINTS
    // Most recent exchange rates
    public static final String LIST = "/list";
    // Spec date to see history ?date=YYYY-MM-DD
    public static final String HISTORICAL = "/historical";
    // Convert one to another ?from=EUR&to=GBP&amount=100
    public static final String CONVERT = "/convert";
    // Rates for specific period of time ?start_date=2015-01-01&end_date=2015-05-01
    public static final String TIME_FRAME = "/timeframe";
    // Request any currency's change parameters (margin, percentage) ?currencies=USD,EUR
    public static final String CHANGE = "/change";
    // Get the most recent exchange rate data
    public static final String LIVE = "/live";

//CURRENCY
    public static final String EUR = "EUR";
    public static final String GBP = "GBP";
    public static final String USD = "USD";
    public static final String RUB = "RUB";
    public static final String AUD = "AUD";
}

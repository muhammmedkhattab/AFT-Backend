package helper;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonHelper {
    public static String getResponseValue(Response responseBody, String getItemValue)  {
        JsonPath jsonPathValidator = responseBody.jsonPath();
        String jsonValue = jsonPathValidator.get(getItemValue).toString();
        return jsonValue;
    }

}

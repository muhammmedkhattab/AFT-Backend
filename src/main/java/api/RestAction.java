package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Pet;
import org.apache.http.params.CoreConnectionPNames;
import org.awaitility.Awaitility;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class RestAction {

  String IMAGES_SRC = "/src/test/resources/images/";
  String IMAGE_NAME = "photo";
  String IMAGE_JPEG_EXTENSION = ".jpeg";
  RequestSpecification requestSpecification =
      given().config(getRestAssuredConfig()).filter(new AllureRestAssured());

  public Response getRequest(String requestURI, int statusCode) {

    //    //
    RequestSpecification requestSpecification =
        given().config(getRestAssuredConfig()).filter(new AllureRestAssured());
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;
  }

  private RestAssuredConfig getRestAssuredConfig() {
    RestAssuredConfig newConfig =
        RestAssured.config()
            .httpClient(
                HttpClientConfig.httpClientConfig()
                    .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000)
                    .setParam(CoreConnectionPNames.SO_TIMEOUT, 10000));
    return newConfig;
  }

  public Response postRequest(String requestURI, Pet requestBody, int statusCode, boolean update) {
    Awaitility.await().atMost(10, TimeUnit.SECONDS).pollInterval(7, TimeUnit.SECONDS);
    RequestSpecification requestSpecificationWithBody = RequestBuilder(requestBody);
    if (update) {
      requestSpecificationWithBody.contentType(ContentType.URLENC);
    }
    Response response = requestSpecificationWithBody.post(requestURI);
    RequestBuilder(response, requestSpecificationWithBody, statusCode);
    return response;
  }

  public Response postImageRequest(String requestURI, int statusCode) {

    RequestSpecification requestSpecification =
        given()
            .multiPart(
                "file",
                new File(
                    System.getProperty("user.dir")
                        + IMAGES_SRC
                        + IMAGE_NAME
                        + IMAGE_JPEG_EXTENSION))
            .filter(new AllureRestAssured());
    Response response = requestSpecification.post(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;
  }

  private RequestSpecification RequestBuilder(Pet requestBody) {
    RequestSpecification requestSpecification =
        given().config(getRestAssuredConfig()).body(requestBody).filter(new AllureRestAssured());
    requestSpecification.contentType(ContentType.JSON);
    return requestSpecification;
  }

  private String RequestBuilder(
      Response response, RequestSpecification requestSpecification, int statusCode) {
    response.then().assertThat().statusCode(statusCode).extract().response();
    requestSpecification.contentType(ContentType.JSON);
    return response.body().prettyPrint();
  }

  public Response putRequest(String requestURI, Pet requestBody, int statusCode) {
    RequestSpecification requestSpecificationWithBody = RequestBuilder(requestBody);
    Response response = requestSpecificationWithBody.put(requestURI);
    RequestBuilder(response, requestSpecificationWithBody, statusCode);
    return response;
  }

  public Response deleteRequest(String requestURI, int statusCode) {
    Awaitility.await().atMost(10, TimeUnit.SECONDS).pollInterval(7, TimeUnit.SECONDS);
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;
  }

  public Response getAnyResponseByID(String requestURI, String key, long id, int statusCode) {
    RequestSpecification requestSpecification =
        given().config(getRestAssuredConfig()).param(key, id);
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);

    return response;
  }
}

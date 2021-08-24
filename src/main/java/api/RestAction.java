package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Pet;

import java.io.File;

import static io.restassured.RestAssured.given;

public class RestAction {

  String IMAGES_SRC="/src/test/resources/images/";
  String IMAGE_NAME="photo";
  String IMAGE_JPEG_EXTENSION=".jpeg";
  RequestSpecification requestSpecification = given().filter(new AllureRestAssured());

  public Response getRequest(String requestURI, int statusCode) {
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;
  }

  public Response postRequest(String requestURI, Pet requestBody, int statusCode, boolean update) {
    RequestSpecification requestSpecificationWithBody = RequestBuilder(requestBody);
    if (update) {
      requestSpecificationWithBody.contentType(ContentType.URLENC);
    }
    Response response = requestSpecificationWithBody.post(requestURI);
    RequestBuilder(response, requestSpecificationWithBody, statusCode);
    return response;
  }

  public Response postImageRequest(String requestURI,int statusCode) {

    RequestSpecification requestSpecification = given().multiPart("file", new File(System.getProperty("user.dir")+IMAGES_SRC+IMAGE_NAME+IMAGE_JPEG_EXTENSION));
    Response response = requestSpecification.post(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;

  }

  private RequestSpecification RequestBuilder(Pet requestBody) {

    RequestSpecification requestSpecification =
        given().body(requestBody).filter(new AllureRestAssured());
    requestSpecification.contentType(ContentType.JSON);
    return requestSpecification;
  }

  private String RequestBuilder(Response response, RequestSpecification requestSpecification, int statusCode) {
    requestSpecification.contentType(ContentType.JSON);
    response.then().assertThat().statusCode(statusCode).extract().response();
    return response.body().prettyPrint();
  }

  public Response putRequest(String requestURI, Pet requestBody, int statusCode) {
    RequestSpecification requestSpecificationWithBody = RequestBuilder(requestBody);
    Response response = requestSpecificationWithBody.put(requestURI);
    RequestBuilder(response, requestSpecificationWithBody, statusCode);
    return response;
  }

  public Response deleteRequest(String requestURI, int statusCode) {
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);
    return response;
  }

  public Response getAnyResponseByID(String requestURI, String key, long id, int statusCode) {
    RequestSpecification requestSpecification = given().param(key, id);
    Response response = requestSpecification.get(requestURI);
    RequestBuilder(response, requestSpecification, statusCode);

    return response;
  }
}

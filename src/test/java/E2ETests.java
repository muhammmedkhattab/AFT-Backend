import api.RestAction;
import helper.JsonHelper;
import io.qameta.allure.*;
import io.restassured.response.Response;
import model.Pet;
import model.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.Endpoint;

public class E2ETests extends TestBase {
  Response response;

  /**
   * This E2E flow will not work as there are 2 main issues :
   *
   *    1-API not reliable as in the Get request the id is found in the first request and not found in the following request
   *    Verified also that Swagger have this issue, added Video in the Email.
   *
   *    2-API is not reflected immediately so we need to add wait time and shouldn't be the case for a simple get and post request
   *    and I don't know the SLA to test against ,finally didn't want to use Thread.sleep
   * */
  @Description("User add new Pet to the store then find it by ID then Edit the status and name Then Delete Pet")
  @Story("ZoPlus-E2E-001")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io")
  @Test(groups = {"PetsIntegration"})
  void userAdd_Edit_Delete_Get_PetTest() {
    JsonHelper jsonHelper = new JsonHelper();
    Pet pet = petObjectPreparation();
    RestAction restAction = new RestAction();
    String id;
    //Create a new Pet
    response = restAction.postRequest(endpoint, pet, 200, false);
    id=jsonHelper.getResponseValue(response, "id");

    //get the new Pet by ID
    endpoint = Endpoint.getBaseURI(id);
    response = restAction.getRequest(endpoint, 200);

    //Update Pet Data (Name and Status)
    response = restAction.postRequest(endpoint, updatePetObject(pet, "Tokki",Status.pending),200, true);
    Assert.assertEquals(JsonHelper.getResponseValue(response, "code"), "200");
    response = restAction.getRequest(endpoint, 200);

    //delete the Pet
    response = restAction.deleteRequest(endpoint, 200);
    Assert.assertEquals(JsonHelper.getResponseValue(response, "code"), "200");
  }

}

import api.RestAction;
import io.qameta.allure.*;
import io.restassured.response.Response;
import model.Pet;
import model.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import helper.JsonHelper;
import utilities.Endpoint;


public class PetsTests extends TestBase {
  Response response;

  @DataProvider(name = "status-dataset")
  public static Object[][] getStatusData() {
    String available=Status.available.toString();
    String pending=Status.pending.toString();
    String sold=Status.sold.toString();
    return new Object[][] {{available}, {pending}, {sold}};
  }
  @Description("User upload an Image to the Pet")
  @Story("ZoPlus-01")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/uploadFile")
  @Test(groups = {"PetsRegression"})
  void userUploadPetImageSuccessfully() {
    RestAction restAction = new RestAction();
    endpoint = Endpoint.getBaseURI("9223372000666033000"+"/uploadImage");
    response = restAction.postImageRequest(endpoint,200);
    Assert.assertEquals(JsonHelper.getResponseValue(response, "code"), "200");
  }

  @Description("User add new Pet to the store")
  @Story("ZoPlus-02")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/addPet")
  @Test(groups = {"PetsRegression"})
  void userAddNewPetToStoreSuccessfully() {
    Pet pet = petObjectPreparation();
    RestAction restAction = new RestAction();
    response = restAction.postRequest(endpoint, pet, 200, false);
  }

  @Description("Update an existing pet")
  @Story("ZoPlus-03")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/updatePet")
  @Test(groups = {"PetsRegression"})
  void userUpdateExistingPet() {
    RestAction restAction = new RestAction();
    Pet pet = petObjectPreparation();
    String endpoint = Endpoint.getBaseURI();
    response = restAction.putRequest(endpoint,pet,200);
  }

  @Description("User Finds Pets by status")
  @Story("ZoPlus-04")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/findPetsByStatus")
  @Test(dataProvider = "status-dataset")
  void userFiltersPetsSuccessfullyByStatus(String statusName) {
    String endpoint = Endpoint.getBaseURI(findByStatusUrl + statusName);
    RestAction restAction = new RestAction();
    response = restAction.getRequest(endpoint, 200);
  }

  @Description("User Find pet by ID")
  @Story("ZoPlus-05")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/getPetById")
  @Test(groups = {"PetsRegression"},timeOut = 10000)
  void userFindPetByIdSuccessfully() throws InterruptedException {
    Response response ;
    String id;
    String endpoint = Endpoint.getBaseURI();
    RestAction restAction = new RestAction();
    JsonHelper jsonHelper = new JsonHelper();
    Pet pet = petObjectPreparation();

    response = restAction.postRequest(endpoint, pet, 200, false);

    id=jsonHelper.getResponseValue(response, "id");

    endpoint = Endpoint.getBaseURI(id);
    restAction.getRequest(endpoint, 200);

  }

/*  @Description("User Find pet by ID")
  @Story("ZoPlus-06")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/getPetById")
  @Test(groups = {"PetsRegression"})
  void userSearchForAnyPetWithAnyID() {
    String key = "";
    RestAction restAction = new RestAction();
    String endpoint = Endpoint.getBaseURI("4223372000666033242");
    response = restAction.getAnyResponseByID(endpoint, key, id, 200);
  }*/

  @Description("User Updates a pet in the store with form data")
  @Story("ZoPlus-07")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/updatePetWithForm")
  @Test(groups = {"PetsRegression"})
  void userUpdatePetByIdSuccessfully() throws InterruptedException {
    RestAction restAction = new RestAction();
    Pet pet = petObjectPreparation();

/**
  due to flaky behavior can't depend on the chain unless I used thread.sleep()
*/
    //  JsonHelper jsonHelper = new JsonHelper();
    //  userAddNewPetToStoreSuccessfully();
    //  String id=jsonHelper.getResponseValue(response, "id");

    String endpoint = Endpoint.getBaseURI("7308227435999201729");
    response = restAction.postRequest(endpoint, updatePetObject(pet, "Tokki",Status.pending),200, true);
    Assert.assertEquals(JsonHelper.getResponseValue(response, "code"), "200");
  }

  @Description("User Finds Pets by pending status")
  @Story("ZoPlus-08")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/findPetsByStatus")
  @Test()
  void userFiltersPetsByStatusPendingSuccessfully() {
    endpoint = Endpoint.getBaseURI(findByStatusUrl + Status.pending);
    RestAction restAction = new RestAction();
    response = restAction.getRequest(endpoint, 200);
    JsonHelper jsonHelper = new JsonHelper();
    String status=jsonHelper.getResponseValue(response, "status[0]");
    Assert.assertEquals(status,"pending");

  }

  @Description("User Delete a pet")
  @Story("ZoPlus-07")
  @Epic("Regression")
  @Feature("Pets Feature")
  @Link("https://petstore.swagger.io/#/pet/deletePet")
  @Test(groups = {"PetsRegression"})
  void userDeletePetByIdSuccessfully() {
    RestAction restAction = new RestAction();
    JsonHelper jsonHelper = new JsonHelper();
    long randomIndex= faker.number().numberBetween(0, 20);
    String id;
    String deletedId;

    userFiltersPetsByStatusPendingSuccessfully();

    id=jsonHelper.getResponseValue(response, "id["+randomIndex+"]");
    endpoint = Endpoint.getBaseURI(id);
    response = restAction.deleteRequest(endpoint, 200);

    Assert.assertEquals(Status.pending.toString(),"pending");

    deletedId=jsonHelper.getResponseValue(response, "id");

    Assert.assertEquals(jsonHelper.getResponseValue(response, "status"), "pending");
    Assert.assertEquals(id,deletedId);
  }

}

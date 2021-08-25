import com.github.javafaker.Faker;
import model.*;
import org.testng.annotations.BeforeMethod;
import helper.PropertyFileHelper;
import utilities.Endpoint;

import java.util.Arrays;

public class TestBase {
  static String endpoint = Endpoint.getBaseURI();
  String name;
  String categoryName;
  String tagName;
  String photoUrl;
  String findByStatusUrl;
  Faker faker = new Faker();
  long id;

  @BeforeMethod()
  void init() {
    dataPreparation();

  }

  void dataPreparation() {
    id = faker.number().randomNumber(19, true);
    name = PropertyFileHelper.LoadTestDataFile.getProperty("name");
    categoryName = PropertyFileHelper.LoadTestDataFile.getProperty("categoryName");
    tagName = PropertyFileHelper.LoadTestDataFile.getProperty("tagName");
    photoUrl = PropertyFileHelper.LoadTestDataFile.getProperty("photoUrl");
    findByStatusUrl = PropertyFileHelper.LoadTestDataFile.getProperty("findByStatusUrl");
  }

  Pet petObjectPreparation() {
    Category category = new Category();
    Pet pet = new Pet();
    Tag tag = new Tag();
    pet.setId(id);
    pet.setName(name);
    pet.setStatus(Status.available);
    pet.setCategory(category);
    pet.setPhotosUrls(Arrays.asList(photoUrl));
    pet.setTags(Arrays.asList(tag));

    category.setId(id);
    category.setName(categoryName);

    tag.setId(id);
    tag.setName(tagName);
    return pet;
  }

  Pet updatePetObject(Pet pet, String name, Status status) {
    pet.setName(name);
    pet.setStatus(status);
    return pet;
  }
}

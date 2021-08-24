package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileHelper {

    static final String SRC_TEST_RESOURCES_ENDPOINTS = "/src/test/resources/endpoints/";
    static final String SRC_TEST_RESOURCES_TEST_DATA= "/src/test/resources/test_data/";
    static final String API_ENDPOINT_PROPERTIES = "api_endpoint.properties";
    static final String TEST_DATA_PROPERTIES = "test_data.properties";

    public static Properties LoadEndpointsFile = loadProperty(System.getProperty("user.dir") + SRC_TEST_RESOURCES_ENDPOINTS + API_ENDPOINT_PROPERTIES);
    public static Properties LoadTestDataFile = loadProperty(System.getProperty("user.dir") + SRC_TEST_RESOURCES_TEST_DATA + TEST_DATA_PROPERTIES);

    public static Properties loadProperty(String path) {

        Properties pro = new Properties();
        try {
            FileInputStream streamfile = new FileInputStream(path);
            try {
                pro.load(streamfile);
            } catch (IOException e) {
                System.out.println("File Cann't be streamed!:" + "\n" + e);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!" + e);
        }
        return pro;
    }
}

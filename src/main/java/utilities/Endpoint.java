package utilities;

import helper.PropertyFileHelper;

public class Endpoint {

    public final static String baseURI= PropertyFileHelper.LoadEndpointsFile.getProperty("baseURI");

    public static String getBaseURI(){

        return baseURI;
    }
    public static String getBaseURI(String resourcePath){

        return baseURI+resourcePath;
    }

}


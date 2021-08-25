**PreRequisites:**

	java JDK installed
	(hint change in the pom file to change to your java version line (156,157, "source","target") supported from java 8 
	    url:https://www.oracle.com/de/java/technologies/javase-downloads.html
	maven installed (any version)
	    url:https://maven.apache.org/install.html
	setting up variables
	    url:https://www.tutorialspoint.com/maven/maven_environment_setup.htm
	allure(last version or 2.13.8)
	    url:https://docs.qameta.io/allure/ 

**Tests:**
    Tests are located under: src/test/java 
    
**Run:**
    *If tests failed try to rerun them again as every time the results are different due to the Flaky behaviour for the API"
    
    Run all tests:
    
        mvn clean test
    
    Run Regression tests:
        
        mvn clean test -Dgroups=PetsRegression -DSuiteXmlFiles=RegressionFeature.xml
    
    Run Integration tests:(Not working at the moment)
    
        mvn clean test -Dgroups=PetsIntegration -DSuiteXmlFiles=RegressionFeature.xml

**Generate Report:**

 Open Terminal in the same project
    cd dir
    cd allure-results
    allure serve
    
    
**Framework Capabilities:**

	Java classes
	Testng tests
	Data driven
	Maven
	Json files
	rest-asured Api
	Reporting tool
	
	
**Issues to be aware of**

    E2E flow and some other tests that relay on each other are not working this due to the flaky behaviour for the API 
    implementated some waiting methods but some times fails due the timeout takes more than 10 second 
   
       * This E2E flow will not work as there are 2 main issues :
       *
       *    1-API not reliable as in the Get request the id is found in the first request and not found in the following request
       *    Verified also that Swagger have this issue, added Video in the Email.
       *
       *    2-API is not reflected immediately so we need to add wait time implemented a method that help but also some times the timeout exceed
            and shouldn't be the case for a simple get and post request
       *    and I don't know the SLA to test against ,finally didn't want to use Thread.sleep even with it some times it fails 
       * */

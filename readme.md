**PreRequisites:**

	java JDK installed 
	    url:https://www.oracle.com/de/java/technologies/javase-downloads.html
	maven installed
	    url:https://maven.apache.org/install.html
	setting up variables
	    url:https://www.tutorialspoint.com/maven/maven_environment_setup.htm
	allure 
	    url:https://docs.qameta.io/allure/ 

**Tests:**
    Tests are located under: src/test/java 
    
**Run:**

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
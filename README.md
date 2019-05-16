# ScenarioDrivenTests

# Tools and Technologies used.
	-> IntelliJ (Java)
	 -> Junit 5 (for writing tests, for test runner, for test suites, for test reports)
	 -> Maven (for dependency management)
	 -> SQLlite (for storing global varibles)
	 -> JDBC connections (for connecting to SQLlite, MSSQL, or any other database, you need to connect to read data)
 
# Stuff that you need to make a Scenario driven System test framework in Intellij - Junit.

# A. A Test Runner class, which will do a data driven test for you.
	-> This is the place where you will "iterate" all the scenarios that you want to feed your application under test.
	-> This is also the place, where you "order" all the test steps you want to test.
	-> These test steps will typically contain the "test suites" that you want to run.
	-> These test steps, will also update some global variables and/or flags, so that your test classes (where actual assertions happens),
	know, where to read your inputs from and how to deal with certain functionalities. 
	
# B. A Couple of Test Suite class(es), where each Test Suite class, contains all the related classes that you want to test.
	-> The order of execution, is also defined here, so if you need to run and assert certain things in an order, 
	you can define it here.
	
# C. Actual test classes, where you do you validations of expected vs actuals. These are typically two types
	-> Classes that you create in "main" to generate expected results and to create other general purpose functions. 
	-> Classes that you create in "test" to test expected vs actual results.
	-> The names of these "test" classes are then added in the "Test Suite" class, so that they become a part of system test.
	
# Note: As you can see, the above framework is pretty scalable and mainteable. 
	-> If you want to add/delete a new method in any class, it doesnt need any change in Test Runner or Test Suite classes.
	-> If you want to add/delete a new class, you would only then need to add its name in the relevant "test suite class"
	
# Now some supporting things to make the above framework work. 
	
# For A. Test Runner class, 
	1. A folder to store all your test input files.
		-> You would need a place holder to store all your test scenarios, that you want to test your application with and thus 		feed to the Test Runnner class. 
		-> These could be the input files that your actual application under test consumes to create output results. 
		-> You can store all these scenarios say in a folder. 
		-> File Format could be whatever your applications consumes as input (so, xml, json, csv, blady blady bla...)
	
	2. A folder from where your application process the input files.
		-> At any given moment, your application will say process one set of scenario at a given time. 
		-> You would need to create a Input folder, from where the application picks up the Current test scenario to be run.
		-> This is the place from where the application will pick up the scenario as input to create an output. 
	
	3. A feeder logic, to iterate all the scenairos from the scenairos folder to the current test scenario folder.
		-> A iterating mechanism using which we pass on the scenarios one by one to the application, so that we can 
		test all possible scenarios with one click of button.
		-> This could be a for loop that iterates on each of the folder in the test scnearios folder 
			-> and move the test scenario one by one in the input folder for application to process. 
		 

# For B. Test Suite.
	-> A clarity of application under test, so that you can break down the application into logical checkpoints to test.
	-> Each logical entity can become a Test Suite.
	-> Each Test Suite can then contain the related sub functinoalities (so say classes).
	-> So instead of validating the full system, end to end, you can identify the input/output areas that you can 
	assert. The asserted output of one checkpoint can then become the input for next step.
	

# For C. Test Classes.
	-> A way to parse and read the input for the "current test scenario" and create your expected output . 
		-> All such generic functions goes to the MAIN class.
	-> Classes to create expected results based on the parsed input and the business business rule that deals with it. 
		-> This can also go under MAIN .
		-> This area, right here, building different expected results test classes, is also where 90% of your effort, should, 		will go in long run. 
	-> Classes to be able to parse actual outputs. So say databases, or file formats.
		-> This can also go under MAIN .
	-> Place where we call these expected and actual classes for current test iteration, and assert them.
		-> This would go under the TEST section. 

# For global variables
	Note: Since we are not passing any flags or values, directly from Test Runner class to the Test Class, one way, in which we can 	"inform" all the test classes about current test run is by setting some global variables in the "Test Runner" class. You can set 	 these flags/variables in a config file, or a json file, or a txt file, but I am currently preferring doing it via sqllite 		databases. This is considering that sqllite is free, and the ease of working with sqllite is great using sql for differnt 		operations. 
	-> So you set up these variables in A. In Test Runner class. 
	-> You read the values of these varaibles in C. Under diffferent Test Classes that are a part of Test Suites that the TestRunner is calling above. 

# A way to read the actual output to validate for completeness and correctness. 
	So lets take an example:

	If I have an application which can be broken down into say 5 logical units. With each logical unit dealing with couple realated
	functionalities;
		-> I can now say create 5 pakages (one for each logical unit), with each pakage having one or more classes for each related functionality
	to test.
	
	-> You can think of each of these related classes in a pakage as a test suite.
	
	Now to make all this stuff run in a scenario driven manner, we need a test runner.
	
	This test runner, will iterate all the scenario files one by one, and for each iteration, it will call all the test steps, 
        -> in our case, these will be test suites. 	
		-> Test suties, in themeselves, only contains the 'name' of the classes. So in that sense, they are really scalalabe. 
		-> Every time you have a step to add, add a new class or method, and with minimum changes in the framework, you can add the step 
		in sysetm test.
 
 # Test Reports:
 	-> Code to be added in the coming days. This when added will show how to generate JSON reports at two levels (high level and detailed level).
   
# To be continued...
	with more code samples in coming days... 	

# Credits and References:
	I did not invented any of the stuff that I used in building my framework. Below I mention some of the key websites I used to put all the stuff together in a framework.This way, you know where the information is coming from and possibly explore these websites for more content. So with that note, I say happy learning. 
  

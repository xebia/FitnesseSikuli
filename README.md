** Current application does not work yet with latest version of Sikuli. Will be fixed ASAP **

FitnesseSikuli enables you to use Sikuli from with FitNesse. The project is a fork of Xebium, which in turn integrates Selenium with Fitnesse.
Sikuli is software to automatate any UI-related task. Fitnesse is a acceptence-testframework. Combined, you can use Fitnesse-Sikuli to automate any UI related task you need within a test to be fully automatic - 'the last mile of test-automation'.

For more information and a demonstration, just download and run Fitnesse Sikuli by following the steps below

---------------

Execute the following command to run Fitnesse-Sikuli

	$ install Sikuli IDE (http://www.sikuli.org/) to a default location.
	$ If needed update sikuli-script-jar.location property (see pom.xml) to point to the right location of sikuli
	$ mvn -Pfitnesse test

and open a browser, pointing at http://localhost:8000.

Have fun!

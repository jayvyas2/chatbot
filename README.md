Clone the repo


To run the backend service, 
 install Java 11.0.17 openjdk by searching online or download it from here: https://developer.ibm.com/languages/java/semeru-runtimes/downloads/

Run the below command and expect output to be of verison you installed.

 java -version                                         


Set this env variable, set the path where java is installed similar to below.

export JAVA_HOME=/Users/jayvyas/Contents/Home


Run this command in project directory

mvn clean install


then go to target directory using below command

cd target


Then using the below command, you can run the jar. Make sure where you're running this command, jar should be in the same directory. 

java -jar chatbot-0.0.1-SNAPSHOT.jar    


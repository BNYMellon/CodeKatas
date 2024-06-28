## Setting up the project

### Requirements
* [JDK 21](https://jdk.java.net/21/) installed on your computer 
  
* [Maven 3.6.1+](https://maven.apache.org/download.cgi) installed on your computer
* [IntelliJ IDEA 2023.2.5+](https://www.jetbrains.com/idea/download/?section=windows) or similar IDE for Java

### Project setup
1. Git clone the entire [code-katas](https://github.com/BNYMellon/CodeKatas) project from GitHub or download the project
   as a .zip file.
2. Launch the project in the IDE and point to the pom.xml to be opened as a project.
   You can find more instructions on how to do
   that [here](https://www.jetbrains.com/idea/guide/tutorials/working-with-maven/importing-a-project/).
3. To use Java 21 features in IntelliJ IDEA, follow these steps:
   - Go to File | Project Structure.
   - Set the Project SDK to 21.
   - Set the Project language level to "21 - Record patterns, pattern matching for switch...".
   - Make sure you have the correct JDK selected.
4. To verify that the Java 8 module is set up correctly,
   run [CoffeeShopTest](jdk8/src/test/java/bny/codekatas/coffeeshopkata/CoffeeShopTest.java) in the
   jdk8 module - the class should compile and all tests will pass.
5. To verify that the Java 21 module is set up correctly,
   run [CoffeeShopTest](jdk21/src/test/java/bny/codekatas/coffeeshopkata/CoffeeShopTest.java) in the
   jdk21 module - the class should compile but all tests will fail.

### Getting started
* Follow the [README](README.md) for instructions on how to complete the kata.

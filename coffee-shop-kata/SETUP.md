## Setting up the project

### Tooling used
* [JDK 20.0.2+](https://jdk.java.net/20/) installed on your computer
* IntelliJ IDEA 2023.1+ or similar IDE for Java

### Initial setup
1. Launch the project in the IDE (can be configured as a maven project, pointing to the root pom.xml)

#### Test Java 8 module
1. Verify that the module old-java-features uses JDK 8
2. Run [CoffeeShopTest](/old-java-features/src/test/java/CoffeeShopTest.java), all tests should pass
#### Test Java 21 module
1. Verify that the module old-java-features uses JDK 20
2. Run [CoffeeShopTest](/new-java-features/src/test/java/CoffeeShopTest.java), most tests should fail
#### Get started
* Make the failing tests in [CoffeeShopTest](/new-java-features/src/test/java/CoffeeShopTest.java) pass by following and completing TODOs
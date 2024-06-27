# **What is the Donut Code Kata?**

The Donut Kata is an advanced kata which can help developers
become familiar with lesser known APIs of Eclipse Collections.  

The domain for the kata is a Donut Shop.  There are several domain
classes that are shared by all of the exercises.  These are 
[`DonutType`](src/main/java/bny/codekatas/donutkata/DonutType.java), 
[`Donut`](src/main/java/bny/codekatas/donutkata/Donut.java),
[`DonutShop`](src/main/java/bny/codekatas/donutkata/DonutShop.java),
[`Customer`](src/main/java/bny/codekatas/donutkata/Customer.java),
[`Order`](src/main/java/bny/codekatas/donutkata/Order.java) and
[`Delivery`](src/main/java/bny/codekatas/donutkata/Delivery.java).  

![Diagram](donutshop.png)
</p> 

### How to get started

Java version (17) (uses [Local-Variable Type Inference](http://openjdk.java.net/jeps/286) and [Records](https://openjdk.java.net/jeps/359)):
* There are failing tests in [`DonutShopTest.java`](./src/test/java/bny/codekatas/donutkata/DonutShopTest.java)
	* Make the tests pass by following and completing the TODOs in [`DonutShop.java`](src/main/java/bny/codekatas/donutkata/DonutShop.java)

Kotlin version (1.8.0):
* There are failing tests in [`DonutShopTest.kt`](./src/test/kotlin/bny/codekatas/donutkatakotlin/DonutShopTest.kt)
	* Make the tests pass by following and completing the TODOs in [`DonutShop.kt`](src/main/kotlin/bny/codekatas/donutkatakotlin/DonutShop.kt)

All of the `DonutShop` implementations and tests use [Eclipse Collections](http://www.eclipse.org/collections/).

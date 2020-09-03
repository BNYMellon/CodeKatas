# **What is the Donut Code Kata?**

The Donut Kata is an advanced kata which can help developers
become familiar with lesser known APIs of Eclipse Collections.  

The domain for the kata is a Donut Shop.  There are several domain
classes that are shared by all of the exercises.  These are 
[`DonutType`](./src/main/java/bnymellon/codekatas/donutkata/DonutType.java), 
[`Donut`](./src/main/java/bnymellon/codekatas/donutkata/Donut.java),
[`DonutShop`](./src/main/java/bnymellon/codekatas/donutkata/DonutShop.java),
[`Customer`](./src/main/java/bnymellon/codekatas/donutkata/Customer.java),
[`Order`](./src/main/java/bnymellon/codekatas/donutkata/Order.java) and
[`Delivery`](./src/main/java/bnymellon/codekatas/donutkata/Delivery.java).  

![Diagram](donutshop.png)
</p> 

### How to get started

Java version (uses [Local-Variable Type Inference](http://openjdk.java.net/jeps/286) and [Records](https://openjdk.java.net/jeps/359) preview feature):
* There are failing tests in [`DonutShopTest.java`](./src/test/java/bnymellon/codekatas/donutkata/DonutShopTest.java)
	* Make the tests pass by following and completing the TODOs in [`DonutShop.java`](./src/main/java/bnymellon/codekatas/donutkata/DonutShop.java)

Kotlin version:
* There are failing tests in [`DonutShopTest.kt`](./src/test/kotlin/bnymellon/codekatas/donutkatakotlin/DonutShopTest.kt)
	* Make the tests pass by following and completing the TODOs in [`DonutShop.kt`](./src/main/kotlin/bnymellon/codekatas/donutkatakotlin/DonutShop.kt)

All of the `DonutShop` implementations and tests use [Eclipse Collections](http://www.eclipse.org/collections/).

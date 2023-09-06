## What is the Coffee Shop Kata? ##

The Coffee Kata exercise is designed to provide a hands-on experience in learning and demonstrating the usage of latest
Java features while comparing them with older.

The domain for the kata is a Coffee Shop. There are several domain
classes that are shared by all the exercises. These are
[`Items`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/Item.java),
[`CoffeeShopOrder`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/CoffeeShopOrder.java),
[`Beverage`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/Beverage.java),
[`Tea`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/Tea.java),
[`CoffeeDrink`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/CoffeeDrink.java),
[`Latte`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/Latte.java),
[`Macchiato`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/Macchiato.java),
[`Americano`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/beverage/Americano.java),
[`BakeryItem`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/food/BakeryItem.java),
[`Cookie`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/food/Cookie.java),
[`Bagel`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/food/Bagel.java), and
[`Donut`](./old-java-features/src/main/java/bnymellon/codekatas/coffeekata/food/Donut.java).

![Diagram](CoffeeShopDomain.png)

## Getting Started ##
This kata involves refactoring existing code and implementing missing code! In the [`new-java-features`](./new-java-features) module, you will find a test class called [CoffeeShopTest](./new-java-features/src/test/java/CoffeeShopTest.java). Each test case contains a TODO that needs to be completed in order to make the code pass. All the code you need to complete is in the [`new-java-features`](./new-java-features) module, with the corresponding solutions in [`coffee-shop-kata-solutions/new-java-features`](../coffee-shop-kata-solutions/new-java-features-solutions). The purpose of the [`old-java-features`](./old-java-features) module is to show you the Java 8 way of solving these problems; there are no TODOs in this module. For technical setup, follow the instructions in [`SETUP.md`](./SETUP.md)! 
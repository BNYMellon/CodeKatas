# **What is the Java Lambda Kata?**

The Java Lambda Kata is meant to give a quick practical introduction to understanding and
leveraging lambdas in Java.  Lambdas can be used with any library that currently uses
Functional Interfaces, which are interfaces with one unimplemented method.  Two libraries
that make extensive use of Functional Interfaces are [Java Streams](https://www.ibm.com/developerworks/library/j-java-streams-1-brian-goetz/index.html)
and [Eclipse Collections](http://www.eclipse.org/collections/).

The kata is split into multiple packages (by library) and have tests that currently pass using
anonymous inner classes that you should convert to lambdas or method references.  The tests
are organized by Functional Interface (e.g. Consumer, Predicate, Function).

Oracle has a [quick-start tutorial online](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html)
for those that are brand new to using lambdas in Java.

## **Java Lambdas in 10 minutes!**
Java 8 ushered in support for Lambda syntax and Method References.  The support for lambdas
was added in such a way that existing libraries could take advantage of the support without
needing to change their types.  Lambdas are a huge improvement over [anonymous inner 
classes](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html) in that 
they reduce a lot of unsightly code, thus making it easier to both read and write.  A lambda 
has three parts:

* Parameter List: `(a, b)`
* A separator: `->`
* Expression: `a + b`

Lambda: `(a, b) -> a + b`

Parameter types can be inferred so aren't necessary.  Single parameter lambdas do not require
parentheses.  Zero parameter lambdas use empty `()`.  There are more rules if you have multiple
statement lambdas.  Look [online here](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)
for the full list of syntax rules for lambdas.

There is no special pre-defined type for a lambda.  Instead of introducing a new function type
syntax, the Oracle JDK team along with the JSR-335 Expert Group decided to leverage nominal 
function types as the types for lambdas.  This allowed for any existing Single Abstract Method (SAM types) 
or Functional interfaces to be used with lambdas.

So for instance, let's look at the following lambda

````java
() -> System.out.println("Hello World");
````
This lambda will not compile unless it is associated with some named type.  For instance,
this lambda could be a Runnable.
````java
Runnable runnable = () -> System.out.println("Hello World");
````
The equivalent code using an anonymous inner class would look as follows:
````java
Runnable runnable = new Runnable()
{
    @Override
    public void run()
    {
        System.out.println("Hello World");
    }
};
````
Hopefully you will agree the lambda is much nicer.

Running the following code will output "Hello World".
````java
Runnable runnable = () -> System.out.println("Hello World");
runnable.run();
// Prints: "Hello World"
````
As you can see, the type of the lambda here is determined at compile time.  A lambda
can also be inlined as a parameter to a method call.  The lambda will adapt to the 
type that is expected in the method call.
````java
List<String> list = Arrays.asList("2", "3", "1");
Collections.sort(list, (one, two) -> one.compareTo(two));
Assertions.assertEquals(Arrays.asList("1", "2", "3"), list);
````

That's Java Lambdas in 10 minutes!  Lambdas are awesome!  Now enjoy using them in the kata and your applications!

Start converting the [tests here](./src/test/java/bnymellon/codekatas/lambdakata/) to use lambdas.

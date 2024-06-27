# **What is the Deck of Cards Code Kata?**

The Deck of Cards Kata is an advanced kata which can help developers
become familiar with multiple Java Collections Frameworks and multiple
programming languages.  The Kata is based on the
**_Collections.compare(JDK; Apache; Eclipse; Guava...)_**
talk from the Devoxx US 2017 conference.  

The domain for the kata is Deck of Playing Cards.  There are three
classes that are shared by all of the exercises.  These are 
[`Rank`](./src/main/java/bny/codekatas/deckofcards/Rank.java), 
[`Suit`](./src/main/java/bny/codekatas/deckofcards/Suit.java) and 
[`Card`](./src/main/java/bny/codekatas/deckofcards/Card.java).  

![Diagram](diagram.png)

There are two sets of implementations for the
DeckOfCards with stubs provided for each of the Java Collections
frameworks.  The implementations that can be worked on are building
a DeckOfCards using an `ImmutableSortedList` or using an `ImmutableSortedSet`.

Six Java Collections frameworks are currently available to 
learn in the kata.  They are:
 
* [Java Collections + Streams](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Apache Commons Collections 4.4](http://commons.apache.org/proper/commons-collections/apidocs/)
* [Google Guava 31.1.1-jre](https://google.github.io/guava/releases/31.1-jre/api/docs/)
* [Eclipse Collections 11.1.0](https://github.com/eclipse/eclipse-collections/blob/master/docs/0-RefGuide.adoc)
* [Vavr 0.10.4](http://www.vavr.io/vavr-docs/)
* [Custom Collections Framework](./src/main/java/bny/codekatas/deckofcards/custom/collections/) (Uses features from [Project Amber](https://openjdk.java.net/projects/amber/))

There are two classes which are fully implemented and which have 
passing tests.  They are:

* [`JDKImperativeDeckOfCardsAsList`](./src/main/java/bny/codekatas/deckofcards/list/immutable/JDKImperativeDeckOfCardsAsList.java)
	* [`JDKImperativeDeckOfCardsAsListTest`](./src/test/java/bny/codekatas/deckofcards/list/immutable/JDKImperativeDeckOfCardsAsListTest.java)
* [`JDKImperativeDeckOfCardsAsSortedSet`](./src/main/java/bny/codekatas/deckofcards/sortedset/immutable/JDKImperativeDeckOfCardsAsSortedSet.java)
	* [`JDKImperativeDeckOfCardsAsSortedSetTest`](./src/test/java/bny/codekatas/deckofcards/sortedset/immutable/JDKImperativeDeckOfCardsAsSortedSetTest.java)

The JDK imperative deck of cards is used as the control subject in
the tests for each of the other frameworks.  There are TODOs in each
class that must be completed in order for a test to pass.
</p> 

### How to get started

* Refactor the imperative code in `Card` that calculates the cartesian product of `Rank` and `Suit`.
	* Replace the code with Java Streams - Look to use `flatMap` and `map`.
* Choose whether you could like to solve the kata using an `ImmutableSortedList`
or an `ImmutableSortedSet`.
	* [`ImmutableSortedList`](./src/test/java/bny/codekatas/deckofcards/list/immutable/) Tests
	* [`ImmutableSortedSet`](./src/test/java/bny/codekatas/deckofcards/sortedset/immutable/) Tests
* Choose the framework(s) you would like to complete the kata with.
* Fix the failing tests for each framework.
* Follow the TODOs and hints in each of the frameworks `DeckOfCards` domain class and test.
* Repeat as necessary.

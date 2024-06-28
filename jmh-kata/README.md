# **What is the JMH Kata?**

The JMH Kata is a simple project setup with some basic micro-benchmarks 
that you can use as a model to try out your own Java micro-benchmarks.

The JMH Kata leverages the [Java Micro-benchmark Harness](http://openjdk.java.net/projects/code-tools/jmh/)
tool from OpenJDK.

There are two micro-benchmarks currently checked in:

1. [`IntListJMHBenchmark`](./src/test/java/bny/codekatas/jmhkata/IntListJMHBenchmark.java)
 This test compares the performance of filtering, summing and transforming a million randomly generated integers stored 
 in an `ArrayList<Integer>`, a `FastList<Integer>` and an `IntArrayList`.<br>
2. [`PersonJMHBenchmark`](./src/test/java/bny/codekatas/jmhkata/PersonJMHBenchmark.java)
 This test compares the performance of various algorithms applied to a `List` of class `Person` which has randomly 
 generated values for age, height and weight.  The instances of class `Person` are stored in a JDK `List` and an 
 Eclipse Collections `MutableList`<br> 

Both of these benchmarks use throughput in seconds for the values output (operations per second).  This means 
the bigger the numbers, the better the performance.  You should be able to execute these benchmarks after 
compiling the code and running the main() method in each class.  You can use these tests as examples to write 
your own benchmarks.  

# **What is the CodePoint Code Kata?**

The CodePoint code kata is simple.  There is a single test where you have to read a secret message 
from a file called `codepoints.txt` and translate the file into an `ImmutableList` of `String` and 
then collect all the characters into a `CharBag` so they can be counted to verify that you have translated 
the message correctly.  You should finally write the `ImmutableList` of `String` to a file so you 
can read the secret message.

Here's an example of translating a comma separated `String` of code point values to a human readable `String`.

#### **Secret Message:**

72,101,108,108,111,32,87,111,114,108,100,33

#### **Message Revealed:**

Hello World!

To learn more about Characters and Code Points in Java, [here is a link](https://docs.oracle.com/javase/tutorial/i18n/text/characterClass.html). 



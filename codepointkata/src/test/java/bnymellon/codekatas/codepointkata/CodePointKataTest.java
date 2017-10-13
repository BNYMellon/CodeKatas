/*
 * Copyright 2017 BNY Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.codekatas.codepointkata;

import java.net.URL;

import org.eclipse.collections.api.bag.primitive.CharBag;
import org.eclipse.collections.api.bag.primitive.MutableCharBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.primitive.CharBags;
import org.junit.Assert;
import org.junit.Test;

public class CodePointKataTest
{
    @Test
    public void translateTheSecretMessage() throws Exception
    {
        URL resource = this.getClass().getClassLoader().getResource("codepoints.txt");
        // Hint: Look at Paths.get(URI)
        // Hint: Look at Files.lines(Path) which returns a Stream<String>

        // Write the code necessary to read the file of code points into an ImmutableList of String
        // The code points are comma separated, and need to be converted into a String
        // Blank lines will need to be converted to empty Strings
        // Hint: Look at String.split(String)
        // Hint: Look at new String(int[], int, int) or CodePointList.from(int...)
        // Hint: Look at Collectors2.toImmutableList()
        ImmutableList<String> list = null;

        // Write the code necessary to collect the list of Strings into a bag of characters
        // Iterate over each String collecting it's characters into the characters Bag
        // Hint: Look at CharAdapter.adapt(String)
        // Hint: Look at CharAdapter.toBag() which returns a MutableCharBag.
        // Hint: Look at ImmutableList.collect(Function)
        // Hint: Look at ImmutableList.each(Procedure) or ImmutableList.injectInto(IV, Function2)
        MutableCharBag characters = CharBags.mutable.empty();

        Assert.assertTrue(this.expectedBagOfCharacters(characters));

        // Output the list of strings to a file and read the secret message
        // Hint: Look at Files.write() or FileWriter
    }

    private boolean expectedBagOfCharacters(CharBag actual)
    {
        return  actual.occurrencesOf(' ') == 8 &&
                actual.occurrencesOf('.') == 1 &&
                actual.occurrencesOf('T') == 1 &&
                actual.occurrencesOf('a') == 1 &&
                actual.occurrencesOf('b') == 1 &&
                actual.occurrencesOf('c') == 1 &&
                actual.occurrencesOf('d') == 1 &&
                actual.occurrencesOf('e') == 3 &&
                actual.occurrencesOf('f') == 1 &&
                actual.occurrencesOf('g') == 1 &&
                actual.occurrencesOf('h') == 2 &&
                actual.occurrencesOf('i') == 1 &&
                actual.occurrencesOf('j') == 1 &&
                actual.occurrencesOf('k') == 1 &&
                actual.occurrencesOf('l') == 1 &&
                actual.occurrencesOf('m') == 1 &&
                actual.occurrencesOf('n') == 1 &&
                actual.occurrencesOf('o') == 4 &&
                actual.occurrencesOf('p') == 1 &&
                actual.occurrencesOf('q') == 1 &&
                actual.occurrencesOf('r') == 2 &&
                actual.occurrencesOf('s') == 1 &&
                actual.occurrencesOf('t') == 1 &&
                actual.occurrencesOf('u') == 2 &&
                actual.occurrencesOf('v') == 1 &&
                actual.occurrencesOf('w') == 1 &&
                actual.occurrencesOf('x') == 1 &&
                actual.occurrencesOf('y') == 1 &&
                actual.occurrencesOf('z') == 1;
    }

    @Test
    public void codePointsToHelloWorldHowAreYou()
    {
        Assert.assertEquals("Hello World!", this.convertCodePointsToString(72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33));
        Assert.assertEquals("How are you?", this.convertCodePointsToString(72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63));
    }

    private String convertCodePointsToString(int... codePoints)
    {
        // Hint: Look at new String(int[], int, int) or CodePointList.from(int...)
        return "";
    }
}

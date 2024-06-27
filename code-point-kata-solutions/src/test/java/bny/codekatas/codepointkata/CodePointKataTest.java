/*
 * Copyright 2024 The Bank of New York Mellon.
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

package bny.codekatas.codepointkata;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.collections.api.bag.primitive.CharBag;
import org.eclipse.collections.api.bag.primitive.MutableCharBag;
import org.eclipse.collections.api.factory.primitive.CharBags;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Strings;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.string.immutable.CharAdapter;
import org.eclipse.collections.impl.string.immutable.CodePointList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodePointKataTest
{
    @Test
    public void translateTheSecretMessage() throws Exception
    {
        var url = this.getClass().getClassLoader().getResource("codepoints.txt");
        Path path = Paths.get(url.toURI());
        ImmutableList<String> list = Files.lines(path)
                .filter(string -> !string.isBlank())
                .map(string -> string.split(" "))
                .map(array -> ArrayAdapter.adapt(array).collectInt(Integer::parseInt))
                .map(ints -> CodePointList.from(ints.toArray()))
                .map(CodePointList::toString)
                .collect(Collectors2.toImmutableList());

        var characters = CharBags.mutable.empty();
        list.asLazy()
                .collect(Strings::asChars)
                .collect(CharAdapter::toBag)
                .forEach(characters::addAll);

        Assertions.assertTrue(this.expectedBagOfCharacters(characters));

        Files.write(Path.of("src", "test", "resources", "secretMessage.txt"), list);
    }

    private boolean expectedBagOfCharacters(CharBag actual)
    {
        MutableCharBag expected = CharBags.mutable.empty();
        expected.addOccurrences(' ', 8);
        expected.addOccurrences('.', 1);
        expected.addOccurrences('T', 1);
        expected.addOccurrences('a', 1);
        expected.addOccurrences('b', 1);
        expected.addOccurrences('c', 1);
        expected.addOccurrences('d', 1);
        expected.addOccurrences('e', 3);
        expected.addOccurrences('f', 1);
        expected.addOccurrences('g', 1);
        expected.addOccurrences('h', 2);
        expected.addOccurrences('i', 1);
        expected.addOccurrences('j', 1);
        expected.addOccurrences('k', 1);
        expected.addOccurrences('l', 1);
        expected.addOccurrences('m', 1);
        expected.addOccurrences('n', 1);
        expected.addOccurrences('o', 4);
        expected.addOccurrences('p', 1);
        expected.addOccurrences('q', 1);
        expected.addOccurrences('r', 2);
        expected.addOccurrences('s', 1);
        expected.addOccurrences('t', 1);
        expected.addOccurrences('u', 2);
        expected.addOccurrences('v', 1);
        expected.addOccurrences('w', 1);
        expected.addOccurrences('x', 1);
        expected.addOccurrences('y', 1);
        expected.addOccurrences('z', 1);
        return expected.equals(actual);
    }

    @Test
    public void codePointsToHelloWorldHowAreYou()
    {
        Assertions.assertEquals(
                "Hello World!",
                this.convertCodePointsToString(72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33));
        Assertions.assertEquals(
                "How are you?",
                this.convertCodePointsToString(72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63));
    }

    private String convertCodePointsToString(int... codePoints)
    {
        return CodePointList.from(codePoints).toString();
    }
}

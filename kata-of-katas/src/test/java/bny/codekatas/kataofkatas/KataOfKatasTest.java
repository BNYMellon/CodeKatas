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
package bny.codekatas.kataofkatas;

import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.multimap.set.ImmutableSetMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KataOfKatasTest
{
    @Test
    void groupKatasByTechnologies()
    {
        // Group all of the katas by Technology
        ImmutableSetMultimap<Technology, Kata> techToKatas = null;

        Assertions.assertEquals(
                Sets.mutable.with(Kata.DECK_OF_CARDS, Kata.DONUT),
                techToKatas.get(Language.KOTLIN));
        Assertions.assertEquals(
                Kata.ALL,
                techToKatas.get(Library.ECLIPSE_COLLECTIONS));
    }

    @Test
    void intersectKatasForKotlinAndScala()
    {
        // Find the katas that are common between Kotlin and Scala
        ImmutableSet<Kata> intersect = null;

        Assertions.assertEquals(Sets.mutable.with(Kata.DECK_OF_CARDS), intersect);
    }

    @Test
    void intersectKatasForKotlinAndEclipseCollections()
    {
        // Find the katas that are common between Kotlin and Eclipse Collections
        ImmutableSet<Kata> intersect = null;

        Assertions.assertEquals(
                Sets.mutable.with(Kata.DONUT, Kata.DECK_OF_CARDS), intersect);
    }

    @Test
    void intersectKatasForJavaStreamAndEclipseCollections()
    {
        // Find the katas that are common between Java Streams and Eclipse Collections
        ImmutableSet<Kata> intersect = null;

        Assertions.assertEquals(
                Sets.mutable.with(Kata.DECK_OF_CARDS, Kata.PET, Kata.JAVA_LAMBDA, Kata.CODE_POINT, Kata.TOP_METHODS),
                intersect);
    }

    @Test
    void countKatasByLanguages()
    {
        // Count the Katas by Language
        ImmutableBag<Language> languages = null;

        Assertions.assertEquals(
                Language.JAVA,
                languages.topOccurrences(1).getFirst().getOne());
    }

    @Test
    void countKatasByLibraries()
    {
        // Count the Katas by Library
        ImmutableBag<Library> libraries = null;

        Assertions.assertEquals(
                Library.ECLIPSE_COLLECTIONS,
                libraries.topOccurrences(1).getFirst().getOne());
    }
}

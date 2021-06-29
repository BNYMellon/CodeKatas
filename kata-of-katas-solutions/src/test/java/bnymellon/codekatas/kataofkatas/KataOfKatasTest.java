  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

  
package bnymellon.codekatas.kataofkatas;

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
        ImmutableSetMultimap<Technology, Kata> techToKatas = Kata.ALL.groupByEach(Kata::getTechnologies);

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
        ImmutableSetMultimap<Language, Kata> techToKatas = Kata.ALL.groupByEach(Kata::getLanguages);
        ImmutableSet<Kata> intersect = techToKatas.get(Language.KOTLIN).intersect(techToKatas.get(Language.SCALA));

        Assertions.assertEquals(Sets.mutable.with(Kata.DECK_OF_CARDS), intersect);
    }

    @Test
    void intersectKatasForKotlinAndEclipseCollections()
    {
        ImmutableSetMultimap<Technology, Kata> techToKatas = Kata.ALL.groupByEach(Kata::getTechnologies);
        ImmutableSet<Kata> intersect = techToKatas.get(Library.ECLIPSE_COLLECTIONS).intersect(techToKatas.get(Language.KOTLIN));

        Assertions.assertEquals(
                Sets.mutable.with(Kata.DONUT, Kata.DECK_OF_CARDS), intersect);
    }

    @Test
    void intersectKatasForJavaStreamAndEclipseCollections()
    {
        ImmutableSetMultimap<Library, Kata> techToKatas = Kata.ALL.groupByEach(Kata::getLibraries);
        ImmutableSet<Kata> intersect = techToKatas.get(Library.ECLIPSE_COLLECTIONS).intersect(techToKatas.get(Library.JAVA_STREAM));

        Assertions.assertEquals(
                Sets.mutable.with(Kata.DECK_OF_CARDS, Kata.PET, Kata.JAVA_LAMBDA, Kata.CODE_POINT, Kata.TOP_METHODS),
                intersect);
    }

    @Test
    void countKatasByLanguages()
    {
        ImmutableBag<Language> languages = Kata.ALL.countByEach(Kata::getLanguages);

        Assertions.assertEquals(
                Language.JAVA,
                languages.topOccurrences(1).getFirst().getOne());
    }

    @Test
    void countKatasByLibraries()
    {
        ImmutableBag<Library> libraries = Kata.ALL.countByEach(Kata::getLibraries);

        Assertions.assertEquals(
                Library.ECLIPSE_COLLECTIONS,
                libraries.topOccurrences(1).getFirst().getOne());
    }
}

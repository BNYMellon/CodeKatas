  /**
* Copyright © The Bank of New York Mellon 2021
* Licensed under the BSD-3-Clause License (the “License”);
* You may not use this file except in compliance with the License.
* You may obtain a copy of the License at:
* https://opensource.org/licenses/BSD-3-Clause
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES ARE DISCLAIMED.  SEE THE LICENSE FOR FURTHER RESTRICTIONS.
*/

  
package bnymellon.codekatas.kataofkatas;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;

public enum Kata
{
    PET(Language.JAVA, Language.PYTHON, Library.ECLIPSE_COLLECTIONS, Library.JAVA_STREAM),
    COMPANY(Language.JAVA, Library.ECLIPSE_COLLECTIONS),
    CANDY(Language.JAVA, Library.ECLIPSE_COLLECTIONS),
    CONVERTER_METHOD(Language.JAVA, Library.ECLIPSE_COLLECTIONS),
    CALENDAR(Language.JAVA, Library.ECLIPSE_COLLECTIONS, Library.JAVA_TIME, Library.THREE_TEN_EXTRA),
    DECK_OF_CARDS(Language.JAVA, Language.KOTLIN, Language.GROOVY, Language.SCALA, Language.SMALLTALK,
            Library.ECLIPSE_COLLECTIONS, Library.JAVA_STREAM, Library.GOOGLE_GUAVA, Library.APACHE_COMMONS_COLLECTIONS, Library.VAVR),
    DONUT(Language.JAVA, Language.KOTLIN, Library.ECLIPSE_COLLECTIONS),
    JAVA_LAMBDA(Language.JAVA, Library.JAVA_STREAM, Library.ECLIPSE_COLLECTIONS),
    CODE_POINT(Language.JAVA, Library.ECLIPSE_COLLECTIONS, Library.JAVA_STREAM),
    TOP_METHODS(Language.JAVA, Library.ECLIPSE_COLLECTIONS, Library.JAVA_STREAM),
    KATA_OF_KATAS(Language.JAVA, Library.ECLIPSE_COLLECTIONS);

    public static final ImmutableSet<Kata> ALL = Sets.immutable.with(Kata.values());
    public final ImmutableSet<Technology> technologies;

    Kata(Technology... values)
    {
        this.technologies = Sets.immutable.with(values);
    }

    public ImmutableSet<Language> getLanguages()
    {
        return this.technologies.selectInstancesOf(Language.class);
    }

    public ImmutableSet<Library> getLibraries()
    {
        return this.technologies.selectInstancesOf(Library.class);
    }

    public ImmutableSet<Technology> getTechnologies()
    {
        return this.technologies;
    }
}

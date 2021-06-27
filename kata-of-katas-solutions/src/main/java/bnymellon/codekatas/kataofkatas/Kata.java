/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
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

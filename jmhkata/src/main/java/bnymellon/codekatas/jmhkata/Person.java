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

package bnymellon.codekatas.jmhkata;

import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

public class Person
{
    private static final AtomicInteger ID = new AtomicInteger(0);
    private static final PrimitiveIterator.OfDouble HEIGHT_GENERATOR = new Random(1L).doubles(100.0, 250.00).iterator();
    private static final PrimitiveIterator.OfDouble WEIGHT_GENERATOR = new Random(1L).doubles(48.0, 200.00).iterator();
    private static final PrimitiveIterator.OfInt AGE_GENERATOR = new Random(1L).ints(18, 85).iterator();
    static final int NUMBER_OF_PEOPLE = 1_000_000;
    private static final List<Person> JDK_PEOPLE = Stream.generate(Person::new).limit(NUMBER_OF_PEOPLE).collect(Collectors.toList());
    private static final MutableList<Person> EC_PEOPLE = Lists.mutable.withAll(JDK_PEOPLE).shuffleThis();
    private String name;
    private int age;
    private double heightInInches;
    private double weightInPounds;

    static
    {
        Collections.shuffle(JDK_PEOPLE);
    }

    public Person()
    {
        this.name = "Person " + ID.getAndIncrement();
        this.age = AGE_GENERATOR.nextInt();
        this.heightInInches = HEIGHT_GENERATOR.nextDouble();
        this.weightInPounds = WEIGHT_GENERATOR.nextDouble();
    }

    public static List<Person> getJDKPeople()
    {
        return JDK_PEOPLE;
    }

    public static MutableList<Person> getECPeople()
    {
        return EC_PEOPLE;
    }

    public int getAge()
    {
        return this.age;
    }

    public double getHeightInInches()
    {
        return this.heightInInches;
    }

    public double getWeightInPounds()
    {
        return this.weightInPounds;
    }
}

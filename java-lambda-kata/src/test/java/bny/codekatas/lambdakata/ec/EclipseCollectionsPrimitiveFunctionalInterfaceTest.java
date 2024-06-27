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

package bny.codekatas.lambdakata.ec;

import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.eclipse.collections.api.block.predicate.primitive.DoublePredicate;
import org.eclipse.collections.api.block.predicate.primitive.IntPredicate;
import org.eclipse.collections.api.block.predicate.primitive.LongPredicate;
import org.eclipse.collections.api.block.procedure.primitive.DoubleProcedure;
import org.eclipse.collections.api.block.procedure.primitive.IntProcedure;
import org.eclipse.collections.api.block.procedure.primitive.LongProcedure;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EclipseCollectionsPrimitiveFunctionalInterfaceTest
{
    @Test
    public void IntProcedure()
    {
        var adder = new LongAdder();
        // TODO - Convert the anonymous inner class to a lambda
        var procedure = new IntProcedure()
        {
            @Override
            public void value(int value)
            {
                adder.add((long) value);
            }
        };
        IntInterval.oneTo(5).forEach(procedure);
        Assertions.assertEquals(15, adder.longValue());
    }

    @Test
    public void LongProcedure()
    {
        var adder = new LongAdder();
        // TODO - Convert the anonymous inner class to a lambda
        var procedure = new LongProcedure()
        {
            @Override
            public void value(long value)
            {
                adder.add(value);
            }
        };
        LongLists.mutable.with(1, 2, 3, 4, 5).forEach(procedure);
        Assertions.assertEquals(15, adder.longValue());
    }

    @Test
    public void DoubleProcedure()
    {
        var adder = new DoubleAdder();
        // TODO - Convert the anonymous inner class to a lambda
        var procedure = new DoubleProcedure()
        {
            @Override
            public void value(double value)
            {
                adder.add(value);
            }
        };
        DoubleLists.mutable.with(1.0d, 2.0d, 3.0d, 4.0d, 5.0d).forEach(procedure);
        Assertions.assertEquals(15.0, adder.doubleValue(), 0.0);
    }

    @Test
    public void IntPredicate()
    {
        // TODO - Convert the anonymous inner class to a lambda
        var predicate = new IntPredicate()
        {
            @Override
            public boolean accept(int value)
            {
                return value % 2 == 0;
            }
        };
        IntInterval interval = IntInterval.oneTo(5);
        ImmutableIntList evens = interval.select(predicate);
        Assertions.assertEquals(IntLists.mutable.with(2, 4), evens);
        Assertions.assertEquals(IntInterval.evensFromTo(1, 5), evens);
        ImmutableIntList odds = interval.reject(predicate);
        Assertions.assertEquals(IntLists.mutable.with(1, 3, 5), odds);
        Assertions.assertEquals(IntInterval.oddsFromTo(1, 5), odds);
        Assertions.assertTrue(interval.anySatisfy(predicate));
        Assertions.assertFalse(interval.allSatisfy(predicate));
        Assertions.assertFalse(interval.noneSatisfy(predicate));
        Assertions.assertTrue(IntStream.rangeClosed(1, 5).anyMatch(predicate));
        Assertions.assertFalse(IntStream.rangeClosed(1, 5).allMatch(predicate));
        Assertions.assertFalse(IntStream.rangeClosed(1, 5).noneMatch(predicate));
    }

    @Test
    public void LongPredicate()
    {
        // TODO - Convert the anonymous inner class to a lambda
        var predicate = new LongPredicate()
        {
            @Override
            public boolean accept(long value)
            {
                return value % 2 == 0;
            }
        };
        var list = LongLists.mutable.with(1, 2, 3, 4, 5);
        var evens = list.select(predicate);
        Assertions.assertEquals(LongLists.mutable.with(2, 4), evens);
        var odds = list.reject(predicate);
        Assertions.assertEquals(LongLists.mutable.with(1, 3, 5), odds);
        Assertions.assertTrue(list.anySatisfy(predicate));
        Assertions.assertFalse(list.allSatisfy(predicate));
        Assertions.assertFalse(list.noneSatisfy(predicate));
        Assertions.assertTrue(LongStream.rangeClosed(1, 5).anyMatch(predicate));
        Assertions.assertFalse(LongStream.rangeClosed(1, 5).allMatch(predicate));
        Assertions.assertFalse(LongStream.rangeClosed(1, 5).noneMatch(predicate));
    }

    @Test
    public void DoublePredicate()
    {
        // TODO - Convert the anonymous inner class to a lambda
        var predicate = new DoublePredicate()
        {
            @Override
            public boolean accept(double value)
            {
                return value > 3.0;
            }
        };
        var list = DoubleLists.mutable.with(1.0, 2.0, 3.0, 4.0, 5.0);
        var greaterThan = list.select(predicate);
        Assertions.assertEquals(DoubleLists.mutable.with(4.0d, 5.0d), greaterThan);
        var lessThanEqualTo = list.reject(predicate);
        Assertions.assertEquals(DoubleLists.mutable.with(1.0d, 2.0d, 3.0d), lessThanEqualTo);
        Assertions.assertTrue(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).anyMatch(predicate));
        Assertions.assertFalse(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).allMatch(predicate));
        Assertions.assertFalse(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).noneMatch(predicate));
    }
}

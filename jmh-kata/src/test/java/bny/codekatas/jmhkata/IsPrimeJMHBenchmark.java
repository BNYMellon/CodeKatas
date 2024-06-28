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

package bny.codekatas.jmhkata;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.primes.Primes;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MultiReaderBag;
import org.eclipse.collections.api.bag.primitive.MutableIntBag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.parallel.ParallelIterate;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
public class IsPrimeJMHBenchmark
{
    private static final ExecutorService EXECUTOR_SERVICE = ParallelIterate.newPooledExecutor(IsPrimeJMHBenchmark.class.getSimpleName(), true);
    private static final MutableList<Integer> INTEGERS = Interval.zeroTo(2_000_000).toList().shuffleThis(new Random(42L));
    private static final MutableIntList INTS = IntInterval.zeroTo(2_000_000).toList().shuffleThis(new Random(42L));
    private static final int SIZE = INTEGERS.size();
    private static final int EXPECTED_COUNT = INTEGERS.count(Primes::isPrime);
    private static final int BATCH_SIZE = SIZE / (Runtime.getRuntime().availableProcessors() * 2);

    public static void main(String[] args) throws RunnerException
    {
        System.out.println("Size: " + SIZE);
        System.out.println("Expected: " + EXPECTED_COUNT);
        System.out.println("Batch Size: " + BATCH_SIZE);
        var options = new OptionsBuilder().include(".*" + IsPrimeJMHBenchmark.class.getSimpleName() + ".*")
                .forks(2)
                .warmupIterations(10)
                .warmupTime(TimeValue.seconds(5L))
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(5L))
                .timeout(TimeValue.seconds(20))
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Bag<Integer> boxedSerialEagerSelectEC()
    {
        Bag<Integer> serialSelect = INTEGERS.select(Primes::isPrime, Bags.mutable.empty());
        return serialSelect;
    }

    @Benchmark
    public Bag<Integer> boxedSerialLazyFilterJDK()
    {
        Bag<Integer> serialFilter = INTEGERS.stream().filter(Primes::isPrime).collect(Collectors2.toBag());
        return serialFilter;
    }

    @Benchmark
    public Bag<Integer> boxedParallelEagerSelectEC()
    {
        Bag<Integer> parallelSelect = ParallelIterate.select(INTEGERS, Primes::isPrime, Bags.mutable.empty(), true);
        return parallelSelect;
    }

    @Benchmark
    public Bag<Integer> boxedParallelEagerForEachEC()
    {
        MultiReaderBag<Integer> bag = Bags.multiReader.empty();
        ParallelIterate.forEach(INTEGERS, each -> {
            if (Primes.isPrime(each))
            {
                bag.add(each);
            }
        });
        return bag;
    }

    @Benchmark
    public Bag<Integer> boxedParallelLazySelectEC()
    {
        Bag<Integer> parallelSelect = INTEGERS.asParallel(EXECUTOR_SERVICE, BATCH_SIZE).select(Primes::isPrime).toBag();
        return parallelSelect;
    }

    @Benchmark
    public Bag<Integer> boxedSerialLazySelectEC()
    {
        Bag<Integer> parallelSelect = INTEGERS.asLazy().select(Primes::isPrime).toBag();
        return parallelSelect;
    }

    @Benchmark
    public Bag<Integer> boxedParallelLazyForEachEC()
    {
        MultiReaderBag<Integer> bag = Bags.multiReader.empty();
        INTEGERS.asParallel(EXECUTOR_SERVICE, BATCH_SIZE).forEach(each -> {
            if (Primes.isPrime(each))
            {
                bag.add(each);
            }
        });
        return bag;
    }

    @Benchmark
    public Bag<Integer> boxedParallelLazyFilterJDK()
    {
        Bag<Integer> parallelFilter = INTEGERS.parallelStream().filter(Primes::isPrime).collect(Collectors2.toBag());
        return parallelFilter;
    }

    @Benchmark
    public Bag<Integer> boxedParallelForEachJDK()
    {
        MultiReaderBag<Integer> bag = Bags.multiReader.empty();
        INTEGERS.parallelStream().forEach(each -> {
            if (Primes.isPrime(each))
            {
                bag.add(each);
            }
        });
        return bag;
    }

    @Benchmark
    public MutableIntBag primitiveSerialEagerSelectEC()
    {
        MutableIntBag serialSelect = INTS.select(Primes::isPrime, IntBags.mutable.empty());
        return serialSelect;
    }

    @Benchmark
    public MutableIntBag primitiveSerialLazyFilterJDK()
    {
        MutableIntBag serialFilter = IntBags.mutable.withAll(INTS.primitiveStream().filter(Primes::isPrime));
        return serialFilter;
    }

    @Benchmark
    public MutableIntBag primitiveParallelLazyFilterJDK()
    {
        MutableIntBag parallelFilter = IntBags.mutable.withAll(INTS.primitiveParallelStream().filter(Primes::isPrime));
        return parallelFilter;
    }
}

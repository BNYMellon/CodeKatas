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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class IntListJMHBenchmark
{
    private List<Integer> jdkList;
    private IntList ecIntList;
    private MutableList<Integer> ecList;
    private ExecutorService executor;

    @Setup
    public void setUp()
    {
        this.executor = Executors.newWorkStealingPool();
        var iterator = new Random(1L).ints(-1000, 1000).iterator();
        this.ecList = FastList.newWithNValues(1_000_000, iterator::nextInt);
        this.jdkList = new ArrayList<>(1_000_000);
        this.jdkList.addAll(this.ecList);
        this.ecIntList = this.ecList.collectInt(i -> i, new IntArrayList(1_000_000));
    }

    public static void main(String[] args) throws RunnerException
    {
        var options = new OptionsBuilder().include(".*" + IntListJMHBenchmark.class.getSimpleName() + ".*")
                .forks(2)
                .warmupIterations(10)
                .warmupTime(TimeValue.seconds(5L))
                .measurementIterations(10)
                .measurementTime(TimeValue.seconds(5L))
                .timeout(TimeValue.seconds(20))
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public long sumJDK()
    {
        return this.jdkList.stream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long sumJDKParallel()
    {
        return this.jdkList.parallelStream().mapToLong(i -> i).sum();
    }

    @Benchmark
    public long sumECPrimitive()
    {
        return this.ecIntList.sum();
    }

    @Benchmark
    public long sumEC()
    {
        return this.ecList.sumOfInt(i -> i);
    }

    @Benchmark
    public long sumECParallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).sumOfInt(i -> i);
    }

    @Benchmark
    public List<Integer> filterJDKBoxed()
    {
        return this.jdkList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> filterJDKBoxedParallel()
    {
        return this.jdkList.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    @Benchmark
    public IntList filterECPrimitive()
    {
        return this.ecIntList.select(i -> i % 2 == 0);
    }

    @Benchmark
    public MutableList<Integer> filterEC()
    {
        return this.ecList.select(i -> i % 2 == 0);
    }

    @Benchmark
    public MutableList<Integer> filterECParallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).select(i -> i % 2 == 0).toList();
    }

    @Benchmark
    public List<Integer> transformJDKBoxed()
    {
        return this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> transformJDKBoxedParallel()
    {
        return this.jdkList.parallelStream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public IntList transformECPrimitive()
    {
        return this.ecIntList.collectInt(i -> i * 2, IntLists.mutable.empty());
    }

    @Benchmark
    public MutableList<Integer> transformEC()
    {
        return this.ecList.collect(i -> i * 2).toList();
    }

    @Benchmark
    public MutableList<Integer> transformECParallel()
    {
        return this.ecList.asParallel(this.executor, 100_000).collect(i -> i * 2).toList();
    }
}

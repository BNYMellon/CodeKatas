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

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
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
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class IntListJMHTest
{
    private List<Integer> jdkList;
    private IntList ecList;

    @Setup
    public void setUp()
    {
        PrimitiveIterator.OfInt AGE_GENERATOR = new Random(1L).ints(-1000, 1000).iterator();
        final FastList<Integer> integers = FastList.newWithNValues(1_000_000, AGE_GENERATOR::nextInt);
        this.jdkList = new ArrayList<>(1_000_000);
        this.jdkList.addAll(integers);
        this.ecList = integers.collectInt(i -> i, new IntArrayList(1_000_000));

        System.out.println();
        System.out.println("Memory for ArrayList    (bytes): " + ObjectSizeCalculator.getObjectSize(this.jdkList));
        System.out.println("Memory for IntArrayList (bytes): " + ObjectSizeCalculator.getObjectSize(this.ecList));
    }

    public static void main(String[] args) throws RunnerException
    {
        Options options = new OptionsBuilder().include(".*" + IntListJMHTest.class.getSimpleName() + ".*")
                .forks(2)
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public long sumJDK()
    {
        return this.jdkList.stream().mapToInt(i -> i).sum();
    }

    @Benchmark
    public long sumJDKParallel()
    {
        return this.jdkList.stream().mapToInt(i -> i).sum();
    }

    @Benchmark
    public long sumEC()
    {
        return this.ecList.sum();
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
        return this.ecList.select(i -> i % 2 == 0);
    }

    @Benchmark
    public List<Integer> transformJDKBoxed()
    {
        return this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> transformJDKBoxedParallel()
    {
        return this.jdkList.stream().mapToInt(i -> i * 2).boxed().collect(Collectors.toList());
    }

    @Benchmark
    public IntList transformECPrimitive()
    {
        return this.ecList.collectInt(i -> i * 2, IntLists.mutable.empty());
    }
}

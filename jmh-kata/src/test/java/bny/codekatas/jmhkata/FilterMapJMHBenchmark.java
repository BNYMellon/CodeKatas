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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.impl.forkjoin.FJIterate;
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
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class FilterMapJMHBenchmark
{
    private static final ExecutorService EXECUTOR_SERVICE = ParallelIterate.newPooledExecutor(FilterMapJMHBenchmark.class.getSimpleName(), true);
    private static final Predicate<Person> PERSON_PREDICATE = person -> person.getHeightInInches() > 60.0 && person.getHeightInInches() < 78.0;
    private static final java.util.function.Predicate<Person> JDK_PERSON_PREDICATE = person -> person.getHeightInInches() > 60.0 && person.getHeightInInches() < 78.0;
    private static final int EXPECTED_COUNT = Person.getECPeople().count(PERSON_PREDICATE);
    private static final int SIZE = Person.getECPeople().size();
    private static final int BATCH_SIZE = SIZE / (Runtime.getRuntime().availableProcessors() * 2);

    public static void main(String[] args) throws RunnerException
    {
        System.out.println("Size: " + SIZE);
        System.out.println("Expected: " + EXPECTED_COUNT);
        System.out.println("Batch Size: " + BATCH_SIZE);
        var options = new OptionsBuilder().include(".*" + FilterMapJMHBenchmark.class.getSimpleName() + ".*")
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
    public List<String> filterMapJDK_parallel_toList()
    {
        var filtered = Person.getJDKPeople()
                .parallelStream()
                .filter(JDK_PERSON_PREDICATE)
                .map(Person::getName)
                .toList();
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapJDK_parallel_CollectorToList()
    {
        var filtered = Person.getJDKPeople()
                .parallelStream()
                .filter(JDK_PERSON_PREDICATE)
                .map(Person::getName)
                .collect(Collectors.toList());
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapECLazy_parallel()
    {
        var filtered = Person.getECPeople()
                .asParallel(EXECUTOR_SERVICE, BATCH_SIZE)
                .select(PERSON_PREDICATE)
                .collect(Person::getName)
                .toList();
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public Collection<String> filterMapECEager_parallel()
    {
        var filtered = ParallelIterate.collectIf(Person.getECPeople(), PERSON_PREDICATE, Person::getName);
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public Collection<String> filterMapECEager_forkJoin()
    {
        var filtered = FJIterate.collectIf(Person.getECPeople(), PERSON_PREDICATE, Person::getName);
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapJDK_serial_toList()
    {
        var filtered = Person.getJDKPeople()
                .stream()
                .filter(JDK_PERSON_PREDICATE)
                .map(Person::getName)
                .toList();
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapJDK_serial_CollectorToList()
    {
        var filtered = Person.getJDKPeople()
                .stream()
                .filter(JDK_PERSON_PREDICATE)
                .map(Person::getName)
                .collect(Collectors.toList());
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapECEager_serial()
    {
        var filtered = Person.getECPeople().collectIf(PERSON_PREDICATE, Person::getName);
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }

    @Benchmark
    public List<String> filterMapECLazy_serial()
    {
        var filtered = Person.getECPeople()
                .asLazy()
                .select(PERSON_PREDICATE)
                .collect(Person::getName)
                .toList();
        assert filtered.size() == EXPECTED_COUNT;
        return filtered;
    }
}

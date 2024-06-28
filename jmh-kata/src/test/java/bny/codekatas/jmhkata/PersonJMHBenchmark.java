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
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntSets;
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
public class PersonJMHBenchmark
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    public static void main(String[] args) throws RunnerException
    {
        var options = new OptionsBuilder().include(".*" + PersonJMHBenchmark.class.getSimpleName() + ".*")
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
    public Object[] combinedStatisticsJDK_parallel()
    {
        var stats1 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        var stats2 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        var stats3 =
                Person.getJDKPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECStream_parallel()
    {
        var stats1 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        var stats2 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        var stats3 =
                Person.getECPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_parallel()
    {
        var grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 72)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public ListMultimap<Integer, Person> filterAndGroupByAgeECLazy_parallel()
    {
        var grouped =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < 72)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableMultimap<Integer, Person> filterAndGroupByAgeECEager_parallel()
    {
        var select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() < 72);
        var grouped =
                ParallelIterate.groupBy(select, Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECStream_parallel()
    {
        var grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 72)
                        .collect(Collectors2.toListMultimap(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filterJDK_parallel()
    {
        var filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() > 60)
                        .filter(person -> person.getHeightInInches() < 72)
                        .collect(Collectors.toList());
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_parallel()
    {
        var filtered =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() > 60)
                        .select(person -> person.getHeightInInches() < 72)
                        .toList();
        return filtered;
    }

    @Benchmark
    public Collection<Person> filterECEager_parallel()
    {
        var select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() > 60);
        return ParallelIterate.select(select, person -> person.getHeightInInches() < 72);
    }

    @Benchmark
    public List<Person> filterECStream_parallel()
    {
        var filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() > 60)
                        .collect(Collectors2.select(person -> person.getHeightInInches() < 72, Lists.mutable::empty));
        return filtered;
    }

    @Benchmark
    public Object[] combinedStatisticsJDK_serial()
    {
        var stats1 =
                Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        var stats2 =
                Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        var stats3 =
                Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECLazy_serial()
    {
        var stats1 =
                Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics();
        var stats2 =
                Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics();
        var stats3 =
                Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECEager_serial()
    {
        var stats1 =
                Person.getECPeople().summarizeDouble(Person::getHeightInInches);
        var stats2 =
                Person.getECPeople().summarizeDouble(Person::getWeightInPounds);
        var stats3 =
                Person.getECPeople().summarizeInt(Person::getAge);
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_serial()
    {
        var grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < 72)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECEager_serial()
    {
        var grouped =
                Person.getECPeople()
                        .select(person -> person.getHeightInInches() < 72)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Multimap<Integer, Person> filterAndGroupByAgeECLazy_serial()
    {
        var grouped = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < 72)
                .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Map<Integer, List<Person>> filterJDK_serial()
    {
        var grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() > 60)
                        .filter(person -> person.getHeightInInches() < 72)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filterECEager_serial()
    {
        var filtered = Person.getECPeople()
                .select(person -> person.getHeightInInches() > 60)
                .select(person -> person.getHeightInInches() < 72);
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_serial()
    {
        var filtered = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() > 60)
                .select(person -> person.getHeightInInches() < 72)
                .toList();
        return filtered;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECEager_serial()
    {
        var uniqueAges =
                Person.getECPeople().collectInt(Person::getAge, IntSets.mutable.empty());
        var summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECLazy_serial()
    {
        var uniqueAges =
                Person.getECPeople().asLazy().collectInt(Person::getAge).toSet();
        var summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsJDK_serial()
    {
        var uniqueAges =
                Person.getJDKPeople().stream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        var summary = uniqueAges.stream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECStream_parallel()
    {
        var uniqueAges =
                Person.getECPeople()
                        .parallelStream()
                        .collect(Collectors2.collectInt(Person::getAge, IntSets.mutable::empty));
        var summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsJDK_parallel()
    {
        var uniqueAges =
                Person.getJDKPeople().parallelStream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        var summary = uniqueAges.parallelStream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }
}

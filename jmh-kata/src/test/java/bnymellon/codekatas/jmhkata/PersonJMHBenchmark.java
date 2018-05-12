/*
 * Copyright 2018 BNY Mellon.
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

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
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
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class PersonJMHBenchmark
{
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    public static void main(String[] args) throws RunnerException
    {
        Options options = new OptionsBuilder().include(".*" + PersonJMHBenchmark.class.getSimpleName() + ".*")
                .forks(2)
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public Object[] combinedStatisticsJDK_parallel()
    {
        DoubleSummaryStatistics stats1 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getJDKPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getJDKPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECStream_parallel()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().parallelStream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getECPeople().parallelStream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_parallel()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public ListMultimap<Integer, Person> filterAndGroupByAgeECLazy_parallel()
    {
        ListMultimap<Integer, Person> grouped =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < 150)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableMultimap<Integer, Person> filterAndGroupByAgeECEager_parallel()
    {
        Collection<Person> select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() < 150);
        MutableMultimap<Integer, Person> grouped =
                ParallelIterate.groupBy(select, Person::getAge);
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECStream_parallel()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors2.toListMultimap(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filterJDK_parallel()
    {
        final List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .filter(person -> person.getHeightInInches() > 80)
                        .collect(Collectors.toList());
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_parallel()
    {
        MutableList<Person> filtered =
                Person.getECPeople().asParallel(EXECUTOR_SERVICE, 100_000)
                        .select(person -> person.getHeightInInches() < 150)
                        .select(person -> person.getHeightInInches() > 80)
                        .toList();
        return filtered;
    }

    @Benchmark
    public Collection<Person> filterECEager_parallel()
    {
        Collection<Person> select =
                ParallelIterate.select(Person.getECPeople(), person -> person.getHeightInInches() < 150);
        return ParallelIterate.select(select, person -> person.getHeightInInches() > 80);
    }

    @Benchmark
    public List<Person> filterECStream_parallel()
    {
        List<Person> filtered =
                Person.getJDKPeople().parallelStream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors2.select(person -> person.getHeightInInches() > 80, Lists.mutable::empty));
        return filtered;
    }

    @Benchmark
    public Object[] combinedStatisticsJDK_serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getJDKPeople().stream().mapToDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getJDKPeople().stream().mapToDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getJDKPeople().stream().mapToInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECLazy_serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().asLazy().collectDouble(Person::getHeightInInches).summaryStatistics();
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().asLazy().collectDouble(Person::getWeightInPounds).summaryStatistics();
        IntSummaryStatistics stats3 =
                Person.getECPeople().asLazy().collectInt(Person::getAge).summaryStatistics();
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Object[] combinedStatisticsECEager_serial()
    {
        DoubleSummaryStatistics stats1 =
                Person.getECPeople().summarizeDouble(Person::getHeightInInches);
        DoubleSummaryStatistics stats2 =
                Person.getECPeople().summarizeDouble(Person::getWeightInPounds);
        IntSummaryStatistics stats3 =
                Person.getECPeople().summarizeInt(Person::getAge);
        return new Object[]{stats1, stats2, stats3};
    }

    @Benchmark
    public Map<Integer, List<Person>> filterAndGroupByAgeJDK_serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public MutableListMultimap<Integer, Person> filterAndGroupByAgeECEager_serial()
    {
        MutableListMultimap<Integer, Person> grouped =
                Person.getECPeople()
                        .select(person -> person.getHeightInInches() < 150)
                        .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Multimap<Integer, Person> filterAndGroupByAgeECLazy_serial()
    {
        Multimap<Integer, Person> grouped = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < 150)
                .groupBy(Person::getAge);
        return grouped;
    }

    @Benchmark
    public Map<Integer, List<Person>> filterJDK_serial()
    {
        Map<Integer, List<Person>> grouped =
                Person.getJDKPeople().stream()
                        .filter(person -> person.getHeightInInches() < 150)
                        .filter(person -> person.getHeightInInches() > 80)
                        .collect(Collectors.groupingBy(Person::getAge));
        return grouped;
    }

    @Benchmark
    public List<Person> filterECEager_serial()
    {
        final MutableList<Person> filtered = Person.getECPeople()
                .select(person -> person.getHeightInInches() < 150)
                .select(person -> person.getHeightInInches() > 80);
        return filtered;
    }

    @Benchmark
    public List<Person> filterECLazy_serial()
    {
        List<Person> filtered = Person.getECPeople()
                .asLazy()
                .select(person -> person.getHeightInInches() < 150)
                .select(person -> person.getHeightInInches() > 80)
                .toList();
        return filtered;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECEager_serial()
    {
        MutableIntSet uniqueAges =
                Person.getECPeople().collectInt(Person::getAge, IntSets.mutable.empty());
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECLazy_serial()
    {
        MutableIntSet uniqueAges =
                Person.getECPeople().asLazy().collectInt(Person::getAge).toSet();
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsJDK_serial()
    {
        final Set<Integer> uniqueAges =
                Person.getJDKPeople().stream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        IntSummaryStatistics summary = uniqueAges.stream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsECStream_parallel()
    {
        MutableIntSet uniqueAges =
                Person.getECPeople()
                        .parallelStream()
                        .collect(Collectors2.collectInt(Person::getAge, IntSets.mutable::empty));
        IntSummaryStatistics summary = uniqueAges.summaryStatistics();
        return summary;
    }

    @Benchmark
    public IntSummaryStatistics uniqueAgesSummaryStatisticsJDK_parallel()
    {
        final Set<Integer> uniqueAges =
                Person.getJDKPeople().parallelStream()
                        .mapToInt(Person::getAge)
                        .boxed()
                        .collect(Collectors.toSet());
        IntSummaryStatistics summary = uniqueAges.parallelStream().mapToInt(i -> i).summaryStatistics();
        return summary;
    }
}

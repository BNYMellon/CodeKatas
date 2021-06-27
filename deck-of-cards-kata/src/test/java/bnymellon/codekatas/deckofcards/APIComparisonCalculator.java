/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.StringIterate;

import java.lang.reflect.Method;

public class APIComparisonCalculator {

    private final boolean includeParameterTypesInMethods;

    public APIComparisonCalculator(boolean includeParameterTypesInMethods) {
        this.includeParameterTypesInMethods = includeParameterTypesInMethods;
    }

    public Twin<MutableSortedSet<String>> compare(Class<?> leftClass, Class<?> rightClass) {
        Twin<Class<?>> classPair = Tuples.twin(leftClass, rightClass);
        MutableSortedSet<String> leftMethods =
                this.getMethodNames(classPair.getOne());
        MutableSortedSet<String> rightMethods =
                this.getMethodNames(classPair.getTwo());

        MutableSortedSet<String> symmetricDifference = leftMethods.symmetricDifference(rightMethods);
        this.output(classPair, "Symmetric Difference",
                symmetricDifference);
        MutableSortedSet<String> intersection = leftMethods.intersect(rightMethods);
        this.output(classPair, "Intersection",
                intersection);
        return Tuples.twin(symmetricDifference, intersection);
    }

    private void output(Twin<Class<?>> classPair,
                        String operation,
                        RichIterable<String> strings) {
        this.outputTitle(operation + " (" + this.classNames(classPair) + ")");
        this.outputGroupByToString(strings);
    }

    private String classNames(Twin<Class<?>> classPair) {
        return classPair.getOne().getSimpleName() +
                ", " +
                classPair.getTwo().getSimpleName();
    }

    private MutableSortedSet<String> getMethodNames(Class<?> classOne) {
        return ArrayIterate.collect(
                classOne.getMethods(),
                this::methodNamePlusParms,
                SortedSets.mutable.empty());
    }

    private String methodNamePlusParms(Method method) {
        return this.includeParameterTypesInMethods ?
                method.getName() + "(" + this.parameterNames(method) + ")" :
                method.getName();
    }

    private String parameterNames(Method method) {
        return ArrayIterate.collect(
                method.getParameters(),
                parm -> parm.getType().getSimpleName())
                .makeString();
    }

    private void outputTitle(String title) {
        System.out.println(title);
        System.out.println(StringIterate.repeat('-', (title).length()));
    }

    private void outputGroupByToString(RichIterable<String> methods) {
        String output = methods.groupBy(string -> string.charAt(0))
                .keyMultiValuePairsView()
                .toSortedListBy(Pair::getOne)
                .makeString("\n");

        System.out.println(output);
        System.out.println();
    }
}

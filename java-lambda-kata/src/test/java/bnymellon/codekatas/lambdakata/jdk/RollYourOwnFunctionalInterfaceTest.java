/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.lambdakata.jdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RollYourOwnFunctionalInterfaceTest
{
    @Test
    public void addFourInts()
    {
        // TODO create a Functional Interface that accepts four ints and write a lambda which adds them together
        FourIntFunction function = () -> 0;
        Assertions.assertEquals(10, this.applyFourIntFunction(1, 2, 3, 4, function));
    }

    @Test
    public void multiplyFourInts()
    {
        // TODO use the sam Functional Interface and write a lambda which multiplies them together
        FourIntFunction function = () -> 0;
        Assertions.assertEquals(16, this.applyFourIntFunction(2, 2, 2, 2, function));
    }

    @Test
    public void addFirstThreeIntsAndDivideByLast()
    {
        // TODO use the sam Functional Interface and write a lambda which adds the first three together and divides by last
        FourIntFunction function = () -> 0;
        Assertions.assertEquals(3, this.applyFourIntFunction(1, 1, 1, 1, function));
    }

    private int applyFourIntFunction(int one, int two, int three, int four, FourIntFunction function)
    {
        // TODO - Fix this code to call a method on function with the four parameters
        return function.hashCode();
    }

    @FunctionalInterface
    public interface FourIntFunction
    {
        // TODO - Fix this method signature
        int writeAFourParameterMethod();
    }
}

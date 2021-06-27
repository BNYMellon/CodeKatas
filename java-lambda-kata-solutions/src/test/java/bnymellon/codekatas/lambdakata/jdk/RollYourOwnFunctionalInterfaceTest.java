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
        FourIntFunction function = (one, two, three, four) -> one + two + three + four;
        Assertions.assertEquals(10, this.applyFourIntFunction(1, 2, 3, 4, function));
    }

    @Test
    public void multiplyFourInts()
    {
        FourIntFunction function = (one, two, three, four) -> one * two * three * four;
        Assertions.assertEquals(16, this.applyFourIntFunction(2, 2, 2, 2, function));
    }

    @Test
    public void addFirstThreeIntsAndDivideByLast()
    {
        FourIntFunction function = (one, two, three, four) -> (one + two + three) / four;
        Assertions.assertEquals(3, this.applyFourIntFunction(1, 1, 1, 1, function));
    }

    private int applyFourIntFunction(int one, int two, int three, int four, FourIntFunction function)
    {
        return function.writeAFourParameterMethod(one, two, three, four);
    }

    @FunctionalInterface
    public interface FourIntFunction
    {
        int writeAFourParameterMethod(int one, int two, int three, int four);
    }
}

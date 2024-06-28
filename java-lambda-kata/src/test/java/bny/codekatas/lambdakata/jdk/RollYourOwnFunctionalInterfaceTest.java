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

package bny.codekatas.lambdakata.jdk;

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

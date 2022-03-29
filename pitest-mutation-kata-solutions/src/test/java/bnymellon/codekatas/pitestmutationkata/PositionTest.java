/*
 * Copyright 2022 The Bank of New York Mellon.
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
package bnymellon.codekatas.pitestmutationkata;

import org.junit.jupiter.api.Test;

import static org.eclipse.collections.impl.test.Verify.assertEqualsAndHashCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PositionTest
{
    private static final String POSITION_ID = "id1";

    @Test
    public void position()
    {
        Position position = this.newPosition();
        assertNotNull(position.getPositionId());
        assertEquals(5, position.getQuantity());
        assertEquals(10, position.getValueOne());
        assertEquals(20, position.getValueTwo());
        assertEquals(30, position.getValueThree());
        assertEquals(0, position.getAdditionCounter());

        assertEquals(POSITION_ID, position.getPositionId());
    }

    @Test
    public void add()
    {
        Position position = this.newPosition();
        Position positionTwo = new Position("id2", 2, 100, 200, 300);
        position = position.add(positionTwo);
        assertEquals(110, position.getValueOne());
        assertEquals(220, position.getValueTwo());

        assertEquals(7, position.getQuantity());
        assertEquals(330, position.getValueThree());
        assertEquals(1, position.getAdditionCounter());
    }

    @Test
    public void testEqualsHashcode()
    {
        Position positionOne = this.newPosition();
        Position positionTwo = this.newPosition();
        assertEqualsAndHashCode(positionOne, positionTwo);
        assertNotEquals(positionOne, new Position("id2", 0, 0, 0, 0));

        assertNotEquals(0, positionOne.hashCode());
    }

    private Position newPosition()
    {
        return new Position(POSITION_ID, 5, 10, 20, 30);
    }
}

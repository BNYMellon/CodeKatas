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
package bny.codekatas.pitestmutationkata;

import java.util.Objects;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

/**
 * This class represents a simplistic implementation of Position domain object.
 */
public class Position
{
    public static final int VALUE_ONE_INDEX = 0;
    public static final int VALUE_TWO_INDEX = 1;
    public static final int VALUE_THREE_INDEX = 2;

    /**
     * Unique identifier of the {@link Position}.
     */
    private final String positionId;

    /**
     * Position values.
     */
    private final IntArrayList values;

    /**
     * Position quantity.
     */
    private int quantity;

    /**
     * Number of times method {@link #add(Position)} was called.
     */
    private int additionCounter;

    public Position(String positionId, int quantity, int valueOne, int valueTwo, int valueThree)
    {
        this.positionId = positionId;
        this.values = new IntArrayList(valueOne, valueTwo, valueThree);
        this.quantity = quantity;
    }

    public int getValueOne()
    {
        return this.values.get(VALUE_ONE_INDEX);
    }

    public int getValueTwo()
    {
        return this.values.get(VALUE_TWO_INDEX);
    }

    public int getValueThree()
    {
        return this.values.get(VALUE_THREE_INDEX);
    }

    public String getPositionId()
    {
        return this.positionId;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public int getAdditionCounter()
    {
        return this.additionCounter;
    }

    /**
     * Adds another position. Mutates quantity, values and addition counter of this instance.
     * @param position {@link Position} to add
     * @return itself
     */
    public Position add(Position position)
    {
        this.quantity += position.getQuantity();
        for (int i = 1; i <= this.values.size(); i++)
        {
            int index = i - 1;
            this.values.set(index, this.values.get(index) + position.values.get(index));
        }
        this.additionCounter += 1;
        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Position position = (Position) o;
        return this.quantity == position.quantity
                && this.additionCounter == position.additionCounter
                && this.positionId.equals(position.positionId)
                && this.values.equals(position.values);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.positionId, this.values, this.quantity, this.additionCounter);
    }
}

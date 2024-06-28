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

package bny.codekatas.coffeeshopkata.food;

import java.util.Objects;

public class Bagel implements BakeryItem
{
    private final BagelType bagelType;
    private final SpreadType spreadType;
    private final boolean toasted;

    public Bagel(BagelType bagelType, SpreadType spreadType, boolean toasted)
    {
        this.bagelType = bagelType;
        this.spreadType = spreadType;
        this.toasted = toasted;
    }

    @Override
    public double getPrice()
    {
        return 2.50;
    }

    public boolean isToasted()
    {
        return toasted;
    }

    public BagelType getBagelType()
    {
        return bagelType;
    }

    public SpreadType getSpreadType()
    {
        return spreadType;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Bagel)
        {
            Bagel bagel = (Bagel) obj;
            return this.isToasted() == bagel.isToasted() && this.getBagelType() == bagel.getBagelType()
                    && this.getSpreadType() == bagel.getSpreadType();
        }
        return false;
    }

    @Override
    public String toString()
    {
        return String.format("Bagel[bagelType=%s, spreadType=%s, toasted=%s]",
                this.getBagelType(), this.getSpreadType(), this.isToasted());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(bagelType, spreadType, toasted);
    }
}

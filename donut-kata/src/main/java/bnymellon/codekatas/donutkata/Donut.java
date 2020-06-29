/*
 * Copyright 2020 The Bank of New York Mellon.
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

package bnymellon.codekatas.donutkata;

public class Donut
{
    private final DonutType type;
    private final double price;

    public Donut(DonutType type, double price)
    {
        this.type = type;
        this.price = price;
    }

    public DonutType getType()
    {
        return this.type;
    }

    public double getPrice()
    {
        return this.price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }
        Donut donut = (Donut) o;
        return Double.compare(donut.price, this.price) == 0 &&
                this.type == donut.type;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = this.type.hashCode();
        temp = Double.doubleToLongBits(this.price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return "Donut(" +
                "type=" + this.type +
                ", price=" + this.price +
                ')';
    }
}

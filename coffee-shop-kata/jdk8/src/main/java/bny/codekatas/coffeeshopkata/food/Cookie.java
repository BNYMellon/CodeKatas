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

public class Cookie implements BakeryItem
{
    private final CookieType cookieType;
    private final boolean warmed;

    public Cookie(CookieType cookieType, boolean warmed)
    {
        this.cookieType = cookieType;
        this.warmed = warmed;
    }

    @Override
    public double getPrice()
    {
        return 1.25;
    }

    public boolean isWarmed()
    {
        return warmed;
    }

    public CookieType getCookieType()
    {
        return cookieType;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Cookie)
        {
            Cookie cookie = (Cookie) obj;
            return this.isWarmed() == cookie.isWarmed() && this.getCookieType() == cookie.getCookieType();
        }
        return false;
    }

    @Override
    public String toString()
    {
        return String.format("Cookie[cookieType=%s, warmed=%s]",
                this.getCookieType(), this.isWarmed());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cookieType, warmed);
    }
}

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

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.DoubleSummaryStatistics;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DonutShopTest
{
    private final Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
    private final LocalDate today = LocalDate.now(this.clock);
    private final LocalDate tomorrow = this.today.plusDays(1);
    private final LocalDate yesterday = this.today.minusDays(1);
    private DonutShop donutShop;

    @Before
    public void setup()
    {
        this.donutShop = new DonutShop();
        this.donutShop.makeDonuts(DonutType.BOSTON_CREAM, 10);
        this.donutShop.makeDonuts(DonutType.BAVARIAN_CREAM, 10);
        this.donutShop.makeDonuts(DonutType.BLUEBERRY, 10);
        this.donutShop.makeDonuts(DonutType.GLAZED, 10);
        this.donutShop.makeDonuts(DonutType.OLD_FASHIONED, 10);
        this.donutShop.makeDonuts(DonutType.PUMPKIN, 10);
        this.donutShop.makeDonuts(DonutType.JELLY, 10);
        this.donutShop.makeDonuts(DonutType.VANILLA_FROSTED, 10);

        Delivery delivery1 =
                this.donutShop.deliverOrder("Ted Smith", this.today, "BC:2,BA:1,B:2");
        Assert.assertEquals(6.75d, delivery1.getTotalPrice(), 0.001);
        Delivery delivery2 =
                this.donutShop.deliverOrder("Mary Williams", this.today, "BC:1,G:1");
        Assert.assertEquals(2.70d, delivery2.getTotalPrice(), 0.001);
        Delivery delivery3 =
                this.donutShop.deliverOrder("Sally Prince", this.tomorrow, "BC:6,P:2,B:2,OF:2");
        Assert.assertEquals(12.0d, delivery3.getTotalPrice(), 0.001);
        Delivery delivery4 =
                this.donutShop.deliverOrder("Donnie Dapper", this.yesterday, "BC:6,P:2,B:2,OF:2,G:10");
        Assert.assertEquals(20.9d, delivery4.getTotalPrice(), 0.001);

        System.out.println(this.donutShop);
    }

    @After
    public void tearDown()
    {
        this.donutShop = null;
    }

    @Test
    public void getTop2Donuts()
    {
        MutableList<ObjectIntPair<DonutType>> expected =
                Lists.mutable.with(
                        PrimitiveTuples.pair(DonutType.BOSTON_CREAM, 15),
                        PrimitiveTuples.pair(DonutType.GLAZED, 11));
        Assert.assertEquals(expected, this.donutShop.getTopDonuts(2));
    }

    @Test
    public void totalDeliveryValueByDate()
    {
        Assert.assertEquals(
                9.45d,
                this.donutShop.getTotalDeliveryValueFor(this.today),
                0.001);
        Assert.assertEquals(
                12.0d,
                this.donutShop.getTotalDeliveryValueFor(this.tomorrow),
                0.001);
        Assert.assertEquals(
                20.9d,
                this.donutShop.getTotalDeliveryValueFor(this.yesterday),
                0.001);
    }

    @Test
    public void getTopCustomer()
    {
        Assert.assertEquals("Donnie Dapper", this.donutShop.getTopCustomer().getName());
    }

    @Test
    public void getCustomersByDonutTypesOrdered()
    {
        Multimap<DonutType, Customer> multimap = this.donutShop.getCustomersByDonutTypesOrdered();
        Assert.assertEquals(6, multimap.keySet().size());
        Verify.assertIterableSize(1, multimap.get(DonutType.BAVARIAN_CREAM));
        Verify.assertAllSatisfy(
                multimap.get(DonutType.BAVARIAN_CREAM),
                customer -> customer.named("Ted Smith"));
    }

    @Test
    public void getDonutPriceStatistics()
    {
        DoubleSummaryStatistics stats1 =
                this.donutShop.getDonutPriceStatistics(this.today, this.today);
        Assert.assertEquals(9.45d, stats1.getSum(), 0.01);
        Assert.assertEquals(1.35d, stats1.getAverage(), 0.01);
        Assert.assertEquals(7, stats1.getCount(), 0.01);

        DoubleSummaryStatistics stats2 =
                this.donutShop.getDonutPriceStatistics(this.tomorrow, this.tomorrow);
        Assert.assertEquals(12.0d, stats2.getSum(), 0.01);
        Assert.assertEquals(1.0d, stats2.getAverage(), 0.01);
        Assert.assertEquals(12, stats2.getCount(), 0.01);

        DoubleSummaryStatistics stats3 =
                this.donutShop.getDonutPriceStatistics(this.yesterday, this.yesterday);
        Assert.assertEquals(20.9d, stats3.getSum(), 0.001);
        Assert.assertEquals(0.95d, stats3.getAverage(), 0.01);
        Assert.assertEquals(22, stats3.getCount(), 0.01);

        DoubleSummaryStatistics statsTotal =
                this.donutShop.getDonutPriceStatistics(this.yesterday, this.tomorrow);
        Assert.assertEquals(42.35d, statsTotal.getSum(), 0.01);
        Assert.assertEquals(1.03d, statsTotal.getAverage(), 0.01);
        Assert.assertEquals(41, statsTotal.getCount(), 0.01);
        Assert.assertEquals(0.95, statsTotal.getMin(), 0.01);
        Assert.assertEquals(1.35, statsTotal.getMax(), 0.01);
    }
}

package bnymellon.codekatas.deckofcards.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class ArrayListMultimapTest {
    private ArrayListMultimap<String, Integer> testObj;

    @Before
    public void setup() {
        this.testObj = new ArrayListMultimap<>();
    }

    @Test
    public void isConstructed() {
        Assert.assertNotNull(new ArrayListMultimap<>());
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(this.testObj.isEmpty());
        this.testObj.put("A", 1);
        Assert.assertFalse(this.testObj.isEmpty());
    }

    @Test
    public void get_put() {
        this.testObj.put("A", 1);
        Assert.assertEquals(MutableList.of(1), this.testObj.get("A"));
        Assert.assertEquals(1, this.testObj.size());
        this.testObj.put("A", 2);
        Assert.assertEquals(MutableList.of(1, 2), this.testObj.get("A"));
        Assert.assertEquals(2, this.testObj.size());
        this.testObj.put("A", 1);
        Assert.assertEquals(MutableList.of(1, 2, 1), this.testObj.get("A"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.put("B", 10);
        Assert.assertEquals(MutableList.of(10), this.testObj.get("B"));
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.put("B", 11);
        Assert.assertEquals(MutableList.of(10, 11), this.testObj.get("B"));
        Assert.assertEquals(5, this.testObj.size());
        this.testObj.put("A", 2);
        Assert.assertEquals(MutableList.of(1, 2, 1, 2), this.testObj.get("A"));
        Assert.assertEquals(6, this.testObj.size());
    }

    @Test
    public void get_putIterable() {
        this.testObj.put("A", MutableList.of(1));
        Assert.assertEquals(MutableList.of(1), this.testObj.get("A"));
        Assert.assertEquals(1, this.testObj.size());
        this.testObj.put("A", MutableList.of(2, 1));
        Assert.assertEquals(MutableList.of(1, 2, 1), this.testObj.get("A"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.put("B", MutableList.of(10, 11));
        Assert.assertEquals(MutableList.of(10, 11), this.testObj.get("B"));
        Assert.assertEquals(5, this.testObj.size());
        this.testObj.put("A", MutableList.of(2, 3, 4));
        Assert.assertEquals(MutableList.of(1, 2, 1, 2, 3, 4), this.testObj.get("A"));
        Assert.assertEquals(8, this.testObj.size());
    }

    @Test
    public void putAll() {
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        Assert.assertEquals(MutableList.of(1, 2, 3), this.testObj.get("A"));
        Assert.assertEquals(3, this.testObj.size());
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(MutableList.of(10, 10), this.testObj.get("B"));
        Assert.assertEquals(5, this.testObj.size());
    }

    @Test
    public void remove() {
        Assert.assertEquals(MutableList.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(5, this.testObj.size());
        this.testObj.remove("A");
        Assert.assertEquals(2, this.testObj.size());
        this.testObj.remove("B");
        Assert.assertEquals(0, this.testObj.size());
    }

    @Test
    public void remove_keyValue() {
        Assert.assertEquals(MutableList.empty(), this.testObj.remove("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(6, this.testObj.size());
        this.testObj.remove("A", 1);
        Assert.assertEquals(MutableList.of(2, 3, 1), this.testObj.get("A"));
        Assert.assertEquals(5, this.testObj.size());
        this.testObj.remove("B", 10);
        MutableList<Integer> getForB = this.testObj.get("B");
        Assert.assertEquals(MutableList.of(10), getForB);
        Assert.assertEquals(4, this.testObj.size());
        this.testObj.remove("B", 10);
        Assert.assertEquals(MutableList.empty(), getForB);
        Assert.assertEquals(3, this.testObj.size());
        Assert.assertNotSame(getForB, this.testObj.get("B"));
    }

    @Test
    public void containsKey() {
        Assert.assertFalse(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        Assert.assertTrue(this.testObj.containsKey("A"));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertTrue(this.testObj.containsKey("A"));
        Assert.assertTrue(this.testObj.containsKey("B"));
    }

    @Test
    public void containsValue() {
        Assert.assertFalse(this.testObj.containsValue(1));
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        Assert.assertTrue(this.testObj.containsValue(1));
        Assert.assertTrue(this.testObj.containsValue(2));
        Assert.assertTrue(this.testObj.containsValue(3));
        Assert.assertFalse(this.testObj.containsValue(4));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertTrue(this.testObj.containsValue(10));
        Assert.assertFalse(this.testObj.containsValue(4));
    }

    @Test
    public void clear() {
        Assert.assertEquals(0, this.testObj.size());
        this.testObj.clear();
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(6, this.testObj.size());
        this.testObj.clear();
        Assert.assertEquals(0, this.testObj.size());
    }

    @Test
    public void keySet() {
        Assert.assertEquals(new HashSet<>(), this.testObj.keySet());
        this.testObj.putAll(MutableMap.of("A", MutableList.of(1, 2, 3, 1)));
        this.testObj.putAll(MutableMap.of("B", MutableList.of(10, 10)));
        Assert.assertEquals(MutableSet.of("A", "B"), this.testObj.keySet());
    }
}

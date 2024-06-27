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
package bny.codekatas.deckofcards.custom.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HashBag<T> implements MutableBag<T>
{
    private MutableMap<T, Integer> backingMap = MutableMap.empty();
    private int size = 0;

    @Override
    public int sizeDistinct()
    {
        return this.backingMap.size();
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        return this.backingMap.containsKey(o);
    }

    @Override
    public Object[] toArray()
    {
        Object[] result = new Object[this.size()];
        this.forEachWithIndex((each, index) -> result[index] = each);
        return result;
    }

    private void forEachWithIndex(BiConsumer<? super T, Integer> biConsumer)
    {
        Counter index = new Counter();
        this.backingMap.forEach((key, count) ->
        {
            for (int i = 0; i < count; i++)
            {
                biConsumer.accept(key, index.getCount());
                index.increment();
            }
        });
    }

    @Override
    public <T1> T1[] toArray(T1[] array)
    {
        int size = this.size();
        T1[] result = array.length < size
                ? (T1[]) Array.newInstance(array.getClass().getComponentType(), size)
                : array;

        this.forEachWithIndex((each, index) -> result[index] = (T1) each);
        if (result.length > size)
        {
            result[size] = null;
        }
        return result;
    }

    @Override
    public boolean add(T element)
    {
        this.backingMap.merge(element, 1, (existingValue, newValue) -> existingValue + 1);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o)
    {
        return this.removeOccurrences((T) o, Integer.MAX_VALUE);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return c.stream().allMatch(each -> this.backingMap.containsKey(each));
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        int sizeBefore = this.size;
        c.forEach(each -> this.addOccurrence(each));
        return sizeBefore != this.size;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        int sizeBefore = this.size;
        c.forEach(each -> this.removeOccurrences((T) each, Integer.MAX_VALUE));
        return sizeBefore != this.size;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        MutableMap<T, Integer> map = MutableMap.empty();
        Counter counter = new Counter();
        c.forEach(each ->
        {
            Integer occurrences = this.backingMap.get(each);
            if (occurrences != null)
            {
                map.put((T) each, occurrences);
                counter.incrementBy(occurrences);
            }
        });
        if (!map.isEmpty())
        {
            this.backingMap = map;
            this.size = counter.getCount();
        }
        return counter.getCount() > 0;
    }

    @Override
    public void clear()
    {
        this.backingMap.clear();
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new BagIterator();
    }

    @Override
    public int getOccurrences(T element)
    {
        return this.backingMap.getOrDefault(element, 0);
    }

    @Override
    public boolean addOccurrence(T element)
    {
        return this.addOccurrences(element, 1);
    }

    @Override
    public boolean addOccurrences(T element, int occurrences)
    {
        int sizeBefore = size;
        Integer merged = this.backingMap.merge(element, occurrences, (existingCount, e) -> existingCount + e);
        size = size + occurrences;
        return sizeBefore != size;
    }

    @Override
    public boolean removeOccurrence(T element)
    {
        return this.removeOccurrences(element, 1);
    }

    @Override
    public boolean removeOccurrences(T element, int occurrences)
    {
        int sizeBefore = size;
        Integer existing = this.backingMap.get(element);
        if (existing != null)
        {
            Integer newCount = existing - occurrences;
            if (newCount <= 0)
            {
                this.backingMap.remove(element);
                size = size - existing;
            }
            else
            {
                this.backingMap.put(element, newCount);
                size = size - occurrences;
            }
        }
        return sizeBefore != size;
    }

    @Override
    public void forEachWithOccurrences(BiConsumer<? super T, Integer> biConsumer)
    {
        this.backingMap.forEach(biConsumer);
    }

    @Override
    public void forEach(Consumer<? super T> consumer)
    {
        this.backingMap.forEach((key, count) ->
        {
            for (int i = 0; i < count; i++)
            {
                consumer.accept(key);
            }
        });
    }

    @Override
    public int hashCode()
    {
        return this.backingMap.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof Bag))
        {
            return false;
        }
        Bag<T> bag = (Bag<T>) other;
        if (this.sizeDistinct() != bag.sizeDistinct())
        {
            return false;
        }

        return this.backingMap
                .keySet()
                .stream()
                .allMatch(element -> bag.getOccurrences(element) == this.backingMap.get(element));
    }

    private class BagIterator implements Iterator<T>
    {
        private final Iterator<T> iterator = HashBag.this.backingMap.keySet().iterator();

        private T currentItem;
        private int occurrences;
        private boolean canRemove;

        @Override
        public boolean hasNext()
        {
            return this.occurrences > 0 || this.iterator.hasNext();
        }

        @Override
        public T next()
        {
            if (this.occurrences == 0)
            {
                this.currentItem = this.iterator.next();
                this.occurrences = HashBag.this.getOccurrences(this.currentItem);
            }
            this.occurrences--;
            this.canRemove = true;
            return this.currentItem;
        }

        @Override
        public void remove()
        {
            if (!this.canRemove)
            {
                throw new IllegalStateException();
            }
            if (this.occurrences == 0)
            {
                this.iterator.remove();
                HashBag.this.size--;
            }
            else
            {
                HashBag.this.removeOccurrence(this.currentItem);
            }
            this.canRemove = false;
        }
    }

    private class Counter
    {
        private int count;

        public Counter()
        {
            this.count = 0;
        }

        public void increment()
        {
            this.count++;
        }

        public void decrement()
        {
            this.count--;
        }

        public void incrementBy(int increment)
        {
            this.count += increment;
        }

        public int getCount()
        {
            return this.count;
        }

        public void reset()
        {
            this.count = 0;
        }
    }
}

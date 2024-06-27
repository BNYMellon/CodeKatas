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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class AbstractMutableMultimap<K, V, C extends MutableCollection<V>>
        implements MutableMultimap<K, V>
{
    protected abstract MutableMap<K, C> getBackingMap();

    protected abstract void incrementSizeBy(int increment);

    protected abstract void decrementSizeBy(int decrement);

    protected abstract C createEmptyValueCollection();

    @Override
    public boolean isEmpty()
    {
        return this.getBackingMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return this.getBackingMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        Collection<C> values = this.getBackingMap().values();
        return values.stream().anyMatch(each -> each.contains(value));
    }

    public void clear()
    {
        this.getBackingMap().clear();
        this.decrementSizeBy(this.size());
    }

    @Override
    public Set<K> keySet()
    {
        return this.getBackingMap().keySet();
    }

    @Override
    public C get(Object key)
    {
        C value = this.getBackingMap().get(key);
        return value == null ? this.createEmptyValueCollection() : value;
    }

    @Override
    public boolean put(K key, RichIterable<V> value)
    {
        boolean isEmpty = value.anyMatch(each -> true);
        if (!isEmpty)
        {
            return false;
        }
        C existing = this.getBackingMap().get(key);
        if (existing == null)
        {
            existing = this.createEmptyValueCollection();
            this.getBackingMap().put(key, existing);
        }
        int previousSize = existing.size();
        existing.addAllIterable(value);
        int newSize = existing.size();
        this.incrementSizeBy(newSize - previousSize);
        return newSize != previousSize;
    }

    @Override
    public boolean put(K key, V value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Value cannot be null");
        }
        C existing = this.getBackingMap().get(key);
        if (existing == null)
        {
            existing = this.createEmptyValueCollection();
            this.getBackingMap().put(key, existing);
        }
        int previousSize = existing.size();
        existing.add(value);
        int newSize = existing.size();
        this.incrementSizeBy(newSize - previousSize);
        return newSize != previousSize;
    }

    @Override
    public C remove(Object key)
    {
        C removed = this.getBackingMap().remove(key);
        if (removed != null)
        {
            this.decrementSizeBy(removed.size());
            return removed;
        }
        return this.createEmptyValueCollection();
    }

    @Override
    public boolean remove(K key, V value)
    {
        C existing = this.getBackingMap().get(key);
        if (existing != null)
        {
            int previousSize = existing.size();
            existing.remove(value);
            int newSize = existing.size();
            this.decrementSizeBy(previousSize - newSize);
            if (newSize == 0)
            {
                this.getBackingMap().remove(key);
            }
            return newSize != previousSize;
        }
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends RichIterable<V>> map)
    {
        if (!map.isEmpty())
        {
            map.forEach((key, values) -> {
                C existing = this.getBackingMap().get(key);
                if (existing == null)
                {
                    existing = this.createEmptyValueCollection();
                    this.getBackingMap().put(key, existing);
                }
                int previousSize = existing.size();
                existing.addAllIterable(values);
                int newSize = existing.size();
                this.incrementSizeBy(newSize - previousSize);
            });
        }
    }

    @Override
    public void forEach(BiConsumer<K, V> biConsumer)
    {
        this.getBackingMap()
                .keySet()
                .forEach(key -> {
                    C values = this.getBackingMap().get(key);
                    values.forEach(value -> biConsumer.accept(key, value));
                });
    }

    @Override
    public int hashCode()
    {
        return this.getBackingMap().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MutableMultimap)
        {
            return this.getBackingMap().equals(((AbstractMutableMultimap) obj).getBackingMap());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return this.getBackingMap().toString();
    }
}

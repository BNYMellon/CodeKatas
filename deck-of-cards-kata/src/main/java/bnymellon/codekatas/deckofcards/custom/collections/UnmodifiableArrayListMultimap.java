/**
 * Copyright 2021 BNYMellon. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class UnmodifiableArrayListMultimap<K, V>
        extends AbstractMutableMultimap<K, V, MutableList<V>>
        implements MutableListMultimap<K, V>
{

    private final ArrayListMultimap<K, V> BACKING_MULTIMAP;

    UnmodifiableArrayListMultimap(MutableMultimap<K, V> multimap)
    {
        ArrayListMultimap<K, V> arrayListMultimap = ArrayListMultimap.newMultimap();
        multimap.forEach(arrayListMultimap::put);
        this.BACKING_MULTIMAP = arrayListMultimap;
    }

    @Override
    public MutableListMultimap<K, V> asUnmodifiable()
    {
        return this;
    }

    @Override
    protected MutableMap<K, MutableList<V>> getBackingMap()
    {
        throw new UnsupportedOperationException("Should not reach here!");
    }

    @Override
    protected void incrementSizeBy(int increment)
    {
        throw new UnsupportedOperationException("Should not reach here!");
    }

    @Override
    protected void decrementSizeBy(int decrement)
    {
        throw new UnsupportedOperationException("Should not reach here!");
    }

    @Override
    protected MutableList<V> createEmptyValueCollection()
    {
        throw new UnsupportedOperationException("Should not reach here!");
    }

    @Override
    public int size()
    {
        return this.BACKING_MULTIMAP.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.BACKING_MULTIMAP.isEmpty();
    }

    @Override
    public boolean containsKey(Object key)
    {
        return this.BACKING_MULTIMAP.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return this.BACKING_MULTIMAP.containsValue(value);
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("Not allowed to perform clear() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public Set<K> keySet()
    {
        return this.BACKING_MULTIMAP.keySet();
    }

    @Override
    public MutableList<V> get(Object key)
    {
        return this.BACKING_MULTIMAP.get(key).asUnmodifiable();
    }

    @Override
    public void forEach(BiConsumer<K, V> biConsumer)
    {
        this.BACKING_MULTIMAP.forEach(biConsumer);
    }

    @Override
    public boolean put(K key, RichIterable<V> value)
    {
        throw new UnsupportedOperationException("Not allowed to perform put() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public boolean put(K key, V value)
    {
        throw new UnsupportedOperationException("Not allowed to perform put() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public MutableList<V> remove(Object key)
    {
        throw new UnsupportedOperationException("Not allowed to perform remove() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public boolean remove(K key, V value)
    {
        throw new UnsupportedOperationException("Not allowed to perform remove() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public void putAll(Map<? extends K, ? extends RichIterable<V>> map)
    {
        throw new UnsupportedOperationException("Not allowed to perform putAll() on "
                + UnmodifiableArrayListMultimap.class);
    }

    @Override
    public int hashCode()
    {
        return this.BACKING_MULTIMAP.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.BACKING_MULTIMAP.equals(obj);
    }
}

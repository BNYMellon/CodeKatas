package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Multimap<K, V> {
    int size();

    boolean isEmpty();

    RichIterable<V> get(Object key);

    boolean put(K key, V value);

    boolean put(K key, RichIterable<V> value);

    void putAll(Map<? extends K, ? extends RichIterable<V>> map);

    RichIterable<V> remove(K key);

    boolean remove(K key, V value);

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    public void clear();

    Set<K> keySet();
}

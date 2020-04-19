package bnymellon.codekatas.deckofcards.custom.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMultimap<K, V, C extends MutableCollection<V>>
        implements Multimap<K, V> {
    protected abstract MutableMap<K, C> getBackingMap();

    protected abstract void incrementSizeBy(int increment);

    protected abstract void decrementSizeBy(int decrement);

    protected abstract C createEmptyValueCollection();

    @Override
    public boolean isEmpty() {
        return this.getBackingMap().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getBackingMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        Collection<C> values = this.getBackingMap().values();
        return values.stream().anyMatch(each -> each.contains(value));
    }

    @Override
    public void clear() {
        this.getBackingMap().clear();
        this.decrementSizeBy(this.size());
    }

    @Override
    public Set<K> keySet() {
        return this.getBackingMap().keySet();
    }

    @Override
    public C get(Object key) {
        C value = this.getBackingMap().get(key);
        return value == null ? this.createEmptyValueCollection() : value;
    }

    @Override
    public boolean put(K key, RichIterable<V> value) {
        boolean isEmpty = value.anyMatch(each -> true);
        if (!isEmpty) {
            return false;
        }
        C existing = this.getBackingMap().get(key);
        if (existing == null) {
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
    public boolean put(K key, V value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        C existing = this.getBackingMap().get(key);
        if (existing == null) {
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
    public C remove(Object key) {
        C removed = this.getBackingMap().remove(key);
        if (removed != null) {
            this.decrementSizeBy(removed.size());
            return removed;
        }
        return this.createEmptyValueCollection();
    }

    @Override
    public boolean remove(K key, V value) {
        C existing = this.getBackingMap().get(key);
        if (existing != null) {
            int previousSize = existing.size();
            existing.remove(value);
            int newSize = existing.size();
            this.decrementSizeBy(previousSize - newSize);
            if (newSize == 0) {
                this.getBackingMap().remove(key);
            }
            return newSize != previousSize;
        }
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends RichIterable<V>> map) {
        if (!map.isEmpty()) {
            map.forEach((key, values) ->
            {
                C existing = this.getBackingMap().get(key);
                if (existing == null) {
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
}

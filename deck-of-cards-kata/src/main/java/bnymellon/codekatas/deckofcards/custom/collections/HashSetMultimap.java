package bnymellon.codekatas.deckofcards.custom.collections;


public class HashSetMultimap<K, V> extends AbstractMultimap<K, V, MutableSet<V>> {
    private final MutableMap<K, MutableSet<V>> BACKING_MAP = MutableMap.empty();
    private int size;

    public static <K, V> HashSetMultimap<K, V> newMultimap() {
        return new HashSetMultimap<>();
    }

    @Override
    protected MutableMap<K, MutableSet<V>> getBackingMap() {
        return BACKING_MAP;
    }

    @Override
    protected void incrementSizeBy(int increment) {
        this.size += increment;
    }

    @Override
    protected void decrementSizeBy(int decrement) {
        this.size -= decrement;
    }

    @Override
    protected MutableSet<V> createEmptyValueCollection() {
        return MutableSet.empty();
    }

    @Override
    public int size() {
        return this.size;
    }
}

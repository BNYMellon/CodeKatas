package bnymellon.codekatas.deckofcards.custom.collections;

public class ArrayListMultimap<K, V> extends AbstractMultimap<K, V, MutableList<V>> {
    private final MutableMap<K, MutableList<V>> BACKING_MAP = MutableMap.empty();
    private int size;

    public static <K, V> ArrayListMultimap<K, V> newMultimap() {
        return new ArrayListMultimap<>();
    }

    @Override
    protected MutableMap<K, MutableList<V>> getBackingMap() {
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
    protected MutableList<V> createEmptyValueCollection() {
        return MutableList.empty();
    }

    @Override
    public int size() {
        return this.size;
    }
}

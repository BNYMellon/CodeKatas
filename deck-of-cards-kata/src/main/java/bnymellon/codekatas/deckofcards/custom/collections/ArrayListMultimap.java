/*
 * Copyright 2020 BNY Mellon.
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

    public UnmodifiableArrayListMultimap<K, V> asUnmodifiable() {
        return new UnmodifiableArrayListMultimap<>(this);
    }
}

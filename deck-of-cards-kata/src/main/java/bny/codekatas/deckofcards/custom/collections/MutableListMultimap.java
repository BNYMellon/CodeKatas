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

public interface MutableListMultimap<K, V> extends MutableMultimap<K, V>
{
    public static <K1, V1> MutableListMultimap<K1, V1> empty()
    {
        return ArrayListMultimap.newMultimap();
    }

    @Override
    MutableList<V> get(Object key);

    @Override
    MutableList<V> remove(K key);

    MutableListMultimap<K, V> asUnmodifiable();
}

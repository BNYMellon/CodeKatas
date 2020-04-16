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

import java.util.Map;

public interface MutableMap<K, V> extends Map<K, V> {
    public static <K1, V1> MutableMap<K1, V1> empty() {
        return new HashMap2<>();
    }

    public static <K1, V1> MutableMap<K1, V1> of(K1 key, V1 value) {
        return new HashMap2<K1, V1>().withKeyValue(key, value);
    }

    public static <K1, V1> MutableMap<K1, V1> of(K1 key1, V1 value1, K1 key2, V1 value2) {
        return new HashMap2<K1, V1>()
                .withKeyValue(key1, value1)
                .withKeyValue(key2, value2);
    }

    default MutableMap<K, V> asUnmodifiable() {
        return new UnmodifiableMutableMap<>(this);
    }
}

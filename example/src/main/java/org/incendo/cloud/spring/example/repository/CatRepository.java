//
// MIT License
//
// Copyright (c) 2023 Incendo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package org.incendo.cloud.spring.example.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.spring.example.model.Cat;
import org.springframework.stereotype.Repository;

@Repository
public class CatRepository {

    private final Map<@NonNull String, @NonNull Cat> cats = new HashMap<>();

    /**
     * Adds the cat.
     *
     * @param cat the cat
     */
    public void addCat(final @NonNull Cat cat) {
        this.cats.put(cat.name(), cat);
    }

    /**
     * Removes the cat with the given {@code name}.
     *
     * @param name the name
     * @return the removed cat, or {@code null}
     */
    public @Nullable Cat removeCat(final @NonNull String name) {
        return this.cats.remove(name);
    }

    /**
     * Returns the cats.
     *
     * @return the cats
     */
    public @NonNull Collection<@NonNull Cat> cats() {
        return List.copyOf(this.cats.values());
    }
}

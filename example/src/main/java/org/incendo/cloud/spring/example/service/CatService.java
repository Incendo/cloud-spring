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
package org.incendo.cloud.spring.example.service;

import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.spring.example.model.Cat;
import org.incendo.cloud.spring.example.repository.CatRepository;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    private final CatRepository catRepository;

    /**
     * Creates a new cat service.
     *
     * @param catRepository the cat repository
     */
    public CatService(final @NonNull CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * Adds a cat with the given {@code name}.
     *
     * @param cat the cat
     * @param override whether to override an existing cat
     */
    public void addCat(final @NonNull Cat cat, final boolean override) {
        if (override) {
            this.removeCat(cat.name());
        }
        this.catRepository.addCat(cat);
    }

    /**
     * Removes the cat with the given {@code name}.
     *
     * @param name the name
     * @return the removed cat, or {@code null}
     */
    public @Nullable Cat removeCat(final @NonNull String name) {
        return this.catRepository.removeCat(name);
    }

    /**
     * Returns the cats.
     *
     * @return the cats
     */
    public @NonNull Collection<@NonNull Cat> cats() {
        return this.catRepository.cats();
    }
}

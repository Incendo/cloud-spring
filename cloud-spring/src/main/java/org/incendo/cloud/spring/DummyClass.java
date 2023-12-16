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
package org.incendo.cloud.spring;

import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.immutables.value.Value;

@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "0.0.1")
public class DummyClass {

    private final Dummy dummy;

    /**
     * Creates a new dummy class
     */
    public DummyClass() {
        this.dummy = Dummy.builder().dummyCount(10).build();
    }

    /**
     * Returns the dummy.
     *
     * @return dummy
     */
    public @NonNull Dummy dummy() {
        return this.dummy;
    }


    @Value.Immutable
    @ImmutableStyle
    public interface AbstractDummy {

        /**
         * Returns the number of dummies.
         *
         * @return number of dummies
         */
        int dummyCount();
    }
}

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

/**
 * Function used to determine whether the shell has a given permission node.
 *
 * @param <C> the command sender type
 */
@FunctionalInterface
@API(status = API.Status.STABLE, since = "1.0.0")
public interface SpringCommandPermissionHandler<C> {

    /**
     * Returns a {@link SpringCommandPermissionHandler} that always returns {@code true}.
     *
     * @param <C> the command sender type
     * @return the permission handler
     */
    static <C> @NonNull SpringCommandPermissionHandler<C> alwaysTrue() {
        return permission -> true;
    }

    /**
     * Returns whether the shell has the given {@code permission}.
     *
     * @param permission the permission
     * @return {@code true} if the shell has the permission, {@code false} if not
     */
    boolean hasPermission(@NonNull String permission);
}

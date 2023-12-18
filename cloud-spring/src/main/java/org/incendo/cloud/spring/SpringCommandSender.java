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

import java.util.Objects;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.shell.command.CommandContext;

/**
 * Command sender for Spring.
 *
 * @since 1.0.0
 */
@API(status = API.Status.STABLE, since = "1.0.0")
public sealed interface SpringCommandSender permits SpringCommandSender.SpringCommandSenderImpl {

    /**
     * Returns the sender instance.
     *
     * @param context the context
     * @return the sender instance
     */
    static @NonNull SpringCommandSender sender(final @Nullable CommandContext context) {
        return new SpringCommandSenderImpl(context);
    }

    /**
     * Returns the context.
     *
     * <p>During suggestions the context will be {@code null}.</p>
     *
     * @return the context
     */
    @Nullable CommandContext context();

    /**
     * Writes the given {@code line} to the terminal.
     *
     * <p>A line break will be added to the line before it's written.</p>
     *
     * <p>Do <b>not</b> use this during suggestions, as {@link #context()} will be null.</p>
     *
     * @param line the line to write
     */
    default void writeLine(final @NonNull String line) {
        final CommandContext context = Objects.requireNonNull(this.context());
        context.getTerminal().writer().println(line);
    }


    @API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
    record SpringCommandSenderImpl(@Nullable CommandContext context) implements SpringCommandSender {
    }
}

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
package org.incendo.cloud.spring.event;

import cloud.commandframework.Command;
import java.io.Serial;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.shell.command.CommandContext;

@API(status = API.Status.STABLE, since = "1.0.0")
public final class CommandExecutionEvent<C> extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 5191664996917201150L;

    private final Command<C> command;
    private final CommandContext context;

    /**
     * Creates a new event.
     *
     * @param command the command
     * @param context the command context
     */
    public CommandExecutionEvent(final Command<C> command, final @NonNull CommandContext context) {
        super(command);
        this.command = command;
        this.context = context;
    }

    /**
     * Returns the command.
     *
     * @return the command
     */
    public @NonNull Command<C> command() {
        return this.command;
    }

    /**
     * Returns the context.
     *
     * @return the context
     */
    public @NonNull CommandContext context() {
        return this.context;
    }

    @Override
    public String toString() {
        return String.format("CommandExecutionEvent{command=%s,context=%s}", this.command(), this.context());
    }
}

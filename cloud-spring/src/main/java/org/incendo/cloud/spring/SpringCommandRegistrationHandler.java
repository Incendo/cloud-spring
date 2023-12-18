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

import cloud.commandframework.Command;
import cloud.commandframework.CommandComponent;
import cloud.commandframework.internal.CommandRegistrationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.spring.event.CommandExecutionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.shell.command.CommandCatalog;
import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.stereotype.Component;

@Component
@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
public class SpringCommandRegistrationHandler<C> implements CommandRegistrationHandler<C>, ApplicationEventPublisherAware,
        CommandExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCommandRegistrationHandler.class);

    private final CommandCatalog commandCatalog;
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a new registration handler.
     *
     * @param commandCatalog the catalog to register commands to
     */
    public SpringCommandRegistrationHandler(
            final @NonNull CommandCatalog commandCatalog
    ) {
        this.commandCatalog = commandCatalog;
    }

    @Override
    public final boolean registerCommand(final @NonNull Command<C> command) {
        LOGGER.debug("Registering command: {}", command);

        final List<String> literals = new ArrayList<>();
        for (final CommandComponent<C> component : command.components()) {
            if (component.type() == CommandComponent.ComponentType.LITERAL) {
                literals.add(component.name());
            }
        }

        final CommandRegistration registration = CommandRegistration.builder()
                .description(command.commandDescription().description().textDescription())
                .command(literals.toArray(new String[0]))
                .withTarget()
                .function(context -> {
                    final CommandExecutionEvent<C> event = new CommandExecutionEvent<>(command, context);
                    this.applicationEventPublisher.publishEvent(event);
                    return Objects.requireNonNull(event.result(), "result").getCommandContext().getOrDefault(
                            SpringCommandManager.OUTPUT,
                            null
                    );
                })
                .and()
                .withErrorHandling()
                .resolver(this)
                .and()
                .group(command.commandMeta().getOrDefault(SpringCommandManager.COMMAND_GROUP_KEY, null))
                .build();
        this.commandCatalog.register(registration);

        return true;
    }

    @Override
    public final void setApplicationEventPublisher(final @NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public final @Nullable CommandHandlingResult resolve(final @NonNull Exception exception) {
        if (exception instanceof FailureIndicationException failure) {
            return CommandHandlingResult.of("", 1);
        }
        return null;
    }
}

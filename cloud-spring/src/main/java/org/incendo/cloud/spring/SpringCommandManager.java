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

import cloud.commandframework.CommandManager;
import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.registrar.CommandRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@API(status = API.Status.STABLE, since = "1.0.0")
public class SpringCommandManager<C> extends CommandManager<C> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCommandManager.class);

    private final SpringCommandPermissionHandler<C> commandPermissionHandler;
    private final Collection<@NonNull CommandRegistrar<C>> commandRegistrars;

    /**
     * Creates a new command manager.
     *
     * @param commandExecutionCoordinatorResolver the resolver for the execution coordinator
     * @param commandPermissionHandler the permission handler
     * @param commandRegistrationHandler the registration handler
     * @param commandRegistrars registrars that provide commands
     */
    public SpringCommandManager(
            final @NonNull SpringCommandExecutionCoordinatorResolver<C> commandExecutionCoordinatorResolver,
            final @NonNull SpringCommandPermissionHandler<C> commandPermissionHandler,
            final @NonNull SpringCommandRegistrationHandler<C> commandRegistrationHandler,
            final @NonNull Collection<@NonNull CommandRegistrar<C>> commandRegistrars
    ) {
        super(commandExecutionCoordinatorResolver, commandRegistrationHandler);
        this.commandPermissionHandler = commandPermissionHandler;
        this.commandRegistrars = List.copyOf(commandRegistrars);
    }

    @Override
    public final boolean hasPermission(final @NonNull C sender, final @NonNull String permission) {
        return this.commandPermissionHandler.hasPermission(permission);
    }

    @PostConstruct
    void registerCommands() {
        LOGGER.debug("Registering commands");
        this.commandRegistrars.forEach(registrar -> registrar.registerCommands(this));
    }
}

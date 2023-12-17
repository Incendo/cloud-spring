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
package org.incendo.cloud.spring.registrar;

import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@SuppressWarnings({"unchecked", "rawtypes"})
@Service
@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
public class CommandRegistrationCoordinator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRegistrationCoordinator.class);

    private final SpringCommandManager commandManager;
    private final Collection<@NonNull CommandRegistrar> commandRegistrars;

    /**
     * Creates a new registration coordinator.
     *
     * @param commandManager the command manager
     * @param commandRegistrars the registrars that provide commands
     */
    public CommandRegistrationCoordinator(
            final @NonNull SpringCommandManager<?> commandManager,
            final @NonNull Collection<@NonNull CommandRegistrar<?>> commandRegistrars
    ) {
        this.commandManager = commandManager;
        this.commandRegistrars = List.copyOf(commandRegistrars);
    }

    @PostConstruct
    void registerCommands() {
        LOGGER.debug("Registering commands");
        this.commandRegistrars.forEach(registrar -> registrar.registerCommands(this.commandManager));
    }
}

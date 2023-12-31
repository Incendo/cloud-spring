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
import cloud.commandframework.arguments.suggestion.SuggestionFactory;
import cloud.commandframework.context.CommandInput;
import cloud.commandframework.exceptions.ArgumentParseException;
import cloud.commandframework.exceptions.InvalidCommandSenderException;
import cloud.commandframework.exceptions.InvalidSyntaxException;
import cloud.commandframework.exceptions.NoPermissionException;
import cloud.commandframework.exceptions.NoSuchCommandException;
import cloud.commandframework.execution.FilteringCommandSuggestionProcessor;
import cloud.commandframework.keys.CloudKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.event.CommandExecutionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.completion.CompletionResolver;
import org.springframework.stereotype.Component;

@Component
@API(status = API.Status.STABLE, since = "1.0.0")
public class SpringCommandManager<C> extends CommandManager<C> implements CompletionResolver {

    public static final CloudKey<String> COMMAND_GROUP_KEY = CloudKey.of("group", String.class);
    public static final CloudKey<Object> OUTPUT = CloudKey.of("output", Object.class);

    private static final String MESSAGE_INTERNAL_ERROR = "An internal error occurred while attempting to perform this command.";
    private static final String MESSAGE_INVALID_SYNTAX = "Invalid Command Syntax. Correct command syntax is: ";
    private static final String MESSAGE_NO_PERMS = "I'm sorry, but you do not have permission to perform this command. "
            + "Please contact the server administrators if you believe that this is in error.";
    private static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCommandManager.class);

    private final SpringCommandPermissionHandler<C> commandPermissionHandler;
    private final CommandSenderMapper<C> commandSenderMapper;
    private final SuggestionFactory<C, CloudCompletionProposal> suggestionFactory;

    /**
     * Creates a new command manager.
     *
     * @param commandExecutionCoordinatorResolver the resolver for the execution coordinator
     * @param commandPermissionHandler the permission handler
     * @param commandRegistrationHandler the registration handler
     * @param commandSenderMapper the mapper for the custom command sender type
     * @param applicationContext the application context
     */
    public SpringCommandManager(
            final @NonNull SpringCommandExecutionCoordinatorResolver<C> commandExecutionCoordinatorResolver,
            final @NonNull SpringCommandPermissionHandler<C> commandPermissionHandler,
            final @NonNull SpringCommandRegistrationHandler<C> commandRegistrationHandler,
            final @NonNull CommandSenderMapper<C> commandSenderMapper,
            final @NonNull ApplicationContext applicationContext
    ) {
        super(commandExecutionCoordinatorResolver, commandRegistrationHandler);
        this.commandPermissionHandler = commandPermissionHandler;
        this.commandSenderMapper = commandSenderMapper;
        this.suggestionFactory = super.suggestionFactory().mapped(CloudCompletionProposal::fromSuggestion);
        this.parameterInjectorRegistry().registerInjectionService(new SpringInjectionService<>(applicationContext));
        this.commandSuggestionProcessor(new FilteringCommandSuggestionProcessor<>(
                FilteringCommandSuggestionProcessor.Filter.<C>startsWith(true).andTrimBeforeLastSpace()
        ));

        this.registerDefaultExceptionHandlers();
    }

    @Override
    public final boolean hasPermission(final @NonNull C sender, final @NonNull String permission) {
        return this.commandPermissionHandler.hasPermission(permission);
    }

    @EventListener(CommandExecutionEvent.class)
    void commandExecutionEvent(final @NonNull CommandExecutionEvent<C> event) {
        final CommandInput commandInput = CommandInput.of(Arrays.asList(event.context().getRawArgs()));
        try {
            event.result(this.commandExecutor().executeCommand(
                    this.commandSenderMapper.map(event.context()),
                    commandInput.input()
            ).join());
        } catch (final Exception e) {
            throw new FailureIndicationException();
        }
    }

    @Override
    public final @NonNull SuggestionFactory<C, ? extends CloudCompletionProposal> suggestionFactory() {
        return this.suggestionFactory;
    }

    @Override
    public final @NonNull List<@NonNull CompletionProposal> apply(final @NonNull CompletionContext completionContext) {
        final List<String> strings = new ArrayList<>();
        strings.add(completionContext.getCommandRegistration().getCommand());
        strings.addAll(completionContext.getWords());
        final String input = String.join(" ", strings);

        return this.suggestionFactory().suggestImmediately(this.commandSenderMapper.map(null), input).stream()
                .map(suggestion -> (CompletionProposal) suggestion)
                .toList();
    }

    private void registerDefaultExceptionHandlers() {
        this.exceptionController()
                .registerHandler(Throwable.class, ctx -> LOGGER.error(MESSAGE_INTERNAL_ERROR, ctx.exception()))
                .registerHandler(ArgumentParseException.class, ctx -> LOGGER.error("Invalid Command Argument: {}",
                        ctx.exception().getCause().getMessage()))
                .registerHandler(NoSuchCommandException.class, ctx -> LOGGER.error(MESSAGE_UNKNOWN_COMMAND))
                .registerHandler(NoPermissionException.class, ctx -> LOGGER.error(MESSAGE_NO_PERMS))
                .registerHandler(InvalidCommandSenderException.class, ctx -> LOGGER.error(ctx.exception().getMessage()))
                .registerHandler(InvalidSyntaxException.class,
                        ctx -> LOGGER.error(MESSAGE_INVALID_SYNTAX + ctx.exception().getCorrectSyntax()));
    }
}

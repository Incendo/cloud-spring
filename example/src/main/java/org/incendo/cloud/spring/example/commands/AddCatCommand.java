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
package org.incendo.cloud.spring.example.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandBean;
import cloud.commandframework.CommandProperties;
import cloud.commandframework.arguments.flags.CommandFlag;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandManager;
import org.incendo.cloud.spring.SpringCommandSender;
import org.incendo.cloud.spring.example.model.Cat;
import org.incendo.cloud.spring.example.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cloud.commandframework.CommandDescription.commandDescription;
import static cloud.commandframework.arguments.standard.StringParser.stringParser;

@Component
public class AddCatCommand extends CommandBean<SpringCommandSender> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddCatCommand.class);

    private final CatService catService;

    /**
     * Creates a new command instance.
     *
     * @param catService the cat service
     */
    public AddCatCommand(final @NonNull CatService catService) {
        this.catService = catService;
    }

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("cat");
    }

    @Override
    protected @NonNull CommandMeta meta() {
        return CommandMeta.builder().with(SpringCommandManager.COMMAND_GROUP_KEY, "Cat").build();
    }

    @Override
    protected Command.Builder<SpringCommandSender> configure(final Command.Builder<SpringCommandSender> builder) {
        return builder.literal("add")
                .required("name", stringParser())
                .flag(CommandFlag.builder("override"))
                .commandDescription(commandDescription("Add a cat"));
    }

    @Override
    public void execute(@NonNull final CommandContext<SpringCommandSender> commandContext) {
        final String name = commandContext.get("name");
        final boolean override = commandContext.flags().hasFlag("override");
        final Cat cat = this.catService.addCat(name, override);
        LOGGER.info("Added cat {}", cat.name());
    }
}

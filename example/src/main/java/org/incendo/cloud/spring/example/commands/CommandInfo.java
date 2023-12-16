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
import cloud.commandframework.CommandDescription;
import cloud.commandframework.CommandProperties;
import cloud.commandframework.context.CommandContext;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static cloud.commandframework.arguments.standard.StringParser.greedyStringParser;

@Component
public class CommandInfo extends CommandBean<SpringCommandSender> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandInfo.class);

    @Override
    protected @NonNull CommandProperties properties() {
        return CommandProperties.of("info");
    }

    @Override
    protected Command.Builder<SpringCommandSender> configure(final Command.Builder<SpringCommandSender> builder) {
        return builder.commandDescription(CommandDescription.commandDescription("An informational command."))
                .required("string", greedyStringParser());
    }

    @Override
    public void execute(final @NonNull CommandContext<SpringCommandSender> commandContext) {
        LOGGER.info("much info yes: {}", commandContext.<String>get("string"));
    }
}
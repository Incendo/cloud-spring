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

import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandBean;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.aggregate.AggregateParser;
import org.incendo.cloud.parser.flag.CommandFlag;
import org.incendo.cloud.spring.CloudCompletionProposal;
import org.incendo.cloud.spring.SpringCommandManager;
import org.incendo.cloud.spring.SpringCommandSender;
import org.incendo.cloud.spring.example.model.Cat;
import org.incendo.cloud.spring.example.service.CatService;
import org.incendo.cloud.suggestion.SuggestionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.incendo.cloud.description.CommandDescription.commandDescription;
import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;
import static org.incendo.cloud.parser.standard.StringParser.stringParser;

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
    protected Command.@NonNull Builder<SpringCommandSender> configure(
            final Command.@NonNull Builder<SpringCommandSender> builder
    ) {
        final AggregateParser<SpringCommandSender, Cat> catParser = AggregateParser.<SpringCommandSender>builder()
                .withComponent("name", stringParser(), SuggestionProvider.blocking((ctx, in) -> List.of(
                        CloudCompletionProposal.of("Missy").displayText("Missy (A cute cat name)"),
                        CloudCompletionProposal.of("Donald").displayText("Donald (Old man name = CUTE!)"),
                        CloudCompletionProposal.of("Fluffy").displayText("Fluffy (A classic :))")
                )))
                .withComponent("age", integerParser(0))
                .withDirectMapper(Cat.class, (cmxCtx, ctx) -> ArgumentParseResult.success(new Cat(ctx.get("name"),
                        ctx.get("age"))))
                .build();

        return builder.literal("add")
                .required("cat", catParser)
                .flag(CommandFlag.builder("override"))
                .commandDescription(commandDescription("Add a cat"));
    }

    @Override
    public void execute(final @NonNull CommandContext<SpringCommandSender> commandContext) {
        final Cat cat = commandContext.get("cat");
        final boolean override = commandContext.flags().hasFlag("override");
        this.catService.addCat(cat, override);

        // We can either write the output to the context:
        commandContext.set(SpringCommandManager.OUTPUT, String.format("Added cat: %s", cat.name()));
        // or print directly to the terminal:
        // commandContext.sender().writeLine(String.format("Added cat: %s", cat.name()));
    }
}

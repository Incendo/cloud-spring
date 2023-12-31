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

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandSender;
import org.incendo.cloud.spring.annotation.CommandGroup;
import org.incendo.cloud.spring.annotation.ScanCommands;
import org.incendo.cloud.spring.example.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ScanCommands
@Component
public class ListCatCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCatCommand.class);

    private final CatService catService;

    /**
     * Creates a new command instance.
     *
     * @param catService the cat service
     */
    public ListCatCommand(final @NonNull CatService catService) {
        this.catService = catService;
    }

    /**
     * Command that lists all registered cats.
     *
     * @param sender the command sender
     */
    @CommandGroup("Cat")
    @CommandDescription("List the cats")
    @CommandMethod("cat list")
    public void listCats(final @NonNull SpringCommandSender sender) {
        sender.writeLine("Cats");
        this.catService.cats().forEach(cat -> sender.writeLine(String.format("- %s (Age: %d)", cat.name(), cat.age())));
    }
}

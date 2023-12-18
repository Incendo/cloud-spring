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
package org.incendo.cloud.spring.example;

import cloud.commandframework.annotations.AnnotationParser;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandManager;
import org.incendo.cloud.spring.SpringCommandSender;
import org.incendo.cloud.spring.annotation.CommandGroup;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

/**
 * Configuration for the example application.
 */
@Configuration
public class ExampleConfig {

    @Bean
    @NonNull AnnotationParser<SpringCommandSender> annotationParser(
            final @NonNull SpringCommandManager<SpringCommandSender> commandManager
    ) {
        final AnnotationParser<SpringCommandSender> annotationParser = new AnnotationParser<>(
                commandManager,
                SpringCommandSender.class
        );
        annotationParser.registerBuilderModifier(CommandGroup.class,
                (annotation, builder) -> builder.meta(SpringCommandManager.COMMAND_GROUP_KEY, annotation.value()));
        return annotationParser;
    }

    @Bean
    @NonNull PromptProvider myPromptProvider() {
        return () -> new AttributedString("cat-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}

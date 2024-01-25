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

import java.util.Collection;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.spring.SpringCommandManager;
import org.incendo.cloud.spring.annotation.ScanCommands;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@ConditionalOnClass(AnnotationParser.class)
@ConditionalOnBean(AnnotationParser.class)
@Component
@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
public class AnnotationRegistrar<C> implements CommandRegistrar<C> {

    private final ApplicationContext applicationContext;
    private final Collection<@NonNull Object> commandBeans;

    /**
     * Creates a new annotation registrar.
     *
     * @param applicationContext the application context
     */
    public AnnotationRegistrar(
            final @NonNull ApplicationContext applicationContext
    ) {
        this.applicationContext = applicationContext;
        this.commandBeans = applicationContext.getBeansWithAnnotation(ScanCommands.class).values();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerCommands(final @NonNull SpringCommandManager<C> commandManager) {
        final AnnotationParser<C> annotationParser = this.applicationContext.getBean(AnnotationParser.class);
        this.commandBeans.forEach(annotationParser::parse);
    }
}

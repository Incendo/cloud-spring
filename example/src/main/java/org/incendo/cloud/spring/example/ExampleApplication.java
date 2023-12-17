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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.SpringCommandManager;
import org.incendo.cloud.spring.SpringCommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Example of a spring-shell application using cloud-spring.
 */
@SpringBootApplication
public class ExampleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplication.class);

    private SpringCommandManager<SpringCommandSender> springCommandManager;

    /**
     * Example application entrypoint.
     *
     * @param springCommandManager the command manager
     */
    public ExampleApplication(final @NonNull SpringCommandManager<SpringCommandSender> springCommandManager) {
        LOGGER.info("ExampleApplication woo");
    }

    /**
     * Launches the example application.
     *
     * @param args the args
     */
    public static void main(final @NonNull String @NonNull[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}

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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandBean;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.StandardCommandContextFactory;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.internal.CommandNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test that spins up a custom spring application to make sure that things wire up like they're supposed to.
 */
@SpringBootTest
class ApplicationIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringCommandManager<TestCommandSender> springCommandManager;

    @Test
    @DisplayName("Verify that the application started")
    void test() {
        assertThat(this.applicationContext).isNotNull();
        assertThat(this.springCommandManager).isNotNull();
    }

    @Test
    @DisplayName("Verify that the command bean was registered")
    void testCommandRegistration() {
        // Act
        final CommandNode<TestCommandSender> commandNode = this.springCommandManager.commandTree().getNamedNode("test");

        // Assert
        assertThat(commandNode).isNotNull();
    }

    @Test
    @DisplayName("Verify that unqualified Bean injection works")
    void testBeanInjection() {
        // Arrange
        final CommandContext<TestCommandSender> context = new StandardCommandContextFactory<>(this.springCommandManager).create(
                false,
                new TestCommandSender()
        );

        // Act
        final TestType result = context.inject(TestType.class).orElseThrow();

        // Assert
        assertThat(result).isEqualTo(new TestType("foo"));
    }


    @SpringBootApplication
    static class TestApplication {
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @NonNull CommandSenderMapper<TestCommandSender> commandSenderSupplier() {
            return ctx -> new TestCommandSender();
        }

        @Bean
        @NonNull ExecutionCoordinator<TestCommandSender> executionCoordinator() {
            return ExecutionCoordinator.asyncCoordinator();
        }

        @Bean
        @NonNull TestCommandBean commandBean() {
            return new TestCommandBean();
        }

        @Bean
        @NonNull TestType fooBean() {
            return new TestType("foo");
        }
    }


    static class TestCommandSender {

    }

    record TestType(@NonNull String value) {

    }

    static class TestCommandBean extends CommandBean<TestCommandSender> {

        @Override
        protected @NonNull CommandProperties properties() {
            return CommandProperties.of("test");
        }

        @Override
        protected Command.Builder<TestCommandSender> configure(final Command.Builder<TestCommandSender> builder) {
            return builder;
        }
    }
}

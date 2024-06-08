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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.Command;
import org.incendo.cloud.bean.CommandBean;
import org.incendo.cloud.bean.CommandProperties;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.CommandDescription;
import org.incendo.cloud.parser.flag.CommandFlag;
import org.incendo.cloud.spring.config.CloudSpringConfig;
import org.incendo.cloud.spring.registrar.BeanRegistrar;
import org.incendo.cloud.spring.registrar.CommandRegistrationCoordinator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.context.ContextConfiguration;

import static com.google.common.truth.Truth.assertThat;
import static org.awaitility.Awaitility.await;
import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;
import static org.incendo.cloud.parser.standard.StringParser.stringParser;

@Import(CloudSpringConfig.class)
@ContextConfiguration(classes = {
        SpringCommandRegistrationHandler.class,
        SpringCommandManager.class,
        ShellIntegrationTest.TestConfig.class
})
@ShellTest
public class ShellIntegrationTest {

    @Autowired
    private ShellTestClient client;

    @Test
    void testCommandParsing() {
        // Arrange
        final ShellTestClient.InteractiveShellSession session = this.client.interactive().run();
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            ShellAssertions.assertThat(session.screen()).containsText("shell");
        });

        // Act
        session.write(session.writeSequence().text("help").carriageReturn().build());
        session.write(session.writeSequence().text("command abc --flag 123").carriageReturn().build());

        // Assert
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertThat(TestCommand.EXECUTED.get()).isTrue();
        });

        final CommandContext<SpringCommandSender> context = TestCommand.CONTEXT.get();
        assertThat(context.<String>get("string")).isEqualTo("abc");
        assertThat(context.flags().<Integer>get("flag")).isEqualTo(123);
    }


    @SpringBootApplication
    static class TestApplication {

    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @NonNull CommandRegistrationCoordinator commandRegistrationCoordinator(
                final @NonNull SpringCommandManager<SpringCommandSender> springCommandManager
        ) {
            return new CommandRegistrationCoordinator(
                    springCommandManager,
                    List.of(new BeanRegistrar<>(List.of(this.testCommand())))
            );
        }

        @Bean
        @NonNull TestCommand testCommand() {
            return new TestCommand();
        }
    }

    static class TestCommand extends CommandBean<SpringCommandSender> {

        private static final AtomicBoolean EXECUTED = new AtomicBoolean(false);
        private static final AtomicReference<CommandContext<SpringCommandSender>> CONTEXT = new AtomicReference<>(null);

        @Override
        protected @NonNull CommandProperties properties() {
            return CommandProperties.of("command");
        }

        @Override
        protected Command.Builder<SpringCommandSender> configure(final Command.Builder<SpringCommandSender> builder) {
            return builder.commandDescription(CommandDescription.commandDescription("Description!"))
                    .required("string", stringParser())
                    .flag(CommandFlag.builder("flag").withComponent(integerParser()).build());
        }

        @Override
        public void execute(final @NonNull CommandContext<SpringCommandSender> commandContext) {
            EXECUTED.set(true);
            CONTEXT.set(commandContext);
        }
    }
}

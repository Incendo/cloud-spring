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
package org.incendo.cloud.spring.config;

import cloud.commandframework.execution.CommandExecutionCoordinator;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.spring.CommandSenderMapper;
import org.incendo.cloud.spring.SpringCommandExecutionCoordinatorResolver;
import org.incendo.cloud.spring.SpringCommandPermissionHandler;
import org.incendo.cloud.spring.SpringCommandSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for cloud-spring.
 *
 * @since 1.0.0
 */
@Configuration
@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
public class CloudSpringConfig {

    @Bean
    @ConditionalOnMissingBean(SpringCommandPermissionHandler.class)
    @NonNull SpringCommandPermissionHandler<?> commandPermissionHandler() {
        return SpringCommandPermissionHandler.alwaysTrue();
    }

    @Bean
    @ConditionalOnMissingBean(SpringCommandExecutionCoordinatorResolver.class)
    @NonNull SpringCommandExecutionCoordinatorResolver<?> commandExecutionCoordinatorResolver() {
        return CommandExecutionCoordinator.simpleCoordinator()::apply;
    }

    @Bean
    @ConditionalOnMissingBean(CommandSenderMapper.class)
    @NonNull CommandSenderMapper<?> commandSenderMapper() {
        return SpringCommandSender::sender;
    }
}

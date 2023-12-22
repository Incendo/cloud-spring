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

import cloud.commandframework.annotations.injection.InjectionRequest;
import cloud.commandframework.annotations.injection.InjectionService;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * {@link InjectionService} with support for Spring Bean injection.
 *
 * <p>{@link Qualifier} is supported, but meta-annotations annotated with {@link Qualifier} will not be resolved.</p>
 *
 * @param <C> command sender type
 * @since 1.0.0
 */
@API(status = API.Status.INTERNAL, consumers = "org.incendo.cloud.spring.*", since = "1.0.0")
class SpringInjectionService<C> implements InjectionService<C> {

    private final BeanFactory beanFactory;

    SpringInjectionService(final @NonNull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public final @Nullable Object handle(final @NonNull InjectionRequest<C> request) throws Exception {
        final Qualifier qualifier = request.annotationAccessor().annotation(Qualifier.class);
        try {
            if (qualifier != null) {
                return this.beanFactory.getBean(qualifier.value(), request.injectedClass());
            }
            return this.beanFactory.getBean(request.injectedClass());
        } catch (final NoSuchBeanDefinitionException ignored) {
            return null;
        }
    }
}

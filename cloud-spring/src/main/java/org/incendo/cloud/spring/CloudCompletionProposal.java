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

import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.suggestion.Suggestion;
import org.springframework.shell.CompletionProposal;

@API(status = API.Status.STABLE, since = "1.0.0")
public final class CloudCompletionProposal extends CompletionProposal implements Suggestion {

    /**
     * Creates a {@link CloudCompletionProposal} from the given {@code suggestion}.
     *
     * @param suggestion the suggestion
     * @return the proposal
     */
    public static @NonNull CloudCompletionProposal fromSuggestion(final @NonNull Suggestion suggestion) {
        if (suggestion instanceof CloudCompletionProposal cloudCompletionProposal) {
            return cloudCompletionProposal;
        }
        return new CloudCompletionProposal(suggestion.suggestion());
    }

    /**
     * Returns a {@link CloudCompletionProposal} using the given {@code value}.
     *
     * @param value the value
     * @return the proposal
     */
    public static @NonNull CloudCompletionProposal of(final @NonNull String value) {
        return new CloudCompletionProposal(value);
    }

    private CloudCompletionProposal(final @NonNull String value) {
        super(value);
        this.dontQuote(true);
        this.complete(false);
    }

    @Override
    public @NonNull String suggestion() {
        return this.value();
    }

    @Override
    public @NonNull CloudCompletionProposal value(final @NonNull String value) {
        return (CloudCompletionProposal) super.value(value);
    }

    @Override
    public @NonNull CloudCompletionProposal displayText(final @Nullable String displayText) {
        return (CloudCompletionProposal) super.displayText(displayText);
    }

    @Override
    public @NonNull CloudCompletionProposal category(final @Nullable String category) {
        return (CloudCompletionProposal) super.category(category);
    }

    @Override
    public @NonNull CloudCompletionProposal dontQuote(final boolean dontQuote) {
        return (CloudCompletionProposal) super.dontQuote(dontQuote);
    }

    @Override
    public @NonNull CloudCompletionProposal withSuggestion(final @NonNull String suggestion) {
        return this.value(suggestion);
    }
}

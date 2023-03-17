/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2023 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.yegor256.xsline;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Train that consequently joins a number of trains.
 *
 * The class is immutable, you can't add any more Shifts to it.
 *
 * When constructed, an object of this class doesn't touch the encapsulated
 * Trains. Only when you call {@link #iterator()}, all Trains are checked,
 * their Shifts are retrieved, a new collection is built and its iterator
 * is returned.
 *
 * @param <T> Type of elements
 * @since 0.16.0
 */
public final class TrJoined<T> implements Train<T> {

    /**
     * The chain of trains.
     */
    private final Iterable<Train<T>> chain;

    /**
     * Ctor.
     * @param trains Chain of trains
     */
    @SafeVarargs
    public TrJoined(final Train<T>... trains) {
        this(Arrays.asList(trains));
    }

    /**
     * Ctor.
     * @param trains Chain of trains
     */
    public TrJoined(final Iterable<Train<T>> trains) {
        this.chain = trains;
    }

    @Override
    public Train<T> with(final T shift) {
        final List<Train<T>> trains = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            trains.add(train);
        }
        if (trains.isEmpty()) {
            trains.add(new TrDefault<T>().with(shift));
        } else {
            final Train<T> last = trains.get(trains.size() - 1);
            trains.remove(trains.size() - 1);
            trains.add(last.with(shift));
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Train<T> empty() {
        final List<Train<T>> trains = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            trains.add(train.empty());
        }
        return new TrJoined<>(trains);
    }

    @Override
    public Iterator<T> iterator() {
        final Collection<T> shifts = new LinkedList<>();
        for (final Train<T> train : this.chain) {
            for (final T shift : train) {
                shifts.add(shift);
            }
        }
        return shifts.iterator();
    }
}

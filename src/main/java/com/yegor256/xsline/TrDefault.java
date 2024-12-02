/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2024 Yegor Bugayenko
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Default train.
 *
 * <p>This is the default implementation of {@link Train}. You are supposed
 * to use it almost always, for example like this:</p>
 *
 * <pre> Train&lt;Shift&gt; train = new TrDefault&lt;&gt;()
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 * .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")));</pre>
 *
 * @param <T> Type of element
 * @since 0.1.0
 */
public final class TrDefault<T> implements Train<T> {

    /**
     * The list of elements.
     */
    private final Iterable<T> list;

    /**
     * Ctor.
     */
    public TrDefault() {
        this(new ArrayList<>(0));
    }

    /**
     * Ctor.
     * @param items Items
     */
    @SafeVarargs
    public TrDefault(final T... items) {
        this(Arrays.asList(items));
    }

    /**
     * Ctor.
     * @param items Items
     */
    public TrDefault(final Iterable<T> items) {
        this.list = items;
    }

    @Override
    public Train<T> with(final T element) {
        final Collection<T> items = new LinkedList<>();
        for (final T item : this.list) {
            items.add(item);
        }
        items.add(element);
        return new TrDefault<>(items);
    }

    @Override
    public Train<T> empty() {
        return new TrDefault<>();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }
}

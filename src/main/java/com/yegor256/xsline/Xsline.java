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

import com.jcabi.log.Logger;
import com.jcabi.xml.XML;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Chain of XSL transformations.
 *
 * <p>Use it like this:</p>
 *
 * <pre> XML input = new XMLDocument("&lt;test/&gt;");
 * XML output = new Xsline(
 *   new TrDefault&lt;&gt;()
 *     .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 *     .with(new StXSL(new XSLDocument("&lt;stylesheet&gt;...")))
 * ).pass(input);
 * </pre>
 *
 * <p>See all implementations of {@link Shift} to learn the functionality
 * this package provides.</p>
 *
 * @since 0.1.0
 */
public final class Xsline {

    /**
     * Collection of shifts.
     */
    private final Iterable<Shift> shifts;

    /**
     * Ctor.
     * @param shift One shift to use
     * @since 0.20.0
     */
    public Xsline(final Shift shift) {
        this(Collections.singletonList(shift));
    }

    /**
     * Ctor.
     * @param list List of shifts
     */
    public Xsline(final Iterable<Shift> list) {
        this.shifts = list;
    }

    /**
     * Run it all with the given XML.
     * @param input The input XML
     * @return The output XML
     */
    public XML pass(final XML input) {
        final long start = System.currentTimeMillis();
        XML output = input;
        int pos = 0;
        final Collection<String> ids = new LinkedList<>();
        for (final Shift shift : this.shifts) {
            output = shift.apply(pos, output);
            ++pos;
            ids.add(shift.uid());
        }
        if (Logger.isDebugEnabled(this)) {
            Logger.debug(
                this, "Transformed XML through %d shift(s) in %[ms]s: %s",
                pos, System.currentTimeMillis() - start,
                String.join(", ", ids)
            );
        }
        return output;
    }

}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022-2025 Yegor Bugayenko
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

import com.jcabi.xml.XML;

/**
 * An envelope for {@link Shift}.
 *
 * <p>This supplementary class helps making new classes implementing
 * interface {@link Shift} without writing too much of code. See how
 * {@link StBefore}, {@link StClasspath}, and {@link StXSL} are using
 * this class.</p>
 *
 * @see <a href="https://www.yegor256.com/2017/01/31/decorating-envelopes.html">Blog post about "Decorating Envelopes"</a>
 * @since 0.4.0
 */
public class StEnvelope implements Shift {

    /**
     * The original shift.
     */
    private final Shift origin;

    /**
     * Ctor.
     * @param shift The shift
     */
    public StEnvelope(final Shift shift) {
        this.origin = shift;
    }

    @Override
    public final String uid() {
        return this.origin.uid();
    }

    @Override
    public final XML apply(final int position, final XML xml) {
        return this.origin.apply(position, xml);
    }
}

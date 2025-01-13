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

import com.jcabi.xml.XMLDocument;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link StLambda}.
 *
 * @since 0.13.0
 */
final class StLambdaTest {

    @Test
    void shouldReturnFormattedUid() {
        final Shift lambda = new StLambda((integer, xml) -> xml);
        final String uid = lambda.uid();
        MatcherAssert.assertThat(
            uid.startsWith("λ-"),
            Matchers.is(true)
        );
    }

    @Test
    void shouldReturnUidFromCtor() {
        final String uuid = UUID.randomUUID().toString();
        final Shift lambda = new StLambda(uuid, (integer, xml) -> xml);
        MatcherAssert.assertThat(
            uuid,
            Matchers.is(lambda.uid())
        );
    }

    @Test
    void shouldThrowsExceptions() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new StLambda(
                (pos, xml) -> new StClasspath("not-found").apply(pos, xml)
            ).apply(0, new XMLDocument("<x>test</x>"))
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new StLambda(
                xml -> {
                    final BufferedReader inp = new BufferedReader(new StringReader("test"));
                    inp.close();
                    inp.readLine();
                    return xml;
                }
            ).apply(0, new XMLDocument("<x>test</x>"))
        );
    }

}

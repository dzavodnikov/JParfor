/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Dmitry Zavodnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jparfor.examples;

import org.jparfor.JLoop;
import org.jparfor.JParFor;

/**
 * This is a simple example of usage JParFor Library.
 *
 * @author Dmitry Zavodnikov (d.zavodnikov@gmail.com)
 */
public class Sum {

    public static int sum() {
        // We have some data.
        final int[] arr = new int[1000];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = 10 * i;
        }

        // We want get this value.
        int sum = 0;

        /*
         * 1. Create variables for each thread.
         */
        final int[] tSum = new int[JParFor.getMaxWorkers()];

        /*
         * 2. Write main loop.
         */
        final int[] proxy = arr;
        JParFor.exec(arr.length, new JLoop() {

            @Override
            public void execute(final int i, final int nThread) {
                tSum[nThread] += proxy[i];
            }
        });

        /*
         * 3. Join results from each thread.
         */
        for (int i = 0; i < tSum.length; ++i) {
            sum += tSum[i];
        }

        return sum;
    }

    public static void main(final String[] asrgs) {
        System.out.println(Sum.sum());
    }
}

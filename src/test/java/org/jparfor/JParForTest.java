/*
 * Copyright (c) 2012-2018 JParFor Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * This file is part of Java Parallel For (JParFor).
 */
package org.jparfor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link JParFor}.
 *
 * @author Dmitry Zavodnikov (d.zavodnikov@gmail.com)
 */
public class JParForTest {

    private int[] arr;

    /**
     * Executes before creating instance of the class.
     */
    @Before
    public void setUp() throws Exception {
        this.arr = new int[50];
        for (int i = 0; i < this.arr.length; ++i) {
            this.arr[i] = i;
        }
    }

    /**
     * Calculate single-thread sum.
     */
    private int sum(final int begin, final int end, final int step) {
        int sum = 0;

        for (int i = begin; i < end; i += step) {
            sum += this.arr[i];
        }

        return sum;
    }

    private void run(final int minIter, final int threads, final int step, final int begin, final int end) {
        JParFor.setMinIterations(minIter);
        JParFor.setMaxWorkers(threads);

        // Create and initialize.
        final int[] sumThread = new int[threads];
        for (int i = 0; i < sumThread.length; ++i) {
            sumThread[i] = 0;
        }

        // Parallel calculate.
        JParFor.exec(begin, end, step, new JLoop() {

            @Override
            public void execute(final int i, final int nThread) {
                sumThread[nThread] += i;
            }
        });

        // Join results.
        int sumAll = 0;
        for (int i = 0; i < sumThread.length; ++i) {
            sumAll += sumThread[i];
        }

        // Check.
        final int answer = sum(begin, end, step);
        if (answer != sumAll) {
            System.out.println("MinIters:  " + minIter);
            System.out.println("Workers:   " + threads);
            System.out.println("Step:      " + step);
            System.out.println("Begin:     " + begin);
            System.out.println("End:       " + end);
            System.out.println();

            System.out.println("Answer:    " + answer);
            System.out.println("SumAll:    " + sumAll);
            System.out.println();

            for (int i = 0; i < sumThread.length; ++i) {
                sumAll += sumThread[i];
                System.out.println("Thread[" + i + "]: " + sumThread[i]);
            }

            Assert.fail("Incorrect values!");
        }
    }

    /**
     * Test method for: {@link JParFor#exec(int, int, int, JLoop)}.
     */
    @Test
    public void testExecAll() {
        for (int minIter = 1; minIter < 10; ++minIter) {
            for (int threads = 1; threads < 10; ++threads) {
                for (int step = 1; step < 10; ++step) {
                    for (int begin = 0; begin < 10; ++begin) {
                        for (int end = begin; end < this.arr.length; ++end) {
                            run(minIter, threads, step, begin, end);
                        }
                    }
                }
            }
        }
    }
}

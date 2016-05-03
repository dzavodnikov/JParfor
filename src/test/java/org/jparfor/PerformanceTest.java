/*
 * Copyright (c) 2012-2016 JParFor Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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

/**
 * Compare single-thread and multithread solution.
 *
 * @author Dmitry Zavodnikov (d.zavodnikov@gmail.com)
 */
public class PerformanceTest {

    private static double operation(final double value) {
        return Math.pow(value, Math.PI);
    }

    private static double[] init(final int len) {
        final double[] result = new double[len];

        for (int i = 0; i < result.length; ++i) {
            result[i] = i;
        }

        return result;
    }

    private static double testSingleThreaded(final int len, final int numOfIterations) {
        final double[] arr = init(len);

        // Start.
        final long startTime = System.currentTimeMillis();

        // Calculate.
        for (int iter = 0; iter < numOfIterations; ++iter) {
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = operation(arr[i]);
            }
        }

        // Stop.
        final double averageMilliSeconds = (double) (System.currentTimeMillis() - startTime)
                / (double) (numOfIterations);

        return averageMilliSeconds;
    }

    private static double testMultiThreaded(final int len, final int numOfIterations) {
        final double[] arr = init(len);

        // Start.
        final long startTime = System.currentTimeMillis();

        // Calculate.
        for (int iter = 0; iter < numOfIterations; ++iter) {
            JParFor.exec(len, new JLoop() {

                @Override
                public void execute(final int i, final int nThread) {
                    arr[i] = operation(arr[i]);
                }
            });
        }

        // Stop.
        final double averageMilliSeconds = (double) (System.currentTimeMillis() - startTime)
                / (double) (numOfIterations);

        return averageMilliSeconds;
    }

    /**
     * Run this test.
     */
    public static void main(final String[] args) {
        final int numOfIterations = 100;
        final int[] lengths = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 200, 300,
                400, 500, 600, 700, 800, 900, 1_000, 2_000, 3_000, 4_000, 5_000, 6_000, 7_000, 8_000, 9_000, 10_000,
                20_000, 30_000, 40_000, 50_000, 60_000, 70_000, 80_000, 90_000, 100_000, 200_000, 300_000, 400_000,
                500_000, 600_000, 700_000, 800_000, 900_000, 1_000_000, 2_000_000, 3_000_000, 4_000_000, 5_000_000,
                6_000_000, 7_000_000, 8_000_000, 9_000_000, 10_000_000 };

        for (final int len : lengths) {
            System.out.println("Array with " + len + " elements by " + numOfIterations + " iterations: ");
            System.out.println("    Single-thread: " + Double.toString(testSingleThreaded(len, numOfIterations)));
            System.out.println("    Multi-thread:  " + Double.toString(testMultiThreaded(len, numOfIterations)));
        }
    }
}

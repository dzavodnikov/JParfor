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
 * This class is part of Java Parallel For (JParFor).
 */
package org.jparfor;

import java.util.LinkedList;
import java.util.List;

/**
 * Contains implementation of main methods <code>Parallel FOR</code>.
 *
 * @author Dmitry Zavodnikov (d.zavodnikov@gmail.com)
 */
public class JParFor {

    /**
     * Minimum iterations into one thread by default.
     */
    public static final int MIN_ITERATIONS_DEFAULT = 1;

    /**
     * Minimum iterations into one worker.
     */
    private static int      minIterationsPerWorker = MIN_ITERATIONS_DEFAULT;

    /**
     * Minimal sizes of images when create many threads no needed.
     */
    public static final int DEFAULT_MAX_THREADS    = Runtime.getRuntime().availableProcessors();

    /**
     * Number of workers that will be used.
     */
    private static int      maxWorkers             = DEFAULT_MAX_THREADS;

    /**
     * @return minimum iterations into one worker.
     */
    public static int getMinIterations() {
        return minIterationsPerWorker;
    }

    /**
     * Set minimum iterations for processing into one worker.
     * 
     * @param minIter
     *            Number of iterations.
     */
    public static void setMinIterations(final int minIter) {
        if (minIter < 1) {
            throw new IllegalArgumentException(
                    "Parameter 'minIter' (= " + Integer.toString(minIter) + ") must be more or equals than 1!");
        }

        minIterationsPerWorker = minIter;
    }

    /**
     * @return minimum workers.
     */
    public static int getMaxWorkers() {
        return maxWorkers;
    }

    /**
     * Set minimum workers.
     * 
     * @param maxWork
     *            Number of workers.
     */
    public static void setMaxWorkers(final int maxWork) {
        if (maxWork < 1) {
            throw new IllegalArgumentException(
                    "Parameter 'maxWork' (= " + Integer.toString(maxWork) + ") must be more or equals than 1!");
        }

        maxWorkers = maxWork;
    }

    /**
     * Run single loop into many threads.
     *
     * <p>
     * Same as:
     *
     * <code><pre>
     * for (int i = begin; i < end; i += step) {
     *      runner.exec(i, 0); // Do something...
     * }
     * </pre></code>
     * </p>
     *
     * <p>
     * To save results of the threads use following pattern:
     *
     * <code><pre>
     * final double[] results = new double[JParfor.getMaxWorkers()];
     * JParfor.exec(begin, end, step, new Runner() {
     *      {@literal @}Override
     *      public void execute(int i, int nThread) {
     *          // Perform some calculations.
     *          // nThread starts from 0!
     *          // temp -- some result.
     *          results[nThread] = temp;
     *      }
     * });
     * </pre></code>
     * </p>
     * 
     * @param begin
     *            Start index.
     * @param end
     *            End index.
     * @param step
     *            Increment value.
     * @param runner
     *            Operation that will be executed on each step.
     */
    public static void exec(final int begin, final int end, final int step, final JLoop runner) {
        // Verify parameters.
        if (begin < 0) {
            throw new IllegalArgumentException(
                    "Parameter 'begin' (= " + Integer.toString(begin) + ") must be more or equals than 0!");
        }
        if (end < begin) {
            throw new IllegalArgumentException("Parameter 'end' (= " + Integer.toString(end)
                    + ") must be more or equals than parameter 'begin' (= " + Integer.toString(begin) + "!");
        }
        if (step < 1) {
            throw new IllegalArgumentException(
                    "Parameter 'begin' (= " + Integer.toString(begin) + ") must be more or equals than 1!");
        }
        if (runner == null) {
            throw new IllegalArgumentException("Parameter 'runner' must be not null!");
        }

        /*
         * Calculate running parameters.
         */
        // Number of real iterations.
        final int totalIter = (int) Math.ceil((double) (end - begin) / (double) step); // Rounded up.

        // Number iterations for each worker.
        int bitIter = (int) Math.ceil((double) totalIter / (double) JParFor.getMaxWorkers()); // Round up.
        if (bitIter <= JParFor.getMinIterations()) {
            bitIter = JParFor.getMinIterations();
        }

        // Number of workers that we need.
        final int workers = (int) Math.ceil((double) totalIter / (double) bitIter); // Round up.

        /*
         * Split tasks.
         */
        if (JParFor.getMaxWorkers() == 1 || workers == 1) {
            /*
             * Use single thread.
             */
            for (int i = begin; i < end; i += step) {
                runner.execute(i, 0);
            }
        } else {
            /*
             * Use many threads.
             */
            final List<Thread> threadList = new LinkedList<Thread>();

            // Split iterations to all available workers.
            final int bitIterAbs = bitIter * step; // We can have 'step' value more than 1!
            for (int i = 0; i < workers; ++i) {
                final int startPos = begin + i * bitIterAbs;
                int temp = startPos + bitIterAbs;
                if (i == workers - 1) {
                    temp = end;
                }

                final int endPos = temp;
                final int workNum = i;
                threadList.add(new Thread(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = startPos; i < endPos; i += step) {
                            runner.execute(i, workNum);
                        }
                    }
                }));
            }

            // Fork threads.
            for (final Thread t : threadList) {
                t.start();
            }

            // Join threads.
            for (final Thread t : threadList) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Same {@link JParFor#exec(int, int, int, JLoop)}, but use <code>begin = 0</code> and <code>step = 1</code>:
     * <code><pre>
     * for (int i = 0; i < end; ++i) {
     *      runner.exec(i, 0); // Do something...
     * }
     * </pre></code>
     * 
     * @param end
     *            End index.
     * @param runner
     *            Operation that will be executed on each step.
     */
    public static void exec(final int end, final JLoop runner) {
        JParFor.exec(0, end, 1, runner);
    }
}

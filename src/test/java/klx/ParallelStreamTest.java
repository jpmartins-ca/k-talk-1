package klx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParallelStreamTest {


        /**
         * Total number of elements and operations to process.
         */
        private static final int SIZE = 50_000;
        private static final int OP = 1_000;

        private List<Integer> data;

        /**
         * Builds the data set once before each test.
         */
        @BeforeEach
        void initData() {
            data = IntStream.rangeClosed(1, SIZE)
                    .boxed()
                    .collect(Collectors.toList());
        }

        /**
         * CPU heavy operation
         */
        private static double heavyOperation(int n) {
            double sum = 0.0;
            for (int i = 0; i < OP; i++) {
                sum += Math.sin(n + i) * Math.tan(n + i);
            }
            return sum;
        }

        private static double forLoop(List<Integer> data) {
            double warmupLoop = 0.0;
            for(Integer myInt : data) {
                warmupLoop += heavyOperation(myInt);
            }
            return warmupLoop;
        }

        private static double streamSeq (List<Integer> data) {
            return data.stream()
                    .mapToDouble(ParallelStreamTest::heavyOperation)
                    .sum();
        }

        private static double streamParallel (List<Integer> data) {
            return data.parallelStream()
                .mapToDouble(ParallelStreamTest::heavyOperation)
                .sum();
        }

        @Test
        void parallelShouldBeFasterThanSequential() {

            // Warm up the JVM
            double warmupLoop = forLoop(data);
            double warmupSeq = streamSeq(data);
            double warmPar = streamParallel(data);

            // Sequential
            long startForLoop = System.nanoTime();
            double forLoopSum = forLoop(data);
            long timeForLoop = System.nanoTime() - startForLoop;

            // Sequential
            long startSeq = System.nanoTime();
            double seqSum = streamSeq(data);
            long timeSeq = System.nanoTime() - startSeq;

            // Parallel
            long startPar = System.nanoTime();
            double parSum = streamParallel(data);
            long timePar = System.nanoTime() - startPar;


            assertEquals(seqSum, parSum, 0.0, "Stream sums must match");

            System.out.printf(
                    "For Loop: %.1f ms, Sequential: %.1f ms, Parallel: %.1f ms%n",
                    timeForLoop / 1e6,
                    timeSeq / 1e6,
                    timePar / 1e6
            );

            // Verify if parallel stream was faster
            assertTrue(
                    timePar < timeSeq,
                    "Expected parallel stream to run faster than sequential"
            );
        }

}

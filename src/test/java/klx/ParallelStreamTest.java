package klx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// TODO param. testing and highlight the structure behind the parallel stream
// GOOD PARALLEL CASE
// GOOD SEQUENTIAL CASE
// EQUALS -> GO FOR SEQUENTIAL BECAUSE RESOURCES
// DATA STRUCTURES FOR WHICH PARALLEL STREAMS PERFORM WORSE
// FOR THE AUDIENCE
public class ParallelStreamTest {


        /**
         * Total number of elements to process.
         * With 50_000 elements × 1_000 heavy ops each, this will take
         * a couple of seconds sequentially on a modern CPU.
         */
        private static final int SIZE = 1000;
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
         * does 1_000 sin/tan evaluations per input.
         */
        private static double heavyOperation(int n) {
            double sum = 0.0;
            for (int i = 0; i < OP; i++) {
                sum += Math.sin(n + i) * Math.tan(n + i);
            }
            return sum;
        }

        @Test
        void parallelShouldBeFasterThanSequential() {
            // Warm up the JVM
            double warmupSeq = data.stream()
                    .mapToDouble(ParallelStreamTest::heavyOperation)
                    .sum();
            double warmPar = data.parallelStream()
                    .mapToDouble(ParallelStreamTest::heavyOperation)
                    .sum();

            // Sequential
            long startSeq = System.nanoTime();
            double seqSum = data.stream()
                    .mapToDouble(ParallelStreamTest::heavyOperation)
                    .sum();
            long timeSeq = System.nanoTime() - startSeq;

            // Parallel
            long startPar = System.nanoTime();
            double parSum = data.parallelStream()
                    .mapToDouble(ParallelStreamTest::heavyOperation)
                    .sum();
            long timePar = System.nanoTime() - startPar;


            assertEquals(seqSum, parSum, 0.0, "Stream sums must match");

            System.out.printf(
                    "Sequential: %.1f ms, Parallel: %.1f ms%n",
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

package klx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// TODO PRE 1.8 -> RESPOSTAS PARA KAHOOT
class StreamExercisesTest {

    /** Exercise 1: Filter Even Numbers
     * Goal: Given a List, return a new list containing only even numbers.
     * Justification: filter(...) directly removes odd elements in one pass.*/
    public static List<Integer> filterEvens(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0).toList();
    }
    @Test
    public void testFilterEvens() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> expected = Arrays.asList(2, 4, 6);
        assertEquals(expected, filterEvens(input));
    }

    /**Exercise 2: Uppercase Conversion
     * Goal: Given a List, produce a list of all strings converted to uppercase.
     * Justification: map(...) with String::toUpperCase cleanly transforms each element.*/
    public static List<String> toUppercase(List<String> words) {
        /*return words.stream()
                .map(String::toUpperCase)
                .toList();*/
        return List.of();
    }
    @Test
    public void testToUppercase() {
        List<String> input = Arrays.asList("a", "bCd", "Hello");
        List<String> expected = Arrays.asList("A", "BCD", "HELLO");
        assertEquals(expected, toUppercase(input));
    }

    /**Exercise 3: Sum of Integers
     * Goal: Compute the sum of all integers in a List.
     * Justification: mapToInt(...) yields an IntStream for efficient primitive summation.*/
    public static int sumList(List<Integer> numbers) {
        /*return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return numbers.stream().reduce( 0, Integer::sum);*/
        return -1;
    }
    @Test
    public void testSumList() {
        assertEquals(15, sumList(Arrays.asList(1, 2, 3, 4, 5)));
    }

    /**Exercise 4: Find Maximum Value
     * Goal: Find the largest element in a List, returning Optional.
     * Justification: max(...) uses a comparator and handles empty lists via Optional.*/
    public static Optional maxValue(List<Integer> numbers) {
        /*return numbers.stream()
                .max(Integer::compareTo);*/
        return Optional.empty();
    }
    @Test
    public void testMaxValue() {
        assertTrue(maxValue(Collections.emptyList()).isEmpty());
        assertEquals(Optional.of(99), maxValue(Arrays.asList(10, 99, 5)));
    }

    /** Exercise 5: Group Strings by Length
     * Goal: Group a list of strings into a map from length to list of strings of that length.
     * Justification: Collectors.groupingBy(...) classifies elements based on length.*/
    public static Map<Integer, List<String>> groupByLength(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(String::length));
        //return Map.of();
    }
    @Test
    public void testGroupByLength() {
        List<String> words = List.of("a", "bb", "cc", "ddd");
        Map<Integer, List<String>> result = groupByLength(words);
        assertEquals(List.of("a"), result.get(1));
        assertEquals(List.of("bb", "cc"), result.get(2));
        assertEquals(List.of("ddd"), result.get(3));
    }

    /**Exercise 6 (Java 9+)
     * Goal: From a sorted list, take elements from the start until a negative value appears.
     * Justification: takeWhile(...) stops as soon as the predicate fails, avoiding extra checks.*/
    public static List<Integer> takeUntilNegative(List<Integer> sortedNumbers) {
        /*return sortedNumbers.stream()
                .takeWhile(n -> n >= 0)
                .toList();*/
        return List.of();
    }
    @Test
    public void testTakeUntilNegative() {
        List<Integer> input = Arrays.asList(3, 2, 0, -1, 5);
        List<Integer> expected = Arrays.asList(3, 2, 0);
        assertEquals(expected, takeUntilNegative(input));
    }

    /**Exercise 7 (Java 9+):
     * Goal: Skip all leading zeros in a list of integers, then collect the rest.
     * Justification: dropWhile(...) discards the initial matching prefix in one pass.*/
    public static List<Integer> skipLeadingZeros(List<Integer> numbers) {
        /*return numbers.stream()
                .dropWhile(n -> n == 0)
                .toList();*/
        return List.of();
    }
    @Test
    public void testSkipLeadingZeros() {
        List<Integer> input = Arrays.asList(0, 0, 7, 8, 0);
        List<Integer> expected = Arrays.asList(7, 8, 0);
        assertEquals(expected, skipLeadingZeros(input));
    }

    /**Exercise 8 (Java 9+):
     * Goal: Create a stream from a possibly null reference, filtering nulls gracefully.
     * Justification: Stream.ofNullable(...) produces either a single-element or empty stream.*/
    public static List<String> safeList(String maybeNull) {
        /*return Stream.ofNullable(maybeNull)
                .toList();*/
        return List.of();
    }
    @Test
    public void testSafeList() {
        assertEquals(Collections.emptyList(), safeList(null));
        assertEquals(Collections.singletonList("hi"), safeList("hi"));
    }

    /**Exercise 9: Flatten Nested Lists
     * Goal: Given List of Lists, return a flat list of all strings.
     * Justification: flatMap(...) merges inner collections into a single continuous stream.*/
    public static List<String> flatten(List<List<String>> nested) {
        /*return nested.stream()
                .flatMap(Collection::stream)
                .toList();*/
        return List.of();
    }
    @Test
    public void testFlatten() {
        List<List<String>> nested = Arrays.asList(
                Arrays.asList("x", "y"),
                Collections.singletonList("z")
        );
        assertEquals(Arrays.asList("x", "y", "z"), flatten(nested));
    }

    /**Exercise 10: Join with Comma
     * Goal: Convert a list of words into a single comma-separated string.
     * Justification: Collectors.joining(...) handles delimiter insertion without manual loops.*/
    public static String joinWithCommas(List<String> words) {
        /*return words.stream()
                .collect(Collectors.joining(", "));*/

        return "";
    }
    @Test
    public void testJoinWithCommas() {
        assertEquals("a, b, c", joinWithCommas(Arrays.asList("a", "b", "c")));
    }


    /** Helper Person record*/
    record Person(String department, double salary) {}

    /**Exercise 11: Parallel Grouping of Objects
     * Goal: Given List, compute total salary per department in parallel.
     * Justification: groupingBy(..., summingDouble(...)) combines partial results efficiently in a parallel reduction.*/
    public static Map<String, Double> totalSalaryByDeptPar(List<Person> staff) {
       /* return staff.parallelStream()
                .collect(Collectors.groupingBy(
                        Person::department,
                        Collectors.summingDouble(Person::salary)
                ));*/
         return Map.of();
    }

    public static Map<String, Double> totalSalaryByDeptSeq(List<Person> staff) {
        /*return staff.stream()
                .collect(Collectors.groupingBy(
                        Person::department,
                        Collectors.summingDouble(Person::salary)
                ));*/
        return Map.of();
    }
    @Test
    public void testTotalSalaryByDept() {


        List<String> departments = Arrays.asList(
                "IT",
                "HR",
                "Finance",
                "Marketing",
                "Support",
                "Maintenance",
                "Security"
        );

        double[] salaries = {1000.0, 1250.0, 1500.0, 1750.0, 2000.0, 2250.0, 2500.0, 2750.0, 3000.0};

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<Person> staff = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            String dept = departments.get(rnd.nextInt(departments.size()));
            double sal = salaries[rnd.nextInt(salaries.length)];
            staff.add(new Person(dept, sal));
        }

        Map<String, Double> warmupSeq = totalSalaryByDeptSeq(staff);
        Map<String, Double> warmupPar = totalSalaryByDeptPar(staff);

        long startSeq = System.nanoTime();
        Map<String, Double> totalSeq = totalSalaryByDeptSeq(staff);
        long timeSeq = System.nanoTime() - startSeq;

        long startPar = System.nanoTime();
        Map<String, Double> totalPar = totalSalaryByDeptPar(staff);
        long timePar = System.nanoTime() - startPar;

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

        Double hrSalarySeq = totalSeq.get("HR");
        Double hrSalaryPar = totalPar.get("HR");

        System.out.printf(
                "Sequential Salary: %.1f €€€, Parallel Salary: %.1f €€€%n",
                hrSalarySeq ,
                hrSalaryPar
        );
        assertEquals(hrSalarySeq,hrSalaryPar,0.0,"Expect same salary");
    }

/**Exercise 12: Parallel Streams for CPU intensive tasks
 *
 * @see ParallelStreamTest
 */

/** Exercise 13: Process a log file with streams to extract meaningful information
 *
 * @see FileStreamTests
*/
}

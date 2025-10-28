package klx;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

class StreamExercisesTest {

    /** Exercise 1: Filter Even Numbers
     * Goal: Given a List, return a new list containing only even numbers.*/
    public static List<Integer> filterEvens(List<Integer> numbers) {

        List<Integer> listOfEvenNumbers = new ArrayList<>();

        for(Integer someNumber : numbers) {
            if(someNumber % 2 == 0) {
                listOfEvenNumbers.add(someNumber);
            }
        }

        Iterator<Integer> iteratorNumbers = numbers.iterator();
        while(iteratorNumbers.hasNext()) {
            Integer someNumber = iteratorNumbers.next();
            if(someNumber % 2 != 0) {
                iteratorNumbers.remove();
            }
        }

        numbers.removeIf(someNumber -> someNumber % 2 != 0);

        numbers.stream()
                .filter(n -> n % 2 == 0).toList();

        return listOfEvenNumbers;
    }
    @Test
    public void testFilterEvens() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> expected = Arrays.asList(2, 4, 6);
        assertEquals(expected, filterEvens(input));
    }

    /**Exercise 2: Uppercase Conversion
     * Goal: Given a List, produce a list of all strings converted to uppercase.*/
    public static List<String> toUppercase(List<String> words) {

        List<String> uppercaseWords = new ArrayList<>(words.size());
        for(String word : words) {
            uppercaseWords.add(word.toUpperCase());
        }

        return uppercaseWords;

        // Correct stream solution
        /*return words.stream()
                .map(String::toUpperCase)
                .toList();*/
        // Wrong stream solution
        /*return words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());*/
    }

    @Test
    public void testToUppercase() {
        List<String> input = Arrays.asList("a", "bCd", "Hello");
        List<String> expected = Arrays.asList("A", "BCD", "HELLO");
        assertEquals(expected, toUppercase(input));
    }

    /**Exercise 3: Sum of Integers
     * Goal: Compute the sum of all integers in a List.*/
    public static int sumList(List<Integer> numbers) {

        int totalSum = 0;

        for (Integer someNumber : numbers) {
            totalSum += someNumber;
        }

        return totalSum;

        // Correct stream solution
        /*return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
         OR

        return numbers.stream().reduce( 0, Integer::sum);*/

        // Wrong stream solution
        /*return (int) numbers.stream().count();
         */

    }
    @Test
    public void testSumList() {
        assertEquals(15, sumList(Arrays.asList(1, 2, 3, 4, 5)));
    }

    /**Exercise 4: Find Maximum Value
     * Goal: Find the largest element in a List, returning Optional.*/
    public static Optional maxValue(List<Integer> numbers) {


            if(numbers.isEmpty()) {
                return Optional.empty();
            }

            Integer maxValue = numbers.get(0);
            for(Integer someNumber : numbers) {
                if (someNumber > maxValue) {
                    maxValue = someNumber;
                }
            }
            return Optional.of(maxValue);


            // Alternative implementation
            /*numbers.sort(Integer::compareTo);
            return Optional.of(numbers.get(numbers.size()-1));*/



        // Correct stream solution
        /*return numbers.stream()
                .max(Integer::compareTo);*/
        // Wrong stream solution
        /*return numbers.stream()
          .sorted()
          .findFirst();*/
    }
    @Test
    public void testMaxValue() {
        assertTrue(maxValue(Collections.emptyList()).isEmpty());
        assertEquals(Optional.of(99), maxValue(Arrays.asList(10, 99, 5)));
    }

    /** Exercise 5: Group Strings by Length
     * Goal: Group a list of strings into a map from length to list of strings of that length.*/
    public static Map<Integer, List<String>> groupByLength(List<String> words) {

        Map<Integer,List<String>> lengthMap = new HashMap<>();

        for (String word : words) {
            Integer length = word.length();
            List<String> wordsByLength = lengthMap.get(length);
            if (wordsByLength != null) {
                wordsByLength.add(word);
            } else {
                List<String> wordsByNewLength = new ArrayList<>();
                wordsByNewLength.add(word);
                lengthMap.put(length,wordsByNewLength);
            }
        }

        for (String word : words) {
            Integer length = word.length();
            var lst = lengthMap.computeIfAbsent(length, key -> new ArrayList<>());
            lst.add(word);
        }

        return lengthMap;

        // Correct stream solution
        /*return words.stream()
                .collect(Collectors.groupingBy(String::length));*/

        // Wrong stream solution
        /*return words.stream().collect(Collectors.toMap(String::length, s -> Arrays.asList(s)));
        */
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
     * Goal: From a sorted list, take elements from the start until a negative value appears.*/
    public static List<Integer> takeUntilNegative(List<Integer> sortedNumbers) {

        List<Integer> takeUntilNegativeNumbers = new ArrayList<>();
        for(Integer someNumber : sortedNumbers) {
            if(someNumber < 0) {
                break;
            }
            takeUntilNegativeNumbers.add(someNumber);
        }
        return takeUntilNegativeNumbers;
        // Correct stream solution
        /*return sortedNumbers.stream()
                .takeWhile(n -> n >= 0)
                .toList();*/
        // Wrong stream solution
        /*return sortedNumbers.stream()
                    .filter(n -> n >= 0)
                    .toList();*/

    }
    @Test
    public void testTakeUntilNegative() {
        List<Integer> input = Arrays.asList(3, 2, 0, -1, 5);
        List<Integer> expected = Arrays.asList(3, 2, 0);
        assertEquals(expected, takeUntilNegative(input));
    }

    /**Exercise 7 (Java 9+):
     * Goal: Create a stream from a possibly null reference, filtering nulls gracefully.*/
    public static List<String> safeList(String maybeNull) {
        return maybeNull == null ? List.of() : List.of(maybeNull);

        // Correct stream solution
        /*return Stream.ofNullable(maybeNull)
                .toList();*/
        // Wrong stream solution
        /*List<String> result = Stream.of(maybeNull).toList();
        */


    }
    @Test
    public void testSafeList() {
        assertEquals(Collections.emptyList(), safeList(null));
        assertEquals(Collections.singletonList("hi"), safeList("hi"));
    }


    /**Exercise 8: Join with Comma
     * Goal: Convert a list of words into a single comma-separated string.*/
    public static String joinWithCommas(List<String> words) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if(i != (words.size()-1)) {
                sb.append(", ");
            }
        }

        return sb.toString();

        // Correct stream solution
        /*return words.stream()
                .collect(Collectors.joining(", "));*/

        // Wrong stream solution
        /*return words.stream()
           .reduce("", (a, b) -> a + ", " + b);*/
    }
    @Test
    public void testJoinWithCommas() {
        assertEquals("a, b, c", joinWithCommas(Arrays.asList("a", "b", "c")));
    }


    // Other examples

    /** Helper Person record*/
    record Person(String department, double salary) {}

    public static Map<String, Double> totalSalaryByDeptPar(List<Person> staff) {
       return staff.parallelStream()
                .collect(Collectors.groupingBy(
                        Person::department,
                        Collectors.summingDouble(Person::salary)
                ));
    }

    public static Map<String, Double> totalSalaryByDeptSeq(List<Person> staff) {
        return staff.stream()
                .collect(Collectors.groupingBy(
                        Person::department,
                        Collectors.summingDouble(Person::salary)
                ));
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

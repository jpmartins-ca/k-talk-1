package klx;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankStreamTests {

    @Test
    //@Disabled("Warm up : Basic IntStream and filter to implement using stream")
    void shouldReturnOnlyEvenNumbers() {
        // Arrange
        int[] numbers = {1, 2, 3, 4, 5, 6};

        // Act
        List<Integer> evenNumbers = Arrays.stream(numbers).filter(p -> p % 2 == 0).boxed().toList(); // Collect to List

        // Assert
        assertEquals(List.of(2, 4, 6), evenNumbers);
    }

    @Test
    //@Disabled("Warm up : Map and collect, to implement using stream")
    void shouldConvertStringsToUpperCase() {
        // Arrange
        List<String> names = List.of("Ana", "Beatriz", "Carlos");

        // Act
        List<String> uppercaseNames = names.stream().map(String::toUpperCase).toList();

        // Assert
        assertEquals(List.of("ANA", "BEATRIZ", "CARLOS"), uppercaseNames);
    }

    @Test
    //@Disabled("Warm up : Reduce, to implement using stream")
    void shouldCalculateProductOfNumbers() {
        // Arrange
        List<Integer> numbers = List.of(1, 2, 3, 4);

        // Act
        // Calculate the product of all numbers using reduce
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);

        // Assert
        assertEquals(24, product);
    }

    @Test
    //@Disabled("Displaying output to console to implement using stream, such as peek")
    @SuppressWarnings("java:S1612") // ignore Lambda can be replaced with Method reference
    void shouldDisplayTwoTimesEachNumberInConsole() {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outStream);

        // Tip, notice that streams are lazy
        // We need to use peek for side effects and a terminal operation to execute the stream
        System.setOut(printStream);
        numbers.stream()
               .peek(n -> System.out.print(n))  // First print
               .peek(System.out::print)  // Second print, we can also use method reference instead of lambda
               .forEach(n -> {});  // Terminal operation to execute the stream

        String expectedOutput = "11223344";
        assertEquals(expectedOutput, outStream.toString());
    }

    @Test
    //@Disabled("Account.getBalance to implement using stream")
    void shouldFilterAccountsWithBalanceAboveThreshold() {
        // Arrange
        Account anaAccount = new Account("2", "Ana", 1500);
        Account joaoAccount = new Account("1", "João", 500);
        Account brunAccount = new Account("3", "Bruno", 2000);
        List<Account> accounts = Arrays.asList(joaoAccount, anaAccount, brunAccount);

        // Act
        List<Account> result = accounts.stream()
                .filter(account -> account.getBalance() > 1000)
                .toList();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(anaAccount));
        assertTrue(result.contains(brunAccount));
    }

    @Test
    //@Disabled("groupTransactionsByType to implement using stream")
    void shouldGroupTransactionsByType() {
        // Arrange
        Transaction t1 = new Transaction(100, LocalDate.now(), Account.DEPOSIT);
        Transaction t2 = new Transaction(50, LocalDate.now(), Account.WITHDRAWAL);
        Transaction t3 = new Transaction(200, LocalDate.now(), Account.DEPOSIT);
        List<Transaction> transactions = Arrays.asList(t1, t2, t3);

        // Act
        Map<String, List<Transaction>> grouped = Transaction.groupTrasactionsByType(transactions);

        // Assert
        assertEquals(2, grouped.size());
        assertEquals(2, grouped.get(Account.DEPOSIT).size());
        assertEquals(1, grouped.get(Account.WITHDRAWAL).size());

        // Check total amounts per type
        // Calculate total deposit amount using stream
        double depositTotal = grouped.get(Account.DEPOSIT).stream()
                .mapToDouble(Transaction::amount)
                .sum();

        // Calculate total withdrawal amount using stream
        double withdrawalTotal = grouped.get(Account.WITHDRAWAL).stream()
                .mapToDouble(Transaction::amount)
                .sum();

        assertEquals(300, depositTotal);
        assertEquals(50, withdrawalTotal);
    }


}

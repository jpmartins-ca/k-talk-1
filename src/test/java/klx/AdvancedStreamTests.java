package klx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced tests for Java Streams exercises.
 * These tests cover more complex stream operations and can be used
 * as part of a 30-minute practical session on Java Streams.
 */
class AdvancedStreamTests {

    private Bank bank;
    private Transaction t1, t2, t3, t4, t5;

    @BeforeEach
    void setUp() {
        // Create sample transactions
        t1 = new Transaction(500.0, LocalDate.now(), Account.DEPOSIT);
        t2 = new Transaction(1500.0, LocalDate.now(), Account.WITHDRAWAL);
        t3 = new Transaction(7000.0, LocalDate.now(), Account.DEPOSIT);
        t4 = new Transaction(200.0, LocalDate.now(), Account.DEPOSIT);
        t5 = new Transaction(3000.0, LocalDate.now(), Account.WITHDRAWAL);

        // Create sample accounts
        Account account1 = new Account("12345", "John Doe", List.of(t1, t2));
        Account account2 = new Account("67890", "Jane Smith", List.of(t3, t4));
        Account account3 = new Account("54321", "Bob Johnson", List.of(t5));

        // Create sample bank
        bank = new Bank("TestBank", Arrays.asList(account1, account2, account3));
    }

    /**
     * Partition Transactions by criteria has amount > 1000 or not (boolean partition)
     */
    @Test
    void testPartitioningTransactionsByAmount() {
        // Exercise 1: Partitioning transactions by amount
        Map<Boolean, List<Transaction>> partitioned = bank.accounts().stream()
                .flatMap(account -> account.transactions().stream())
                .collect(Collectors.partitioningBy(tx -> tx.amount() > 1000));

        // Verify partitioning
        assertEquals(3, partitioned.get(true).size());
        assertEquals(2, partitioned.get(false).size());
        assertTrue(partitioned.get(true).contains(t2));
        assertTrue(partitioned.get(true).contains(t3));
        assertTrue(partitioned.get(true).contains(t5));
        assertTrue(partitioned.get(false).contains(t1));
        assertTrue(partitioned.get(false).contains(t4));
    }

    @Test
    void testTransactionStatistics() {
        // Exercise 2: Statistics on transaction amounts
        DoubleSummaryStatistics stats = bank.accounts().stream()
                .flatMap(account -> account.transactions().stream())
                .collect(Collectors.summarizingDouble(Transaction::amount));

        // Verify statistics
        assertEquals(5, stats.getCount());
        assertEquals(12200.0, stats.getSum());
        assertEquals(200.0, stats.getMin());
        assertEquals(7000.0, stats.getMax());
        assertEquals(12200.0 / 5, stats.getAverage());
    }

    @Test
    void testJoiningCustomerNames() {
        // Exercise 3: Joining customer names with a delimiter
        String joined = bank.accounts().stream()
                .map(Account::customerName)
                .collect(Collectors.joining(", ", "Customers: [", "]"));

        // Verify joined string
        assertEquals("Customers: [John Doe, Jane Smith, Bob Johnson]", joined);
    }

    @Test
    void testFindingAccountsWithSpecificTransactionPatterns() {
        // Exercise 4: Finding accounts with specific transaction patterns
        boolean hasLargeDeposit = bank.accounts().stream()
                .anyMatch(account -> account.transactions().stream()
                        .filter(tx -> tx.type().equals(Account.DEPOSIT))
                        .anyMatch(tx -> tx.amount() > 5000));

        // Verify pattern matching (more than 5000)
        assertTrue(hasLargeDeposit);
        
        // Test for a pattern that doesn't exist (amount > 10000)
        boolean hasVeryLargeDeposit = bank.accounts().stream()
                .anyMatch(account -> account.transactions().stream()
                        .filter(tx -> tx.type().equals(Account.DEPOSIT))
                        .anyMatch(tx -> tx.amount() > 10000));
                        
        assertFalse(hasVeryLargeDeposit);
    }

    @Test
    void testTransformingDataWithMappingCollectors() {
        // Exercise 5: Transforming data with mapping and flatMapping collectors
        Map<String, Set<Double>> amountsByType = bank.accounts().stream()
                .flatMap(account -> account.transactions().stream())
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.mapping(Transaction::amount, Collectors.toSet())
                ));

        // Verify transformed data
        assertEquals(2, amountsByType.size());
        assertTrue(amountsByType.containsKey(Account.DEPOSIT));
        assertTrue(amountsByType.containsKey(Account.WITHDRAWAL));
        assertEquals(Set.of(500.0, 7000.0, 200.0), amountsByType.get(Account.DEPOSIT));
        assertEquals(Set.of(1500.0, 3000.0), amountsByType.get(Account.WITHDRAWAL));
    }

    @Test
    void testAverageAccountBalance() {
        // Exercise 6: Custom collector example - calculating average balance
        double avgBalance = bank.accounts().stream()
                .collect(Collectors.averagingDouble(Account::getBalance));

        // Verify average balance
        // account1: 500 - 1500 = -1000
        // account2: 7000 + 200 = 7200
        // account3: -3000
        // Average: ((-1000) + 7200 + (-3000)) / 3 = 3200 / 3 = 1066.67
        assertEquals(1066.67, avgBalance, 0.01);
    }

    @Test
    void testReduceForCustomAccumulation() {
        // Exercise 7: Using reduce for custom accumulation
        double totalPositiveAmount = bank.accounts().stream()
                .flatMap(account -> account.transactions().stream())
                .filter(tx -> tx.type().equals(Account.DEPOSIT))
                .map(Transaction::amount)
                .reduce(0.0, Double::sum);

        // Verify accumulated result
        assertEquals(7700.0, totalPositiveAmount);
    }
}

package klx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Bank class representing a collection of accounts.
 */
class Bank {
    private final String name;
    private final List<Account> accounts;


    public Bank(String name, List<Account> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }
}

/**
 * Represents a bank account with a list of transactions.
 */
class Account {
    static final String DEPOSIT = "DEPOSIT";
    static final String WITHDRAWAL = "WITHDRAWAL";
    private final String accountNumber;
    private final String customerName;
    private final List<Transaction> transactions;

    public Account(String accountNumber, String customerName, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.transactions = transactions;
    }

    public Account(String number, String ana, int initialBalance) {
        this.accountNumber = number;
        this.customerName = ana;
        this.transactions = new ArrayList<>();
        if (initialBalance > 0) {
            this.transactions.add(new Transaction(initialBalance, LocalDate.now(), DEPOSIT));
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        // Calculate balance by summing all transactions
        // For deposits, add the amount; for withdrawals, subtract the amount
        return transactions.stream()
                .mapToDouble(tx -> tx.getType().equals(DEPOSIT) ? tx.getAmount() : -tx.getAmount())
                .sum();
    }
}

/**
 * Represents a bank transaction.
 */
class Transaction {
    private final double amount;
    private final LocalDate date;
    private final String type; // Example: "DEPOSIT", "WITHDRAWAL"

    public Transaction(double amount, LocalDate date, String type) {
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public static Map<String, List<Transaction>> groupTrasactionsByType(List<Transaction> transactions) {
        // Group transactions by their type using the groupingBy collector
        // This is efficient even for large collections as it processes the stream in a single pass
        // For millions of transactions, we could use parallelStream() for better performance
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public double getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
}

/**
 * Demonstrates the use of Java Streams with a banking example.
 * This class contains examples and exercises for a 30-minute practical session on Java Streams.
 */
public class BankStreamDemo {

    /**
     * Main method to demonstrate various stream operations.
     * The examples progress from basic to advanced concepts.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Sample Transactions
        Transaction t1 = new Transaction(500.0, LocalDate.now(), Account.DEPOSIT);
        Transaction t2 = new Transaction(1500.0, LocalDate.now(), Account.WITHDRAWAL);
        Transaction t3 = new Transaction(7000.0, LocalDate.now(), Account.DEPOSIT);

        // Sample Accounts
        Account account1 = new Account("12345", "John Doe", Arrays.asList(t1, t2));
        Account account2 = new Account("67890", "Jane Smith", Collections.singletonList(t3));

        // Sample Bank
        Bank bank = new Bank("MyBank", Arrays.asList(account1, account2));

        // 0. Simple Stream Example from a specific account
        Stream<Transaction> stream = account1.getTransactions().stream();
        double totalBalance = stream.mapToDouble(Transaction::getAmount)
                                    .sum();
        System.out.println("Total Balance for Account " + account1.getAccountNumber() + ": " + totalBalance);

        // 1. Introduction to Streams
        List<String> accountNumbers = bank.getAccounts()
                .stream()
                .map(Account::getAccountNumber)
                .collect(Collectors.toList());
        System.out.println("Account Numbers: " + accountNumbers);

        // 2. Core Concepts and Basics
        List<Transaction> allTransactions = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(tx -> tx.getAmount() > 1000)
                .toList();
        System.out.println("High Value Transactions: " + allTransactions.size());

        // Create Streams
        String[] accountTypes = {"SAVINGS", "CURRENT", "BUSINESS"};
        Stream.of(accountTypes).forEach(System.out::println);

        // 3. Intermediate Operations
        List<String> customers = bank.getAccounts()
                .stream()
                .filter(account -> account.getTransactions()
                        .stream()
                        .anyMatch(tx -> tx.getAmount() > 5000))
                .map(Account::getCustomerName)
                .toList();
        System.out.println("Customers with high-value transactions: " + customers);

        List<Transaction> sortedTransactions = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .sorted(Comparator.comparing(Transaction::getAmount))
                .toList();
        sortedTransactions.forEach(tx -> System.out.println("Sorted Transaction Amount: " + tx.getAmount()));

        // 4. Terminal Operations
        totalBalance = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .mapToDouble(Transaction::getAmount)
                .sum();
        System.out.println("Total Bank Balance: " + totalBalance);

        Map<String, List<Transaction>> transactionsByType = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .collect(Collectors.groupingBy(Transaction::getType));
        System.out.println("Transactions Grouped By Type: " + transactionsByType);

        Transaction firstHighValueTx = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(tx -> tx.getAmount() > 5000)
                .findFirst()
                .orElse(null);
        System.out.println("First High Value Transaction: " + (firstHighValueTx != null ? firstHighValueTx.getAmount() : "None"));

        // 5. Advanced Stream Features
        bank.getAccounts()
                .parallelStream()
                .flatMap(account -> account.getTransactions().parallelStream())
                .forEach(tx -> System.out.println("Transaction in parallel: " + tx.getAmount()));

        double averageTransaction = bank.getAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0);
        System.out.println("Average Transaction Amount: " + averageTransaction);

        // 6. Common Pitfalls
        List<String> names = new ArrayList<>();
        bank.getAccounts().stream()
                .map(Account::getCustomerName)
                .forEach(names::add); // Avoid modifying external state
        System.out.println("Customer names: " + names);

        // 7. Additional Practical Exercises for a 30-minute session
        System.out.println("\n--- PRACTICAL EXERCISES ---");

        // Exercise 1: Partitioning transactions by amount (greater than 1000)
        // Demonstrates partitioningBy collector
        Map<Boolean, List<Transaction>> partitionedByAmount = bank.getAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .collect(Collectors.partitioningBy(tx -> tx.getAmount() > 1000));
        System.out.println("High-value transactions count: " + partitionedByAmount.get(true).size());
        System.out.println("Low-value transactions count: " + partitionedByAmount.get(false).size());

        // Exercise 2: Statistics on transaction amounts
        // Demonstrates statistical collectors
        DoubleSummaryStatistics stats = bank.getAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .collect(Collectors.summarizingDouble(Transaction::getAmount));
        System.out.println("Transaction Statistics: " + stats);

        // Exercise 3: Joining customer names with a delimiter
        // Demonstrates joining collector
        String allCustomers = bank.getAccounts().stream()
                .map(Account::getCustomerName)
                .collect(Collectors.joining(", ", "Customers: [", "]"));
        System.out.println(allCustomers);

        // Exercise 4: Finding accounts with specific transaction patterns
        // Demonstrates anyMatch/allMatch/noneMatch
        boolean hasLargeDeposit = bank.getAccounts().stream()
                .anyMatch(account -> account.getTransactions().stream()
                        .filter(tx -> tx.getType().equals(Account.DEPOSIT))
                        .anyMatch(tx -> tx.getAmount() > 5000));
        System.out.println("Has account with large deposit: " + hasLargeDeposit);

        // Exercise 5: Transforming data with mapping and flatMapping collectors
        // Demonstrates mapping and flatMapping collectors
        Map<String, Set<Double>> amountsByType = bank.getAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.mapping(Transaction::getAmount, Collectors.toSet())
                ));
        System.out.println("Unique amounts by transaction type: " + amountsByType);

        // Exercise 6: Custom collector example - calculating average balance
        // Demonstrates how to create a custom collector
        double avgBalance = bank.getAccounts().stream()
                .collect(Collectors.averagingDouble(Account::getBalance));
        System.out.println("Average account balance: " + avgBalance);

        // Exercise 7: Using reduce for custom accumulation
        // Demonstrates more complex reduce operations
        double totalPositiveAmount = bank.getAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(tx -> tx.getType().equals(Account.DEPOSIT))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
        System.out.println("Total positive amount: " + totalPositiveAmount);
    }
}

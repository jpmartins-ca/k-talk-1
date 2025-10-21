# Java Streams Practical Session (30 minutes)

This repository contains a practical exercise on Java Streams for a 30-minute session. The exercise is based on a banking 
application and covers various aspects of Java Streams from basic to advanced concepts.

## Session Structure

1. **Introduction (2 minutes)**
   - Brief overview of the banking application
   - Explanation of the exercise structure

2. **Warm-up Exercises (8 minutes)**
   - Basic stream operations
   - Filter, map, and collect
   - Reduce operation

3. **Main Exercises (15 minutes)**
   - Account balance calculation using streams
   - Grouping transactions by type
   - Partitioning data
   - Statistical operations
   - Advanced collectors

4. **Discussion and Q&A (5 minutes)**
   - Review of solutions
   - Best practices
   - Performance considerations

## Getting Started

1. Clone this repository
2. Open the project in your IDE
3. Run the tests to see what needs to be implemented
4. Implement the missing code in the classes

## Exercise Details

### Warm-up Exercises

These are simple exercises to get familiar with basic stream operations:

1. **Filter even numbers**: Implement a stream to filter even numbers from an array
2. **Convert strings to uppercase**: Use map to transform strings
3. **Calculate product of numbers**: Use reduce to multiply numbers

### Main Exercises

These exercises focus on applying streams to a banking application:

1. **Account Balance Calculation**: Implement the `getBalance()` method in the `Account` class using streams
2. **Transaction Grouping**: Implement the `groupTrasactionsByType()` method in the `Transaction` class
3. **Advanced Stream Operations**: Implement various stream operations in the `BankStreamDemo` class:
   - Partitioning transactions by amount
   - Calculating statistics on transaction amounts
   - Joining customer names
   - Finding accounts with specific transaction patterns
   - Transforming data with mapping collectors
   - Using custom collectors
   - Using reduce for custom accumulation

## Testing Your Implementation

Run the tests to verify your implementation:

```bash
mvn test
```

The tests are divided into two classes:
- `BankStreamTests`: Basic tests for the warm-up exercises
- `AdvancedStreamTests`: Tests for the advanced stream operations

## Tips for Presenters

1. **Start with the basics**: Begin by explaining the filter, map, and collect operations
2. **Show examples**: Demonstrate each operation with simple examples
3. **Encourage participation**: Ask participants to implement the exercises
4. **Provide hints**: If participants are stuck, provide hints rather than complete solutions
5. **Discuss trade-offs**: Talk about when to use different stream operations
6. **Highlight common pitfalls**: Discuss issues like modifying external state in streams

## Additional Resources

- [Java Streams Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
- [Stream Operations Cheat Sheet](https://www.jrebel.com/blog/java-streams-cheat-sheet)
- [Java 8 Stream Tutorial](https://www.baeldung.com/java-8-streams)

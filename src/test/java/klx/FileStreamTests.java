package klx;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Step-by-step example, generate file or reuse file */
class FileStreamTests {

    /**
         * Represents a parsed log entry with timestamp and HTTP status code.
         */
        record LogEntry(LocalDateTime timestamp, int statusCode) {
    }

    /**
     * Parses raw log lines into LogEntry objects.
     * Expected format: "2025-07-08T12:34:56Z 503"
     */
    static class LogParser {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

        static Optional<LogEntry> parse(String line) {
            try {
                String[] parts = line.split("\\s+", 3);
                LocalDateTime ts = LocalDateTime.parse(parts[0], FORMATTER);
                int code = Integer.parseInt(parts[1]);
                return Optional.of(new LogEntry(ts, code));
            } catch (Exception ex) {
                // drop malformed lines
                return Optional.empty();
            }
        }
    }

    /**
     * Reads the log file, filters for status >= 500 aggregates counts by hour.
     */
    static class LogAggregator {
        static Map<LocalDateTime, Long> aggregateErrorsByHour(Path logPath) throws IOException {
            try (Stream<String> lines = Files.lines(logPath)) {
                return lines
                        .flatMap(line -> LogParser.parse(line).stream())
                        .filter(entry -> entry.statusCode() >= 500)
                        .collect(Collectors.groupingBy(
                                e -> e.timestamp().truncatedTo(ChronoUnit.HOURS),
                                Collectors.counting()
                        ));
            }
        }
    }

    @Test
    @Disabled("Needs preparation create file Public/testFile.log")
    void testAggregateErrorsByHour() throws IOException {

        Path logFile = Paths.get("public/testFile.log");

        // Aggregate
        Map<LocalDateTime, Long> errorsByHour =
                LogAggregator.aggregateErrorsByHour(logFile);

        // Each of the 10 hour-keys should have exactly 3 errors
        errorsByHour.forEach((hour, count) ->
                assertEquals(3L, count,
                        () -> "Expect 3 errors this hour " + hour)
        );

        // Across all hours we should have a total of 30 errors
        long totalErrors = errorsByHour.values().stream()
                .mapToLong(Long::longValue)
                .sum();
        assertEquals(30L, totalErrors, "Total 503 status lines should be 30");


    }

    /**
     * Writes a CSV report with header "hour,errorCount"
     * and one row per hour in ascending order.
     */
    static class CsvReporter {
        static void writeReport(Map<LocalDateTime, Long> errorsByHour, Path outputCsv)
                throws IOException {
            try (BufferedWriter writer = Files.newBufferedWriter(outputCsv)) {
                writer.write("hour,errorCount");
                writer.newLine();

                errorsByHour.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            String row = String.format("%s,%d", entry.getKey(), entry.getValue());
                            try {
                                writer.write(row);
                                writer.newLine();
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            }
        }
    }
}

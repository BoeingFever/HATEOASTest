package com.example.HATEOASTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MathUtilsTest {
    // Example 1: Using @ValueSource for simple values
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testIsPositive_withValueSource(int number) {
        assertTrue(MathUtils.isPositive(number));
    }

    // Example 2: Using @CsvSource for multiple parameters
    @ParameterizedTest
    @CsvSource({
            "2, 3, 5",    // a, b, expected
            "5, 7, 12",
            "0, 0, 0",
            "-1, 1, 0"
    })
    void testAddNumbers(int a, int b, int expected) {
        assertEquals(expected, MathUtils.add(a, b));
    }

    // Example 3: Using @MethodSource for complex objects
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void testIsBlank(String input, boolean expected) {
        assertEquals(expected, MathUtils.isBlank(input));
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    // Example 4: Using enum values
    @ParameterizedTest
    @EnumSource(Month.class) // passing all 12 months
    void testMonthNumberIsBetween1and12(Month month) {
        int monthNumber = month.getMonthNumber();
        assertTrue(monthNumber >= 1 && monthNumber <= 12);
    }
}

// Supporting classes for the example
class MathUtils {
    static boolean isPositive(int number) {
        return number > 0;
    }

    static int add(int a, int b) {
        return a + b;
    }

    static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
}

enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4),
    MAY(5), JUNE(6), JULY(7), AUGUST(8),
    SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

    private final int value;

    Month(int value) {
        this.value = value;
    }

    public int getMonthNumber() {
        return value;
    }
}

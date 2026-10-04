/*
 * File: InputValidator.java
 *
 * Purpose:
 * Checks the text typed by the user and converts it into numbers.
 * If the text is wrong, it throws an exception with a clear message.
 *
 * Why this file is used:
 * We want to keep validation logic separate from the GUI code. The GUI only
 * catches the exception and shows the message, so the GUI stays short.
 *
 * Responsibility:
 * - Validate the number of days (positive whole number)
 * - Validate a sales amount (numeric and not negative)
 * - Validate the day number used for searching (inside the entered range)
 *
 * Connection:
 * RetailSalesGUI calls these methods when the user clicks buttons.
 * Each method throws IllegalArgumentException with a user-friendly message,
 * and the GUI shows that message in a JOptionPane dialog.
 */
package com.retail.sales.util;

public class InputValidator {

    // Maximum days allowed so that the screen does not get too many fields.
    public static final int MAX_DAYS = 365;

    // Maximum sales amount for one day (just to reject unreasonable values).
    public static final double MAX_SALE = 1_000_000_000_000.0;

    // Private constructor: only static methods are used.
    private InputValidator() {
    }

    // Validates the "Number of Days" text and returns it as an int.
    public static int parseNumberOfDays(String text) {
        // Empty input check.
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Number of days cannot be empty. Please enter a valid positive number of days.");
        }

        int days;
        try {
            // Convert text to a whole number. Letters or decimals cause an exception.
            days = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            // Exception handling: the text was not a whole number.
            throw new IllegalArgumentException("Please enter a valid positive number of days.");
        }

        // Days must be at least 1.
        if (days <= 0) {
            throw new IllegalArgumentException("Please enter a valid positive number of days.");
        }

        // Do not allow a huge number of days.
        if (days > MAX_DAYS) {
            throw new IllegalArgumentException("Number of days cannot be more than " + MAX_DAYS + ".");
        }

        return days;
    }

    // Validates one sales amount and returns it as a double.
    public static double parseSalesAmount(String text) {
        // Empty input check.
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Sales amount cannot be empty. Please enter a valid sales amount.");
        }

        // Remove spaces and commas so that values like 12,500 are also accepted.
        String cleaned = text.trim().replace(",", "");

        // The text must look like a plain number: optional minus, digits,
        // and optional decimal part. This also rejects things like "12abc",
        // "NaN" or "1e5".
        if (!cleaned.matches("-?\\d+(\\.\\d+)?")) {
            throw new IllegalArgumentException("Please enter a valid sales amount.");
        }

        double amount;
        try {
            amount = Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            // Exception handling: safety net in case parsing still fails.
            throw new IllegalArgumentException("Please enter a valid sales amount.");
        }

        // Relational operator: sales cannot be below zero.
        if (amount < 0) {
            throw new IllegalArgumentException("Sales amount cannot be negative.");
        }

        // Logical operator: reject numbers that are too big or infinite.
        if (Double.isInfinite(amount) || amount > MAX_SALE) {
            throw new IllegalArgumentException("Sales amount is too large. Please enter a valid sales amount.");
        }

        return amount;
    }

    // Validates the day number typed for searching.
    // maxDay is the number of days the user entered earlier.
    public static int parseDayNumber(String text, int maxDay) {
        // Empty input check.
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Day number cannot be empty. Day number must be between 1 and " + maxDay + ".");
        }

        int day;
        try {
            day = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            // Exception handling: not a whole number.
            throw new IllegalArgumentException("Please enter a valid day number. Day number must be between 1 and " + maxDay + ".");
        }

        // Logical OR: the day is wrong if it is too small or too big.
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("Day number must be between 1 and " + maxDay + ".");
        }

        return day;
    }
}

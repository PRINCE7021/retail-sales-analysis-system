/*
 * File: RupeeFormatter.java
 *
 * Purpose:
 * Converts a number (double) into a text in Indian Rupee format,
 * for example 125000 becomes "1,25,000.00" with the rupee symbol in front.
 *
 * Why this file is used:
 * Java's normal number formatting groups digits in 3s (1,250,000), but the
 * Indian system groups the last 3 digits and then every 2 digits (12,50,000).
 * So we wrote our own small formatter. Having it in one place means every
 * amount in the project looks the same.
 *
 * Responsibility:
 * - Holds the rupee symbol used everywhere in the project
 * - Formats amounts with Indian digit grouping and 2 decimal places
 *
 * Connection:
 * RetailSalesGUI calls RupeeFormatter.format() every time it shows an amount
 * (table, total, average, highest, lowest and search result).
 *
 * Note:
 * The rupee symbol is written as the Unicode escape \u20B9 so that the source
 * file stays plain ASCII and compiles correctly on any computer.
 */
package com.retail.sales.util;

import java.util.Locale;

public class RupeeFormatter {

    // The Indian Rupee symbol (Unicode code 20B9).
    public static final String SYMBOL = "\u20B9";

    // Private constructor: this class only has static methods, so nobody
    // needs to create an object of it.
    private RupeeFormatter() {
    }

    // Formats an amount like 125000 as the rupee symbol followed by 1,25,000.00
    public static String format(double amount) {
        // Round to exactly 2 decimal places. Locale.US makes sure the decimal
        // point is a "." on every computer.
        String text = String.format(Locale.US, "%.2f", Math.abs(amount));

        // Split the text into the whole-rupee part and the paise part.
        int dotPosition = text.indexOf('.');
        String wholePart = text.substring(0, dotPosition);
        String paisePart = text.substring(dotPosition);   // includes the "."

        String groupedWhole;

        if (wholePart.length() <= 3) {
            // Numbers up to 999 need no commas.
            groupedWhole = wholePart;
        } else {
            // Indian style: the last 3 digits stay together...
            String lastThree = wholePart.substring(wholePart.length() - 3);
            String remaining = wholePart.substring(0, wholePart.length() - 3);

            // ...and the remaining digits are grouped in pairs from the right.
            StringBuilder grouped = new StringBuilder();
            int digitCount = 0;

            // Loop from the last remaining digit to the first one.
            for (int i = remaining.length() - 1; i >= 0; i--) {
                grouped.insert(0, remaining.charAt(i));
                digitCount++;

                // After every 2 digits add a comma (but not at the very start).
                if (digitCount == 2 && i != 0) {
                    grouped.insert(0, ',');
                    digitCount = 0;
                }
            }
            groupedWhole = grouped + "," + lastThree;
        }

        // Add a minus sign if the amount was negative (not expected for sales).
        String sign = (amount < 0) ? "-" : "";

        return sign + SYMBOL + groupedWhole + paisePart;
    }
}

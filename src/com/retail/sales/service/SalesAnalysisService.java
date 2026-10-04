/*
 * File: SalesAnalysisService.java
 *
 * Purpose:
 * Contains all the sales analysis logic: total, average, highest, lowest
 * and searching a sale by day number.
 *
 * Why this file is used:
 * We separate the "business logic" (calculations) from the GUI (screen).
 * This class has NO Swing code, so it only works with numbers and arrays.
 * Because of this, the logic is easy to understand, test and reuse.
 *
 * Responsibility:
 * - Stores the daily sales in a double[] array
 * - calculateTotal(), calculateAverage()
 * - findHighestSale(), findLowestSale() (and the day on which they happened)
 * - searchSaleByDay()
 *
 * Connection:
 * RetailSalesGUI creates one object of this class and calls its methods
 * when the user clicks the buttons. The GUI then formats the returned
 * numbers using RupeeFormatter.
 *
 * OOP concepts used here:
 * - Class and object
 * - Encapsulation: the sales array is private; it is only reached through
 *   public methods.
 */
package com.retail.sales.service;

public class SalesAnalysisService {

    // Array that stores one sales value for each day.
    // Index 0 is Day 1, index 1 is Day 2, and so on.
    private double[] sales = new double[0];

    // ---------------------------------------------------------------
    // Module 1: Sales Entry (storing the data)
    // ---------------------------------------------------------------

    // Saves the sales values. We copy the array so that outside code
    // cannot change our data by mistake (encapsulation).
    public void setSales(double[] newSales) {
        // Create an array with the same size as the entered data.
        sales = new double[newSales.length];

        // Loop through every value and copy it into our own array.
        for (int i = 0; i < newSales.length; i++) {
            sales[i] = newSales[i];
        }
    }

    // Returns a copy of the sales array.
    public double[] getSales() {
        double[] copy = new double[sales.length];
        for (int i = 0; i < sales.length; i++) {
            copy[i] = sales[i];
        }
        return copy;
    }

    // Returns how many days of sales are stored.
    public int getNumberOfDays() {
        return sales.length;
    }

    // Returns true if some sales data is stored.
    public boolean hasData() {
        return sales.length > 0;
    }

    // Removes all stored data (used by the Clear button).
    public void clearSales() {
        sales = new double[0];
    }

    // ---------------------------------------------------------------
    // Module 2: Total Calculation
    // ---------------------------------------------------------------

    // Calculates the total sales by adding all daily sales values.
    public double calculateTotal() {
        // Start the total from 0.
        double total = 0;

        // Loop through every day's sales value.
        for (int i = 0; i < sales.length; i++) {
            // Arithmetic operator += adds the day's sale to the total.
            total += sales[i];
        }

        return total;
    }

    // ---------------------------------------------------------------
    // Module 3: Average Calculation
    // ---------------------------------------------------------------

    // Average = total sales / number of days.
    public double calculateAverage() {
        // Avoid dividing by zero when there is no data.
        if (sales.length == 0) {
            return 0;
        }

        // Arithmetic operator / divides the total by the number of days.
        return calculateTotal() / sales.length;
    }

    // ---------------------------------------------------------------
    // Module 4: Highest / Lowest Sales
    // ---------------------------------------------------------------

    // Finds the highest sale value.
    public double findHighestSale() {
        // Start with the first day's sale as the highest value.
        double highest = sales[0];

        // Compare each sale with the current highest value.
        for (int i = 1; i < sales.length; i++) {
            // Comparison operator > checks if this day is bigger.
            if (sales[i] > highest) {
                highest = sales[i];
            }
        }

        return highest;
    }

    // Finds the day number (starting from 1) of the highest sale.
    public int findHighestSaleDay() {
        int highestIndex = 0;

        for (int i = 1; i < sales.length; i++) {
            if (sales[i] > sales[highestIndex]) {
                highestIndex = i;
            }
        }

        // Index 0 means Day 1, so we add 1.
        return highestIndex + 1;
    }

    // Finds the lowest sale value.
    public double findLowestSale() {
        // Start with the first day's sale as the lowest value.
        double lowest = sales[0];

        // Compare each sale with the current lowest value.
        for (int i = 1; i < sales.length; i++) {
            // Comparison operator < checks if this day is smaller.
            if (sales[i] < lowest) {
                lowest = sales[i];
            }
        }

        return lowest;
    }

    // Finds the day number (starting from 1) of the lowest sale.
    public int findLowestSaleDay() {
        int lowestIndex = 0;

        for (int i = 1; i < sales.length; i++) {
            if (sales[i] < sales[lowestIndex]) {
                lowestIndex = i;
            }
        }

        return lowestIndex + 1;
    }

    // ---------------------------------------------------------------
    // Module 5: Sales Search
    // ---------------------------------------------------------------

    // Searches the sales for a given day number using LINEAR SEARCH.
    // Linear search means we check the days one by one from the start
    // until we find the day we want.
    public double searchSaleByDay(int dayNumber) {
        // Loop through all the days in order.
        for (int i = 0; i < sales.length; i++) {
            // Day number is index + 1 (Day 1 is stored at index 0).
            if ((i + 1) == dayNumber) {
                // Found the day, return its sales value.
                return sales[i];
            }
        }

        // If the loop ends, the day was not in the array.
        throw new IllegalArgumentException("Day " + dayNumber + " was not found. Day number must be between 1 and " + sales.length + ".");
    }
}

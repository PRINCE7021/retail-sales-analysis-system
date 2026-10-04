/*
 * File: Main.java
 *
 * Purpose:
 * This is the main entry point of the Retail Sales Analysis System.
 * It starts the Java Swing application and opens the main GUI window.
 *
 * Why this file is used:
 * Every Java program needs a class with a main() method to start running.
 * Keeping it in its own small file means the starting code is easy to find.
 *
 * Responsibility:
 * - Starts the application
 * - Creates the main GUI object (RetailSalesGUI)
 * - Launches the Swing interface on the correct Swing thread
 *
 * Connection:
 * This file starts RetailSalesGUI (gui package). RetailSalesGUI then uses
 * SalesAnalysisService (service package) for calculations, and the helper
 * classes InputValidator and RupeeFormatter (util package).
 */
package com.retail.sales;

import com.retail.sales.gui.RetailSalesGUI;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Swing components should be created on the Event Dispatch Thread (EDT).
        // SwingUtilities.invokeLater() puts our code on that thread safely.
        SwingUtilities.invokeLater(() -> {
            // Create the main window object.
            RetailSalesGUI gui = new RetailSalesGUI();

            // Show the window on the screen.
            gui.setVisible(true);
        });
    }
}


/*
 * File: RetailSalesGUI.java
 *
 * Purpose:
 * This is the main window (screen) of the Retail Sales Analysis System,
 * built with Java Swing.
 *
 * Why this file is used:
 * The case study asks us to accept sales data and show the analysis.
 * A console program would use Scanner for input. In this project we use a
 * Swing GUI instead, so the same job is done by JTextField (input) and
 * buttons with event listeners (actions). Output is shown in a JTable and
 * a JTextArea instead of System.out.println().
 *
 * Responsibility:
 * - Creates the JFrame and all the components (labels, text fields, buttons)
 * - Takes the user's input and handles the button click events
 * - Shows results and error messages (JOptionPane)
 * - Calls SalesAnalysisService for all calculations (no calculation logic
 *   is written here)
 *
 * Connection:
 * - Started by Main.java
 * - Uses SalesAnalysisService (service package) for total, average,
 *   highest, lowest and search
 * - Uses InputValidator (util package) to check what the user typed
 * - Uses RupeeFormatter (util package) to show amounts as Indian Rupees
 *
 * OOP concepts used here:
 * - Inheritance: RetailSalesGUI extends JFrame
 * - Encapsulation: components are private fields
 * - Abstraction / modularity: each button has its own method
 */
package com.retail.sales.gui;

import com.retail.sales.service.SalesAnalysisService;
import com.retail.sales.util.InputValidator;
import com.retail.sales.util.RupeeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

// JFrame is the main window class of Swing. Our class "extends" it,
// so our class IS a window (this is inheritance).
public class RetailSalesGUI extends JFrame {

    // ------------------------------------------------------------
    // Fields (data used by the whole class)
    // ------------------------------------------------------------

    // The object that does all the sales calculations.
    private final SalesAnalysisService service = new SalesAnalysisService();

    // Text field for "Number of Days".
    private JTextField daysField;

    // Array of text fields: one field for each day's sales amount.
    // The array is created after the user enters the number of days.
    private JTextField[] salesFields;

    // Panel which holds the Day 1, Day 2, ... input rows.
    private JPanel dayInputPanel;

    // Button to save the entered sales (enabled after the fields are created).
    private JButton saveButton;

    // Table to display "Day | Sales Amount (Rs)".
    private DefaultTableModel tableModel;
    private JTable salesTable;

    // Text area where results (total, average, etc.) are shown.
    private JTextArea resultArea;

    // Text field where the user types a day number to search.
    private JTextField searchField;

    // Fonts and colours used in the design.
    private final Font normalFont = new Font("SansSerif", Font.PLAIN, 14);
    private final Font boldFont = new Font("SansSerif", Font.BOLD, 14);
    private final Color headerColor = new Color(25, 60, 110);

    // ------------------------------------------------------------
    // Constructor: builds the window
    // ------------------------------------------------------------
    public RetailSalesGUI() {
        // Window title and basic settings.
        setTitle("Retail Sales Analysis System");
        setSize(1000, 650);
        setLocationRelativeTo(null);                    // open in the centre of the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the program when X is clicked

        // BorderLayout divides the window into NORTH, WEST, CENTER, SOUTH parts.
        setLayout(new BorderLayout(10, 10));

        // Add the four sections of the screen.
        add(createHeaderPanel(), BorderLayout.NORTH);      // 1. Header
        add(createInputPanel(), BorderLayout.WEST);        // 2. Sales Input
        add(createOutputPanel(), BorderLayout.CENTER);     // 4. Results / Output
        add(createButtonPanel(), BorderLayout.SOUTH);      // 3. Action Buttons

        // Show a short help message in the result area at the start.
        resultArea.setText("Welcome!\n\n"
                + "1. Enter the number of days and click 'Enter Sales'.\n"
                + "2. Type the sales amount (in " + RupeeFormatter.SYMBOL + ") for each day.\n"
                + "3. Click 'Save Sales', then use the buttons below to analyse the sales.");
    }

    // ------------------------------------------------------------
    // Section 1: Header
    // ------------------------------------------------------------
    private JPanel createHeaderPanel() {
        // JPanel is a container used to group other components together.
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(headerColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JLabel shows fixed text on the screen.
        JLabel title = new JLabel("Retail Sales Analysis System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Daily Sales Analysis  (all amounts in Indian Rupees "
                + RupeeFormatter.SYMBOL + ")", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        subtitle.setForeground(Color.WHITE);

        panel.add(title);
        panel.add(subtitle);
        return panel;
    }

    // ------------------------------------------------------------
    // Section 2: Sales Input (left side)
    // ------------------------------------------------------------
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(340, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 0),
                BorderFactory.createTitledBorder("Sales Input")));

        // Top part: two rows. Row 1 has the "Number of Days" label and text field,
        // row 2 has the Enter Sales button. (Two rows so everything fits the narrow panel.)
        JPanel topPart = new JPanel(new GridLayout(2, 1, 4, 4));

        JPanel daysRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        JLabel daysLabel = new JLabel("Number of Days:");
        daysLabel.setFont(boldFont);

        daysField = new JTextField(6);
        daysField.setFont(normalFont);
        daysField.setName("daysField");

        daysRow.add(daysLabel);
        daysRow.add(daysField);

        JButton enterButton = createButton("Enter Sales", "enterButton");
        // Event handling: when the button is clicked, enterSales() runs.
        enterButton.addActionListener(e -> enterSales());

        topPart.add(daysRow);
        topPart.add(enterButton);

        // Centre: panel for the Day 1, Day 2, ... fields inside a scroll pane,
        // so many days can still fit on the screen.
        dayInputPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        dayInputPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        JPanel holder = new JPanel(new BorderLayout());
        holder.add(dayInputPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(holder);

        // Bottom: Save Sales button (disabled until fields are created).
        saveButton = createButton("Save Sales", "saveButton");
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> saveSales());

        panel.add(topPart, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(saveButton, BorderLayout.SOUTH);
        return panel;
    }

    // ------------------------------------------------------------
    // Section 4: Results / Output (centre)
    // ------------------------------------------------------------
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // JTable shows the sales in rows and columns.
        String[] columnNames = {"Day", "Sales Amount (" + RupeeFormatter.SYMBOL + ")"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Make the table read-only (the user cannot edit the cells).
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salesTable = new JTable(tableModel);
        salesTable.setFont(normalFont);
        salesTable.setRowHeight(24);
        salesTable.getTableHeader().setFont(boldFont);

        // Centre the day column and right-align the amount column.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        salesTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        salesTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

        JScrollPane tableScroll = new JScrollPane(salesTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Daily Sales Table"));

        // JTextArea shows the text results (total, average, highest, ...).
        resultArea = new JTextArea(9, 30);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        resultArea.setEditable(false);
        resultArea.setMargin(new java.awt.Insets(8, 8, 8, 8));
        resultArea.setName("resultArea");

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(BorderFactory.createTitledBorder("Results"));

        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(resultScroll, BorderLayout.SOUTH);
        return panel;
    }

    // ------------------------------------------------------------
    // Section 3: Action Buttons (bottom)
    // ------------------------------------------------------------
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 10, 10),
                BorderFactory.createTitledBorder("Actions")));

        // Row 1: the analysis buttons.
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton displayButton = createButton("Display Sales", "displayButton");
        JButton totalButton = createButton("Total", "totalButton");
        JButton averageButton = createButton("Average", "averageButton");
        JButton highestButton = createButton("Highest", "highestButton");
        JButton lowestButton = createButton("Lowest", "lowestButton");

        // Event listeners: each button calls its own method when clicked.
        displayButton.addActionListener(e -> displaySales());
        totalButton.addActionListener(e -> calculateTotal());
        averageButton.addActionListener(e -> calculateAverage());
        highestButton.addActionListener(e -> findHighestSale());
        lowestButton.addActionListener(e -> findLowestSale());

        row1.add(displayButton);
        row1.add(totalButton);
        row1.add(averageButton);
        row1.add(highestButton);
        row1.add(lowestButton);

        // Row 2: search, clear and exit.
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JLabel searchLabel = new JLabel("Day Number:");
        searchLabel.setFont(boldFont);

        searchField = new JTextField(5);
        searchField.setFont(normalFont);
        searchField.setName("searchField");

        JButton searchButton = createButton("Search Day", "searchButton");
        JButton clearButton = createButton("Clear", "clearButton");
        JButton exitButton = createButton("Exit", "exitButton");

        searchButton.addActionListener(e -> searchSaleByDay());
        clearButton.addActionListener(e -> clearAll());
        exitButton.addActionListener(e -> exitApplication());

        row2.add(searchLabel);
        row2.add(searchField);
        row2.add(searchButton);
        row2.add(clearButton);
        row2.add(exitButton);

        panel.add(row1);
        panel.add(row2);
        return panel;
    }

    // Small helper method so that all buttons look the same.
    private JButton createButton(String text, String name) {
        JButton button = new JButton(text);
        button.setFont(boldFont);
        button.setFocusPainted(false);
        button.setName(name);   // name is useful for finding the button while testing
        return button;
    }

    // ------------------------------------------------------------
    // Module 1: Sales Entry
    // ------------------------------------------------------------

    // Called by the "Enter Sales" button. Creates one input field per day.
    private void enterSales() {
        try {
            // Read and validate the number of days.
            int days = InputValidator.parseNumberOfDays(daysField.getText());

            // Starting a new entry removes the old saved data.
            service.clearSales();
            tableModel.setRowCount(0);

            // Create an array of text fields, one for each day.
            salesFields = new JTextField[days];

            // Remove old fields (if any) from the panel.
            dayInputPanel.removeAll();

            // Loop to create "Day i Sales (Rs):" label and text field for every day.
            for (int i = 0; i < days; i++) {
                JLabel label = new JLabel("Day " + (i + 1) + " Sales (" + RupeeFormatter.SYMBOL + "):");
                label.setFont(normalFont);

                salesFields[i] = new JTextField(8);
                salesFields[i].setFont(normalFont);
                salesFields[i].setName("salesField" + (i + 1));

                dayInputPanel.add(label);
                dayInputPanel.add(salesFields[i]);
            }

            // Refresh the panel so that the new fields appear.
            dayInputPanel.revalidate();
            dayInputPanel.repaint();

            saveButton.setEnabled(true);
            salesFields[0].requestFocusInWindow();

            resultArea.setText("Please enter the sales amount for " + days
                    + " day(s) and click 'Save Sales'.");

        } catch (IllegalArgumentException ex) {
            // Exception handling: show the validation message to the user.
            showError(ex.getMessage());
        }
    }

    // Called by the "Save Sales" button. Reads all the fields and stores
    // the values in the service (which keeps them in a double[] array).
    private void saveSales() {
        // Make sure the user clicked "Enter Sales" first.
        if (salesFields == null) {
            showError("Please enter the number of days and click 'Enter Sales' first.");
            return;
        }

        // Create an array to store sales values for each day.
        double[] enteredSales = new double[salesFields.length];

        // Loop through every text field and convert the text to a number.
        for (int i = 0; i < salesFields.length; i++) {
            try {
                enteredSales[i] = InputValidator.parseSalesAmount(salesFields[i].getText());
            } catch (IllegalArgumentException ex) {
                // Exception handling: show which day has the problem and stop saving.
                showError("Day " + (i + 1) + ": " + ex.getMessage());
                salesFields[i].requestFocusInWindow();
                return;
            }
        }

        // All values are valid, so store them.
        service.setSales(enteredSales);

        // Show the saved values in the table and give a message.
        refreshTable();
        resultArea.setText("Sales saved successfully for " + service.getNumberOfDays() + " day(s).\n"
                + "Now click any analysis button below.");
    }

    // ------------------------------------------------------------
    // Display All Sales
    // ------------------------------------------------------------

    // Shows every day's sales in the table and in the result area.
    private void displaySales() {
        if (!checkDataAvailable()) {
            return;
        }

        refreshTable();

        // Get the sales array and show every day using a loop.
        double[] allSales = service.getSales();
        StringBuilder text = new StringBuilder("Daily Sales:\n\n");

        for (int i = 0; i < allSales.length; i++) {
            text.append("Day ").append(i + 1).append(" Sales: ")
                .append(RupeeFormatter.format(allSales[i])).append("\n");
        }

        resultArea.setText(text.toString());
        resultArea.setCaretPosition(0);
    }

    // Fills the table with the saved sales (one row for each day).
    private void refreshTable() {
        // Remove all old rows.
        tableModel.setRowCount(0);

        double[] allSales = service.getSales();

        // Loop through the array and add one row per day.
        for (int i = 0; i < allSales.length; i++) {
            tableModel.addRow(new Object[]{i + 1, RupeeFormatter.format(allSales[i])});
        }
    }

    // ------------------------------------------------------------
    // Module 2: Total Calculation
    // ------------------------------------------------------------
    private void calculateTotal() {
        if (!checkDataAvailable()) {
            return;
        }
        double total = service.calculateTotal();
        resultArea.setText("Total Sales:\n" + RupeeFormatter.format(total));
    }

    // ------------------------------------------------------------
    // Module 3: Average Calculation
    // ------------------------------------------------------------
    private void calculateAverage() {
        if (!checkDataAvailable()) {
            return;
        }
        double average = service.calculateAverage();
        resultArea.setText("Average Sales:\n" + RupeeFormatter.format(average));
    }

    // ------------------------------------------------------------
    // Module 4: Highest / Lowest Sales
    // ------------------------------------------------------------
    private void findHighestSale() {
        if (!checkDataAvailable()) {
            return;
        }
        double highest = service.findHighestSale();
        int day = service.findHighestSaleDay();
        resultArea.setText("Highest Sale:\n" + RupeeFormatter.format(highest) + "  (Day " + day + ")");

        // Select the row of that day in the table.
        selectTableRow(day);
    }

    private void findLowestSale() {
        if (!checkDataAvailable()) {
            return;
        }
        double lowest = service.findLowestSale();
        int day = service.findLowestSaleDay();
        resultArea.setText("Lowest Sale:\n" + RupeeFormatter.format(lowest) + "  (Day " + day + ")");

        selectTableRow(day);
    }

    // ------------------------------------------------------------
    // Module 5: Sales Search
    // ------------------------------------------------------------
    private void searchSaleByDay() {
        if (!checkDataAvailable()) {
            return;
        }

        try {
            // Validate the day number. It must be between 1 and the number of days.
            int day = InputValidator.parseDayNumber(searchField.getText(), service.getNumberOfDays());

            // Ask the service to search the sales array for this day.
            double sale = service.searchSaleByDay(day);

            resultArea.setText("Day " + day + " Sales: " + RupeeFormatter.format(sale));
            selectTableRow(day);

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // ------------------------------------------------------------
    // Clear / Reset and Exit
    // ------------------------------------------------------------

    // Resets the whole screen and the stored data.
    private void clearAll() {
        service.clearSales();
        salesFields = null;

        daysField.setText("");
        searchField.setText("");

        dayInputPanel.removeAll();
        dayInputPanel.revalidate();
        dayInputPanel.repaint();

        tableModel.setRowCount(0);
        saveButton.setEnabled(false);

        resultArea.setText("Everything has been cleared.\n\nEnter the number of days to start again.");
    }

    // Closes the application after asking the user to confirm.
    private void exitApplication() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION);

        // Comparison operator == checks which button the user pressed.
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ------------------------------------------------------------
    // Helper methods
    // ------------------------------------------------------------

    // Returns true if sales are saved. Otherwise shows a message and returns false.
    private boolean checkDataAvailable() {
        if (!service.hasData()) {
            showError("No sales data available. Please enter and save the sales first.");
            return false;
        }
        return true;
    }

    // Highlights the row of the given day number in the table.
    private void selectTableRow(int dayNumber) {
        // Day 1 is row 0, so we subtract 1.
        int row = dayNumber - 1;
        if (row >= 0 && row < salesTable.getRowCount()) {
            salesTable.setRowSelectionInterval(row, row);
            salesTable.scrollRectToVisible(salesTable.getCellRect(row, 0, true));
        }
    }

    // Shows an error message in a dialog box.
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

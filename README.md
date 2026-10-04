# Retail Sales Analysis System

**Case Study 185 - Java Programming (B.Tech CSE, Semester III)**
A Java Swing desktop application that analyses daily retail sales.
**Currency: Indian Rupee (₹)** - every amount is shown like `₹1,25,000.00`.

## Problem Statement
A retail company requires a system to analyze daily sales figures and determine
total, average, highest, and lowest sales.

## Objectives
1. Record daily sales.
2. Calculate total sales.
3. Calculate average sales.
4. Identify highest and lowest sales.
5. Search sales for a specific day.

## Features
| Button | What it does |
|---|---|
| Enter Sales | Creates one input box per day for the entered "Number of Days" |
| Save Sales | Validates all boxes and stores the values in an array |
| Display Sales | Shows all days in the table and in the results area |
| Total | Total sales of all days |
| Average | Total / number of days |
| Highest / Lowest | Highest and lowest sale, with the day number |
| Search Day | Finds the sale of a given day number |
| Clear | Resets the screen and the stored data |
| Exit | Closes the program (asks for confirmation) |

Input validation (shown with `JOptionPane`): number of days must be a positive
whole number (maximum 365), sales must be numeric and not negative, day number
must be inside the entered range, and empty input gives a clear message.

## Java Concepts Used
| Concept | Where |
|---|---|
| Arrays | `double[] sales` in `SalesAnalysisService`; `JTextField[] salesFields` in `RetailSalesGUI` |
| Loops | `for` loops in `calculateTotal()`, `findHighestSale()`, `findLowestSale()`, `searchSaleByDay()`, `enterSales()`, `saveSales()`, `refreshTable()`, `displaySales()` |
| Operators | `+=` and `/` (total, average), `>` and `<` (highest, lowest), `==`, `<`, `>`, `||`, `&&` in validation |
| Methods | `calculateTotal()`, `calculateAverage()`, `findHighestSale()`, `findLowestSale()`, `searchSaleByDay()`, `displaySales()` and one method per button |
| Searching | Linear search in `SalesAnalysisService.searchSaleByDay()` |
| Exception handling | `try/catch` for `NumberFormatException` in `InputValidator`; `IllegalArgumentException` is caught in `RetailSalesGUI` and shown in a dialog |
| OOP | Classes and objects, encapsulation (private array + public methods), inheritance (`RetailSalesGUI extends JFrame`), separation of GUI and logic |
| Swing | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTable`, `JTextArea`, `JScrollPane`, `JOptionPane`, `BorderLayout`, `GridLayout`, `FlowLayout`, `ActionListener` |

### Scanner vs Swing input
The case study mentions `Scanner`. `Scanner` reads typed text from the **console**.
In a GUI program the same job is done by a `JTextField`: the user types in the box,
and the text is read with `getText()` when a button is clicked. So `JTextField`
replaces `Scanner`, and `JTable` / `JTextArea` replace `System.out.println()`.

## Project Structure
```
retail-sales-analysis-system/
├── src/com/retail/sales/
│   ├── Main.java                         (starts the application)
│   ├── gui/RetailSalesGUI.java           (Swing window, buttons, events)
│   ├── service/SalesAnalysisService.java (total, average, highest, lowest, search)
│   └── util/
│       ├── InputValidator.java           (checks user input)
│       └── RupeeFormatter.java           (₹ Indian number format)
├── README.md
├── build.sh / run.sh                     (Linux / macOS / Git Bash)
└── build.bat / run.bat                   (Windows)
```

## How to Compile
Requires JDK 8 or newer (`javac -version` to check).

Linux / macOS: `./build.sh`  
Windows: `build.bat`

Manual command:
```
javac -encoding UTF-8 -d out src/com/retail/sales/Main.java src/com/retail/sales/gui/RetailSalesGUI.java src/com/retail/sales/service/SalesAnalysisService.java src/com/retail/sales/util/InputValidator.java src/com/retail/sales/util/RupeeFormatter.java
```

## How to Run
Linux / macOS: `./run.sh`  
Windows: `run.bat`

Manual command: `java -Dfile.encoding=UTF-8 -cp out com.retail.sales.Main`

## Sample Usage
1. Type `5` in **Number of Days** and click **Enter Sales**.
2. Enter: Day 1 = 12500, Day 2 = 25000, Day 3 = 18750, Day 4 = 32000, Day 5 = 15500.
3. Click **Save Sales**, then click the analysis buttons.
4. Type `3` in **Day Number** and click **Search Day**.

## Expected Output
| Action | Result |
|---|---|
| Table | Day 1 ₹12,500.00, Day 2 ₹25,000.00, Day 3 ₹18,750.00, Day 4 ₹32,000.00, Day 5 ₹15,500.00 |
| Total | ₹1,03,750.00 |
| Average | ₹20,750.00 |
| Highest | ₹32,000.00 (Day 4) |
| Lowest | ₹12,500.00 (Day 1) |
| Search Day 3 | Day 3 Sales: ₹18,750.00 |
| Search Day 6 | Error: Day number must be between 1 and 5. |

Manual check: 12,500 + 25,000 + 18,750 + 32,000 + 15,500 = 1,03,750; 1,03,750 / 5 = 20,750.

## Viva Quick Guide
| Question | Answer / location |
|---|---|
| Use of Main.java? | Entry point; starts the GUI using `SwingUtilities.invokeLater()` |
| Use of RetailSalesGUI.java? | The window, input, button events and output display |
| Why Swing? | Standard Java library for desktop GUIs; no extra libraries needed |
| What is JFrame / JPanel? | JFrame = main window; JPanel = container that groups components |
| What is an event listener? | Code that runs when something happens; `button.addActionListener(e -> ...)` |
| Why separate GUI and logic? | Logic can be understood and tested without the screen; code stays clean |
| Purpose of InputValidator? | Checks input and throws clear error messages |
| Purpose of RupeeFormatter? | Shows amounts in ₹ with Indian comma style (1,25,000.00) |

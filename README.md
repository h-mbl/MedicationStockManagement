# MedicationStockManagement
This Java program, named "MedicationStockManagement," allows for the management of medication inventory in a pharmacy. It handles various transactions such as receiving medication deliveries, processing client prescriptions, displaying pharmacy inventory, setting the current date, and generating orders for missing medications.

# Usage
The program can be executed via the command line using the following syntax:
``` java MedicationStockManagement filename1.txt filename2.txt ```
filename1.txt: Input file containing the transactions to be processed.
filename2.txt: Output file where the results will be written.
Input Format
The input stream consists of a sequence of transactions, where each transaction starts with an uppercase keyword indicating its type. Transactions should be processed in the order of their appearance in the input file. Each transaction should be followed by a white space (space, tab, or newline).

Here are the supported transaction types:

Delivery: Receipt of medication delivery. Example: Delivery MedicationName Quantity ExpirationDate.
Prescription: Request for a list of medications for a client. Example: Prescription MedicationName Quantity Duration Renewals.
DisplayStock: Display the pharmacy's inventory.
SetDate: Set the current date.
GenerateOrder: Generate an order for missing medications during a date change.
Note that the medication name does not contain any white spaces.

Output Format
The program generates results in the specified format, which are written to the output file filename2.txt.

# Program Structure
The program utilizes a binary search tree to store medications and efficiently handle search and insertion operations. Each node in the tree contains information about a medication, such as the name, quantity in stock, and expiration date.

The processing of transactions occurs in the order they appear, by traversing the input stream. Each transaction is immediately processed to display the corresponding results before moving on to the next transaction.

The program takes into account expired medications and removes them from the inventory when processed.

# Dependencies
This program was developed in Java and requires no external dependencies.

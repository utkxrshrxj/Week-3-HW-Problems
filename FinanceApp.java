package week3.assignment;

public class FinanceApp {
    // Personal Account Class
    static class PersonalAccount {
        private String accountHolderName;
        private String accountNumber;
        private double currentBalance;
        private double totalIncome;
        private double totalExpenses;

        // Static variables (shared across accounts)
        private static int totalAccounts = 0;
        private static String bankName = "Default Bank";

        // Constructor
        public PersonalAccount(String name) {
            this.accountHolderName = name;
            this.accountNumber = generateAccountNumber();
            this.currentBalance = 0;
            this.totalIncome = 0;
            this.totalExpenses = 0;
        }

        // Generate unique account number
        private static String generateAccountNumber() {
            return "AC" + (++totalAccounts);
        }

        // Static methods
        public static void setBankName(String name) { bankName = name; }
        public static int getTotalAccounts() { return totalAccounts; }
        public static String getBankName() { return bankName; }

        // Instance methods
        public void addIncome(double amount, String description) {
            currentBalance += amount;
            totalIncome += amount;
            System.out.println(description + " income added: " + amount);
        }

        public void addExpense(double amount, String description) {
            if (amount <= currentBalance) {
                currentBalance -= amount;
                totalExpenses += amount;
                System.out.println(description + " expense deducted: " + amount);
            } else {
                System.out.println("❌ Insufficient balance for: " + description);
            }
        }

        public double calculateSavings() {
            return totalIncome - totalExpenses;
        }

        public void displayAccountSummary() {
            System.out.println("\nBank: " + bankName);
            System.out.println("Account Holder: " + accountHolderName);
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Balance: " + currentBalance);
            System.out.println("Total Income: " + totalIncome);
            System.out.println("Total Expenses: " + totalExpenses);
            System.out.println("Savings: " + calculateSavings());
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        PersonalAccount.setBankName("Smart Bank");

        PersonalAccount acc1 = new PersonalAccount("Alice");
        PersonalAccount acc2 = new PersonalAccount("Bob");

        acc1.addIncome(1000, "Salary");
        acc1.addExpense(200, "Shopping");
        acc1.displayAccountSummary();

        acc2.addIncome(500, "Freelance Work");
        acc2.addExpense(100, "Snacks");
        acc2.displayAccountSummary();

        System.out.println("\nTotal Accounts Created: " + PersonalAccount.getTotalAccounts());
    }
}

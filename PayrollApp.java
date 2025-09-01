package week3.assignment;

public class PayrollApp {
    // Employee Class
    static class Employee {
        private String empId;
        private String empName;
        private String department;
        private String designation;
        private double baseSalary;
        private boolean[] attendanceRecord; // 30 days
        private double totalSalary;

        // Static variables
        private static int totalEmployees = 0;
        private static String companyName = "Default Company";
        private static int workingDaysPerMonth = 30;
        private static double totalSalaryExpense = 0;

        public Employee(String empId, String empName, String dept, String designation, double baseSalary) {
            this.empId = empId;
            this.empName = empName;
            this.department = dept;
            this.designation = designation;
            this.baseSalary = baseSalary;
            this.attendanceRecord = new boolean[workingDaysPerMonth];
            this.totalSalary = 0;
            totalEmployees++;
        }

        // Mark attendance
        public void markAttendance(int day, boolean present) {
            if (day > 0 && day <= workingDaysPerMonth) {
                attendanceRecord[day - 1] = present;
            }
        }

        // Calculate salary based on attendance
        public void calculateSalary() {
            int presentDays = 0;
            for (boolean d : attendanceRecord) {
                if (d) presentDays++;
            }
            totalSalary = (baseSalary / workingDaysPerMonth) * presentDays;
            totalSalaryExpense += totalSalary;
        }

        // Calculate bonus (simple rule: 10% if present > 25 days)
        public double calculateBonus() {
            int presentDays = 0;
            for (boolean d : attendanceRecord) {
                if (d) presentDays++;
            }
            return (presentDays > 25) ? (0.1 * baseSalary) : 0;
        }

        // Generate Pay Slip
        public void generatePaySlip() {
            calculateSalary();
            double bonus = calculateBonus();
            System.out.println("\n💼 Pay Slip for " + empName);
            System.out.println("Department: " + department);
            System.out.println("Designation: " + designation);
            System.out.println("Base Salary: " + baseSalary);
            System.out.println("Net Salary (with attendance): " + totalSalary);
            System.out.println("Bonus: " + bonus);
            System.out.println("Total Payout: " + (totalSalary + bonus));
        }

        // Static Reports
        public static void calculateCompanyPayroll() {
            System.out.println("\n🏢 Company: " + companyName);
            System.out.println("Total Employees: " + totalEmployees);
            System.out.println("Total Salary Expense: " + totalSalaryExpense);
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        Employee e1 = new Employee("E1", "Alice", "HR", "Manager", 60000);
        Employee e2 = new Employee("E2", "Bob", "IT", "Developer", 50000);

        // Mark attendance
        for (int i = 1; i <= 28; i++) e1.markAttendance(i, true);  // Alice present 28 days
        for (int i = 1; i <= 20; i++) e2.markAttendance(i, true);  // Bob present 20 days

        // Generate Pay Slips
        e1.generatePaySlip();
        e2.generatePaySlip();

        // Company Payroll Report
        Employee.calculateCompanyPayroll();
    }
}

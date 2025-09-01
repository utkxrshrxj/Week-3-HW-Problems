package week3.assignment;

public class StudentGradeApp {
    // Subject Class
    static class Subject {
        private String subjectCode;
        private String subjectName;
        private int credits;
        private String instructor;

        public Subject(String code, String name, int credits, String instructor) {
            this.subjectCode = code;
            this.subjectName = name;
            this.credits = credits;
            this.instructor = instructor;
        }

        public String getSubjectCode() { return subjectCode; }
        public String getSubjectName() { return subjectName; }
        public int getCredits() { return credits; }
    }

    // Student Class
    static class Student {
        private String studentId;
        private String studentName;
        private String className;
        private Subject[] subjects;
        private double[] marks; // one mark per subject
        private double gpa;

        // Static members
        private static int totalStudents = 0;
        private static String schoolName = "Default School";
        private static String[] gradingScale = {"A", "B", "C", "D", "F"};
        private static double passPercentage = 40.0;

        public Student(String id, String name, String className, Subject[] subjects) {
            this.studentId = id;
            this.studentName = name;
            this.className = className;
            this.subjects = subjects;
            this.marks = new double[subjects.length];
            this.gpa = 0.0;
            totalStudents++;
        }

        public void addMarks(String subjectCode, double mark) {
            for (int i = 0; i < subjects.length; i++) {
                if (subjects[i].getSubjectCode().equals(subjectCode)) {
                    marks[i] = mark;
                    break;
                }
            }
        }

        public void calculateGPA() {
            double totalMarks = 0;
            for (double m : marks) totalMarks += m;
            double percentage = totalMarks / subjects.length;
            this.gpa = percentage / 20.0; // GPA out of 5
        }

        public boolean checkPromotionEligibility() {
            for (double m : marks) {
                if (m < passPercentage) return false;
            }
            return true;
        }

        public void generateReportCard() {
            System.out.println("\n📘 Report Card for " + studentName + " (" + className + ")");
            for (int i = 0; i < subjects.length; i++) {
                System.out.println(subjects[i].getSubjectName() + ": " + marks[i]);
            }
            calculateGPA();
            System.out.println("GPA: " + gpa);
            System.out.println("Promotion: " + (checkPromotionEligibility() ? "✅ Yes" : "❌ No"));
        }

        // Static utility methods
        public static void setGradingScale(String[] scale) { gradingScale = scale; }

        public static double calculateClassAverage(Student[] students) {
            double total = 0; int count = 0;
            for (Student s : students) {
                for (double m : s.marks) {
                    total += m;
                    count++;
                }
            }
            return (count == 0) ? 0 : total / count;
        }

        public static void getTopPerformers(Student[] students, int count) {
            System.out.println("\n🏆 Top " + count + " Performers:");
            for (int i = 0; i < students.length; i++) students[i].calculateGPA();

            // simple bubble sort by GPA
            for (int i = 0; i < students.length - 1; i++) {
                for (int j = 0; j < students.length - i - 1; j++) {
                    if (students[j].gpa < students[j+1].gpa) {
                        Student temp = students[j];
                        students[j] = students[j+1];
                        students[j+1] = temp;
                    }
                }
            }

            for (int i = 0; i < count && i < students.length; i++) {
                System.out.println(students[i].studentName + " - GPA: " + students[i].gpa);
            }
        }

        public static void generateSchoolReport(Student[] students) {
            System.out.println("\n📊 School Report for " + schoolName);
            System.out.println("Total Students: " + totalStudents);
            System.out.println("Class Average: " + calculateClassAverage(students));
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        Subject math = new Subject("S1", "Mathematics", 4, "Dr. John");
        Subject physics = new Subject("S2", "Physics", 3, "Dr. Smith");
        Subject chemistry = new Subject("S3", "Chemistry", 3, "Dr. Lee");
        Subject[] subjects = { math, physics, chemistry };

        Student s1 = new Student("ST1", "Alice", "10A", subjects);
        Student s2 = new Student("ST2", "Bob", "10A", subjects);
        Student s3 = new Student("ST3", "Charlie", "10A", subjects);

        // Add Marks
        s1.addMarks("S1", 90); s1.addMarks("S2", 85); s1.addMarks("S3", 80);
        s2.addMarks("S1", 70); s2.addMarks("S2", 60); s2.addMarks("S3", 65);
        s3.addMarks("S1", 40); s3.addMarks("S2", 35); s3.addMarks("S3", 50);

        // Generate Report Cards
        s1.generateReportCard();
        s2.generateReportCard();
        s3.generateReportCard();

        // Top Performers
        Student[] students = { s1, s2, s3 };
        Student.getTopPerformers(students, 2);

        // School Report
        Student.generateSchoolReport(students);
    }
}

package week3.assignment;

public class LibraryApp {
    // Book Class
    static class Book {
        private String bookId;
        private String title;
        private String author;
        private String isbn;
        private String category;
        private boolean isIssued;

        public Book(String id, String title, String author, String isbn, String category) {
            this.bookId = id;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.category = category;
            this.isIssued = false;
        }

        public String getBookId() { return bookId; }
        public String getTitle() { return title; }
        public boolean isIssued() { return isIssued; }
        public void setIssued(boolean issued) { isIssued = issued; }
    }

    // Member Class
    static class Member {
        private String memberId;
        private String memberName;
        private String memberType; // Student, Faculty, General
        private Book[] booksIssued;
        private int issuedCount;
        private double totalFines;

        // Static variables
        private static int totalMembers = 0;
        private static String libraryName = "Default Library";
        private static double finePerDay = 2.0;
        private static int maxBooksAllowed = 3;

        public Member(String id, String name, String type) {
            this.memberId = id;
            this.memberName = name;
            this.memberType = type;
            this.booksIssued = new Book[maxBooksAllowed];
            this.issuedCount = 0;
            this.totalFines = 0;
            totalMembers++;
        }

        public String getMemberName() { return memberName; }
        public double getTotalFines() { return totalFines; }

        // Issue Book
        public void issueBook(Book book) {
            if (issuedCount < maxBooksAllowed && !book.isIssued()) {
                booksIssued[issuedCount++] = book;
                book.setIssued(true);
                System.out.println(memberName + " issued: " + book.getTitle());
            } else {
                System.out.println("❌ Cannot issue book to " + memberName);
            }
        }

        // Return Book
        public void returnBook(Book book, int overdueDays) {
            for (int i = 0; i < issuedCount; i++) {
                if (booksIssued[i] != null && booksIssued[i].getBookId().equals(book.getBookId())) {
                    booksIssued[i].setIssued(false);
                    System.out.println(memberName + " returned: " + book.getTitle());

                    if (overdueDays > 0) {
                        double fine = overdueDays * finePerDay;
                        totalFines += fine;
                        System.out.println("⚠ Fine for " + memberName + ": " + fine);
                    }

                    booksIssued[i] = null;
                    break;
                }
            }
        }

        // Renew Book
        public void renewBook(Book book) {
            System.out.println(memberName + " renewed book: " + book.getTitle());
        }

        // Show Member Info
        public void displayMemberInfo() {
            System.out.println("\nMember: " + memberName + " (" + memberType + ")");
            System.out.println("Total Fines: " + totalFines);
            System.out.print("Books Issued: ");
            for (Book b : booksIssued) {
                if (b != null) System.out.print(b.getTitle() + " | ");
            }
            System.out.println();
        }

        // Static Reports
        public static void generateLibraryReport(Member[] members) {
            System.out.println("\n📚 Library Report for " + libraryName);
            System.out.println("Total Members: " + totalMembers);
            double totalFines = 0;
            for (Member m : members) totalFines += m.getTotalFines();
            System.out.println("Total Fines Collected: " + totalFines);
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        Book b1 = new Book("B1", "Java Programming", "James Gosling", "12345", "Programming");
        Book b2 = new Book("B2", "Python Basics", "Guido van Rossum", "67890", "Programming");
        Book b3 = new Book("B3", "Data Structures", "Cormen", "11223", "CS");

        Member m1 = new Member("M1", "Alice", "Student");
        Member m2 = new Member("M2", "Bob", "Faculty");

        // Issue Books
        m1.issueBook(b1);
        m1.issueBook(b2);
        m2.issueBook(b3);

        // Return with overdue fine
        m1.returnBook(b1, 3);
        m2.returnBook(b3, 0);

        // Renew
        m1.renewBook(b2);

        // Show Member Info
        m1.displayMemberInfo();
        m2.displayMemberInfo();

        // Library Report
        Member[] members = {m1, m2};
        Member.generateLibraryReport(members);
    }
}

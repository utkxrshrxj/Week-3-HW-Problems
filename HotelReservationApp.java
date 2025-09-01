package week3.assignment;

public class HotelReservationApp {
    // Room Class
    static class Room {
        private String roomNumber;
        private String roomType;
        private double pricePerNight;
        private boolean isAvailable;
        private int maxOccupancy;

        public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.pricePerNight = pricePerNight;
            this.maxOccupancy = maxOccupancy;
            this.isAvailable = true; // default
        }

        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public double getPricePerNight() { return pricePerNight; }
        public boolean isAvailable() { return isAvailable; }
        public void setAvailable(boolean available) { isAvailable = available; }
    }

    // Guest Class
    static class Guest {
        private String guestId;
        private String guestName;
        private String phoneNumber;
        private String email;

        public Guest(String guestId, String guestName, String phoneNumber, String email) {
            this.guestId = guestId;
            this.guestName = guestName;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getGuestName() { return guestName; }
    }

    // Booking Class
    static class Booking {
        private String bookingId;
        private Guest guest;
        private Room room;
        private String checkInDate;
        private String checkOutDate;
        private double totalAmount;

        // Static variables
        private static int totalBookings = 0;
        private static double hotelRevenue = 0;
        private static String hotelName = "Default Hotel";

        public Booking(String bookingId, Guest guest, Room room,
                       String checkInDate, String checkOutDate, int nights) {
            this.bookingId = bookingId;
            this.guest = guest;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.totalAmount = nights * room.getPricePerNight();
            room.setAvailable(false); // mark room booked
            totalBookings++;
            hotelRevenue += totalAmount;
        }

        public static int getTotalBookings() { return totalBookings; }
        public static double getHotelRevenue() { return hotelRevenue; }
        public static void setHotelName(String name) { hotelName = name; }
        public static String getHotelName() { return hotelName; }

        public void cancelReservation() {
            room.setAvailable(true);
            System.out.println("Booking " + bookingId + " cancelled for " + guest.getGuestName());
        }

        public void displayBookingDetails() {
            System.out.println("\nBooking ID: " + bookingId);
            System.out.println("Hotel: " + hotelName);
            System.out.println("Guest: " + guest.getGuestName());
            System.out.println("Room: " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
            System.out.println("Check-In: " + checkInDate);
            System.out.println("Check-Out: " + checkOutDate);
            System.out.println("Total Amount: " + totalAmount);
        }
    }

    // Main Method (Testing)
    public static void main(String[] args) {
        Booking.setHotelName("Grand Palace Hotel");

        // Create Rooms
        Room r1 = new Room("101", "Deluxe", 3000, 2);
        Room r2 = new Room("102", "Suite", 5000, 4);

        // Create Guests
        Guest g1 = new Guest("G1", "Alice", "9876543210", "alice@mail.com");
        Guest g2 = new Guest("G2", "Bob", "9876549999", "bob@mail.com");

        // Make Bookings
        Booking b1 = new Booking("B1", g1, r1, "01-09-2025", "03-09-2025", 2);
        Booking b2 = new Booking("B2", g2, r2, "02-09-2025", "04-09-2025", 2);

        b1.displayBookingDetails();
        b2.displayBookingDetails();

        // Cancel a booking
        b1.cancelReservation();

        // Show hotel statistics
        System.out.println("\nTotal Bookings: " + Booking.getTotalBookings());
        System.out.println("Total Revenue: " + Booking.getHotelRevenue());
    }
}

package week3.assignment;

public class FleetApp {
    // ===== Base Vehicle =====
    static abstract class Vehicle {
        private String vehicleId, brand, model, fuelType, currentStatus; // "Available", "On Trip", "Maintenance"
        private int year;
        private double mileage;              // in km
        private double nextServiceAt;        // km threshold
        private double maintenanceCost;      // total spent
        private double purchaseValue;        // for fleetValue calc

        // Shared stats
        private static int totalVehicles = 0;
        private static String companyName = "QuickMove Logistics";
        private static double fleetValue = 0.0;
        private static double totalFuelConsumption = 0.0; // in liters

        protected Vehicle(String id, String brand, String model, int year, double mileage,
                          String fuelType, double purchaseValue) {
            this.vehicleId = id;
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.mileage = mileage;
            this.fuelType = fuelType;
            this.currentStatus = "Available";
            this.nextServiceAt = mileage + 10000; // simple rule: service every 10k km
            this.purchaseValue = purchaseValue;
            totalVehicles++;
            fleetValue += purchaseValue;
        }

        // ----- Core Ops -----
        public void assignDriver(Driver d) {
            d.setAssignedVehicle(this);
            System.out.println("Driver " + d.getDriverName() + " assigned to " + vehicleId);
        }

        public void scheduleMaintenance(double estimateCost) {
            currentStatus = "Maintenance";
            maintenanceCost += estimateCost;
            nextServiceAt = mileage + 10000; // reset next service
            System.out.println(vehicleId + " scheduled for maintenance. Cost +" + estimateCost);
        }

        // Basic running-cost model
        // kmPerLiter = efficiency, fuelPrice per liter, otherCostPerKm covers tolls etc.
        public double calculateRunningCost(double km, double fuelPrice, double kmPerLiter, double otherCostPerKm) {
            double fuelUsed = (kmPerLiter <= 0) ? 0 : km / kmPerLiter;
            totalFuelConsumption += fuelUsed;
            return fuelUsed * fuelPrice + (otherCostPerKm * km);
        }

        public void updateMileage(double km) {
            mileage += km;
            if ("On Trip".equals(currentStatus)) currentStatus = "Available";
        }

        public boolean checkServiceDue() {
            return mileage >= nextServiceAt;
        }

        public void setStatus(String status) { this.currentStatus = status; }

        // ----- Getters -----
        public String getVehicleId() { return vehicleId; }
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public int getYear() { return year; }
        public double getMileage() { return mileage; }
        public String getFuelType() { return fuelType; }
        public String getCurrentStatus() { return currentStatus; }
        public double getMaintenanceCost() { return maintenanceCost; }

        // ----- Static Reports -----
        public static double getFleetUtilization(Vehicle[] fleet) {
            // % of vehicles NOT "Available" (either "On Trip" or "Maintenance")
            if (fleet == null || fleet.length == 0) return 0;
            int busy = 0;
            for (Vehicle v : fleet) {
                if (!"Available".equals(v.getCurrentStatus())) busy++;
            }
            return (busy * 100.0) / fleet.length;
        }

        public static double calculateTotalMaintenanceCost(Vehicle[] fleet) {
            double sum = 0;
            for (Vehicle v : fleet) sum += v.getMaintenanceCost();
            return sum;
        }

        public static void getVehiclesByType(Vehicle[] fleet, String typeName) {
            System.out.println("\nVehicles of type: " + typeName);
            for (Vehicle v : fleet) {
                if (v.getClass().getSimpleName().equalsIgnoreCase(typeName)) {
                    System.out.println("- " + v.getVehicleId() + " " + v.getBrand() + " " + v.getModel()
                            + " (" + v.getCurrentStatus() + ", " + v.getMileage() + " km)");
                }
            }
        }

        public static void printFleetSummary() {
            System.out.println("\n=== " + companyName + " | Fleet Summary ===");
            System.out.println("Total Vehicles: " + totalVehicles);
            System.out.println("Fleet Value: " + fleetValue);
            System.out.println("Total Fuel Consumed: " + totalFuelConsumption + " L");
        }
    }

    // ===== Subclasses =====
    static class Car extends Vehicle {
        private int numDoors;
        public Car(String id, String brand, String model, int year, double mileage,
                   String fuelType, double purchaseValue, int numDoors) {
            super(id, brand, model, year, mileage, fuelType, purchaseValue);
            this.numDoors = numDoors;
        }
    }

    static class Bus extends Vehicle {
        private int seatingCapacity;
        public Bus(String id, String brand, String model, int year, double mileage,
                   String fuelType, double purchaseValue, int seatingCapacity) {
            super(id, brand, model, year, mileage, fuelType, purchaseValue);
            this.seatingCapacity = seatingCapacity;
        }
    }

    static class Truck extends Vehicle {
        private double loadCapacity; // in tons
        public Truck(String id, String brand, String model, int year, double mileage,
                     String fuelType, double purchaseValue, double loadCapacity) {
            super(id, brand, model, year, mileage, fuelType, purchaseValue);
            this.loadCapacity = loadCapacity;
        }
    }

    // ===== Driver =====
    static class Driver {
        private String driverId, driverName, licenseType; // LMV/Heavy etc.
        private Vehicle assignedVehicle;
        private int totalTrips;

        public Driver(String id, String name, String licenseType) {
            this.driverId = id;
            this.driverName = name;
            this.licenseType = licenseType;
            this.totalTrips = 0;
        }

        public String getDriverName() { return driverName; }
        public void setAssignedVehicle(Vehicle v) { assignedVehicle = v; }
        public Vehicle getAssignedVehicle() { return assignedVehicle; }
        public void completeTrip() { totalTrips++; }
        public int getTotalTrips() { return totalTrips; }
    }

    // ===== Demo / Testing =====
    public static void main(String[] args) {
        // Create vehicles
        Vehicle v1 = new Car("C-101", "Toyota", "Etios", 2019, 42000, "Petrol", 500000, 4);
        Vehicle v2 = new Bus("B-210", "Tata", "Starbus", 2021, 88000, "Diesel", 2500000, 40);
        Vehicle v3 = new Truck("T-309", "AshokLeyland", "Boss", 2020, 120000, "Diesel", 1800000, 14);

        Vehicle[] fleet = { v1, v2, v3 };

        // Create drivers
        Driver d1 = new Driver("D1", "Alice", "LMV");
        Driver d2 = new Driver("D2", "Bob", "Heavy");

        // Assign drivers
        v1.assignDriver(d1);
        v3.assignDriver(d2);

        // Trip simulation
        v1.setStatus("On Trip");
        double cost1 = v1.calculateRunningCost(150, 105, 18, 2); // 150 km
        v1.updateMileage(150);
        d1.completeTrip();
        System.out.println("Trip cost (Car): " + cost1);

        v3.setStatus("On Trip");
        double cost2 = v3.calculateRunningCost(320, 95, 5, 3.5); // 320 km heavy truck
        v3.updateMileage(320);
        d2.completeTrip();
        System.out.println("Trip cost (Truck): " + cost2);

        // Maintenance
        if (v3.checkServiceDue()) v3.scheduleMaintenance(12000);
        if (v2.checkServiceDue()) v2.scheduleMaintenance(8000);

        // Reports
        System.out.println("\nUtilization: " + Vehicle.getFleetUtilization(fleet) + "%");
        System.out.println("Total Maintenance Cost: " + Vehicle.calculateTotalMaintenanceCost(fleet));

        Vehicle.getVehiclesByType(fleet, "Truck");
        Vehicle.getVehiclesByType(fleet, "Bus");

        Vehicle.printFleetSummary();

        // Quick driver stats
        System.out.println("\nDriver Trips:");
        System.out.println("Alice: " + d1.getTotalTrips());
        System.out.println("Bob: " + d2.getTotalTrips());
    }
}

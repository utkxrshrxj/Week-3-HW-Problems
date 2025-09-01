package week3.assignment;

public class HospitalApp {
    // ===== Patient =====
    static class Patient {
        private String patientId;
        private String patientName;
        private int age;
        private String gender;
        private String contactInfo;
        private String[] medicalHistory;
        private String[] currentTreatments;

        public Patient(String id, String name, int age, String gender, String contact, String[] history) {
            this.patientId = id;
            this.patientName = name;
            this.age = age;
            this.gender = gender;
            this.contactInfo = contact;
            this.medicalHistory = history;
            this.currentTreatments = new String[5]; // max 5 ongoing treatments
        }

        public String getPatientName() { return patientName; }

        public void updateTreatment(String treatment) {
            for (int i = 0; i < currentTreatments.length; i++) {
                if (currentTreatments[i] == null) {
                    currentTreatments[i] = treatment;
                    System.out.println(patientName + " treatment updated: " + treatment);
                    break;
                }
            }
        }

        public void discharge() {
            System.out.println(patientName + " discharged.");
            currentTreatments = new String[5];
        }
    }

    // ===== Doctor =====
    static class Doctor {
        private String doctorId;
        private String doctorName;
        private String specialization;
        private String[] availableSlots;
        private int patientsHandled;
        private double consultationFee;

        public Doctor(String id, String name, String specialization, String[] slots, double fee) {
            this.doctorId = id;
            this.doctorName = name;
            this.specialization = specialization;
            this.availableSlots = slots;
            this.consultationFee = fee;
            this.patientsHandled = 0;
        }

        public String getDoctorName() { return doctorName; }
        public double getConsultationFee() { return consultationFee; }

        public void incrementPatientsHandled() { patientsHandled++; }
        public int getPatientsHandled() { return patientsHandled; }
    }

    // ===== Appointment =====
    static class Appointment {
        private String appointmentId;
        private Patient patient;
        private Doctor doctor;
        private String appointmentDate;
        private String appointmentTime;
        private String status;
        private double billAmount;

        // Static members
        private static int totalAppointments = 0;
        private static int totalPatients = 0;
        private static String hospitalName = "City Hospital";
        private static double totalRevenue = 0;

        public Appointment(String id, Patient patient, Doctor doctor,
                           String date, String time, String type) {
            this.appointmentId = id;
            this.patient = patient;
            this.doctor = doctor;
            this.appointmentDate = date;
            this.appointmentTime = time;
            this.status = "Scheduled";

            // Billing: different appointment types
            if (type.equalsIgnoreCase("Emergency")) {
                billAmount = doctor.getConsultationFee() * 2;
            } else if (type.equalsIgnoreCase("Follow-up")) {
                billAmount = doctor.getConsultationFee() * 0.5;
            } else {
                billAmount = doctor.getConsultationFee();
            }

            totalAppointments++;
            totalPatients++;
            totalRevenue += billAmount;
            doctor.incrementPatientsHandled();
        }

        public void cancelAppointment() {
            this.status = "Cancelled";
            System.out.println("❌ Appointment " + appointmentId + " cancelled.");
        }

        public void generateBill() {
            System.out.println("\n🧾 Bill for Appointment " + appointmentId);
            System.out.println("Hospital: " + hospitalName);
            System.out.println("Patient: " + patient.getPatientName());
            System.out.println("Doctor: " + doctor.getDoctorName());
            System.out.println("Amount: " + billAmount);
        }

        // Static reports
        public static void generateHospitalReport() {
            System.out.println("\n🏥 Hospital Report - " + hospitalName);
            System.out.println("Total Patients: " + totalPatients);
            System.out.println("Total Appointments: " + totalAppointments);
            System.out.println("Total Revenue: " + totalRevenue);
        }

        public static void getDoctorUtilization(Doctor[] doctors) {
            System.out.println("\n👨‍⚕️ Doctor Utilization Report:");
            for (Doctor d : doctors) {
                System.out.println(d.getDoctorName() + " handled " + d.getPatientsHandled() + " patients.");
            }
        }

        public static void getPatientStatistics() {
            System.out.println("\n📊 Patient Statistics:");
            System.out.println("Total Registered Patients: " + totalPatients);
        }
    }

    // ===== Main (Testing) =====
    public static void main(String[] args) {
        // Doctors
        Doctor d1 = new Doctor("D1", "Dr. Smith", "Cardiology",
                new String[]{"10AM", "2PM"}, 500);
        Doctor d2 = new Doctor("D2", "Dr. Alice", "Dermatology",
                new String[]{"11AM", "3PM"}, 300);

        // Patients
        Patient p1 = new Patient("P1", "John", 45, "Male",
                "9999999999", new String[]{"Hypertension"});
        Patient p2 = new Patient("P2", "Mary", 30, "Female",
                "8888888888", new String[]{"Allergy"});

        // Appointments
        Appointment a1 = new Appointment("A1", p1, d1,
                "01-09-2025", "10AM", "Consultation");
        Appointment a2 = new Appointment("A2", p2, d2,
                "01-09-2025", "11AM", "Emergency");

        // Treatments
        p1.updateTreatment("Blood Pressure Monitoring");
        p2.updateTreatment("Allergy Medication");

        // Bills
        a1.generateBill();
        a2.generateBill();

        // Cancel appointment
        a1.cancelAppointment();

        // Reports
        Appointment.generateHospitalReport();
        Appointment.getDoctorUtilization(new Doctor[]{d1, d2});
        Appointment.getPatientStatistics();

        // Discharge
        p1.discharge();
    }
}

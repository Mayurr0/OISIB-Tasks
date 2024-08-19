import java.util.*;

class ReservationSystem {
    private static Map<String, String> users = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();

    static {
        // Adding some default users
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Reservation System");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(loginId, password)) {
            System.out.println("Login successful!");

            while (true) {
                System.out.println("1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. View My Reservations");
                System.out.println("4. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        makeReservation(scanner, loginId);
                        break;
                    case 2:
                        cancelReservation(scanner);
                        break;
                    case 3:
                        viewReservations(loginId);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login ID or password.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter new login ID: ");
        String newLoginId = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        if (users.containsKey(newLoginId)) {
            System.out.println("Login ID already exists. Please choose a different login ID.");
        } else {
            users.put(newLoginId, newPassword);
            System.out.println("Registration successful! You can now log in with your new credentials.");
        }
    }

    private static boolean authenticate(String loginId, String password) {
        return users.containsKey(loginId) && users.get(loginId).equals(password);
    }

    private static void makeReservation(Scanner scanner, String loginId) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter date of journey: ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter from (place): ");
        String from = scanner.nextLine();
        System.out.print("Enter to (destination): ");
        String to = scanner.nextLine();

        Reservation reservation = new Reservation(loginId, name, trainNumber, classType, dateOfJourney, from, to);
        reservations.add(reservation);
        System.out.println("Reservation made successfully! Your PNR number is: " + reservation.getPnrNumber());
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Enter your PNR number: ");
        String pnrNumber = scanner.nextLine();

        Reservation reservationToCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.getPnrNumber().equals(pnrNumber)) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            reservations.remove(reservationToCancel);
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("No reservation found with the given PNR number.");
        }
    }

    private static void viewReservations(String loginId) {
        boolean hasReservations = false;
        System.out.println("Your Reservations:");
        for (Reservation reservation : reservations) {
            if (reservation.getLoginId().equals(loginId)) {
                System.out.println(reservation);
                hasReservations = true;
            }
        }
        if (!hasReservations) {
            System.out.println("You have no reservations.");
        }
    }
}

class Reservation {
    private static int pnrCounter = 1000;
    private String pnrNumber;
    private String loginId;
    private String name;
    private String trainNumber;
    private String classType;
    private String dateOfJourney;
    private String from;
    private String to;

    public Reservation(String loginId, String name, String trainNumber, String classType, String dateOfJourney, String from, String to) {
        this.pnrNumber = "PNR" + pnrCounter++;
        this.loginId = loginId;
        this.name = name;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public String getLoginId() {
        return loginId;
    }

    @Override
    public String toString() {
        return "PNR: " + pnrNumber + ", Name: " + name + ", Train Number: " + trainNumber + ", Class Type: " + classType +
                ", Date of Journey: " + dateOfJourney + ", From: " + from + ", To: " + to;
    }
}
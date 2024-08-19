import java.util.*;

class OnlineExaminationSystem {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static List<Question> questions = new ArrayList<>();
    private static Timer timer = new Timer();
    private static int score = 0; // Moved score to an instance variable

    static {
        // Adding some default users
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));

        // Adding some default questions
        questions.add(new Question("What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "A"));
        questions.add(new Question("What is 2 + 2?", "3", "4", "5", "6", "B"));
        questions.add(new Question("What is the largest planet in our solar system?", "Earth", "Mars", "Jupiter", "Saturn", "C"));
        questions.add(new Question("What is the boiling point of water?", "90°C", "100°C", "110°C", "120°C", "B"));
        questions.add(new Question("Who wrote 'Hamlet'?", "Charles Dickens", "J.K. Rowling", "William Shakespeare", "Mark Twain", "C"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Examination System");

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
            currentUser = users.get(loginId);

            while (true) {
                System.out.println("1. Update Profile and Password");
                System.out.println("2. Start Exam");
                System.out.println("3. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        updateProfile(scanner);
                        break;
                    case 2:
                        startExam(scanner);
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        currentUser = null;
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
            users.put(newLoginId, new User(newLoginId, newPassword));
            System.out.println("Registration successful! You can now log in with your new credentials.");
        }
    }

    private static boolean authenticate(String loginId, String password) {
        return users.containsKey(loginId) && users.get(loginId).getPassword().equals(password);
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Profile updated successfully!");
    }

    private static void startExam(Scanner scanner) {
        System.out.println("Starting exam...");
        score = 0; // Reset score before starting the exam
        int questionIndex = 0;

        // Shuffle the questions list to ensure different order each time
        Collections.shuffle(questions);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Auto-submitting your answers...");
                System.out.println("Your score: " + score + "/" + questions.size());
                System.exit(0);
            }
        }, 60000); // 1 minute timer

        for (Question question : questions) {
            System.out.println("Q" + (++questionIndex) + ": " + question.getQuestion());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().toUpperCase();

            if (answer.equals(question.getCorrectAnswer())) {
                score++;
            }
        }

        timer.cancel();
        System.out.println("Exam finished! Your score: " + score + "/" + questions.size());
    }
}

class User {
    private String password;

    public User(String loginId, String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Question {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
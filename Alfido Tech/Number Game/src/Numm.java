import java.util.Random;
import java.util.Scanner;

public class Numm {
    public static void main(String[] args) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        int numberOfTries = 0;
        Scanner scanner = new Scanner(System.in);
        int guess = 0;

        System.out.println("Welcome to the Number Prediction Game!");
        System.out.println("Guess a number between 1 and 100.");

        while (guess != randomNumber) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            numberOfTries++;

            if (guess < 1 || guess > 100) {
                System.out.println("Invalid guess. Please enter a number between 1 and 100.");
            } else if (guess < randomNumber) {
                System.out.println("Too low. Try again.");
            } else if (guess > randomNumber) {
                System.out.println("Too high. Try again.");
            } else {
                System.out.println("Congratulations! You guessed the number!");
            }

            if (Math.abs(guess - randomNumber) <= 10) {
                System.out.println("Hint: You are very close!");
            } else if (numberOfTries % 3 == 0) {
                if (randomNumber <= 50) {
                    System.out.println("Hint: The number is between 1 and 50.");
                } else {
                    System.out.println("Hint: The number is between 51 and 100.");
                }
            } else if (numberOfTries % 5 == 0) {
                System.out.println(
                        "Hint: The number is " + (randomNumber % 5 == 0 ? "a multiple of 5." : "not a multiple of 5."));
            } else if (numberOfTries % 7 == 0) {
                System.out.println("Hint: The number is " + (randomNumber > 50 ? "greater than 50." : "less than 50."));
            } else {
                System.out.println("Hint: The number is " + (randomNumber % 2 == 0 ? "even" : "odd") + ".");
            }
        }

        System.out.println("Number of tries: " + numberOfTries);
    }
}

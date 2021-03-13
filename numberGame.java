package com.company;

import java.util.Scanner;
import java.util.Random;

public class numberGame {
    public static void main(String[] args) {
        Random rand = new Random();
        int numGuesses = 10;
        int randomNumber = rand.nextInt(100);
        oddEven(randomNumber);
        guessNum(numGuesses, randomNumber);
    }

    static void oddEven(int randomNumber) {
        Scanner oddInput = new Scanner(System.in);
        String oddGuess;
        System.out.print("Do you think the number is odd or even (odd/even): ");
        oddGuess = oddInput.nextLine();
        String userInput = oddGuess.toLowerCase();

        if (randomNumber % 2 == 0) {
            if (userInput.equals("even")) {
                System.out.println("Correct");
            } else {
                System.out.println("Inorrect");
            }
        } else {
            if (userInput.equals("odd")) {
                System.out.println("Correct");
            } else {
                System.out.println("Incorrect");
            }
        }
    }

    static void guessNum(int numGuesses, int randomNumber) {
        Scanner input = new Scanner(System.in);
        if (numGuesses > 0) {
            String userGuess;
            System.out.print("Enter number: ");
            userGuess = input.nextLine();
            int userGuessInt = Integer.parseInt(userGuess);

            if (userGuessInt == randomNumber) {
                System.out.println("Correct");
                System.exit(0);
            } else if (userGuessInt < randomNumber) {
                System.out.println("Too Low");
            } else {
                System.out.println("Too High");
            }
            numGuesses = numGuesses - 1;
            guessNum(numGuesses, randomNumber);
        } else {
            System.out.println("Game Over");
            System.out.println("The number was " + randomNumber);
        }
    }
}

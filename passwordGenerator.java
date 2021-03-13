package com.company;

import java.util.Random;
import java.util.Scanner;

public class passwordGenerator {
    public static void main(String[] args) {
        Scanner length = new Scanner(System.in);
        String letters = "abcdefghijklnmopqrstuvwxyz";

        System.out.print("Enter length of password: ");
        String userLength = length.nextLine();
        int userLengthInt = Integer.parseInt(userLength);

        System.out.print("Do you want symbols (y/n): ");
        String symbolChoice = length.nextLine();

        generate(symbolChoice, userLengthInt);
    }

    static void generate(String symbolChoice, int userLengthInt) {
        Random rand = new Random();
        String password = "";
        String letters = "abcdefghijklnmopqrstuvwxyz1234567890";
        String symbols = "!@#$%^&*_+~?";
        if (symbolChoice.contentEquals("y")) {
            letters = letters + symbols;
        }

        while (userLengthInt != 0) {
            int randomIndex = rand.nextInt(letters.length());
            char chosenChar = letters.charAt(randomIndex);
            password = password + chosenChar;
            userLengthInt = userLengthInt - 1;
        }
        System.out.println(password);
    }
}
/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Michael Seanluc Montemayor
 * Last Updated: 9/16/2024
 */
package montemayorm;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main Driver class
 */
public class Driver {

    private static Scanner scanner = new Scanner(System.in);
    private static int[] inputs = new int[3];
    private static Die[] dieArray;
    private static int[] freq;
    private static final int MAX_DICE = 10;
    private static final int MIN_DICE = 2;
    public static void main(String[] args) {
        getInput();
        createDice(inputs[0], inputs[1]);
        rollDice();
        report();
    }

    /**
     * Takes an input of three integers then separates them into an array
     */
    private static void getInput(){
        while(true){
            System.out.println("Please enter the number of dice to roll, how many sides the dice have,\n" +
                    "and how many rolls to complete, separating the values by a space.");
            try {
                String userInput = scanner.nextLine();
                String[] splitInput = userInput.split(" ");

                // checks input length matches requirement
                if(splitInput.length != 3){
                    throw new IOException();
                }

                //matches user input array to integer input array
                for(int i = 0; i < 3; i++){
                    inputs[i] = Integer.parseInt(splitInput[i]);
                }

                // check if num dice valid
                if(inputs[0] < MIN_DICE || inputs[0] > MAX_DICE){
                    throw new IOException();
                }
                break;
            } catch(NumberFormatException e) {
                System.out.println("Invalid input: All values must be whole numbers.");
            } catch(IOException e) {
                System.out.println("Invalid input");
            }
        }
    }

    /**
     * Creates an array of dice based off of the user input
     * @param numDice
     * @param diceSides
     */
    private static void createDice(int numDice, int diceSides) {
        dieArray = new Die[numDice];
        try {
            for (int i = 0; i < numDice; i++) {
                dieArray[i] = new Die(diceSides);
            }
        } catch(IllegalArgumentException e) {
            System.out.println("invalid number of sides");
        }
    }

    /**
     * rolls the dice in the array and stores them in a frequency array
     */
    private static void rollDice(){
        /* takes the number of sides and multiplies it by the number of dice to
       get the max possible roll minus one then subtracts number of dice to account for the minimum roll */
        freq = new int[(inputs[0]*inputs[1]) - 1 - inputs[0]];
        for(int i = 0; i < inputs[2]; i++) {
            int total = 0;
            for (Die d : dieArray) {
                d.roll();
                try {
                    total += d.getCurrentValue();
                } catch (DieNotRolledException e) {
                    System.out.println("die was not rolled prior to accessing value");
                }
            }
            // subtracts the number of dice to place minimum roll at 0 index
            freq[total-inputs[0]]++;
        }
    }

    /**
     * finds the highest rolled sum of dice from thr rolldice frequency array
     * @return an int containing the highest rolled sum
     */
    private static int findMax(){
        int max = 0;
        for(int i: freq){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    /**
     * nicely prints out the results of the frequency array
     */
    public static void report(){
        int starVal = findMax()/10;
        for(int i: freq) {
            int numStars = i / starVal;
            System.out.printf("%-8d %s%n", i, "*".repeat(numStars));
        }
    }
}
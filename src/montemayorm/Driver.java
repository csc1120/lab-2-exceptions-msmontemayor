/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Michael Seanluc Montemayor
 * Last Updated: 9/11/2024
 */
package montemayorm;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main Driver class
 */
public class Driver {

    private static Scanner sc = new Scanner(System.in);
    private static int[] inputs = new int[3];
    private static Die[] dieArray;
    private static int[] freq;
    private static final int MAX_DICE = 10;
    private static final int MIN_DICE = 2;
    public static void main(String[] args) {
        getInput();
        createDice(inputs[0], inputs[1]);
        rollDice();
        System.out.println(findMax());
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
                String in = sc.nextLine();
                String[] str = in.split(" ");

                // checks input length matches requirement
                if(str.length != 3){
                    throw new IOException();
                }

                //matches user input array to integer input array
                for(int i = 0; i < 3; i++){
                    inputs[i] = Integer.parseInt(str[i]);
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

    private static void rollDice(){
        /* takes the number of sides and multiplies it by the number of dice to
       get the max possible roll then subtracts number of dice to account for the minimum roll */
        freq = new int[(inputs[0]*inputs[1]) - (inputs[0]-1)];
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
            freq[total-2]++;
        }
    }

    private static int findMax(){
        int max = 0;
        for(int i: freq){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    public static void report(){
        int aVal = findMax()/10;
        for(int i: freq) {
            int numStars = i / aVal;
            System.out.printf("%-8d %s%n", i, "*".repeat(numStars));
        }
    }
}
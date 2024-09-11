/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Michael Seanluc Montemayor
 * Last Updated: 9/11/2024
 */
package montemayorm;

import java.util.Random;
/**
 * Die class
 */
public class Die {
    private int currentValue;
    private final int numSides;
    private final Random rand = new Random();
    private final int MIN_SIDES = 2;
    private final int MAX_SIDES = 100;

    /**
     * Constructor for Die
     * @param numSides number of sides for the die
     * @throws IllegalArgumentException if numSides is less than 2 or greater than 100
     */
    public Die(int numSides) throws IllegalArgumentException {
        this.numSides = numSides;
        if (numSides < MIN_SIDES || numSides > MAX_SIDES) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the current value of the die
     * @return the current value of the die
     * @throws DieNotRolledException if the die has not been rolled
     */
    public int getCurrentValue() throws DieNotRolledException {
        if(currentValue == 0){
            throw new DieNotRolledException();
        }
        int temp = currentValue;
        currentValue = 0;
        return temp;
    }

    /**
     * Roll the die
     */
    public void roll(){
        currentValue = rand.nextInt(numSides) + 1;
    }
}
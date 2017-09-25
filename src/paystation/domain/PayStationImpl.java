package paystation.domain;

import java.util.HashMap;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    private int insertedSoFar;
    private int timeBought;
    private int totalCoinValueCollected;
    private HashMap<Integer, Integer> coinDenominations = new HashMap<>();
    private int numberOfNickels = 0;
    private int numberOfDimes = 0;
    private int numberOfQuarters = 0;

    private RateStrategy rateStrategy;

    public PayStationImpl(RateStrategy rateStrategy) {
        this.rateStrategy = rateStrategy;
    }

    public PayStationImpl() {
        rateStrategy = new LinearRateStrategy();
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: {
                coinDenominations.put(5, numberOfNickels++);
            }
            case 10: {
                coinDenominations.put(10, numberOfDimes++);
            }
            case 25: {
                coinDenominations.put(25, numberOfQuarters++);
            } break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        insertedSoFar += coinValue;
        timeBought = rateStrategy.parkingRateCalculation(insertedSoFar);
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalCoinValueCollected += insertedSoFar;
        reset();
        return r;
    }

    /** Cancel the present transaction. Resets the paystation for a
     * new transaction.
     * @return A Map defining the coins returned to the user.
     * The key is the coin type and the associated value is the
     * number of these coins that are returned.
     * The Map object is never null even if no coins are returned.
     * The Map will only contain only keys for coins to be returned.
     * The Map will be cleared after a cancel or buy.
     */
    @Override
    public HashMap<Integer, Integer> cancel() {
        HashMap<Integer, Integer> coinsReturned = new HashMap<>();
        coinDenominations.putAll(coinsReturned);
        reset();
        return coinsReturned;
    }
    
    private void reset() {
        timeBought = insertedSoFar =  0;
        coinDenominations.clear();
    }

    public int empty() {
        int tempCentsCollected = totalCoinValueCollected;
        totalCoinValueCollected = 0;
        return tempCentsCollected;
    }
}

/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.HashMap;
import java.util.Map;

public class PayStationImplTest {
    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl(new LinearRateStrategy());
    }

    //Limits the amount of addPayment calls required for higher dollar amounts
    public void addHalfDollar()
        throws IllegalCoinException{
        ps.addPayment(25);
        ps.addPayment(25);
    }

    //Limits the amount of addPayment calls required for higher dollar amounts
    public void addDollar()
        throws IllegalCoinException{
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
        ps.addPayment(25);
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());

        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());

        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());

        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }

    /**
     * Verify that call to empty returns the total amount entered
     *
     */
    @Test
    public void shouldReturnTotalCoinAmountCollected()
            throws IllegalCoinException{
        int total;

        ps.addPayment(5);
        ps.buy();
        ps.addPayment(10);
        ps.buy();
        ps.addPayment(25);
        ps.buy();

        total = ps.empty();

        assertEquals("Total should be 40", 40, total);
    }


    /**
     * Verify that call to empty resets total coins collected to zero
     * @throws IllegalCoinException
     */
    @Test
    public void shouldResetTotalCoinAmountCollectedOnCallToEmpty()
            throws IllegalCoinException{
        int total;
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);

        total = ps.empty();
        assertEquals("Total should be 0", 0, total);
    }

    /**
     * Verify that canceled entry does not add to the amount returned by empty
     */
    @Test
    public void shouldNotAddToAmountReturnedByEmptyOnCancelCall()
            throws IllegalCoinException{
        int total;

        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);

        ps.cancel();

        total = ps.empty();

        assertEquals("Total should be zero", total, 0);
    }

    /**
     * Verify that call to cancel returns an empty map
     */
    @Test
    public void shouldReturnEmptyMapOnCallToCancel()
            throws IllegalCoinException{
        Map<Integer, Integer> testMap;

        ps.addPayment(5);

        ps.cancel();

        assertTrue("Map should be empty", true);
    }

    /**
     * Call to buy clears the map
     */
    @Test
    public void callToBuyClearsTheMap()
        throws IllegalCoinException{
        Map<Integer, Integer> testMap;

        ps.addPayment(5);
        ps.buy();

        testMap = ps.cancel();

        assertTrue("Map should be empty", testMap.isEmpty());
    }

    /**
     * Call to cancel returns a map containing one coin entered.
     */
    @Test
    public void shouldReturnOneCoinMapOnCallToCancel()
            throws IllegalCoinException{
        Map<Integer, Integer> coinDenominations = new HashMap<>();
        coinDenominations.put(5, 1);
        int coin = coinDenominations.get(5);

        ps.addPayment(5);

        coinDenominations = ps.cancel();

        assertEquals("Should equal 1", coin, 1);
    }

    /**
     * Call to cancel returns a map containing a mixture of coins entered.
     */
    @Test
    public void shouldReturnMultipleCoinMapOnCallToCancel()
        throws IllegalCoinException{
        Map<Integer, Integer> coinDenominations = new HashMap<>();
        coinDenominations.put(5, 1);
        coinDenominations.put(10, 2);
        coinDenominations.put(25, 3);
        int nickel = coinDenominations.get(5);
        int dimes = coinDenominations.get(10);
        int quarters = coinDenominations.get(25);

        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        addHalfDollar();

        coinDenominations = ps.cancel();

        assertEquals("Should equal 1", nickel, 1);
        assertEquals("Should equal 2", dimes, 2);
        assertEquals("Should equal 3", quarters, 3);
    }

    /**
     * Call to cancel returns a map that does not contain a key for a coin not entered.
     */
    @Test
    public void shouldNotContainKeyForCoinNotEntered()
        throws IllegalCoinException{
        Map<Integer, Integer> testMap;

        ps.addPayment(5);

        testMap = ps.cancel();

        assertFalse(testMap.containsKey(25));

    }

    /**
     * Call to buy should implement Progressive Rate correctly when amount entered is 150 - 350
     */
    @Test
    public void shouldDisplay120MinFor350cents()
        throws IllegalCoinException{
        ps = new PayStationImpl(new ProgressiveRateStrategy());

        addDollar();
        addDollar();
        addDollar();
        addHalfDollar();

        assertEquals("Progressive Rate should give 120 min for 350 cents", 120, ps.readDisplay());
    }

    /**
     * Call to buy should implement Progressive Rate correctly when amount entered exceeds 350
     */
    @Test
    public void shouldDisplay180MinFor650cents()
            throws IllegalCoinException{
        ps = new PayStationImpl(new ProgressiveRateStrategy());

        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addHalfDollar();

        assertEquals("Progressive Rate should give 120 min for 350 cents", 180, ps.readDisplay());
    }
}

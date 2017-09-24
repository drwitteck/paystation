package paystation.domain;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ProgressiveRateStrategyTest {
    PayStation ps;

    @Before
    public void setUp() throws Exception {
        ps = new PayStationImpl(new ProgressiveRateStrategy());
    }

    @After
    public void tearDown() throws Exception {
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
     * Entering $1.50 should make the display report 60 minutes of parking time
     */
    @Test
    public void shouldDisplay60MinFor150cents()
        throws IllegalCoinException{
        addDollar();
        addHalfDollar();

        assertEquals("Should display 60 minutes for 150 cents", 60, ps.readDisplay());
    }

    /**
     * Entering $3.50 should make the display report 120 minutes of parking time
     */
    @Test
    public void shouldDisplay120MinFor350cents()
        throws IllegalCoinException{
        addDollar();
        addDollar();
        addDollar();
        addHalfDollar();

        assertEquals("Should display 120 minutes for 350 cents", 120, ps.readDisplay());
    }

    @Test
    /**
     * Entering $6.50 should make the display report 180 minutes of parking time
     */
    public void shouldDisplay180MinFor650cents()
        throws IllegalCoinException{
        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addDollar();
        addHalfDollar();

        assertEquals("Should display 180 minutes for 650 cents", 180, ps.readDisplay());
    }

}
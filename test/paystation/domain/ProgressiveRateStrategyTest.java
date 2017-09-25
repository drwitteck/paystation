package paystation.domain;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ProgressiveRateStrategyTest {
    RateStrategy rs;

    @Before
    public void setUp(){
        rs = new ProgressiveRateStrategy();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Entering $1.50 should make the display report 60 minutes of parking time
     */
    @Test
    public void shouldDisplay60MinFor150cents()
        throws IllegalCoinException{

        assertEquals("Should display 60 minutes for 150 cents", 60, rs.parkingRateCalculation(150));
    }

    /**
     * Entering $3.50 should make the display report 120 minutes of parking time
     */
    @Test
    public void shouldDisplay120MinFor350cents()
        throws IllegalCoinException{

        assertEquals("Should display 120 minutes for 350 cents", 120, rs.parkingRateCalculation(350));
    }

    @Test
    /**
     * Entering $6.50 should make the display report 180 minutes of parking time
     */
    public void shouldDisplay180MinFor650cents()
        throws IllegalCoinException{

        assertEquals("Should display 180 minutes for 650 cents", 180, rs.parkingRateCalculation(650));
    }

}
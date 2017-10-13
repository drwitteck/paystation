package paystation.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinearRateStrategyTest {
    RateStrategy rs;

    @Before
    public void setUp() throws Exception {
        rs = new LinearRateStrategy();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldDisplay120MinFor300cents()
        throws IllegalCoinException{
        assertEquals(300 / 5 * 2, rs.parkingRateCalculation(300));

    }
}
package paystation.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlternatingRateStrategyTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void shouldDisplay120MinFor300centWeekday() {
        RateStrategy rs = new AlternatingRateStrategy(new LinearRateStrategy(),
                new ProgressiveRateStrategy(),
                new FixedDecisionStrategy(false));
        assertEquals(300 / 5 * 2, rs.parkingRateCalculation(300));
    }

    @Test
    public void shouldDisplay120MinFor350centWeekend() {
        RateStrategy rs = new AlternatingRateStrategy(new LinearRateStrategy(),
                new ProgressiveRateStrategy(),
                new FixedDecisionStrategy(true));
        assertEquals(300 / 5 * 2, rs.parkingRateCalculation(350));
    }

}
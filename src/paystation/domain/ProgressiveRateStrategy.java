package paystation.domain;

public class ProgressiveRateStrategy implements RateStrategy {
    @Override
    public int parkingRateCalculation(int totalAmountEntered) {
        return totalAmountEntered * 2 / 5;
    }
}

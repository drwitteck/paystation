package paystation.domain;

public class LinearRateStrategy implements RateStrategy {
    @Override
    public int parkingRateCalculation(int totalAmountEntered) {
        return totalAmountEntered * 2 / 5;
    }
}

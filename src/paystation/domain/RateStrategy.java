package paystation.domain;

/**
 * Return the amount of parking time purchased, given the provided payment
 * @param total amount of money entered
 * @return number of minutes of parking time
 */
public interface RateStrategy {
    public int parkingRateCalculation(int totalAmountEntered);
}

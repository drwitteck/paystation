package paystation.domain;

public class ProgressiveRateStrategy implements RateStrategy {
    @Override
    public int parkingRateCalculation(int totalAmountEntered) {
        int time = 0;

        if (totalAmountEntered <= 150) {
            time = totalAmountEntered * 2 / 5;
        }
        else if (totalAmountEntered > 150 && totalAmountEntered <= 350){
            time = 60 + (totalAmountEntered - 150) * 3 / 10;
        }
        else if (totalAmountEntered > 350){
            time = 120 + (totalAmountEntered - 350) * 2 / 10;
        }

        return time;
    }
}

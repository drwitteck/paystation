package paystation.domain;

public class AlternatingRateStrategy implements RateStrategy{
    private RateStrategy weekdayStrategy;
    private RateStrategy weekendStrategy;
    private RateStrategy currentState;
    private WeekendDecisionStrategy decisionStrategy;

    public AlternatingRateStrategy(RateStrategy weekdayStrategy,
        RateStrategy weekendStrategy, WeekendDecisionStrategy decisionStrategy){
        this.weekendStrategy = weekendStrategy;
        this.weekdayStrategy = weekdayStrategy;
        this.decisionStrategy = decisionStrategy;
        this.currentState = null;
    }

    @Override
    public int parkingRateCalculation(int totalAmountEntered){
        if(decisionStrategy.isWeekend()){
            currentState = weekendStrategy;
        } else {
            currentState = weekdayStrategy;
        }
        return currentState.parkingRateCalculation(totalAmountEntered);
    }
}

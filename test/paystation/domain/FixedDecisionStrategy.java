package paystation.domain;

public class FixedDecisionStrategy implements WeekendDecisionStrategy {
    private boolean isWeekend;
    public FixedDecisionStrategy(boolean isWeekend){
        this.isWeekend = isWeekend;
    }

    public boolean isWeekend(){
        return isWeekend;
    }
}

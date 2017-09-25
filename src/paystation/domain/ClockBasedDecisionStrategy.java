package paystation.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClockBasedDecisionStrategy implements WeekendDecisionStrategy{
    public boolean isWeekend(){
        Calendar c = new GregorianCalendar();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return(dayOfWeek == Calendar.SATURDAY
                || dayOfWeek == Calendar.SUNDAY);

    }
}

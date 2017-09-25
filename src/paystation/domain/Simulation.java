package paystation.domain;

import com.sun.xml.internal.ws.api.ha.HaInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Simulation {
    public static void main(String[] args)
        throws IllegalCoinException {
        PayStationImpl payStation = new PayStationImpl(new LinearRateStrategy());
        RateStrategy rateStrategy;
        int userChoice;
        Scanner in = new Scanner(System.in);

        while(true){
            displayMenu();

            userChoice = in.nextInt();

            switch (userChoice){
                case 1:{
                    int coin;
                    boolean addingCoins = true;

                    while(addingCoins){
                        System.out.println("Please add 5, 10, or 25 cent coins. 0 to stop.");
                        coin = in.nextInt();

                        if(0 == coin){
                            addingCoins = false;
                        } else{
                            payStation.addPayment(coin);
                        }
                    }
                    break;
                }
                case 2:{
                    int status;
                    System.out.println("Time bought so far: " + payStation.readDisplay() + " minutes");
                    System.out.println("Press 1 to go back, Press 3 to buy this time: ");
                    status = in.nextInt();
                    if(1 == status){
                        displayMenu();
                    } else if(status == 3){
                        Receipt r = payStation.buy();
                        System.out.println("Here is you receipt:\n" + "You have purchased: " + r.value() + " minutes.");
                        System.out.println("");
                        break;
                    }
                    break;
                }
                case 3:{
                    Receipt r = payStation.buy();
                    System.out.println("Thank you for using Pay Station");
                    System.out.println("Here is you receipt:\n" + "You have purchased: " + r.value() + " minutes.");
                    System.out.println("");
                    break;
                }
                case 4:{
                    System.out.println("Cancel");
                    for (Map.Entry<Integer, Integer> entry : payStation.cancel().entrySet()) {
                        System.out.println("Coin Value: " + entry.getKey() + " amount " + entry.getValue());
                    }
                    break;
                }
                case 5:{
                    int maintenanceChoice;

                    System.out.println("Please enter your choice\n" +
                            "1: Alphatown\n" +
                            "2: Betatown\n" +
                            "3: Gammatown");
                    maintenanceChoice = in.nextInt();

                    switch (maintenanceChoice){
                        case 1:{
                            payStation = new PayStationImpl(new LinearRateStrategy());
                            System.out.println("Application changed to Alphatown Rate Strategy");
                            break;
                        }
                        case 2:{
                            payStation = new PayStationImpl(new ProgressiveRateStrategy());
                            System.out.println("Application changed to Betatown Rate Strategy");
                            break;
                        }
                        case 3:{
                            payStation = new PayStationImpl(new AlternatingRateStrategy
                                            (new LinearRateStrategy(),
                                            new ProgressiveRateStrategy(),
                                            new ClockBasedDecisionStrategy()));
                            System.out.println("Application changed to Gammatown Rate Strategy");
                            break;
                        }
                        default:{
                            System.out.println("Not a valid selection.");
                            break;
                        }
                    }
                    break;
                }
                case 6:{
                    System.exit(0);
                }
            }


        }
    }

    public static void displayMenu(){
        System.out.println("Welcome to the Pay Station");
        System.out.println("Please enter you choice: ");
        System.out.println("1: Deposit Coins\n" +
                "2: Display\n" +
                "3: Buy Ticket\n" +
                "4: Cancel\n" +
                "5: Change Rate Strategy\n" +
                "6: To Exit the Application\n");
    }
}

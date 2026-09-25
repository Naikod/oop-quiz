package id.ac.polinema.oop;

public class Cashier {
    public double calculateChange(Order order, double cash){
        double change = cash - order.getTotal();
        if(change < 0){
            return -1;
        } else {
            return change;
        }
    }
}

package id.ac.polinema.oop;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;


    public OrderItem(MenuItem menuItem, int quantity){
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem(){
        return this.menuItem;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getSubtotal(){
        return this.menuItem.getPrice() * this.quantity;
    }
}

package id.ac.polinema.oop;

public class Order {
    private Customer customer;
    private OrderItem[] items;
    private int itemCount;

    public Order(Customer customer){
        this.items = new OrderItem[10];
        this.customer = customer;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public void addItem(MenuItem item, int quantity){
        if(itemCount < 10){
            this.items[itemCount] = new OrderItem(item, quantity);
            this.itemCount++;
        }
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public double getTotal(){
        double total = 0;
        for(int i = 0; i < this.itemCount; i++){
            total += this.items[i].getMenuItem().getPrice() * this.items[i].getQuantity();
        }
        return total;
    }

    public double getFinalTotal(){
        double total = getTotal();
        double discount = 0;
        if(total > 100000){
            discount = total * 0.1;
        } else if(total > 50000){
            discount = total * 0.05;
        }
        return total - discount;
    }
}

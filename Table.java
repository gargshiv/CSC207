package restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * The table class represents a table in its object entity. It is responsible for getting an order
 * from a customer from a particular table of a restaurant. Also, once the order has been delivered,
 * the table class prints the bill on the screen.
 */
public class Table {
    
    private final Set<OrderDish> receivedDishes = new HashSet<>();
    private BigDecimal billAmount = new BigDecimal(0);
    private Restaurant restaurant;
    private int num;
    
    /**
     * Store the restaurant this table is in and the number of the table in that restaurant.
     *
     * @param restaurant the table that belongs to the restaurant
     * @param num        the number of the table
     */
    public Table(Restaurant restaurant, int num) {
        this.restaurant = restaurant;
        this.num = num;
    }
    
    /**
     * Return the restaurant the particular table is in.
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    /**
     * Return the number of the table.
     */
    public int getNum() {
        return num;
    }
    
    /**
     * Add price of dish to tables bill and add dish to tables recivedDishes
     *
     * @param dish a dish from the tables order
     */
    public void receiveDish(OrderDish dish) {
        receivedDishes.add(dish);
        billAmount = billAmount.add(dish.getPrice());
    }
    
    /**
     * Remove price of bill and received dishes
     */
    public void payForDishes() {
        printBill();
        receivedDishes.clear();
        billAmount = new BigDecimal(0);
    }
    
    public Set<OrderDish> getReceivedDishes() {
        return receivedDishes;
    }
    
    /**
     * Print table's bill to console
     */
    public void printBill() {
        StringBuilder bill = new StringBuilder();
        java.util.Date date = new java.util.Date();
        BigDecimal hst = new BigDecimal(1.12);
        String nl = System.getProperty("line.separator");
        String tab = "\t";
        
        bill.append("HERE IS YOUR BILL").append(nl).append(nl);
        bill.append("Table No: ").append(num).append(nl);
        bill.append("DATE: ").append(date).append(nl).append(nl);
        bill.append("Dishes:").append(nl);
        
        for (OrderDish dish : receivedDishes) {
            bill.append(dish.getItem().getName())
                    .append(tab)
                    .append(dish.getItem().getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                    .append(nl);
            
            for (MenuItemMod mod : dish.getMods()) {
                bill.append(tab)
                        .append("- ")
                        .append(mod.getName())
                        .append(tab)
                        .append(mod.getPriceChange().setScale(2, BigDecimal.ROUND_HALF_EVEN))
                        .append(nl);
            }
            
            bill.append(nl);
        }
        
        bill.append("Sub-Total: ")
                .append(tab)
                .append(billAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN))
                .append(nl);
        bill.append("Total: ")
                .append(tab)
                .append(billAmount.multiply(hst).setScale(2, BigDecimal.ROUND_HALF_EVEN))
                .append(nl);
        
        bill.append(
                "***********************************************************************************")
                .append(nl);
        bill.append("THANK YOU! COME AGAIN SOON!").append(nl);
        bill.append("Please keep a copy of this for your records.");
        System.out.println(bill);
        System.out.println();
    }
    
}

package restaurant;

import java.util.Set;

/**
 * The Server class is responsible to take an order from the customer and deliver the order was it
 * has been made after receiving the appropriate signals and method calls.
 */
public class Server extends Employee {
    /**
     * Creates a new Server Object with a name.
     *
     * @param name The servers name.
     */
    public Server(String name) {
        super(name);
    }

    /**
     * Records that the specified Order was delivered to its Table.
     *
     * @param order          The Order that was delivered
     * @param rejectedDishes The Set of OrderDishes from the Order that the Table rejected
     */
    public void deliverOrder(Order order, Set<OrderDish> rejectedDishes) {
        for (OrderDish dish : order.getDishes().values()) {
            if (!rejectedDishes.contains(dish)) {
                order.getTable().receiveDish(dish);
            }
        }
        getRestaurant().markOrderDelivered(order);
        printOrderDelivered(order);
    }

    /**
     * Takes an order from the customer and marks that the order has been placed.
     *
     * @param order The order given by the customer.
     */
    public void placeOrder(Order order) {
        getRestaurant().markOrderPlaced(order);
        printOrderPlaced(order);
    }
    
    private void printOrderPlaced(Order order){
        StringBuilder orderString = new StringBuilder();
        orderString.append("Order for table " + order.getTable().getNum() +
                " has been placed by Server: " + getName() + "\n");
        String nl = System.getProperty("line.separator");
        String tab = "\t";
        orderString.append("Dishes:").append(nl);
        for (OrderDish dish : order.getDishes().values()) {
            orderString.append(dish.getItem().getName()).append(tab);

            for (MenuItemMod mod : dish.getMods()) {
                orderString.append(tab).append("- ").append(mod.getName()).append(tab);
            }
            orderString.append(nl);
        }
        System.out.println(orderString);
    }
    
    private void printOrderDelivered(Order order){
        StringBuilder orderString = new StringBuilder();
        orderString.append("Order has been delivered to table : " + order.getTable().getNum() + " by " + getName() + "\n");
        String nl = System.getProperty("line.separator");
        String tab = "\t";
        orderString.append("Dishes:").append(nl);
        for (OrderDish dish : order.getDishes().values()) {
            orderString.append(dish.getItem().getName()).append(tab);

            for (MenuItemMod mod : dish.getMods()) {
                orderString.append(tab).append("- ").append(mod.getName()).append(tab);
            }

            orderString.append(nl);
        }

        System.out.println(orderString);
    }
    
}

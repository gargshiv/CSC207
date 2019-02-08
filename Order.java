package restaurant;

import java.util.HashMap;
import java.util.Map;

/**
 * This Order class records information that represents a complete order placed by a customer
 * including all of the dishes ordered and the table from which it was ordered from.
 */
public class Order {
    
    private final Map<Integer, OrderDish> dishes = new HashMap<>();
    private Table table;
    
    /**
     * Creates a new Order object that stores that stores all dishes order and the table which ordered
     * them.
     *
     * @param table The table associated with this order.
     */
    public Order(Table table) {
        this.table = table;
    }
    
    /**
     * Returns the table associated with this order.
     */
    public Table getTable() {
        return table;
    }
    
    /**
     * Returns all the dishes in this order.
     */
    public Map<Integer, OrderDish> getDishes() {
        return dishes;
    }
    
    /**
     * Returns a specific dish ordered in this order.
     *
     * @param id The id corresponding to a dish in the Order.
     */
    public OrderDish getDish(int id) {
        return dishes.get(id);
    }
    
    /**
     * Adds a dish to this Order.
     *
     * @param dish the dish ordered by the customer is being added.
     */
    public void addDish(OrderDish dish) {
        dishes.put(dish.getID(), dish);
        dish.setOrder(this);
    }
    
    /**
     * Returns whether all the dishes have been filled and are ready to be made.
     */
    public boolean isFilled() {
        for (OrderDish dish : dishes.values()) {
            if (!dish.isFilled()) {
                return false;
            }
        }
        return true;
    }
    
}

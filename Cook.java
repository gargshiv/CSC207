package restaurant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The cook class is responsible to prepare an Order once it has seen it and also subtracts
 * ingredients as the dish is made and ready to deliver.
 */
public class Cook extends Employee {
    
    private final Set<OrderDish> makingDishes = new HashSet<>();
    
    /**
     * Creates a new Cook with the specified name and no assigned Restaurant.
     *
     * @param name The Cook's name
     */
    public Cook(String name) {
        super(name);
    }
    
    /**
     * Returns an unmodifiable Set view of the OrderDishes that this Cook is making.
     *
     * @return The OrderDishes that this Cook is making
     */
    public Set<OrderDish> getMakingDishes() {
        return Collections.unmodifiableSet(makingDishes);
    }
    
    /**
     * Sees the specified OrderDish and begins making it.
     *
     * @param dish The OrderDish to see
     */
    public void seeDish(OrderDish dish) {
        makingDishes.add(dish);
        getRestaurant().markDishSeen(dish);
        printSeenDish(dish);
    }
    
    /**
     * Finishes preparing the specified OrderDish, if this Cook was making it, and subtracts its
     * ingredients from the inventory.
     *
     * @param dish the dish that needs to be prepared
     * @return Whether the OrderDish was prepared
     */
    public boolean prepareDish(OrderDish dish) {
        if (makingDishes.contains(dish)) {
            makingDishes.remove(dish);
            for (Map.Entry<String, Integer> amount : dish.getIngredientAmounts().entrySet()) {
                getRestaurant().subtractAmount(amount.getKey(), amount.getValue());
            }
            if (dish.getOrder().isFilled()) {
                getRestaurant().markOrderFilled(dish.getOrder());
            }
            printPreparedDish(dish);
            return true;
        }
        return false;
    }
    
    private void printPreparedDish(OrderDish dish) {
        StringBuilder dishString = new StringBuilder();
        String nl = System.getProperty("line.separator");
        String tab = "\t";
        dishString.append(
                dish.getItem().getName() + " for table " + dish.getOrder().getTable().getNum());
        dishString.append(" has been prepared by Cook: " + getName());
        if (!dish.getMods().isEmpty()) {
            dishString.append(" with" + nl);
            for (MenuItemMod mod : dish.getMods()) {
                dishString.append(tab).append("- ").append(mod.getName()).append(tab);
            }
        }
        System.out.println(dishString);
        System.out.println();
    }
    
    private void printSeenDish(OrderDish dish) {
        System.out.println(
                dish.getItem().getName()
                        + " placed by table "
                        + dish.getOrder().getTable().getNum()
                        + " has been seen by "
                        + "Cook: "
                        + getName());
        System.out.println();
    }
    
}

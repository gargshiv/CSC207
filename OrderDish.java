package restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderDish {
    
    private final int id;
    private final Set<MenuItemMod> mods = new HashSet<>();
    private final Map<String, Integer> ingredientAmounts;
    private Order order = null;
    private MenuItem item;
    private BigDecimal price;
    private boolean filled = false;
    
    /**
     * Creates an OrderDish object with no assigned Order, an id, and item.
     *
     * @param id   represents the id associated with this OrderDish
     * @param item Represents the MenuItem that has been ordered.
     */
    public OrderDish(int id, MenuItem item) {
        this.id = id;
        this.item = item;
        this.price = item.getPrice();
        this.ingredientAmounts = item.getIngredientAmounts();
    }
    
    /**
     * Returns a set of the MenuItemMods to which this OrderDish is assigned.
     *
     * @return A set of the MenuItemMods to which this OrderDish is assigned
     */
    public Set<MenuItemMod> getMods() {
        return mods;
    }
    
    /**
     * Returns the Order to which this OrderDish is assigned.
     *
     * @return The Order to which this OrderDish is assigned
     */
    public Order getOrder() {
        return order;
    }
    
    /**
     * Records that this OrderDish is assigned to the specified Order.
     *
     * @param order The Order to which this OrderDish is assigned
     */
    public void setOrder(Order order) {
        this.order = order;
    }
    
    /**
     * Returns the MenuItem to which this OrderDish is assigned.
     *
     * @return The MenuItem to which this OrderDish is assigned
     */
    public MenuItem getItem() {
        return item;
    }
    
    /**
     * Return the ID associated with this OrderDish.
     */
    public int getID() {
        return id;
    }
    
    /**
     * Adds a modification to the item ordered.
     *
     * @param mod represents a MenuItemMod containing all the modification to the item.
     */
    public void addMod(MenuItemMod mod) {
        mods.add(mod);
        for (String ingredient : mod.getIngredientChanges().keySet()) {
            ingredientAmounts.put(
                    ingredient,
                    ingredientAmounts.getOrDefault(ingredient, 0) + mod.getIngredientChange(ingredient));
        }
        price = price.add(mod.getPriceChange());
    }
    
    /**
     * Returns the price of the OrderDish.
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Returns the Amount of ingredients that the item in this OrderDish has.
     */
    public Map<String, Integer> getIngredientAmounts() {
        return ingredientAmounts;
    }
    
    /**
     * Return whether the order has been filled or not.
     */
    public boolean isFilled() {
        return filled;
    }
    
    /**
     * Assert that order has been filled.
     */
    public void fill() {
        filled = true;
    }
    
}

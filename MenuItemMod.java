package restaurant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps a track of all the modifications that are present in a particular dish of a
 * particular order. It also keeps a track of all the prices and ingredient amount changes of that
 * particular modification applied to the dish.
 */
public class MenuItemMod {
    
    private final String name;
    private final BigDecimal priceChange;
    private final Map<String, Integer> ingredientChanges = new HashMap<>();
    
    /**
     * Stores the name of the modification and the price to be applied.
     */
    public MenuItemMod(String name, BigDecimal priceChange) {
        this.name = name;
        this.priceChange = priceChange;
    }
    
    /**
     * Returns the name of the modification.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the price of the modification applied.
     */
    public BigDecimal getPriceChange() {
        return priceChange;
    }
    
    /**
     * Returns the ingredient amount changes information.
     */
    public Map<String, Integer> getIngredientChanges() {
        return ingredientChanges;
    }
    
    /**
     * Returns ingredient changes of a particular ingredient
     */
    public int getIngredientChange(String ingredient) {
        return ingredientChanges.getOrDefault(ingredient, 0);
    }
    
    /**
     * Stores the ingredient changes information with respect to that ingredient.
     */
    public void setIngredientChange(String ingredient, int change) {
        ingredientChanges.put(ingredient, change);
    }
    
}

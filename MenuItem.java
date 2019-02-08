package restaurant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class stores the number of ingredients each menuItem has.
 */
public class MenuItem {
    
    private final String name;
    private final BigDecimal price;
    private final Map<String, Integer> ingredientAmounts = new HashMap<>();
    private final Map<String, MenuItemMod> mods = new HashMap<>();
    
    /**
     * Stores the name and price relating to it.
     *
     * @param name  the name of the item on the menu
     * @param price the price of the item on the menu
     */
    public MenuItem(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
    
    /**
     * Returns the name of the item on the menu
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the price of the item on the Menu
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Returns the ingredients and its number the item on the menu takes to prepare.
     */
    public Map<String, Integer> getIngredientAmounts() {
        return ingredientAmounts;
    }
    
    /**
     * Returns the default ingredient amount of the ingredient.
     */
    public int getIngredientAmount(String ingredient) {
        return ingredientAmounts.getOrDefault(ingredient, 0);
    }
    
    /**
     * Setter method that sets the number of a particular ingredient needed to make a dish.
     */
    public void setIngredientAmount(String ingredient, int amount) {
        ingredientAmounts.put(ingredient, amount);
    }
    
    /**
     * Returns a Map from the names of the MenuItemMods available for this MenuItem to the
     * MenuItemMods themselves.
     *
     * @return A Map from the names of the MenuItemMods available for this MenuItem to the
     * MenuItemMods themselves
     */
    public Map<String, MenuItemMod> getMods() {
        return mods;
    }
    
    /**
     * Returns the MenuItemMod with the specified name available for this MenuItem, or null if there
     * is none.
     *
     * @param name The name of the MenuItemMod
     * @return This MenuItem's mod with the specified name
     */
    public MenuItemMod getMod(String name) {
        return mods.get(name);
    }
    
    /**
     * Adds modifications to a dish, if customised.
     */
    public void addMod(MenuItemMod mod) {
        mods.put(mod.getName(), mod);
    }
    
}

package restaurant;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a menu class. This contains all the menu items that are available and offered by a
 * restaurant.
 */
public class Menu {
    
    private final Map<String, MenuItem> items = new HashMap<>();
    
    /**
     * Returns the menu item object of the restaurant
     *
     * @param name name of the menu item
     * @return menu item object that contains information
     */
    public MenuItem getItem(String name) {
        return items.get(name);
    }
    
    /**
     * Adds the menu item to the menu of the restaurant.
     */
    public void addItem(MenuItem item) {
        items.put(item.getName(), item);
    }
    
}

package restaurant;

/**
 * The receiver class brings ingredients to the restaurant and adds their amounts to the inventory.
 */
public class Receiver extends Employee {
    
    public Receiver(String name) {
        super(name);
    }
    
    /**
     * Adds a number of a particular ingredient to the inventory of the restaurant.
     *
     * @param ingredient the receiver has brought
     * @param amount     the value the receiver has brought
     */
    public void receiveIngredients(String ingredient, int amount) {
        getRestaurant().addAmount(ingredient, amount);
        System.out.println(amount + " units of " + ingredient + " has been received by " + getName());
        System.out.println();
    }
    
}

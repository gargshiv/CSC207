package restaurant;

/**
 * A Manager class can see the requests made and creates an email (not within the program) to send
 * it to the distributer. At any point of time, the manager can see a list of all inventory items
 * and ingredients including their current amounts and thresholds.
 */
public class Manager extends Employee {
    
    public Manager(String name) {
        super(name);
    }
    
    /**
     * The manager can check the inventory anytime he wants to. Once the method is invoked, the
     * current status of the inventory with its respective amounts and thresholds display on the
     * screen.
     */
    public void checkInventory() {
        System.out.println("INVENTORY");
        for (String ingredient : getRestaurant().getIngredients()) {
            System.out.println("Item: " + ingredient);
            System.out.println("Threshold: " + getRestaurant().getThreshold(ingredient));
            System.out.println("Current amount: " + getRestaurant().getAmount(ingredient));
        }
        System.out.println();
    }
    
}

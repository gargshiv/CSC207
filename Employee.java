package restaurant;

/**
 * This is an abstract class that is the superclass of all the employees that work in a restaurant.
 * It stores all the information about the employee, that is, its name, and the restaurant its
 * associated with.
 */
public abstract class Employee {
    
    private final String name;
    private Restaurant restaurant = null;
    
    /**
     * Sets the name of the employee
     */
    public Employee(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the employee
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the restaurant the employee works at
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    /**
     * Assigns the restaurant an employee works at.
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
}

package restaurant;

import java.util.*;

/**
 * The restaurant class deals with a particular restaurant. All different employees that belong to a
 * particular restaurant, the tables associated with it and the processes that happen in and from
 * this restaurant occur here.
 */
public class Restaurant {
    
    private final Menu menu;
    private final Map<String, IngredientData> ingredientData = new HashMap<>();
    private final List<Table> tables;
    private final Map<String, Cook> cooks = new HashMap<>();
    private final Map<String, Server> servers = new HashMap<>();
    private final Map<String, Manager> managers = new HashMap<>();
    private final Map<String, Receiver> receivers = new HashMap<>();
    private final Set<OrderDish> unseenDishes = new HashSet<>();
    private final Set<Order> readyOrders = new HashSet<>();
    private Formatter formatter = null;
    
    /**
     * This parameterised constructor instantiates tables for that restaurant for the customers to
     * give their orders in, assigns a particular menu and makes a new requests.txt file: all of which
     * are unique to a particular restaurant.
     *
     * @param menu      the menu that the restaurant is dealt with.
     * @param numTables the number of tables that the restaurant has.
     */
    public Restaurant(Menu menu, int numTables) {
        //assigns the menu to the particular restaurant
        this.menu = menu;
        tables = new ArrayList<>(numTables);
        //initialises the different number of tables present in the restaurant.
        for (int i = 1; i <= numTables; i++) {
            tables.add(new Table(this, i));
        }
        //creates a requests.txt file as soon as restaurant class has been instantiated.
        try {
            formatter = new Formatter("requests.txt");
        } catch (Exception e) {
            System.out.println("Error: Could not find or create requests.txt");
        }
    }
    
    /**
     * This method serves a getter method used by different classes.
     *
     * @return the menu that this particualar restaurant has.
     */
    public Menu getMenu() {
        return menu;
    }
    
    /**
     * The set of the IngredientsData Hashmap is returned.
     *
     * @return the Set of the ingredientsData HashMap that contains ingredients and their amounts.
     */
    public Set<String> getIngredients() {
        return ingredientData.keySet();
    }
    
    /**
     * A request is created in the requests.txt file, for the manager to cut and paste into the email.
     * The manager can also manually changed the quantity that needs to be ordered.
     *
     * @param ingredient the item (string) that needs to be ordered.
     */
    private void orderIngredient(String ingredient) {
        if (formatter != null) {
            formatter.format("%s %s %s", "ITEM REQUIRED :", ingredient, "20");
            formatter.close();
        }
    }
    
    /**
     * As it receives input after reading a file, this method adds a particular ingredient with its
     * respective values to the hashMap so that it could be used in the program. Additionally, it
     * checks whether the current amount of that ingredient is not less than the threshold. If it is
     * then it it signals to add that ingredient in the requests.txt file.
     *
     * @param ingredient     ingredient(String) that is present in the restaurant and constitutes dishes.
     * @param threshold      limiting amount that is passed by the file.
     * @param startingAmount the initial number of that particular ingredient the restaurant has.
     */
    public void addIngredient(String ingredient, int threshold, int startingAmount) {
        ingredientData.put(ingredient, new IngredientData(threshold, startingAmount));
        if (startingAmount < threshold) {
            orderIngredient(ingredient);
        }
    }
    
    /**
     * This method returns the threshold value of a particular ingredient in a restaurant.
     *
     * @param ingredient Ingredient whose threshold value needs to be found.
     * @return the threshold value of the ingredient by accessing it through the key of the Map.
     */
    public int getThreshold(String ingredient) {
        return ingredientData.get(ingredient).threshold;
    }
    
    /**
     * This method returns the current amount of a particular ingredient in a restaurant.
     *
     * @param ingredient Ingredient whose current amount needs to be found.
     * @return the current amount of the ingredient by accessing it through the key of the Map.
     */
    public int getAmount(String ingredient) {
        return ingredientData.get(ingredient).amount;
    }
    
    /**
     * This method updates the current amount of an ingredient as its brought by a receiver.
     *
     * @param ingredient Ingredient whose current amount needs to be updated.
     */
    public void addAmount(String ingredient, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Attempted to add a negative amount of an ingredient");
        }
        IngredientData data = ingredientData.get(ingredient);
        data.amount += amount;
    }
    
    /**
     * As the dishes are prepared, the current amount of an ingredient gets updated. If it is not
     * possible, it gives an appropriate message on the screen. In the process, if the value after
     * updating goes below the threshold value, then a signal to create a request is sent.
     *
     * @param ingredient the string whose value needs to be subtracted as a dish is prepared.
     * @param amount     the current value of the ingredient that needs to be subtracted
     * @return if subtraction can be done after all the constraints are applied then it returns true
     */
    public boolean subtractAmount(String ingredient, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Attempted to subtract a negative amount of an ingredient");
        }
        IngredientData data = ingredientData.get(ingredient);
        if (data.amount < amount) {
            return false;
        }
        if (data.amount >= data.threshold && data.amount - amount < data.threshold) {
            orderIngredient(ingredient);
        }
        data.amount -= amount;
        return true;
    }
    
    /**
     * This method returns the number of tables that are there in a restaurant, used by other classes.
     *
     * @return the number of tables in the restaurant
     */
    public int getNumTables() {
        return tables.size();
    }
    
    /**
     * Returns this Restaurant's table with the specified number, or null if there is none.
     *
     * @param num The number of the table
     * @return The table with the specified number
     */
    public Table getTable(int num) {
        if (num >= 1 && num <= tables.size()) {
            return tables.get(num - 1);
        }
        return null;
    }
    
    /**
     * This method acts as a getter to know which cook has prepared a particular dish.
     *
     * @param name name of the cook
     * @return the Cook Object that has attributes and behaviour.
     */
    public Cook getCook(String name) {
        return cooks.get(name);
    }
    
    /**
     * Adds to the Map of Cooks that are responsible to make dishes in a particular restaurant.
     *
     * @param cook Adds the cook to the Map of Cooks
     */
    public void addCook(Cook cook) {
        cooks.put(cook.getName(), cook);
        cook.setRestaurant(this);
    }
    
    /**
     * Returns the Server assigned to this Restaurant with the specified name, or null if there is
     * none.
     *
     * @param name The name of the Server
     * @return The Server with the specified name
     */
    public Server getServer(String name) {
        return servers.get(name);
    }
    
    /**
     * Adds the server to the Map of Servers that are responsible to collect orders and serve dishes
     * in a particular restaurant.
     *
     * @param server adds the server instance to the list of servers in a restaurant.
     */
    public void addServer(Server server) {
        servers.put(server.getName(), server);
        server.setRestaurant(this);
    }
    
    /**
     * Returns the Manager assigned to this Restaurant with the specified name, or null if there is
     * none.
     *
     * @param name The name of the Manager
     * @return The Manager with the specified name
     */
    public Manager getManager(String name) {
        return managers.get(name);
    }
    
    /**
     * Adds the Manager to the Map of Managers that can access the inventory and send emails of and
     * from a particular restaurant.
     *
     * @param manager adds the Manager instance to the Map of Managers in a restaurant.
     */
    public void addManager(Manager manager) {
        managers.put(manager.getName(), manager);
        manager.setRestaurant(this);
    }
    
    /**
     * Returns the Receiver Object associated with the name.
     *
     * @param name The name of the Receiver
     * @return Returns the Receiver object
     */
    public Receiver getReceiver(String name) {
        return receivers.get(name);
    }
    
    /**
     * Adds the Receiver to the Map of Receiver that scan items and add ingredients to the inventory
     * of a restaurant.
     *
     * @param receiver adds the Receiver instance to the Map of Receiver in a restaurant.
     */
    public void addReceiver(Receiver receiver) {
        receivers.put(receiver.getName(), receiver);
        receiver.setRestaurant(this);
    }
    
    /**
     * Reports that the specified Order was placed, which records that its dishes need to be seen by
     * Cooks.
     *
     * @param order The Order to mark as placed
     */
    public void markOrderPlaced(Order order) {
        unseenDishes.addAll(order.getDishes().values());
    }
    
    /**
     * Returns the OrderDishes that have not been seen and still need to be prepared.
     *
     * @return the unseen dishes
     */
    public Set<OrderDish> getUnseenDishes() {
        return unseenDishes;
    }
    
    /**
     * Reports that the specified OrderDish has been seen by a Cook and is being prepared.
     *
     * @param dish The OrderDish to mark as seen
     */
    public void markDishSeen(OrderDish dish) {
        unseenDishes.remove(dish);
    }
    
    /**
     * This method is an indication that the order has been prepared and is ready for the server to
     * pick up and deliver to the customer. It appends the order to the list of readyOrders.
     *
     * @param order order that has been prepared and ready to deliver.
     */
    public void markOrderFilled(Order order) {
        readyOrders.add(order);
    }
    
    /**
     * Returns orders that have been prepared.
     *
     * @return the unseen orders list
     */
    public Set<Order> getReadyOrders() {
        return readyOrders;
    }
    
    /**
     * This method is an indication whether an order has been successfully delivered to a customer. It
     * removes that order from the readyOrders list.
     *
     * @param order order that has been delivered to a customer.
     */
    public void markOrderDelivered(Order order) {
        readyOrders.remove(order);
    }
    
    private class IngredientData {
        /**
         * The ingredient class is a class that keeps track of a particular ingredient's starting amount
         * that the restaurant currently has and the threshold value that the restaurant will be needing
         * to create a request if the amount goes below it. This is done to ensure that the restaurant
         * does not run out of a particular ingredient to ensure the well functioning of the restaurant.
         *
         * @param threshold the limiting factor to ensure that ingredients do not run out of.
         * @param startingAmount the amount that the restaurant currently has at that instant.
         */
        private int threshold;
        
        private int amount;
        
        private IngredientData(int threshold, int startingAmount) {
            this.threshold = threshold;
            amount = startingAmount;
        }
    }
    
}

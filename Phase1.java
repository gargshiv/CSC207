package restaurant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * This is the place where the Main Simulation of the entire program begins. It is controller that
 * determines the logical flow and sequence of the program. All other classes need instruction to be
 * given from this class.
 *
 * @author group_0236
 * @version %I%, %G%
 */
public class Phase1 {
    
    /**
     * Returns the specified input, modified to ensure that the Unicode byte order mark character is
     * absent from its front.
     *
     * @param input The input to process
     * @return The specified input without the byte order mark character at its front, if it was
     * present
     */
    private static String removeByteOrderMark(String input) {
        if (input.startsWith("\uFEFF")) {
            return input.substring(1);
        }
        return input;
    }
    
    /**
     * Returns the specified input String stripped of the specified prefix.
     *
     * @param input  The input
     * @param prefix The prefix that should be removed from the beginning of the input
     * @return The input with the prefix removed
     * @throws IOException If the input does not start with the prefix
     */
    private static String stripInputPrefix(String input, String prefix) throws IOException {
        if (input.startsWith(prefix)) {
            return input.substring(prefix.length());
        }
        throw new IOException();
    }
    
    /**
     * The main method of the Phase 1 program. It reads in information about a restaurant from several
     * input files, then reads through events.txt to simulate events occuring in the restaurant.
     *
     * @param args Unused
     * @see restaurant Contains the information about employees in the restaurant.
     */
    public static void main(String[] args) {
        //Get restaurant.txt information
        Scanner input;
        try {
            File restaurantFile = new File("restaurant.txt");
            input = new Scanner(restaurantFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find restaurant.txt");
            return;
        }
        List<String> lines = new ArrayList<>();
        while (input.hasNextLine()) {
            lines.add(input.nextLine());
        }
        input.close();
        if (lines.size() != 6) {
            System.out.println("Error: restaurant.txt has the wrong number of lines");
            return;
        }
        int numTables;
        String[] cookNames, serverNames, managerNames, receiverNames;
        int lineNum = 1;
        try {
            numTables = Integer.parseInt(stripInputPrefix(removeByteOrderMark(lines.get(0)), "Tables: "));
            lineNum = 2;
            cookNames = stripInputPrefix(lines.get(1), "Cooks: ").split(", ");
            lineNum = 3;
            serverNames = stripInputPrefix(lines.get(2), "Servers: ").split(", ");
            lineNum = 4;
            managerNames = stripInputPrefix(lines.get(3), "Managers: ").split(", ");
            lineNum = 5;
            receiverNames = stripInputPrefix(lines.get(4), "Receivers: ").split(", ");
            lineNum = 6;
            if (lines.get(5).length() > 0) {
                throw new IOException();
            }
        } catch (Exception e) {
            System.out.println("Error: Could not parse restaurant.txt line " + lineNum);
            return;
        }
        //Make restaurant and its menu
        Menu menu = new Menu();
        Restaurant restaurant = new Restaurant(menu, numTables);
        for (String cookName : cookNames) {
            restaurant.addCook(new Cook(cookName));
        }
        for (String serverName : serverNames) {
            restaurant.addServer(new Server(serverName));
        }
        for (String managerName : managerNames) {
            restaurant.addManager(new Manager(managerName));
        }
        for (String receiverName : receiverNames) {
            restaurant.addReceiver(new Receiver(receiverName));
        }
        //Add ingredients.txt information to restaurant
        try {
            File ingredientsFile = new File("ingredients.txt");
            input = new Scanner(ingredientsFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find ingredients.txt");
            return;
        }
        lineNum = 0;
        try {
            while (input.hasNextLine()) {
                //Parse ingredient block
                lineNum++;
                String ingredient = removeByteOrderMark(input.nextLine());
                lineNum++;
                int threshold = Integer.parseInt(stripInputPrefix(input.nextLine(), "Threshold: "));
                lineNum++;
                int startingAmount = Integer.parseInt(stripInputPrefix(input.nextLine(), "Starting: "));
                lineNum++;
                if (input.nextLine().length() > 0) {
                    throw new IOException();
                }
                restaurant.addIngredient(ingredient, threshold, startingAmount);
            }
        } catch (Exception e) {
            System.out.println("Error: Could not parse ingredients.txt line " + lineNum);
            return;
        }
        input.close();
        //Add menu.txt information to restaurant
        try {
            File menuFile = new File("menu.txt");
            input = new Scanner(menuFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find menu.txt");
            return;
        }
        lineNum = 0;
        try {
            while (input.hasNextLine()) {
                //Parse menu item block
                lineNum++;
                String itemName = removeByteOrderMark(input.nextLine());
                lineNum++;
                BigDecimal itemPrice = new BigDecimal(input.nextLine());
                MenuItem item = new MenuItem(itemName, itemPrice);
                lineNum++;
                String line = input.nextLine();
                while (line.length() > 0 && !line.equals("Mod")) {
                    //Parse ingredient information
                    int ingredientAmountEnd = line.indexOf(' ');
                    int ingredientAmount = Integer.parseInt(line.substring(0, ingredientAmountEnd));
                    String ingredient = line.substring(ingredientAmountEnd + 1);
                    item.setIngredientAmount(ingredient, ingredientAmount);
                    lineNum++;
                    line = input.nextLine();
                }
                while (line.length() > 0) {
                    //Parse mod sub-block
                    lineNum++;
                    String modName = input.nextLine();
                    List<String> modInfo = new ArrayList<>();
                    //Read in ingredient and price change information
                    lineNum++;
                    int infoStartNum = lineNum;
                    line = input.nextLine();
                    while (line.length() > 0 && !line.equals("Mod")) {
                        modInfo.add(line);
                        lineNum++;
                        line = input.nextLine();
                    }
                    //Extract price change
                    lineNum = infoStartNum + modInfo.size() - 1;
                    BigDecimal modPriceChange = new BigDecimal(modInfo.get(modInfo.size() - 1));
                    MenuItemMod mod = new MenuItemMod(modName, modPriceChange);
                    //Extract ingredient changes
                    lineNum = infoStartNum;
                    for (int i = 1; i < modInfo.size() - 1; i++) {
                        String infoLine = modInfo.get(i);
                        int ingredientChangeEnd = infoLine.indexOf(" ");
                        int ingredientChange = Integer.parseInt(infoLine.substring(0, ingredientChangeEnd));
                        String ingredient = infoLine.substring(ingredientChangeEnd + 1);
                        mod.setIngredientChange(ingredient, ingredientChange);
                        lineNum++;
                    }
                    lineNum++;
                    item.addMod(mod);
                }
                menu.addItem(item);
            }
        } catch (Exception e) {
            System.out.println("Error: Could not parse menu.txt line " + lineNum);
            return;
        }
        input.close();
        //Simulate events in events.txt
        try {
            File eventsFile = new File("events.txt");
            input = new Scanner(eventsFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find events.txt");
            return;
        }
        Map<Integer, Order> orders = new HashMap<>(); //Keep track of orders by number
        lineNum = 0;
        try {
            while (input.hasNextLine()) {
                //Parse event
                lineNum++;
                String event = removeByteOrderMark(input.nextLine());
                if (event.startsWith("Order ")) {
                    //Parse order to be placed
                    int orderID = Integer.parseInt(event.substring("Order ".length()));
                    lineNum++;
                    Table table =
                            restaurant.getTable(Integer.parseInt(stripInputPrefix(input.nextLine(), "Table: ")));
                    if (table == null) {
                        throw new IOException();
                    }
                    Order order = new Order(table);
                    lineNum++;
                    Server server = restaurant.getServer(stripInputPrefix(input.nextLine(), "Server: "));
                    if (server == null) {
                        throw new IOException();
                    }
                    lineNum++;
                    String line = input.nextLine();
                    while (line.length() > 0) {
                        //Parse dish sub-block
                        int dishID = Integer.parseInt(stripInputPrefix(line, "Dish "));
                        if (order.getDish(dishID) != null) {
                            throw new IOException();
                        }
                        lineNum++;
                        line = input.nextLine();
                        MenuItem item = menu.getItem(line);
                        if (item == null) {
                            throw new IOException();
                        }
                        OrderDish dish = new OrderDish(dishID, item);
                        lineNum++;
                        line = input.nextLine();
                        while (line.length() > 0 && !line.startsWith("Dish ")) {
                            //Parse modification
                            MenuItemMod mod = item.getMod(line);
                            if (mod == null) {
                                throw new IOException();
                            }
                            dish.addMod(mod);
                            lineNum++;
                            line = input.nextLine();
                        }
                        order.addDish(dish);
                    }
                    orders.put(orderID, order);
                    server.placeOrder(order);
                } else if (event.equals("Seen")) {
                    //Parse an order's dishes being seen by a cook
                    lineNum++;
                    Cook cook = restaurant.getCook(stripInputPrefix(input.nextLine(), "Cook: "));
                    if (cook == null) {
                        throw new IOException();
                    }
                    lineNum++;
                    Order order = orders.get(Integer.parseInt(stripInputPrefix(input.nextLine(), "Order: ")));
                    if (order == null) {
                        throw new IOException();
                    }
                    //Make the cook see the appropriate dishes
                    lineNum++;
                    String[] dishIDs = stripInputPrefix(input.nextLine(), "Dishes: ").split(", ");
                    for (String dishID : dishIDs) {
                        OrderDish dish = order.getDish(Integer.parseInt(dishID));
                        if (dish == null) {
                            throw new IOException();
                        }
                        cook.seeDish(dish);
                    }
                    lineNum++;
                    if (input.nextLine().length() > 0) {
                        throw new IOException();
                    }
                } else if (event.equals("Filled")) {
                    //Parse an order's dishes being filled by a cook
                    lineNum++;
                    Cook cook = restaurant.getCook(stripInputPrefix(input.nextLine(), "Cook: "));
                    if (cook == null) {
                        throw new IOException();
                    }
                    lineNum++;
                    Order order = orders.get(Integer.parseInt(stripInputPrefix(input.nextLine(), "Order: ")));
                    if (order == null) {
                        throw new IOException();
                    }
                    //Make the cook fill the appropriate dishes
                    lineNum++;
                    String[] dishIDs = stripInputPrefix(input.nextLine(), "Dishes: ").split(", ");
                    for (String dishID : dishIDs) {
                        OrderDish dish = order.getDish(Integer.parseInt(dishID));
                        if (dish == null || !cook.prepareDish(dish)) {
                            throw new IOException();
                        }
                    }
                    lineNum++;
                    if (input.nextLine().length() > 0) {
                        throw new IOException();
                    }
                } else if (event.equals("Delivered")) {
                    //Parse an order's dishes being delivered by a server
                    lineNum++;
                    Server server = restaurant.getServer(stripInputPrefix(input.nextLine(), "Server: "));
                    if (server == null) {
                        throw new IOException();
                    }
                    lineNum++;
                    Order order = orders.get(Integer.parseInt(stripInputPrefix(input.nextLine(), "Order: ")));
                    if (order == null) {
                        throw new IOException();
                    }
                    //Determine which dishes should be rejected by the table
                    Set<OrderDish> rejectedDishes = new HashSet<>();
                    lineNum++;
                    String line = input.nextLine();
                    if (line.length() > 0) { //Should any dishes be rejected at all?
                        String[] rejectedIDs = stripInputPrefix(line, "Rejected: ").split(", ");
                        for (String rejectedID : rejectedIDs) {
                            OrderDish dish = order.getDish(Integer.parseInt(rejectedID));
                            if (dish == null || !rejectedDishes.add(dish)) {
                                throw new IOException();
                            }
                        }
                        lineNum++;
                        if (input.nextLine().length() > 0) {
                            throw new IOException();
                        }
                    }
                    server.deliverOrder(order, rejectedDishes);
                } else if (event.equals("Paid")) {
                    //Parse a table paying for their food
                    lineNum++;
                    restaurant.getTable(Integer.parseInt(
                            stripInputPrefix(input.nextLine(), "Table: "))).payForDishes();
                    lineNum++;
                    if (input.nextLine().length() > 0) {
                        throw new IOException();
                    }
                } else if (event.equals("Checked")) {
                    //Parse a manager checking the inventory
                    lineNum++;
                    Manager manager = restaurant.getManager(stripInputPrefix(input.nextLine(), "Manager: "));
                    if (manager == null) {
                        throw new IOException();
                    }
                    manager.checkInventory();
                    lineNum++;
                    if (input.nextLine().length() > 0) {
                        throw new IOException();
                    }
                } else if (event.equals("Received")) {
                    //Parse a receiver receiving ingredients
                    lineNum++;
                    Receiver receiver = restaurant.getReceiver(stripInputPrefix(input.nextLine(), "Receiver: "));
                    if (receiver == null) {
                        throw new IOException();
                    }
                    lineNum++;
                    String line = input.nextLine();
                    while (line.length() > 0) {
                        //Parse ingredient information
                        int ingredientAmountEnd = line.indexOf(' ');
                        int ingredientAmount = Integer.parseInt(line.substring(0, ingredientAmountEnd));
                        String ingredient = line.substring(ingredientAmountEnd + 1);
                        receiver.receiveIngredients(ingredient, ingredientAmount);
                        lineNum++;
                        line = input.nextLine();
                    }
                } else {
                    throw new IOException();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Could not parse events.txt line " + lineNum);
            return;
        }
        input.close();
    }
    
}

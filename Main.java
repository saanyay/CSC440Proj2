import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
 
public class Main {
 
    private static final Random random = new Random();
    Scanner myObj = new Scanner(System.in);
 
    public static void main(String[] args) {
        int D = 15;
        String menuOption;

        menuOption = printMenu();

        while(true){
            if(menuOption.equals("1")){ // Bot 1
                // Set Up & Initialization
                Ship ship = new Ship(D);
                boolean needRecalculation = true;
                // Bot
                Bot_1 bot_1 = new Bot_1(ship);
                Cell botLocation = bot_1.placeBot();
                ship.setBot(botLocation.getX(), botLocation.getY());

                // Mouse
                Mouse mouse = new Mouse (ship);
                Cell mouseLocation = mouse.placeMouse();
                ship.setMouse(mouseLocation.getX(), mouseLocation.getY());
                mouseLocation.setLocation(mouseLocation.getX(), mouseLocation.getY());
                
                while(needRecalculation){
                    // Probability & Path
                    Cell hProbCell = ship.findHProbCell();
                    int timesteps = 0;
                    List<Cell> path = (bot_1.breadthFirstSearch(ship, hProbCell));

                    for(int i = 0; i < path.size(); i++){
                        System.out.println("=================================");
                        System.out.println("Bot Location: " + botLocation.toString());
                        ship.setBot(botLocation.getX(), botLocation.getY());
                        if(i != 0){
                            // Comment off lines 47 & 48 for a stationary mouse
                            Cell tempMouse = mouse.moveMouse(mouseLocation);
                            mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                            System.out.println("Mouse Location: " + mouseLocation.toString());
                        }
                        if(i == 0){
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println("Prob of bot location: " + ship.calculateProb(botLocation));
                            System.out.println("The cell w highest Prob: " + hProbCell.toString());
                            System.out.println("Mouse Location: " + mouseLocation.toString());
                            System.out.println("\nThe path's size: " + path.size() + "\n");
                        }
                        ship.printCompleteGrid();

                        if(path.size() > 0){
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            System.out.println();
                            botLocation.setLocation(path.get(i).getX(), path.get(i).getY());
                        }
                        else if(path.size() == 0){
                            System.out.println("There is no path from the bot to the mouse. (1)");
                            needRecalculation = true;
                        }
                        if(botLocation.getX() == ship.getMouse().getX() && botLocation.getY() == ship.getMouse().getY()){
                            ship.printCompleteGrid();
                            System.out.println("Bot reached the button (2).");
                            System.out.println("Bot reached the button in " + timesteps + " timesteps.");
                            needRecalculation = false;
                            break;
                        }
                    }
                }
                break;
            }
            if(menuOption.equals("2")){ // Bot 2
                // Set Up & Initialization
                Ship ship = new Ship(D);
                int alternateMoves = 1; 

                // Bot
                Bot_2 bot_2 = new Bot_2(ship);
                Cell botLocation = bot_2.placeBot();
                ship.setBot(botLocation.getX(), botLocation.getY());

                // Mouse
                Mouse mouse = new Mouse (ship);
                Cell mouseLocation = mouse.placeMouse();
                ship.setMouse(mouseLocation.getX(), mouseLocation.getY());
                mouseLocation.setLocation(mouseLocation.getX(), mouseLocation.getY());

                int timesteps = 0;

                

                while(true){
                    System.out.println("===============================");
                    System.out.println("Bot Location: " + botLocation.toString());
                    ship.setBot(botLocation.getX(), botLocation.getY());

                    // Alternate between bot moving & sensing 

                    if(alternateMoves % 2 == 0){ // Bot Moves

                        System.out.println("BOT SENSED, NOW MOVE TURN");

                        // Moving Mouse
                        Cell tempMouse = mouse.moveMouse(mouseLocation);
                        mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                        System.out.println("Mouse Location: " + mouseLocation.toString());

                        ship.printCompleteGrid();
                        if(mouseLocation.getX() == botLocation.getX() && mouseLocation.getY() == botLocation.getY()){
                            System.out.println("Bot has found the mouse in " + timesteps + " timesteps.");
                            break;
                        }
                        Cell hProbCell = ship.findHProbCell();
                        List<Cell> path = (bot_2.breadthFirstSearch(ship, hProbCell));
                        if(path.size() > 0){
                            System.out.println("Path size: " + path.size());
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println();
                            System.out.println("==============================");
                            botLocation.setLocation(path.get(0).getX(), path.get(0).getY());
                        }
                        else if (path.size() == 0){
                            System.out.println("There is no path from the bot to the button. ");
                            break;
                        }
                        alternateMoves++;
                    }
                    else if(alternateMoves % 2 == 1){ // Bot senses

                        System.out.println("BOT MOVED, NOW SENSE TURN");
                        
                        // Moving Mouse
                        Cell tempMouse = mouse.moveMouse(mouseLocation);
                        mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                        System.out.println("Mouse Location: " + mouseLocation.toString());

                        ship.printCompleteGrid();
                        if(mouseLocation.getX() == botLocation.getX() && mouseLocation.getY() == botLocation.getY()){
                            System.out.println("Bot has found the mouse in " + timesteps + " timesteps.");
                            break;
                        }
                        Cell hProbCell = ship.findHProbCell();
                        List<Cell> path = (bot_2.breadthFirstSearch(ship, hProbCell));
                        if(path.size() > 0){
                            System.out.println("Path size: " + path.size());
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println();
                            System.out.println("==============================");
                        }
                        else if (path.size() == 0){
                            System.out.println("There is no path from the bot to the button. ");
                            break;
                        }
                        if(botLocation.getX() == ship.getMouse().getX() && botLocation.getY() == ship.getMouse().getY()){
                            ship.printCompleteGrid();
                            System.out.println("Bot reached the button (2).");
                            System.out.println("Bot reached the button in " + timesteps + " timesteps.");
                            break;
                        }
                        alternateMoves++;
                    }
                }
                break;
            }
            
            /*
             * Bot 3 Implementation: Plan a path, move along the path similar to Bot 1,
             * when x steps away from hProbCell, start switching between sensing and moving as Bot 2 does
             */
            if(menuOption.equals("3")){ // Bot 3
                // Set Up & Initialization
                Ship ship = new Ship(D);
                int alternateMoves = 1; 
                boolean needRecalculation = true;
                boolean needDetection = true;

                // Bot
                Bot_3 bot_3 = new Bot_3(ship);
                Cell botLocation = bot_3.placeBot();
                ship.setBot(botLocation.getX(), botLocation.getY());

                // Mouse
                Mouse mouse = new Mouse (ship);
                Cell mouseLocation = mouse.placeMouse();
                ship.setMouse(mouseLocation.getX(), mouseLocation.getY());
                mouseLocation.setLocation(mouseLocation.getX(), mouseLocation.getY());

                int timesteps = 0;

                while(needRecalculation){
                    // Probability & Path
                    Cell hProbCell = ship.findHProbCell();
                    timesteps = 0;
                    List<Cell> path = (bot_3.breadthFirstSearch(ship, hProbCell));

                    for(int i = 0; i < path.size(); i++){
                        System.out.println("=================================");
                        System.out.println("Bot Location: " + botLocation.toString());
                        ship.setBot(botLocation.getX(), botLocation.getY());
                        if(i != 0){
                            // Comment off lines 47 & 48 for a stationary mouse
                            Cell tempMouse = mouse.moveMouse(mouseLocation);
                            mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                            System.out.println("Mouse Location: " + mouseLocation.toString());
                        }
                        if(i == 0){
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println("Prob of bot location: " + ship.calculateProb(botLocation));
                            System.out.println("The cell w highest Prob: " + hProbCell.toString());
                            System.out.println("Mouse Location: " + mouseLocation.toString());
                            System.out.println("\nThe path's size: " + path.size() + "\n");
                        }
                        ship.printCompleteGrid();

                        if(path.size() > 0){
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            System.out.println();
                            botLocation.setLocation(path.get(i).getX(), path.get(i).getY());
                        }
                        if(path.size() > 4 && botLocation.getX() == path.get(path.size() - 4).getX() && botLocation.getY() == path.get(path.size() - 4).getY()){
                            System.out.println("~~~ Switching to DETECTION MODE ~~~");
                            needDetection = true;
                            break;
                        }
                        else if(path.size() == 0){
                            System.out.println("There is no path from the bot to the mouse. (1)");
                            needRecalculation = true;
                        }
                        if(botLocation.getX() == ship.getMouse().getX() && botLocation.getY() == ship.getMouse().getY()){
                            ship.printCompleteGrid();
                            System.out.println("Bot reached the button (1).");
                            System.out.println("Bot reached the button in " + timesteps + " timesteps.");
                            needDetection = false;
                            needRecalculation = false;
                            break;
                        }
                    }
                    break;
                }

                while(needDetection){
                    System.out.println("===============================");
                    System.out.println("Bot Location: " + botLocation.toString());
                    ship.setBot(botLocation.getX(), botLocation.getY());

                    // Alternate between bot moving & sensing 

                    if(alternateMoves % 2 == 0){ // Bot Moves

                        System.out.println("BOT SENSED, NOW MOVE TURN");

                        // Moving Mouse
                        Cell tempMouse = mouse.moveMouse(mouseLocation);
                        mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                        System.out.println("Mouse Location: " + mouseLocation.toString());

                        ship.printCompleteGrid();
                        if(mouseLocation.getX() == botLocation.getX() && mouseLocation.getY() == botLocation.getY()){
                            System.out.println("Bot has found the mouse in " + timesteps + " timesteps.");
                            break;
                        }
                        Cell hProbCell = ship.findHProbCell();
                        List<Cell> path = (bot_3.breadthFirstSearch(ship, hProbCell));
                        if(path.size() > 0){
                            System.out.println("Path size: " + path.size());
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println();
                            System.out.println("==============================");
                            botLocation.setLocation(path.get(0).getX(), path.get(0).getY());
                        }
                        else if (path.size() == 0){
                            System.out.println("There is no path from the bot to the button. ");
                            break;
                        }
                        alternateMoves++;
                    }
                    else if(alternateMoves % 2 == 1){ // Bot senses

                        System.out.println("BOT MOVED, NOW SENSE TURN");
                        
                        // Moving Mouse
                        Cell tempMouse = mouse.moveMouse(mouseLocation);
                        mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
                        System.out.println("Mouse Location: " + mouseLocation.toString());

                        ship.printCompleteGrid();
                        if(mouseLocation.getX() == botLocation.getX() && mouseLocation.getY() == botLocation.getY()){
                            System.out.println("Bot has found the mouse in " + timesteps + " timesteps.");
                            break;
                        }
                        Cell hProbCell = ship.findHProbCell();
                        List<Cell> path = (bot_3.breadthFirstSearch(ship, hProbCell));
                        if(path.size() > 0){
                            System.out.println("Path size: " + path.size());
                            if(path.size() > timesteps){
                                timesteps = path.size();
                            }
                            for(Cell cell : path){
                                System.out.print(cell.toString() + " -> ");
                            }
                            System.out.println();
                            System.out.println("==============================");
                        }
                        else if (path.size() == 0){
                            System.out.println("There is no path from the bot to the button. ");
                            break;
                        }
                        if(botLocation.getX() == ship.getMouse().getX() && botLocation.getY() == ship.getMouse().getY()){
                            ship.printCompleteGrid();
                            System.out.println("Bot reached the button (2).");
                            System.out.println("Bot reached the button in " + timesteps + " timesteps.");
                            break;
                        }
                        alternateMoves++;
                    }
                }
                break;

            }
        }
    }

    private static String printMenu(){
        String menuOption = null;
        try{
            System.out.print("what bot: ");
            Scanner myObj = new Scanner(System.in);
            menuOption = myObj.nextLine();
            if(menuOption.equals("1") || menuOption.equals("2") || menuOption.equals("3") || menuOption.equals("4")){
                return menuOption;
            }
            else{
                throw new Exception("Invalid input, please try again. ");
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage() + "\n");
        }
        return menuOption;
    }

}

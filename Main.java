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
        int D = 7;
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

                    // Test Statements
                    // System.out.println("Prob of bot location: " + ship.calculateProb(botLocation));
                    // System.out.println("The cell w highest Prob: " + hProbCell.toString());
                    // System.out.println("Mouse Location: " + mouseLocation.toString());
                    
                    for(int i = 0; i < path.size(); i++){
                        System.out.println("================");
                        System.out.println("Bot Location: " + botLocation.toString());
                        ship.setBot(botLocation.getX(), botLocation.getY());
                        if(i != 0){
                            Cell tempMouse = mouse.moveMouse(mouseLocation);
                            mouseLocation.setLocation(tempMouse.getX(), tempMouse.getY());
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
                            System.out.println("Bot reached the button (2).");
                            System.out.println("Bot reached the button in " + timesteps + " timesteps.");
                            needRecalculation = false;
                            break;
                        }
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

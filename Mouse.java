import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mouse {

    private static final Random random = new Random();
    private int x;
    private int y;
    protected final Ship ship;

    public Mouse(Ship ship){
        this.ship = ship;
    }

    public Cell placeMouse(){
        List<Cell> openCells = new ArrayList<>();
        for(int i = 0; i < ship.getSize(); i++){
            for(int j = 0; j < ship.getSize(); j++){
                if(i != ship.getBot().getX() && j != ship.getBot().getY()){
                    if(ship.isOpenCell(i,j)){
                        openCells.add(new Cell(i,j));
                    }
                }
            }
        }
        Cell initialPosition = openCells.get(random.nextInt(openCells.size()));
        return initialPosition;
    }

    // Parameter: current mouse
    // Returns: new mouse
    public Cell moveMouse(Cell mouse){
        int randomNumber = random.nextInt(5) + 1;
        Cell newMouse = new Cell();

        if(randomNumber == 1){ // Move up
            if((mouse.getY() > 0) && (ship.isOpenCell(mouse.getX(), mouse.getY() - 1))){
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY() -1);
                ship.setMouse(newMouse.getX(), newMouse.getY());
                return newMouse;
            }
            else{
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY());
            }
        }
        if(randomNumber == 2){ // Move down
            if((mouse.getY() < ship.getSize() - 1) && (ship.isOpenCell(mouse.getX(), mouse.getY() + 1))){
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY() + 1);
                ship.setMouse(newMouse.getX(), newMouse.getY());
                return newMouse;
            }
            else{
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY());
            }
        }
        if(randomNumber == 3){ // Move Left
            if((mouse.getX() > 0) && (ship.isOpenCell(mouse.getX() - 1, mouse.getY()))){
                newMouse.setX(mouse.getX() - 1);
                newMouse.setY(mouse.getY());
                ship.setMouse(newMouse.getX(), newMouse.getY());
                return newMouse;
            }
            else{
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY());
            }
        }
        if(randomNumber == 4){ // Move Right
            if((mouse.getX() < ship.getSize() - 1) && (ship.isOpenCell(mouse.getX() + 1, mouse.getY()))){
                newMouse.setX(mouse.getX() + 1);
                newMouse.setY(mouse.getY());
                ship.setMouse(newMouse.getX(), newMouse.getY());
                return newMouse;
            }
            else{
                newMouse.setX(mouse.getX());
                newMouse.setY(mouse.getY());
            }
        }
        else{ // Stay in place
            newMouse.setX(mouse.getX());
            newMouse.setY(mouse.getY());
        }
        
        return newMouse;
    }

    
}

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot{

    private static final Random random = new Random();

    private int x;
    private int y;
    protected final Ship ship;

    public Bot(Ship ship){
        this.ship = ship;
    }

    public Cell placeBot(){
        List<Cell> openCells = new ArrayList<>();
        for(int i = 0; i < ship.getSize(); i++){
            for(int j = 0; j < ship.getSize(); j++){
                if(ship.isOpenCell(i,j)){
                    openCells.add(new Cell(i,j));
                }
            }
        }
        Cell initialPosition = openCells.get(random.nextInt(openCells.size()));
        return initialPosition;
    }

    public void moveBot(){
        Cell cell = new Cell(getX(), getY());
        List<Cell> neighbors = ship.getNeighbors(cell);
        List<Cell> openNeighbors = new ArrayList<>();

        for(Cell neighbor : neighbors){
            if(ship.isOpenCell(neighbor.getX(), neighbor.getY())){
                openNeighbors.add(neighbor);
            }
        }
        if(!openNeighbors.isEmpty()){
            Cell newPosition = openNeighbors.get(random.nextInt(openNeighbors.size()));
            this.x = newPosition.getX();
            this.y = newPosition.getY();
        }
    }

    public List<Cell> getBotNeighbors(Cell cell){
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        if ((x > 0) && (ship.isOpenCell(x - 1, y))){
            neighbors.add(new Cell(x - 1, y));
        }
        if ((x < ship.getSize() - 1) && (ship.isOpenCell(x + 1, y))){
            neighbors.add(new Cell(x + 1, y));
        }
        if ((y > 0) && (ship.isOpenCell(x, y - 1))){
            neighbors.add(new Cell(x, y - 1));
        }
        if ((y < ship.getSize() - 1) && (ship.isOpenCell(x, y + 1))){
            neighbors.add(new Cell(x, y + 1));
        }
        return neighbors;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}

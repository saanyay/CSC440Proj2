import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Ship{

    // Initialize variables
    private static final Random random = new Random();
    private String[][] grid;
    private int size = 5;
    private Cell bot = new Cell();
    private Cell mouse = new Cell();
    private Cell highestProbCell = new Cell();

    public Ship(int size){
        this.size = size;
        this.grid = new String[size][size];
        // Create a D x D grid of blocked cells
        createGrid();
        // Setup Open and Blocked cells per instructions
        startShip();
    }

    // Bot Getter
    public Cell getBot(){
        return bot;
    }

    // Bot Setter
    public void setBot(int x, int y) {
        this.bot.setX(x);
        this.bot.setY(y);
    }

    // Mouse Getter
    public Cell getMouse(){
        return mouse;
    }

    // Mouse Setter
    public void setMouse(int x, int y){
        this.mouse.setX(x);
        this.mouse.setY(y);
    }

    // Create a D x D grid of blocked cells
    private void createGrid(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                grid[i][j] = "blocked";
            }
        }
    }

    // Retrieving neighbors of cell as adjacent cells in up/down/left/right direction
    public List<Cell> getNeighbors(Cell cell){
        List<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        //Neighbors in order
        if(y > 0){
            neighbors.add(new Cell(x,y-1));
        }
        if(y < size-1){
            neighbors.add(new Cell(x, y+1));
        }
        if(x > 0){
            neighbors.add(new Cell(x-1,y));
        }
        if(x < size-1){
            neighbors.add(new Cell(x+1,y));
        }
        return neighbors;
    }

    // Choose a square in the interior to 'open' at random
    private void openRandomInteriorCell(){
        int x = random.nextInt(size-2)+1;
        int y = random.nextInt(size-2)+1;
        grid[x][y] = "open";
    }

    // Boolean if cell has one open neighbor
    private boolean hasOneOpenNeighbor(Cell cell){
        List<Cell> neighbors = getNeighbors(cell);
        int openNeighbors = 0;
        for(Cell neighbor : neighbors){
            if(grid[neighbor.getX()][neighbor.getY()].equals("open")){
                openNeighbors++;
            }
        }
        return openNeighbors == 1;
    }

    // Return list of blocked cells with one open neighbor
    private List<Cell> getBlockedCellsWOneOpenNeighbor(){
        List <Cell> blockedCells = new ArrayList<>();
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                Cell cell = new Cell(x,y);
                if(grid[x][y].equals("blocked") && hasOneOpenNeighbor(cell)){
                    blockedCells.add(new Cell(x, y));
                }
            }
        }
        return blockedCells;
    }

    // To open a cell
    private void openCell(int x, int y){
        grid[x][y] = "open";
    }

    // Return all dead ends
    private List<Cell> identifyDeadEnds(){
        List<Cell> deadEnds = new ArrayList<>();
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                Cell cell = new Cell(x,y);
                if(grid[x][y].equals("open") && hasOneOpenNeighbor(cell)){
                    deadEnds.add(new Cell(x,y));
                }
            }
        }
        return deadEnds;
    }

    // Open closed neighbor of a dead end at random
    private void openRandomNeighborOfDeadEnd(Cell cell){
        List<Cell> neighbors = getNeighbors(cell);
        List<Cell> closedNeighbors = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if(grid[cell.getX()][cell.getY()].equals("blocked")){
                closedNeighbors.add(neighbor);
            }
        }
        if(!closedNeighbors.isEmpty()){
            Cell chosenNeighbor = closedNeighbors.get(random.nextInt(closedNeighbors.size()));
            openCell(chosenNeighbor.getX(), chosenNeighbor.getY());
        }
    }

    // Generates Environment
    public void startShip(){
        openRandomInteriorCell();

        // Iterates and opens all blocked cells that have exactly one neighbor until none left
        while(true){
            List<Cell> blockedCells = getBlockedCellsWOneOpenNeighbor();
            if(blockedCells.isEmpty()){
                break;
            }
            else{
                Cell chosenCell = blockedCells.get(random.nextInt(blockedCells.size()));
                openCell(chosenCell.getX(), chosenCell.getY());

                // Update blockedCells list
                List<Cell> newBlockedCells = getBlockedCellsWOneOpenNeighbor();
                blockedCells.removeAll(blockedCells);
                blockedCells.addAll(newBlockedCells);
            }
        }

        // Takes half of set of dead ends and opens one of closed neighbors at random
        List<Cell> deadEnds = identifyDeadEnds();
        Collections.shuffle(deadEnds);
        for(int i = 0; i< deadEnds.size() / 2; i++){
            Cell deadEnd = deadEnds.get(i);
            openRandomNeighborOfDeadEnd(deadEnd);
        }
    }

    // Prints grid including bot, fire, and button
    public void printCompleteGrid() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (bot.getX() == x && bot.getY() == y) {
                    System.out.print("B ");
                }
                else if(mouse.getX() == x && mouse.getY() == y){
                    System.out.print("M ");
                }
                else {
                    System.out.print(grid[x][y].equals("open") ? ". " : "x ");
                }
            }
            System.out.println();
        }
    }

    // Checks if cell is open -- returns boolean
    public boolean isOpenCell(int x, int y){
        return grid[x][y].equals("open");
    }

    public int getSize(){
        return size;
    }

    public List<Cell> getOpenCells(){
        List<Cell> openCells = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (isOpenCell(i, j)) {
                    openCells.add(new Cell(i, j));
                }
            }
        }
        return openCells;
    }

    public String[][] getGrid(){
        return grid;
    }

    public String getGridCellString(int x, int y) {
        return grid[x][y];
    }

    // Returns a List of all openNeighbors given a position in the grid
    public List<Cell> getOpenNeighbors(Cell cell){
        List<Cell> openNeighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        if(x > 0 && isOpenCell(x-1,y)){
            openNeighbors.add(new Cell(x-1,y));
        }
        if(x < getSize()-1 && isOpenCell(x+1,y)){
            openNeighbors.add(new Cell(x+1,y));
        }
        if(y > 0 && isOpenCell(x,y-1)){
            openNeighbors.add(new Cell(x,y-1));
        }
        if(y < getSize()-1 && isOpenCell(x,y+1)){
            openNeighbors.add(new Cell(x,y+1));
        }

        return openNeighbors;
    }

    public Cell findHProbCell(){
        // Calculates probabilities of all cells in grid
        highestProbCell.setProb(0.0);
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if(isOpenCell(x,y)){
                    Cell currentCell = new Cell(x, y);
                    currentCell.setProb(calculateProb(currentCell));
                    // Finds the cell with the highest probability 
                    if(highestProbCell.getProb() < currentCell.getProb()){
                        highestProbCell.setX(currentCell.getX());
                        highestProbCell.setY(currentCell.getY());
                        highestProbCell.setProb(currentCell.getProb());
                    }
                }
            }
        }
        return highestProbCell;
    }

    public Cell getHProbCell(){
        return highestProbCell;
    }

    public double calculateProb(Cell cell){
        float manhattanDist = Math.abs(cell.getX() - mouse.getX()) + Math.abs(cell.getY() - mouse.getY());
        double alpha = 0.7;

        double result = Math.exp(-alpha * (manhattanDist - 1));
        return result;
    }

    public int calcFunction(int pathSize){
        int p;
        p = (int)((pathSize * 0.5) - 0.5);
        return p;
    }

    public void noBeepProbs(){
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if(isOpenCell(x,y)){
                    Cell currentCell = new Cell(x, y);
                    currentCell.setProb(calculateProb(currentCell));

                }
            }
        }
    }

}

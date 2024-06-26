
public class Cell {

    int x, y, dist;
    double probability;
    Cell parentCell;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, int dist, Cell parentCell){
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.parentCell = parentCell;
    }

    public Cell() {
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getProb(){
        return this.probability;
    }

    public void setProb(double prob){
        this.probability = prob;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDist() {
        return this.dist;
    }

    public void setDist(int x) {
        this.dist = x;
    }

    public Cell getParentCell() {
        return this.parentCell;
    }

    public void setParentCell(Cell x) {
        this.parentCell = x;
    }

    public boolean isOpenCell(Ship ship){
        return ship.getGrid()[x][y].equals("open");
    }

    @Override
    public String toString(){
        return "Cell: (" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object cell){

        if (cell == this) {
            return true;
        }

        if (!(cell instanceof Cell)) {
            return false;
        }

        Cell newCell = (Cell) cell;
        if((newCell.getX() == this.x) && (newCell.getY() == this.y)){
            return true;
        }
        else{
            return false;
        }
    }

}

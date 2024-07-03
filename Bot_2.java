import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Bot_2 extends Bot{

    public Bot_2(Ship ship){
        super(ship);
    }

    public List<Cell> breadthFirstSearch(Ship ship, Cell hProbCell){

        Cell bot = new Cell(ship.getBot().getX(), ship.getBot().getY());
        Cell dest = new Cell(hProbCell.getX(), hProbCell.getY());
        Queue <Cell> queue = new LinkedList<>();
        boolean visited[][] = new boolean[ship.getSize()][ship.getSize()];
        Cell[][] parent = new Cell[ship.getSize()][ship.getSize()];
        Cell[][] cells = new Cell[ship.getSize()][ship.getSize()];
        for (int i = 0; i < ship.getSize(); i++) {
            for (int j = 0; j < ship.getSize(); j++) {
                if (ship.isOpenCell(i,j)) {
                    cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null);
                }
                visited[i][j] = false;
            }
        }

        //Starting Node
        queue.add(cells[bot.getX()][bot.getY()]);
        visited[bot.getX()][bot.getY()] = true;
        parent[bot.getX()][bot.getY()] = null;
        Cell current = null;
        Cell previous = null;

        while(!queue.isEmpty()){

            // Assign current node & Moves to next cell in queue
            previous = current;
            current = queue.poll();

            // Check if bot = button
            if(current.equals(dest)){
                List<Cell> path = new ArrayList<>();
                Cell node = current;
                if (node.getParentCell() == null || node == null) {
                    Collections.reverse(path);
                    return path;
                }
                else {
                    while(node.getParentCell() != null){
                        path.add(node);
                        node = node.getParentCell();
                    }
                    Collections.reverse(path);
                    return path;
                }
            }
            //Add children of bot to queue
            else{
                for(Cell neighbor : getBotNeighbors(current)){
                    if((neighbor.isOpenCell(ship)) && visited[neighbor.getX()][neighbor.getY()] == false){
                        int dist = current.getDist() + 1;
                        if (dist < neighbor.getDist()) {
                            visited[neighbor.getX()][neighbor.getY()] = true;
                            cells[neighbor.getX()][neighbor.getY()].setDist(dist);
                            cells[neighbor.getX()][neighbor.getY()].setParentCell(cells[current.getX()][current.getY()]);
                            queue.add(cells[neighbor.getX()][neighbor.getY()]);
                        }
                    }
                }
            }
        }
        System.out.println("No path exists between bot and button.");
        return new ArrayList<>();
    }
}

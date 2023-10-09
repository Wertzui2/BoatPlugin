import java.nio.file.Path;
import java.util.*;

public class Pathfinder {/*

    private int numRows = 25; //TEMP
    private int numCols = 50; //TEMP
    private int[] target = new int[]{12,49};
    private int[] source = new int[]{12,0};
    int[][] grid = new int[numRows][numCols];
    //public boolean[][] closedListTest = new boolean[numRows][numCols];//TEMP
    Pathfinder(int numRows, int numCols, int[] source, int[] target, int[][] grid){
        this.numRows = numRows;
        this.numCols = numCols;
        this.source = source;
        this.target = target;
        this.grid = grid;
    }
    public static String arrayToString(int[] array){
        String output = "";
        output += "[";
        for(int i = 0; i < array.length; i++){
            output += (array[i]);
            if(i < array.length - 1)
                output += ", ";
        }
        output += "]";
        return output;
    }
    public void printGrid(){
        for(int i = 0; i < numRows; i++) {
            System.out.println(arrayToString(grid[i]));
        }
    }



    public void gridInit(){
        for(int i = 0; i < numRows;i++){
            for(int j = 0; j < numCols;j++){
                Random rand = new Random();
                if(rand.nextFloat() > 0.3)
                    grid[i][j] = 1;
                else grid[i][j] = 0;
            }
        }
    }



    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }
    public boolean isValid(int row, int col){
        return (row >= 0) && (row < numRows) && (col >= 0) && (col < numCols);
    }
    public boolean isUnBlocked(int row, int col){
        if(grid[row][col]==1){
            return true;
        }
        else return false;
    }
    public boolean targetReached(int row, int col){
        if(target[0] == row && target[1] == col)
            return true;
        else return false;
    }
    public int calculateH(int row, int col){
        return Math.abs(row - target[0])+Math.abs(col-target[1]);
    }
    public void tracePath(Cell[][] cellDetails, int[] target) {
        System.out.println("The Path is ");
        int row = target[0];
        int col = target[1];

        // stack<Pair> Path;
        Stack<int[]> Path = new Stack();

        while (!(cellDetails[row][col].getParent_row() == row && cellDetails[row][col].getParent_col() == col)) {
            Path.push(new int[]{row, col});
            int temp_row = cellDetails[row][col].getParent_row();
            int temp_col = cellDetails[row][col].getParent_col();
            row = temp_row;
            col = temp_col;
        }

        Path.push(new int[]{row, col});
        while (Path.size() > 0) {
            int[] p = Path.get(0);
            Path.pop();

            if(p[0] == 2 || p[0] == 1){
                System.out.println("-> (" + p[0] + ", " + (p[1] - 1) + ")");
            }
            System.out.println("-> (" + p[0] + ", " + p[1] + ")");
        }

        return;
    }

    public boolean aStarSearch(){ //returns whether path is possible
        //gridInit();
        //printGrid();
        if (isValid(source[0], source[1]) == false) {
            System.out.println("Source is invalid");
            return false;
        }
        // If the destination is out of range
        if (isValid(target[0], target[1]) == false) {
            System.out.println("Destination is invalid");
            return false;
        }

        // Either the source or the destination is blocked
        if (isUnBlocked(source[0], source[1]) == false || isUnBlocked(target[0], target[1]) == false) {
            System.out.println("Source or the destination is blocked");
            return false;
        }
        if(targetReached(source[0],source[1])==true){
            System.out.println("We are already at the destination");
            return true;
        }
        boolean[][] closedList = new boolean[numRows][numCols];

        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                closedList[i][j] = false;
            }
        }
        Cell[][] cellDetails = new Cell[numRows][numCols];
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                cellDetails[i][j] = new Cell();
                cellDetails[i][j].setF(2147483647);
                cellDetails[i][j].setG(2147483647);
                cellDetails[i][j].setH(2147483647);
                cellDetails[i][j].setParent_row(-1);
                cellDetails[i][j].setParent_col(-1);

            }
        }
        cellDetails[source[0]][source[1]].setF(0);
        cellDetails[source[0]][source[1]].setG(0);
        cellDetails[source[0]][source[1]].setH(0);
        cellDetails[source[0]][source[1]].setParent_row(source[0]);
        cellDetails[source[0]][source[1]].setParent_col(source[1]);

        Queue<int[]> openList = new PriorityQueue<>(new NodeComparator());
        openList.add(new int[]{0,source[0],source[1]});

        boolean foundTarget = false;

        while(openList.size() > 0){
            int[] q = openList.poll();
            //System.out.println(q[0] + ", " + q[1] + ", " + q[2]);
            int i = q[1];
            int j = q[2];
            closedList[i][j] = true;

            int gNew,hNew,fNew;
            //NORTH
            if(isValid(i-1,j)){
                //System.out.println("North valid");
                if(targetReached(i-1,j)){
                    cellDetails[i - 1][j].setParent_row(i);
                    cellDetails[i - 1][j].setParent_col(j);
                    //tracePath(cellDetails,target);
                    return true;
                }
                else if(closedList[i-1][j] == false && isUnBlocked(i-1,j)){
                    //System.out.println("North unBlocked");
                    gNew = cellDetails[i][j].getG()+1;
                    hNew = calculateH(i-1,j);
                    fNew = gNew + hNew;

                    if(cellDetails[i-1][j].getF()==2147483647 || cellDetails[i-1][j].getF() > fNew){
                        openList.add(new int[]{fNew,i-1,j});

                        cellDetails[i-1][j].setF(fNew);
                        cellDetails[i-1][j].setG(gNew);
                        cellDetails[i-1][j].setH(hNew);
                        cellDetails[i-1][j].setParent_row(i);
                        cellDetails[i-1][j].setParent_col(j);

                    }
                }
            }
            //SOUTH
            if(isValid(i+1,j)){
                if(targetReached(i+1,j)){
                    cellDetails[i + 1][j].setParent_row(i);
                    cellDetails[i + 1][j].setParent_col(j);
                    foundTarget = true;
                    //tracePath(cellDetails,target);
                    return true;
                }
                else if(closedList[i+1][j] == false && isUnBlocked(i+1,j)){
                    gNew = cellDetails[i][j].getG()+1;
                    hNew = calculateH(i+1,j);
                    fNew = gNew + hNew;

                    if(cellDetails[i+1][j].getF()==2147483647 || cellDetails[i+1][j].getF() > fNew){
                        openList.add(new int[]{fNew,i+1,j});

                        cellDetails[i+1][j].setF(fNew);
                        cellDetails[i+1][j].setG(gNew);
                        cellDetails[i+1][j].setH(hNew);
                        cellDetails[i+1][j].setParent_row(i);
                        cellDetails[i+1][j].setParent_col(j);

                    }
                }
            }
            //EAST
            if(isValid(i,j+1)){
                if(targetReached(i,j+1)){
                    cellDetails[i][j+1].setParent_row(i);
                    cellDetails[i][j+1].setParent_col(j);
                    foundTarget = true;
                    //tracePath(cellDetails,target);
                    return true;
                }
                else if(closedList[i][j+1] == false && isUnBlocked(i,j+1)){
                    gNew = cellDetails[i][j].getG()+1;
                    hNew = calculateH(i,j+1);
                    fNew = gNew + hNew;

                    if(cellDetails[i][j+1].getF()==2147483647 || cellDetails[i][j+1].getF() > fNew){
                        openList.add(new int[]{fNew,i,j+1});

                        cellDetails[i][j+1].setF(fNew);
                        cellDetails[i][j+1].setG(gNew);
                        cellDetails[i][j+1].setH(hNew);
                        cellDetails[i][j+1].setParent_row(i);
                        cellDetails[i][j+1].setParent_col(j);

                    }
                }
            }
            //WEST
            if(isValid(i,j-1)){
                if(targetReached(i,j-1)){
                    cellDetails[i][j-1].setParent_row(i);
                    cellDetails[i][j-1].setParent_col(j);
                    foundTarget = true;
                   // tracePath(cellDetails,target);
                    return true;
                }
                else if(closedList[i][j-1] == false && isUnBlocked(i,j-1)){
                    gNew = cellDetails[i][j].getG()+1;
                    hNew = calculateH(i,j-1);
                    fNew = gNew + hNew;

                    if(cellDetails[i][j-1].getF()==2147483647 || cellDetails[i][j-1].getF() > fNew){
                        openList.add(new int[]{fNew,i,j-1});

                        cellDetails[i][j-1].setF(fNew);
                        cellDetails[i][j-1].setG(gNew);
                        cellDetails[i][j-1].setH(hNew);
                        cellDetails[i][j-1].setParent_row(i);
                        cellDetails[i][j-1].setParent_col(j);

                    }
                }
            }

        }
        //tracePath(cellDetails,target);
        return false;



    }


*/
}

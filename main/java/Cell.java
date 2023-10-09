public class Cell {
    private int f;
    private int g;
    private int h;
    private int parent_row;
    private int parent_col;

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getParent_col() {
        return parent_col;
    }

    public int getParent_row() {
        return parent_row;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setParent_col(int parent_col) {
        this.parent_col = parent_col;
    }

    public void setParent_row(int parent_row) {
        this.parent_row = parent_row;
    }

    public Cell(){
        this.f=0;
        this.g=0;
        this.h=0;
        this.parent_row=0;
        this.parent_col=0;

    }
}

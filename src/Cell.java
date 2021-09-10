import java.util.ArrayList;
import java.util.List;

public class Cell implements NotifyCells {
    private String exp;
    private int value;
    private UpdateCells updateCells;
    private List<Cell> cellsToNotify;

    public Cell(String exp, UpdateCells updateCells) {
        this.exp = exp;
        this.value = 0;
        this.updateCells = updateCells;
        cellsToNotify = new ArrayList<>();
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Cell> getCellsToNotify() {
        return cellsToNotify;
    }

    public void addCellsToNotify(Cell cell) {
        this.cellsToNotify.add(cell);
    }

    public void setCellsToNotify(List<Cell> list) {
        this.cellsToNotify = list;
    }

    public void notifyCells() {
        for (Cell cell : cellsToNotify) {
            cell.updateCells();
        }
    }

    @Override
    public void updateCells() {
        updateCells.evaluate(this);
    }
}

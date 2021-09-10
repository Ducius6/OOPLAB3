import java.util.ArrayList;
import java.util.List;

public class Sheet implements UpdateCells {
    private Cell[][] sheet;
    int x;
    int y;

    public Sheet(int x, int y) {
        sheet = new Cell[x][y];
        this.x = x;
        this.y = y;
    }

    public Cell cell(String ref) {
        Point p = Point.calculatePosition(ref);
        return sheet[p.getX()][p.getY()];
    }

    public void set(String ref, String content) {
        Point p = Point.calculatePosition(ref);
        Cell c = new Cell(content, this);
        if (sheet[p.getX()][p.getY()] != null) {
            Cell oldCell = sheet[p.getX()][p.getY()];
            c.setCellsToNotify(oldCell.getCellsToNotify());
        }
        sheet[p.getX()][p.getY()] = c;
        evaluate(c);
        List<Cell> refs = getRefs(c);
        for (Cell cell : refs) {
            cell.addCellsToNotify(c);
        }
        c.notifyCells();
    }

    public List<Cell> getRefs(Cell cell) {
        List<Cell> resultList = new ArrayList<>();
        String[] refs = cell.getExp().split("\\+");
        for (String cellRef : refs) {
            try {
                Integer.parseInt(cellRef);
            } catch (NumberFormatException ex) {
                Point p = Point.calculatePosition(cellRef);
                resultList.add(sheet[p.getX()][p.getY()]);
            }
        }
        return resultList;
    }

    public void evaluate(Cell cell) {
        int value = eval(cell, new ArrayList<>());
        cell.setValue(value);
    }

    private int eval(Cell cell, List<Cell> cellList) {
        int result = 0;
        try {
            return Integer.parseInt(cell.getExp());
        } catch (NumberFormatException ex) {
            for (Cell childCell : getRefs(cell)) {
                for (Cell c : cellList) {
                    if (childCell == c) throw new RuntimeException("Circulating equation NOT GOOD!");
                }
                cellList.add(cell);
                result += eval(childCell, cellList);
                cellList.remove(childCell);
            }
            return result;
        }
    }

    public void print() {
        for (int i = 0; i < x; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < y; j++) {
                if (sheet[i][j] == null) stringBuilder.append("x").append(" ");
                else stringBuilder.append(sheet[i][j].getValue()).append(" ");
            }
            System.out.println(stringBuilder);
        }
    }
}

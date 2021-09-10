public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point calculatePosition(String str) {
        char rowChar = str.charAt(0);
        int rowNum = rowChar - 65;
        String columnString = str.substring(1);
        int columnNum = Integer.parseInt(columnString) - 1;
        return new Point(rowNum, columnNum);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

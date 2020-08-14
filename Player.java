import java.util.Scanner;

public class Player implements Inputtable {
    private static Scanner scan = new Scanner(System.in);
    static Position lastPos = new Position();

    private String name;
    private int numWin;

    public Player() {
        this(scan.nextLine());
    }

    public Player(String name) {
        this.name = name;
        this.numWin = 0;
    }

    public String getName() {
        return name;
    }

    public int getNumWin() {
        return numWin;
    }

    public void setNumWin(int val) {
        numWin = val;
    }

    @Override
    public void getKeyboardInput() {
        int M = Gomoku.board.length;
        int N = Gomoku.board[0].length;
        int x = -1, y = -1;
        boolean isInvalid = false;
        while (!((x >= 0 && x < M) && (y >= 0 && y < N))) {
            if (isInvalid) {
                System.out.println("Please put valid coordinate:");
            }
            String line = scan.nextLine();
            String[] split = line.split(" ");
            if ((split[0].matches("^[qQ]$"))) {
                Gomoku.isQuit = true;
                return;
            } else {
                if (split.length > 2) {
                    isInvalid = true;
                    continue;
                } else if (split.length == 1 && !split[0].matches("[0-9]+")) {
                    isInvalid = true;
                    continue;
                } else if (split.length == 2 && (!split[0].matches("[0-9]+") || !split[1].matches("[0-9]+"))) {
                    isInvalid = true;
                    continue;
                }
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
                if (Gomoku.board[x][y] != '.') {
                    x = -1;
                    y = -1;
                    isInvalid = true;
                    continue;
                }
                Gomoku.board[x][y] = (this == Gomoku.getInstance().getPlayer1()) ? 'O' : 'X';
                lastPos.setX(x);
                lastPos.setY(y);
            }
        }
    }
}

import java.util.Scanner;

public class Player implements Inputtable {
    private static Scanner scan = new Scanner(System.in);

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

    @Override
    public void getKeyboardInput() {
        int M = Gomoku.board.length;
        int N = Gomoku.board[0].length;
        int x = -1, y = -1;
        while (!((x >= 0 && x < M) && (y >= 0 && y < N))) {
            String line = scan.nextLine();
            System.out.println(line);
            String[] split = line.split(" ");
            if ((split[0].matches("^[qQ]$"))) {
                Gomoku.isQuit = true;
                return;
            } else {
                if (split.length > 2) {
                    continue;
                } else if (split.length == 1 && !split[0].matches("[0-9]+")) {
                    continue;
                } else if (split.length == 2 && (!split[0].matches("[0-9]+") || !split[1].matches("[0-9]+"))) {
                    continue;
                }
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);

                Gomoku.board[x][y] = (this == Gomoku.getInstance().getPlayer1()) ? 'O' : 'X';
            }
        }
    }
}

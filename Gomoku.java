public class Gomoku implements Simulatable, Winnable, Playable, Printable {
    private static Gomoku gomoku = new Gomoku();

    static boolean isQuit = false;
    static char[][] board = new char[15][15];
    static private Position[] directions = new Position[]{new Position(0, -1), new Position(-1, -1),
                                                    new Position(1, 0), new Position(-1, 1)};
    private Player player1, player2;

    private Gomoku() {}

    public static Gomoku getInstance() {
        return gomoku;
    }

    @Override
    public void play(Player player, Position pos) {
        printStatus();
        player.getKeyboardInput();
        if (Gomoku.isQuit) {
            isFinished();
            return;
        }
        if (threeByThreeCondition()) {
            if (player == gomoku.player2) {
               gomoku.player1.setNumWin(gomoku.player1.getNumWin()+1);
            } else {
                gomoku.player2.setNumWin(gomoku.player2.getNumWin()+1);
            }
        }
        if (winCondition(pos)) {
            System.out.println(player.getName() + "'s Win!!!");
            player.setNumWin(player.getNumWin()+1);
            reset();
        }
    }

    @Override
    public void printStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j <= board[0].length; j++) {
            if (j == 0) {
                stringBuilder.append("   ");
            } else {
                stringBuilder.append(String.format("%2d", j-1)).append(" ");
            }
        }
        stringBuilder.append('\n');

        for (int i = 0; i < board.length; i++) {
            stringBuilder.append(String.format("%2d", i)).append(" ");
            for (int j = 0; j < board[0].length; j++) {
                stringBuilder.append(" ").append(board[i][j]).append(" ");
            }
            stringBuilder.append('\n');
        }
        System.out.print(stringBuilder.toString());
    }

    @Override
    public void initialize() {
        System.out.println("Put player1's name: ");
        gomoku.setPlayer1(new Player());

        System.out.println("Put player2's name: ");
        gomoku.setPlayer2(new Player());
    }

    @Override
    public void isFinished() {
        System.out.println(player1.getName() + " " + player1.getNumWin() +
                " - " + player2.getName() + " " + player2.getNumWin());
        if (getWinner() != null) {
            System.out.println(getWinner().getName() + " is Winner!, Congrats!!!");
        } else {
            System.out.println("Draw");
        }
        System.out.println("-- The End. --");
    }

    @Override
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '.';
            }
        }
    }

    @Override
    public Player getWinner() {
        return (player1.getNumWin() > player2.getNumWin()) ? player1 : ((player1.getNumWin() == player2.getNumWin()) ? null : player2);
    }

    private boolean boundaryCheck(int x, int y) {
        return ((x >= 0 && x < Gomoku.board.length) && (y >= 0 && y < Gomoku.board[0].length));
    }

    private boolean winCondition(Position p) {
        System.out.println(p.getX() + " " + p.getY());
        int maxCount = 0;
        int px = p.getX(), py = p.getY();
        char stone = Gomoku.board[px][py];

        for (Position d: directions) {
            int count = 1;
            int dx = d.getX(), dy = d.getY();
            for (int cx = px+dx, cy = py+dy; boundaryCheck(cx, cy) &&
                    (stone == Gomoku.board[cx][cy]); cx += dx, cy += dy) {
                if (stone == Gomoku.board[cx][cy]) count++;
            }

            for (int cx = px-dx, cy = py-dy; boundaryCheck(cx, cy) &&
                    (stone == Gomoku.board[cx][cy]); cx -= dx, cy -= dy) {
                if (stone == Gomoku.board[cx][cy]) count++;
            }
            maxCount = Math.max(maxCount, count);
        }
        return maxCount == 5;
    }

    private boolean threeByThreeCondition() {
        return false;
    }

    public void setPlayer1(Player player1) {
        gomoku.player1 = player1;
    }

    public Player getPlayer1() {
        return gomoku.player1;
    }

    public void setPlayer2(Player player2) {
        gomoku.player2 = player2;
    }

    public Player getPlayer2() {
        return gomoku.player2;
    }
}

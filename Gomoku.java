public class Gomoku implements Simulatable, Winnable, Playable, Printable {
    private static Gomoku gomoku = new Gomoku();

    static boolean isQuit = false;
    private Player player1, player2;
    static char[][] board = new char[15][15];

    private Gomoku() {}

    public static Gomoku getInstance() {
        return gomoku;
    }

    @Override
    public void play(Player player, Position pos) {
        player.getKeyboardInput();
        if (Gomoku.isQuit) {
            isFinished();
            return;
        }
        printStatus();
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
    }

    @Override
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '.';
            }
        }
        gomoku.getPlayer1().setNumWin(0);
        gomoku.getPlayer2().setNumWin(0);
    }

    @Override
    public Player getWinner() {
        return null;
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

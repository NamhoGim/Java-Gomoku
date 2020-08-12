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
        printStatus();
    }

    @Override
    public void printStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                stringBuilder.append(chars[j]);
            }
            stringBuilder.append('\n');
        }
        System.out.print(stringBuilder.toString());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void isFinished() {

    }

    @Override
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
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

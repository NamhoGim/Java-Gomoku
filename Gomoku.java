import java.util.HashSet;

public class Gomoku implements Simulatable, Winnable, Playable, Printable {
    private static final Gomoku gomoku = new Gomoku();
    private static final Position[] directions = new Position[]{new Position(0, -1), new Position(-1, -1),
                                                                new Position(-1, 0), new Position(1, -1)};
    private static final String[] pattern1 = new String[]{".OOO..", "..OOO.", ".O.OO.", ".OO.O."};
    private static final String[] pattern2 = new String[]{".XXX..", "..XXX.", ".X.XX.", ".XX.X."};

    static boolean isQuit = false;
    static char[][] board = new char[15][15];

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
            printStatus();
            System.out.println("33 입니다.");

            Player winner;
            if (player == player2) {
                winner = player1;
                player1.setNumWin(player1.getNumWin()+1);
            } else {
                winner = player2;
                player2.setNumWin(player2.getNumWin()+1);
            }
            System.out.println(winner.getName() + "의 승리 !!!");
            System.out.println(winner.getNumWin() + "번 이겼다!");
            reset();
            return;
        }

        if (winCondition(pos)) {
            printStatus();
            System.out.println(player.getName() + "의 승리 !!!");
            player.setNumWin(player.getNumWin()+1);
            System.out.println(player.getNumWin() + "번 이겼다!");
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
        System.out.println("Player1의 이름을 입력하세요: ");
        gomoku.setPlayer1(new Player());

        System.out.println("Player2의 이름을 입력하세요: ");
        gomoku.setPlayer2(new Player());
    }

    @Override
    public void isFinished() {
        System.out.println(player1.getName() + " " + player1.getNumWin() +
                " - " + player2.getName() + " " + player2.getNumWin());
        if (getWinner() != null) {
            System.out.println(getWinner().getName() + "님이 최종 승리자 입니다. 축하합니다!!");
        } else {
            System.out.println("무승부");
        }
        System.out.println("-- 게임 종료. --");
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
        if (player1.getNumWin() > player2.getNumWin()) return player1;
        return (player1.getNumWin() == player2.getNumWin()) ? null : player2;
    }

    private boolean boundaryCheck(int x, int y) {
        return ((x >= 0 && x < board.length) && (y >= 0 && y < board[0].length));
    }

    private boolean winCondition(Position p) {
        return checkFiveSequence(p);
    }

    private boolean checkFiveSequence(Position p) {
        int maxCount = 0;
        int px = p.getX(), py = p.getY();
        char stone = board[px][py];

        for (Position d : directions) {
            int count = 1;
            int dx = d.getX(), dy = d.getY();
            for (int cx = px + dx, cy = py + dy; boundaryCheck(cx, cy) &&
                    (stone == board[cx][cy]); cx += dx, cy += dy) {
                if (stone == board[cx][cy]) count++;
            }

            for (int cx = px - dx, cy = py - dy; boundaryCheck(cx, cy) &&
                    (stone == board[cx][cy]); cx -= dx, cy -= dy) {
                if (stone == board[cx][cy]) count++;
            }
            maxCount = Math.max(maxCount, count);
        }
        return maxCount == 5;
    }

    private boolean threeByThreeCondition() {
        return false;
    }

    private boolean threeByThreeCondition(Position currPos) {
        for (Position d1: directions) {
            HashSet<Position> set = getOpenThree(currPos, d1);
            if (set.size() == 0) {
                continue;
            }
            System.out.println("check!");
            for (Position pos : set) {
                System.out.println(pos);
                for (Position d2 : directions) {
                    if (d1.getX() != d2.getX() || d1.getY() != d2.getY()) {
                        if (isIncludedOpenThree(currPos, pos, d2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private HashSet<Position> getOpenThree(Position pos, Position dir) {
        HashSet<Position> result = new HashSet<>();
        patternCollect(pos, dir, result);
        return result;
    }

    private boolean isIncludedOpenThree(Position pos, Position candi, Position dir) {
        int x = pos.getX(), y = pos.getY();
        String[] patterns = (board[x][y] == 'O') ? pattern1 : pattern2;

        if (candi.isBlank()) { // EAEAAE, EAAEAE
            if (patternMatch(candi, dir, patterns[2], 2) != null) {
                return true;
            } else return patternMatch(candi, dir, patterns[3], 3) != null;
        }

        for (int i = 0; i < 4; i++) {
            if (patternMatch(candi, dir, patterns[i], i) != null) {
                return true;
            }
        }

        return false;
    }

    private void patternCollect(Position pos, Position dir, HashSet<Position> set) {
        int x = pos.getX(), y = pos.getY();
        String[] patterns = (board[x][y] == 'O') ? pattern1 : pattern2;
        for (int i = 0; i < patterns.length; i++) {
            Position left = patternMatch(pos, dir, patterns[i], i);
            if (left != null) {
                if (i == 0 || i == 1) {
                    addToSet(set, left, dir, 3);
                } else {
                    addToSet(set, left, dir, 4);
                }
            }
        }
    }

    private Position patternMatch(Position pos, Position dir, String pattern, int i) {
        Position result;
        if (!pos.isBlank()) {
            switch (i) {
                //case 0: // EAAAEE
                //    for (int j = 1; j <= 3; j++) {
                //        if (compareWithPattern(pos, dir, pattern, j)) {
                //            System.out.println(pattern);
                //            result.setX(pos.getX() + dir.getX() * (j - 1));
                //            result.setY(pos.getY() + dir.getY() * (j - 1));
                //            return result;
                //        }
                //    }
                //    break;
                case 1: // EEAAAE
                    for (int j = 2; j <= 4; j++) {
                        if (compareWithPattern(pos, dir, pattern, j)) {
                            result = new Position();
                            result.setX(pos.getX() + dir.getX() * (j - 2));
                            result.setY(pos.getY() + dir.getY() * (j - 2));
                            return result;
                        }
                    }
                    break;
                //case 2: // EAEAAE
                //    for (int j = 1; j <= 4; j++) {
                //        if (j != 2 && compareWithPattern(pos, dir, pattern, j)) {
                //            System.out.println(pattern);
                //            result.setX(pos.getX() + dir.getX() * (j - 1));
                //            result.setY(pos.getY() + dir.getY() * (j - 1));
                //            return result;
                //        }
                //    }
                //    break;
                //case 3: // EAAEAE
                //    for (int j = 1; j <= 4; j++) {
                //        if (j != 3 && compareWithPattern(pos, dir, pattern, j)) {
                //            System.out.println(pattern);
                //            result.setX(pos.getX() + dir.getX() * (j - 1));
                //            result.setY(pos.getY() + dir.getY() * (j - 1));
                //            return result;
                //        }
                //    }
                //    break;
            }
        }
        //else {
        //    if (i == 2 && compareWithPattern(pos, dir, pattern, 2)) {
        //        System.out.println(pattern);
        //        result.setX(pos.getX() + dir.getX());
        //        result.setY(pos.getY() + dir.getY());
        //        return result;
        //    } else if (i == 3 && compareWithPattern(pos, dir, pattern, 3)) {
        //        System.out.println(pattern);
        //        result.setX(pos.getX() + dir.getX()*2);
        //        result.setY(pos.getY() + dir.getY()*2);
        //        return result;
        //    }
        //}
        return null;
    }

    private boolean compareWithPattern(Position pos, Position dir, String pattern, int offset) {
        System.out.println(pattern);
        int x = pos.getX(), y = pos.getY();
        int dx = dir.getX(), dy = dir.getY();
        int cx = x + offset*dx, cy = y + offset*dy;
        System.out.println("start: " + cx + ", " + cy);
        for (int i = 0; boundaryCheck(cx, cy) && i < pattern.length(); cx -= dx, cy -= dy, i++) {
            System.out.println(cx + ", " + cy + "," + board[cx][cy]);
            if (board[cx][cy] != pattern.charAt(i)) return false;
        }
        return true;
    }

    private void addToSet(HashSet<Position> set, Position left, Position dir, int len) {
        int x = left.getX(), y = left.getY();
        int dx = dir.getX(), dy = dir.getY();
        for (int i = 0; boundaryCheck(x, y) && i < len; i++, x -= dx, y -= dy ) {
            set.add(new Position(x, y, (board[x][y] == '.')));
        }
    }

    public void setPlayer1(Player player1) {
        gomoku.player1 = player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        gomoku.player2 = player2;
    }

    public Player getPlayer2() {
        return player2;
    }
}

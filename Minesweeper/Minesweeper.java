package Minesweeper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A class for the Minesweeper game.
 *
 * @author Adrian Carpio
 */
public class Minesweeper {
    Coords dim;
    char[][] board;
    // 0-8 represents bombs, F represents flag, B represents bomb, # is unselected.
    Coords[] bombs;
    ArrayList<Coords> flags = new ArrayList<>();
    Random rng;

    /**
     * Initialises a new Minesweeper instance with a seeded {@link Random} instance,
     * and predetermined bombs.
     * @throws IllegalArgumentException if the number of bombs is
     * greater than or equal to the total number of tiles in the board.
     */
    public Minesweeper(
            int width, int height, Coords[] bombs, long seed
    ) throws IllegalArgumentException {
        if (width * height <= bombs.length) {
            throw new IllegalArgumentException(
                    "Minesweeper: too many bombs! " + bombs.length
            );
        }
        this.dim = new Coords(width,height);
        this.board = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(this.board[i], '#');
        }

        this.bombs = bombs;
        this.rng = new Random(seed);
    }

    /**
     * Initialises a new Minesweeper instance with an unseeded {@link Random} instance,
     * and predetermined bombs.
     * @throws IllegalArgumentException if the number of bombs is
     * greater than or equal to the total number of tiles in the board.
     */
    public Minesweeper(
            int width, int height, Coords[] bombs
    ) throws IllegalArgumentException {
        if (width * height <= bombs.length) {
            throw new IllegalArgumentException(
                    "Minesweeper: too many bombs! " + bombs.length
            );
        }
        this.dim = new Coords(width,height);
        this.board = new char[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(this.board[i], '#');
        }
        this.bombs = bombs;
        this.rng = new Random();
    }

    public static Minesweeper newGame(
            int width, int height, int numBombs, long seed
    ) throws IllegalArgumentException {
        return new Minesweeper(width, height, new Coords[numBombs], seed);
    }
    public static Minesweeper newGame(
            int width, int height, int numBombs
    ) throws IllegalArgumentException {
        return new Minesweeper(width, height, new Coords[numBombs]);
    }

    public boolean isValidCoords(Coords p) {
        return !((p.x < 0 || p.x >= dim.x) || (p.y < 0 || p.y >= dim.y));
    }

    public boolean isFlagged(Coords p) {
        return flags.contains(p);
    }

    public boolean isSelected(Coords p) {
        return board[p.y][p.x] != '#';
    }

    public char getValue(Coords p) {
        return board[p.y][p.x];
    }

    /**
     * A helper function to determine if two {@link Coords} are adjacent.
     */
    private static boolean isAdjacent(Coords lhs, Coords rhs) {
        int dx = Math.abs(lhs.x - rhs.x);
        int dy = Math.abs(lhs.y - rhs.y);
        return (dx <= 1) && (dy <= 1);
    }

    /**
     * Generates the list of valid coordinates adjacent to {@code p}.
     */
    public List<Coords> getAdjacent(Coords p) {
        List<Coords> coordsList = new ArrayList<>();
        Coords[] potentialCoords = new Coords[] {
                new Coords(p.x - 1, p.y - 1),
                new Coords(p.x, p.y - 1),
                new Coords(p.x + 1, p.y - 1),
                new Coords(p.x - 1, p.y),
                new Coords(p.x + 1, p.y),
                new Coords(p.x - 1, p.y + 1),
                new Coords(p.x, p.y + 1),
                new Coords(p.x + 1, p.y + 1),
        };
        for (Coords c : potentialCoords) {
            if(isValidCoords(c)) {
                coordsList.add(c);
            }
        }
        return coordsList;
    }

    /**
     * Determines the tile number of a valid coordinate by counting the number
     * of adjacent bombs.
     */
    private int getTileNumber(Coords p) {
        List<Coords> adjacentCoords = getAdjacent(p);
        int count = 0;
        for (Coords c : adjacentCoords) {
            if (Arrays.asList(bombs).contains(c)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Generates the bombs, making sure none generate in position
     * {@code firstPlay}
     * @param firstPlay the first tile the player selects.
     */
    private void generateBombs(Coords firstPlay) {
        if (bombs[0] != null) { // bombs already generated
            return;
        }

        ArrayList<Coords> newBombs = new ArrayList<>();
        while (newBombs.size() < bombs.length) {
            Coords curCoords = new Coords(
                    rng.nextInt(0,dim.x),rng.nextInt(0,dim.y)
            );
            if (newBombs.contains(curCoords) || curCoords.equals(firstPlay)) {
                continue;
            }
            newBombs.add(curCoords);
        }
        this.bombs = newBombs.toArray(this.bombs);
    }

    /**
     * Places a flag on a given tile, if valid. If the given tile
     * is already flagged, remove the flag.
     * @param p The coordinates of the tile to flag.
     * @return true if the flag is placed, false if removed.
     * @throws IllegalArgumentException if {@code p} is invalid.
     */
    public boolean toggleFlag(Coords p)
    throws IllegalArgumentException {
        if (!isValidCoords(p)) {
            throw new IllegalArgumentException(
                    "Minesweeper.toggleFlag: Invalid placement! " + p
            );
        }

        if (flags.remove(p)) {
            board[p.y][p.x] = '#';
            return false;
        }
        flags.add(p);
        board[p.y][p.x] = 'F';
        return true;
    }

    /**
     * Recursively exposes all the tile adjacent to {@code p},
     * provided that {@code p} is a zero tile.
     */
    private void exposeZeroTiles(Coords p) {
        int bombCount = getTileNumber(p);
        board[p.y][p.x] = Character.forDigit(bombCount,10);
        if (bombCount > 0) {
            return;
        }
        for (Coords c : getAdjacent(p)) {
            if (isFlagged(c) || isSelected(c)) {
                continue;
            }
            exposeZeroTiles(c);
        }
    }

    /**
     * Selects a tile and returns its value, if valid. If the tile was
     * previously unselected, exposes it as per the rules of Minesweeper.
     * If the selected tile is a bomb, ends the game.
     * @param p THe coordinates of the tile to select.
     * @throws IllegalArgumentException if {@code p} is invalid.
     */
    public char selectTile(Coords p)
    throws IllegalArgumentException {
        if (!isValidCoords(p)) {
            throw new IllegalArgumentException(
                    "Minesweeper.selectTile: Invalid placement! " + p
            );
        }
        if (bombs[0] == null) { // bombs not yet generated
            generateBombs(p);
        }

        if (Arrays.asList(bombs).contains(p)) {
            endGame();
            return 'B';
        }

        if (flags.contains(p)) { // selecting flags are "safe"
            return 'F';
        }

        int bombCount = getTileNumber(p);
        board[p.y][p.x] = Character.forDigit(bombCount, 10);
        if (bombCount == 0) {
            exposeZeroTiles(p);
        }
        return Character.forDigit(bombCount, 10);

    }

    /**
     * Returns true if every non-bomb tile is exposed; flagged tiles do not count
     * as exposed.
     */
    public boolean isFinished() {
        int unexposedCount = 0;
        for (int y = 0; y < dim.y; y++) {
            for (int x = 0; x < dim.x; x++) {
                if (board[y][x] == '#' || board[y][x] == 'F' || board[y][x] == 'B') {
                    unexposedCount++;
                }
            }
        }
        return unexposedCount <= bombs.length;
    }

    /**
     * Ends the game by exposing all tiles.
     */
    public void endGame() {
        for (int y = 0; y < dim.y; y++) {
            for (int x = 0; x < dim.x; x++) {
                Coords curCoords = new Coords(x,y);
                if (Arrays.asList(bombs).contains(curCoords)) {
                    board[y][x] = 'B';
                    continue;
                }
                board[y][x] = Character.forDigit(
                        getTileNumber(curCoords),10
                );
            }
        }
    }

    private static String coloured(char c) {
        Map<Character,String> charToColour = new HashMap<>();
        charToColour.put('0', TermColours.GREY);
        charToColour.put('1', TermColours.BLUE);
        charToColour.put('2', TermColours.GREEN);
        charToColour.put('3', TermColours.RED);
        charToColour.put('4', TermColours.PURPLE);
        charToColour.put('5', TermColours.YELLOW);
        charToColour.put('6', TermColours.ORANGE);
        charToColour.put('7', TermColours.CYAN);
        charToColour.put('8', TermColours.WHITEBOLD);
        charToColour.put('B', TermColours.REDBG);
        charToColour.put('F', TermColours.REDBOLD);
        charToColour.put('#', "");


        return charToColour.get(c) + c + TermColours.RESET;
    }

    @Override
    public String toString() {
        List<String> rowStrings = new ArrayList<>();
        for (int y = 0; y < dim.y; y++) {
            List<String> curRow = new ArrayList<>();
            for (char c : board[y]) {
                curRow.add(coloured(c));
            }
            rowStrings.add(String.join(" ", curRow));
        }
        return String.join("\n", rowStrings);
    }

    public static void main(String[] args) {
        Minesweeper a = newGame(15,14,25);
        a.selectTile(new Coords(8,8));
        a.endGame();
        System.out.println(a);
    }

}

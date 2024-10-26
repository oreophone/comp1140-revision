package Minesweeper;

import java.util.ArrayList;
import java.util.Arrays;

public class Coords {
    public int x;
    public int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a list of coordinates from a list of x values and y values. Matches
     * values by index until one list is exhausted.
     */
    public static Coords[] toCoordsList(int[] xs, int[] ys) {
        ArrayList<Coords> returnList = new ArrayList<>();
        for (int i = 0; i < Math.min(xs.length,ys.length); i++) {
            returnList.add(new Coords(xs[i], ys[i]));
        }
        return returnList.toArray(new Coords[0]);
    }

    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof Coords rhsCoords)) {
            return false;
        }
        return (rhsCoords.x == this.x) && (rhsCoords.y == this.y);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
}

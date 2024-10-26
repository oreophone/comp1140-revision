package Minesweeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MinesweeperTest {
    public Minesweeper m;

    @BeforeEach
    public void setUp() {
        m = new Minesweeper(
                4, 5, Coords.toCoordsList(
                        new int[] {0,3,3}, new int[] {0,0,2})
        );
    }

    @Test
    public void getAdjacentTest() {
        Coords t1 = new Coords(0,0);
        Coords t2 = new Coords(2,3);
        Coords t3 = new Coords (3,3);
        Assertions.assertArrayEquals(Coords.toCoordsList(
                new int[] {1,0,1}, new int[] {0,1,1}
        ), m.getAdjacent(t1).toArray());
        Assertions.assertArrayEquals(Coords.toCoordsList(
                new int[] {1,2,3,1,3,1,2,3}, new int[] {2,2,2,3,3,4,4,4}
        ), m.getAdjacent(t2).toArray());
        Assertions.assertArrayEquals(Coords.toCoordsList(
                new int[] {2,3,2,2,3}, new int[] {2,2,3,4,4}
        ), m.getAdjacent(t3).toArray());
    }

    @Test
    public void selectTileTest() {
        Coords t1 = new Coords(0,1); // 1
        Coords t2 = new Coords(3,1); // 2
        Coords t3 = new Coords(1,2); // zero
        Coords z1 = new Coords(1,4); // zero
        Coords z2 = new Coords(1,2); // zero
        Coords u1 = new Coords(0,0); // zero
        Coords u2 = new Coords(0,1); // zero
        Coords f1 = new Coords(3,0); // zero
        m.selectTile(t1);
        m.selectTile(t2);
        m.selectTile(t3);
        m.toggleFlag(f1);
        Assertions.assertEquals('1', m.getValue(t1), "selectTile: t1");
        Assertions.assertEquals('2', m.getValue(t2), "selectTile: t2");
        Assertions.assertEquals('0', m.getValue(t3), "selectTile: t3");
        Assertions.assertEquals('0', m.getValue(z1), "selectTile: z1");
        Assertions.assertEquals('0', m.getValue(z2), "selectTile: z2");
        Assertions.assertEquals('#', m.getValue(u1), "selectTile: u1");
        Assertions.assertEquals('1', m.getValue(u2), "selectTile: u2");
        Assertions.assertEquals('F', m.getValue(f1), "selectTile: f1");
    }

    @Test
    public void isFinishedTest() {
        m.endGame();
        Assertions.assertTrue(m.isFinished());
    }

}

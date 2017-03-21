package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Gonzalez on 3/20/2017.
 */
public class TilePlacerTest {
    GameBoard map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
    }

    @Test
    public void testPlacementFromBottomRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testPlacementFromBottomRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testPlacementFromTopRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testPlacementFromTopRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromTop);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testPlacementFromTopLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testPlacementFromBottomLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
    }

    @Test
    public void testAtLeastOneEdgeTouchesEdgeOfPreviouslyPlacedTile() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Jungle, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,0)));

        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 3),
                new Coordinate(1,-1), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-2)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Grasslands, 4),
                new Coordinate(-1,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,-1)));
    }

    @Test
    public void testTileIsNotPlacedIfNoEdgeTouchesPreviouslyPlacedTile() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 2),
                new Coordinate(0,-2), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1, -2)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,-1)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-2,0)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Jungle, 3),
                new Coordinate(3,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,3)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(3,0)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(2,0)));
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
    }
}
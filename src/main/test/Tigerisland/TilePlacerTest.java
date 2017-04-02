package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/20/2017.
 */
public class TilePlacerTest {
    GameBoard map;

    //TODO  refactor this test using what Dave said

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
    }

    @Test
    public void testPlacementFromBottomRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testPlacementFromBottomRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testPlacementFromTopRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testPlacementFromTopRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromTop);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testPlacementFromTopLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testPlacementFromBottomLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
    }

    @Test
    public void testAtLeastOneEdgeTouchesEdgeOfPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.JUNGLE, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,0)));

        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS, 3),
                new Coordinate(1,-1), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-2)));

        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.GRASS, 4),
                new Coordinate(-1,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,-1)));
    }

    @Test
    public void testTileIsNotPlacedIfNoEdgeTouchesPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(0,-2), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1, -2)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,-1)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-2,0)));

        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.JUNGLE, 3),
                new Coordinate(3,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,3)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(3,0)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(2,0)));
    }

    @Test
    public void testNukeIncreasesHexLevel() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS, 3),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(),2);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getLevel(),2);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getLevel(),2);
    }

    @Test
    public void testNukeOverwritesTerrainTypeOfHexes() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS, 3),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.ROCK);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.GRASS);
    }

    @Test
    public void testVolcanoIsPlacedOnTopOfAnotherVolcano() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(1,1), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.LAKE, 3),
                new Coordinate(1,0), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.ROCK);
    }

    @Test
    public void testTileIsNotPerfectlyOnTopOfOtherTile() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.LAKE);
    }

    @Test
    public void testTileCompletelyCoversHexesBeneathIt() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(1,0)));

        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 3),
                new Coordinate(1,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(1,0)));

        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.LAKE);
    }

    @Test
    public void testNukingIsNotDoneIfItReducesAnySettlementSizeToZero() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(0,1));
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 3),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.GRASS);
        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(1,1)).getTerrainType(),
                TerrainType.ROCK);
    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTotoro() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.gameBoard.get(new Coordinate(0,1)).placeTotoro();
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 3),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 1);

    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTiger() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.gameBoard.get(new Coordinate(0,1)).placeTiger();
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK, 3),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 1);
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
        map.getPlayer().resetScoreAndInventory();
    }
}
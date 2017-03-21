package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    protected static Map<Coordinate, Hex> gameBoard = new HashMap<>();

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        TilePlacer placer = new TilePlacer();
        placer.placeTile(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    //TODO add checks so that tile is is not placed perfectly on top of another
    //TODO checks so that tile is only placed on full ground
}

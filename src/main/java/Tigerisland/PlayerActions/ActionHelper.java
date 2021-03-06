package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.Game;
import Tigerisland.Orientation;

public class ActionHelper {
    protected final int sidesOfAHex = 6;
    public Coordinate leftOfMainTerrainCoordinate;
    public Coordinate mainTerrainCoordinate;
    public Coordinate rightOfMainTerrainCoordinate;
    public Coordinate[] surroundingCoordinates;
    protected Orientation orientation;
    private int currentTerrainXCoordinate;
    private int currentTerrainYCoordinate;

    public void determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation() {
        switch (orientation) {
            case FromBottom:
                leftOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromBottomRight:
                leftOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = belowAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTopRight:
                leftOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTop:
                leftOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheRightOfMain(mainTerrainCoordinate);
                break;
            case FromTopLeft:
                leftOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = overAndToTheLeftOfMain(mainTerrainCoordinate);
                break;
            case FromBottomLeft:
                leftOfMainTerrainCoordinate = belowAndToTheLeftOfMain(mainTerrainCoordinate);
                rightOfMainTerrainCoordinate = toTheLeftOfMain(mainTerrainCoordinate);
                break;
        }
    }

    public Coordinate belowAndToTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate + 1);
    }

    public Coordinate belowAndToTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate + 1);
    }

    public Coordinate toTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate);
    }

    public Coordinate overAndToTheRightOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate + 1, currentTerrainYCoordinate - 1);
    }

    public Coordinate overAndToTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate, currentTerrainYCoordinate - 1);
    }

    public Coordinate toTheLeftOfMain(Coordinate terrainCoordinate) {
        updateXAndYCoordinateOfCurrentTerrain(terrainCoordinate);
        return new Coordinate(currentTerrainXCoordinate - 1, currentTerrainYCoordinate);
    }

    protected void updateXAndYCoordinateOfCurrentTerrain(Coordinate terrainCoordinate) {
        this.currentTerrainXCoordinate = terrainCoordinate.getXCoordinate();
        this.currentTerrainYCoordinate = terrainCoordinate.getYCoordinate();
    }

    public void findCounterClockwiseCoordinatesAroundCoordinate(Coordinate terrainCoordinate) {
        surroundingCoordinates = new Coordinate[sidesOfAHex];
        surroundingCoordinates[0] = belowAndToTheLeftOfMain(terrainCoordinate);
        surroundingCoordinates[1] = belowAndToTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[2] = toTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[3] = overAndToTheRightOfMain(terrainCoordinate);
        surroundingCoordinates[4] = overAndToTheLeftOfMain(terrainCoordinate);
        surroundingCoordinates[5] = toTheLeftOfMain(terrainCoordinate);
    }

    public void setMainCoordinateAndOrientation(Coordinate c, Orientation o) {
        mainTerrainCoordinate = c;
        orientation = o;
    }

}

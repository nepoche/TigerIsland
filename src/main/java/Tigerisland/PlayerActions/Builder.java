package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Builder {

    protected static ActionHelper locator = new ActionHelper();
    private final int pointsForTigerPlacement = 75;
    public Set<Coordinate> visitedCoordinates;
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected Coordinate coordinate;
    protected TerrainType terrainType;
    protected int settlementID;
    protected Set<Integer> differentSettlementIDsAroundCoordinate;
    protected int possiblePointsAdded;
    protected int possibleVillagersPlaced;
    protected Player player;
    protected HashMap<Integer, Settlement> settlements;
    public HashMap<Integer, Settlement> getSettlements(){
        return this.settlements;
    }
    public void setSettlements(HashMap<Integer, Settlement> settlements){
        this.settlements = settlements;
    }

    public HashMap<Coordinate, Hex> getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(HashMap<Coordinate, Hex> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void foundNewSettlement() {
        foundNewSettlement(coordinate);
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void foundNewSettlement(Coordinate coordinate) {
        gameBoard.get(coordinate).placeVillagers();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        settlements.put(settlementID, new Settlement(coordinate));
//        player.addSettlement(new Settlement(coordinate));
        player.addPoints(1);
        player.useVillagers(1);
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate)
            mergeSettlementsIntoASingleSettlement(id);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void getDifferentSettlementIDsAroundCoordinate(Coordinate coordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        differentSettlementIDsAroundCoordinate = new HashSet<>();

        for (Coordinate neighborCoordinate : locator.surroundingCoordinates) {
            if (terrainContainsAPiece(neighborCoordinate)
                    && settlements.containsKey(gameBoard.get(neighborCoordinate).getSettlementID())){
                differentSettlementIDsAroundCoordinate.add(gameBoard.get(neighborCoordinate).getSettlementID());
            }
        }
    }

    private void mergeSettlementsIntoASingleSettlement(int id) {
        if (id != settlementID) {
            for (Coordinate coordinatesToBeMoved : settlements.get(id).bfs()) {
                gameBoard.get(coordinatesToBeMoved).setSettlementID(settlementID);
                settlements.get(settlementID).addToSettlement(coordinatesToBeMoved);
            }
            removeSettlementThatWasMerged(id);
        }
    }

    public boolean terrainContainsAPiece(Coordinate terrainCoordinate) {
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }

    private void removeSettlementThatWasMerged(int id) {
        settlements.remove(id);
    }

    public void expandSettlement() {
        findCoordinatesOfPossibleSettlementExpansion();
        completeSettlementExpansion();
        mergeSettlementsThatCanBeMerged();
    }

    public void placeTotoro() {
        placeTotoroAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    protected void findIdOfSettlementTotoroCouldBeAdjacentTo() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlements.get(id).getSize() >= 5
                    && !settlements.get(id).hasTotoro()) {
                settlementID = id;
                return;
            }
        }
        settlementID = -1;
    }

    protected void findIdOfSettlementTigerCouldBeAdjacentTo() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (!settlements.get(id).hasTiger()) {
                settlementID = id;
                return;
            }
        }
        settlementID = -1;
    }

    public void placeTiger() {
        placeTigerAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private void placeTigerAtGivenCoordinate() {
        findIdOfSettlementTigerCouldBeAdjacentTo();
        gameBoard.get(coordinate).placeTiger();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        getPlayer().addPoints(pointsForTigerPlacement);
        getPlayer().useTiger();
    }

    private void setSettlementIdToPieceBeingPlaced(int id) {
        settlementID = id;
    }

    private void placeTotoroAtGivenCoordinate() {
        findIdOfSettlementTotoroCouldBeAdjacentTo();
        gameBoard.get(coordinate).placeTotoro();
        gameBoard.get(coordinate).setSettlementID(settlementID);
        settlements.get(settlementID).addToSettlement(coordinate);
        settlements.get(settlementID).placeTotoro();
        player.useTotoro();
        player.addPoints(200);
    }

    public void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
    }

    public void processParameters(Coordinate coordinate, TerrainType terrainType) {
        this.settlementID = gameBoard.get(coordinate).getSettlementID();
        this.terrainType = terrainType;
        possibleVillagersPlaced = 0;
        possiblePointsAdded = 0;
    }

    public void findCoordinatesOfPossibleSettlementExpansion() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private void completeSettlementExpansion() {
        for (Coordinate coordinateToExpand : visitedCoordinates) {
            gameBoard.get(coordinateToExpand).placeVillagers();
            gameBoard.get(coordinateToExpand).setSettlementID(settlementID);
            settlements.get(settlementID).addToSettlement(coordinateToExpand);
        }
        getPlayer().useVillagers(possibleVillagersPlaced);
        getPlayer().addPoints(possiblePointsAdded);
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        for (Coordinate coordinateInSettlement : settlements.get(settlementID).bfs()) {
            locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinateInSettlement);
            expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
        }
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : locator.surroundingCoordinates)
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(neighborCoordinate);
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(neighborCoordinate)
                && !gameBoard.get(neighborCoordinate).hasVillager();
    }

    private void expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(Coordinate neighborCoordinate) {
        markCoordinateAsVisited(neighborCoordinate);
        locator.findCounterClockwiseCoordinatesAroundCoordinate(neighborCoordinate);
        expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
    }

    private void markCoordinateAsVisited(Coordinate neighborCoordinate) {
        visitedCoordinates.add(neighborCoordinate);
        updateScoreAndPlayersInventory(neighborCoordinate);
    }

    private void updateScoreAndPlayersInventory(Coordinate neighborCoordinate) {
        int level = gameBoard.get(neighborCoordinate).getLevel();
        possiblePointsAdded += level * level;
        possibleVillagersPlaced += level;
    }
}
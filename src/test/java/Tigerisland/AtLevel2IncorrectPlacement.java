//package Tigerisland;
//
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
//import java.lang.Error;
//
//public class AtLevel2IncorrectPlacement {
//    App app = new App();
//
//    @Given("^that the board has at least (\\d+) tiles from game start$")
//    public void that_the_board_has_at_least_tiles_from_game_start(int arg1) throws Throwable {
//        app.map.getBoard().clear();
//        if(!app.isEmptyBoard()){
//            throw new Error("The Board already has tiles");
//        }
//        app.updateCurrentTile("Grassland", "Grassland");
//        app.placeTile("FromBottom",0,0);
//        if(!app.doesTileExist(0, 0)){
//            throw new Error("couldn't place tile at (0, 0)");
//        }
//        app.updateCurrentTile("Grassland", "Grassland");
//        app.placeTile("FromBottom", 1,-2);
//        if(!app.doesTileExist(1, -2)){
//            throw new Error("couldn't place tile at (1, -2)");
//        }
//    }
//    @When("^the player places a tile on level (\\d+) at a invalid level two location$")
//    public void the_player_places_a_tile_on_level_at_a_invalid_level_two_location(int level) throws Throwable {
//        app.updateCurrentTile("LAKE", "Grassland");
//    }
//
//    @When("^the level two tile's origin will be at an incorrect coordinate \\((-?\\d+),(-?\\d+)\\) with orientation \"([^\"]*)\"$")
//    public void the_level_two_tile_s_origin_will_be_at_an_incorrect_coordinate_with_orientation(int x, int y, String orientation) throws Throwable {
//        app.placeTile(orientation, x, y);
//    }
//
//    @Then("^the gameboard should not accept the tile at \\((-?\\d+),(-?\\d+)\\) at level (\\d+)$")
//    public void the_gameboard_should_not_accept_the_tile_at_at_level(int coordinateX, int coordinateY, int level) throws Throwable {
//        if((app.returnLevel(coordinateX,coordinateY)==level)){
//            throw new Error("Tile was placed on the level");
//        }
//    }
//}

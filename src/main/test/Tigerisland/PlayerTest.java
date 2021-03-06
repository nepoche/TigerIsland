package Tigerisland;

import org.junit.*;

public class PlayerTest {
    static Player player = new Player();
    @Test
    public void getPoints() throws Exception{
        Assert.assertTrue(player.getPoints() == 0);
    }
    @Test
    public void addPlayerPoints(){
        player.addPoints(50);
        Assert.assertTrue(player.getPoints() == 50);
    }
    @Test
    public void placedVillagers1(){
        player.useVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 15);
    }
    @Test
    public void placedTotoro1(){
        player.useTotoro();
        Assert.assertTrue((player.getNumberOfTotoroLeft() == 2));
    }
    @Test
    public void placedTiger1() {
        player.useTiger();
        Assert.assertTrue(player.getNumberOfTigersLeft() == 1);
    }
    @Test
    public void placedVillagers2() {
        player.useVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 10);
    }
    @Test
    public void placedTotoro2(){
        player.useTotoro();
        Assert.assertTrue((player.getNumberOfTotoroLeft() == 1));
    }
    @Test
    public void placedTiger2() {
        player.useTiger();
        Assert.assertTrue(player.getNumberOfTigersLeft() == 0);
    }
}

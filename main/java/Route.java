import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Route {
    private Player owner;
    private Location locOfSign1;
    private Location locOfSign2;

    public Player getOwner() {
        return owner;
    }

    public Location getLocOfSign1() {
        return locOfSign1;
    }

    public Location getLocOfSign2() {
        return locOfSign2;
    }

    public Route(Location locOfSign1, Location locOfSign2, Player owner){
        this.owner = owner;
        this.locOfSign1 = locOfSign1;
        this.locOfSign2 = locOfSign2;
        owner.sendMessage("Route has been successfully created");
    }
    /*
    private int routeNumber;
    private int time = -1; //seconds
    private int totalDist = -1;
    private int price = -1;
    private int boatType = -1; //0 slowest, 2 fastest
    private ArrayList<Location> searched = new ArrayList<Location>();
    private Location start;
    private Location end;
    public int getPrice() {
        return price;
    }
    public int getTotalDist() {
        return totalDist;
    }
    public Route(Location sign1, Location sign2, int boatType){
            MyListener.setLinkCode("");
            start = MyListener.nearestWater(sign1);
            end = MyListener.nearestWater(sign2);
            Bukkit.broadcastMessage("( " + start.getBlockX() + ", " + start.getBlockY() + ", " + start.getBlockZ() + " )");
            Bukkit.broadcastMessage("( " + end.getBlockX() + ", " + end.getBlockY() + ", " + end.getBlockZ() + " )");
            if (canCreateRoute(start, end)) {
                Bukkit.broadcastMessage("Route has been created");
                BoatPlugin.routes.add(this);
                routeNumber = BoatPlugin.routes.size() - 1;
                this.boatType = boatType;
                totalDist = (int) Math.sqrt((end.getBlockX() - start.getBlockX()) ^ 2 + (end.getBlockY() - start.getBlockY()) ^ 2);
                price = (totalDist / 50) * (boatType + 1);
                time = totalDist / 25 * (boatType + 1);
            }
            else{
                Bukkit.broadcastMessage("Route has failed");
            }
    }


    private boolean canCreateRoute(Location start, Location end) { //REMOVE FOR LATER (BAD)
        int numRows = Math.abs(start.getBlockX() - end.getBlockX());
        int numCols = Math.abs(start.getBlockZ() - end.getBlockZ());
        Chunk chunk = start.getChunk();

        for (int row = numRows; row >= 0; row--) {

            for (int column = 0; column < numCols; column++) {
               // if (new Location(start.getWorld(), start.getBlockX() - 1 - N / 2 + (row), start.getBlockY(), start.getBlockZ() - 1 - M / 2 + (column)).getBlock().isLiquid())
                    //field[row][column] = 0;
                //else {
                   // field[row][column] = -1;
               // }
            }
        }
        return  false;
    }

    private boolean isSearched(Location loc){
        for(int i = 0;i < searched.size(); i++){
            if ((loc.getBlockX() == searched.get(i).getBlockX())&&(loc.getBlockY() == searched.get(i).getBlockY())&&(loc.getBlockZ() == searched.get(i).getBlockZ()))
                return true;
        }
        return false;
    }*/
}

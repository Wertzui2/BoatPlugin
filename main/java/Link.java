import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class Link {
    private Location locOfSign1;
    private Player player;
    public boolean leftBoat;

    public Location getLocOfSign1() {
        return locOfSign1;
    }
    public Player getPlayer() {
        return player;
    }
    public boolean isLeftBoat() {
        return leftBoat;
    }

    public Link(Location loc, Player p){
        player = p;
        BoatPlugin.links.add(this);
        locOfSign1 = loc;
        leftBoat = false;
    }

}

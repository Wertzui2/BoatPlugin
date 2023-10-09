import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MyListener implements Listener{
public BoatPlugin plugin;
public int cooldown = 0;
public MyListener(BoatPlugin plugin){
    this.plugin = plugin;
}

    @EventHandler
    public void onClicked(PlayerInteractEvent e){
        if(e.getClickedBlock().getType() != null){
            if(e.getClickedBlock().getType().toString().equals("SIGN_POST") || (e.getClickedBlock().getType().toString().equals("WALL_SIGN"))) {
                if(e.getAction().toString().equals("RIGHT_CLICK_BLOCK"))
                    onSignRightClicked(e);
                else {
                    //onSignLeftClicked(e);
                }
            }
        }
    }

    public void onSignRightClicked(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Location loc = e.getClickedBlock().getLocation();
        if(cooldown == 0){
            if(isSignPort(loc,p)){
                if(doesLinkExist(p)){
                    Link link = getLink(p);
                    createRoute(link.getLocOfSign1(),loc,p);
                    BoatPlugin.links.remove(BoatPlugin.links.indexOf(link)); //Remove that link
                }
                else{
                    BoatPlugin.links.add(new Link(loc,p));
                    //AESTHETIC CHANGES//////////////////
                    Sign sign = (Sign) e.getClickedBlock().getState();
                    sign.setWaxed(true);
                    String line = ChatColor.LIGHT_PURPLE + "[Transport]";
                    sign.getSide(Side.FRONT).setLine(0, line);
                    sign.update();
                    /////////////////////////////////////
                    p.sendMessage("Port has been initialized. Right click another \" [Transport] \" sign to create a route. Please make sure to remain in your boat, or route creation will fail");
                }
            }
            else{
                if(doesRouteExist(loc)){
                    new ConfirmTravelDialogue(p, this);
                }
            }
            startCooldown();
            return;
        }

        else {
            if(isSignPort(loc,p))
                p.sendMessage("SLOW DOWN!!!");

        }
    }
    public void startCooldown(){ //
        cooldown = 5000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cooldown = 0;
                Bukkit.broadcastMessage("DONE TIMER");
            }
        }, cooldown);

    } //Makes a 5-second cooldown
    public boolean isSignPort(Location loc, Player p){ //RETURN WHETHER PORT CAN BE CREATED FROM SIGN
        Sign sign = (Sign) loc.getBlock().getState();
        if(sign.getSide(Side.FRONT).getLine(0).equals("[Transport]")){
            if(p.isInsideVehicle()&&p.getVehicle().getType().toString().equals("BOAT") &&p.getVehicle().isInWater()){
                return true;
            }
            else{
                p.sendMessage("Make sure to be inside of a boat while initializing your port!");
                return false;
            }
        }
        else return false;

    }//Returns whether a sign can be initialized as a port
    public boolean doesRouteExist(Location loc){
        if(getRoute(loc) == null)
            return false;
        return true;
    } //Returns whether there exists a route with either sign at loc
    public Route getRoute(Location loc){
        for(int i = 0; i < BoatPlugin.routes.size();i++){
            Route route = BoatPlugin.routes.get(i);
            if((route.getLocOfSign1().getBlockX()==loc.getBlockX()
                    && route.getLocOfSign1().getBlockY()==loc.getBlockY()
                    && route.getLocOfSign1().getBlockZ()==loc.getBlockZ())
                    ||
                    (route.getLocOfSign2().getBlockX()==loc.getBlockX()
                            && route.getLocOfSign2().getBlockY()==loc.getBlockY()
                            && route.getLocOfSign2().getBlockZ()==loc.getBlockZ()))

                return route;
        }
        return null;
    } //Returns a route that has either of its signs at loc, and null if there are none
    public Route getRoute(Player owner){
        for(int i = 0; i < BoatPlugin.routes.size();i++){
            Route route = BoatPlugin.routes.get(i);
            if(route.getOwner().getName().equals(owner.getName()))
                return route;
        }
        return null;
    } //Returns the first route that a player owns, and null if the player owns none
    public boolean doesLinkExist(Player p){
        if(getLink(p) == null)
            return false;
        return true;
    } //Returns whether p has created a link
    public Link getLink(Player p){
        for(int i = 0; i < BoatPlugin.links.size();i++){
            Link link = BoatPlugin.links.get(i);
            if(link.getPlayer().getName().equals(p.getName()))
                return link;
        }
        return null;
    } //Returns a link that was created by p, and null if none exist
    public void createRoute(Location locOfSign1, Location locOfSign2, Player p){
        if(getLink(p).isLeftBoat()){
            p.sendMessage("Unfortunately, you left the boat, so no route could be created :(, please try again");
            return;
        }
        else {
            BoatPlugin.routes.add(new Route(locOfSign1, locOfSign2, p));
            //AESTHETIC CHANGES//////////////////
            Sign sign = (Sign) locOfSign2.getBlock().getState();
            sign.setWaxed(true);
            String line = ChatColor.LIGHT_PURPLE + "[Transport]";
            sign.getSide(Side.FRONT).setLine(0, line);
            sign.update();
            /////////////////////////////////////

        }
    }


    public void travel(Player p){
        //p.sendMessage(p + " is Travelling");
        p.teleport(new Location(p.getWorld(), 500, 100, 500));
    }

    @EventHandler
    public void onVehicleLeave(VehicleExitEvent e){
        if(e.getVehicle().getType().toString().equals("BOAT")){
            onBoatLeave(e);
        }
    } //Calls onBoatLeave if vehicle type is BOAT
    public void onBoatLeave(VehicleExitEvent e){
        Player p = (Player) e.getExited();
        if(doesLinkExist(p)){
            getLink(p).leftBoat = true;
        }
    }

/*
    @EventHandler
    public void onSignRightClicked(PlayerInteractEvent event){
        if(event.getClickedBlock().getType() != null){
            //Bukkit.broadcastMessage("Sign has been right-clicked" + event.getClickedBlock().getType().toString());
        if(event.getAction().toString().equals("RIGHT_CLICK_BLOCK") && (event.getClickedBlock().getType().toString().equals("SIGN_POST") || (event.getClickedBlock().getType().toString().equals("WALL_SIGN")))){
            //Bukkit.broadcastMessage("Sign has been right-clicked" + event.getClickedBlock().getType().toString());
            Sign sign = (Sign) event.getClickedBlock().getState();
            if(sign.getSide(Side.FRONT).getLine(0).equals("[Transport]")){ //Checks if [Transport] is written on the first line
                //Bukkit.broadcastMessage("Transport");

                if(isNearWater(event.getClickedBlock())){ //Checks if near water
                    //Bukkit.broadcastMessage("NEAR WATER");
                    sign.setWaxed(true);
                    String line = ChatColor.LIGHT_PURPLE + "[Transport]";
                    sign.getSide(Side.FRONT).setLine(0, line);
                    sign.update();
                    Link link = new Link(event.getClickedBlock());

                    for(int i = 0; i < BoatPlugin.links.size(); i++){
                        if(linkCode.equals(BoatPlugin.links.get(i).getCode())) {
                            event.getPlayer().sendMessage("Route is being created, please be patient");
                            new Route(event.getClickedBlock().getLocation(), BoatPlugin.links.get(i).getLocOfSign1(), 0);
                            linkCode = "";
                            return;

                        }
                    }
                    event.getPlayer().sendMessage("Transport has been initialized, /link " + link.getCode());
                    return;

                }

            }
        }
        }
    }

    public static boolean isNearWater(Block block){ //is block next to water
        int x = block.getLocation().getBlockX();
        int y = block.getLocation().getBlockY();
        int z = block.getLocation().getBlockZ();
        for(int tempX = x - 2; tempX < x + 3; tempX++){
            for (int tempY = y + 2; tempY > y - 2; tempY--){
                for(int tempZ = z - 2; tempZ < z + 3; tempZ++){
                    //Bukkit.broadcastMessage(""+ tempX +" , "+ tempY +" , "+ tempZ);
                    if(new Location(block.getWorld(), tempX, tempY, tempZ).getBlock().isLiquid())
                        return true;
                }
            }
        }

        return false;
    }
    public static Location nearestWater(Location loc){
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        for(int tempX = x - 2; tempX < x + 3; tempX++) {
            for (int tempY = y + 2; tempY > y - 2; tempY--) {
                for (int tempZ = z - 2; tempZ < z + 3; tempZ++) {
                    //Bukkit.broadcastMessage(""+ tempX +" , "+ tempY +" , "+ tempZ);
                    if (new Location(loc.getWorld(), tempX, tempY, tempZ).getBlock().isLiquid())
                        return new Location(loc.getWorld(), tempX, tempY, tempZ);
                }
            }
        }
        return null;
    }


*/
}

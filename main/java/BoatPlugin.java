import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Boat;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class BoatPlugin extends JavaPlugin {
    public static Listener listener;
    public static ArrayList<Route> routes = new ArrayList<Route>();
    public static ArrayList<Link> links = new ArrayList<Link>();
    //public ConversationFactory test = new ConversationFactory(this);

    @Override
    public void onDisable() {
        super.onDisable();
    }
    @Override
    public void onEnable() {
        super.onEnable();
        //checkChunks(getServer().getWorlds().get(0));

        this.getLogger().log(Level.INFO, "BoatPlugin successfully loaded");
        listener = new MyListener(this);
        getServer().getPluginManager().registerEvents(listener, this);


    }
    //@Override
    /*public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if(command.toString().equals("link")) {
            MyListener.setLinkCode(args[0]);
            MyListener.setPlayerLinking(Bukkit.getPlayer(sender.getName()));
            Bukkit.broadcastMessage("command has run");
        //}
        //return super.onCommand(sender, command, label, args);
        return true;
    }*/


}

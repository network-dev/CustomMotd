package me.sendpacket.custommotd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomMotd extends JavaPlugin implements Listener {

    private String plugin_prefix = "[CustomMOTD] ";

    @Override
    public void onEnable()
    {
        Bukkit.getServer().getLogger().info(plugin_prefix+"Plugin enabled");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        Utils.plugin = this;

        if(!Utils.config_file_exists())
            Utils.create_config_file();

        Utils.reload_config_file();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getServer().getLogger().info(plugin_prefix+"Plugin disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cmotd"))
        {
            try
            {
                Utils.reload_config_file();
                sender.sendMessage(plugin_prefix + "Reloaded!");
            }catch (Exception e){ sender.sendMessage(plugin_prefix + "Invalid Config Found"); }

            return false;
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void onServerRequest(ServerListPingEvent event)
    {
        event.setMotd(Utils.motd_line_1 + "\n" + Utils.motd_line_2);
        event.setMaxPlayers(Utils.motd_maxplayers);
    }
}

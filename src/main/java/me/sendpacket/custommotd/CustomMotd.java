package me.sendpacket.custommotd;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomMotd extends JavaPlugin implements Listener {

    @Override
    public void onEnable()
    {
        Bukkit.getServer().getLogger().info(Utils.plugin_prefix+"Plugin enabled");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        Utils.plugin = this;

        if(!Utils.config_file_exists())
            Utils.create_config_file();

        Utils.reload_config_file();
    }

    @Override
    public void onDisable()
    {
        Bukkit.getServer().getLogger().info(Utils.plugin_prefix+"Plugin disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cmotd"))
        {
            try {
                if (args[0] != null && args[0].length() > 0) {
                    if (ArrayUtils.contains(Utils.help_alias, args[0])) {
                        Utils.chat_help(sender);
                    } else {
                        if (ArrayUtils.contains(Utils.reload_alias, args[0])) {
                            Utils.chat_config_reload(sender);
                        } else {
                            if (ArrayUtils.contains(Utils.usemaxp_alias, args[0])) {
                                Utils.set_use_maxplayers(!Utils.use_maxplayers);
                                sender.sendMessage(Utils.plugin_prefix + (Utils.use_maxplayers ? "Now overwriting max players" : "Not overwriting max players anymore"));
                            } else {
                                if (ArrayUtils.contains(Utils.line1_alias, args[0])) {
                                    try {
                                        String new_motd = "";
                                        for(int i = 1; i < args.length; i++)
                                        {
                                            if(i > 1)
                                            {
                                                new_motd += " " + args[i];
                                            }else{
                                                new_motd = args[i];
                                            }
                                        }
                                        Utils.set_line1(new_motd);
                                        sender.sendMessage(Utils.plugin_prefix + "Set line 1 to '" + ChatColor.translateAlternateColorCodes('&', new_motd) + ChatColor.WHITE + "'");
                                    } catch (Exception e) {
                                        sender.sendMessage(Utils.plugin_prefix + "Use: line1 <text>");
                                    }
                                } else {
                                    if (ArrayUtils.contains(Utils.line2_alias, args[0])) {
                                        try {
                                            String new_motd = "";
                                            for(int i = 1; i < args.length; i++)
                                            {
                                                if(i > 1)
                                                {
                                                    new_motd += " " + args[i];
                                                }else{
                                                    new_motd = args[i];
                                                }
                                            }
                                            Utils.set_line2(new_motd);
                                            sender.sendMessage(Utils.plugin_prefix + "Set line 2 to '" + ChatColor.translateAlternateColorCodes('&', new_motd) + ChatColor.WHITE + "'");
                                        } catch (Exception e) {
                                            sender.sendMessage(Utils.plugin_prefix + "Use: line2 <text>");
                                        }
                                    } else {
                                        if (ArrayUtils.contains(Utils.maxp_alias, args[0])) {
                                            try {
                                                Utils.set_maxplayers(Integer.valueOf(args[1]));
                                                sender.sendMessage(Utils.plugin_prefix + "Set max players to '" + args[1] + "'");
                                            } catch (Exception e) {
                                                sender.sendMessage(Utils.plugin_prefix + "Use: maxplayers <number>");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){Utils.chat_config_reload(sender);};
            return false;
        }
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void onServerRequest(ServerListPingEvent event) {
        event.setMotd(Utils.motd_line_1 + ChatColor.stripColor("\n") + Utils.motd_line_2);
        if (Utils.use_maxplayers) {
            event.setMaxPlayers(Utils.motd_maxplayers);
        }
    }
}

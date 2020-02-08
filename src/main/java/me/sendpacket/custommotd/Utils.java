package me.sendpacket.custommotd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class Utils {

    public static JavaPlugin plugin;
    public static String plugin_prefix = "[CustomMOTD] ";
    public static String motd_line_1 = "§6[§cDefault§6] §fThis is an §l§nexample";
    public static String motd_line_2 = "§6[§cDefault§6] §fThis is an §l§nexample";
    public static boolean use_maxplayers = true;
    public static int motd_maxplayers = 999;

    public static String[] help_alias = {"h","help","hp"};
    public static String[] reload_alias = {"r","reload","rl"};
    public static String[] line1_alias = {"l1","line1"};
    public static String[] line2_alias = {"l2","line2"};
    public static String[] maxp_alias = {"maxplayers","max"};
    public static String[] usemaxp_alias = {"usemaxplayers","usemax"};

    public static void chat_config_reload(CommandSender sender)
    {
        try
        {
            Utils.reload_config_file();
            sender.sendMessage(plugin_prefix + "Reloaded!");
        }catch (Exception e){ sender.sendMessage(plugin_prefix + "Invalid Config Found, Using Default"); Utils.create_config_file(); Utils.reload_config_file(); }
    }

    public static void chat_help(CommandSender sender)
    {
        sender.sendMessage(plugin_prefix + "Commands:");
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" help " + Arrays.toString(help_alias));
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" reload " + Arrays.toString(reload_alias));
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" line1 " + Arrays.toString(line1_alias));
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" line2 " + Arrays.toString(line2_alias));
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" maxplayers " + Arrays.toString(maxp_alias));
        sender.sendMessage(plugin_prefix + ChatColor.GREEN + "cmotd"+ChatColor.WHITE+" usemaxplayers " + Arrays.toString(usemaxp_alias));
    }

    public static void set_line1(String s)
    {
        plugin.getConfig().set("Line1", s);
        plugin.saveConfig();
        reload_config_file();
    }

    public static void set_line2(String s)
    {
        plugin.getConfig().set("Line2", s);
        plugin.saveConfig();
        reload_config_file();
    }

    public static void set_use_maxplayers(boolean b)
    {
        plugin.getConfig().set("UseMaxPlayers", b);
        plugin.saveConfig();
        reload_config_file();
    }

    public static void set_maxplayers(int n)
    {
        plugin.getConfig().set("MaxPlayers", n);
        plugin.saveConfig();
        reload_config_file();
    }

    public static void reload_config_file()
    {
        plugin.reloadConfig();

        motd_maxplayers = plugin.getConfig().getInt("MaxPlayers");
        motd_line_1 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Line1"));
        motd_line_2 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Line2"));
        use_maxplayers = plugin.getConfig().getBoolean("UseMaxPlayers");
    }

    public static void create_config_file()
    {
        plugin.getConfig().addDefault("MaxPlayers", motd_maxplayers);
        plugin.getConfig().addDefault("Line1", motd_line_1.replace('§', '&'));
        plugin.getConfig().addDefault("Line2", motd_line_2.replace('§', '&'));
        plugin.getConfig().addDefault("UseMaxPlayers", use_maxplayers);
        plugin.getConfig().options().copyDefaults(true);

        plugin.saveConfig();
    }

    public static boolean config_file_exists()
    {
        if(Utils.plugin.getDataFolder().exists())
        {
            File f = new File(Utils.plugin.getDataFolder() + "/config.yml");
            if (f.exists())
            {
                return true;
            }
        }
        return false;
    }
}

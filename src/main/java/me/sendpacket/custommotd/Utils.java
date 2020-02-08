package me.sendpacket.custommotd;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Utils {

    public static JavaPlugin plugin;

    public static String motd_line_1 = "§6[§cDefault§6] §fThis is an §l§nexample";
    public static String motd_line_2 = "§6[§cDefault§6] §fThis is an §l§nexample";
    public static int motd_maxplayers = 999;

    public static void reload_config_file()
    {
        plugin.reloadConfig();

        motd_maxplayers = plugin.getConfig().getInt("MaxPlayers");
        motd_line_1 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Line1"));
        motd_line_2 = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Line2"));
    }

    public static void create_config_file() {
        plugin.getConfig().addDefault("MaxPlayers", motd_maxplayers);
        plugin.getConfig().addDefault("Line1", motd_line_1.replace('§', '&'));
        plugin.getConfig().addDefault("Line2", motd_line_2.replace('§', '&'));
        plugin.getConfig().options().copyDefaults(true);

        plugin.saveConfig();
    }

    public static boolean config_file_exists()
    {
        if(Utils.plugin.getDataFolder().exists()) {
            File f = new File(Utils.plugin.getDataFolder() + "/config.yml");
            if (f.exists()) {
                return true;
            }
        }
        return false;
    }
}

package me.cole.survivalproject.utils;

import org.bukkit.ChatColor;

public class CC {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}


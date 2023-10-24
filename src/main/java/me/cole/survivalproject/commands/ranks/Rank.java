package me.cole.survivalproject.commands.ranks;

import lombok.Getter;
import me.cole.survivalproject.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Getter
public enum Rank {
    OWNER("Owner", ChatColor.RED, false, false),
    DEV("Dev", ChatColor.DARK_PURPLE, false, false),
    ADMIN("Admin", ChatColor.GOLD, false, false),
    MOD("Mod", ChatColor.DARK_GREEN, false, false),
    HELPER("Helper", ChatColor.DARK_AQUA, false, false),
    DEFAULT("", ChatColor.GRAY, false, false);

    private final String name;
    private final ChatColor colour;
    private final boolean bold;
    private final boolean italicised;

    Rank(String name, ChatColor colour, boolean bold, boolean italicised){
        this.name = name;
        this.colour = colour;
        this.bold = bold;
        this.italicised = italicised;
    }

    public boolean has(Player player, Rank rank, boolean callback){
        if(compareTo(rank) <= 0){
            return true;
        }

        if(callback){
            player.sendMessage(CC.translate(""));
        }
        return false;
    }

    public String getPrefix(){
        String name = this.name.toUpperCase();

        if(bold && italicised) return this.colour + "&o&l" + name;
        if(bold) return this.colour + "&l" + name;
        if(italicised) return this.colour + "&o" + name;

        if (!this.name().equalsIgnoreCase("default")) return CC.translate("&7[") + this.colour + name + CC.translate("&7] ");
        return this.colour + name;
    }

}




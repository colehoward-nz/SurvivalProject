package me.cole.survivalproject.commands.admin;

import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.commands.ranks.RankListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class FlyCommand implements CommandExecutor {

    public void toggleFly(Player player) {

        RankListener rankListener = RankListener.instance;
        String player_text = rankListener.getRank(SurvivalProject.instance.profileManager.getStats(player.getUniqueId()).getRank()).getColour() + player.getDisplayName();


        if (player.hasMetadata("fly")) {
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.YELLOW + "Fight has been disabled for " + player_text);
            player.removeMetadata("fly", SurvivalProject.instance);
        } else {
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.YELLOW + "Fight has been enabled for " + player_text);
            player.setMetadata("fly", new FixedMetadataValue(SurvivalProject.instance, true));
        }
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player ){
            Player player = (Player) sender;
            if (player.hasPermission("core.command.fly") || player.isOp()){
                if (args.length == 0) {
                    toggleFly(player);
                }
                else{
                    Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                    if (argumentPlayer != null){
                        toggleFly(argumentPlayer);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Player not found.");
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "No permissions.");
            }
        }
        return true;
    }
}

package me.cole.survivalproject.commands.admin;

import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.commands.ranks.RankListener;
import me.cole.survivalproject.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    private final SurvivalProject core;
    public TeleportCommand(SurvivalProject core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission("core.command.teleport") || player.isOp()){
                if (args.length == 0){
                    player.sendMessage(ChatColor.RED + "Incorrect usage: /teleport <player> [player]");
                }
                else {
                    Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                    if (argumentPlayer != null){
                        if (args.length == 1){
                            player.teleport(argumentPlayer);
                            RankListener rankListener = RankListener.instance;
                            String player_text = rankListener.getRank(SurvivalProject.instance.profileManager.getStats(argumentPlayer.getUniqueId()).getRank()).getColour() + argumentPlayer.getDisplayName();

                            player.sendMessage(ChatColor.YELLOW + "Teleported to " + player_text);
                        }
                        else {
                            Player otherPlayer = Bukkit.getServer().getPlayerExact(args[1]);
                            if (otherPlayer != null) {
                                argumentPlayer.teleport(otherPlayer);
                                RankListener rankListener = RankListener.instance;
                                String player_text = rankListener.getRank(SurvivalProject.instance.profileManager.getStats(argumentPlayer.getUniqueId()).getRank()).getColour() + argumentPlayer.getDisplayName();
                                String other_text = rankListener.getRank(SurvivalProject.instance.profileManager.getStats(otherPlayer.getUniqueId()).getRank()).getColour() + otherPlayer.getDisplayName();

                                player.sendMessage(ChatColor.YELLOW + "Teleported " + player_text + ChatColor.YELLOW + " to " + other_text);
                            }
                            else {
                                player.sendMessage(CC.translate("&cCannot find player ") + args[1]);
                            }
                        }
                    }
                    else {
                        player.sendMessage(CC.translate("&cCannot find player ") + args[0]);
                    }
                }
            }
            else{
                player.sendMessage(CC.translate("&cInsufficient permissions"));
            }
        }
        return true;
    }
}

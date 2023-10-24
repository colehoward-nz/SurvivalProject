package me.cole.survivalproject.commands.admin;

import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.commands.ranks.RankListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    public void changeGamemode(Player player, String gamemode){
        if (gamemode.equalsIgnoreCase("c") || gamemode.equalsIgnoreCase("creative")){
            player.setGameMode(GameMode.CREATIVE);
        }
        else if (gamemode.equalsIgnoreCase("s") || gamemode.equalsIgnoreCase("survival")){
            player.setGameMode(GameMode.SURVIVAL);
        }
        else if (gamemode.equalsIgnoreCase("a") || gamemode.equalsIgnoreCase("adventure")){
            player.setGameMode(GameMode.ADVENTURE);
        }
        else if (gamemode.equalsIgnoreCase("sp") || gamemode.equalsIgnoreCase("spec") || gamemode.equalsIgnoreCase("spectator")){
            player.setGameMode(GameMode.SPECTATOR);
        }

        RankListener rankListener = RankListener.instance;
        String player_text = rankListener.getRank(SurvivalProject.instance.profileManager.getStats(player.getUniqueId()).getRank()).getColour() + player.getDisplayName();
        player.sendMessage(ChatColor.YELLOW + "Gamemode for " + player_text + ChatColor.YELLOW + " set to " + player.getGameMode().toString().toUpperCase());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player ){
            Player player = (Player) sender;
            if (player.hasPermission("core.command.gamemode") || player.isOp()){
                if (args.length == 0){
                    player.sendMessage(ChatColor.RED + "Incorrect usage: /gamemode <type> [player]");
                }
                else if (args.length == 1){
                    changeGamemode(player, args[0]);
                }
                else if (args.length == 2){
                    Player argumentPlayer = Bukkit.getServer().getPlayerExact(args[1]);
                    if (argumentPlayer != null){
                        changeGamemode(argumentPlayer, args[0]);
                    }
                    else {
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

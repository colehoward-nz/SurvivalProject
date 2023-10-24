package me.cole.survivalproject.commands.ranks.commands;

import me.cole.survivalproject.commands.ranks.Rank;
import me.cole.survivalproject.profile.ProfileManager;
import me.cole.survivalproject.profile.StatsProfile;
import me.cole.survivalproject.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankCommand implements CommandExecutor {
    private final ProfileManager profileManager;
    private List<Rank> ranks = new ArrayList<>(
            Arrays.asList(Rank.OP, Rank.DEFAULT)
    );

    public RankCommand(ProfileManager profileManager){
        this.profileManager = profileManager;
    }

    public boolean exists(String rank){
        for (Rank check : ranks) {
            if (rank.equalsIgnoreCase(check.name())) return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("core.command.setrank")) {
                if (args.length == 0 || args.length == 1) {
                    player.sendMessage(CC.translate("&cIncorrect usage: /rank <player> <rank name>"));
                    return true;
                }

                if (exists(args[1])){
                    Player arg = Bukkit.getServer().getPlayerExact(args[0]);
                    if (arg == null) {
                        player.sendMessage(CC.translate("&cPlayer not found"));
                        return true;
                    }

                    StatsProfile rankProfile = profileManager.getStats(arg.getUniqueId());
                    Rank rank = Rank.valueOf(args[1].toUpperCase());
                    rankProfile.setRank(args[1]);

                    profileManager.saveStats(rankProfile);
                    player.sendMessage(CC.translate("&eRank of &b") + arg.getDisplayName() + CC.translate(" &eset to ") + rank.getColour() + rankProfile.getRank().toUpperCase());
                }
            }
            else {
                player.sendMessage(CC.translate("&cInsufficient permissions"));
            }
        }
        return false;
    }
}

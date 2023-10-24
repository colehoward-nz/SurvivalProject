package me.cole.survivalproject.commands.admin.stats;

import me.cole.survivalproject.profile.ProfileManager;
import me.cole.survivalproject.profile.StatsProfile;
import me.cole.survivalproject.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPoints implements CommandExecutor {
    private final ProfileManager profileManager;

    public SetPoints(ProfileManager profileManager){

        this.profileManager = profileManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("core.command.setpoints")) {
                if (args.length == 0 || args.length == 1) {
                    player.sendMessage(CC.translate("&cIncorrect usage: /setpoints <player> <balance>"));
                    return true;
                }

                Player argPlayer = Bukkit.getServer().getPlayerExact(args[0]);
                if (argPlayer == null) {
                    player.sendMessage(CC.translate("&cCannot find player"));
                    return true;
                }

                int points = Integer.parseInt(args[1]);
                StatsProfile profile = profileManager.getStats(argPlayer.getUniqueId());

                profile.setPoints(points);

                profileManager.saveStats(profile);

                player.sendMessage(CC.translate("&ePoints of &b" + argPlayer.getDisplayName() + " &ehave been set to &2$&a" + points));
                argPlayer.sendMessage(CC.translate("&eYour points have been manually updated"));
            }
            else {
                player.sendMessage(CC.translate("&cInsufficient permissions"));
            }
        }
        return false;
    }
}

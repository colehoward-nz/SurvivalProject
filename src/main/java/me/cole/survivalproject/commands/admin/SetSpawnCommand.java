package me.cole.survivalproject.commands.admin;

import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("core.command.setspawn")) {
                SurvivalProject.instance.worldHandler.setSpawn(player.getLocation());
                player.sendMessage(CC.translate("&eSpawn set to your location"));
            }
        }
        return true;
    }
}

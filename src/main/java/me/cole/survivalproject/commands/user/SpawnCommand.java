package me.cole.survivalproject.commands.user;

import me.cole.survivalproject.SurvivalProject;
import me.cole.survivalproject.utils.CC;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location spawn = SurvivalProject.instance.worldHandler.getSpawn();
            player.teleport(spawn);

            player.sendMessage(CC.translate("&eTeleported to spawn"));
        }

        return false;
    }
}

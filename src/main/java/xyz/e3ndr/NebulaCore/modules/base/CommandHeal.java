package xyz.e3ndr.nebulacore.modules.base;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;
import xyz.e3ndr.nebulacore.api.Util;
import xyz.e3ndr.nebulacore.modules.BaseCommand;

public class CommandHeal extends BaseCommand {

    @SuppressWarnings("deprecation")
    @Override
    public void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole) {
        if (executor.hasPermission("Nebula.heal")) {
            if (args.length == 0) {
                if (isConsole) {
                    executor.sendMessage("Only players can execute this command.");
                } else {
                    Player player = (Player) executor;

                    player.setHealth(player.getMaxHealth());
                    player.sendMessage(NebulaCore.getLang("heal-healed", player));
                }
            } else if (executor.hasPermission("Nebula.heal.others")) {
                NebulaPlayer player = NebulaPlayer.getPlayer(args[0]);

                if (player == null) {
                    executor.sendMessage(NebulaCore.getLang("error.player.offline"));
                } else {
                    player.getBukkit().setHealth(player.getBukkit().getMaxHealth());
                    player.getBukkit().sendMessage(NebulaCore.getLang("heal-healed", player.getUuid()));
                    executor.sendMessage(NebulaCore.getLang("heal-other", player.getUuid()));
                }
            } else {
                executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.heal.others"));
            }
        } else {
            executor.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", "Nebula.heal"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole) {
        List<String> ret = new ArrayList<>();

        if ((args.length == 1) && executor.hasPermission("Nebula.heal.others")) {
            ret.addAll(Util.getPlayerNames());
        }

        return ret;
    }

}

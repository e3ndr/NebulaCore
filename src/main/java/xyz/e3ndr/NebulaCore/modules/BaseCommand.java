package xyz.e3ndr.nebulacore.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;

public abstract class BaseCommand implements CommandExecutor, TabCompleter {
    public abstract void onCommand(CommandSender executor, String alias, String[] args, boolean isConsole);

    public abstract List<String> onTabComplete(CommandSender executor, String alias, String[] args, boolean isConsole);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        this.onCommand(sender, alias, args, sender instanceof ConsoleCommandSender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> ret = new ArrayList<>();
        List<String> out = this.onTabComplete(sender, alias, args, sender instanceof ConsoleCommandSender);
        String lastArg = args[args.length - 1].toLowerCase();

        for (String o : out) {
            if (o.toLowerCase().startsWith(lastArg)) {
                ret.add(o);
            }
        }

        return ret;
    }

}

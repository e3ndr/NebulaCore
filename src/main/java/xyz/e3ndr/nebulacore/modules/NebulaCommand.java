package xyz.e3ndr.nebulacore.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import xyz.e3ndr.consolidate.CommandRegistry;
import xyz.e3ndr.consolidate.PermissionChecker;
import xyz.e3ndr.consolidate.Resolver;
import xyz.e3ndr.consolidate.command.CommandListener;
import xyz.e3ndr.consolidate.exception.ArgumentsLengthException;
import xyz.e3ndr.consolidate.exception.CommandExecutionException;
import xyz.e3ndr.consolidate.exception.CommandNameException;
import xyz.e3ndr.consolidate.exception.CommandPermissionException;
import xyz.e3ndr.nebulacore.NebulaCore;
import xyz.e3ndr.nebulacore.api.NebulaPlayer;

public abstract class NebulaCommand implements CommandExecutor, TabCompleter, CommandListener<CommandSender>, PermissionChecker<CommandSender> {

    private static final Resolver<NebulaPlayer> NPLAYER_RESOLVER = new Resolver<NebulaPlayer>() {
        @Override
        public NebulaPlayer resolve(String value) throws IllegalArgumentException {
            NebulaPlayer np = NebulaPlayer.getPlayer(value);

            if (np == null) {
                throw new IllegalArgumentException("lang:" + NebulaCore.getLang("error.player.offline"));
            } else {
                return np;
            }
        }
    };

    private static final Resolver<Player> PLAYER_RESOLVER = new Resolver<Player>() {
        @Override
        public Player resolve(String value) throws IllegalArgumentException {
            return NPLAYER_RESOLVER.resolve(value).getBukkit();
        }
    };

    private CommandRegistry<CommandSender> registry = new CommandRegistry<>();

    public NebulaCommand() {
        this.registry.addCommand(this);
        this.registry.addResolver(PLAYER_RESOLVER, Player.class);
        this.registry.addResolver(NPLAYER_RESOLVER, NebulaPlayer.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        this.executeCommand(false, sender, alias, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = executeCommand(true, sender, alias, args);
        List<String> ret = new ArrayList<>();

        if (out != null) {
            String lastArg = args[args.length - 1].toLowerCase();

            for (String o : out) {
                if (o.toLowerCase().startsWith(lastArg)) {
                    ret.add(o);
                }
            }
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    private <R> R executeCommand(boolean isTabComplete, CommandSender sender, String alias, String[] args) {
        try {
            String commandLine = getCommandLine(isTabComplete, alias, args);

            try {
                return (R) this.registry.execute(commandLine, sender, this);
            } catch (CommandExecutionException e) {
                throw e.getCause(); // Always get the underlying reason.
            } catch (CommandNameException e) {
                if (isTabComplete) {
                    return null;
                } else {
                    throw e;
                }
            }

        } catch (IllegalArgumentException e) {
            String message = e.getMessage();

            if (message.startsWith("lang:")) {
                String[] split = message.split(":");
                String lang = split[1];

                sender.sendMessage(lang);
            } else {
                sender.sendMessage(message);
            }
        } catch (ArgumentsLengthException e) {
            if (!isTabComplete) {
                sender.sendMessage(NebulaCore.getLang("error-specify-argument"));
            }
        } catch (CommandPermissionException e) {
            if (isTabComplete) {
                String[] message = e.getMessage().split(": ");
                String permission = message[message.length - 1];

                sender.sendMessage(NebulaCore.getLang("error.perm").replace("%perm%", permission));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean check(CommandSender executor, String permission) {
        return executor.hasPermission(permission);
    }

    private static String getCommandLine(boolean isTabComplete, String alias, String[] args) {
        StringBuilder commandLine = new StringBuilder();

        if (isTabComplete) {
            commandLine.append("complete:");
        } else {
            commandLine.append(xyz.e3ndr.consolidate.command.Command.NO_OWNER).append(':');
        }

        commandLine.append(alias);

        for (String arg : args) {
            commandLine
                .append(' ')
                .append(arg);
        }

        return commandLine.toString();
    }

}

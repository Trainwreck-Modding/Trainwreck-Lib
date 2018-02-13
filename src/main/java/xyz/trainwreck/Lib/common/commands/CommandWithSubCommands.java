package xyz.trainwreck.Lib.common.commands;

import com.google.common.base.Joiner;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import xyz.trainwreck.Lib.common.util.ModContainerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class CommandWithSubCommands extends CommandBase {

    private static java.util.List<CommandBase> modCommands = new ArrayList<>();
    private static java.util.List<String> commands = new ArrayList<>();

    private static String baseCommand;

    public CommandWithSubCommands() {
        baseCommand = ModContainerHelper.getModIdFromActiveContainer();
    }

    public CommandWithSubCommands(String baseCommand) {
        CommandWithSubCommands.baseCommand = baseCommand;
    }

    @Override
    public String getName() {
        return baseCommand;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        boolean found = false;

        if (args.length >= 1) {

            for (CommandBase command : modCommands) {

                if (command.getName().equalsIgnoreCase(args[0]) && command.checkPermission(server, sender)) {
                    found = true;
                    command.execute(server, sender, args);
                }
            }
        }

        if (!found) {
            throw new WrongUsageException("Invalid command. Usage: /" + baseCommand + " " + Joiner.on(" ").join(commands));
        }
    }

    @Override
    public java.util.List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, commands);
        } else if (args.length >= 2) {
            for (CommandBase command : modCommands) {
                if (command.getName().equalsIgnoreCase(args[0])) {
                    return command.getTabCompletions(server, sender, args, pos);
                }
            }
        }

        return null;
    }

    public void addSubCommand(CommandBase command) {
        modCommands.add(command);
        commands.add(command.getName());
    }
}

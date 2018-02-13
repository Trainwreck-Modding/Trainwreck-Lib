package xyz.trainwreck.Lib;

import com.google.common.base.Stopwatch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import xyz.trainwreck.Lib.common.commands.CommandTPS;
import xyz.trainwreck.Lib.common.commands.CommandWithSubCommands;
import xyz.trainwreck.Lib.common.exception.OutdatedJavaException;
import xyz.trainwreck.Lib.common.util.Logger;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MOD_NAME)
public class Lib {

    public static Logger LOGGER = new Logger(Reference.MOD_NAME);
    public static Stopwatch STOPWATCH = Stopwatch.createUnstarted();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        STOPWATCH.start();
        LOGGER.info("Pre-Init - Started");

        if (!SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
            throw new OutdatedJavaException(String.format("%s requires Java 8 or newer, Please update your java", Reference.MOD_NAME));
        }

        LOGGER.info("Pre-Init - Finished - " + STOPWATCH.elapsed(TimeUnit.MILLISECONDS) + "ms");
        STOPWATCH.reset();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        STOPWATCH.start();
        LOGGER.info("Initialization - Started");

        LOGGER.info("Initialization - Finished - " + STOPWATCH.elapsed(TimeUnit.MILLISECONDS) + "ms");
        STOPWATCH.reset();
    }

    @Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event) {
        STOPWATCH.start();
        LOGGER.info("Post-Initialization - Started");

        LOGGER.info("Post-Initialization - Finished - " + STOPWATCH.elapsed(TimeUnit.MILLISECONDS) + "ms");
        STOPWATCH.reset();
    }

    @Mod.EventHandler
    public void onServerStartup(FMLServerStartingEvent event) {
        CommandWithSubCommands command = new CommandWithSubCommands();
        command.addSubCommand(new CommandTPS());
        event.registerServerCommand(command);
    }
}
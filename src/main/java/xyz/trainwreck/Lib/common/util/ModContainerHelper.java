package xyz.trainwreck.Lib.common.util;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ModContainerHelper {
    public static String getModIdFromActiveContainer() {
        ModContainer container = Loader.instance().activeModContainer();
        String modId;

        if (container != null) {
            modId = container.getModId();
            if (!modId.isEmpty())
                return modId;
        }
        throw new RuntimeException("Cannot get Mod Id from FML");
    }

    public static String getModNameFromActiveContainer() {
        ModContainer container = Loader.instance().activeModContainer();
        String modName;

        if (container != null) {
            modName = container.getName();
            if (!modName.isEmpty())
                return modName;
        }
        throw new RuntimeException("Cannot get Mod Name from FML");
    }
}

package xyz.trainwreck.Lib.common.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.Map;

public class Platform {
    public static boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    public static boolean isServer() {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> p_1874891, Comparable<?> p_1874892) {
        return p_1874891.getName((T) p_1874892);
    }

    public static String getPropertyString(Map<IProperty<?>, Comparable<?>> values, String... extrasArgs) {
        StringBuilder stringbuilder = new StringBuilder();

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
            if (stringbuilder.length() != 0) {
                stringbuilder.append(",");
            }

            IProperty<?> iproperty = (IProperty) entry.getKey();
            stringbuilder.append(iproperty.getName());
            stringbuilder.append("=");
            stringbuilder.append(getPropertyName(iproperty, (Comparable) entry.getValue()));
        }

        if (stringbuilder.length() == 0) {
            stringbuilder.append("inventory");
        }

        for (String args : extrasArgs) {
            if (stringbuilder.length() != 0)
                stringbuilder.append(",");
            stringbuilder.append(args);
        }

        return stringbuilder.toString();
    }

    public static boolean isSameItem(@Nullable ItemStack itemStack1, @Nullable ItemStack itemStack2) {
        return itemStack1 != ItemStack.EMPTY && itemStack2 != ItemStack.EMPTY && itemStack1.isItemEqual(itemStack2);
    }
}

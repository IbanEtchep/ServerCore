package fr.iban.bukkitcore.utils;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class SerializationUtils {

    public static String toBase64(@NotNull ItemStack itemStack) {
        return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes());
    }

    public static ItemStack fromBase64(@NotNull String base64) {
        return ItemStack.deserializeBytes(Base64.getDecoder().decode(base64));
    }

}

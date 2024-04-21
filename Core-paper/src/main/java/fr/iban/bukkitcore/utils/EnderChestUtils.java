package fr.iban.bukkitcore.utils;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.iban.bukkitcore.menu.Menu;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.bukkit.util.io.BukkitObjectInputStream;

public class EnderChestUtils {

    private Map<UUID, Menu> openMenus = new HashMap<>();

    public byte[] serializeItemStackArray(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to serialize item stack array.", e);
        }
    }

    public ItemStack[] deserializeItemStackArray(byte[] data) {
        if (data.length == 0) {
            return new ItemStack[0];
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }
            return items;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to deserialize item stack array.", e);
        }
    }

    public void registerMenu(UUID playerId, Menu menu) {
        openMenus.put(playerId, menu);
    }

    public Menu getMenu(UUID playerId) {
        return openMenus.get(playerId);
    }

    public void removeMenu(UUID playerId) {
        openMenus.remove(playerId);
    }

}

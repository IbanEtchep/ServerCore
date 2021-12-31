package fr.iban.survivalcore.listeners;

import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import fr.iban.survivalcore.tools.SpecialTools;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareResultListener implements Listener {

    @EventHandler
    public void onPrepare(PrepareResultEvent e){
        ItemStack result = e.getResult();

        if(result == null){
            return;
        }

        if(SpecialTools.is3x3Pickaxe(result)){
            e.setResult(new ItemStack(Material.AIR));
        }
    }
}

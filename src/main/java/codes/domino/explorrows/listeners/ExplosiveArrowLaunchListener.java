package codes.domino.explorrows.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.persistence.PersistentDataType;

import static org.bukkit.util.NumberConversions.toByte;

public class ExplosiveArrowLaunchListener implements Listener {

    @EventHandler
    public void onArrowLaunch(EntityShootBowEvent event) {
        if (!event.getConsumable().getItemMeta().getPersistentDataContainer().has(ArrowAnvilListener.EXPLOSIVE_ITEM_KEY, PersistentDataType.BYTE))
            return;
        event.getProjectile().getPersistentDataContainer().set(ExplosiveArrowLandEvent.EXPLOSIVE_ENTITY_KEY, PersistentDataType.BYTE, toByte(1));
    }
}

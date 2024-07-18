package codes.domino.explorrows.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.persistence.PersistentDataType;

public class ExplosiveArrowLaunchListener implements Listener {

    @EventHandler
    public void onArrowLaunch(EntityShootBowEvent event) {
        if (!event.getConsumable().getItemMeta().getPersistentDataContainer().has(ArrowAnvilListener.EXPLOSIVE_ITEM_KEY, PersistentDataType.STRING))
            return;
        event.getProjectile().getPersistentDataContainer().set(ExplosiveArrowLandEvent.EXPLOSIVE_ENTITY_KEY, PersistentDataType.STRING, "explosivearrow");
    }
}

package codes.domino.explorrows.listeners;

import codes.domino.explorrows.Explorrows;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;

public class ExplosiveArrowLandEvent implements Listener {

    public static final NamespacedKey EXPLOSIVE_ENTITY_KEY = new NamespacedKey(Explorrows.getInstance(), "explosiveentity");

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (!event.getEntity().getPersistentDataContainer().has(EXPLOSIVE_ENTITY_KEY, PersistentDataType.BYTE))
            return;
        Location hitLocation;
        if (event.getHitEntity() != null) {
            hitLocation = event.getHitEntity().getLocation();
        } else {
            hitLocation = event.getHitBlock().getLocation();
        }
        System.out.println(hitLocation);
        hitLocation.getWorld().createExplosion(hitLocation, 2f);
        event.getEntity().getPersistentDataContainer().remove(EXPLOSIVE_ENTITY_KEY);
        event.getEntity().remove();
    }
}

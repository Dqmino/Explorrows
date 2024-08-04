package codes.domino.explorrows.listeners;

import codes.domino.explorrows.Explorrows;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static org.bukkit.util.NumberConversions.toByte;

public class ArrowAnvilListener implements Listener {
    public static NamespacedKey EXPLOSIVE_ITEM_KEY = new NamespacedKey(Explorrows.getInstance(), "explosivearrow");
    private final ItemStack explosiveArrow = new ItemStack(Material.ARROW);

    public ArrowAnvilListener() {
        ItemMeta itemMeta = explosiveArrow.getItemMeta();
        itemMeta.setItemName(ChatColor.LIGHT_PURPLE + "Explosive Arrow");
        itemMeta.setEnchantmentGlintOverride(true);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(EXPLOSIVE_ITEM_KEY, PersistentDataType.BYTE, toByte(1));
        explosiveArrow.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onAnvilPreparation(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        if (!(anvilInventory.contains(Material.ARROW) && anvilInventory.contains(Material.TNT))) return;

        int amount = Math.min(anvilInventory.getContents()[0].getAmount(), anvilInventory.getContents()[1].getAmount());
        ItemStack newItem = explosiveArrow.clone();
        newItem.setAmount(amount);
        event.setResult(newItem);

        Explorrows.getInstance().getServer().getScheduler().runTask(Explorrows.getInstance(), () -> event.getInventory().setRepairCost(1));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onResultClick(InventoryClickEvent event) {
        if (event.getClickedInventory().getType() != InventoryType.ANVIL) return;
        AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();

        if (!event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(EXPLOSIVE_ITEM_KEY, PersistentDataType.BYTE))
            return;
        if (!(anvilInventory.contains(Material.ARROW) && anvilInventory.contains(Material.TNT))) return;

        int amount = Math.min(anvilInventory.getContents()[0].getAmount(), anvilInventory.getContents()[1].getAmount());
        for (int i = 0; i < 2; i++) {
            if (anvilInventory.getContents()[i].getAmount() > amount) {
                ItemStack newItem = anvilInventory.getContents()[i].clone();
                int leftOver = newItem.getAmount() - amount;
                if (leftOver == 0) {
                    continue;
                }
                newItem.setAmount(leftOver);
                int finalI = i;
                Explorrows.getInstance().getServer().getScheduler().runTask(Explorrows.getInstance(), () -> anvilInventory.setItem(finalI, newItem));
            }
        }
    }
}

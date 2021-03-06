package io.github.zorua162.geolimitentitiesaddon.events;

import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;

import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.panels.Panel;
import world.bentobox.bentobox.api.panels.PanelItem.ClickHandler;
import world.bentobox.bentobox.api.panels.builders.TabbedPanelBuilder;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.managers.IslandWorldManager;
import world.bentobox.bentobox.util.Util;

/**
 * Provide geo limiting to entities - removed them if they go outside island bounds
 * @author tastybento
 *
 */
public class GeoLimitEntitiesClickListener implements ClickHandler {

    @Override
    public boolean onClick(Panel panel, User user, ClickType clickType, int slot) {
        // Get the world
        if (!user.inWorld()) {
            user.sendMessage("general.errors.wrong-world");
            return true;
        }
        IslandWorldManager iwm = BentoBox.getInstance().getIWM();
        String reqPerm = iwm.getPermissionPrefix(Util.getWorld(user.getWorld())) + "admin.settings.GEO_LIMIT_MOBS";
        if (!user.hasPermission(reqPerm)) {
            user.sendMessage("general.errors.no-permission", "[permission]", reqPerm);
            user.getPlayer().playSound(user.getLocation(), Sound.BLOCK_METAL_HIT, 1F, 1F);
            return true;
        }

        // Open the Sub Settings panel
        openPanel(user);

        return true;
    }

    private void openPanel(User user) {
        // Close the current panel
        user.closeInventory();
        // Open a new panel
        new TabbedPanelBuilder()
                .user(user)
                .world(user.getWorld())
                .tab(1, new GeoEntityLimitTab(user, GeoEntityLimitTab.EntityLimitTabType.GEO_LIMIT))
                .startingSlot(1)
                .size(54)
                .build().openPanel();
    }
}

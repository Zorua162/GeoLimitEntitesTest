package io.github.zorua162.geolimitentitiesaddon;

import io.github.zorua162.geolimitentitiesaddon.events.GeoLimitEntitiesClickListener;
import io.github.zorua162.geolimitentitiesaddon.events.GeoLimitEntitiesListener;
import org.bukkit.Material;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.flags.Flag;
import world.bentobox.bentobox.listeners.flags.clicklisteners.GeoLimitClickListener;

public class GeoLimitEntitiesAddon extends Addon {


    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        // Check if BentoBox is enabled - it might be loaded, but not enabled.
        if (this.getPlugin() == null || !this.getPlugin().isEnabled()) {
            this.logError("BentoBox is not available or disabled!");
            //Disable this addon as BentoBox was not enabled
            this.setState(State.DISABLED);
            return;
        }

        // Check if addon is not disabled before loading
        if (this.getState().equals(State.DISABLED))
        {
           this.logError("GeoLimitEntities Addon is not available or disabled!");
            return;
        }
        getLogger().info("Loaded GeoLimitEntities addon!");
        //Register player leave event
        final Flag GEO_LIMIT_MOBS = new Flag.Builder("GEO_LIMIT_ENTITIES", Material.GOLDEN_CHESTPLATE).type(Flag.Type.WORLD_SETTING)
                .listener(new GeoLimitEntitiesListener()).clickHandler(new GeoLimitEntitiesClickListener()).usePanel(true).build();
        getLogger().info("ADDED GEO_LIMIT_MOBS");
        this.getPlugin().getFlagsManager().registerFlag(GEO_LIMIT_MOBS);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable(){
        getLogger().info("Unloaded GeoLimitEntities addon!");
        //Not sure if the event needs to be unregistered, but I can't see a way to do this.
    }
}

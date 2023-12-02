package com.lypaka.marketplace.Listeners;

import com.lypaka.marketplace.ConfigGetters;
import com.lypaka.marketplace.Marketplace;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Marketplace.MOD_ID)
public class ServerStartingListener {

    @SubscribeEvent
    public static void onServerStarting (FMLServerStartingEvent event) {

        boolean needsSaving = false;
        if (ConfigGetters.blockPrices.isEmpty()) {

            needsSaving = true;
            Map<String, Integer> map = new HashMap<>();
            Marketplace.logger.info("Detected empty block prices config, generating...");
            ForgeRegistries.BLOCKS.getEntries().forEach(entry -> {

                String blockID = entry.getValue().getRegistryName().toString();
                map.put(blockID, 0);

            });
            Marketplace.configManager.getConfigNode(0, "Prices").setValue(map);

        }
        if (ConfigGetters.itemPrices.isEmpty()) {

            if (!needsSaving) needsSaving = true;
            Map<String, Integer> map = new HashMap<>();
            Marketplace.logger.info("Detected empty item prices config, generating...");
            ForgeRegistries.ITEMS.getEntries().forEach(entry -> {

                String itemID = entry.getValue().getRegistryName().toString();
                map.put(itemID, 0);

            });
            Marketplace.configManager.getConfigNode(1, "Prices").setValue(map);

        }
        if (needsSaving) Marketplace.configManager.save();

    }

}

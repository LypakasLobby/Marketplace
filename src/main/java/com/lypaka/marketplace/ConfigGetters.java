package com.lypaka.marketplace;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Map;

public class ConfigGetters {

    public static Map<String, Integer> blockPrices;
    public static Map<String, Integer> itemPrices;

    public static void load() throws ObjectMappingException {

        blockPrices = Marketplace.configManager.getConfigNode(0, "Prices").getValue(new TypeToken<Map<String, Integer>>() {});
        itemPrices = Marketplace.configManager.getConfigNode(1, "Prices").getValue(new TypeToken<Map<String, Integer>>() {});

    }

}

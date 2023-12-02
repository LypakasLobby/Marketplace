package com.lypaka.marketplace.Commands;

import com.lypaka.marketplace.ConfigGetters;
import com.lypaka.marketplace.Marketplace;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Marketplace.MOD_ID)
public class MarketplaceCommand {

    public static final List<String> ALIASES = Arrays.asList("marketplace", "market");
    public static List<String> BLOCKS;
    public static List<String> ITEMS;

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
        loadCommandOptions();

    }

    public static void loadCommandOptions() {

        BLOCKS = new ArrayList<>();
        ITEMS = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ConfigGetters.blockPrices.entrySet()) {

            if (entry.getValue() > 0) BLOCKS.add(entry.getKey());

        }
        for (Map.Entry<String, Integer> entry : ConfigGetters.itemPrices.entrySet()) {

            if (entry.getValue() > 0) ITEMS.add(entry.getKey());

        }
        /*List<String> blocks = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ConfigGetters.blockPrices.entrySet()) {

            if (entry.getValue() > 0) blocks.add(entry.getKey());

        }
        BLOCKS = (context, builder) -> ISuggestionProvider.suggest(blocks, builder);
        List<String> items = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ConfigGetters.itemPrices.entrySet()) {

            if (entry.getValue() > 0) items.add(entry.getKey());


        }
        ITEMS = (context, builder) -> ISuggestionProvider.suggest(items, builder);*/

    }

}

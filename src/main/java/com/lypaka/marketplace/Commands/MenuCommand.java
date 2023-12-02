package com.lypaka.marketplace.Commands;

import com.lypaka.marketplace.GUI.MarketplaceMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : MarketplaceCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("blocks")
                                            .then(
                                                    Commands.argument("block", ItemArgument.item())
                                                            .suggests(
                                                                    (context, builder) -> ISuggestionProvider.suggest(MarketplaceCommand.BLOCKS, builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    String id = ItemArgument.getItem(c, "block").getItem().getRegistryName().toString();
                                                                    MarketplaceMenu menu = new MarketplaceMenu(player, id, "blocks");
                                                                    menu.open();

                                                                }

                                                                return 1;

                                                            })
                                            )
                            )
                            .then(
                                    Commands.literal("items")
                                            .then(
                                                    Commands.argument("item", ItemArgument.item())
                                                            .suggests(
                                                                    (context, builder) -> ISuggestionProvider.suggest(MarketplaceCommand.ITEMS, builder)
                                                            )
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    String id = ItemArgument.getItem(c, "item").getItem().getRegistryName().toString();
                                                                    MarketplaceMenu menu = new MarketplaceMenu(player, id, "items");
                                                                    menu.open();

                                                                }

                                                                return 1;

                                                            })
                                            )
                            )
            );

        }

    }

}

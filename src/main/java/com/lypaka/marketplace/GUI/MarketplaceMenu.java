package com.lypaka.marketplace.GUI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.ButtonClick;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.lypaka.lypakautils.MiscHandlers.LogicalPixelmonMoneyHandler;
import com.lypaka.marketplace.ConfigGetters;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.Map;

public class MarketplaceMenu {

    private final ServerPlayerEntity player;
    private final String id;
    private final String type;
    private int quantity;

    public MarketplaceMenu (ServerPlayerEntity player, String id, String type) {

        this.player = player;
        this.id = id;
        this.type = type;
        this.quantity = 1;

    }

    public void open() {

        ChestTemplate template = ChestTemplate.builder(3).build();
        GooeyPage page = GooeyPage.builder()
                .template(template)
                .title(FancyText.getFormattedText("&eMarketplace"))
                .build();

        int[] border = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        ItemStack glass = ItemStackBuilder.buildFromStringID("minecraft:yellow_stained_glass_pane").setDisplayName(FancyText.getFormattedText(""));
        GooeyButton button = GooeyButton.builder().display(glass).build();
        for (int i : border) {

            page.getTemplate().getSlot(i).setButton(button);

        }

        ItemStack item = ItemStackBuilder.buildFromStringID(this.id);
        page.getTemplate().getSlot(10).setButton(GooeyButton.builder().display(item).build());

        ItemStack emeraldBlock = ItemStackBuilder.buildFromStringID("minecraft:emerald_block");
        emeraldBlock.setDisplayName(FancyText.getFormattedText("&aIncrease quantity"));
        ListNBT emeraldLore = new ListNBT();
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aLeft click &eto increase quantity by &a1"))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aShift left click &eto increase quantity by &a10"))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aRight click &eto increase quantity by &a5"))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        emeraldLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aShift right click &eto set quantity to &a64"))));
        emeraldBlock.getOrCreateChildTag("display").put("Lore", emeraldLore);
        GooeyButton emeraldButton = GooeyButton.builder()
                .display(emeraldBlock)
                .onClick(click -> {

                    if (click.getClickType() == ButtonClick.LEFT_CLICK) {

                        increaseBuy(1);
                        update();

                    } else if (click.getClickType() == ButtonClick.SHIFT_LEFT_CLICK) {

                        increaseBuy(10);
                        update();

                    } else if (click.getClickType() == ButtonClick.RIGHT_CLICK) {

                        increaseBuy(5);
                        update();

                    } else if (click.getClickType() == ButtonClick.SHIFT_RIGHT_CLICK) {

                        this.quantity = 64;
                        update();

                    }

                })
                .build();
        page.getTemplate().getSlot(12).setButton(emeraldButton);

        ItemStack redstoneBlock = ItemStackBuilder.buildFromStringID("minecraft:redstone_block");
        redstoneBlock.setDisplayName(FancyText.getFormattedText("&cDecrease quantity"));
        ListNBT redstoneLore = new ListNBT();
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aLeft click &eto decrease quantity by &a1"))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aShift left click &eto decrease quantity by &a10"))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aRight click &eto decrease quantity by &a5"))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
        redstoneLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&aShift right click &eto set quantity to &a1"))));
        redstoneBlock.getOrCreateChildTag("display").put("Lore", redstoneLore);
        GooeyButton redstoneButton = GooeyButton.builder()
                .display(redstoneBlock)
                .onClick(click -> {

                    if (click.getClickType() == ButtonClick.LEFT_CLICK) {

                        decreaseBuy(1);
                        update();

                    } else if (click.getClickType() == ButtonClick.SHIFT_LEFT_CLICK) {

                        decreaseBuy(10);
                        update();

                    } else if (click.getClickType() == ButtonClick.RIGHT_CLICK) {

                        decreaseBuy(5);
                        update();

                    } else if (click.getClickType() == ButtonClick.SHIFT_RIGHT_CLICK) {

                        this.quantity = 1;
                        update();

                    }

                })
                .build();
        page.getTemplate().getSlot(14).setButton(redstoneButton);

        ItemStack netherStar = ItemStackBuilder.buildFromStringID("minecraft:nether_star");
        netherStar.setDisplayName(FancyText.getFormattedText("&eConfirm purchase"));
        ListNBT starLore = new ListNBT();
        starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eAmount: &a" + this.quantity))));
        Map<String, Integer> map = this.type.equals("items") ? ConfigGetters.itemPrices : ConfigGetters.blockPrices;
        int base = map.get(this.id);
        int price = base * this.quantity;
        starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&ePrice: &a" + price))));
        int balance = (int) LogicalPixelmonMoneyHandler.getBalance(this.player.getUniqueID());
        GooeyButton starButton;
        if (balance >= price) {

            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eLeft click to buy!"))));
            netherStar.getOrCreateChildTag("display").put("Lore", starLore);
            starButton = GooeyButton.builder()
                    .display(netherStar)
                    .onClick(() -> {

                        buy(price);

                    })
                    .build();

        } else {

            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&cInsufficient funds to purchase!"))));
            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&ePrice is: &a" + price))));
            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eYour balance is: &a" + balance))));
            int difference = price - balance;
            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&eYou need &a" + difference + " &emore PokeDollars to buy this amount!"))));
            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText(""))));
            starLore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(FancyText.getFormattedText("&ePlease decrease the quantity to be able to complete your purchase!"))));
            netherStar.getOrCreateChildTag("display").put("Lore", starLore);
            starButton = GooeyButton.builder()
                    .display(netherStar)
                    .build();

        }

        page.getTemplate().getSlot(16).setButton(starButton);
        UIManager.openUIForcefully(this.player, page);

    }

    private void increaseBuy (int amount) {

        this.quantity = Math.min(this.quantity + amount, 64);

    }

    private void decreaseBuy (int amount) {

        this.quantity = Math.max(this.quantity - amount, 1);

    }

    private void buy (int price) {

        ItemStack item = ItemStackBuilder.buildFromStringID(this.id);
        item.setCount(this.quantity);
        LogicalPixelmonMoneyHandler.remove(this.player.getUniqueID(), price);
        this.player.addItemStackToInventory(item);
        UIManager.closeUI(this.player);
        this.player.sendMessage(FancyText.getFormattedText("&ePurchased " + this.quantity + " " + this.id + " for " + price), this.player.getUniqueID());

    }

    private void update() {

        open();

    }

}

package org.pokesplash.gts.UI.button;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.component.ItemLore;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.AllListings;
import org.pokesplash.gts.UI.ExpiredListings;
import org.pokesplash.gts.UI.FilterType;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class FilterButton {
    public static Button getButton(FilterType currentFilter, Sort currentSort) {
        return GooeyButton.builder()
                .display(Utils.parseItemId(Gts.language.getFilterButtonItem()))
                .with(DataComponents.ITEM_NAME, Component.literal(Gts.language.getFilterButtonLabel()))
                .with(DataComponents.LORE, new ItemLore(Arrays.stream(FilterType.values())
                        .map( filterType -> Component.literal(" - " + filterType.name)
                            .withStyle(filterType.equals(currentFilter) ? ChatFormatting.GREEN : ChatFormatting.GRAY))
                        .collect(Collectors.toList())))
                .onClick((action) -> {
                    ServerPlayer sender = action.getPlayer();
                    Page page = new AllListings().getPage(currentFilter.getNext(), currentSort, null);
                    UIManager.openUIForcefully(sender, page);
                })
                .build();
    }
}

package org.pokesplash.gts.UI.button;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.AllListings;
import org.pokesplash.gts.UI.FilterType;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.Utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class SortButton {
    public static Button getButton(FilterType currentFilter, Sort currentSort) {
        return GooeyButton.builder()
                .display(Utils.parseItemId(Gts.language.getSortButtonItem()))
                .title(Gts.language.getSortButtonLabel())
                .lore(Component.class, Arrays.stream(Sort.values())
                        .map(sortType -> Component.literal(" - " + sortType.name)
                            .withStyle(sortType.equals(currentSort) ? ChatFormatting.GREEN : ChatFormatting.GRAY))
                        .collect(Collectors.toList()))
                .onClick((action) -> {
                    ServerPlayer sender = action.getPlayer();
                    Page page = new AllListings().getPage(currentFilter, currentSort.getNext(), null);
                    UIManager.openUIForcefully(sender, page);
                })
                .build();
    }
}

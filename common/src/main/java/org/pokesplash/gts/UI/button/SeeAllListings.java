package org.pokesplash.gts.UI.button;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.Page;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.UI.AllListings;
import org.pokesplash.gts.UI.FilterType;
import org.pokesplash.gts.UI.PokemonListings;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.Utils;

public abstract class SeeAllListings {
    public static Button getButton() {
        return GooeyButton.builder()
                .display(Utils.parseItemId(Gts.language.getAllListingsButtonItem()))
                .hideFlags(FlagType.All)
                .title(Gts.language.getAllListingsButtonLabel())
                .onClick((action) -> {
                    ServerPlayer sender = action.getPlayer();
                    Page page = new AllListings().getPage(FilterType.ALL, Sort.NONE, null);
                    UIManager.openUIForcefully(sender, page);
                })
                .build();
    }
}

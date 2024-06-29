package org.pokesplash.gts.UI.button;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.Page;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.util.Utils;

public abstract class ExpiredListings {
    public static Button getButton() {
        return GooeyButton.builder()
                .display(Utils.parseItemId(Gts.language.getExpiredListingsButtonItem()))
                .title("§cExpired Listings")
                .onClick((action) -> {
                    ServerPlayer sender = action.getPlayer();
                    Page page = new org.pokesplash.gts.UI.ExpiredListings().getPage(action.getPlayer().getUUID());
                    UIManager.openUIForcefully(sender, page);
                })
                .build();
    }
}

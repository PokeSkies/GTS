package org.pokesplash.gts.UI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.ItemListing;
import org.pokesplash.gts.UI.button.ManageListings;
import org.pokesplash.gts.UI.button.*;
import org.pokesplash.gts.UI.module.ListingInfo;
import org.pokesplash.gts.enumeration.Sort;
import org.pokesplash.gts.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * UI of the Item Listings page.
 */
public class ItemListings {

	/**
	 * Method that returns the page.
	 * @return Pokemon Listings page.
	 */
	public Page getPage(Sort sort) {
		List<ItemListing> itmListings = Gts.listings.getItemListings();

		if (sort.equals(Sort.PRICE)) {
			itmListings.sort(Comparator.comparingDouble(ItemListing::getPrice));
		} else if (sort.equals(Sort.DATE)) {
			itmListings.sort(Comparator.comparingLong(ItemListing::getEndTime));
		} else if (sort.equals(Sort.NAME)) {
			itmListings.sort(Comparator.comparing(ItemListing::getListingName));
		}

		Button sortByPriceButton = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getSortByPriceButtonItem()))
				.title(Gts.language.getSortByPriceButtonLabel())
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new ItemListings().getPage(Sort.PRICE);
					UIManager.openUIForcefully(sender, page);
				})
				.build();

		Button sortByNewestButton = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getSortByNewestButtonItem()))
				.title(Gts.language.getSortByNewestButtonLabel())
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new ItemListings().getPage(Sort.DATE);
					UIManager.openUIForcefully(sender, page);
				})
				.build();

		Button sortByNameButton = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getSortByNameButtonItem()))
				.title(Gts.language.getSortByNameButtonLabel())
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new ItemListings().getPage(Sort.NAME);
					UIManager.openUIForcefully(sender, page);
				})
				.build();

		PlaceholderButton placeholder = new PlaceholderButton();

		List<Button> itemButtons = new ArrayList<>();
		for (ItemListing listing : itmListings) {
			Collection<Component> lore = ListingInfo.parse(listing);

			Button button = GooeyButton.builder()
					.display(listing.getListing())
					.title("§3" + Utils.capitaliseFirst(listing.getListingName()))
					.lore(Component.class, lore)
					.hideFlags(FlagType.All)
					.onClick((action) -> {
						ServerPlayer sender = action.getPlayer();
						Page page = new SingleItemListing().getPage(sender, listing);
						UIManager.openUIForcefully(sender, page);
					})
					.build();
			itemButtons.add(button);
		}

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(Filler.getButton())
				.set(47, sortByPriceButton)
				.set(48, sortByNewestButton)
				.set(49, sortByNameButton)
				.set(50, SeePokemonListings.getButton())
				.set(51, ManageListings.getButton())
				.set(53, NextPage.getButton())
				.set(45, PreviousPage.getButton())
				.set(52, RelistAll.getButton())
				.build();

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, itemButtons, null);
		page.setTitle(Gts.language.getItemListingsTitle());

		setPageTitle(page);

		return page;
	}

	private void setPageTitle(LinkedPage page) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle(Gts.language.getItemListingsTitle());
			setPageTitle(next);
		}
	}
}

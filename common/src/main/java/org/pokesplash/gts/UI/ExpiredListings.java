package org.pokesplash.gts.UI;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.cobblemon.mod.common.item.PokemonItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.pokesplash.gts.Gts;
import org.pokesplash.gts.Listing.ItemListing;
import org.pokesplash.gts.Listing.PokemonListing;
import org.pokesplash.gts.UI.module.PokemonInfo;
import org.pokesplash.gts.util.Utils;

import java.util.*;

/**
 * UI of the Manage Listings page.
 */
public class ExpiredListings {

	/**
	 * Method that returns the page.
	 * @return Pokemon Listings page.
	 */
	public Page getPage(UUID owner) {

		List<PokemonListing> pkmListings = Gts.listings.getExpiredPokemonListings(owner);
		List<ItemListing> itmListings = Gts.listings.getExpiredItemListings(owner);

		Button seePokemonListings = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getPokemon_listing_display()))
				.hideFlags(FlagType.All)
				.title("§9See Pokemon Listings")
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new PokemonListings().getPage(PokemonListings.SORT.NONE);
					UIManager.openUIForcefully(sender, page);
				})
				.build();

		Button seeItemListings = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getItem_listing_display()))
				.title("§9See Item Listings")
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new ItemListings().getPage(ItemListings.SORT.NONE);
					UIManager.openUIForcefully(sender, page);
				})
				.build();

		Button manageListings = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getManage_listing_display()))
				.title("§dManage Listings")
				.onClick((action) -> {
					ServerPlayer sender = action.getPlayer();
					Page page = new ManageListings().getPage(action.getPlayer().getUUID());
					UIManager.openUIForcefully(sender, page);
				})
				.build();


		LinkedPageButton nextPage = LinkedPageButton.builder()
				.display(Utils.parseItemId(Gts.language.getNext_page_display()))
				.title("§7Next Page")
				.linkType(LinkType.Next)
				.build();

		LinkedPageButton previousPage = LinkedPageButton.builder()
				.display(Utils.parseItemId(Gts.language.getPrevious_page_display()))
				.title("§7Previous Page")
				.linkType(LinkType.Previous)
				.build();


		PlaceholderButton placeholder = new PlaceholderButton();

		List<Button> pokemonButtons = new ArrayList<>();
		if (pkmListings != null) {
			for (PokemonListing listing : pkmListings) {
				Collection<Component> lore = new ArrayList<>();

				lore.add(Component.literal("§9Seller: §b" + listing.getSellerName()));
				lore.add(Component.literal("§9Price: §b" + listing.getPrice()));
				lore.add(Component.literal("§9Time Remaining: §b" + Utils.parseLongDate(listing.getEndTime() - new Date().getTime())));
				lore.addAll(PokemonInfo.parse(listing));

				Button button = GooeyButton.builder()
						.display(PokemonItem.from(listing.getListing(), 1))
						.title("§3" + Utils.capitaliseFirst(listing.getListing().getSpecies().toString()))
						.lore(Component.class, lore)
						.onClick((action) -> {
							ServerPlayer sender = action.getPlayer();
							Page page = new ExpiredPokemonListing().getPage(listing);
							UIManager.openUIForcefully(sender, page);
						})
						.build();
				pokemonButtons.add(button);
			}
		}


		List<Button> itemButtons = new ArrayList<>();
		if (itmListings != null) {
			for (ItemListing listing : itmListings) {
				Collection<String> lore = new ArrayList<>();

				lore.add("§9Seller: §b" + listing.getSellerName());
				lore.add("§9Price: §b" + listing.getPrice());
				lore.add("§9Time Remaining: §b" + Utils.parseLongDate(listing.getEndTime() - new Date().getTime()));

				Button button = GooeyButton.builder()
						.display(listing.getListing())
						.title("§3" + Utils.capitaliseFirst(listing.getListing().getDisplayName().getString()))
						.lore(lore)
						.onClick((action) -> {
							ServerPlayer sender = action.getPlayer();
							Page page = new ExpiredItemListing().getPage(listing);
							UIManager.openUIForcefully(sender, page);
						})
						.build();
				itemButtons.add(button);
			}
		}

		Button filler = GooeyButton.builder()
				.display(Utils.parseItemId(Gts.language.getFiller_item()))
				.hideFlags(FlagType.All)
				.lore(new ArrayList<>())
				.title("")
				.build();

		ChestTemplate template = ChestTemplate.builder(6)
				.rectangle(0, 0, 5, 9, placeholder)
				.fill(filler)
				.set(48, seePokemonListings)
				.set(49, manageListings)
				.set(50, seeItemListings)
				.set(53, nextPage)
				.set(45, previousPage)
				.build();

		pokemonButtons.addAll(itemButtons);

		LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, pokemonButtons, null);
		page.setTitle("§3" + Gts.language.getTitle() + " - Expired");

		setPageTitle(page);

		return page;
	}

	private void setPageTitle(LinkedPage page) {
		LinkedPage next = page.getNext();
		if (next != null) {
			next.setTitle("§3" + Gts.language.getTitle() + " - Expired");
			setPageTitle(next);
		}
	}
}

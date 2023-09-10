package org.pokesplash.gts;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.pokesplash.gts.Listing.ItemListing;
import org.pokesplash.gts.Listing.ListingsProvider;
import org.pokesplash.gts.command.basecommand.GtsCommand;
import org.pokesplash.gts.config.Config;
import org.pokesplash.gts.config.Lang;
import org.pokesplash.gts.expired.ExpiredProvider;
import org.pokesplash.gts.timer.TimerProvider;
import org.pokesplash.gts.util.CommandsRegistry;
import org.pokesplash.gts.util.GtsLogger;
import org.pokesplash.gts.util.Permissions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Gts
{
	public static final String MOD_ID = "gts";
	public static final Config config = new Config();
	public static final Permissions permissions = new Permissions();
	public static ListingsProvider listings = new ListingsProvider();
	public static ExpiredProvider history = new ExpiredProvider();
	public static TimerProvider timers = new TimerProvider();
	public static final GtsLogger LOGGER = new GtsLogger();
	public static final Lang language = new Lang();

	public static void init() {
		CommandsRegistry.addCommand(new GtsCommand());
		CommandRegistrationEvent.EVENT.register(CommandsRegistry::registerCommands);
		config.init();
		listings.init();
		history.init();
		language.init();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
//				for (ItemListing listing : listings.getItemListings()) {
//					System.out.println(new ItemStack(listing.getItem()).toString());
//				}
//				for (int x=0; x<100; x++) {
//					listings.addItemListing(new ItemListing(UUID.randomUUID(), "bencrow11", x+10, x,
//							Item.byId(x + 100)));
//				}
			}
		}, 1000 * 30);
	}
}

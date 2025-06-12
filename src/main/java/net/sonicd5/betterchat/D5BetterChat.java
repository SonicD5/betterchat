package net.sonicd5.betterchat;

import net.fabricmc.api.ModInitializer;

import net.minecraft.network.message.SentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class D5BetterChat implements ModInitializer {
	public static final String MOD_ID = "d5betterchat";
	public static final Logger LOGGER = LoggerFactory.getLogger("SonicD5's Better Chat");

	@Override
	public void onInitialize() {
		SentMessage.Chat
	}
}
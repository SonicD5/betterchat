package net.sonicd5.betterchat;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class D5BetterChat implements ModInitializer {
	public static final String MOD_ID = "d5betterchat";
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		var cfg = D5BetterChatConfig.load();
	}

	public static Identifier id(String path) { return Identifier.of(MOD_ID, path); }

}
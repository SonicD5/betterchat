package net.sonicd5.betterchat;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Function;

public class D5BetterChatConfig {
    public static final D5BetterChatConfig DEFAULT = new D5BetterChatConfig(null, null);
    private static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(D5BetterChatConfig.class, new Serializer()).setPrettyPrinting().create();
    private static final File FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            D5BetterChat.MOD_ID + ".json"
    );
    Identifier font = Style.DEFAULT_FONT_ID;
    Identifier texture = D5BetterChat.id("textures/gui/default.png");

    private D5BetterChatConfig(
            Identifier font,
            Identifier texture
    ) {
        if (font != null) this.font = font;
        if (font != null) this.texture = texture;
    }

    public static D5BetterChatConfig load() {
        if (!FILE.exists()) save(DEFAULT);
        try (var reader = new FileReader(FILE)) {
            return GSON.fromJson(reader, D5BetterChatConfig.class);
        } catch (IOException e) {
            D5BetterChat.LOGGER.error("Failed to load config", e);
        }
        return DEFAULT;
    }

    public static void save(D5BetterChatConfig config) {
        try (var writer = new FileWriter(FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            D5BetterChat.LOGGER.error("Failed to save config", e);
        }
    }

    public Identifier getFont() {
        return font;
    }

    public Identifier getTexture() {
        return texture;
    }

    public static class Serializer implements JsonSerializer<D5BetterChatConfig>, JsonDeserializer<D5BetterChatConfig> {
        private static final Function<JsonElement, JsonParseException> PARSE_EXCEPTION = e ->
                new JsonParseException("Don't know how to turn " + e + " into a " + D5BetterChatConfig.class.getName());
        private static final Function<JsonElement, Identifier> IDENTIFIER_DESERIALIZER = e -> {
            if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) return Identifier.tryParse(e.getAsString());
            else throw PARSE_EXCEPTION.apply(e);
        };

        private <T> T deserializeElement(
                String name, JsonObject obj,
                Function<JsonElement, T> deserializer
        ) {
            var e = obj.get(name);
            if (obj.has(name) && !e.isJsonNull()) return deserializer.apply(e);
            return null;
        }

        @Override
        public D5BetterChatConfig deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            var jsonObj = element.getAsJsonObject();
            return new D5BetterChatConfig(
                    deserializeElement("font", jsonObj, IDENTIFIER_DESERIALIZER),
                    deserializeElement("texture", jsonObj, IDENTIFIER_DESERIALIZER)
            );
        }

        @Override
        public JsonElement serialize(D5BetterChatConfig config, Type type, JsonSerializationContext jsonSerializationContext) {
            var json = new JsonObject();

            if (config.font != null) json.addProperty("font", config.font.toString());
            if (config.texture != null) json.addProperty("texture", config.texture.toString());

            return json;
        }
    }
}

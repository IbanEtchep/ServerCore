package fr.iban.bungeecore.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

public class ChatUtils {
    
    private ChatUtils() {}
    
    @SuppressWarnings("deprecation")
    public static HoverEvent getShowTextHoverEvent(String text) {
	return new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder(text).create());
    }
    
    @SuppressWarnings("deprecation")
	public static HoverEvent getShowTextHoverEvent(BaseComponent[] text) {
	return new HoverEvent(Action.SHOW_TEXT, text);
    }
    
    public static ClickEvent getCommandClickEvent(String command) {
    	return new ClickEvent(ClickEvent.Action.RUN_COMMAND, command);
    }
    
    public static ClickEvent getSuggestCommandClickEvent(String command) {
    	return new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command);
    }

	public static String translateColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}

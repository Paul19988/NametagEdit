package com.nametagedit.plugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HexText {

    private final String text;

    public HexText(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

    public HexText translateColorCodes() {
        return new HexText(ChatColor.translateAlternateColorCodes('&', this.text));
    }

    public HexText parseHex() {
        return new HexText((HexParser.parseHexText(this.text, HexParser.findHexIndexes(this.text))).text);
    }

    public HexText append(String text) {
        return new HexText((this.text + text));
    }

    public HexText append(HexText hexText) {
        return append(hexText.text);
    }

    public void send(CommandSender sender) {
        sender.sendMessage(translateColorCodes().parseHex().toString());
    }

}

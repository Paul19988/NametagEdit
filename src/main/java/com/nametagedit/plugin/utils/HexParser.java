package com.nametagedit.plugin.utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HexParser {

    static List<Integer> findHexIndexes(String text) {
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0;; i++) {
            int index = text.indexOf("&#", i);
            if(index == -1)
                break;
            indexes.add(Integer.valueOf(index));
        }
        return indexes;
    }

    static HexText parseHexText(String text, List<Integer> indexes) {
        int HEX_LENGTH = 7;
        StringBuilder newText = new StringBuilder();
        StringBuilder currentHex = new StringBuilder();
        boolean isInHex = false;
        for(int i = 0; i < text.length(); i++) {
            if(indexes.contains(Integer.valueOf(i))) {
                isInHex = true;
            }else if(isInHex) {
                currentHex.append(text.charAt(i));
                if(currentHex.length() == 7) {
                    isInHex = false;
                    newText.append(nearestColor(currentHex.toString()));
                    currentHex.setLength(0);
                }
            }else{
                newText.append(text.charAt(i));
            }
        }
        return new HexText(newText.toString());
    }

    private static String nearestColor(String hex) {
        if(checkHexSupport()) {
            return ChatColor.of(hex).toString();
        }
        Color awtColor = Color.decode(hex);
        ChatColor nearestColor = ChatColor.WHITE;
        double shorterDistance = -1.0D;
        for(ChatColor chatColor : ChatColor.values()) {
            org.bukkit.Color color = getChatColorPaint(chatColor, awtColor);
            if(color != null) {
                double distance = calcColorDistance(awtColor, color);
                if(shorterDistance == -1.0D || distance < shorterDistance) {
                    nearestColor = chatColor;
                    shorterDistance = distance;
                }
            }
        }
        return nearestColor.toString();
    }

    private static org.bukkit.Color getChatColorPaint(ChatColor chatColor, Color awtColor) {
        if(awtColor.getRed() == awtColor.getBlue() && awtColor.getBlue() == awtColor.getGreen()) {
            if(ChatColor.BLACK.equals(chatColor)) {
                return org.bukkit.Color.BLACK;
            }
            if(ChatColor.DARK_GRAY.equals(chatColor)) {
                return org.bukkit.Color.GRAY;
            }
            if(ChatColor.GRAY.equals(chatColor)) {
                return org.bukkit.Color.SILVER;
            }
            if(ChatColor.WHITE.equals(chatColor)) {
                return org.bukkit.Color.WHITE;
            }
        }
        if (ChatColor.AQUA.equals(chatColor))
            return org.bukkit.Color.AQUA;
        if (ChatColor.BLUE.equals(chatColor))
            return org.bukkit.Color.BLUE;
        if (ChatColor.DARK_BLUE.equals(chatColor))
            return org.bukkit.Color.BLUE;
        if (ChatColor.DARK_AQUA.equals(chatColor))
            return org.bukkit.Color.TEAL;
        if (ChatColor.GREEN.equals(chatColor))
            return org.bukkit.Color.LIME;
        if (ChatColor.DARK_GREEN.equals(chatColor))
            return org.bukkit.Color.GREEN;
        if (ChatColor.DARK_PURPLE.equals(chatColor))
            return org.bukkit.Color.PURPLE;
        if (ChatColor.LIGHT_PURPLE.equals(chatColor))
            return org.bukkit.Color.FUCHSIA;
        if (ChatColor.RED.equals(chatColor))
            return org.bukkit.Color.RED;
        if (ChatColor.DARK_RED.equals(chatColor))
            return org.bukkit.Color.MAROON;
        if (ChatColor.YELLOW.equals(chatColor))
            return org.bukkit.Color.YELLOW;
        if (ChatColor.GOLD.equals(chatColor))
            return org.bukkit.Color.ORANGE;
        return null;
    }

    private static double calcColorDistance(Color awtColor, org.bukkit.Color color) {
        return Math.sqrt(Math.pow((awtColor.getRed() - color.getRed()), 2.0D) + Math.pow((awtColor.getGreen() - color.getGreen()), 2.0D) + Math.pow((awtColor.getBlue() - color.getBlue()), 2.0D));
    }

    private static boolean checkHexSupport() {
        try{
            ChatColor.of(Color.BLACK);
            return true;
        }catch(NoSuchMethodError e) {
            return false;
        }
    }

}

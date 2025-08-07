package me.nv.utility;

import me.nv.Core;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class GeneralUtility {

    public static void log(String info) {
        Bukkit.getConsoleSender().sendMessage(info);
    }

    public static String translate(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    public static void sendActionBar(Player p, String nachricht) {
        if (p != null) {
            Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), () ->
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(nachricht))
            );
        }
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getRomanNumeral(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999.");
        }
        String[] romanNumerals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (number > 0) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(romanNumerals[i]);
            }
            i++;
        }
        return result.toString();
    }

    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("§[0-9A-Fa-fK-ORk-o]");

    public static String stripColorCodes(String input) {
        return COLOR_CODE_PATTERN.matcher(input).replaceAll("");
    }

    public static char getLastColorCode(String input) {
        char lastColorCode = 'f';
        for (int i = input.length() - 1; i >= 0; i--) {
            char currentChar = input.charAt(i);
            if (currentChar == '&' && i + 1 < input.length()) {
                char colorCode = input.charAt(i + 1);
                if (isColorCode(colorCode)) {
                    lastColorCode = colorCode;
                    break;
                }
            }
        }
        return lastColorCode;
    }

    private static boolean isColorCode(char code) {
        return ("0123456789aAbCcDdEeFkKlLmMnNoOrR".indexOf(code) != -1);
    }

    public static Location stringToLocation(Player player, String coords) {
        double x, y, z;
        if (player == null || coords == null || coords.isEmpty()) {
            return null;
        }
        String[] parts = coords.split(" ");
        if (parts.length != 3) {
            return null;
        }
        try {
            x = Double.parseDouble(parts[0]);
            y = Double.parseDouble(parts[1]);
            z = Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            return null;
        }
        World world = player.getWorld();
        return new Location(world, x, y, z, 0.0F, 0.0F);
    }

    public static int getRomanNumeralValue(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            char currentChar = roman.charAt(i);
            int currentValue = getValue(currentChar);
            if (i + 1 < roman.length()) {
                char nextChar = roman.charAt(i + 1);
                int nextValue = getValue(nextChar);
                if (nextValue > currentValue) {
                    result -= currentValue;
                } else {
                    result += currentValue;
                }
            } else {
                result += currentValue;
            }
        }
        return result;
    }

    private static int getValue(char romanChar) {
        return switch (romanChar) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }

    public static String convertToDecimal(int number) {
        return String.format("%.1f", number / 10.0F);
    }

    public static int convertFromDecimal(String number) {
        return Math.round(Float.parseFloat(number) * 10.0F);
    }

}

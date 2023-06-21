package ru.dimianas.colortext;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class ColorText extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }
    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        if (getConfig().getBoolean("ColorText.enable")) {

            BookMeta bookMeta = event.getNewBookMeta();
            String[] pages = bookMeta.getPages().toArray(new String[0]);

            for (int i = 0; i < pages.length; i++) {
                String page = pages[i];
                String coloredPage = ChatColor.translateAlternateColorCodes('&', page);
                pages[i] = coloredPage;
            }

            bookMeta.setPages(pages);
            event.setNewBookMeta(bookMeta);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("colort")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("colortext.reload")) {
                    reloadConfig();
                    saveDefaultConfig();
                    String message = getConfig().getString("Reload-Config.reload-message");
                    assert message != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&' , message));
                } else {
                    String perm_message = getConfig().getString("Reload-Config.not-permission");
                    assert perm_message != null;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&' , perm_message));
                }
                return true;
            }
        }
        return false;
    }
}

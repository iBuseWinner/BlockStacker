package ru.ibusewinner.fundaily.blockstacker.addon.Commands.Console;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.ibusewinner.fundaily.blockstacker.addon.BSAddon;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.IStack;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.MessageManager;
import ru.ibusewinner.fundaily.blockstacker.addon.Utils.Worth;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.user.User;

import java.util.List;

public class BSCommand extends CompositeCommand {
    private MessageManager mm = BSAddon.get().mm;

    public BSCommand(BSAddon addon) {
        super(addon, "blockStacker", "bs");
    }

    @Override
    public void setup() {
        setOnlyPlayer(false);
        setPermission("blockstacker");
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        if(args.isEmpty()) {
            mm.sendUsage(user);
        } else {
            if(args.size() == 1) {
                if(args.get(0).equalsIgnoreCase("list")) {
                    sendList(user);
                } else {
                    mm.sendUsage(user);
                }
            } else if(args.size() == 2) {
                if(args.get(0).equalsIgnoreCase("getworth")) {
                    int worth = Worth.getWorth(Material.valueOf(args.get(1).toUpperCase()));
                    user.sendMessage(mm.applyCC("&7> Цена этого блока: &a"+worth));
                } else {
                    mm.sendUsage(user);
                }
            } else if(args.size() == 3) {
                if(args.get(0).equalsIgnoreCase("setworth")) {
                    String type = args.get(1);
                    try {
                        int worth = Integer.parseInt(args.get(2));

                        if(type.equalsIgnoreCase("IRON_BLOCK")) {
                            Worth.IRON_BLOCK = worth;
                            user.sendMessage(mm.applyCC("&7> Успешно установлена цена &2$&a"+worth+"&7 предмету &a"+type.toUpperCase()));
                        } else if(type.equalsIgnoreCase("GOLD_BLOCK")) {
                            Worth.GOLD_BLOCK = worth;
                            user.sendMessage(mm.applyCC("&7> Успешно установлена цена &2$&a"+worth+"&7 предмету &a"+type.toUpperCase()));
                        } else if(type.equalsIgnoreCase("DIAMOND_BLOCK")) {
                            Worth.DIAMOND_BLOCK = worth;
                            user.sendMessage(mm.applyCC("&7> Успешно установлена цена &2$&a"+worth+"&7 предмету &a"+type.toUpperCase()));
                        } else if(type.equalsIgnoreCase("EMERALD_BLOCK")) {
                            Worth.EMERALD_BLOCK = worth;
                            user.sendMessage(mm.applyCC("&7> Успешно установлена цена &2$&a"+worth+"&7 предмету &a"+type.toUpperCase()));
                        } else {
                            user.sendMessage(mm.applyCC("&c> Предмет не найден!"));
                        }
                    }catch (NumberFormatException ex) {
                        user.sendMessage(mm.applyCC("&c> Пожалуйста, укажите реальное число!"));
                    }
                } else {
                    mm.sendUsage(user);
                }
            } else if(args.size() == 4) {
                if(args.get(0).equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args.get(1));
                    if(target == null) {
                        user.sendMessage(mm.applyCC("&c> Игрок не найден!"));
                    } else {
                        String stackerType = args.get(2);
                        int count = 0;
                        try {
                            count = Integer.parseInt(args.get(3));
                        }catch (NumberFormatException ex) {
                            user.sendMessage(mm.applyCC("&c> Пожалуйста, укажите реальное число!"));
                        }

                        if(count == 0) {
                            user.sendMessage(mm.applyCC("&7> Я не могу выдавать 0 предметов!"));
                        } else {
                            if(count < 0) {
                                user.sendMessage(mm.applyCC("&c> Как я тебе выдам отрицательное количество предметов? Ты еблан!"));
                            } else {
                                for(IStack iStack : BSAddon.stackers) {
                                    if(iStack.getCfgName().equalsIgnoreCase(stackerType)) {
                                        target.getInventory().addItem(BSAddon.get().blockFactory.getStacker(iStack.getCfgName(), 1));
                                        user.sendMessage(mm.applyCC("&7> Блок &a"+iStack.getCfgName()+"&7 выдан игроку &a"+target.getName()+"&7 в количестве &a"+count+"шт&7!"));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    mm.sendUsage(user);
                }
            } else {
                mm.sendUsage(user);
            }
        }
        return false;
    }

    private void sendList(User user) {
        user.sendMessage(mm.applyCC("&7> Список стакеров и немного инфы о них:"));
        user.sendMessage("§0");
        for(IStack iStack : BSAddon.stackers) {
            user.sendMessage(mm.applyCC("&7> Название: &a"+iStack.getCfgName()+"&7, Блок(и): &a"+iStack.getAllowedBlocks().toString()+"&7, Вместимость: &a"+iStack.getMax()));
        }
    }
}

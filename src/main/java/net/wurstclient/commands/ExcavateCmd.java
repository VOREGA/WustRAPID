/*
 * Copyright (c) 2014-2025 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.commands;

import net.minecraft.util.math.BlockPos;
import net.wurstclient.WurstClient;
import net.wurstclient.command.Command;
import net.wurstclient.hacks.ExcavatorHack;

public final class ExcavateCmd extends Command {
    public ExcavateCmd() {
        super("excavate", "Bot mantığıyla özel koordinatlı kazıyı başlatır.",
            ".excavate <x1> <y1> <z1> <x2> <y2> <z2>");
    }

    @Override
    public void call(String[] args) {
        if (args.length != 6) {
            WURST.getGui().getChat().addMessage("Hatalı kullanım! Örnek: .excavate 6491 62 -3089 6516 54 -3069");
            return;
        }

        try {
            int x1 = Integer.parseInt(args[0]);
            int y1 = Integer.parseInt(args[1]);
            int z1 = Integer.parseInt(args[2]);
            int x2 = Integer.parseInt(args[3]);
            int y2 = Integer.parseInt(args[4]);
            int z2 = Integer.parseInt(args[5]);

            ExcavatorHack hack = WURST.getHax().excavatorHack;
            hack.setEnabled(true);
            hack.enableWithArea(new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
            
            WURST.getGui().getChat().addMessage("Kazı hedefi ayarlandı ve başlatıldı.");
        } catch (NumberFormatException e) {
            WURST.getGui().getChat().addMessage("Hata: Koordinatlar sayı olmalıdır!");
        }
    }
}
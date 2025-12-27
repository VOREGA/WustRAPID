package net.wurstclient.commands;

import net.wurstclient.WurstClient;
import net.wurstclient.command.Command;
import net.wurstclient.hacks.ExcavatorHack;

public final class ExcavateCmd extends Command
{
    public ExcavateCmd()
    {
        super("excavate", "Özel koordinatlı kazıyı başlatır.",
            ".excavate <x1> <y1> <z1> <x2> <y2> <z2>");
    }

    @Override
    public void call(String[] args) throws Exception
    {
        if(args.length != 6)
        {
            addErrorMessage("Hatalı kullanım! Örnek:");
            addErrorMessage(".excavate 6491 62 -3089 6516 54 -3069");
            return;
        }

        // Sayıları oku
        int x1 = Integer.parseInt(args[0]);
        int y1 = Integer.parseInt(args[1]);
        int z1 = Integer.parseInt(args[2]);
        int x2 = Integer.parseInt(args[3]);
        int y2 = Integer.parseInt(args[4]);
        int z2 = Integer.parseInt(args[5]);

        // Excavator hilesini bul
        ExcavatorHack hack = WurstClient.INSTANCE.getHax().excavatorHack;
        
        // Hileyi senin koordinatlarınla başlat
        hack.setEnabled(true);
        hack.startCustomExcavate(x1, y1, z1, x2, y2, z2);
        
        addMessage("Kazı hedefi ayarlandı ve başlatıldı.");
    }
}
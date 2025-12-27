package net.wurstclient.hacks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.wurstclient.Category;
import net.wurstclient.WurstClient;
import net.wurstclient.hack.Hack;
import net.wurstclient.util.BlockUtils;
import net.wurstclient.util.RotationUtils;

public final class ExcavatorHack extends Hack {

    // --- SENİN DEĞİŞKENLERİN ---
    private int minX, minY, minZ, maxX, maxY, maxZ;
    private int curX, curY, curZ;
    private boolean isRunning = false;

    public ExcavatorHack() {
        super("Excavator", "Alan temizleme hilesi (Bot mantığıyla güncellendi).");
        setCategory(Category.BLOCKS);
    }

    // Komuttan gelen veriyi set eden metod
    public void startCustomExcavate(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);

        // Kazıya senin botun gibi en üstten (maxY) başla
        this.curY = maxY;
        this.curZ = minZ;
        this.curX = minX;
        this.isRunning = true;
    }

    @Override
    public void onUpdate() {
        if (!isRunning) return;

        BlockPos targetPos = new BlockPos(curX, curY, curZ);
        BlockState state = mc.world.getBlockState(targetPos);

        // 1. Blok kazılabilir mi kontrol et (Hava veya kırılamayanlar hariç)
        if (!state.isAir() && state.getBlock() != Blocks.BEDROCK && state.getBlock() != Blocks.BARRIER) {
            
            // Mesafe Kontrolü (Senin botundaki 2.5 blok kuralı)
            if (mc.player.squaredDistanceTo(targetPos.toCenterPos()) > 6.25) { // 2.5'in karesi
                WurstClient.INSTANCE.getPathFinder().setGoal(targetPos); // Uzaktaysa yaklaş
                return;
            }

            // --- KAZMA ADIMLARI ---
            BlockUtils.equipBestTool(state); // En iyi aleti al
            RotationUtils.lookAt(targetPos); // Bloğa bak
            mc.interactionManager.breakBlock(targetPos); // Bloğu kır
            
            // Doğrulama (Verification): Blok hava olana kadar bir sonraki tick'e geçme
            if (!mc.world.getBlockState(targetPos).isAir()) return;
        }

        // 2. SIRADAKİ BLOĞA GEÇİŞ (X -> Z -> Y sırasıyla)
        curX++;
        if (curX > maxX) {
            curX = minX;
            curZ++;
            if (curZ > maxZ) {
                curZ = minZ;
                curY--; // Bir alt kata in (Y ekseni)
                if (curY < minY) {
                    isRunning = false;
                    setEnabled(false);
                    addMessage("Kazı görevi başarıyla tamamlandı!");
                }
            }
        }
    }

    @Override
    public void onDisable() {
        isRunning = false;
        super.onDisable();
    }
}
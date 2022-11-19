package sjkz1.com.stack_mob.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.stack_mob.StackMob;

@Mod(StackMob.MOD_ID)
public class StackMobForge {
    public StackMobForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(StackMob.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        StackMob.init();
    }
}
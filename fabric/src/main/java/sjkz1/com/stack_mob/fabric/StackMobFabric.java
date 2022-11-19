package sjkz1.com.stack_mob.fabric;

import net.fabricmc.api.ModInitializer;
import sjkz1.com.stack_mob.StackMob;

public class StackMobFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        StackMob.init();
    }
}
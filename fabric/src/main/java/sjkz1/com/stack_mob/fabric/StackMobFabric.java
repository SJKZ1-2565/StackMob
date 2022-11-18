package sjkz1.com.stack_mob.fabric;

import sjkz1.com.stack_mob.StackMob;
import net.fabricmc.api.ModInitializer;

public class StackMobFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        StackMob.init();
    }
}
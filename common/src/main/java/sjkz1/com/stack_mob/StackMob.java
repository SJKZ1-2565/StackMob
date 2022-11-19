package sjkz1.com.stack_mob;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class StackMob {
    public static final String MOD_ID = "stack_mob";

    public static void init() {
        System.out.println(MOD_ID + " is running...");
    }

    public static Component stackedEntityName(long count, String typeName) {
        var format = count <= 50 ? 0xffff00 : count <= 100 ? 0xbfff00 : count <= 500 ? 0x80ff00 : count <= 1000 ? 0x40ff00 : count <= 2000 ? 0x8000ff : count <= 3000 ? 0xbf00ff : count <= 4000 ? 0xff00ff : count <= 5000 ? 0xff00bf : count <= 6000 ? 0xff0080 : 0xff0040;
        return Component.literal("x" + count).setStyle(Style.EMPTY.withBold(true).withColor(format)).append(Component.literal(" " + typeName).setStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA)));
    }
}
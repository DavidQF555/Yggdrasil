package io.github.davidqf555.minecraft.yggdrasil;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("yggdrasil")
public class Yggdrasil {

    public static final String ID = "yggdrasil";

    public Yggdrasil() {
        MinecraftForge.EVENT_BUS.register(this);
    }

}

package com.inolia_zaicek.mine_fargo.Register.Key;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MyClientHandler {
    // 注册按键映射方法，接受Forge事件RegisterKeyMappingsEvent作为参数
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        // 创建第一个按键映射，描述为"jcrj.copy.recipe.json"，默认无绑定键(-1)，类别为"jei.key.category.dev.tools"
        event.register(MyKeyMappingUtil.KEYMAPPING = new KeyMapping(
                "mine_fargo.key.anchor_soul_stone_skill",
                GLFW.GLFW_KEY_Y,  // -1表示默认不绑定任何键，GLFW.GLFW_KEY_C则是C键
                "mine_fargo.key" // 按键归类于"XXX"
        ));
        event.register(MyKeyMappingUtil.KEYMAPPING2 = new KeyMapping(
                "mine_fargo.key.magnet_soul_stone_skill",
                GLFW.GLFW_KEY_I,  // -1表示默认不绑定任何键，GLFW.GLFW_KEY_C则是C键
                "mine_fargo.key" // 按键归类于"XXX"
        ));
    }
}
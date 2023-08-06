package com.ohussar.conduittest.Core.Rendering;

import com.ohussar.conduittest.ConduitMain;
import com.ohussar.conduittest.Core.Rendering.Models.Model;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ModCustomModels {

    private static Map<String, Model> modelMap = new HashMap<String, Model>();
    private static Map<String, ResourceLocation> textureMap = new HashMap<>();

    public static void registerModel(String key, Model model){
        modelMap.put(key, model);
    }

    public static void assembleTextures(){
    }


    public static Model getModel(String key){
        return modelMap.get(key);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event){
        for(String key : textureMap.keySet()){
            event.addSprite(textureMap.get(key));
        }
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event){
    }

}

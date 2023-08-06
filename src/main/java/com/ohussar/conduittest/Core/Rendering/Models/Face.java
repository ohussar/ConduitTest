package com.ohussar.conduittest.Core.Rendering.Models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ohussar.conduittest.Core.Rendering.ModRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

public class Face {

    private float left = 0;
    private float right = 0;
    private float down = 0;
    private float up = 0;
    private float tickness = 0;
    public int color = 0x00FFFFFF;
    private Direction side = null;
    private TextureAtlasSprite texture;


    public Face(float left, float right, float down, float up, float thickness, TextureAtlasSprite tex, Direction side){
        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
        this.tickness = thickness;
        this.texture = tex;
        this.side = side;
    }

    public void renderFace(VertexConsumer builder, PoseStack pose, int light){

        if(side != null && texture != null){
            ModRenderer.renderTiledFace(side, left, down, right, up, tickness, builder, pose, light, color, texture, 1);
        }
    }

}

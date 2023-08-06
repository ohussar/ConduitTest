package com.ohussar.conduittest.Core.Rendering.Models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Face> faces = new ArrayList<>();

    public void assembleFace(float left, float right, float up, float down, float tickness, Direction side, TextureAtlasSprite texture){
        faces.add(new Face(left, right, up, down, tickness, texture, side));
    }

    public void renderFaces(VertexConsumer buffer, PoseStack pose, int light){
        for(Face f : faces){
            f.renderFace(buffer, pose, light);
        }
    }

}

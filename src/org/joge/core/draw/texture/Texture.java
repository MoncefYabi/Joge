/*
 * Copyright (C) 2013 Moncef YABI
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.joge.core.draw.texture;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Moncef YABI
 */
public class Texture
{

    private final int target;
    private final int textureID;
    private int height;
    private int width;
    private int textureWidth;
    private int textureHeight;
    private float widthRatio;
    private float heightRatio;

    public Texture(int target, int textureID)
    {
        this.target = target;
        this.textureID = textureID;
    }

    public void bind()
    {
        GL11.glBindTexture(target, textureID);
    }

    public void setHeight(int height)
    {
        this.height = height;
        setHeight();
    }

    public void setWidth(int width)
    {
        this.width = width;
        setWidth();
    }

    public int getImageHeight()
    {
        return height;
    }

    public int getImageWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return heightRatio;
    }

    public float getWidth()
    {
        return widthRatio;
    }

    public void setTextureHeight(int texHeight)
    {
        this.textureHeight = texHeight;
        setHeight();
    }

    public void setTextureWidth(int texWidth)
    {
        this.textureWidth = texWidth;
        setWidth();
    }

    private void setHeight()
    {
        if (textureHeight != 0)
        {
            heightRatio = ((float) height) / textureHeight;
        }
    }

    private void setWidth()
    {
        if (textureWidth != 0)
        {
            widthRatio = ((float) width) / textureWidth;
        }
    }

    public static void rest()
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}

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
package org.joge.game.j2d;

import org.joge.game.tools.ToolKit;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Moncef YABI
 */
public class Image
{

    private Texture texture;
    private float x;
    private float y;
    private int width;
    private int height;
    private float tWidth;
    private float tHeight;
    private float tOffsetX = 0;
    private float tOffsetY = 0;
    private String ref;

    public Image()
    {
    }

    public Image(String ref)
    {
        this(ref, 0, 0);
    }

    public Image(String ref, float x, float y)
    {
        texture = ToolKit.getTexture(ref);
        this.x = x;
        this.y = y;
        this.ref = ref;
        if (texture != null)
        {
            width = texture.getImageWidth();
            height = texture.getImageHeight();
            tWidth = texture.getWidth();
            tHeight = texture.getHeight();
        }
    }

    public void draw(float xpos, float ypos)
    {
        texture.bind();
        bindColor();
        drawGL(xpos, ypos);
    }

    public void draw(float xpos, float ypos, Color col)
    {
        texture.bind();
        GL11.glColor3f(col.r, col.g, col.b);
        drawGL(xpos, ypos);
    }
    public void drawWithOutLoadIdentity(float xpos, float ypos)
    {
        GL11.glTranslatef(x + xpos, y + ypos, 0);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(tOffsetX, tOffsetY);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(tOffsetX, tHeight + tOffsetY);
            GL11.glVertex2f(0, height);
            GL11.glTexCoord2f(tWidth + tOffsetX, tHeight + tOffsetY);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(tWidth + tOffsetX, tOffsetY);
            GL11.glVertex2f(width, 0);
        }
        GL11.glEnd();
    }

    private void drawGL(float xpos, float ypos)
    {
        drawWithOutLoadIdentity(xpos, ypos);
        GL11.glLoadIdentity();
    }

    public void bind()
    {
        texture.bind();
    }

    private void bindColor()
    {
        Color f = Color.WHITE;
        f.bind();
    }

    public Image getSubImage(int x, int y, int width, int height)
    {
        float newTOffsetX = ((x / (float) this.width) * tWidth) + tOffsetX;
        float newTOffsetY = ((y / (float) this.height) * tHeight) + tOffsetY;
        float newTWidth = ((width / (float) this.width) * tWidth);
        float newTHeight = ((height / (float) this.height) * tHeight);
        Image sub = new Image();
        sub.texture = this.texture;
        sub.tOffsetX = newTOffsetX;
        sub.tOffsetY = newTOffsetY;
        sub.tWidth = newTWidth;
        sub.tHeight = newTHeight;

        sub.width = width;
        sub.height = height;
        sub.ref = ref;

        return sub;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }
}

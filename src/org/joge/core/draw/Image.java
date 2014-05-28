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
package org.joge.core.draw;

import org.joge.core.draw.texture.Texture;
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
    private int tw;
    private int th;
    private  int margin = 0;
    private Image[][] subImages;
    private int spacing;
    protected float alpha = 1.0f;

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

    public Image(Image img, int tw, int th, int margin, int spacing)
    {
        texture = img.getTexture();
        this.tw = tw;
        this.th = th;
        this.spacing = spacing;
        this.margin = margin;
        if (texture != null)
        {
            width = texture.getImageWidth();
            height = texture.getImageHeight();
            tWidth = texture.getWidth();
            tHeight = texture.getHeight();
        }
        initImpl();
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

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    protected final void initImpl()
    {
        if (subImages != null)
        {
            return;
        }

        int tilesAcross = ((getWidth() - (margin * 2) - tw) / (tw + spacing)) + 1;
        int tilesDown = ((getHeight() - (margin * 2) - th) / (th + spacing)) + 1;
        if ((getHeight() - th) % (th + spacing) != 0)
        {
            tilesDown++;
        }

        subImages = new Image[tilesAcross][tilesDown];
        for (int i = 0; i < tilesAcross; i++)
        {
            for (int j = 0; j < tilesDown; j++)
            {
                subImages[i][j] = getSprite(i, j);
            }
        }
    }

    public int getHorizontalCount()
    {
        initImpl();

        return subImages.length;
    }

    public int getVerticalCount()
    {
        initImpl();

        return subImages[0].length;
    }

    public Image getSprite(int x, int y)
    {
        initImpl();

        if ((x < 0) || (x >= subImages.length))
        {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }
        if ((y < 0) || (y >= subImages[0].length))
        {
            throw new RuntimeException("SubImage out of sheet bounds: " + x + "," + y);
        }

        return getSubImage(x * (tw + spacing) + margin, y * (th + spacing) + margin, tw, th);
    }

    public float getAlpha()
    {
        return alpha;
    }

    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }

    public void drawSubImages(int i, int i0, int sheetX, int sheetY)
    {
//        inUse.bind();
        bindColor();
        subImages[sheetX][sheetY].draw(i, i0);
    }

}

/*
 * Copyright (C) 2014 Moncef YABI
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
package org.joge.core.draw.font;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joge.core.draw.Color;
import org.joge.core.draw.texture.Texture;
import org.joge.core.draw.texture.TextureLoader;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author dell
 */
public class Font
{

    private int width;
    private int height;
    private float tWidth;
    private float tHeight;

    private final boolean antiAlias;

    private int fontSize = 0;

    private final java.awt.Font font;

    private FontMetrics fontMetrics;

    private final Texture charArray[] = new Texture[256];

    private final float tOffsetX = 0;
    private final float tOffsetY = 0;

    public Font(java.awt.Font font, boolean antiAlias)
    {
        this.antiAlias = antiAlias;
        this.font = font;
        fontSize = font.getSize();
        creatFont();
    }

    private void creatFont()
    {
        Texture tex = null;
        TextureLoader textureLoader = new TextureLoader();
        for (int i = 0; i < charArray.length; i++)
        {
            char ch = (char) i;
            try
            {
                tex = textureLoader.getTextureFromBuffer(getFontImage(ch));
                charArray[i] = tex;
            } catch (IOException ex)
            {
                Logger.getLogger(Font.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void render(String text, Color color, float xpos, float ypos)
    {
        Texture tex;
        char[] chars = text.toCharArray();
        float textWidth = 0;
        for (int i = 0; i < chars.length; i++)
        {
            tex = charArray[(int) chars[i]];
            width = tex.getImageWidth();
            height = tex.getImageHeight();
            tWidth = tex.getWidth();
            tHeight = tex.getHeight();
            tex.bind();
            GL11.glTranslatef(xpos+textWidth, ypos, 0);
            GL11.glColor3f(color.r, color.g, color.b);
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
            GL11.glLoadIdentity();
            textWidth += tex.getWidth() * fontSize ;
        }

    }

    private BufferedImage getFontImage(char ch)
    {
        // Create a temporary image to extract the character's size
        BufferedImage tempfontImage = new BufferedImage(1, 1,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
        if (antiAlias == true)
        {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        fontMetrics = g.getFontMetrics();
        int charwidth = fontMetrics.charWidth(ch);

        if (charwidth <= 0)
        {
            charwidth = 1;
        }
        int charheight = fontMetrics.getHeight();
        if (charheight <= 0)
        {
            charheight = fontSize;
        }

        // Create another image holding the character we are creating
        BufferedImage fontImage;
        fontImage = new BufferedImage(charwidth, charheight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D gt = (Graphics2D) fontImage.getGraphics();
        if (antiAlias == true)
        {
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        gt.setFont(font);

        gt.setColor(java.awt.Color.WHITE);
        int charx = 0;
        int chary = 0;
        gt.drawString(String.valueOf(ch), (charx), (chary)
                + fontMetrics.getAscent());

        return fontImage;

    }

}

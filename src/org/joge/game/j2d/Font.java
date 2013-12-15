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

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

/**
 *
 * @author Moncef YABI
 */
public class Font
{

    private int base;
    private int width;
    private int height;

    public Font(String fontPath)
    {

        Image img;

        base = glGenLists(256);

        img = new Image(fontPath);

        width = img.getWidth() / 16;
        height = img.getHeight() / 16;
        int z = -1;
        for (int i = 0; i < 16; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                z++;
                if (z < 256)
                {
                    glNewList(base + z, GL_COMPILE);

                    img.getSubImage(j * width, i * height, width, height).drawWithOutLoadIdentity(0, 0);
                    glEndList();
                }
            }
        }
    }

    public int getBase()
    {
        return base;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}

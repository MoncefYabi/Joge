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

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Moncef YABI
 */
public class Color
{

    public static final Color white = new Color(255, 255, 255);
    public final static Color WHITE = white;
    public static final Color yellow = new Color(255, 255, 0);
    public final static Color YELLOW = yellow;
    public static final Color red = new Color(255, 0, 0);
    public final static Color RED = red;
    public static final Color light_red = new Color(1f, 0.5f, 0.5f);
    public static final Color LIGHT_RED = light_red;
    public static final Color dark_red = new Color(139, 0, 0);
    public static final Color DARK_RED = dark_red;
    public static final Color blue = new Color(0, 0, 255);
    public final static Color BLUE = blue;
    public static final Color light_blue = new Color(173, 216, 230);
    public static final Color LIGHT_BLUE = light_blue;
    public static final Color dark_blue = new Color(0, 0, 139);
    public static final Color DARK_BLUE = dark_blue;
    public static final Color dark_green = new Color(0, 100, 0);
    public static final Color green = new Color(0, 255, 0);
    public static final Color light_green = new Color(144, 238, 144);
    public static final Color DARK_GREEN = dark_green;
    public final static Color GREEN = green;
    public static final Color LIGHT_GREEN = light_green;
    public static final Color black = new Color(0, 0, 0);
    public final static Color BLACK = black;
    public static final Color gray = new Color(128, 128, 128);
    public final static Color GRAY = gray;
    public static final Color cyan = new Color(0, 255, 255);
    public final static Color CYAN = cyan;
    public static final Color dark_Gray = new Color(64, 64, 64);
    public final static Color DARK_GRAY = dark_Gray;
    public static final Color light_Gray = new Color(192, 192, 192);
    public final static Color LIGHT_GRAY = light_Gray;
    public static final Color light_Gray_glass = new Color(192, 192, 192, 150);
    public final static Color LIGHT_GRAY_GLASS = light_Gray_glass;
    public final static Color pink = new Color(255, 175, 175);
    public final static Color PINK = pink;
    public final static Color orange = new Color(255, 200, 0);
    public final static Color ORANGE = orange;
    public final static Color magenta = new Color(255, 0, 255);
    public final static Color MAGENTA = magenta;
    public float r;
    public float g;
    public float b;
    public float a;

    public Color()
    {
    }

    public Color(Color color)
    {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    public Color(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
    }

    public Color(float r, float g, float b, float a)
    {
        this.r = Math.min(r, 1);
        this.g = Math.min(g, 1);
        this.b = Math.min(b, 1);
        this.a = Math.min(a, 1);
    }

    public Color(int r, int g, int b)
    {
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        this.a = 1;
    }

    public Color(int r, int g, int b, int a)
    {
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        this.a = a / 255.0f;
    }

    public Color getAlphaColor(int a)
    {
        this.a = a / 255.0f;
        return new Color(this.r, this.g, this.b, this.a);
    }

    public void bind()
    {
        GL11.glColor4f(r, g, b, a);
    }
}

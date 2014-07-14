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
package org.joge.core.math;

/**
 *
 * @author Moncef YABI
 */
public class Vector2d
{

    private double x;
    private double y;

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double vec2dLength(Vector2d v)
    {
        return Math.sqrt(v.x * v.x + v.y * v.y);
    }

//------------------------- Vec2DNormalize -----------------------------
//
//	normalizes a 2D Vector
//--------------------------------------------------------------------
    public Vector2d vec2dNormalize(Vector2d v)
    {
        double vector_length = vec2dLength(v);

        v.x = v.x / vector_length;
        v.y = v.y / vector_length;

        return v;
    }

//------------------------- Vec2DDot --------------------------
//
//	calculates the dot product
//--------------------------------------------------------------------
    public double vec2dDot(Vector2d v1, Vector2d v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

//------------------------ Vec2DSign --------------------------------
//
//  returns positive if v2 is clockwise of v1, minus if anticlockwise
//-------------------------------------------------------------------
    public int vec2dSign(Vector2d v1, Vector2d v2)
    {
        if (v1.y * v2.x > v1.x * v2.y)
        {
            return 1;
        } else
        {
            return -1;
        }
    }
}

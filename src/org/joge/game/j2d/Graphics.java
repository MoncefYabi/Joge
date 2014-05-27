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

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 *
 * @author Moncef YABI
 */
public class Graphics
{

    private boolean pushed;
    private final double PI = Math.PI;
    private Color color = Color.white;
//	private SFont string;

    public Graphics()
    {
    }

    private void setColor()
    {
        GL11.glColor4f(color.r, color.g, color.b, color.a);
    }

    private void disableGl()
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    private void enableGl()
    {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }


    private void checkPush()
    {
        if (!pushed)
        {
            GL11.glPushMatrix();
            pushed = true;
        }
    }

    protected void clear()
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void translate(float xpos, float ypos)
    {
        GL11.glTranslatef(xpos, ypos, 0);
    }

    public void setBackground(Color color)
    {
        GL11.glClearColor(color.r, color.g, color.b, color.a);
    }

    public void rotate2f(float rx, float ry, float ang)
    {
        this.rotate(rx, ry, ang);
    }

    public void rotate(float rx, float ry, float ang)
    {
        checkPush();
        GL11.glTranslatef(rx, ry, 0);
        GL11.glRotatef(ang, 0, 0, 1);
        GL11.glTranslatef(-rx, -ry, 0);
    }

    public void rotate3f(float rx, float ry, float rz, float ang)
    {
        checkPush();
        GL11.glTranslatef(rx, ry, rz);
        GL11.glRotatef(ang, 0, 0, 1);
        GL11.glTranslatef(-rx, -ry, -rz);
    }

    public void stop()
    {
        GL11.glLoadIdentity();
    }

    public void scale(float sx, float sy)
    {
        checkPush();
        GL11.glScalef(sx, sy, 0);
    }

    public void setColor(Color color)
    {
        this.color = color.getAlphaColor(255);
    }

    public void drawImage(Image img, float xpos, float ypos, boolean stopAktion)
    {
        img.draw(xpos, ypos);
        if (stopAktion)
        {
            GL11.glLoadIdentity();
        }
    }
    public void drawString(String text, Font font,float xpos, float ypos)
    {
        setColor();
        glTranslatef(xpos, ypos, 0.0f);
        for (int i = 0; i < text.length(); i++)
        {
           
            glCallList(font.getBase() + text.charAt(i));
            glTranslatef(font.getWidth()/2, 0.0f, 0.0f);
        }
        stop();
    }

    public void drawLine(float x1, float y1, float x2, float y2)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        enableGl();
    }

    public void drawRect(float x1, float y1, float width, float height)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1 + width, y1);
        GL11.glVertex2f(x1 + width, y1 + height);
        GL11.glVertex2f(x1, y1 + height);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        enableGl();
    }

    public void fillRect(float x, float y, float width, float height)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();
        enableGl();
    }

    public void fillMultiColoredRect(float x, float y, float width, float height, Color c1, Color c2, Color c3, Color c4)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4f(c1.r, c1.g, c1.b, c1.a);
        GL11.glVertex2f(x, y);
        GL11.glColor4f(c2.r, c2.g, c2.b, c2.a);
        GL11.glVertex2f(x + width, y);
        GL11.glColor4f(c3.r, c3.g, c3.b, c3.a);
        GL11.glVertex2f(x + width, y + height);
        GL11.glColor4f(c4.r, c4.g, c4.b, c4.a);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();
        enableGl();
    }

    public void fillRect2(float x, float y, float x2, float y2)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x2, y);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x, y2);
        GL11.glEnd();
        enableGl();
    }

    public void drawTriangle(int x, int y, int x1, int y1, int x2, int y2)
    {
        drawLine(x, y, x1, y1);
        drawLine(x1, y1, x2, y2);
        drawLine(x2, x2, x, y);
    }

    public void fillTriangle(int x, int y, int x1, int y1, int x2, int y2)
    {
        setColor();
        disableGl();
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        enableGl();
    }

    public void drawOvall(float x_center, float y_center, float w, float h, int n)
    {
        double pheta, angle_increment;
        float x, y;
        if (n <= 0)
        {
            n = 1;
        }
        angle_increment = 2 * PI / n;
        setColor();
        disableGl();
        GL11.glPushMatrix();
        GL11.glTranslatef(x_center, y_center, 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (pheta = 0.0f; pheta < 2 * PI; pheta += angle_increment)
        {
            x = w / 2 * (float) Math.cos(pheta);
            y = h / 2 * (float) Math.sin(pheta);

            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        enableGl();
    }

    public void fillOvall(float x_center, float y_center, float w, float h, int n)
    {
        double pheta, angle_increment;
        float x, y;
        if (n <= 0)
        {
            n = 1;
        }
        angle_increment = 2 * PI / n;
        setColor();
        disableGl();
        GL11.glPushMatrix();
        GL11.glTranslatef(x_center, y_center, 0);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (pheta = 0.0f; pheta < 2 * PI; pheta += angle_increment)
        {
            x = w / 2 * (float) Math.cos(pheta);
            y = h / 2 * (float) Math.sin(pheta);

            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        enableGl();
    }
}

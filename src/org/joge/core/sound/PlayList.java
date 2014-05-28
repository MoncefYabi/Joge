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
package org.joge.core.sound;

import java.io.File;
import java.util.HashMap;
import org.joge.core.tools.ToolKit;

/**
 *
 * @author Moncef YABI
 */
public class PlayList
{

    private HashMap<String, String> resourseMap;

    public PlayList(String resourceFolder, String filter, boolean setFilter)
    {
        File file = new File(ToolKit.getResource(resourceFolder).getFile());
        String list[] = file.list();
        String parent = file.getName();
        resourseMap = new HashMap<>();
        String tempName;
        for (String fileName : list)
        {
            if (setFilter && !fileName.endsWith("." + filter))
            {
                continue;
            }
            tempName = fileName.split("\\.")[0];
            resourseMap.put(tempName, parent + File.separator + fileName);
        }

    }

    public String getResourse(String key)
    {
        return resourseMap.get(key);
    }
}

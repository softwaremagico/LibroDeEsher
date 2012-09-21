package com.softwaremagico.files;
/*
 * #%L
 * KendoTournamentGenerator
 * %%
 * Copyright (C) 2008 - 2012 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero.
 * Jorge Hortelano Otero <softwaremagico@gmail.com>
 * C/Quart 89, 3. Valencia CP:46008 (Spain).
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
 * along with this program; If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.File;

/**
 *
 * @author jorge
 */
public class Path {

    private Path() {
    }

    public static String returnRootPath() {
        String soName = System.getProperty("os.name");
        if (soName.contains("Linux") || soName.contains("linux")) {
            File f = new File("/usr/share/libro-de-esher");
            if (f.exists()) {
                return f.getPath() + File.separator;
            } else {
                return "";
            }
        } else if (soName.contains("Windows") || soName.contains("windows") || soName.contains("vista") || soName.contains("Vista")) {
            return "";
        }
        return "";
    }

    public static String returnImagePath() {
        return returnRootPath() + "images" + File.separator;
    }

    public static String returnTranslatorPath() {
        return returnRootPath() + "translations" + File.separator;
    }

    public static String returnBackgroundPath() {
        return returnImagePath() + "background" + File.separator + "background.png";
    }

    public static String returnBannerPath() {
        return returnImagePath() + "banner" + File.separator + "banner.png";
    }

    public static String returnLogoPath() {
        return returnImagePath() + "logo" + File.separator + "kendoUV.gif";
    }

    public static String returnDefaultBanner() {
        return returnDefault() + "defaultBanner.png";
    }

    public static String returnMainPhoto() {
        return returnDefault() + "mainPhoto.png";
    }

    public static String returnDefault() {
        return returnImagePath() + "defaults" + File.separator;
    }

    public static String returnManualPath() {
        return returnRootPath() + "manual" + File.separator;
    }

    public static String returnLogFile() {
        return "kendoTournament.log";
    }

    public static String returnIconFolder() {
        return returnImagePath() + "icons" + File.separator;
    }
}

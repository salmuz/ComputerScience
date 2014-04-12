/* ==========================================
 * CategorizeUserForum : a free Java graph-theory library
 * ==========================================
 * 
 * salmuz : Carranza Alarcon Yonatan Carlos
 * 
 * (C) Copyright 2014, by salmuz and Contributors.
 * 
 * Project Info:  https://github.com/salmuz/Graphes_Multi_Plateformes
 * Project Creator:  salmuz (https://www.assembla.com/spaces/salmuz-java) 
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 
 * ------------------
 * Point.java
 * ------------------
 * (C) Copyright 2014, by salmuz and Contributors
 *
 * Original Author: Carranza Alarcon Yonatan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
 */

package org.montp2.m1decol.ter.utils;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final String HOME_TREETAGER = "/Users/user/Applications/tree-tagger/";

    //public static final String TREETAGGER_LANGUAGE = "/Users/user/Applications/tree-tagger/lib/french-utf8.par";
    public static final String TREETAGGER_LANGUAGE = "/Users/user/Applications/tree-tagger/lib/english.par";

    public static final List<String> tagsINV = Arrays.asList(new String[]{"PUN", "NUM", "PRP", "SENT", "ADV"});

    public static final List<String> subtagsINV = Arrays.asList(new String[]{"DEM","PER","REL","POS"});

    // il faut l'extraire de la base de données, on l'a fait avec python :)
    public static final List<String> userExclude = Arrays.asList(new String[]{
            "user_51.txt","user_62.txt","user_85.txt","user_93.txt","user_111.txt","user_116.txt","user_117.txt","user_123.txt",     //6
            "user_164.txt","user_174.txt","user_180.txt","user_219.txt","user_223.txt","user_305.txt","user_326.txt","user_336.txt", //6
            "user_17.txt","user_20.txt","user_48.txt","user_53.txt","user_104.txt","user_140.txt","user_203.txt","user_324.txt","user_342.txt", //7
            "user_25.txt","user_29.txt","user_39.txt","user_79.txt","user_101.txt","user_145.txt","user_151.txt", //8
            "user_19.txt","user_45.txt","user_50.txt","user_114.txt","user_152.txt","user_154.txt","user_188.txt", //9
            "user_1.txt","user_2.txt","user_6.txt","user_9.txt","user_12.txt","user_21.txt","user_27.txt","user_168.txt","user_209.txt", //10
            "user_3.txt","user_7.txt","user_10.txt","user_13.txt","user_14.txt","user_22.txt","user_30.txt", // 11
            "user_5.txt","user_8.txt","user_11.txt","user_31.txt","user_32.txt","user_33.txt","user_34.txt", // 12
            "user_640.txt","user_277.txt","user_61.txt","user_646.txt" // vide
             });
}

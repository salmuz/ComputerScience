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

package org.montp2.m1decol.ter;

import org.montp2.m1decol.ter.gramms.UniGramsPreProcessing;

public class LemmatizeFolders {

    public static void main(String[] args) {
        UniGramsPreProcessing uni = new UniGramsPreProcessing();
        /*uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/PresentationRD/",
                "/Users/user/Documents/XXXEnv/data_lemma/PresentationRD/",
                "/Users/user/Documents/XXXEnv/motvides.txt");
        uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/guideline/",
                "/Users/user/Documents/XXXEnv/data_lemma/guideline/",
                "/Users/user/Documents/XXXEnv/motvides.txt");
        uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/ides/",
                "/Users/user/Documents/XXXEnv/data_lemma/ides/",
                "/Users/user/Documents/XXXEnv/motvides.txt");
        uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/Proce/",
                "/Users/user/Documents/XXXEnv/data_lemma/Proce/",
                "/Users/user/Documents/XXXEnv/motvides.txt");
        uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/Tips/",
                "/Users/user/Documents/XXXEnv/data_lemma/Tips/",
                "/Users/user/Documents/XXXEnv/motvides.txt");     */

        uni.computeLemmatization("/Users/user/Documents/XXXEnv/data/test/",
                "/Users/user/Documents/XXXEnv/data_lemma/",
                "/Users/user/Documents/XXXEnv/motvides.txt");



    }
}

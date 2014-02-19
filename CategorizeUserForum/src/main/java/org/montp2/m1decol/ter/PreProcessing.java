/* ==========================================
 * GrapheMultiPlateforme : a free Java graph-theory library
 * ==========================================
 * 
 * salmuz : Carranza Alarcon Yonatan Carlos
 * 
 * (C) Copyright 2013, by salmuz and Contributors.
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
 * (C) Copyright 2013, by salmuz and Contributors
 *
 * Original Author: Carranza Alarcon Yonatan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
 */

package org.montp2.m1decol.ter;

import org.annolab.tt4j.TreeTaggerException;
import org.montp2.m1decol.ter.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PreProcessing {

    private Lemmatisation lemma = Lemmatisation.getInstance();

    private void splitWordsByFile(String path,Map<String,Integer> keys) throws IOException, TreeTaggerException {

        for(TreeTaggerWordWrapper word : lemma.execution(path)){
            if(!word.isINV()){
               Integer value = keys.get(word.getWord());
               if(value == null) value = 0;
               keys.put(word.getWord(),++value);
            }
        }

    }

    public Map<String,Integer> splitWords(String path) throws IOException, TreeTaggerException{
        Map<String,Integer> keys = new HashMap<String, Integer>();
        if (FileUtils.isFile(path)) {
            splitWordsByFile(path,keys);
        } else {
            for (File subpath : FileUtils.ls(path)) {
                System.out.println(subpath);
                splitWordsByFile(subpath.getAbsolutePath(),keys);
            }
        }
        return keys;
    }

}

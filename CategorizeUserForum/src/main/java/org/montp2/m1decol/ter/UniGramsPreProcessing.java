/* ==========================================
 * CategorizeUserForum : a free Java graph-theory library
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

import org.annolab.tt4j.TreeTaggerException;
import org.montp2.m1decol.ter.exception.NotDirectoryException;
import org.montp2.m1decol.ter.exception.NotFileException;
import org.montp2.m1decol.ter.utils.FileUtils;
import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniGramsPreProcessing {

    private Lemmatisation lemmatisation = Lemmatisation.getInstance();
    private List<String>  stopWords;

    public UniGramsPreProcessing() {
        stopWords = new ArrayList<String>();
    }

    public void loadStopWordsFromFile(String path,List<String> otherStopWords) throws IOException {
        loadStopWordsFromFile(path);
        stopWords.addAll(otherStopWords);
    }

    public void loadStopWordsFromFile(String path) throws IOException {
        if (!FileUtils.isFile(path))
            throw new NotFileException("The inPath " + path + " is not file");
        stopWords = InputStreamUtils.readByLine(path);
    }

    public void convertToFileLemmatised(String inPath, String outPath) throws IOException, TreeTaggerException {
        if (!FileUtils.isDirectory(inPath))
            throw new NotDirectoryException("The inPath " + inPath + " is not directory");
        boolean isSameDirectory = inPath.equalsIgnoreCase(outPath);
        StringBuilder buffer;
        for (File file : FileUtils.ls(inPath)) {
            buffer = new StringBuilder();
            System.err.println(file.getAbsolutePath());
            for (TreeTaggerWordWrapper word : lemmatisation.execution(file.getAbsolutePath())) {
                if (!word.isINV() && !stopWords.contains(word.getWord())) {
                    buffer.append(word.getWord());
                    buffer.append(" ");
                }
            }
            // remove the extension of the file
            String name = isSameDirectory ? file.getName().replaceFirst("[.][^.]+$", "") + "_uni_lemma.txt" : file.getName();
            OutputStreamUtils.writeSimple(buffer.toString(), outPath + name);
        }
    }

    public void generateFileArff(String inPath){

    }


}


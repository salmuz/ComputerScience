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
import org.montp2.m1decol.ter.exception.ProcessingException;
import org.montp2.m1decol.ter.utils.FileUtils;
import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalPreProcessing {

    private Lemmatisation lemma = Lemmatisation.getInstance();
    private static final String FILE_ALL_MSG = "/tmp/allmsg.txt";
    private static final String FILE_SH = "/tmp/catall.sh";

    private void splitWordsByFile(String path, Map<String, Integer> keys) throws IOException, TreeTaggerException {

        for (TreeTaggerWordWrapper word : lemma.execution(path)) {
            if (!word.isINV()) {
                Integer value = keys.get(word.getWord());
                if (value == null) value = 0;
                keys.put(word.getWord(), ++value);
            }
        }
    }

    public Map<String, Integer> splitWordsByFiles(String path) throws IOException, TreeTaggerException {
        Map<String, Integer> keys = new HashMap<String, Integer>();
        if (FileUtils.isFile(path)) {
            splitWordsByFile(path, keys);
        } else {
            for (File subpath : FileUtils.ls(path)) {
                splitWordsByFile(subpath.getAbsolutePath(), keys);
            }
        }
        return keys;
    }


    private Map<String, Integer> loadNumberOfOccurrences(String path)
            throws IOException, InterruptedException, TreeTaggerException {

        String[] lineSH = {"#/bin/bash", "for i in `ls " + path + "`", "do", "\tcat " + path + "$i >> " + FILE_ALL_MSG, "done"};
        OutputStreamUtils.writeSimple(lineSH, FILE_SH);

        String[] commands = {"/bin/bash", FILE_SH};

        Process process = new ProcessBuilder(commands).start();
        process.waitFor();
        String error = InputStreamUtils.readInputStream(process.getErrorStream());

        if (error.length() > 0)
            throw new ProcessingException("The load all the message ");

        Map<String, Integer> keys = splitWordsByFiles(FILE_ALL_MSG);

        FileUtils.removeFile(FILE_SH);
        FileUtils.removeFile(FILE_ALL_MSG);

        return keys;
    }

    public List<String> getWordsMostOrLeastFrequentOfCorpus(String path, int max, int min)
            throws InterruptedException, IOException, TreeTaggerException {

        List<String> stopWords = new ArrayList<String>();
        Map<String, Integer> words = loadNumberOfOccurrences(path);
        for (Map.Entry<String, Integer> word : words.entrySet()) {
            if (word.getValue() >= max || word.getValue() <= min) {
                stopWords.add(word.getKey());
            }
        }

        return stopWords;
    }
}

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

package org.montp2.m1decol.ter.preprocessing;

import org.annolab.tt4j.TreeTaggerException;
import org.apache.commons.collections.CollectionUtils;
import org.montp2.m1decol.ter.exception.ProcessingException;
import org.montp2.m1decol.ter.preprocessing.lemmatize.Lemmatisation;
import org.montp2.m1decol.ter.preprocessing.lemmatize.TreeTaggerWordWrapper;
import org.montp2.m1decol.ter.utils.FileUtils;
import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;
import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Map<String, Integer> lemmaWordsByFiles(String path) throws IOException, TreeTaggerException {
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

    //===== refactorin the class

    private void splitWordsByFileWL(String path, Map<String, Integer> keys) throws IOException, TreeTaggerException {

        for (String line : InputStreamUtils.readByLine(path)) {
            for (String word : line.split("[^\\p{L}]+")) {
                Integer value = keys.get(word);
                if (value == null) value = 0;
                keys.put(word, ++value);
            }
        }
    }

    private Map<String, Integer> withoutLemmaWordsByFiles(String path) throws IOException, TreeTaggerException {

        Map<String, Integer> keys = new HashMap<String, Integer>();
        if (FileUtils.isFile(path)) {
            splitWordsByFileWL(path, keys);
        } else {
            for (File subpath : FileUtils.ls(path)) {
                splitWordsByFileWL(subpath.getAbsolutePath(), keys);
            }
        }
        return keys;
    }


    public Map<String, Integer> loadNumberOfOccurrences(String path, boolean isLemmatize)
            throws IOException, InterruptedException, TreeTaggerException {
        Map<String, Integer> keys = new HashMap<String, Integer>();
        try {
            String[] lineSH = {"#/bin/bash", "for i in `ls " + path + "`", "do",
                    "\tcat " + path + ((FileUtils.isDirectory(path)) ? "$i" : "") + ">> " + FILE_ALL_MSG,
                    "echo \"  \" >> " + FILE_ALL_MSG, "done"};
            OutputStreamUtils.writeSimple(lineSH, FILE_SH);

            String[] commands = {"/bin/bash", FILE_SH};

            Process process = new ProcessBuilder(commands).start();
            process.waitFor();
            String error = InputStreamUtils.readInputStream(process.getErrorStream());

            if (error.length() > 0)
                throw new ProcessingException("The load all the message :" + error);


            keys = (isLemmatize) ? lemmaWordsByFiles(FILE_ALL_MSG) : withoutLemmaWordsByFiles(FILE_ALL_MSG);

        } finally {
            FileUtils.removeFile(FILE_SH);
            FileUtils.removeFile(FILE_ALL_MSG);
        }

        return keys;
    }

    public List<String> getWordsMostOrLeastFrequentOfCorpus(String path, int max, int min)
            throws InterruptedException, IOException, TreeTaggerException {

        List<String> stopWords = new ArrayList<String>();
        Map<String, Integer> words = loadNumberOfOccurrences(path, true);
        for (Map.Entry<String, Integer> word : words.entrySet()) {
            if (word.getValue() >= max || word.getValue() <= min) {
                stopWords.add(word.getKey());
            }
        }

        return stopWords;
    }


    private void splitWordsByFile(String path, Collection<String> words) throws IOException {
        for (String line : InputStreamUtils.readByLine(path)) {
            for (String word : line.split("[^\\p{L}]+")) {
                words.add(word);
            }
        }
    }

    private Set<String> splitWordsByFiles(String path) throws IOException {
        Set<String> words = new HashSet<String>();
        if (FileUtils.isFile(path)) {
            splitWordsByFile(path, words);
        } else {
            for (File subpath : FileUtils.ls(path)) {
                splitWordsByFile(subpath.getAbsolutePath(), words);
            }
        }
        return words;
    }

    public Collection<String> intersectVocabulary(String path) throws IOException {
        List<Set<String>> list = new ArrayList<Set<String>>();
        for (File file : FileUtils.ls(path)) {
            list.add(splitWordsByFiles(file.getAbsolutePath()));
        }

        Collection<String> intersections = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            intersections = CollectionUtils.intersection(intersections, list.get(i));
        }

        /*
        Set<String> val = new HashSet<String>();
        int ix = list.size()-1;
        int n;
        for (int i = 0; i < list.size(); i++) {
            Collection<String> intersections2 = null;
            if(ix != 0){
                intersections2 = list.get(0);
                n = 0;
            }else{
                intersections2 = list.get(1);
                n = 1;
            }
            for (int j = 0; j < list.size(); j++) {
                if(ix != j && n != j){
                    intersections = CollectionUtils.intersection(intersections2,list.get(j));
                }
            }
            ix--;
            val.addAll(intersections2);
        }

        intersections.addAll(val);    */

        return intersections;
    }

    public Map<Integer, Integer> getMapOfInstanceArffToIdUser(String dirPath, String inArff) throws Exception {

        Pattern pattern = Pattern.compile("^( \\p{Print}+ ) (_(\\p{Digit}+).txt)$", Pattern.COMMENTS);

        Map<Integer, String> arffIds = new HashMap<Integer, String>();
        for (File file : FileUtils.ls(dirPath)) {
            Matcher matcher = pattern.matcher(file.getAbsolutePath());
            matcher.matches();
            arffIds.put(Integer.parseInt(matcher.group(3)),
                    InputStreamUtils.readInputStream(new BufferedInputStream(new FileInputStream(file))));
        }

        Instances instances = WekaUtils.loadARFF(inArff);
        Enumeration<Instance> en = instances.enumerateInstances();
        Map<Integer, Integer> arffTOIdUser = new HashMap<Integer, Integer>();
        int index = 0;
        while (en.hasMoreElements()) {
            String value = en.nextElement().toString();
            value = value.substring(1, value.length() - 1);
            String works[] = value.split("\\s");
            for (Map.Entry<Integer, String> arff : arffIds.entrySet()) {
                String wordArff[] = arff.getValue().split("\\s");
                boolean isEqual = true;
                if (wordArff.length == works.length) {
                    for (int j = 0; j < wordArff.length; j++) {
                        if (!wordArff[j].equals(works[j])) {
                            isEqual = false;
                            break;
                        }
                    }
                    if (isEqual) {
                        arffTOIdUser.put(index, arff.getKey());
                        break;
                    }
                }
            }
            index++;
        }

        return arffTOIdUser;
    }

    private Integer findFileByTxt(String dirPath, String txt) throws Exception {

        if (txt.length() > 12600) throw new IllegalArgumentException("Text very large for the command grep");

        String[] command = {"grep", "-l", "-R", txt, dirPath};

        Process process = new ProcessBuilder(command).start();
        process.waitFor();
        String error = InputStreamUtils.readInputStream(process.getErrorStream());

        if (error.length() > 0)
            throw new ProcessingException("The load all the message :" + error);

        String output = InputStreamUtils.readInputStream(process.getInputStream());

        Pattern pattern = Pattern.compile("^( \\p{Print}+ ) (_(\\p{Digit}+).txt)$", Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(output);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(3));
        }

        throw new NullPointerException("Not exist the user id");
    }

}

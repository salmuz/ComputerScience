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

package org.montp2.m1decol.ter.gramms;

import org.annolab.tt4j.TreeTaggerException;
import org.montp2.m1decol.ter.preprocessing.GlobalPreProcessing;
import org.montp2.m1decol.ter.preprocessing.lemmatize.Lemmatisation;
import org.montp2.m1decol.ter.preprocessing.lemmatize.TreeTaggerWordWrapper;
import org.montp2.m1decol.ter.exception.NotDirectoryException;
import org.montp2.m1decol.ter.exception.NotFileException;
import org.montp2.m1decol.ter.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class UniGramsPreProcessing implements GramsPreProcessing {

    private Lemmatisation lemmatisation = Lemmatisation.getInstance();
    private FilterTokenizer filterTokenizer;
    private List<String> stopWords;

    public UniGramsPreProcessing(FilterTokenizer filterTokenizer) {
        stopWords = new ArrayList<String>();
        this.filterTokenizer = filterTokenizer;
    }

    private void loadStopWordsFromFile(String path) throws IOException {
        if (!FileUtils.isFile(path))
            throw new NotFileException("The inPath " + path + " is not file");
        stopWords = InputStreamUtils.readByLine(path);
    }

    public void computeLemmatization(String inPath, String stopWordsPath) {
        this.computeLemmatization(inPath, inPath, stopWordsPath, new ArrayList<String>());
    }

    public void computeLemmatization(String inPath, String outPath, String stopWordsPath) {
        this.computeLemmatization(inPath, outPath, stopWordsPath, new ArrayList<String>());
    }

    public void computeLemmatization(String inPath, String outPath, String stopWordsPath, List<String> otherStopWords) {
        this.computeLemmatization(inPath, outPath, stopWordsPath, otherStopWords, false);
    }

    public void computeLemmatization(String inPath, String outPath, String stopWordsPath,
                                     List<String> otherStopWords, boolean delWordsMostOrLestFrequent) {
        try {
            loadStopWordsFromFile(stopWordsPath);
            GlobalPreProcessing pre = new GlobalPreProcessing();
            //il faut faire une algorithm pour savoir le min et max, pour pouvoir enlever ces mots
            if (delWordsMostOrLestFrequent)
                stopWords.addAll(pre.getWordsMostOrLeastFrequentOfCorpus(inPath, Integer.MAX_VALUE, 4));

            //Enleve les autres mots vide; mots qui intersect tous les forums
            if (otherStopWords != null)
                stopWords.addAll(otherStopWords);

            convertToFileLemmatised(inPath, outPath);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void convertToFileLemmatised(String inPath, String outPath) throws IOException, TreeTaggerException {
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
            String name = isSameDirectory ? file.getName().replaceFirst("[.][^.]+$", "") + "_lemma.txt" : file.getName();
            OutputStreamUtils.writeSimple(buffer.toString(), outPath + File.separator + name);
        }
    }

    /**
     * Execute UniGramme
     */
    public void executePreProccesing(Properties properties) throws Exception {

        //validate s'il exist tous les parametres

        if (!isProcessingLemmatization(properties.getProperty(NGramProperties.RAW_DATA_PATH),
                properties.getProperty(NGramProperties.LEMMA_DATA_PATH)))
            this.computeLemmatization(properties.getProperty(NGramProperties.RAW_DATA_PATH),
                    properties.getProperty(NGramProperties.LEMMA_DATA_PATH),
                    properties.getProperty(NGramProperties.STOP_WORD_PATH),
                    (List<String>) properties.get(NGramProperties.OTHER_STOP_WORDS), true);

        String arffPath = properties.getProperty(NGramProperties.ARFF_DATA_FILE_PATH);

        if (arffPath == null){
            arffPath = properties.getProperty(NGramProperties.ARFF_DATA_PATH) + File.separator +
                    properties.getProperty(NGramProperties.ARFF_DATA_NAME) +
                    DateUtils.currentDate("ddMMyyyyHHmmss", Locale.FRANCE);
        }else{
            arffPath = arffPath.replace(WekaUtils.EXTENSION_ARFF, "");
        }

        properties.setProperty(NGramProperties.ARFF_DATA_FILE_PATH, arffPath + WekaUtils.EXTENSION_ARFF);

        WekaUtils.createARFF(properties.getProperty(NGramProperties.LEMMA_DATA_PATH),
                arffPath + WekaUtils.EXTENSION_ARFF,
                (List<String>) properties.get(NGramProperties.EXCLUDE_FILE));

        properties.setProperty(NGramProperties.ARFF_FILTER_FILE_PATH,
                arffPath + filterTokenizer.typeFilter() + WekaUtils.EXTENSION_ARFF);

        this.filterTokenizer.indexingToTokenizer(properties.getProperty(NGramProperties.ARFF_DATA_FILE_PATH),
                properties.getProperty(NGramProperties.ARFF_FILTER_FILE_PATH));
    }

    private boolean isProcessingLemmatization(String pathData, String pathLemma) {
        return FileUtils.ls(pathData).size() == FileUtils.ls(pathLemma).size();
    }
}


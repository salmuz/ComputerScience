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

import org.montp2.m1decol.ter.clustering.Clustering;
import org.montp2.m1decol.ter.clustering.KMeansClustering;
import org.montp2.m1decol.ter.clustering.NearestNeighbor;
import org.montp2.m1decol.ter.data.JDBCPostgreSQL;
import org.montp2.m1decol.ter.gramms.UniGramsPreProcessing;
import org.montp2.m1decol.ter.gramms.filters.FilterTokenizerBoolean;
import org.montp2.m1decol.ter.preprocessing.GlobalPreProcessing;
import org.montp2.m1decol.ter.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class TestMain {
    static String DATA = "/Users/user/Downloads/TER/test/users/";
    static String FORUMS = "/Users/user/Downloads/TER/test/forums_lemma/";
    static String FILE_ARFF = "/Users/user/Downloads/TER/test/";
    static String NAME_ARFF = "CategorizeUserForum";
    static String DATA_LEMMA = "/Users/user/Downloads/TER/test/users_lemma/";
    static String STOP_WORD = "/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt";

    public static void computeFrecuency(String file, String path, boolean isLemmatize) {
        try {
            GlobalPreProcessing pre = new GlobalPreProcessing();
            OutputStreamUtils.writeSimpleMap(MapUtils.sortByValue(pre.loadNumberOfOccurrences(path, isLemmatize)), file);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void computeUniGramme() {
        try {
            UniGramsPreProcessing uni = new UniGramsPreProcessing(new FilterTokenizerBoolean());
            Properties properties = new Properties();
            properties.put(NGramProperties.RAW_DATA_PATH, DATA);
            properties.put(NGramProperties.LEMMA_DATA_PATH, DATA_LEMMA);
            properties.put(NGramProperties.STOP_WORD_PATH, STOP_WORD);
            properties.put(NGramProperties.NAME_ARFF, NAME_ARFF);
            properties.put(NGramProperties.ARFF_PATH, FILE_ARFF);
            properties.put(NGramProperties.EXCLUDE_FILE, Constants.userExclude);
            properties.put(NGramProperties.OTHER_STOP_WORDS, new GlobalPreProcessing().intersectVocabulary(FORUMS));
            uni.executePreProccesing(properties);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void computeClustering() throws Exception {
        Clustering clu = new KMeansClustering();
        clu.computeClustering("/Users/user/Downloads/TER/test/CategorizeUserForum12042014112801bool.arff",
                "/Users/user/Downloads/TER/test/Kmeans.model");
        NearestNeighbor near = new NearestNeighbor();
        near.computeNearestNeighbor("/Users/user/Downloads/TER/test/CategorizeUserForum12042014112801bool.arff",
                "/Users/user/Downloads/TER/test/Kmeans.model",
                "/Users/user/Downloads/TER/test/nearNeighbor.txt");
    }

    public static void main(String... arg) throws Exception {
        /*UniGramsPreProcessing uni = new UniGramsPreProcessing(new FilterTokenizerBoolean());
        uni.computeLemmatization("/Users/user/Downloads/TER/test/forums/",
                "/Users/user/Downloads/TER/test/forums_lemma/",STOP_WORD,false);*/

        /*for(File file: FileUtils.ls("/Users/user/Downloads/TER/test/forums_lemma/")){
            computeFrecuency(file.getParentFile().getParent()+File.separator+"Freq"+file.getName(),file.getAbsolutePath());
        }
        GlobalPreProcessing pre = new GlobalPreProcessing();
        System.out.println(pre.intersectVocabulary("/Users/user/Downloads/TER/test/forums_lemma/"));*/
        //computeUniGramme();
        //computeFrecuency("/Users/user/Downloads/TER/test/freq1.txt",DATA);
        //computeFrecuency("/Users/user/Downloads/TER/test/freq2.txt",DATA_LEMMA,false);

        /*UniGramsPreProcessing pre = new UniGramsPreProcessing(new FilterTokenizerBoolean());
        pre.computeLemmatization("/Users/user/Downloads/AVIS_HOTELS_Tripadvisor/Excellent/",STOP_WORD);
        pre.computeLemmatization("/Users/user/Downloads/AVIS_HOTELS_Tripadvisor/Horrible/",STOP_WORD);
        pre.computeLemmatization("/Users/user/Downloads/AVIS_HOTELS_Tripadvisor/Moyen/",STOP_WORD);
        */

        JDBCPostgreSQL jdbc = new JDBCPostgreSQL();
        for(String line : InputStreamUtils.readByLine("/Users/user/Downloads/TER/test/nearNeighbor.txt")){
            if(line.isEmpty()) break;
            String [] users = line.split(":")[1].split(",");
            System.out.println(jdbc.forumsBelongUsers(new ArrayList<String>(Arrays.asList(users))));
        }
    }
}

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

public class TestMain {

    public static void main(String... arg) {
        String DATA = "/Users/user/Downloads/TER/test/usetes/";
        GlobalPreProcessing pre = new GlobalPreProcessing();
        UniGramsPreProcessing uni = new UniGramsPreProcessing();
        try {
//            List<String> stopwords = pre.getWordsMostOrLeastFrequentOfCorpus(DATA, 1000, 4);
//            uni.loadStopWordsFromFile("/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt", stopwords);
//            uni.convertToFileLemmatised(DATA, "/Users/user/Downloads/TER/test/usetes_lemma/");
            //uni.createFileARFF("/Users/user/Downloads/TER/test/users_lemma/","/Users/user/Downloads/TER/test/CategorizeUserForum.arff");
            //uni.indexingToVector("/Users/user/Downloads/TER/test/CategorizeUserForum.arff","/Users/user/Downloads/TER/test/CategorizeUserForum.vector.arff");
            uni.clusteringKMeans("/Users/user/Downloads/TER/test/CategorizeUserForum.vector.arff");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

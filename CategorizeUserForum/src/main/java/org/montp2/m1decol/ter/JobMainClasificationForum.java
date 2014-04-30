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
import org.montp2.m1decol.ter.clustering.DistanceUser;
import org.montp2.m1decol.ter.clustering.KMeansClustering;
import org.montp2.m1decol.ter.clustering.NearestNeighbor;
import org.montp2.m1decol.ter.gramms.FilterTokenizer;
import org.montp2.m1decol.ter.gramms.UniGramsPreProcessing;
import org.montp2.m1decol.ter.gramms.filters.FilterTokenizerBoolean;
import org.montp2.m1decol.ter.preprocessing.GlobalPreProcessing;
import org.montp2.m1decol.ter.utils.Constants;
import org.montp2.m1decol.ter.utils.MapUtils;
import org.montp2.m1decol.ter.utils.NGramProperties;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JobMainClasificationForum {

    static String DATA = "/Users/user/Downloads/TER/test/users/";
    static String FORUMS_LEMMA = "/Users/user/Downloads/TER/test/forums_lemma/";
    static String FILE_ARFF = "/Users/user/Downloads/TER/test/";
    static String NAME_ARFF = "CategorizeUserForum";
    static String DATA_LEMMA = "/Users/user/Downloads/TER/test/users_lemma/";
    static String STOP_WORD = "/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt";
    static Properties properties = new Properties();

    public static void computeFrecuency(String file, String path, boolean isLemmatize) {
        try {
            GlobalPreProcessing pre = new GlobalPreProcessing();
            OutputStreamUtils.writeSimpleMap(MapUtils.sortByValue(pre.loadNumberOfOccurrences(path, isLemmatize)), file);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void computeUniGramme(FilterTokenizer filterTokenizer) {
        try {
            UniGramsPreProcessing uni = new UniGramsPreProcessing(filterTokenizer);
            properties.put(NGramProperties.RAW_DATA_PATH, DATA);
            properties.put(NGramProperties.LEMMA_DATA_PATH, DATA_LEMMA);
            properties.put(NGramProperties.STOP_WORD_PATH, STOP_WORD);
            properties.put(NGramProperties.NAME_ARFF, NAME_ARFF);
            properties.put(NGramProperties.ARFF_PATH, FILE_ARFF);
            properties.put(NGramProperties.EXCLUDE_FILE, Constants.userExclude);
            properties.put(NGramProperties.OTHER_STOP_WORDS, new GlobalPreProcessing().intersectVocabulary(FORUMS_LEMMA));
            uni.executePreProccesing(properties);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static Map<Integer, List<DistanceUser>>  computeClustering( Map<Integer,Integer> arffToIdUser,
                                                                       String arffFilter,String modelCluster) throws Exception {
        Clustering clu = new KMeansClustering();
        clu.computeClustering(arffFilter,modelCluster);
        clu.computeInstanceByCluster(arffFilter,modelCluster,arffToIdUser);
        NearestNeighbor near = new NearestNeighbor();
        return near.computeNearestNeighbor(arffFilter,modelCluster,arffToIdUser);
    }

    public static void main(String... arg) throws Exception {

        GlobalPreProcessing global = new GlobalPreProcessing();
        System.out.println(global.intersectVocabulary(FORUMS_LEMMA));
        computeUniGramme(new FilterTokenizerBoolean());

        Map<Integer,Integer> convert = global.getMapOfInstanceArffToIdUser(
                DATA_LEMMA,properties.getProperty(NGramProperties.ARFF_FILE_PATH));

        computeClustering(convert,"/Users/user/Downloads/TER/test/bool1.arff","/Users/user/Downloads/TER/test/kmeans.model");

        computeFrecuency("/Users/user/Downloads/TER/test/cfreq_7.txt",
                "/Users/user/Downloads/TER/test/cluster_7.txt", false);
        /*
        JDBCPostgreSQL jdbc = new JDBCPostgreSQL();
        for(String line : InputStreamUtils.readByLine("/Users/user/Downloads/TER/test/nearNeighbor2.txt")){
            if(line.isEmpty()) break;
            String [] users = line.split(":")[1].split(",");
            System.out.println(jdbc.forumsBelongUsers(new ArrayList<String>(Arrays.asList(users))));
        }*/

        /*
        for(File file: FileUtils.ls("/Users/user/Downloads/TER/test/forums_lemma/")){

            computeFrecuency(file.getParentFile().getParent()+File.separator+"Freq"+file.getName(),file.getAbsolutePath());
        }

        /*
        }*/


    }
}

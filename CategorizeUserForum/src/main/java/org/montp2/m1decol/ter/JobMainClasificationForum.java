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

import org.montp2.m1decol.ter.business.AbstractBusiness;
import org.montp2.m1decol.ter.business.ForumBusinness;
import org.montp2.m1decol.ter.clustering.Clustering;
import org.montp2.m1decol.ter.clustering.DistanceUser;
import org.montp2.m1decol.ter.clustering.KMeansClustering;
import org.montp2.m1decol.ter.clustering.NearestNeighbor;
import org.montp2.m1decol.ter.gramms.FilterTokenizer;
import org.montp2.m1decol.ter.gramms.UniGramsPreProcessing;
import org.montp2.m1decol.ter.gramms.filters.FilterTokenizerBoolean;
import org.montp2.m1decol.ter.preprocessing.GlobalPreProcessing;
import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.MapUtils;
import org.montp2.m1decol.ter.utils.NGramProperties;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JobMainClasificationForum {

    public static void computeFrecuency(String file, String path, boolean isLemmatize) {
        try {
            GlobalPreProcessing pre = new GlobalPreProcessing();
            OutputStreamUtils.writeSimpleMap(MapUtils.sortByValue(pre.loadNumberOfOccurrences(path, isLemmatize)), file);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void computeUniGramme(FilterTokenizer filterTokenizer, int minUserToPost, int maxUserToPost) {
        try {
            UniGramsPreProcessing uni = new UniGramsPreProcessing(filterTokenizer);

            properties.put(NGramProperties.RAW_DATA_PATH, DATA);
            properties.put(NGramProperties.LEMMA_DATA_PATH, DATA_LEMMA);
            properties.put(NGramProperties.STOP_WORD_PATH, STOP_WORD);
            properties.put(NGramProperties.ARFF_DATA_NAME, NAME_ARFF);
            properties.put(NGramProperties.ARFF_DATA_PATH, PATH_ARFF);
            properties.put(NGramProperties.EXCLUDE_FILE,
                    business.getUserPostInMinAndMaxForum(minUserToPost, maxUserToPost, "user_", ".txt"));
            properties.put(NGramProperties.OTHER_STOP_WORDS, new GlobalPreProcessing().intersectVocabulary(FORUMS_LEMMA));
            uni.executePreProccesing(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, List<Integer>> computeClustering(Map<Integer, Integer> arffToIdUser,
                                                                String arffFilter, String modelCluster) throws Exception {
        Clustering clu = new KMeansClustering();
        clu.computeClustering(arffFilter, modelCluster);
        return clu.computeInstanceByCluster(arffFilter, modelCluster, arffToIdUser);
    }


    static String ROOT_DATA = "/Users/user/Downloads/TER/test";
    static String DATA = ROOT_DATA + "/users/";
    static String DATA_LEMMA = ROOT_DATA + "/users_lemma/";
    static String FORUMS_LEMMA = ROOT_DATA + "/forums_lemma/";
    static String STOP_WORD = "/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt";


    static String ROOT_OUT_PATH = "/Users/user/Downloads/TER/test/boolean/Avec5Forums";
    static String MODEL_CLUSTER = ROOT_OUT_PATH + "/kmeans.model";
    static String PATH_ARFF = ROOT_OUT_PATH;
    static String NAME_ARFF = "CategorizeUserForum";

    static Properties properties = new Properties();
    static AbstractBusiness business = new ForumBusinness();

    public static void main(String... arg) throws Exception {

        GlobalPreProcessing global = new GlobalPreProcessing();
        NearestNeighbor near = new NearestNeighbor();

        //OutputStreamUtils.writeSimpleCollection(global.intersectVocabulary(FORUMS_LEMMA),
        //        PATH_MAIN + File.separator + "intersectionWordsForums.txt");

        computeUniGramme(new FilterTokenizerBoolean(), 6, 12);

        Map<Integer, Integer> arffToIdUser = global.getMapOfInstanceArffToIdUser(
                DATA_LEMMA, properties.getProperty(NGramProperties.ARFF_DATA_FILE_PATH));

        properties.setProperty(NGramProperties.CLUSTER_MODEL, MODEL_CLUSTER);

        Map<Integer, List<Integer>> instanceByCluster = computeClustering(arffToIdUser,
                properties.getProperty(NGramProperties.ARFF_FILTER_FILE_PATH),
                properties.getProperty(NGramProperties.CLUSTER_MODEL));

        Map<Integer, List<DistanceUser>> usersNearest = near.computeNearestNeighbor(
                properties.getProperty(NGramProperties.ARFF_FILTER_FILE_PATH),
                properties.getProperty(NGramProperties.CLUSTER_MODEL), arffToIdUser);


        for (Map.Entry<Integer, List<Integer>> instance : instanceByCluster.entrySet()) {
            StringBuilder buffer = new StringBuilder();
            for (Integer iduser : instance.getValue()) {
                buffer.append(InputStreamUtils.readInputStream(DATA_LEMMA + File.separator + "user_" + iduser + ".txt"));
                buffer.append(System.getProperty("line.separator"));
            }
            business.forumsBelongUsers(instance.getValue());
            String file = ROOT_OUT_PATH + File.separator + "cluster_" + instance.getKey();
            OutputStreamUtils.writeSimple(buffer.toString(), file + ".txt");
            computeFrecuency(file + "_frec.txt", file + ".txt", false);
        }


        //intersectect mots de les clusters
        //On peut le garder dans un xml: version 2
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<Integer, List<DistanceUser>> userNearest : usersNearest.entrySet()) {
            buffer.append("Cluster:" + userNearest.getKey() + "\n");
            for (DistanceUser user : userNearest.getValue()) {
                List<String> forums = business.forumsBelongUsers(user.getIdentifier());
                buffer.append("id_user:" + user.getIdentifier() + " : ");
                buffer.append("id_forums:" + forums);
                buffer.append("\n");
            }
        }

        OutputStreamUtils.writeSimple(buffer.toString(), ROOT_OUT_PATH + "/userProcheEtForums.txt");

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

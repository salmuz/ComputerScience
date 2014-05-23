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
import org.montp2.m1decol.ter.utils.*;

import java.io.File;
import java.util.Collection;
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
            Collection<String> intersect =  new GlobalPreProcessing().intersectVocabulary(FORUMS_LEMMA);
            //intersect.addAll(new GlobalPreProcessing().intersectVocabulary("/Users/user/Downloads/TER/test/boolean/Avec5Forums/clusters/"));
            //intersect.addAll(new GlobalPreProcessing().intersectVocabulary("/Users/user/Downloads/TER/test/boolean/Avec5ForumsIntersect/clusters/"));
            //intersect.addAll(new GlobalPreProcessing().intersectVocabulary("/Users/user/Downloads/TER/test/boolean/Avec5ForumsIntersect2/clusters/"));
            //System.out.println(intersect);
            properties.put(NGramProperties.OTHER_STOP_WORDS, intersect);
            uni.executePreProccesing(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, List<Integer>> computeClustering(Map<Integer, Integer> arffToIdUser,
                                                                Properties propertiesCluster,
                                                                String arffFilter, String modelCluster) throws Exception {
        Clustering clu = new KMeansClustering();
        clu.computeClustering(arffFilter, modelCluster, propertiesCluster);
        return clu.computeInstanceByCluster(arffFilter, modelCluster, arffToIdUser);
    }


    static String ROOT_DATA = "/Users/user/Downloads/TER/test";
    static String DATA = ROOT_DATA + "/users_forums/";
    static String DATA_LEMMA = ROOT_DATA + "/users_forums_lemma/";
    static String FORUMS_LEMMA = ROOT_DATA + "/forums_users/";
    static String STOP_WORD = "/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt";


    static String ROOT_OUT_PATH = "/Users/user/Downloads/TER/test/boolean/";
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

        // {POST, CLUSTER, WORDS_TO_KEEP}
        // Utilisatuer qui postent sur 5 post
        int input[] = new int[]{5, 8, 100};
        // Utilisatuer qui postent sur 4 post
        //int input[] = new int[]{4, 12, 100};
        // Utilisatuer qui postent sur 3 post
        //int input[] = new int[]{3, 6, 90};
        // Utilisatuer qui postent sur 2 post
        //int input[] = new int[]{2, 7, 50};
        // Utilisatuer qui postent sur 1 post
        //int input[] = new int[]{1, 9, 50};

        ROOT_OUT_PATH += "Avec" + input[0] + "ForumsUsers";
        MODEL_CLUSTER = ROOT_OUT_PATH + "/kmeans.model";
        PATH_ARFF = ROOT_OUT_PATH;

        new File(ROOT_OUT_PATH).mkdir();

        computeUniGramme(new FilterTokenizerBoolean(input[2]), input[0] + 1, 12);

        Map<Integer, Integer> arffToIdUser = global.getMapOfInstanceArffToIdUser(
                DATA_LEMMA, properties.getProperty(NGramProperties.ARFF_DATA_FILE_PATH));

        properties.setProperty(NGramProperties.CLUSTER_MODEL, MODEL_CLUSTER);

        Properties clusterProp = new Properties();
        clusterProp.setProperty(ClusterProperties.Kmeans.PERSERVE_INSTANCE, "true");
        clusterProp.setProperty(ClusterProperties.Kmeans.DONT_REPLACE_MISSING_VALUES, "false");
        clusterProp.setProperty(ClusterProperties.Kmeans.DISPLAY_STD_DEVS, "false");
        clusterProp.setProperty(ClusterProperties.Kmeans.MAX_ITERATIONS, "500");
        clusterProp.setProperty(ClusterProperties.Kmeans.NUM_CLUSTERS, String.valueOf(input[1]));


        Map<Integer, List<Integer>> instanceByCluster = computeClustering(arffToIdUser,
                clusterProp,
                properties.getProperty(NGramProperties.ARFF_FILTER_FILE_PATH),
                properties.getProperty(NGramProperties.CLUSTER_MODEL));

        Map<Integer, List<DistanceUser>> usersNearest = near.computeNearestNeighbor(
                properties.getProperty(NGramProperties.ARFF_FILTER_FILE_PATH),
                properties.getProperty(NGramProperties.CLUSTER_MODEL), arffToIdUser);


        File rootCluster = new File(ROOT_OUT_PATH + File.separator + "clusters");
        rootCluster.mkdir();
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
            new File(file + ".txt").renameTo(new File(rootCluster.getPath() + File.separator + "cluster_" + instance.getKey() + ".txt"));
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

        OutputStreamUtils.writeSimple(buffer.toString(), ROOT_OUT_PATH + "/forumsBelongUserProche.txt");

        buffer = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> instance : instanceByCluster.entrySet()) {
            buffer.append("id_cluster:" + instance.getKey() + " ");
            buffer.append("id_forums:" + business.forumsBelongUsers(instance.getValue()));
            buffer.append("\n");
        }

        OutputStreamUtils.writeSimple(buffer.toString(), ROOT_OUT_PATH + "/forumsBelongCluster.txt");

        buffer = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> instance : instanceByCluster.entrySet()) {
            buffer.append("id_cluster:" + instance.getKey() + "\n");
            for(String item : business.percentForumsByUsers(instance.getValue())){
                buffer.append(item.replace(Constants.JDBC_SEPARATOR,":") + "\n");
            }
            buffer.append("\n");
        }

        OutputStreamUtils.writeSimple(buffer.toString(), ROOT_OUT_PATH + "/percentForumsByUsers.txt");

        /*System.out.println(global.intersectVocabulary(FORUMS_LEMMA));
        System.out.println(global.intersectVocabulary(ROOT_OUT_PATH+"/clusters/"));*/

    }
}

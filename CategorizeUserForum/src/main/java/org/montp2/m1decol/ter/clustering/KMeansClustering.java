/* ==========================================
 * CategorizeUserForum : a free Java graph-theory library
 * ==========================================
 * 
 * salmuz : Carranza Alarcon Yonatan Carlos
 * 
 * (C) Copyright 2014, by salmuz and Contributors.
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

package org.montp2.m1decol.ter.clustering;

import org.montp2.m1decol.ter.utils.ClusterProperties;
import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

import java.util.*;

public class KMeansClustering implements Clustering {

    public Clusterer computeClustering(String inPath, String outPath,Properties propertiesCluster) throws Exception {
        Instances inputInstances = WekaUtils.loadARFF(inPath);

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        euclideanDistance.setAttributeIndices("first-last");
        euclideanDistance.setDontNormalize(false);
        euclideanDistance.setInvertSelection(false);

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(
                Boolean.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.PERSERVE_INSTANCE)));
        kmeans.setDontReplaceMissingValues(
                Boolean.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.DONT_REPLACE_MISSING_VALUES)));
        kmeans.setDisplayStdDevs(
                Boolean.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.DISPLAY_STD_DEVS)));
        kmeans.setMaxIterations(
                Integer.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.MAX_ITERATIONS)));
        kmeans.setNumClusters(
                Integer.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.NUM_CLUSTERS)));
        kmeans.setSeed(10);
        //kmeans.setSeed(
          //      Integer.valueOf(propertiesCluster.getProperty(ClusterProperties.Kmeans.SEED)));
        kmeans.setDistanceFunction(euclideanDistance);
        kmeans.buildClusterer(inputInstances);

        WekaUtils.saveModel(kmeans, outPath);

        /*
        *
        * Pour obtenir les pourcentages de les clusters
        * ClusterEvaluation eval = new ClusterEvaluation();
        * eval.setClusterer(kmeans);
        * eval.evaluateClusterer(inputInstances);
        * System.out.println(eval.clusterResultsToString());
        *
        * */

        return kmeans;
    }

    public Map<Integer, List<Integer>> computeInstanceByCluster(String arffFilter, String inModel,
                                                                Map<Integer, Integer> arffToIdUser) throws Exception {

        SimpleKMeans kmeans = WekaUtils.loadModel(inModel);

        Instances data = new Instances(WekaUtils.loadARFF(arffFilter));

        int[] clusters = kmeans.getAssignments();

        Map<Integer, List<Integer>> idUserByCluster = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < data.numInstances(); i++) {
            int ind = clusters[i];

            List<Integer> users = idUserByCluster.get(ind);
            if (users == null) {
                users = new ArrayList<Integer>();
                idUserByCluster.put(ind, users);
            }
            users.add(arffToIdUser.get(i));
        }

        return idUserByCluster;

    }
}

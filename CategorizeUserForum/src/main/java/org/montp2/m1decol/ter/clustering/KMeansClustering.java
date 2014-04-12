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

import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

public class KMeansClustering implements Clustering {

    public Clusterer computeClustering(String inPath,String outPath) throws Exception {
        Instances inputInstances = WekaUtils.loadARFF(inPath);

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        euclideanDistance.setAttributeIndices("first-last");
        euclideanDistance.setDontNormalize(false);
        euclideanDistance.setInvertSelection(false);

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);
        kmeans.setDontReplaceMissingValues(false);
        kmeans.setDisplayStdDevs(false);
        kmeans.setMaxIterations(500);
        kmeans.setNumClusters(6);
        kmeans.setSeed(10);
        kmeans.setDistanceFunction(euclideanDistance);
        kmeans.buildClusterer(inputInstances);


        WekaUtils.saveModel(kmeans,outPath);

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
}

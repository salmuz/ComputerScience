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
import weka.clusterers.XMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class XMeansClustering  implements Clustering {

    public Clusterer computeClustering(String inPath, String outPath, Properties propertiesCluster) throws Exception {
        Instances inputInstances = WekaUtils.loadARFF(inPath);

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        euclideanDistance.setAttributeIndices("first-last");
        euclideanDistance.setDontNormalize(false);
        euclideanDistance.setInvertSelection(false);

        XMeans xmeans = new XMeans();
        xmeans.setMaxIterations(500);
        xmeans.setSeed(10);
        xmeans.setMinNumClusters(5);
        xmeans.setMaxNumClusters(12);
        xmeans.setMaxKMeans(1000);
        xmeans.setMaxKMeansForChildren(1000);
        xmeans.setBinValue(1.0);
        xmeans.setCutOffFactor(0.5);
        xmeans.setDebugLevel(0);
        xmeans.setMaxIterations(1);
        xmeans.buildClusterer(inputInstances);

        Enumeration<Instance> e = inputInstances.enumerateInstances();
        while(e.hasMoreElements()){
            Instance ins = e.nextElement();
            int cluster_num = xmeans.clusterInstance(ins);
            System.out.println(ins.toString());
            System.out.println(cluster_num);
        }

        WekaUtils.saveModel(xmeans,outPath);
        
        return xmeans;
    }

    public Map<Integer, List<Integer>> computeInstanceByCluster(String arffData, String inModel, Map<Integer, Integer> arffToIdUser) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

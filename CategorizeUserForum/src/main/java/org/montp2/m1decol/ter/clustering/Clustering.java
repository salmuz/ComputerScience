/* ==========================================
 * GrapheMultiPlateforme : a free Java graph-theory library
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
 * (C) Copyright 2013, by salmuz and Contributors
 *
 * Original Author: Carranza Alarcon Yonatan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
 */

package org.montp2.m1decol.ter.clustering;

import weka.clusterers.Clusterer;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: salmuz
 * Date: 12/04/2014
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public interface Clustering {

    /**
     *
     * @param inPath
     * @param outPath
     * @return
     * @throws Exception
     */
    public Clusterer computeClustering(String inPath,String outPath) throws Exception;

    /**
     *
     * @param arffData
     * @param inModel
     * @param arffToIdUser Position of id user in the file arff
     * @return
     * @throws Exception
     */
    public Map<Integer, List<Integer>> computeInstanceByCluster(String arffData,String inModel, Map<Integer, Integer> arffToIdUser) throws Exception;

}

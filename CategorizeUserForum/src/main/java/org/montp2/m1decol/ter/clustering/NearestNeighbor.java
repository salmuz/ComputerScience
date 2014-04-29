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

import org.montp2.m1decol.ter.utils.OutputStreamUtils;
import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

import java.util.*;

public class NearestNeighbor {

   public NearestNeighbor(){
   }

   public void computeNearestNeighbor(String arffData,String inFile, String outFile,Map<Integer,Integer> arffToUser) throws Exception {

       SimpleKMeans kmeans = WekaUtils.loadModel(inFile);

       EuclideanDistance eclidean = (EuclideanDistance) kmeans.getDistanceFunction();

       Instances data = new Instances(WekaUtils.loadARFF(arffData));

       int[] clusters = kmeans.getAssignments();
       Instances clusterCentroid = kmeans.getClusterCentroids();

       Map<Integer,List<DistanceUser>> nearUser = new HashMap<Integer, List<DistanceUser>>();

       for (int i = 0; i < clusterCentroid.numInstances(); i++) {
           nearUser.put(i,new ArrayList<DistanceUser>());
       }

       Map<Integer,List<Integer>> userByCluster = new HashMap<Integer, List<Integer>>();

       for (int i = 0; i < data.numInstances(); i++) {
           int ind = clusters[i];
           double dist = eclidean.distance(clusterCentroid.instance(ind), data.instance(i));
           List<DistanceUser> nears = nearUser.get(ind);
           if(nears.size() < 10){
               nears.add(new DistanceUser(i,dist));
           }else{
               DistanceUser max = Collections.max(nears);
               if(max.getDistance() > dist){
                   int maxIndex = nears.indexOf(max);
                   nears.set(maxIndex,new DistanceUser(i,dist));
               }
           }

           List<Integer>  users = userByCluster.get(ind);
           if(users == null) {
               users = new ArrayList<Integer>();
               userByCluster.put(ind,users);
           }
           users.add(i);
       }

       StringBuilder builder = new StringBuilder();
       for (Map.Entry<Integer,List<DistanceUser>> item: nearUser.entrySet()){
           builder.append(item.getKey());
           builder.append(":");
           builder.append(item.getValue().get(0).getIdentifier());
           for (int i=1;i<item.getValue().size();i++){
               DistanceUser user = item.getValue().get(i);
               builder.append(",");
               builder.append(arffToUser.get(user.getIdentifier()));
           }
           builder.append("\n");
       }

       StringBuilder builder2 = new StringBuilder();
       for(Map.Entry<Integer,List<Integer>> item: userByCluster.entrySet()){
           builder2.append(item.getKey());
           builder2.append(":");
           builder2.append(arffToUser.get(item.getValue().get(0)));
           for (int i=1;i<item.getValue().size();i++){
               builder2.append(",");
               builder2.append(arffToUser.get(item.getValue().get(i)));
           }
           builder2.append("\n");
       }

       OutputStreamUtils.writeSimple(builder2.toString(), outFile);

       //OutputStreamUtils.writeSimple(builder.toString(),outFile);
   }

}

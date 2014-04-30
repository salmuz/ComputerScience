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

package org.montp2.m1decol.ter.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public final class OutputStreamUtils{

    private static final String ENCODING = "UTF-8";

    public static void writeSimple(String data,String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, ENCODING);
        writer.println(data);
        writer.close();
    }

    public static void writeSimple(String []lines,String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, ENCODING);
        for(String data : lines)
            writer.println(data);
        writer.close();
    }

    public static void writeSimpleMap(Map<? extends Object,? extends Object> values,String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(path, ENCODING);
        for(Map.Entry entry : values.entrySet())
            writer.println(entry.getKey().toString()+":"+entry.getValue().toString());
        writer.close();
    }

    public void saveMap(){
        StringBuilder builder2 = new StringBuilder();
       /* for (Map.Entry<Integer, List<Integer>> item : userByCluster.entrySet()) {
            builder2.append(item.getKey());
            builder2.append(":");
            builder2.append(arffToUser.get(item.getValue().get(0)));
            for (int i = 1; i < item.getValue().size(); i++) {
                builder2.append(",");
                builder2.append(arffToUser.get(item.getValue().get(i)));
            }
            builder2.append("\n");
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


        */

    }


}

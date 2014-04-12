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

import weka.clusterers.Clusterer;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class WekaUtils {

    public static String EXTENSION_ARFF = ".arff";

    public static Instances loadARFF(String path) throws IOException {
        BufferedReader reader = null;
        Instances inputInstances = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            inputInstances = arff.getData();
        } finally {
            if (reader != null) reader.close();
        }
        return inputInstances;
    }

    public static void createARFF(String inPath, String outPath,List<String> excludeFiles) throws IOException {

        FastVector atts = new FastVector(1);
        atts.addElement(new Attribute("data", (FastVector) null));
        Instances data = new Instances("CategorizeUserForum", atts, 0);

        for (File file : FileUtils.ls(inPath)) {
            if(!excludeFiles.contains(file.getName())){
                double[] newInstance = new double[1];
                newInstance[0] = (double) data.attribute(0).addStringValue(InputStreamUtils.readInputStream(file.getAbsolutePath()));
                data.add(new Instance(1.0, newInstance));
            }
        }

        OutputStreamUtils.writeSimple(data.toString(), outPath);
    }

    public static <T extends Clusterer> void saveModel(T clustering, String outPath) throws Exception {
        weka.core.SerializationHelper.write(outPath, clustering);
    }

    public static <T extends Clusterer> T loadModel(String inPath) throws Exception {
        return (T) weka.core.SerializationHelper.read(inPath);
    }

}

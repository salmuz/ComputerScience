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

package org.montp2.m1decol.ter.supervised;

import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.classifiers.functions.SMO;
import weka.core.Instances;

import java.io.IOException;


public class ClassifierTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        SMO classifier;
        Instances testingInstances = new Instances(WekaUtils.loadARFF("/Users/user/Documents/XXXXEnv/ts02arff.arff"));
        int cIdx=testingInstances.numAttributes()-1;
        testingInstances.setClassIndex(cIdx);

        try {
            //Classifier deserialization
            classifier = (SMO) weka.core.SerializationHelper.read("/Users/user/Desktop/SMO.model");

            //classifier testing code

            double score = classifier.classifyInstance(testingInstances.instance(0));
            System.out.println(testingInstances.attribute("@@class@@").value((int)score));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}

package org.montp2.m1decol.ter.supervised;/* ==========================================
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

import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateArff {

    public static void main(String[] args) throws IOException {
          createArff();
    }

    private static void createArff() throws IOException {
        List<String> arffData = InputStreamUtils.readByLine("/Users/user/Documents/XXXXEnv/arff_cate_clean.arff");
        List<String> mgsLem = new ArrayList<String>();

        for (String line : InputStreamUtils.readByLine("/Users/user/Documents/XXXXEnv/ts02.txt")) {
            mgsLem.addAll(Arrays.asList(line.split("\\s")));
        }

        StringBuilder arffMessage = new StringBuilder();
        arffMessage.append("@relation 'TEST_CLASIFIER'\n\n");
        StringBuilder vectorMessage = new StringBuilder();
        vectorMessage.append("{");
        boolean copy = true;
        for (int i = 2; i < arffData.size(); i++) {
            String line = arffData.get(i);
            if (line.equalsIgnoreCase("@data")) {
                arffMessage.append(line + "\n");
                break;
            }

            if (!line.equals("")) {
                String values[] = line.split("\\s");
                if (mgsLem.contains(values[1])) {
                    vectorMessage.append(i - 2 + " 1,");
                }
            }

            arffMessage.append(line + "\n");
        }

        String vector = vectorMessage.toString();
        arffMessage.append(vector.substring(0, vector.length() - 1) + "}\n");

        OutputStreamUtils.writeSimple(arffMessage.toString(), "/Users/user/Documents/XXXXEnv/ts02arff.arff");
    }

}

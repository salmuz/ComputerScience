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

package org.montp2.m1decol.ter.servlets;

import org.montp2.m1decol.ter.gramms.UniGramsPreProcessing;
import org.montp2.m1decol.ter.utils.InputStreamUtils;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;
import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AnalyserMessageServlet", urlPatterns = {"/AnalyserMessageServlet"})
public class AnalyserMessageServlet extends HttpServlet {

    private String ROOT_PATH = "/Users/user/Documents/TER/";
    private String STOP_WORD = "/Users/user/Dropbox/MasterM1_DECOL/Semestre02/ProjetTER/TER_NLP/source/motvides.txt";
    private String IN_MODEL = "/Users/user/Downloads/TER/test/boolean/Avec1Forums/kmeans.model";
    private String ARFF_BASE = "/Users/user/Downloads/TER/test/boolean/Avec1Forums/CategorizeUserForum06052014031750bool.arff";

    /**
     * Constructeur de la classe DateServlet
     */
    public AnalyserMessageServlet() {
        super();// Appelle du constructeur parent
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String message = request.getParameter("message");

        //create file message
        try {
            OutputStreamUtils.writeSimple(message.toLowerCase(), ROOT_PATH + "message.txt");
            UniGramsPreProcessing uni = new UniGramsPreProcessing();
            uni.computeLemmatization(ROOT_PATH, STOP_WORD);

            // créer le fichier arff
            List<String> arffData = InputStreamUtils.readByLine(ARFF_BASE);
            List<String> mgsLem = new ArrayList<String>();

            for (String line : InputStreamUtils.readByLine(ROOT_PATH + "message_lemma.txt")) {
                mgsLem.addAll(Arrays.asList(line.split("\\s")));
            }

            StringBuilder arffMessage = new StringBuilder();
            arffMessage.append("@relation 'Message_Utilisateur'\n\n");
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

            OutputStreamUtils.writeSimple(arffMessage.toString(), ROOT_PATH + "message_arff.arff");


            // chercher le cluster
            SimpleKMeans kmeans = WekaUtils.loadModel(IN_MODEL);

            EuclideanDistance eclidean = (EuclideanDistance) kmeans.getDistanceFunction();

            Instances data = new Instances(WekaUtils.loadARFF(ROOT_PATH + "message_arff.arff"));

            int[] clusters = kmeans.getAssignments();
            Instances clusterCentroid = kmeans.getClusterCentroids();

            double dist = Double.MAX_VALUE;
            int cluster_current = -99;
            for (int i = 0; i < clusters.length; i++) {
                int ind = clusters[i];
                double newDist = eclidean.distance(clusterCentroid.instance(ind), data.instance(0));
                if (newDist < dist) {
                    cluster_current = ind;
                    newDist = dist;
                }
            }
            // chercher les 10 profil
            System.out.println("cluster_current:"+cluster_current);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //FileUtils.removeFile(ROOT_PATH + "message.txt");
            //FileUtils.removeFile(ROOT_PATH + "message_lemma.txt");
        }
        response.sendRedirect("/CategorizeUserForum/results.jsp");
    }

    /**
     * Implémentation de la méthode doGet pour des solicituds HTTP GET
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Implémentation de la méthode doGet pour des solicituds HTTP POST
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

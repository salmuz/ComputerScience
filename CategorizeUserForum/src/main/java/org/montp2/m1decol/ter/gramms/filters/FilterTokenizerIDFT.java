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

package org.montp2.m1decol.ter.gramms.filters;

import org.montp2.m1decol.ter.gramms.FilterTokenizer;
import org.montp2.m1decol.ter.utils.OutputStreamUtils;
import org.montp2.m1decol.ter.utils.WekaUtils;
import weka.core.Instances;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class FilterTokenizerIDFT implements FilterTokenizer {

    public void indexingToTokenizer(String inPath, String outPath) throws Exception {

        WordTokenizer wordTokenizer = new WordTokenizer();
        wordTokenizer.setDelimiters("\r \t.,;:'\"()?!");

        Instances inputInstances = WekaUtils.loadARFF(inPath);
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(inputInstances);
        filter.setIDFTransform(true);
        filter.setTFTransform(true);
        filter.setDoNotOperateOnPerClassBasis(false);
        filter.setInvertSelection(false);
        filter.setLowerCaseTokens(true);
        filter.setMinTermFreq(3);
        filter.setOutputWordCounts(true);
        filter.setTokenizer(wordTokenizer);
        filter.setUseStoplist(true);
        filter.setWordsToKeep(200);

        Instances outputInstances = Filter.useFilter(inputInstances, filter);

        OutputStreamUtils.writeSimple(outputInstances.toString(), outPath);
    }

    public String typeFilter() {
        return "IDFT";
    }
}

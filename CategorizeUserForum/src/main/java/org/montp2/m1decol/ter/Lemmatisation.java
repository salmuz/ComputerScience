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
 *
 *
 * Documentation
 * 	  ABR	abreviation
 * 	  ADJ	adjective
 * 	  ADV	adverb
 * 	  DET:ART	article
 * 	  DET:POS	possessive pronoun (ma, ta, ...)
 * 	  INT	interjection
 * 	  KON	conjunction
 * 	  NAM	proper name
 * 	  NOM	noun
 * 	  NUM	numeral
 * 	  PRO	pronoun
 * 	  PRO:DEM	demonstrative pronoun
 * 	  PRO:IND	indefinite pronoun
 * 	  PRO:PER	personal pronoun
 * 	  PRO:POS	possessive pronoun (mien, tien, ...)
 * 	  PRO:REL	relative pronoun
 * 	  PRP	preposition
 * 	  PRP:det	preposition plus article (au,du,aux,des)
 * 	  PUN	punctuation
 * 	  PUN:cit	punctuation citation
 * 	  SENT	sentence tag
 * 	  SYM	symbol
 * 	  VER:cond	verb conditional
 * 	  VER:futu	verb futur
 * 	  VER:impe	verb imperative
 * 	  VER:impf	verb imperfect
 * 	  VER:infi	verb infinitive
 * 	  VER:pper	verb past participle
 * 	  VER:ppre	verb present participle
 * 	  VER:pres	verb present
 * 	  VER:simp	verb simple past
 * 	  VER:subi	verb subjunctive imperfect
 * 	  VER:subp	verb subjunctive present
 */

package org.montp2.m1decol.ter;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;
import org.montp2.m1decol.ter.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lemmatisation{

    private static class Holder {
        static final Lemmatisation INSTANCE = new Lemmatisation();
    }

    public static Lemmatisation getInstance() {
        return Holder.INSTANCE;
    }

    private Lemmatisation() {
        System.setProperty("treetagger.home", Constants.HOME_TREETAGER);
    }

    public List<TreeTaggerWordWrapper> execution(String pathFile) throws IOException, TreeTaggerException {

        TreeTaggerWrapper taggerWrapper = new TreeTaggerWrapper<String>();
        final List<TreeTaggerWordWrapper> wordWrappers = new ArrayList<TreeTaggerWordWrapper>();
        try {
            taggerWrapper.setModel(Constants.TREETAGGER_LANGUAGE);
            taggerWrapper.setHandler(new TokenHandler<String>() {
                public void token(String token, String tag, String lemma) {
                    TreeTaggerWordWrapper word = new TreeTaggerWordWrapper(token, tag, lemma);
                    wordWrappers.add(word);
                }
            });
            taggerWrapper.process(readLinesOfFile(pathFile));
        } finally {
            taggerWrapper.destroy();
        }
        return wordWrappers;
    }

    private List<String> readLinesOfFile(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            for (String item : line.split("[^\\p{L}]+")) {
                if (item.length() > 1)
                    lines.add(item);
            }
        }
        bufferedReader.close();
        return lines;
    }
}

/* ==========================================
 * CategorizeUserForum : a free Java graph-theory library
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
import org.montp2.m1decol.ter.jobs.Task;
import org.montp2.m1decol.ter.utils.Constants;

import java.io.IOException;

import static java.util.Arrays.asList;

public class Lemmatisation extends Task {

    private static class Holder {
        static final Lemmatisation INSTANCE = new Lemmatisation();
    }

    public static Lemmatisation getInstance() {
        return Holder.INSTANCE;
    }

    private Lemmatisation(){
        System.setProperty("treetagger.home", Constants.HOME_TREETAGER);
    }

    public void execution() throws IOException,TreeTaggerException{

        TreeTaggerWrapper tt = new TreeTaggerWrapper<String>();
        try {
            tt.setModel(Constants.TREETAGGER_LANGUAGE);
            tt.setHandler(new TokenHandler<String>() {
                public void token(String token, String pos, String lemma) {
                    System.out.println(token + "\t" + pos + "\t" + lemma);
                }
            });
            tt.process(asList(new String[] { "This", "is", "a", "test", "." }));
        }finally {
            tt.destroy();
        }
    }
}

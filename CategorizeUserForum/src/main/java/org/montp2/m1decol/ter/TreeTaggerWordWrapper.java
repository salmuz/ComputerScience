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

package org.montp2.m1decol.ter;

public class TreeTaggerWordWrapper {

    private String word;
    private String tag;
    private String lemma;

    public TreeTaggerWordWrapper() {

    }

/*class TreeTaggerWordWrapper:
    def __init__(self, triplet):
    self.word,self.tag,self.lemma = triplet
    self.subTag=''
    tags=self.tag.split(":")
            if len(tags) > 1 :
    self.tag=tags[0]
    self.subTag=tags[1]

            if self.lemma != "<unknown>" :
    self.word = self.lemma

    self.isINV = False
    tagINV = ['PUN','NUM','PRP','SENT','ADV']
    subtagINV = ['DEM','PER','REL','POS']
            # INVALID

    if self.tag in tagINV or self.subTag in subtagINV:
    self.isINV = True
    else:
    regexp = re.compile(r'([/|(|)|?|«|*|;|#|+|@|,|"|.|>|=|…|♥|!|^|0-9]|-t-)')
            if regexp.search(self.word) is not None:
    self.isINV=True     */
}

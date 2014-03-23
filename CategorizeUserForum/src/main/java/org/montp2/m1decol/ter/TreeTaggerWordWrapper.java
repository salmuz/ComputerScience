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
 * (C) Copyright 2014, by salmuz and Contributors
 *
 * Original Author: Carranza Alarcon Yonatan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
 */

package org.montp2.m1decol.ter;

import java.util.regex.Pattern;

import static org.montp2.m1decol.ter.utils.Constants.tagsINV;
import static org.montp2.m1decol.ter.utils.Constants.subtagsINV;

public class TreeTaggerWordWrapper {

    private String word;
    private String tag;
    private String lemma;

    private String subTag;
    private boolean isINV;

    public TreeTaggerWordWrapper(String word, String tag, String lemma) {
        this.lemma = lemma;
        this.tag = tag;
        this.word = word;
        this.subTag = "";
        this.isINV = false;
        preprocessing();
    }

    private void preprocessing(){
        String []tags = this.tag.split(":");

        if (tags.length > 1){
            this.tag = tags[0];
            this.subTag = tags[1];
        }

        if(!this.lemma.equals("<unknown>") &&
                !lemma.contains("|")){
            this.word = this.lemma;
        }

        if(tagsINV.contains(this.tag) || subtagsINV.contains(this.subTag)){
            this.isINV = true;
        }else{
            Pattern regexp = Pattern.compile("([/|(|)|?|«|*|;|#|+|@|,|\"|.|>|=|…|♥|!|^|0-9]|-t-)");
            if(regexp.matcher(this.word).matches()){
                this.isINV = true;
            }
        }
    }

    public boolean isINV() {
        return isINV;
    }

    public String getWord() {
        return word;
    }


    @Override
    public String toString() {
        return "TreeTaggerWordWrapper{" +
                "isINV=" + isINV +
                ", lemma='" + lemma + '\'' +
                ", subTag='" + subTag + '\'' +
                ", tag='" + tag + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}

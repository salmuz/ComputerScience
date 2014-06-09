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

package org.montp2.m1decol.ter.data.beans;

import java.io.Serializable;

public class Author implements Serializable {

    private Integer idAut;
    private String pseudonymAut;
    private String profileLinkAut;
    private String rankAut;
    private String genderAut;

    public Author() {
    }

    public Author(Integer idAut, String profileLinkAut,
                  String pseudonymAut, String rankAut,
                  String genderAut) {
        this.genderAut = genderAut;
        this.idAut = idAut;
        this.profileLinkAut = profileLinkAut;
        this.pseudonymAut = pseudonymAut;
        this.rankAut = rankAut;
    }

    public String getGenderAut() {
        return genderAut;
    }

    public void setGenderAut(String genderAut) {
        this.genderAut = genderAut;
    }

    public Integer getIdAut() {
        return idAut;
    }

    public void setIdAut(Integer idAut) {
        this.idAut = idAut;
    }

    public String getProfileLinkAut() {
        return profileLinkAut;
    }

    public void setProfileLinkAut(String profileLinkAut) {
        this.profileLinkAut = profileLinkAut;
    }

    public String getPseudonymAut() {
        return pseudonymAut;
    }

    public void setPseudonymAut(String pseudonymAut) {
        this.pseudonymAut = pseudonymAut;
    }

    public String getRankAut() {
        return rankAut;
    }

    public void setRankAut(String rankAut) {
        this.rankAut = rankAut;
    }

    @Override
    public String toString() {
        return "Author{" +
                " idAut=" + idAut +
                ", genderAut='" + genderAut + '\'' +
                ", pseudonymAut='" + pseudonymAut + '\'' +
                ", profileLinkAut='" + profileLinkAut + '\'' +
                ", rankAut='" + rankAut + '\'' +
                '}';
    }
}

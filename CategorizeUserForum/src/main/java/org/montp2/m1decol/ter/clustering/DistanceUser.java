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

package org.montp2.m1decol.ter.clustering;

public class DistanceUser implements Comparable<DistanceUser>{
    private double distance;
    private int identifier;

    public DistanceUser(int identifier, double dist) {
        this.distance = dist;
        this.identifier = identifier;
    }

    public int compareTo(DistanceUser o) {
        return new Double(distance).compareTo(o.distance);
    }

    public double getDistance() {
        return distance;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DistanceUser)) return false;

        DistanceUser distUsers = (DistanceUser) o;

        if (identifier != distUsers.identifier) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return identifier;
    }

    @Override
    public String toString() {
        return "DistanceUser{" +
                "distance=" + distance +
                ", identifier=" + identifier +
                '}';
    }
}

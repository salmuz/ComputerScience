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

package org.montp2.m1decol.ter.business;

import org.montp2.m1decol.ter.business.exception.BusinessException;
import org.montp2.m1decol.ter.data.beans.Author;

import java.util.List;

public abstract class AbstractBusiness {

    public abstract List<String> getUserPostInMinAndMaxForum(int minUserToPost, int maxUserToPost,
                                              String prefixFile, String suffixFile) throws BusinessException;

    public abstract List<String> forumsBelongUsers(List<Integer> users) throws BusinessException;

    public abstract List<String> forumsBelongUsers(Integer idUser) throws BusinessException;

    public abstract List<String> percentForumsByUsers(List<Integer> users) throws BusinessException;

    public abstract List<Author> findUsersByIDs(List<Integer> users) throws BusinessException;
}

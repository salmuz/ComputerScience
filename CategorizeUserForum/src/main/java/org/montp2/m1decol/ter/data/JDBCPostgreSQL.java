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

package org.montp2.m1decol.ter.data;

import org.montp2.m1decol.ter.data.exception.JDBCException;
import org.montp2.m1decol.ter.utils.Constants;
import org.montp2.m1decol.ter.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostgreSQL extends JDBCAbstract {

    protected static final String USER_IN_FORUM_SELECT =
            " select distinct t.id_for " +
                    " from website w " +
                    " inner join forum f on f.id_web = w.id_web" +
                    " inner join topic t on t.id_for = f.id_for" +
                    " inner join message m on m.id_top = t.id_top" +
                    " where w.id_web = 1" +
                    " and m.id_aut IN (%s)";


    protected static final String PERCENT_FORUMS_BY_USERS_SELECT =
            " select id_for, 100*count/(sum(count) over()),count " +
                    " from ( select tbl.id_for, count(id_for) as count " +
                    "           from " +
                    "               (select distinct m.id_aut,t.id_for " +
                    "                from website w " +
                    "                   inner join forum f on f.id_web = w.id_web" +
                    "                   inner join topic t on t.id_for = f.id_for " +
                    "                   inner join message m on m.id_top = t.id_top " +
                    "                where w.id_web = 1 " +
                    "                   and m.id_aut in (%s)) as tbl " +
                    "        group by tbl.id_for ) a";

    protected static final String USER_FREQUENTS_BY_FORUM =
            "   select distinct tbl.id_aut from " +
                    "             (select m.id_aut, " +
                    "                           f.id_for, " +
                    "                           count(*) over( partition by  m.id_aut) as nbposte " +
                    "                      from website w\n" +
                    "                        inner join forum f on f.id_web = w.id_web " +
                    "                        inner join topic t on t.id_for = f.id_for " +
                    "                        inner join message m on m.id_top = t.id_top " +
                    "                    where w.id_web = 1" +
                    "                    group by m.id_aut, f.id_for) as tbl " +
                    "                where nbposte = ? ";

    public List<String> usersVeryFrequentsByForum(int MIN, int MAX) throws JDBCException {

        List<String> userExclude = new ArrayList<String>();
        PreparedStatement pStmt = null;
        Connection con = connection();
        try {
            con.setAutoCommit(true);
            for (int i = MIN; i <= MAX; i++) {
                pStmt = con.prepareCall(USER_FREQUENTS_BY_FORUM);
                pStmt.setInt(1, i);
                ResultSet rs = pStmt.executeQuery();
                while (rs.next()) {
                    userExclude.add(rs.getString(1));
                }
            }
        } catch (SQLException se) {
            throw new JDBCException(se);
        } finally {
            try {
                if (pStmt != null) pStmt.close();
                con.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return userExclude;
    }

    public synchronized List<String> forumsBelongUsers(List<Integer> users) throws JDBCException {
        PreparedStatement pStmt = null;
        Connection con = connection();
        List<String> forums = new ArrayList<String>();
        try {
            con.setAutoCommit(true);
            pStmt = con.prepareCall(String.format(USER_IN_FORUM_SELECT, JDBCUtils.preparePlaceHolders(users.size())));
            int i = 1;
            for (Integer item : users) {
                pStmt.setInt(i++, item);
            }
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                forums.add(rs.getString(1) + "");
            }
        } catch (SQLException se) {
            throw new JDBCException(se);
        } finally {
            try {
                if (pStmt != null) pStmt.close();
                con.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return forums;
    }


    public List<String> percentForumsByUsers(List<Integer> users) throws JDBCException {

        List<String> table = new ArrayList<String>();
        PreparedStatement pStmt = null;
        Connection con = connection();
        try {
            con.setAutoCommit(true);
            pStmt = con.prepareCall(String.format(PERCENT_FORUMS_BY_USERS_SELECT, JDBCUtils.preparePlaceHolders(users.size())));
            int i = 1;
            for (Integer item : users) {
                pStmt.setInt(i++, item);
            }
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                table.add(rs.getString(1) + Constants.JDBC_SEPARATOR +
                        rs.getString(2) + Constants.JDBC_SEPARATOR +
                        rs.getString(3));
            }
        } catch (SQLException se) {
            throw new JDBCException(se);
        } finally {
            try {
                if (pStmt != null) pStmt.close();
                con.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return table;
    }
}



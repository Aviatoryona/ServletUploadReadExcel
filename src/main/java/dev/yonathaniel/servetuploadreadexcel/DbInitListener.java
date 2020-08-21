/*
 * Copyright (C) 2020 Aviator
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package dev.yonathaniel.servetuploadreadexcel;

import dev.yonathaniel.servetuploadreadexcel.db.DbConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Aviator
 */
public class DbInitListener implements ServletContextListener {

    private DbConnection dbConnection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ServletContext context = sce.getServletContext();
            dbConnection = DbConnection.getInstance();
            context.setAttribute("dbConnection", dbConnection);
        } catch (SQLException ex) {
            Logger.getLogger(DbInitListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbInitListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbInitListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

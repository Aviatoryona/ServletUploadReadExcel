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
import dev.yonathaniel.servetuploadreadexcel.models.UserModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aviator
 */
public class App {

    String fileName;

    public App(String fileName, DbConnection dbConnection) {
        this.fileName = fileName;
        this.dbConnection = dbConnection;
    }

    //
    public void saveData() {

    }

    //void read excel file
    private List<UserModel> getUserModels() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(getFileName()));
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            List<UserModel> models = new ArrayList<>();
            int numRow = 0;
            while (itr.hasNext()) {
                if (numRow != 0) {
                    Row row = itr.next();
                    UserModel userModel = new UserModel(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(2).getStringCellValue(),
                            row.getCell(1).getNumericCellValue()
                    );
                    models.add(userModel);
                } else {
                    itr.next();
                    numRow++;
                }
            }
            return models;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //
    DbConnection dbConnection;

    private void saveRecords(List<UserModel> lists) {
        try {
            if (dbConnection == null) {
                dbConnection = DbConnection.getInstance();
            }
            if (lists == null) {
                return;
            }

            lists.forEach(list -> {
                addToDb(list);
            });

        } catch (SQLException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addToDb(UserModel list) {
        try {
            String sql = "INSERT INTO excelusers(name,age,town) VALUES(?,?,?)";
            PreparedStatement ps = dbConnection.getPreparedStatement(sql);
            ps.setString(1, list.getName());
            ps.setDouble(2, list.getAge());
            ps.setString(3, list.getTown());
            dbConnection.execute(ps);

        } catch (SQLException ex) {
            Logger.getLogger(App.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getFileName() {
        return fileName;
    }

    /*
    Test read excel and insert files to db
     */
    public static void main(String[] args) {
        List<UserModel> l = new App("uploads/users.xlsx", null).getUserModels();
        System.out.println(l.size());
        new App("uploads/users.xlsx", null).saveRecords(l);
    }
}

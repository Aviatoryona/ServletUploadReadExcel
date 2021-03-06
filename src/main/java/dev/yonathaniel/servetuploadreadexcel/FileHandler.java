/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yonathaniel.servetuploadreadexcel;

import dev.yonathaniel.servetuploadreadexcel.db.DbConnection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Aviator
 */
@WebServlet(name = "FileHandler", urlPatterns = {"/FileHandler"})
@MultipartConfig(location = "C:\\uploads\\")
public class FileHandler extends HttpServlet {

    DbConnection dbConnection;
    String path;

    @Override
    public void init() throws ServletException {
        path = getServletContext().getInitParameter("file-upload");
        dbConnection = (DbConnection) getServletContext().getAttribute("dbConnection");
        File f = new File("uploads");
        if (!f.exists()) {
            System.out.println("");
            f.mkdirs();
        }
        path = f.getPath();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        doUpload(request, response);
        doUpload1(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*

     */
    private void doUpload1(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> fileName = new ArrayList<>();
            for (Part part : request.getParts()) {
                part.write(part.getSubmittedFileName());
                fileName.add(readFileName(part));
            }

            fileName.forEach(string -> {
                new App("C:\\uploads\\".concat(string), dbConnection).saveData();
            });

        } catch (IOException | ServletException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readFileName(Part part) {
        String strContent = part.getHeader("content-disposition");
        String[] tokens = strContent.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    /*

     */
    private void doUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");

            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(4 * 1024);
            diskFileItemFactory.setRepository(new File("c:\\temp"));

            ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
            upload.setSizeMax(250 * 1024);//50mb
            List fileItems = upload.parseRequest(request);
            File file = null;
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
//                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
//                    String contentType = fi.getContentType();
//                    boolean isInMemory = fi.isInMemory();
//                    long sizeInBytes = fi.getSize();

                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(path + File.separator + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(path + File.separator + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                }
            }
            if (file != null) {
                response.getWriter().write(file.getPath());
                new App(file.getName(), dbConnection).saveData();
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

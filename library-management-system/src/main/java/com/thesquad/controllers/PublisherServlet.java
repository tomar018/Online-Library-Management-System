package com.thesquad.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thesquad.connection.DBConnection;
import com.thesquad.dao.AddressDAO;
import com.thesquad.dao.CommuneDAO;
import com.thesquad.dao.MunicipalityDAO;
import com.thesquad.dao.ProvinceDAO;
import com.thesquad.dao.PublisherDAO;
import com.thesquad.dao.PublisherEmailDAO;
import com.thesquad.dao.PublisherPhoneDAO;
import com.thesquad.models.AddressModel;
import com.thesquad.models.CommuneModel;
import com.thesquad.models.MunicipalityModel;
import com.thesquad.models.ProvinceModel;
import com.thesquad.models.PublisherEmailModel;
import com.thesquad.models.PublisherModel;
import com.thesquad.models.PublisherPhoneModel;
import com.thesquad.utils.Helpers;

/**
 *
 * @author edsonpaulo
 */
@WebServlet(name = "PublisherServlet", urlPatterns = {"/publisher-servlet"})
public class PublisherServlet extends HttpServlet {

    private DBConnection connection;

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            connection = new DBConnection();

            if (request.getParameter("operation") != null) {
                ProvinceDAO provinceDao = new ProvinceDAO();
                MunicipalityDAO muniDao = new MunicipalityDAO();
                CommuneDAO communeDao = new CommuneDAO();

                Gson json = new Gson();
                PrintWriter writer = response.getWriter();
                response.setContentType("text/html");
                String op = request.getParameter("operation");

                int id = Integer.parseInt(request.getParameter("id"));

                switch (op) {
                    case "province" -> {
                        List<ProvinceModel> provinceList = provinceDao.getProvincesByCountryId(id, connection);
                        writer.write(json.toJson(provinceList));
                    }
                    case "municipality" -> {
                        List<MunicipalityModel> muniList = muniDao.getMunicipalitiesByProvinceId(id, connection);
                        writer.write(json.toJson(muniList));
                    }
                    case "commune" -> {
                        List<CommuneModel> communeList = communeDao.getCommunesByMunicipalityId(id, connection);
                        writer.write(json.toJson(communeList));
                    }
                    default -> {
                    }
                }
            }

            if (request.getParameter("action") != null) {
                PublisherDAO publisherDao = new PublisherDAO();
                int id = Integer.parseInt(request.getParameter("id"));

                if (request.getParameter("action").equals("delete")) {
                    publisherDao.delete(id, connection);
                    response.sendRedirect(request.getContextPath() + "/publisher/list.jsp");
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.closeConnection();
            }
        }
    }
    
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        try {
            connection = new DBConnection();
            PublisherModel publisher = new PublisherModel();
            PublisherDAO publisherDao = new PublisherDAO();

            publisher.setName(request.getParameter("name"));
            publisher.setNif(request.getParameter("nif"));            
            publisher.setFax(request.getParameter("fax"));

            /**
             * FILL ADDRESS
             */
            AddressModel address = new AddressModel();
            AddressDAO addressDao = new AddressDAO();

            address.setStreet(request.getParameter("street"));
            address.setHouseNum(Integer.parseInt(request.getParameter("houseNum")));
            address.setDistrict(request.getParameter("district"));

            // address.setCommune(Integer.parseInt(request.getParameter("commune")));
            
            /**
             * FILL PHONE
             */
            PublisherPhoneDAO publisherPhoneDao = new PublisherPhoneDAO();
            PublisherPhoneModel publisherPhone1 = new PublisherPhoneModel();
            PublisherPhoneModel publisherPhone2 = new PublisherPhoneModel();

            publisherPhone1.setPhone(request.getParameter("phone1"));
            
            boolean hasPhone2 = !request.getParameter("phone2").trim().isEmpty();
            if (hasPhone2) {
                publisherPhone2.setPhone(request.getParameter("phone2"));
            }
            
            /**
             * FILL EMAIL
             */
            PublisherEmailDAO publisherEmailDao = new PublisherEmailDAO();
            PublisherEmailModel publisherEmail1 = new PublisherEmailModel();
            PublisherEmailModel publisherEmail2 = new PublisherEmailModel();
            
            publisherEmail1.setEmail(request.getParameter("email1"));
            
            boolean hasEmail2 = !request.getParameter("email2").trim().isEmpty();
            if (hasEmail2) {
                publisherEmail2.setEmail(request.getParameter("email2"));
            }
                        
            if (request.getParameter("edit") == null) {
                // ***** CREATE MODE ***/
                // CREATE ADDRESS
                addressDao.create(address, connection);
                
                // CREATE PUBLISHER WITH THE ADDRESS ADDED ABOVE
                publisher.setAddressId(Helpers.getIdOfLastRow("morada", connection));
                publisherDao.create(publisher, connection);
                
                int publisherId = Helpers.getIdOfLastRow("editora", connection);
                
                // INSERT PHONE AFTER CREATE PERSON
                publisherPhone1.setPublisherId(publisherId);
                publisherPhoneDao.create(publisherPhone1, connection);
                
                // CREATE PHONE 2 IF IT EXISTS
                if(hasPhone2){
                    publisherPhone2.setPublisherId(publisherId);
                    publisherPhoneDao.create(publisherPhone2, connection);
                }
                
                // INSERT EMAIL AFTER CREATE PERSON
                publisherEmail1.setPublisherId(publisherId);
                publisherEmailDao.create(publisherEmail1, connection);
                
                
                // CREATE EMAIL 2 IF IT EXISTS
                if(hasEmail2){
                    publisherEmail2.setPublisherId(publisherId);
                    publisherEmailDao.create(publisherEmail2, connection);
                }
            }
            else {             
                // ***** EDIT MODE ***/
                int publisherId = Integer.parseInt(request.getParameter("publisherId"));
                
                // Update the publisher phone
                publisherPhone1.setPublisherPhoneId(Integer.parseInt(request.getParameter("phone1Id")));
                publisherPhoneDao.update(publisherPhone1, connection);
                
                if(hasPhone2) {
                    if(request.getParameter("phone2Id") == null || request.getParameter("phone2Id").isEmpty()) {
                        publisherPhone2.setPublisherId(publisherId);
                        publisherPhoneDao.create(publisherPhone2, connection);
                    }
                    else {
                        publisherPhone2.setPublisherPhoneId(Integer.parseInt(request.getParameter("phone2Id")));
                        publisherPhoneDao.update(publisherPhone2, connection);
                    }
                }

                // Update the publisher email
                publisherEmail1.setPublisherEmailId(Integer.parseInt(request.getParameter("email1Id")));
                publisherEmailDao.update(publisherEmail1, connection);
                
                if(hasEmail2) {
                    if(request.getParameter("email2Id") == null || request.getParameter("email2Id").isEmpty()) {
                        publisherEmail2.setPublisherId(publisherId);
                        publisherEmailDao.create(publisherEmail2, connection);
                    }
                    else {
                        publisherEmail2.setPublisherEmailId(Integer.parseInt(request.getParameter("email2Id")));
                        publisherEmailDao.update(publisherEmail2, connection);
                    }
                }

                // Update the publisher address
                address.setAddressId(Integer.parseInt(request.getParameter("addressId")));
                addressDao.update(address, connection);

                // Update the publisher
                publisher.setPublisherId(publisherId);
                publisherDao.update(publisher, connection);
            }

           response.sendRedirect(request.getContextPath() + "/publisher/list.jsp");

        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.closeConnection();
            }
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

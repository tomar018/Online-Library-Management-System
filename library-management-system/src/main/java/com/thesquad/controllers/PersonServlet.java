package com.thesquad.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.thesquad.connection.DBConnection;
import com.thesquad.dao.AddressDAO;
import com.thesquad.dao.AuthorDAO;
import com.thesquad.dao.CommuneDAO;
import com.thesquad.dao.MunicipalityDAO;
import com.thesquad.dao.PersonDAO;
import com.thesquad.dao.PersonEmailDAO;
import com.thesquad.dao.PersonPhoneDAO;
import com.thesquad.dao.ProvinceDAO;
import com.thesquad.dao.ReaderDAO;
import com.thesquad.models.AddressModel;
import com.thesquad.models.AuthorModel;
import com.thesquad.models.CommuneModel;
import com.thesquad.models.MunicipalityModel;
import com.thesquad.models.PersonEmailModel;
import com.thesquad.models.PersonModel;
import com.thesquad.models.PersonPhoneModel;
import com.thesquad.models.ProvinceModel;
import com.thesquad.models.ReaderModel;
import com.thesquad.utils.Helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PersonServlet", urlPatterns = {"/person-servlet"})
public class PersonServlet extends HttpServlet {

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
                PersonDAO personDao = new PersonDAO();
                int id = Integer.parseInt(request.getParameter("id"));

                if (request.getParameter("action").equals("delete")) {
                    personDao.delete(id, connection);
                    response.sendRedirect(request.getContextPath() + "/person/list.jsp");
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
            PersonModel person = new PersonModel();
            PersonDAO personDao = new PersonDAO();

            person.setName(request.getParameter("name"));
            person.setSurname(request.getParameter("surname"));
            person.setBi(request.getParameter("bi"));
            person.setBirthDate(Helpers.stringToDateTime(request.getParameter("birthDate"), true));
            person.setGenderId(Integer.parseInt(request.getParameter("gender")));

            /**
             * FILL ADDRESS
             *
             *
             */
            AddressModel address = new AddressModel();
            AddressDAO addressDao = new AddressDAO();

            address.setStreet(request.getParameter("street"));
            address.setHouseNumber(Integer.parseInt(request.getParameter("houseNumber")));
            address.setNeighborhood(request.getParameter("neighborhood"));
            address.setDistrictId(Integer.parseInt(request.getParameter("districtId")));

            /**
             * FILL PHONE
             */
            PersonPhoneDAO personPhoneDao = new PersonPhoneDAO();
            PersonPhoneModel personPhone1 = new PersonPhoneModel();
            PersonPhoneModel personPhone2 = new PersonPhoneModel();

            personPhone1.setPhone(request.getParameter("phone1"));

            boolean hasPhone2 = !request.getParameter("phone2").trim().isEmpty();
            if (hasPhone2) {
                personPhone2.setPhone(request.getParameter("phone2"));
            }

            /**
             * FILL EMAIL
             */
            PersonEmailDAO personEmailDao = new PersonEmailDAO();
            PersonEmailModel personEmail1 = new PersonEmailModel();
            PersonEmailModel personEmail2 = new PersonEmailModel();

            personEmail1.setEmail(request.getParameter("email1"));

            boolean hasEmail2 = !request.getParameter("email2").trim().isEmpty();
            if (hasEmail2) {
                personEmail2.setEmail(request.getParameter("email2"));
            }

            if (request.getParameter("edit") == null) {
                // ***** CREATE MODE ***/
                // CREATE ADDRESS
                addressDao.create(address, connection);

                // CREATE PERSON WITH THE ADDRESS ADDED ABOVE
                person.setAddressId(Helpers.getIdOfLastRow("morada", connection));
                personDao.create(person, connection);

                int personId = Helpers.getIdOfLastRow("pessoa", connection);

                // INSERT PHONE AFTER CREATE PERSON
                personPhone1.setPersonId(personId);
                personPhoneDao.create(personPhone1, connection);

                // CREATE PHONE 2 IF IT EXISTS
                if (hasPhone2) {
                    personPhone2.setPersonId(personId);
                    personPhoneDao.create(personPhone2, connection);
                }

                // INSERT EMAIL AFTER CREATE PERSON
                personEmail1.setPersonId(personId);
                personEmailDao.create(personEmail1, connection);

                // CREATE EMAIL 2 IF IT EXISTS
                if (hasEmail2) {
                    personEmail2.setPersonId(personId);
                    personEmailDao.create(personEmail2, connection);
                }

                /**
                 * SAVE PERSON AS AUTHOR OR READER
                 */
                if ("AUTHOR".equals(request.getParameter("personType"))) {
                    AuthorModel author = new AuthorModel();
                    author.setPersonId(personId);
                    new AuthorDAO().create(author, connection);
                } else {
                    ReaderModel reader = new ReaderModel();
                    reader.setPersonId(personId);
                    new ReaderDAO().create(reader, connection);
                }
            } else {
                // ***** EDIT MODE ***/
                int personId = Integer.parseInt(request.getParameter("personId"));

                // Update the person phone
                personPhone1.setPersonPhoneId(Integer.parseInt(request.getParameter("phone1Id")));
                personPhoneDao.update(personPhone1, connection);

                if (hasPhone2) {
                    if (request.getParameter("phone2Id") == null || request.getParameter("phone2Id").isEmpty()) {
                        personPhone2.setPersonId(personId);
                        personPhoneDao.create(personPhone2, connection);
                    } else {
                        personPhone2.setPersonPhoneId(Integer.parseInt(request.getParameter("phone2Id")));
                        personPhoneDao.update(personPhone2, connection);
                    }
                }

                // Update the person email
                personEmail1.setPersonEmailId(Integer.parseInt(request.getParameter("email1Id")));
                personEmailDao.update(personEmail1, connection);

                if (hasEmail2) {
                    if (request.getParameter("email2Id") == null || request.getParameter("email2Id").isEmpty()) {
                        personEmail2.setPersonId(personId);
                        personEmailDao.create(personEmail2, connection);
                    } else {
                        personEmail2.setPersonEmailId(Integer.parseInt(request.getParameter("email2Id")));
                        personEmailDao.update(personEmail2, connection);
                    }
                }

                // Update the person address
                address.setAddressId(Integer.parseInt(request.getParameter("addressId")));
                addressDao.update(address, connection);

                // Update the person
                person.setPersonId(personId);
                personDao.update(person, connection);
            }

            response.sendRedirect(request.getContextPath() + "/person/list.jsp");

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
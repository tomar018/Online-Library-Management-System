package com.thesquad.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thesquad.connection.DBConnection;
import com.thesquad.models.AuthorModel;

public class AuthorDAO {

    public AuthorDAO() {

    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public void create(AuthorModel author, DBConnection connection) {
        String sql = "INSERT INTO author(person_id) VALUES(?)";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, author.getPersonId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public void update(AuthorModel author, DBConnection connection) {
        String sql = "UPDATE author SET person_id = ? WHERE author_id = ?";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, author.getPersonId());
            ps.setInt(2, author.getAuthorId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public void delete(int authorId, DBConnection connection) {
        String sql = "DELETE FROM author WHERE author_id = ?";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, authorId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public List<AuthorModel> getAll(DBConnection connection) {
        String sql = "SELECT * FROM author";
        List<AuthorModel> authorList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                AuthorModel author = new AuthorModel();
                author.setAuthorId(resultSet.getInt("author_id"));
                author.setPersonId(resultSet.getInt("person_id"));
                author.setCreationDate(resultSet.getTimestamp("created_at").toLocalDateTime());
                authorList.add(author);
            }
            ps.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorList;
    }

    @SuppressWarnings({"ConvertToTryWithResources", "CallToPrintStackTrace"})
    public AuthorModel getAuthorById(int authorId, DBConnection connection) {
        String sql = "SELECT * FROM author WHERE author_id = ?";
        try {
            AuthorModel author = new AuthorModel();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, authorId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                author.setAuthorId(resultSet.getInt("author_id"));
                author.setPersonId(resultSet.getInt("person_id"));
                author.setCreationDate(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
            ps.close();
            resultSet.close();
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
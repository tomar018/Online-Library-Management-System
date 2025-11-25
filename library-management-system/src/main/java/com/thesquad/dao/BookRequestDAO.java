package com.thesquad.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.thesquad.models.BookRequestModel;
import com.thesquad.connection.DBConnection;

public class BookRequestDAO {
 

    public BookRequestDAO() {
    }

    // Create method for MySQL
    public void create(BookRequestModel bookRequest, DBConnection connection) {
        String sql = "INSERT INTO requisicao(fk_livro, fk_leitor, data_requisicao, data_entrega) values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, bookRequest.getBookId());
            ps.setInt(2, bookRequest.getReaderId());
            ps.setTimestamp(3, Timestamp.valueOf(bookRequest.getRequestDate()));
            ps.setTimestamp(4, Timestamp.valueOf(bookRequest.getReturnDate()));

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update method for MySQL
    public void update(BookRequestModel bookRequest, DBConnection connection) {
        String sql = "UPDATE requisicao SET fk_livro = ?, fk_leitor = ?, data_requisicao = ?, data_entrega = ? WHERE pk_requisicao = ?";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);

            ps.setInt(1, bookRequest.getBookId());
            ps.setInt(2, bookRequest.getReaderId());
            ps.setTimestamp(3, Timestamp.valueOf(bookRequest.getRequestDate()));
            ps.setTimestamp(4, Timestamp.valueOf(bookRequest.getReturnDate()));
            ps.setInt(5, bookRequest.getRequestId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete method for MySQL
    public void delete(int requestId, DBConnection connection) {
        String sql = "DELETE FROM requisicao WHERE pk_requisicao = ?";
        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, requestId);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all requests method for MySQL
    public List<BookRequestModel> getAll(DBConnection connection) {
        String sql = "SELECT * FROM requisicao";

        List<BookRequestModel> requestList = new ArrayList<>();

        try {
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                BookRequestModel request = new BookRequestModel();
                request.setRequestId(resultSet.getInt("pk_requisicao"));
                request.setBookId(resultSet.getInt("fk_livro"));
                request.setReaderId(resultSet.getInt("fk_leitor"));
                request.setRequestDate(resultSet.getTimestamp("data_requisicao").toLocalDateTime());
                request.setReturnDate(resultSet.getTimestamp("data_entrega").toLocalDateTime());
                request.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());

                requestList.add(request);
            }
            ps.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requestList;
    }

    // Get a book request by ID for MySQL
    public BookRequestModel getBookRequestById(int requestId, DBConnection connection) {
        String sql = "SELECT * FROM requisicao WHERE pk_requisicao = ?";

        try {
            BookRequestModel request = new BookRequestModel();
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, requestId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                request.setRequestId(resultSet.getInt("pk_requisicao"));
                request.setBookId(resultSet.getInt("fk_livro"));
                request.setReaderId(resultSet.getInt("fk_leitor"));
                request.setRequestDate(resultSet.getTimestamp("data_requisicao").toLocalDateTime());
                request.setReturnDate(resultSet.getTimestamp("data_entrega").toLocalDateTime());
                request.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());
            }

            ps.close();
            resultSet.close();
            return request;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.thesquad.dao;

import com.thesquad.models.BookAuthorModel;
import com.thesquad.connection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookAuthorDAO {

    private static final Logger LOGGER = Logger.getLogger(BookAuthorDAO.class.getName());

    public BookAuthorDAO() {
    }

    // Create method for inserting book-author relationships
    public void create(BookAuthorModel bookAuthor, DBConnection connection) {
        String sql = "INSERT INTO livro_autor(fk_livro, fk_autor) VALUES (?, ?)";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookAuthor.getBookId());
            ps.setInt(2, bookAuthor.getAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating book-author relationship", e);
        }
    }

    // Update method for modifying existing book-author relationships
    public void update(BookAuthorModel bookAuthor, DBConnection connection) {
        String sql = "UPDATE livro_autor SET fk_livro = ?, fk_autor = ? WHERE pk_livro_autor = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookAuthor.getBookId());
            ps.setInt(2, bookAuthor.getAuthorId());
            ps.setInt(3, bookAuthor.getBookAuthorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating book-author relationship", e);
        }
    }

    // Delete method for removing book-author relationships
    public void delete(int bookAuthorId, DBConnection connection) {
        String sql = "DELETE FROM livro_autor WHERE pk_livro_autor = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookAuthorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting book-author relationship", e);
        }
    }

    // Fetch all book-author relationships
    public List<BookAuthorModel> getAll(DBConnection connection) {
        String sql = "SELECT * FROM livro_autor";
        List<BookAuthorModel> bookAuthorList = new ArrayList<>();
        
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                BookAuthorModel bookAuthor = new BookAuthorModel();
                bookAuthor.setBookAuthorId(resultSet.getInt("pk_livro_autor"));
                bookAuthor.setBookId(resultSet.getInt("fk_livro"));
                bookAuthor.setAuthorId(resultSet.getInt("fk_autor"));
                bookAuthor.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());
                bookAuthorList.add(bookAuthor);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all book-author relationships", e);
        }

        return bookAuthorList;  
    }

    public BookAuthorModel getBookAuthorById(int bookAuthorId, DBConnection connection) {
        String sql = "SELECT * FROM livro_autor WHERE pk_livro_autor = ?";
        
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookAuthorId);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    BookAuthorModel bookAuthor = new BookAuthorModel();
                    bookAuthor.setBookAuthorId(resultSet.getInt("pk_livro_autor"));
                    bookAuthor.setBookId(resultSet.getInt("fk_livro"));
                    bookAuthor.setAuthorId(resultSet.getInt("fk_autor"));
                    bookAuthor.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());
                    return bookAuthor;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching book-author relationship by ID", e);
        }

        return null;  
    }
}
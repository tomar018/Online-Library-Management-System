package com.thesquad.dao;

import com.thesquad.models.BookModel;
import com.thesquad.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {

    private static final Logger LOGGER = Logger.getLogger(BookDAO.class.getName());

    public BookDAO() {}

    public void create(BookModel book, DBConnection connection) {
        String sql = "INSERT INTO livro(nome, isbn, num_paginas, num_edicao, ano_lancamento, "
                + "fk_editora, fk_estado, fk_localizacao, fk_categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getIsbn());
            ps.setInt(3, book.getNumPages());
            ps.setInt(4, book.getEditionNum());
            ps.setInt(5, book.getReleaseYear());
            ps.setInt(6, book.getPublisherId());
            ps.setInt(7, book.getStatusId());
            ps.setInt(8, book.getLocationId());
            ps.setInt(9, book.getCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating book", e);
        }
    }

    public void update(BookModel book, DBConnection connection) {
        String sql = "UPDATE livro SET nome = ?, isbn = ?, num_paginas = ?, num_edicao = ?, "
                + "ano_lancamento = ?, fk_estado = ?, fk_localizacao = ?, fk_categoria = ? WHERE pk_livro = ?";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getIsbn());
            ps.setInt(3, book.getNumPages());
            ps.setInt(4, book.getEditionNum());
            ps.setInt(5, book.getReleaseYear());
            ps.setInt(6, book.getPublisherId());
            ps.setInt(7, book.getStatusId());
            ps.setInt(8, book.getLocationId());
            ps.setInt(9, book.getCategoryId());
            ps.setInt(10, book.getBookId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating book", e);
        }
    }

    public void delete(int livroId, DBConnection connection) {
        String sql = "DELETE FROM livro WHERE pk_livro = ?";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, livroId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting book", e);
        }
    }

    public List<BookModel> getAll(DBConnection connection) {
        String sql = "SELECT * FROM livro";
        List<BookModel> bookList = new ArrayList<>();

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                BookModel book = new BookModel();
                book.setBookId(resultSet.getInt("pk_livro"));
                book.setName(resultSet.getString("nome"));
                book.setIsbn(resultSet.getString("isbn"));
                book.setNumPages(resultSet.getInt("num_paginas"));
                book.setEditionNum(resultSet.getInt("num_edicao"));
                book.setReleaseYear(resultSet.getInt("ano_lancamento"));
                book.setPublisherId(resultSet.getInt("fk_editora"));
                book.setStatusId(resultSet.getInt("fk_estado"));
                book.setLocationId(resultSet.getInt("fk_localizacao"));
                book.setCategoryId(resultSet.getInt("fk_categoria"));
                book.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());

                bookList.add(book);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all books", e);
        }

        return bookList;  // Return an empty list if no books are found
    }

    public BookModel getBookById(int bookId, DBConnection connection) {
        String sql = "SELECT * FROM livro WHERE pk_livro = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    BookModel book = new BookModel();
                    book.setBookId(resultSet.getInt("pk_livro"));
                    book.setName(resultSet.getString("nome"));
                    book.setIsbn(resultSet.getString("isbn"));
                    book.setNumPages(resultSet.getInt("num_paginas"));
                    book.setEditionNum(resultSet.getInt("num_edicao"));
                    book.setReleaseYear(resultSet.getInt("ano_lancamento"));
                    book.setPublisherId(resultSet.getInt("fk_editora"));
                    book.setStatusId(resultSet.getInt("fk_estado"));
                    book.setLocationId(resultSet.getInt("fk_localizacao"));
                    book.setCategoryId(resultSet.getInt("fk_categoria"));
                    book.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());

                    return book;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching book by ID", e);
        }
        return null;
    }

    public Vector<String> getBookAuthors(int bookId, DBConnection connection) {
        Vector<String> authors = new Vector<>();
        String sql = "SELECT pessoa.nome FROM livro_autor "
                + "INNER JOIN autor ON livro_autor.fk_autor = autor.pk_autor "
                + "INNER JOIN livro ON livro_autor.fk_livro = livro.pk_livro "
                + "INNER JOIN pessoa ON autor.fk_pessoa = pessoa.pk_pessoa WHERE livro_autor.fk_livro = ?";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    authors.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching authors for book", e);
        }

        return authors;
    }

    public Vector<String> getBookTags(int bookId, DBConnection connection) {
        Vector<String> tags = new Vector<>();
        String sql = "SELECT descritores.nome FROM livro_descritores "
                + "INNER JOIN descritores ON livro_descritores.fk_descritores = descritores.pk_descritores "
                + "INNER JOIN livro ON livro_descritores.fk_livro = livro.pk_livro WHERE livro_descritores.fk_livro = ?";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, bookId);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    tags.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching tags for book", e);
        }

        return tags;
    }
}

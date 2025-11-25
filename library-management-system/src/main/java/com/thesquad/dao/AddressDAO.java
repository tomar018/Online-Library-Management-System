package com.thesquad.dao;

import com.thesquad.models.AddressModel;
import com.thesquad.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressDAO {

    private static final Logger LOGGER = Logger.getLogger(AddressDAO.class.getName());

    public AddressDAO() {}

    // Create an address
    public void create(AddressModel address, DBConnection connection) {
        String sql = "INSERT INTO address (street, house_number, neighborhood, district_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, address.getStreet());
            ps.setInt(2, address.getHouseNumber());
            ps.setString(3, address.getNeighborhood());
            ps.setInt(4, address.getDistrictId());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating address", e);
        }
    }

    // Update an existing address
    public void update(AddressModel address, DBConnection connection) {
        String sql = "UPDATE address SET street = ?, house_number = ?, neighborhood = ?, district_id = ? WHERE address_id = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, address.getStreet());
            ps.setInt(2, address.getHouseNumber());
            ps.setString(3, address.getNeighborhood());
            ps.setInt(4, address.getDistrictId());
            ps.setInt(5, address.getAddressId());

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating address", e);
        }
    }

    // Delete an address by its ID
    public void delete(int addressId, DBConnection connection) {
        String sql = "DELETE FROM address WHERE address_id = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, addressId);

            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting address", e);
        }
    }

    // Fetch all addresses
    public List<AddressModel> getAll(DBConnection connection) {
        String sql = "SELECT * FROM address";
        List<AddressModel> addressList = new ArrayList<>();

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                AddressModel address = new AddressModel();
                address.setAddressId(resultSet.getInt("address_id"));
                address.setStreet(resultSet.getString("street"));
                address.setHouseNumber(resultSet.getInt("house_number"));
                address.setNeighborhood(resultSet.getString("neighborhood"));
                address.setDistrictId(resultSet.getInt("district_id"));
                address.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                addressList.add(address);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all addresses", e);
        }

        return addressList; // Return an empty list if no addresses are found
    }

    // Fetch a single address by its ID
    public AddressModel getAddressById(int addressId, DBConnection connection) {
        String sql = "SELECT * FROM address WHERE address_id = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, addressId);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    AddressModel address = new AddressModel();
                    address.setAddressId(resultSet.getInt("address_id"));
                    address.setStreet(resultSet.getString("street"));
                    address.setHouseNumber(resultSet.getInt("house_number"));
                    address.setNeighborhood(resultSet.getString("neighborhood"));
                    address.setDistrictId(resultSet.getInt("district_id"));
                    address.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

                    return address;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching address by ID", e);
        }

        return null; // Return null if address not found
    }
}
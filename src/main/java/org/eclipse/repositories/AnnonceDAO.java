package org.eclipse.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.Annonce;

public class AnnonceDAO implements GenericDAO<Annonce, Integer> {

    @Override
    public List<Annonce> findAll() {
        List<Annonce> annonces = new ArrayList<>();
        String sql = "SELECT * FROM annonce";

        try (Connection connection = MySqlConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                annonces.add(mapToAnnonce(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des annonces", e);
        }

        return annonces;
    }

    @Override
    public Annonce findById(Integer id) {
        String sql = "SELECT * FROM annonce WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToAnnonce(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'annonce ID=" + id, e);
        }
        return null;
    }

    @Override
    public Annonce save(Annonce annonce) {
        String sql = "INSERT INTO annonce (titre, description, prix, ville, idUtilisateur) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, annonce.getTitre());
            ps.setString(2, annonce.getDescription());
            ps.setDouble(3, annonce.getPrix());
            ps.setString(4, annonce.getVille());
            ps.setInt(5, annonce.getIdUtilisateur());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    annonce.setId(keys.getInt(1));
                }
            }

            return annonce;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'annonce", e);
        }
    }

    @Override
    public Annonce update(Annonce annonce) {
        String sql = "UPDATE annonce SET titre = ?, description = ?, prix = ?, ville = ?, idUtilisateur = ? WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, annonce.getTitre());
            ps.setString(2, annonce.getDescription());
            ps.setDouble(3, annonce.getPrix());
            ps.setString(4, annonce.getVille());
            ps.setInt(5, annonce.getIdUtilisateur());
            ps.setInt(6, annonce.getId());

            int updated = ps.executeUpdate();
            if (updated > 0) return annonce;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'annonce ID=" + annonce.getId(), e);
        }
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        String sql = "DELETE FROM annonce WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'annonce ID=" + id, e);
        }
    }

    private Annonce mapToAnnonce(ResultSet rs) throws SQLException {
        return new Annonce(
            rs.getInt("id"),
            rs.getString("titre"),
            rs.getString("description"),
            rs.getDouble("prix"),
            rs.getString("ville"),
            rs.getInt("idUtilisateur")
        );
    }
    
    
    public List<Annonce> findNotByUtilisateur(int idUtilisateur) {
        List<Annonce> annonces = new ArrayList<>();
        String sql = "SELECT * FROM annonce WHERE idUtilisateur <> ?";

        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    annonces.add(mapToAnnonce(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des annonces des autres utilisateurs (idUtilisateur=" + idUtilisateur + ")", e);
        }

        return annonces;
    }
    
    public List<Annonce> findByUtilisateur(int idUtilisateur) {
        List<Annonce> annonces = new ArrayList<>();
        String sql = "SELECT * FROM annonce WHERE idUtilisateur = ?";

        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    annonces.add(mapToAnnonce(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des annonces de l'utilisateur ID=" + idUtilisateur, e);
        }

        return annonces;
    }
    
    public boolean removeByUtilisateur(int idUtilisateur) {
        String sql = "DELETE FROM annonce WHERE idUtilisateur = ?";
        try (Connection cnx = MySqlConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

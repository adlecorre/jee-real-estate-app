package org.eclipse.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.Utilisateur;

public class UtilisateurDAO implements GenericDAO<Utilisateur, Integer> {

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String select = "SELECT * FROM utilisateur";

        try (Connection connection = MySqlConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {

            while (resultSet.next()) {
                utilisateurs.add(mapToUtilisateur(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }
        return utilisateurs;
    }

    @Override
    public Utilisateur findById(Integer id) {
        String select = "SELECT * FROM utilisateur WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(select)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapToUtilisateur(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur ID=" + id, e);
        }
        return null;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        String insert = "INSERT INTO utilisateur (nom, prenom, email, motDePasse) VALUES (?, ?, ?, ?)";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setString(3, utilisateur.getEmail());
            statement.setString(4, utilisateur.getMotDePasse());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de l’insertion, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilisateur.setId(generatedKeys.getInt(1));
                }
            }

            return utilisateur;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'utilisateur", e);
        }
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        String update = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, motDePasse = ? WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(update)) {

            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setString(3, utilisateur.getEmail());
            statement.setString(4, utilisateur.getMotDePasse());
            statement.setInt(5, utilisateur.getId());

            int affected = statement.executeUpdate();
            if (affected > 0) {
                return utilisateur;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur ID=" + utilisateur.getId(), e);
        }
        return null;
    }
    
    @Override
    public boolean remove(Integer id) {
        String delete = "DELETE FROM utilisateur WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(delete)) {

            statement.setInt(1, id);
            int result = statement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur ID=" + id, e);
        }
    }

    private Utilisateur mapToUtilisateur(ResultSet rs) throws SQLException {
        return new Utilisateur(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("motDePasse")
        );
    }
    
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToUtilisateur(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur avec l'email : " + email, e);
        }

        return null;
    }
    
    public Utilisateur findByEmailMotDePasse(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE email = ? AND motDePasse = ?";
        
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, email.trim());
            ps.setString(2, motDePasse.trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToUtilisateur(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur avec email et mot de passe", e);
        }

        return null;
    }

}

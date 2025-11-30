package org.eclipse.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.config.MySqlConnection;
import org.eclipse.models.Annonce;
import org.eclipse.models.Favori;

public class FavoriDAO implements GenericDAO<Favori, Integer> {

    @Override
    public List<Favori> findAll() {
        List<Favori> favoris = new ArrayList<>();
        String sql = "SELECT * FROM favori";

        try (Connection connection = MySqlConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                favoris.add(mapToFavori(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des favoris", e);
        }

        return favoris;
    }

    @Override
    public Favori findById(Integer id) {
        String sql = "SELECT * FROM favori WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapToFavori(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du favori ID=" + id, e);
        }
        return null;
    }

    @Override
    public Favori save(Favori favori) {
        String sql = "INSERT INTO favori (idUtilisateur, idAnnonce) VALUES (?, ?)";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, favori.getIdUtilisateur());
            ps.setInt(2, favori.getIdAnnonce());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    favori.setId(keys.getInt(1));
                }
            }

            return favori;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du favori", e);
        }
    }

    @Override
    public Favori update(Favori favori) {
        String sql = "UPDATE favori SET idUtilisateur = ?, idAnnonce = ? WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, favori.getIdUtilisateur());
            ps.setInt(2, favori.getIdAnnonce());
            ps.setInt(3, favori.getId());

            int updated = ps.executeUpdate();
            if (updated > 0) return favori;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du favori ID=" + favori.getId(), e);
        }
        return null;
    }

    @Override
    public boolean remove(Integer id) {
        String sql = "DELETE FROM favori WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du favori ID=" + id, e);
        }
    }


    private Favori mapToFavori(ResultSet rs) throws SQLException {
        return new Favori(
            rs.getInt("id"),
            rs.getInt("idUtilisateur"),
            rs.getInt("idAnnonce")
        );
    }
    
    public boolean exists(int idUtilisateur, int idAnnonce) {
        String sql = "SELECT * FROM favori WHERE idUtilisateur=? AND idAnnonce=?";
        try (Connection cnx = MySqlConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.setInt(2, idAnnonce);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(int idUtilisateur, int idAnnonce) {
        String sql = "INSERT INTO favori(idUtilisateur,idAnnonce) VALUES (?,?)";
        try (Connection cnx = MySqlConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.setInt(2, idAnnonce);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int idUtilisateur, int idAnnonce) {
        String sql = "DELETE FROM favori WHERE idUtilisateur=? AND idAnnonce=?";
        try (Connection cnx = MySqlConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.setInt(2, idAnnonce);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Annonce> findByUtilisateur(int idUtilisateur) {
        List<Annonce> favoris = new ArrayList<>();
        String sql = "SELECT a.* FROM annonce a " +
                     "JOIN favori f ON a.id = f.idAnnonce " +
                     "WHERE f.idUtilisateur = ?";

        try (Connection cnx = MySqlConnection.getConnection();
             PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Annonce a = new Annonce(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getString("ville"),
                        rs.getInt("idUtilisateur")
                    );
                    a.setFavori(true); 
                    favoris.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return favoris;
    }
    
    public boolean removeByUtilisateur(int idUtilisateur) {
        String sql = "DELETE FROM favori WHERE idUtilisateur = ?";
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

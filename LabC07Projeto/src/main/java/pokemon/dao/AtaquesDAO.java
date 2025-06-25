package pokemon.dao;

import pokemon.model.Ataques;
import pokemon.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtaquesDAO {

    // INSERT -------------------------------------------------------------------
    public boolean insertAtaque(Ataques ataque) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO Ataques (idAtaques, nome, forca) VALUES (?, ?, ?)")) {

            pst.setInt(1, ataque.getIdAtaques());
            pst.setString(2, ataque.getNome());
            pst.setInt(3, ataque.getForca());
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // UPDATE -------------------------------------------------------------------
    public boolean updateAtaque(int id, Ataques ataque) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE Ataques SET nome = ?, forca = ? WHERE idAtaques = ?")) {

            pst.setString(1, ataque.getNome());
            pst.setInt(2, ataque.getForca());
            pst.setInt(3, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // DELETE -------------------------------------------------------------------
    public boolean deleteAtaque(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM Ataques WHERE idAtaques = ?")) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // SELECT (todos) -------------------------------------------------------------------
    public List<Ataques> selectAtaques() {
        List<Ataques> ataques = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Ataques")) {

            while (rs.next()) {
                Ataques ataque = new Ataques();
                ataque.setIdAtaques(rs.getInt("idAtaques"));
                ataque.setNome(rs.getString("nome"));
                ataque.setForca(rs.getInt("forca"));
                ataques.add(ataque);
            }

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        }
        return ataques;
    }
    //-------------------------------------------------------------------

    //Join -------------------------------------------------------------------
    public boolean updateAtaque(int id, String novoNome, int novaForca) {
        try (Connection con = DatabaseConnection.getConnection()) {
            // Atualiza apenas os campos que foram modificados
            if (!novoNome.isEmpty() && novaForca > 0) {
                String sql = "UPDATE Ataques SET nome = ?, forca = ? WHERE idAtaques = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, novoNome);
                pst.setInt(2, novaForca);
                pst.setInt(3, id);
                return pst.executeUpdate() > 0;
            }
            else if (!novoNome.isEmpty()) {
                String sql = "UPDATE Ataques SET nome = ? WHERE idAtaques = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, novoNome);
                pst.setInt(2, id);
                return pst.executeUpdate() > 0;
            }
            else if (novaForca > 0) {
                String sql = "UPDATE Ataques SET forca = ? WHERE idAtaques = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, novaForca);
                pst.setInt(2, id);
                return pst.executeUpdate() > 0;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------
}
package pokemon.dao;

import pokemon.model.Pokedex;
import pokemon.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokedexDAO {

    // INSERT -------------------------------------------------------------------
    public boolean insertPokedex(Pokedex pokedex) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO Pokedex (idPokedex, numPkmRegistrados, descricao) VALUES (?, ?, ?)")) {

            pst.setInt(1, pokedex.getIdPokedex());
            pst.setInt(2, pokedex.getNumPkmRegistrados());
            pst.setString(3, pokedex.getDescricao());
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    // -------------------------------------------------------------------



    // UPDATE -------------------------------------------------------------------
    public boolean updatePokedex(int id, Pokedex pokedex) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE Pokedex SET numPkmRegistrados = ?, descricao = ? WHERE idPokedex = ?")) {

            pst.setInt(1, pokedex.getNumPkmRegistrados());
            pst.setString(2, pokedex.getDescricao());
            pst.setInt(3, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    // -------------------------------------------------------------------




    // DELETE
    public boolean deletePokedex(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM Pokedex WHERE idPokedex = ?")) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            return false;
        }
    }
    // -------------------------------------------------------------------



    // SELECT (todos) -------------------------------------------------------------------
    public List<Pokedex> selectPokedex() {
        List<Pokedex> pokedexes = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Pokedex")) {

            while (rs.next()) {
                Pokedex p = new Pokedex();
                p.setIdPokedex(rs.getInt("idPokedex"));
                p.setNumPkmRegistrados(rs.getInt("numPkmRegistrados"));
                p.setDescricao(rs.getString("descricao"));
                pokedexes.add(p);
            }

        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        }
        return pokedexes;
    }
    // -------------------------------------------------------------------


}
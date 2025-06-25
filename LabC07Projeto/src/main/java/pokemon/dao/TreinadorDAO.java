package pokemon.dao;

import pokemon.dao.join.TreinadorComPokemon;
import pokemon.model.Treinador;
import pokemon.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinadorDAO {

    // INSERT -------------------------------------------------------------------
    public boolean insertTreinador(Treinador treinador) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO Treinador (idTreinador, nome, qtdPokebolas, qtdPotion, Pokedex_idPokedex) VALUES (?, ?, ?, ?, ?)")) {

            pst.setInt(1, treinador.getIdTreinador());
            pst.setString(2, treinador.getNome());
            pst.setInt(3, treinador.getQtdPokebolas());
            pst.setInt(4, treinador.getQtdPotion());
            pst.setInt(5, treinador.getPokedexId());
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao inserir treinador: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // UPDATE -------------------------------------------------------------------
    public boolean updateTreinador(int id, Treinador treinador) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE Treinador SET nome = ?, qtdPokebolas = ?, qtdPotion = ?, Pokedex_idPokedex = ? WHERE idTreinador = ?")) {

            pst.setString(1, treinador.getNome());
            pst.setInt(2, treinador.getQtdPokebolas());
            pst.setInt(3, treinador.getQtdPotion());
            pst.setInt(4, treinador.getPokedexId());
            pst.setInt(5, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao atualizar treinador: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // DELETE -------------------------------------------------------------------
    public boolean deleteTreinador(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM Treinador WHERE idTreinador = ?")) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao deletar treinador: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // SELECT (todos os treinadores) -------------------------------------------------------------------
    public List<Treinador> selectAllTreinadores() {
        List<Treinador> treinadores = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Treinador")) {

            while (rs.next()) {
                Treinador t = new Treinador();
                t.setIdTreinador(rs.getInt("idTreinador"));
                t.setNome(rs.getString("nome"));
                t.setQtdPokebolas(rs.getInt("qtdPokebolas"));
                t.setQtdPotion(rs.getInt("qtdPotion"));
                t.setPokedexId(rs.getInt("Pokedex_idPokedex"));
                treinadores.add(t);
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar treinadores: " + exc.getMessage());
        }
        return treinadores;
    }
    //-------------------------------------------------------------------



    // SELECT por ID (bônus) -------------------------------------------------------------------
    public Treinador selectTreinadorById(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM Treinador WHERE idTreinador = ?")) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Treinador t = new Treinador();
                t.setIdTreinador(rs.getInt("idTreinador"));
                t.setNome(rs.getString("nome"));
                t.setQtdPokebolas(rs.getInt("qtdPokebolas"));
                t.setQtdPotion(rs.getInt("qtdPotion"));
                t.setPokedexId(rs.getInt("Pokedex_idPokedex"));
                return t;
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar treinador por ID: " + exc.getMessage());
        }
        return null;
    }
    //-------------------------------------------------------------------



    //Join -------------------------------------------------------------------
    public List<TreinadorComPokemon> listarTreinadoresComPokemons() {
        List<TreinadorComPokemon> resultados = new ArrayList<>();
        String sql = "SELECT t.idTreinador, t.nome as nomeTreinador, p.idPokemon, p.nome as nomePokemon " +
                "FROM Treinador t " +
                "LEFT JOIN Pokemon p ON t.idTreinador = p.Treinador_idTreinador";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultados.add(new TreinadorComPokemon(
                        rs.getInt("idTreinador"),
                        rs.getString("nomeTreinador"),
                        rs.getInt("idPokemon"),
                        rs.getString("nomePokemon")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro no JOIN: " + e.getMessage());
        }
        return resultados;
    }
    //-------------------------------------------------------------------
}
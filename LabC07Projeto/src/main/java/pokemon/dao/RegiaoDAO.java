package pokemon.dao;

import pokemon.dao.join.RegiaoComPokemon;
import pokemon.model.Regiao;
import pokemon.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegiaoDAO {

    //INSERT -------------------------------------------------------------------
    public boolean insertRegiao(Regiao regiao) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO Regiao (idRegiao, nome) VALUES (?, ?)")) {

            pst.setInt(1, regiao.getIdRegiao());
            pst.setString(2, regiao.getNome());
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao inserir região: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    //UPDATE -------------------------------------------------------------------
    public boolean updateRegiao(int id, Regiao regiao) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE Regiao SET nome = ? WHERE idRegiao = ?")) {

            pst.setString(1, regiao.getNome());
            pst.setInt(2, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao atualizar região: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    //DELETE -------------------------------------------------------------------
    public boolean deleteRegiao(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM Regiao WHERE idRegiao = ?")) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao deletar região: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------




    //SELECT (todas as regiões) -------------------------------------------------------------------
    public List<Regiao> selectAllRegioes() {
        List<Regiao> regioes = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Regiao")) {

            while (rs.next()) {
                Regiao r = new Regiao();
                r.setIdRegiao(rs.getInt("idRegiao"));
                r.setNome(rs.getString("nome"));
                regioes.add(r);
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar regiões: " + exc.getMessage());
        }
        return regioes;
    }
    //-------------------------------------------------------------------



    //SELECT por ID (bônus) -------------------------------------------------------------------
    public Regiao selectRegiaoById(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM Regiao WHERE idRegiao = ?")) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Regiao r = new Regiao();
                r.setIdRegiao(rs.getInt("idRegiao"));
                r.setNome(rs.getString("nome"));
                return r;
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar região por ID: " + exc.getMessage());
        }
        return null;
    }
    //-------------------------------------------------------------------



    //Join -------------------------------------------------------------------
    public List<RegiaoComPokemon> listarRegioesComPokemons() {
        List<RegiaoComPokemon> resultados = new ArrayList<>();
        String sql = "SELECT r.nome as nomeRegiao, p.nome as nomePokemon, p.idPokemon " +
                "FROM Regiao r " +
                "LEFT JOIN Pokemon p ON r.idRegiao = p.Regiao_idRegiao " +
                "ORDER BY r.nome";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultados.add(new RegiaoComPokemon(
                        rs.getString("nomeRegiao"),
                        rs.getString("nomePokemon"),
                        rs.getInt("idPokemon")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro no JOIN: " + e.getMessage());
        }
        return resultados;
    }
    //-------------------------------------------------------------------
}
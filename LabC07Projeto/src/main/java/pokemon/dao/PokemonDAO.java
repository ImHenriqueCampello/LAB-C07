package pokemon.dao;

import pokemon.dao.join.PokemonComTipo;
import pokemon.model.Pokemon;
import pokemon.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PokemonDAO {

    // INSERT -------------------------------------------------------------------
    public boolean insertPokemon(Pokemon pokemon) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO Pokemon (idPokemon, nome, peso, altura, nature, stats, Treinador_idTreinador, Regiao_idRegiao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setInt(1, pokemon.getIdPokemon());
            pst.setString(2, pokemon.getNome());
            pst.setInt(3, pokemon.getPeso());
            pst.setInt(4, pokemon.getAltura());
            pst.setString(5, pokemon.getNature());
            pst.setInt(6, pokemon.getStats());
            pst.setInt(7, pokemon.getTreinadorId());
            pst.setInt(8, pokemon.getRegiaoId());
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao inserir Pokémon: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------




    // UPDATE -------------------------------------------------------------------
    public boolean updatePokemon(int id, Pokemon pokemon) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "UPDATE Pokemon SET nome = ?, peso = ?, altura = ?, nature = ?, stats = ?, Treinador_idTreinador = ?, Regiao_idRegiao = ? WHERE idPokemon = ?")) {

            pst.setString(1, pokemon.getNome());
            pst.setInt(2, pokemon.getPeso());
            pst.setInt(3, pokemon.getAltura());
            pst.setString(4, pokemon.getNature());
            pst.setInt(5, pokemon.getStats());
            pst.setInt(6, pokemon.getTreinadorId());
            pst.setInt(7, pokemon.getRegiaoId());
            pst.setInt(8, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao atualizar Pokémon: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------




    // DELETE -------------------------------------------------------------------
    public boolean deletePokemon(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM Pokemon WHERE idPokemon = ?")) {

            pst.setInt(1, id);
            pst.execute();
            return true;

        } catch (SQLException exc) {
            System.out.println("Erro ao deletar Pokémon: " + exc.getMessage());
            return false;
        }
    }
    //-------------------------------------------------------------------



    // SELECT (todos) -------------------------------------------------------------------
    public List<Pokemon> selectAllPokemon() {
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Pokemon")) {

            while (rs.next()) {
                Pokemon p = new Pokemon();
                p.setIdPokemon(rs.getInt("idPokemon"));
                p.setNome(rs.getString("nome"));
                p.setPeso(rs.getInt("peso"));
                p.setAltura(rs.getInt("altura"));
                p.setNature(rs.getString("nature"));
                p.setStats(rs.getInt("stats"));
                p.setTreinadorId(rs.getInt("Treinador_idTreinador"));
                p.setRegiaoId(rs.getInt("Regiao_idRegiao"));
                pokemons.add(p);
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar Pokémon: " + exc.getMessage());
        }
        return pokemons;
    }
    //-------------------------------------------------------------------



    // SELECT por ID -------------------------------------------------------------------
    public Pokemon selectPokemonById(int id) {
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM Pokemon WHERE idPokemon = ?")) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Pokemon p = new Pokemon();
                p.setIdPokemon(rs.getInt("idPokemon"));
                p.setNome(rs.getString("nome"));
                p.setPeso(rs.getInt("peso"));
                p.setAltura(rs.getInt("altura"));
                p.setNature(rs.getString("nature"));
                p.setStats(rs.getInt("stats"));
                p.setTreinadorId(rs.getInt("Treinador_idTreinador"));
                p.setRegiaoId(rs.getInt("Regiao_idRegiao"));
                return p;
            }

        } catch (SQLException exc) {
            System.out.println("Erro ao buscar Pokémon por ID: " + exc.getMessage());
        }
        return null;
    }
    //-------------------------------------------------------------------



    //JOIN -------------------------------------------------------------------
    public List<PokemonComTipo> listarPokemonsETipos() {
        List<PokemonComTipo> resultados = new ArrayList<>();
        String sql = "SELECT p.idPokemon, p.nome, t.nomeTipo " +
                "FROM Pokemon p " +
                "JOIN Pokemon_has_Tipos pt ON p.idPokemon = pt.Pokemon_idPokemon " +
                "JOIN Tipos t ON pt.TipoPokemon_idTipoPokemon = t.idTipoPokemon";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                resultados.add(new PokemonComTipo(
                        rs.getInt("idPokemon"),
                        rs.getString("nome"),
                        rs.getString("nomeTipo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro no JOIN: " + e.getMessage());
        }
        return resultados;
    }
    //-------------------------------------------------------------------
}
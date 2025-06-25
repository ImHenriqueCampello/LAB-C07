package pokemon.dao.join;

public class TreinadorComPokemon {
    //-------------------------------------------------------------------
    private int idTreinador;
    private String nomeTreinador;
    private int idPokemon;
    private String nomePokemon;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public TreinadorComPokemon(int idTreinador, String nomeTreinador, int idPokemon, String nomePokemon) {
        this.idTreinador = idTreinador;
        this.nomeTreinador = nomeTreinador;
        this.idPokemon = idPokemon;
        this.nomePokemon = nomePokemon;
    }
    //-------------------------------------------------------------------



    // Getters -------------------------------------------------------------------
    public int getIdTreinador() { return idTreinador; }
    public String getNomeTreinador() { return nomeTreinador; }
    public int getIdPokemon() { return idPokemon; }
    public String getNomePokemon() { return nomePokemon; }
    //-------------------------------------------------------------------
}

package pokemon.dao.join;


public class PokemonComTipo {

    //-------------------------------------------------------------------
    private int idPokemon;
    private String nomePokemon;
    private String nomeTipo;
    //-------------------------------------------------------------------


    // Construtor -------------------------------------------------------------------
    public PokemonComTipo(int idPokemon, String nomePokemon, String nomeTipo) {
        this.idPokemon = idPokemon;
        this.nomePokemon = nomePokemon;
        this.nomeTipo = nomeTipo;
    }
    //-------------------------------------------------------------------


    //-------------------------------------------------------------------
    public int getIdPokemon() { return idPokemon; }
    public String getNomePokemon() { return nomePokemon; }
    public String getNomeTipo() { return nomeTipo; }
    //-------------------------------------------------------------------

}
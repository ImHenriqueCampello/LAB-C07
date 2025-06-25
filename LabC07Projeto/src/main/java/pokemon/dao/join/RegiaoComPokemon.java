package pokemon.dao.join;

public class RegiaoComPokemon {
    //-------------------------------------------------------------------
    private String nomeRegiao;
    private String nomePokemon;
    private int idPokemon;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public RegiaoComPokemon(String nomeRegiao, String nomePokemon, int idPokemon) {
        this.nomeRegiao = nomeRegiao;
        this.nomePokemon = nomePokemon;
        this.idPokemon = idPokemon;
    }
    //-------------------------------------------------------------------



    // Getters -------------------------------------------------------------------
    public String getNomeRegiao() { return nomeRegiao; }
    public String getNomePokemon() { return nomePokemon; }
    public int getIdPokemon() { return idPokemon; }
    //-------------------------------------------------------------------
}

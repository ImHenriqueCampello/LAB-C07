package pokemon.model;

public class Treinador{
    //-------------------------------------------------------------------
    private int idTreinador;
    private String nome;
    private int qtdPokebolas;
    private int qtdPotion;
    private int pokedexId;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public int getIdTreinador() { return idTreinador; }
    public void setIdTreinador(int idTreinador) { this.idTreinador = idTreinador; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQtdPokebolas() { return qtdPokebolas; }
    public void setQtdPokebolas(int qtdPokebolas) { this.qtdPokebolas = qtdPokebolas; }
    public int getQtdPotion() { return qtdPotion; }
    public void setQtdPotion(int qtdPotion) { this.qtdPotion = qtdPotion; }
    public int getPokedexId() { return pokedexId; }
    public void setPokedexId(int pokedexId) { this.pokedexId = pokedexId; }
    //-------------------------------------------------------------------
}
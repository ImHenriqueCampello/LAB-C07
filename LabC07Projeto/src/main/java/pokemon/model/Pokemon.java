package pokemon.model;

public class Pokemon {
    //-------------------------------------------------------------------
    private int idPokemon;
    private String nome;
    private int peso;
    private int altura;
    private String nature;
    private int stats;
    private int treinadorId;
    private int regiaoId;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public int getIdPokemon() { return idPokemon; }
    public void setIdPokemon(int idPokemon) { this.idPokemon = idPokemon; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }
    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }
    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }
    public int getStats() { return stats; }
    public void setStats(int stats) { this.stats = stats; }
    public int getTreinadorId() { return treinadorId; }
    public void setTreinadorId(int treinadorId) { this.treinadorId = treinadorId; }
    public int getRegiaoId() { return regiaoId; }
    public void setRegiaoId(int regiaoId) { this.regiaoId = regiaoId; }
    //-------------------------------------------------------------------
}

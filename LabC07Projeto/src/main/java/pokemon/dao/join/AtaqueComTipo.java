package pokemon.dao.join;

public class AtaqueComTipo {
    //-------------------------------------------------------------------
    private String nomeAtaque;
    private int forca;
    private String nomeTipo;
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public AtaqueComTipo(String nomeAtaque, int forca, String nomeTipo) {
        this.nomeAtaque = nomeAtaque;
        this.forca = forca;
        this.nomeTipo = nomeTipo;
    }
    //-------------------------------------------------------------------



    // Getters -------------------------------------------------------------------
    public String getNomeAtaque() { return nomeAtaque; }
    public int getForca() { return forca; }
    public String getNomeTipo() { return nomeTipo; }
    //-------------------------------------------------------------------
}

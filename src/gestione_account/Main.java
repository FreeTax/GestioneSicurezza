package gestione_account;
import java.time.LocalDate;
/*import luoghi.Dipartimento;
import visite.schedaVisite;*/
import accessi.Accesso;

public abstract class Utente{
    public Utente(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita) {
        this.codice = codice;
        this.cfu_sostenuti = cfu_sostenuti;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dipartimento = dipartimento;
        this.dataNascita = dataNascita;
    }

    public CreditoFormativo getCfu_sostenuti() {
        return cfu_sostenuti;
    }

    public void setCfu_sostenuti(CreditoFormativo cfu_sostenuti) {
        this.cfu_sostenuti = cfu_sostenuti;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    private int codice;
    private CreditoFormativo cfu_sostenuti;
    private String nome,cognome,sesso,dipartimento;
    private LocalDate dataNascita;
}

class UtenteEsterno extends Utente{
    public UtenteEsterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int idEsterno) {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita);
        this.idEsterno = idEsterno;
    }

    public int getIdEsterno() {
        return idEsterno;
    }

    private int idEsterno;
}

class UtenteInterno extends Utente{
    public UtenteInterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int matricola, int type) {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita);
        this.matricola = matricola;
        this.type = type;
    }

    public int getMatricola() {
        return matricola;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int matricola,type;
}

class CreditoFormativo{
    public CreditoFormativo(int codice, int rischio, String certificaEsterna) {
        this.codice = codice;
        this.rischio = rischio;
        this.certificaEsterna = certificaEsterna;
    }

    private int codice,rischio;
    private String certificaEsterna;
}

abstract class Richiesta{
    Richiesta(int utente, int statoRichiesta) {
        this.utente = utente;
        this.statoRichiesta = statoRichiesta;
    }
    private int utente, statoRichiesta;
}

class RichiestaLuogo extends Richiesta{
    public RichiestaLuogo(int utente, int statoRichiesta, String luogo) {
        super(utente, statoRichiesta);
        idLuogo = luogo;
    }

    private String idLuogo;
}

class RichiestaDipartimento extends Richiesta{
    public RichiestaDipartimento(int utente, int statoRichiesta, String idDipartimento) {
        super(utente, statoRichiesta);
        this.idDipartimento = idDipartimento;
    }

    public void setIdDipartimento(String idDipartimento) {
        this.idDipartimento = idDipartimento;
    }

    private String idDipartimento;
}
public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

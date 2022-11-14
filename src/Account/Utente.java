package Account;

import java.time.LocalDate;

import Rischi.Rischio;
import Visite.SchedaVisita;
import Luoghi.Luogo;
import Luoghi.Dipartimento;

public abstract class Utente{

    private int codice;
    private CreditoFormativo cfuSostenuti;
    private String nome,cognome,sesso,dipartimento;
    private LocalDate dataNascita;
    private SchedaVisita visite;

    public Utente(int codice, CreditoFormativo cfuSstenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, SchedaVisita visite) {
        this.codice = codice;
        this.cfuSostenuti = cfuSostenuti;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dipartimento = dipartimento;
        this.dataNascita = dataNascita;
        this.visite=visite;
    }

    public CreditoFormativo getCfu_sostenuti() {
        return cfuSostenuti;
    }

    public void setCfu_sostenuti(CreditoFormativo cfu_sostenuti) {
        this.cfuSostenuti = cfuSostenuti;
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
}

class UtenteEsterno extends Utente{
    public UtenteEsterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int idEsterno, SchedaVisita visite) {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita,visite);
        this.idEsterno = idEsterno;
    }

    public int getIdEsterno() {
        return idEsterno;
    }

    private int idEsterno;
}

class UtenteInterno extends Utente{
    public UtenteInterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int matricola, int type, SchedaVisita visite) {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita, visite);
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
    public CreditoFormativo(int codice, Rischio rischio, String certificaEsterna) {
        this.codice = codice;
        this.rischio = rischio;
        this.certificaEsterna = certificaEsterna;
    }

    private int codice;
    private Rischio rischio;
    private String certificaEsterna;
}

abstract class Richiesta{
    Richiesta(Utente utente, int statoRichiesta) {
        this.utente = utente;
        this.statoRichiesta = statoRichiesta;
    }
    private Utente utente;
    private int statoRichiesta;
}

class RichiestaLuogo extends Richiesta{
    public RichiestaLuogo(Utente utente, int statoRichiesta, Luogo luogo) {
        super(utente, statoRichiesta);
        Luogo = luogo;
    }

    private Luogo Luogo;
}

class RichiestaDipartimento extends Richiesta{
    public RichiestaDipartimento(Utente utente, int statoRichiesta, Dipartimento dipartimento) {
        super(utente, statoRichiesta);
        this.dipartimento = dipartimento;
    }

    public void setIdDipartimento(String idDipartimento) {
        this.dipartimento = dipartimento;
    }

    private Dipartimento dipartimento;
}

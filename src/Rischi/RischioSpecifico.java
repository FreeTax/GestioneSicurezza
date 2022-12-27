package Rischi;

import CorsiSicurezza.Corso;
import Visite.Visita;

class RischioSpecifico extends Rischio {
    public RischioSpecifico(String codice, String nome, String descrizione, String tipologia, Corso corso, Visita visita) {
        super(codice, nome, descrizione, tipologia, corso, visita);
    }
}

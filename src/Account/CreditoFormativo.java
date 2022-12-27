package Account;

import Rischi.Rischio;

public class CreditoFormativo {
    private int codice;
    private Rischio rischio;
    private String certificaEsterna;

    public CreditoFormativo(int codice, /*Rischio rischio,*/ String certificaEsterna) {
        this.codice = codice;
        this.rischio = rischio;
        this.certificaEsterna = certificaEsterna;
    }
}

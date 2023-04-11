package AsyncIPCEventBus;

import Account.*;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;
import Visite.Visita;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class GatewayUtente {
    private Utente u;
    private CreditoFormativo cf;

    private EventBusService eventBusService;
    private Publisher pub;

    //Executor deleyed=CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS);


    public GatewayUtente(EventBusService eventBusService) {
        this.eventBusService = eventBusService;
        this.pub = new PublisherConcr();
    }

    public static boolean checkSupervisore(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if (u.getType() == null) {
            return false;
        } else {
            return u.getType().equals("supervisore");
        }
    }

    public static boolean checkAvanzato(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if (u.getType() == null) {
            return false;
        } else {
            return u.getType().equals("avanzato");
        }
    }

    public void insertUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                Date date = Date.valueOf(datanascita);
                u = new UtenteInterno(matricola, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
                pub.publish(new Message("Account", "insertUtente", u), eventBusService);
                System.out.println("Utente" + matricola + "creato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    public void insertUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                Date date = Date.valueOf(datanascita);
                u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
                pub.publish(new Message("Account", "insertUtente", u), eventBusService);
                System.out.println("Utente" + idEsterno + "creato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    public void insertCreditoFormativo(int codice, int idRischio) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                cf = new CreditoFormativo(codice, idRischio, "");
                pub.publish(new Message("Account", "insertCreditoFormativo", cf), eventBusService);
                System.out.println("Credito formativo creato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    /*login utente interno e esterno*/
    public CompletableFuture<Boolean> loginInterno(int matricola, String password) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                //UtenteInterno u = new UtenteInterno(matricola);

                SubscriberConcr subscriber = new SubscriberConcr("loginInterno" + matricola, eventBusService);
                pub.publish(new Message("Account", "loginInternoReq", null, Arrays.asList(matricola,password), "loginInterno" + matricola), eventBusService);

                CompletableFuture<Boolean> login = CompletableFuture
                        .supplyAsync(() -> (Boolean) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(false, 3, TimeUnit.SECONDS);
                Boolean res = login.join();

                subscriber.unSubscribe("loginInterno" + matricola, eventBusService);

                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public CompletableFuture<Boolean> loginEsterno(int idEsterno, String password) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                //UtenteEsterno u = new UtenteEsterno(idEsterno);
                SubscriberConcr subscriber = new SubscriberConcr("loginEsterno" + idEsterno, eventBusService);
                pub.publish(new Message("Account", "loginEsternoReq", null, Arrays.asList(idEsterno,password), "loginEsterno" + idEsterno), eventBusService);

                CompletableFuture<Boolean> login = CompletableFuture
                        .supplyAsync(() -> (Boolean) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(false, 3, TimeUnit.SECONDS);
                Boolean res = login.join();

                subscriber.unSubscribe("loginInterno" + idEsterno, eventBusService);
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });

    }

    /* inserimento richiesta accesso a luogo e dipartimento*/
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                RichiestaLuogo rl = new RichiestaLuogo(idUtente, 0, idLuogo);
                pub.publish(new Message("Account", "insertRichiestaLuogo", rl), eventBusService);
                System.out.println("Richiesta utente" + idUtente + "luogo creato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    public void insertRichiestaDipartimento(int idUtente, int idDipartimento) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                RichiestaDipartimento rd = new RichiestaDipartimento(idUtente, 0, idDipartimento);
                pub.publish(new Message("Account", "insertRichiestaDipartimento", rd), eventBusService);
                System.out.println("Richiesta utente" + idUtente + "dipartimento creato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    /* aggiorna dati utente interno e esterno*/
    public void aggiornaUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                Date date = Date.valueOf(datanascita);
                UtenteInterno u = new UtenteInterno(1, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
                pub.publish(new Message("Account", "updateUtenteInterno", u), eventBusService);
                System.out.println("Utente" + matricola + "aggiornato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    public void aggiornaUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                Date date = Date.valueOf(datanascita);
                UtenteEsterno u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
                pub.publish(new Message("Account", "updateUtenteEsterno", u), eventBusService);
                System.out.println("Utente" + idEsterno + "aggiornato");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    /* caricamento certificazione*/
    public void sostieniCredito(int idUtente, int codice, String certificazione) throws SQLException {
        CompletableFuture.runAsync(()-> {
            try {
                //Utente u = new UtenteInterno();//FIXME not good
                List<Object> param = Arrays.asList(idUtente, codice, certificazione);
                pub.publish(new Message("Account", "sostieniCredito", u, param), eventBusService);
                System.out.println("Utente" + idUtente + " ha sostenuto il credito");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).join();
    }

    public CompletableFuture<ArrayList<String>> getCFUSostenuti(int idAutorizzato, int idUtente) throws SQLException {
        //Utente u = new UtenteInterno();//FIXME not good
        return CompletableFuture.supplyAsync(()-> {
            try {
                if (checkSupervisore(idAutorizzato) || checkAvanzato(idAutorizzato)) {
                    SubscriberConcr subscriber = new SubscriberConcr("CFUsostenuti" + idUtente, eventBusService);
                    pub.publish(new Message("Account", "getCfuSostenuti", null, Arrays.asList(idUtente), "CFUsostenuti" + idUtente), eventBusService);

                    CompletableFuture<ArrayList<CreditoFormativo>> getCFU = CompletableFuture
                            .supplyAsync(() -> (ArrayList<CreditoFormativo>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
                            .completeOnTimeout(new ArrayList<>(), 3, TimeUnit.SECONDS);
                    ArrayList<CreditoFormativo> cfus = getCFU.join();

                    subscriber.unSubscribe("CFUsostenuti", eventBusService);
                    ArrayList<String> cfuSostenuti = new ArrayList<>();

                    for (CreditoFormativo cfu : cfus) {
                        String nomeRischio;
                        if (new RischioGenerico((cfu.getIdRischio())).getNome() != null) {
                            nomeRischio = new RischioGenerico((cfu.getIdRischio())).getNome();
                        } else {
                            nomeRischio = new RischioSpecifico((cfu.getIdRischio())).getNome();
                        }
                        cfuSostenuti.add(nomeRischio);
                    }

                    return cfuSostenuti;
                } else {
                    throw new RuntimeException("Utente non autorizzato");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /* get richieste luogo e dipartimento*/
    public CompletableFuture<ArrayList<String>> getRichiesteLuogo(int idUtente) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                if (checkSupervisore(idUtente)) {
                    //ArrayList<RichiestaLuogo> richiesteLuogo = new UtenteInterno(idUtente).getRichiesteLuogoUtenti();
                    SubscriberConcr subscriber = new SubscriberConcr("richiesteLuogoUtenti", eventBusService);
                    pub.publish(new Message("Account", "getRichiesteLuogoUtenti", null, Arrays.asList(idUtente), "richiesteLuogoUtenti"), eventBusService);

                    CompletableFuture<ArrayList<RichiestaLuogo>> getRichiesteLuogo = CompletableFuture
                            .supplyAsync(() -> (ArrayList<RichiestaLuogo>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                            .completeOnTimeout(new ArrayList<>(), 2, TimeUnit.SECONDS);

                    ArrayList<RichiestaLuogo> richiesteLuogo = getRichiesteLuogo.join();
                    ArrayList<String> richiesteLuogoString = new ArrayList<>();

                    subscriber.unSubscribe("richiesteLuogoUtenti", eventBusService);
                    richiesteLuogo.forEach(rl -> richiesteLuogoString.add(rl.toString()));
                    return richiesteLuogoString;
                } else {
                    throw new RuntimeException("Utente non autorizzato");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<ArrayList<String>> getRichiesteDipartimento(int idUtente) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                if (checkAvanzato(idUtente)) {
                    //ArrayList<RichiestaDipartimento> richiesteDipartimento = new UtenteInterno(idUtente).getRichiesteDipartimentoUtenti();
                    SubscriberConcr subscriber = new SubscriberConcr("richiesteDipartimentoUtenti", eventBusService);
                    pub.publish(new Message("Account", "getRichiesteDipartimentoUtenti", null, Arrays.asList(idUtente), "richiesteDipartimentoUtenti"), eventBusService);

                    CompletableFuture<ArrayList<RichiestaDipartimento>> getRichiesteDipartimento = CompletableFuture
                            .supplyAsync(() -> (ArrayList<RichiestaDipartimento>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                            .completeOnTimeout(new ArrayList<>(), 2, TimeUnit.SECONDS);

                    ArrayList<RichiestaDipartimento> richiesteDipartimento = getRichiesteDipartimento.join();
                    ArrayList<String> richiesteDipartimentoString = new ArrayList<>();

                    subscriber.unSubscribe("richiesteDipartimentoUtenti", eventBusService);
                    richiesteDipartimento.forEach(rd -> richiesteDipartimentoString.add(rd.toString()));
                    return richiesteDipartimentoString;
                } else {
                    throw new RuntimeException("Utente non autorizzato");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<ArrayList<String>> getCFUdaSostenere(int idUtente) throws SQLException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ArrayList<Integer> rischi;
                SubscriberConcr subscriber = new SubscriberConcr("CFUdaSostenere" + idUtente, eventBusService);
                pub.publish(new Message("Account", "getCFUdaSostenere", null, Arrays.asList(idUtente), "CFUdaSostenere" + idUtente), eventBusService);
                CompletableFuture<ArrayList<Integer>> getRischi = CompletableFuture
                        .supplyAsync(() -> (ArrayList<Integer>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
                        .completeOnTimeout(new ArrayList<>(), 3, TimeUnit.SECONDS);
                rischi = getRischi.join();
                System.out.println("dimensioni richi: " + rischi.size());
                ArrayList<String> rischiString = new ArrayList<>();

                subscriber.unSubscribe("CFUdaSostenere" + idUtente, eventBusService);
                for (Integer rischio : rischi) {
                    String nomeRischio;
                    if (new RischioGenerico(rischio).getNome() != null) {
                        nomeRischio = new RischioGenerico(rischio).getNome();
                    } else {
                        nomeRischio = new RischioSpecifico(rischio).getNome();
                    }
                    rischiString.add(nomeRischio);
                }
                return rischiString;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<ArrayList<String>> getDashboard(int idAutorizzato) throws SQLException {
        return CompletableFuture.supplyAsync(()->{
            try {
                GatewayVisite vGateway = new GatewayVisite(eventBusService);
                GatewayAccessi aGateway = new GatewayAccessi(eventBusService);

                ArrayList<String> dashboard = new ArrayList<>();

                for (String utente : new UtenteInterno().getUtenti()) {
                    String[] utenteSplit = utente.split(" ");
                    int idUtente = Integer.parseInt(utenteSplit[0]);

                    ArrayList<String> cfuDaSostenere = getCFUdaSostenere(idUtente).join();

                    ArrayList<String> cfuSostenuti = getCFUSostenuti(idAutorizzato, idUtente).join();

                    ArrayList<Visita> visiteSostenute = vGateway.getVisiteSostenute(idUtente).join();

                    ArrayList<Visita> visiteDaSostenere = vGateway.getVisiteDaSostentere(idUtente).join();

                    ArrayList<Integer> luoghiFrequentati = aGateway.getLuoghiFrequentati(idUtente).join();

                    ArrayList<Integer> dipartimentiFrequentati = aGateway.getDipartimentiFrequentati(idUtente).join();

                    utente += " CFU da sostenere: ";

                    for (String cfu : cfuDaSostenere) {
                        utente += cfu + ", ";
                    }

                    utente += " CFU sostenuti: ";

                    for (String cfu : cfuSostenuti) {
                        utente += cfu + ", ";
                    }

                    utente += " Visite da sostenere: ";

                    for (Visita v : visiteDaSostenere) {
                        utente += v.getDescrizione() + ",";
                    }

                    utente += " Visite sostenute: ";

                    for (Visita v : visiteSostenute) {
                        utente += v.getDescrizione() + ",";
                    }

                    utente += "Luoghi frequentati: ";

                    for (int i : luoghiFrequentati) {
                        utente += new Luogo(i).getNome() + ",";
                    }

                    utente += "Dipartimenti frequentati: ";

                    for (int i : dipartimentiFrequentati) {
                        utente += new Dipartimento(i).getNome() + ",";
                    }
                    dashboard.add(utente);
                }

                return dashboard;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        });

    }
}

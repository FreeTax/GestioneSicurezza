DROP TABLE IF EXISTS Utente;

CREATE TABLE IF NOT EXISTS Utente(
    matricola int PRIMARY KEY AUTO_INCREMENT, nome varchar(30),
    cognome varchar(30)
);


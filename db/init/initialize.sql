CREATE DATABASE IF NOT EXISTS AccountDB;
USE AccountDB;
DROP TABLE IF EXISTS Utente;

CREATE TABLE IF NOT EXISTS Utente (
  idUtente INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  cognome VARCHAR(45) NOT NULL,
  sesso VARCHAR(45) NOT NULL,
  datanascita DATE NOT NULL,
  dipartimento VARCHAR(45) NOT NULL,
  tipologia ENUM('interno', 'esterno') NOT NULL
);

DROP TABLE IF EXISTS UtenteInterno;

CREATE TABLE IF NOT EXISTS UtenteInterno (
  idUtente INT NOT NULL,
  matricola INT NOT NULL,
  tipo ENUM('base', 'supervisore', 'avanzato') NOT NULL,
  PRIMARY KEY (matricola,idUtente),
  CONSTRAINT fk_UtenteInterno_Utente
    FOREIGN KEY (idUtente)
    REFERENCES Utente (idUtente)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS UtenteEsterno;

CREATE TABLE IF NOT EXISTS UtenteEsterno (
  idUtente INT NOT NULL,
  idEsterno INT NOT NULL,
  tipo ENUM('base', 'supervisore', 'avanzato') NOT NULL,
  PRIMARY KEY (idEsterno,idUtente),
  CONSTRAINT fk_UtenteEsterno_Utente
    FOREIGN KEY (idUtente)
    REFERENCES Utente (idUtente)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

DROP TABLE IF EXISTS CreditoFormativo;

CREATE TABLE IF NOT EXISTS CreditoFormativo (
  idCreditoFormativo INT PRIMARY KEY AUTO_INCREMENT,
  idRischio INT NOT NULL,
  CertificazioneEsterna VARCHAR(45) NULL
);

DROP TABLE IF EXISTS CreditoFormativoSostenuto;

CREATE TABLE IF NOT EXISTS CreditoFormativoSostenuto (
  idCreditoFormativo INT NOT NULL,
  idUtente INT NOT NULL,
  PRIMARY KEY (idCreditoFormativo, idUtente),
  INDEX fk_CreditoFormativo_has_Utente_Utente1_idx (idUtente ASC) VISIBLE,
  INDEX fk_CreditoFormativo_has_Utente_CreditoFormativo1_idx (idCreditoFormativo ASC) VISIBLE,
  CONSTRAINT fk_CreditoFormativo_has_Utente_CreditoFormativo1
    FOREIGN KEY (idCreditoFormativo)
    REFERENCES CreditoFormativo (idCreditoFormativo)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_CreditoFormativo_has_Utente_Utente1
    FOREIGN KEY (idUtente)
    REFERENCES Utente (idUtente)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS Richiesta;

CREATE TABLE IF NOT EXISTS Richiesta (
  idRichiesta INT PRIMARY KEY AUTO_INCREMENT,
  stato VARCHAR(45) NOT NULL,
  idUtente INT NOT NULL,
  tipo ENUM('luogo', 'dipartimento') NOT NULL,
  INDEX fk_Richiesta_Utente1_idx (idUtente ASC) VISIBLE,
  CONSTRAINT fk_Richiesta_Utente1
    FOREIGN KEY (idUtente)
    REFERENCES Utente (idUtente)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS RichiestaLuogo;

CREATE TABLE IF NOT EXISTS RichiestaLuogo (
  idRichiesta INT NOT NULL,
  idLuogo VARCHAR(45) NOT NULL,
  PRIMARY KEY (idRichiesta),
  CONSTRAINT fk_RichiestaLuogo_Richiesta1
    FOREIGN KEY (idRichiesta)
    REFERENCES Richiesta (idRichiesta)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
DROP TABLE IF EXISTS RichiestaDipartimento;

CREATE TABLE IF NOT EXISTS RichiestaDipartimento (
  idRichiesta INT NOT NULL,
  idDipartimento VARCHAR(45) NOT NULL,
  PRIMARY KEY (idRichiesta),
  CONSTRAINT fk_RichiestaDipartimento_Richiesta1
    FOREIGN KEY (idRichiesta)
    REFERENCES Richiesta (idRichiesta)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE DATABASE IF NOT EXISTS VisiteDB;
USE VisiteDB;
DROP TABLE IF EXISTS SchedaVisita;

CREATE TABLE IF NOT EXISTS SchedaVisita (
  idSchedaVisita INT NOT NULL,
  idUtente VARCHAR(45) NOT NULL,
  PRIMARY KEY (idSchedaVisita)
);

DROP TABLE IF EXISTS VisitaType;

CREATE TABLE IF NOT EXISTS VisitaType (
  idVisitaType INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  descrizione VARCHAR(45) NOT NULL,
  frequenza VARCHAR(45) NOT NULL,
  PRIMARY KEY (idVisitaType)
);

DROP TABLE IF EXISTS Visita;

CREATE TABLE IF NOT EXISTS Visita (
  idVisita INT NOT NULL,
  idMedico VARCHAR(45) NOT NULL,
  descrizione VARCHAR(45) NULL,
  data DATETIME NOT NULL,
  stato ENUM('effettuata', 'non effettuata') NOT NULL,
  esito VARCHAR(45) NOT NULL,
  idSchedaVisita INT NOT NULL,
  idVisitaType INT NOT NULL,
  PRIMARY KEY (idVisita),
  INDEX fk_Visita_SchedaVisita1_idx (idSchedaVisita ASC) VISIBLE,
  INDEX fk_Visita_VisitaType1_idx (idVisitaType ASC) VISIBLE,
  CONSTRAINT fk_Visita_SchedaVisita1
    FOREIGN KEY (idSchedaVisita)
    REFERENCES SchedaVisita (idSchedaVisita)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Visita_VisitaType1
    FOREIGN KEY (idVisitaType)
    REFERENCES VisitaType (idVisitaType)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS Patologia;

CREATE TABLE IF NOT EXISTS Patologia (
  idPatologia INT NOT NULL,
  descrizione VARCHAR(45) NOT NULL,
  PRIMARY KEY (idPatologia)
);

DROP TABLE IF EXISTS SchedaVisita_has_Patologia;

CREATE TABLE IF NOT EXISTS SchedaVisita_has_Patologia (
  idSchedaVisita INT NOT NULL,
  idPatologia INT NOT NULL,
  PRIMARY KEY (idSchedaVisita, idPatologia),
  INDEX fk_SchedaVisita_has_Patologia_Patologia1_idx (idPatologia ASC) VISIBLE,
  INDEX fk_SchedaVisita_has_Patologia_SchedaVisita_idx (idSchedaVisita ASC) VISIBLE,
  CONSTRAINT fk_SchedaVisita_has_Patologia_SchedaVisita
    FOREIGN KEY (idSchedaVisita)
    REFERENCES SchedaVisita (idSchedaVisita)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_SchedaVisita_has_Patologia_Patologia1
    FOREIGN KEY (idPatologia)
    REFERENCES Patologia (idPatologia)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE DATABASE IF NOT EXISTS CorsiSicurezzaDB;
USE CorsiSicurezzaDB;

DROP TABLE IF EXISTS CorsoType;

CREATE TABLE IF NOT EXISTS CorsoType (
  idCorsoType INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  descrizione VARCHAR(45) NOT NULL,
  PRIMARY KEY (idCorsoType)
);

DROP TABLE IF EXISTS Corso;

CREATE TABLE IF NOT EXISTS Corso (
  idCorso INT NOT NULL,
  nome VARCHAR(45) NOT NULL,
  descrizione VARCHAR(45) NOT NULL,
  inizio DATE NOT NULL,
  fine DATE NOT NULL,
  idCorsoType INT NOT NULL,
  PRIMARY KEY (idCorso),
  INDEX fk_Corso_CorsoType_idx (idCorsoType ASC) VISIBLE,
  CONSTRAINT fk_Corso_CorsoType
    FOREIGN KEY (idCorsoType)
    REFERENCES CorsoType (idCorsoType)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE DATABASE IF NOT EXISTS RischiDB;
USE RischiDB;
DROP TABLE IF EXISTS Rischi;
CREATE TABLE IF NOT EXISTS Rischi(
    id int PRIMARY KEY AUTO_INCREMENT, matricola int,
    data date, luogo varchar(30));

CREATE DATABASE IF NOT EXISTS LuoghiDB;
USE LuoghiDB;
DROP TABLE IF EXISTS Luoghi;
CREATE TABLE IF NOT EXISTS Luoghi(
    id int PRIMARY KEY AUTO_INCREMENT, matricola int,
    data date, luogo varchar(30));

create DATABASE IF NOT EXISTS AccessiDB;
USE AccessiDB;
DROP TABLE IF EXISTS Accessi;
CREATE TABLE IF NOT EXISTS Accessi(
    id int PRIMARY KEY AUTO_INCREMENT, matricola int,
    data date, luogo varchar(30));


CREATE DATABASE IF NOT EXISTS AccountDB;
USE AccountDB;
DROP TABLE IF EXISTS Utente;
CREATE TABLE IF NOT EXISTS Utente(
    matricola int PRIMARY KEY AUTO_INCREMENT, nome varchar(30),
    cognome varchar(30)
);

CREATE DATABASE IF NOT EXISTS VisiteDB;
USE VisiteDB;
DROP TABLE IF EXISTS Visite;
CREATE TABLE IF NOT EXISTS Visite(
    id int PRIMARY KEY AUTO_INCREMENT, matricola int,
    data date, ora time, luogo varchar(30)
);

CREATE DATABASE IF NOT EXISTS CorsiSicurezzaDB;
USE CorsiSicurezzaDB;
DROP TABLE IF EXISTS Corsi;
CREATE TABLE IF NOT EXISTS Corsi(
    id int PRIMARY KEY AUTO_INCREMENT, matricola int,
    data date, luogo varchar(30));

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


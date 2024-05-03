-- Création de la base de données
CREATE DATABASE IF NOT EXISTS car_rental_db;

-- Utilisation de la base de données
USE car_rental_db;

-- Création de la table Utilisateur
CREATE TABLE IF NOT EXISTS Utilisateur (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    email VARCHAR(100),
    mot_de_passe VARCHAR(100)
);

-- Création de la table Reservation
CREATE TABLE IF NOT EXISTS Reservation (
    id_reservation INT AUTO_INCREMENT PRIMARY KEY,
    date_debut DATE,
    date_fin DATE,
    id_utilisateur INT,
    id_vehicule INT,
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur),
    FOREIGN KEY (id_vehicule) REFERENCES Vehicule(id_vehicule)
);

-- Création de la table Vehicule
CREATE TABLE IF NOT EXISTS Vehicule (
    id_vehicule INT AUTO_INCREMENT PRIMARY KEY,
    marque VARCHAR(100),
    modele VARCHAR(100),
    categorie VARCHAR(50),
    tarif_journalier DECIMAL(10, 2),
    id_agency INT,
    FOREIGN KEY (id_agency) REFERENCES Agency(id_agency)
);

-- Création de la table Bill
CREATE TABLE IF NOT EXISTS Bill (
    id_bill INT AUTO_INCREMENT PRIMARY KEY,
    id_reservation INT,
    user_id INT,
    amount DECIMAL(10, 2),
    status INT,
    date DATE,
    FOREIGN KEY (id_reservation) REFERENCES Reservation(id_reservation),
    FOREIGN KEY (user_id) REFERENCES Utilisateur(id_utilisateur)
);

-- Création de la table Agency
CREATE TABLE IF NOT EXISTS Agency (
    id_agency INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);


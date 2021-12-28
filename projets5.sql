DROP TABLE Appartenance;
DROP TABLE EstDans;
DROP TABLE Lu;

DROP TABLE FilDiscussion;
DROP TABLE Message;
DROP TABLE Groupe;
DROP TABLE Utilisateur;


CREATE TABLE Utilisateur
(
	id_utilisateur VARCHAR(6),
	motDePasse VARCHAR(32),
	nom VARCHAR(35),
	prenom VARCHAR(35),
    CONSTRAINT pk_id_utilisateur PRIMARY KEY(id_utilisateur)
);


CREATE TABLE Groupe
(
	role VARCHAR(20) NOT NULL,
    CONSTRAINT pk_id_groupe PRIMARY KEY(role) 
);


CREATE TABLE Message
(
    id_message int NOT NULL AUTO_INCREMENT, 
    date_emission DATETIME,
    statut VARCHAR(20),
    contenu VARCHAR(300), 
	id_utilisateur VARCHAR(6) CHECK (id_utilisateur is NOT NULL),
	id_filDiscussion VARCHAR(6) CHECK (id_filDiscussion is NOT NULL),
    CONSTRAINT pk_id_message PRIMARY KEY(id_message),
	CONSTRAINT fk_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);


CREATE TABLE FilDiscussion
(
	id_filDiscussion int NOT NULL AUTO_INCREMENT,
	id_groupe VARCHAR(20) CHECK (id_groupe is NOT NULL),
	premierMessage VARCHAR(300),
    CONSTRAINT pk_id_filDiscussion PRIMARY KEY(id_filDiscussion),
	CONSTRAINT fk_FilDiscu_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe)
);



CREATE TABLE Appartenance
(
	id_utilisateur VARCHAR(6),
	id_groupe VARCHAR(20),	
	CONSTRAINT pk_Appartenance PRIMARY KEY(id_groupe, id_utilisateur),
	CONSTRAINT fk_Appartenance_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe),
	CONSTRAINT fk_Appartenance_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);



CREATE TABLE EstDans
(
	id_utilisateur VARCHAR(6),
	id_filDiscussion INT(6),
	CONSTRAINT pk_EstDans PRIMARY KEY(id_utilisateur,id_filDiscussion),
	CONSTRAINT fk_EstDans_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur),
	CONSTRAINT fk_EstDans_id_filDiscussion FOREIGN KEY (id_filDiscussion) REFERENCES FilDiscussion(id_filDiscussion)
);


CREATE TABLE Lu
(
    id_message INT(6), 
	id_utilisateur VARCHAR(6),
    CONSTRAINT pk_Lu_id_message PRIMARY KEY(id_message,id_utilisateur),
	CONSTRAINT fk_Lu_id_message FOREIGN KEY (id_message) REFERENCES Message(id_message),
	CONSTRAINT fk_Lu_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);


INSERT INTO Utilisateur 
VALUES	("Dpt01", "aaaaaaaaaaaaaaaaaaaabb", "Jacques", "Dupont"),
		("Bnm01", "5aaaaaaaaaaaaaaaaaaaff", "Bernard", "Montagne");

INSERT INTO Groupe 
VALUES	("TPA22"),
		("Service technique");

INSERT INTO Appartenance
VALUES	("Dpt01", "TA22");

INSERT INTO FilDiscussion 
VALUES	(1, "TPA22", "Le premier message"),
		(2, "Service technique", "Le truc est pété");

INSERT INTO Message 
VALUES	(NULL,"2021-12-25 12:32:54", "Rouge", "Le premier message", "Dpt01", 1),
		(NULL,"2021-12-25 12:36:54", "Rouge", "Le deuxieme", "Bnm01", 1),
		(NULL,"2021-12-29 12:32:54", "Rouge", "Le truc est pété", "Bnm01", 2),
		(NULL,"2021-12-29 12:35:59", "Rouge", "C'est réparé", "Dpt01", 2);

INSERT INTO EstDans
VALUES  ("Bnm01",1),
		("Bnm01",2),
		("Dpt01",1),
		("Dpt01",2);
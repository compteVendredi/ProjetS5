fffff
#0429

CBT_Bastini — Hier à 18:49
Je fait un gros changement sur le getListFil en gros ca retournera une list<String[3]>
Avec list[0] = id_fil
list[1] = message
list[2] = nb_message non lu
Sera juste chiant parce que le id_fil est le nb_messageNonLu serons en string
Mais au moins on enleve l'atribue dans la classe eFilDiscussion
fffff — Hier à 18:55
tu parles bien de la fonction getListFil ?
CBT_Bastini — Hier à 18:55
Oui
fffff — Hier à 18:56
public Map<Integer, String> getListFil(String id_utilisateur) ?
CBT_Bastini — Hier à 18:56
oui
Je suis dispo vocal si jamais
fffff — Hier à 18:58
mais c'est toujours Map<Integer, String> ?
CBT_Bastini — Hier à 18:58
J'ai pas encore pull
public List<String[]> getListFil(String id_utilisateur)
Ou sinon on peut faire une List<FilDiscussion> mais y aurra que un message dans les fil. Ils seront pas complet
?
fffff — Hier à 19:02
reste sur la première
CBT_Bastini — Hier à 19:02
Isoké
fffff — Hier à 19:12
et renvoie moi directement le bdd.java plutôt que de le pull sur github puisque t'as pas dû prendre le dernier dépôt
(ou dépose uniquement le bdd.java)
CBT_Bastini — Hier à 19:12
Je met que bdd a chaque fois
fffff — Hier à 19:57
ping moi quand t'as fini puisqu'il faut que je teste d'autres modifications
CBT_Bastini — Hier à 19:58
J'ai fini le principal je fait les requete "admin" la @fffff

DROP TABLE Appartenance;
DROP TABLE EstDans;
DROP TABLE Lu;
DROP TABLE Recu;
Afficher plus
projetS5.sql
4 Ko
J'ai pull bdd
UhtredTheDane — Hier à 20:16
je finis l'interface serveur ce soir je la post vous l'aurez demain matin
CBT_Bastini — Hier à 20:17
Ok je te posse les fonction vite fait
supprimerUser(String id_user) : int
UhtredTheDane — Hier à 20:17
faudrait qu'on se voit aussi genre demain aprem ou dimanche matin pour faire le point
CBT_Bastini — Hier à 20:17
supprimerMessage(int id_message) : int
Demain fin aprème ?
ajouterGroupe(String role) : int
ajouterUser(String id_user, String hashMDP, String Nom, String prenom) : int
UhtredTheDane — Hier à 20:18
ok parfait pour moi
CBT_Bastini — Hier à 20:18
supprimerGroupe(id_groupe) : int
supprimerMessage(Int ID_message)
supprimerFil(Int ID_fil)
insertGroupe(String Id_user, Int ID_groupe)
CBT_Bastini — Hier à 20:38

DROP TABLE Appartenance;
DROP TABLE EstDans;
DROP TABLE Lu;
DROP TABLE Recu;

DROP TABLE FilDiscussion;
DROP TABLE Message;
DROP TABLE Groupe;
DROP TABLE Utilisateur;


CREATE TABLE Utilisateur
(
	id_utilisateur VARCHAR(6),
	motDePasse VARCHAR(64),
	nom VARCHAR(35),
	prenom VARCHAR(35),
    CONSTRAINT pk_id_utilisateur PRIMARY KEY(id_utilisateur)
);


CREATE TABLE Groupe
(
	id_groupe VARCHAR(30),
	nb_utilisateur INT(6),
    CONSTRAINT pk_id_groupe PRIMARY KEY(id_groupe) 
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
	CONSTRAINT fk_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE FilDiscussion
(
	id_filDiscussion int NOT NULL AUTO_INCREMENT,
	id_groupe VARCHAR(30) CHECK (id_groupe is NOT NULL),
	premierMessage VARCHAR(300),
	nb_utilisateur INT(6),
	nb_message INT(6),
    CONSTRAINT pk_id_filDiscussion PRIMARY KEY(id_filDiscussion),
	CONSTRAINT fk_FilDiscu_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe) ON DELETE CASCADE
);

CREATE TABLE Appartenance
(
	id_groupe VARCHAR(30),
	id_utilisateur VARCHAR(6),
	CONSTRAINT pk_Appartenance PRIMARY KEY(id_groupe, id_utilisateur),
	CONSTRAINT fk_Appartenance_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe) ON DELETE CASCADE,
	CONSTRAINT fk_Appartenance_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE EstDans
(
	id_utilisateur VARCHAR(6),
	id_filDiscussion INT(6),
	CONSTRAINT pk_EstDans PRIMARY KEY(id_utilisateur,id_filDiscussion),
	CONSTRAINT fk_EstDans_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE,
	CONSTRAINT fk_EstDans_id_filDiscussion FOREIGN KEY (id_filDiscussion) REFERENCES FilDiscussion(id_filDiscussion) ON DELETE CASCADE
);


CREATE TABLE Lu
(
    id_message INT(6), 
	id_utilisateur VARCHAR(6),
    CONSTRAINT pk_Lu_id_message PRIMARY KEY(id_message,id_utilisateur),
	CONSTRAINT fk_Lu_id_message FOREIGN KEY (id_message) REFERENCES Message(id_message) ON DELETE CASCADE,
	CONSTRAINT fk_Lu_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Recu
(
    id_filDiscussion INT(6), 
	id_utilisateur VARCHAR(6),
    CONSTRAINT pk_Recu_id_filDiscussion PRIMARY KEY(id_filDiscussion,id_utilisateur),
	CONSTRAINT fk_Recu_id_filDiscussion FOREIGN KEY (id_filDiscussion) REFERENCES FilDiscussion(id_filDiscussion) ON DELETE CASCADE,
	CONSTRAINT fk_Recu_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

INSERT INTO Utilisateur 
VALUES	("Dpt01", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", "Jacques", "Dupont"), #1234
		("Bnm01", "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589", "Bernard", "Montagne"); #abcd

INSERT INTO Groupe 
VALUES	("TPA22",1),
		("Service technique",1);

INSERT INTO FilDiscussion 
VALUES	(1, "TPA22", "Le premier message",2,2),
		(2, "Service technique", "Le truc est pété",2,2);
... (24 lignes restantes)
Réduire
projetS5.sql
4 Ko
J'ai tout fini j'ai fait des tests pour toutes les fonctions. Je referai une phase de test pour check tout demain. Et voir un peut d'opti. J'ai pull le bdd sur git
fffff — Hier à 20:50
t'as pris en compte les modifications pour FilDiscussionUtilisateur et ServiceUtilisateur?
après je serais pas là samedi soir et dimanche (jusqu'à 14h)
fffff — Hier à 20:58
tu peux voc @CBT_Bastini ?
CBT_Bastini — Hier à 21:01
oui
CBT_Bastini — Hier à 21:27
@UhtredTheDane Demain 17h au final
fffff — Hier à 22:02
c'est bon je viens de mettre à jour bdd.java pour les majuscules (et les import), je viens de le déposer tu peux le lancer 
Il te reste plus qu'à faire les Communication.log 
CBT_Bastini — Hier à 22:04
Ok nice merci
fffff — Hier à 22:04
et le javadoc 
CBT_Bastini — Aujourd’hui à 16:00
Je serai al vers 17h30 commencer sans moi
CBT_Bastini — Aujourd’hui à 16:54
Changement je suis la a 17h
CBT_Bastini — Aujourd’hui à 17:06
    // updateUtilisateurMDP(String id_utilisateur,String mdp) : int
    // updateUtilisateurNom(String id_utilisateur,String nom) : int
    // updateUtilisateurPrenom(String id_utilisateur,String prenom) : int
    // updateUtilisateurId(String id_utilisateur,String id_utilisateur) : int
List<String> list = bdd.getListGroupeUtilisateur("Dpt01");
        for (String string : list) {
            System.out.println(string);
        }
﻿

DROP TABLE Appartenance;
DROP TABLE EstDans;
DROP TABLE Lu;
DROP TABLE Recu;

DROP TABLE FilDiscussion;
DROP TABLE Message;
DROP TABLE Groupe;
DROP TABLE Utilisateur;


CREATE TABLE Utilisateur
(
	id_utilisateur VARCHAR(6),
	motDePasse VARCHAR(64),
	nom VARCHAR(35),
	prenom VARCHAR(35),
    CONSTRAINT pk_id_utilisateur PRIMARY KEY(id_utilisateur)
);


CREATE TABLE Groupe
(
	id_groupe VARCHAR(30),
	nb_utilisateur INT(6),
    CONSTRAINT pk_id_groupe PRIMARY KEY(id_groupe) 
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
	CONSTRAINT fk_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE FilDiscussion
(
	id_filDiscussion int NOT NULL AUTO_INCREMENT,
	id_groupe VARCHAR(30) CHECK (id_groupe is NOT NULL),
	premierMessage VARCHAR(300),
	nb_utilisateur INT(6),
	nb_message INT(6),
    CONSTRAINT pk_id_filDiscussion PRIMARY KEY(id_filDiscussion),
	CONSTRAINT fk_FilDiscu_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe) ON DELETE CASCADE
);

CREATE TABLE Appartenance
(
	id_groupe VARCHAR(30),
	id_utilisateur VARCHAR(6),
	CONSTRAINT pk_Appartenance PRIMARY KEY(id_groupe, id_utilisateur),
	CONSTRAINT fk_Appartenance_id_groupe FOREIGN KEY (id_groupe) REFERENCES Groupe(id_groupe) ON DELETE CASCADE,
	CONSTRAINT fk_Appartenance_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE EstDans
(
	id_utilisateur VARCHAR(6),
	id_filDiscussion INT(6),
	CONSTRAINT pk_EstDans PRIMARY KEY(id_utilisateur,id_filDiscussion),
	CONSTRAINT fk_EstDans_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE,
	CONSTRAINT fk_EstDans_id_filDiscussion FOREIGN KEY (id_filDiscussion) REFERENCES FilDiscussion(id_filDiscussion) ON DELETE CASCADE
);


CREATE TABLE Lu
(
    id_message INT(6), 
	id_utilisateur VARCHAR(6),
    CONSTRAINT pk_Lu_id_message PRIMARY KEY(id_message,id_utilisateur),
	CONSTRAINT fk_Lu_id_message FOREIGN KEY (id_message) REFERENCES Message(id_message) ON DELETE CASCADE,
	CONSTRAINT fk_Lu_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

CREATE TABLE Recu
(
    id_filDiscussion INT(6), 
	id_utilisateur VARCHAR(6),
    CONSTRAINT pk_Recu_id_filDiscussion PRIMARY KEY(id_filDiscussion,id_utilisateur),
	CONSTRAINT fk_Recu_id_filDiscussion FOREIGN KEY (id_filDiscussion) REFERENCES FilDiscussion(id_filDiscussion) ON DELETE CASCADE,
	CONSTRAINT fk_Recu_id_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

INSERT INTO Utilisateur 
VALUES	("Dpt01", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", "Jacques", "Dupont"), #1234
		("Bnm01", "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589", "Bernard", "Montagne"); #abcd

INSERT INTO Groupe 
VALUES	("TPA22",1),
		("Service technique",1);

INSERT INTO FilDiscussion 
VALUES	(1, "TPA22", "Le premier message",2,2),
		(2, "Service technique", "Le truc est pété",2,2);

INSERT INTO Message 
VALUES	(NULL,"2021-12-25 12:32:54", "Rouge", "Le premier message", "Dpt01", 1),
		(NULL,"2021-12-25 12:36:54", "Rouge", "Le deuxieme", "Bnm01", 1),
		(NULL,"2021-12-29 12:32:54", "Rouge", "Le truc est pété", "Bnm01", 2),
		(NULL,"2021-12-29 12:35:59", "Rouge", "C est réparé", "Dpt01", 2);

INSERT INTO EstDans
VALUES  ("Bnm01",1),
		("Bnm01",2),
		("Dpt01",1),
		("Dpt01",2);


INSERT INTO Appartenance
VALUES ("TPA22", "Bnm01"),
       ("Service technique", "Dpt01");






projetS5.sql
4 Ko

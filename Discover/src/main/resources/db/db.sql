drop schema discover;
create schema discover;
use discover;

set foreign_key_checks=0;

drop table if exists USERCONNECTION;
create table USERCONNECTION (
	USERID         VARCHAR(255) not null,
	PROVIDERID     VARCHAR(255) not null,
	PROVIDERUSERID VARCHAR(255) not null,
	RANK           INTEGER not null,
	DISPLAYNAME    VARCHAR(255),
	PROFILEURL     VARCHAR(512),
	IMAGEURL       VARCHAR(512),
	ACCESSTOKEN    VARCHAR(255) not null,
	SECRET         VARCHAR(255),
	REFRESHTOKEN   VARCHAR(255),
	EXPIRETIME     BIGINT
);

alter table USERCONNECTION
add primary key (USERID, PROVIDERID, PROVIDERUSERID) ;

create unique index USERCONNECTIONRANK on USERCONNECTION (USERID, PROVIDERID, RANK) ;

drop table if exists USER_ACCOUNTS;
create table USER_ACCOUNTS (
	ID         VARCHAR(255) not null,
	EMAIL      VARCHAR(100) not null,
	USER_NAME      VARCHAR(100) not null,
	FIRST_NAME VARCHAR(100),
	LAST_NAME  VARCHAR(100),
	PASSWORD   VARCHAR(255),
	ROLE       VARCHAR(20),
	ENABLED    VARCHAR(1) default 'Y' not null,
	LIVELLO	   INTEGER,
	PROVIDER   VARCHAR(30)
);

alter table USER_ACCOUNTS add image_url varchar(400);
ALTER TABLE USER_ACCOUNTS ADD punti INTEGER DEFAULT 0;
ALTER TABLE USER_ACCOUNTS ADD SESSO CHAR;
ALTER TABLE USER_ACCOUNTS ADD DATA_NASCITA DATE;

alter table USER_ACCOUNTS
add primary key (ID) ;
alter table USER_ACCOUNTS
add constraint User_Account_UK1 unique (EMAIL);

drop table if exists MESSAGGI;
CREATE TABLE MESSAGGI (
	ID INT NOT NULL AUTO_INCREMENT,
	USER_FROM VARCHAR(255),
	USER_TO VARCHAR(255),
	TESTO VARCHAR(4000),
	DATA_INVIO DATE,
	CONSTRAINT PK_MESSAGGI PRIMARY KEY (ID)
);

ALTER TABLE MESSAGGI ADD CONSTRAINT FK_MESSAGGI_FROM FOREIGN KEY (USER_FROM) REFERENCES USER_ACCOUNTS(ID);
ALTER TABLE MESSAGGI ADD CONSTRAINT FK_MESSAGGI_TO FOREIGN KEY (USER_TO) REFERENCES USER_ACCOUNTS(ID);

DROP TABLE IF EXISTS NOTIFICHE;
CREATE TABLE NOTIFICHE (
	ID INT NOT NULL AUTO_INCREMENT,
	TIPO VARCHAR(255) COMMENT 'GESTITO DA UN ENUMERATORE',
	DESCRIZIONE VARCHAR(4000),
	DATA_NOTIFICA DATE,
	USER_TO VARCHAR(255),
	CONSTRAINT PK_NOTIFICHE PRIMARY KEY (ID)
);

ALTER TABLE NOTIFICHE ADD CONSTRAINT FK_NOTIFICHE_TO FOREIGN KEY (USER_TO) REFERENCES USER_ACCOUNTS(ID);

DROP TABLE IF EXISTS TIPO_ATTRAZIONE;
CREATE TABLE TIPO_ATTRAZIONE (
	ID INT AUTO_INCREMENT,
	DESCRIZIONE VARCHAR(255),
	CONSTRAINT PK_TIPO_ATTRAZIONE PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS STATO_ATTRAZIONE;
CREATE TABLE STATO_ATTRAZIONE (
	ID INT AUTO_INCREMENT,
	DESCRIZIONE VARCHAR(255),
	CONSTRAINT PK_STATO_ATTRAZIONE PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS MARKER_POSIZIONE;
CREATE TABLE MARKER_POSIZIONE (
	ID INT AUTO_INCREMENT,
	LATITUDINE VARCHAR(200),
	LONGITUDINE VARCHAR(200),
	DESCRIZIONE VARCHAR(500),
	CONSTRAINT PK_MARKER_POSIZIONE PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS ATTRAZIONI;
CREATE TABLE ATTRAZIONI (
	ID INT AUTO_INCREMENT,
	NOME VARCHAR(255),
	DESCRIZIONE VARCHAR(4000),
	ID_TIPO_ATTRAZIONE INT COMMENT 'GESTITO CON TABELLA DI DECODIFICA',
	ACCESSO VARCHAR(400) COMMENT 'GESTITO DA UN ENUMERATORE ',
	ID_MARKER_POSIZIONE INT,
	ID_STATO_ATTRAZIONE INT COMMENT 'GESTITO CON UNA TABELLA DI DECODIFICA',
	USER_INSERIMENTO VARCHAR(255),
	DATA_INSERIMENTO DATE,
	CONSTRAINT PK_ATTRAZIONI PRIMARY KEY (ID)
);

ALTER TABLE ATTRAZIONI ADD CONSTRAINT FK_ATTRAZIONI_TIPO FOREIGN KEY (ID_TIPO_ATTRAZIONE) REFERENCES TIPO_ATTRAZIONE (ID);
ALTER TABLE ATTRAZIONI ADD CONSTRAINT FK_ATTRAZIONI_POSIZIONE FOREIGN KEY (ID_MARKER_POSIZIONE) REFERENCES MARKER_POSIZIONE (ID);
ALTER TABLE ATTRAZIONI ADD CONSTRAINT FK_ATTRAZIONI_STATO FOREIGN KEY (ID_STATO_ATTRAZIONE) REFERENCES STATO_ATTRAZIONE (ID);
ALTER TABLE ATTRAZIONI ADD CONSTRAINT FK_ATTRAZIONI_USERINS FOREIGN KEY (USER_INSERIMENTO) REFERENCES user_accounts(ID);

DROP TABLE IF EXISTS RECENSIONI;
CREATE TABLE RECENSIONI (
	ID INT AUTO_INCREMENT,
	TITOLO VARCHAR(250),
	TESTO VARCHAR(4000),
	VALUTAZIONE DOUBLE,
	REAZIONE VARCHAR(100) COMMENT 'GESTITO DA UN ENUMERATORE',
	VISITA_CONFERMATA INT COMMENT 'BOOLEANO: 1 VISITATA, 0 ALTRIMENTI',
	ID_ATTRAZIONE INT,
	USER_INSERIMENTO VARCHAR(255),
	DATA_INSERIMENTO DATE,
	CONSTRAINT PK_RECENSIONI PRIMARY KEY (ID)
);

ALTER TABLE RECENSIONI ADD CONSTRAINT FK_RECENSIONI_ATTRAZIONE FOREIGN KEY (ID_ATTRAZIONE) REFERENCES ATTRAZIONI(ID);
ALTER TABLE RECENSIONI ADD CONSTRAINT FK_RECENSIONI_USERINS FOREIGN KEY (USER_INSERIMENTO) REFERENCES user_accounts(ID);

DROP TABLE IF EXISTS FOTO;
CREATE TABLE FOTO (
	ID INT AUTO_INCREMENT,
	PATH VARCHAR(400),
	DATA_CARICAMENTO DATE,
	ID_RECENSIONE INT,
	ID_ATTRAZIONE INT,
	PRINCIPALE INT COMMENT 'BOOLEANO: 1 è PRINCIPALE, 0 ALTRIMENTI',
	CONSTRAINT PK_FOTO PRIMARY KEY (ID)
);

ALTER TABLE FOTO ADD CONSTRAINT FK_FOTO_RECENSIONE FOREIGN KEY (ID_RECENSIONE) REFERENCES RECENSIONI(ID);
ALTER TABLE FOTO ADD CONSTRAINT FK_FOTO_ATTRAZIONE FOREIGN KEY (ID_ATTRAZIONE) REFERENCES ATTRAZIONI(ID);

DROP TABLE IF EXISTS WISHLIST;
CREATE TABLE WISHLIST (
	ID INT AUTO_INCREMENT,
	NOME VARCHAR(200),
	DATA_CREAZIONE DATE,
	USER_PROPRIETARIO VARCHAR(255),
	ARCHIVIATA INT DEFAULT 0 COMMENT 'BOOLEANO: 1 è STATA ARCHIVIATA (ITINERARIO), 0 ALTRIMENTI',
	CONSTRAINT PK_WISHLIST PRIMARY KEY (ID)
);

ALTER TABLE WISHLIST ADD CONSTRAINT FK_WISHLIST_USERPROP FOREIGN KEY (USER_PROPRIETARIO) REFERENCES user_accounts(ID);

DROP TABLE IF EXISTS REL_WISHLIST_ATTRAZIONE;
CREATE TABLE REL_WISHLIST_ATTRAZIONE (
	ID INT AUTO_INCREMENT,
	ID_WISHLIST INT,
	ID_ATTRAZIONE INT,
	CONSTRAINT PK_REL_WISHLIST_ATTRAZIONE PRIMARY KEY (ID)
);

ALTER TABLE REL_WISHLIST_ATTRAZIONE ADD CONSTRAINT FK_RELWISHATTR_WISH FOREIGN KEY (ID_WISHLIST) REFERENCES WISHLIST(ID);
ALTER TABLE REL_WISHLIST_ATTRAZIONE ADD CONSTRAINT FK_RELWISHATTR_ATTR FOREIGN KEY (ID_ATTRAZIONE) REFERENCES ATTRAZIONI(ID);

DROP TABLE IF EXISTS ITINERARI;
CREATE TABLE ITINERARI (
	ID INT AUTO_INCREMENT,
	NOME VARCHAR(200),
	DATA_CREAZIONE DATE,
	USER_PROPRIETARIO VARCHAR(255),
	ARCHIVIATA INT DEFAULT 0 COMMENT 'BOOLEANO: 1 è STATA ARCHIVIATA (GIà FATTO), 0 ALTRIMENTI',
	DATA_INIZIO DATE,
	DATA_FINE DATE,
	NUMERO_GIORNI INT,
	CONSTRAINT PK_ITINERARI PRIMARY KEY (ID)
);

ALTER TABLE itinerari ADD CONFERMATO INT DEFAULT 0;
ALTER TABLE ITINERARI ADD CONSTRAINT FK_ITINERARI_USERPROP FOREIGN KEY (USER_PROPRIETARIO) REFERENCES user_accounts(ID);

DROP TABLE IF EXISTS VISITE;
CREATE TABLE VISITE (
	ID INT AUTO_INCREMENT,
	GIORNO INT,
	DATA_VISITA DATE,
	ORDINE VARCHAR(20),
	ORA VARCHAR(20),
	ETICHETTA VARCHAR(100),
	NOTA_PREC VARCHAR(4000),
	NOTA VARCHAR(4000),
	ID_ITINERARIO INT,
	ID_ATTRAZIONE INT,
	CONSTRAINT PK_VISITE PRIMARY KEY (ID)
);

ALTER table visite add CONFERMA INT default 0;
ALTER TABLE VISITE ADD CONSTRAINT FK_VISITE_ITINERARIO FOREIGN KEY (ID_ITINERARIO) REFERENCES ITINERARI(ID);
ALTER TABLE VISITE ADD CONSTRAINT FK_VISITE_ATTRAZIONE FOREIGN KEY (ID_ATTRAZIONE) REFERENCES ATTRAZIONI(ID);

delete from USER_ACCOUNTS;
insert into USER_ACCOUNTS values ('f9d91768-50ae-477d-8284-f338e940b5d2','m.manfredelli@campus.unimib.it', 'Mauro Manfredelli', 'Mauro','Manfredelli','password','ROLE_USER','Y',500,'DISCOVER',null,50000,'m',null);
insert into USER_ACCOUNTS values ('1', 'susanna.bella@esempio.at', 'Susanna Bella', 'Susanna', 'Bella', 'password', 'ROLE_USER', 'Y', 400, 'DISCOVER', '/discover/resources/dist/img/user3-128x128.jpg', 40000, 'f', null);
insert into USER_ACCOUNTS values ('2', 'rebecca.brambilla@esempio.at', 'Rebecca Brambilla', 'Rebecca', 'Brambilla', 'password', 'ROLE_USER', 'Y',600, 'DISCOVER', '/discover/resources/dist/img/user4-128x128.jpg', 60000, 'f', null);
insert into USER_ACCOUNTS values ('3', 'alessandro.rossi@esempio.at', 'Alessandro Rossi', 'Alessandro', 'Rossi', 'password', 'ROLE_USER', 'Y',700,'DISCOVER', '/discover/resources/dist/img/user2-160x160.jpg', 70000, 'm', null);
insert into USER_ACCOUNTS values ('4', 'hichame.yessou@esempio.at', 'Hichame Yessou', 'Hichame', 'Yessou', 'password', 'ROLE_USER', 'Y',300,'DISCOVER', '/discover/resources/dist/img/hic.jpg', 30000, 'm', null);
insert into USER_ACCOUNTS values ('5', 'test@gmail.com', 'Mario Rossi', 'Mario', 'Rossi', 'test', 'ROLE_USER', 'Y', 300, 'DISCOVER', '/discover/resources/dist/img/eder.jpg', 300000, 'm', null);


INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Spiaggia');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Punto panoramico');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Percorso');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Monumento');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Museo');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Costruzione');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Borgo');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione naturalistica');

INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Nuova attrazione');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione da scoprire');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione verificata');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione secondaria');

create or replace view vw_liste_utente as
	(select CONCAT('wishlist',w.id) ID, w.id ID_WISHLIST, null ID_ITINERARIO, w.nome, w.DATA_CREAZIONE, w.USER_PROPRIETARIO, w.ARCHIVIATA, 0 CONFERMATO, null DATA_INIZIO, null DATA_FINE, null NUMERO_GIORNI,
		(select count(*)
		 from rel_wishlist_attrazione rwa
		 where rwa.ID_WISHLIST=w.ID) NUMERO_ATTRAZIONI
	from wishlist w) 
	union
	(select CONCAT('itinerario',i.ID) ID, null ID_WISHLIST, i.ID ID_ITINERARIO, i.NOME, i.DATA_CREAZIONE, i.USER_PROPRIETARIO, i.ARCHIVIATA, i.CONFERMATO, i.DATA_INIZIO, i.DATA_FINE, i.NUMERO_GIORNI,
		(select count(*)
		 from visite v
		 where v.ID_ITINERARIO=i.ID) NUMERO_ATTRAZIONI
	from itinerari i);

insert into marker_posizione values (100, '40.95307314144854', '8.226498126459774', 'Stintino (SS), Italy, 07040');
insert into attrazioni values (
	100,
	'Spiaggia "La Pelosa"',
	'La spiaggia della Pelosa è sicuramente una delle spiaggie più belle della Sardegna e da molti considerata come la spiaggia con l''acqua così incantevole da non avere uguali in nessun altro posto d''Italia. E'' stata eletta per la sua bellezza, la 2^ spiaggia più bella in Italia (1^ in Sardegna) e la 4^ in Europa. Il suo colore turchese dell''accqua della Pelosa e le sue trasparenze, nonché la finissima sabbia bianca la fanno somigliare ad una spiaggia tropicale più che ad una mediterranea. Oltre all''assenza dei pesci tropicali, è la vegetazione a ricordarci che siamo in Sardegna; non palme, ma macchia mediterranea ai limiti e perfino sulla stessa spiaggia. Nel corso degli anni, la noncuranza, se non maleducazione dei villeggianti ha messo a rischio la vegetazione ai bordi e sulla spiaggia stessa, vegetazione che peraltro è essenziale per la sua funzione di trattenere la sabbia, per cui negli ultimi anni si è provveduto a transennare e porre divieti per difendere tale vegetazione. La spiaggia della Pelosa è situata nel golfo dell''Asinara, all''estremità nord-est di Capo Falcone ed è protetta dal mare aperto da una barriera naturale formata dai faraglioni di Capo Falcone, dall''isola Piana e dall''Asinara (come si può notare dalla panoramica aerea alla pag. Capo Falcone). L''acqua della Pelosa per questo motivo è sempre calma anche quando il maestrale, frequente in queste zone, si fa sentire. Di fronte alla spiaggia si trova l''isolotto (chiamato appunto della Pelosa) con la caratteristica torre aragonese edificata nel 1578 a difesa del litorale, che dà il nome alla spiaggia. Per una più dettagliata descrizione della torre si veda la pagina delle Torri. L''isolotto della Pelosa è raggiungibile a piedi dalla costa seguendo un guado naturale in un punto all''estremità nord della spiaggia della Pelosa. Poco oltre si nota l''isola Piana usata in passato come pascolo per il bestiame, che data la vicinanza dell''isola alla costa, veniva portato a nuoto trainandolo con barconi. Sull''isola Piana, in passato proprietà privata della famiglia Berlinguer ed oggi demanio, si trova un''altra torre chiamata Torre della Finanza anch''essa descritta alla pagina delle torri costiere. Nella stessa direzione oltre l''isola Piana si scorge il profilo delle rocce maestose dell''Asinara.',
	2,
	'LIBERO',
	100,
	2,
	'1',
	STR_TO_DATE('08/08/2018 12:22', '%d/%m/%Y %T')
);
insert into recensioni values (
	200, 
	'Vivamente consigliata',
	'Spiaggia eccezionale peccato la calca di gente ma ad agosto credo sia più che normale!Il parcheggio si paga 2 euro l''ora ma vista la spiaggia ne vale la pena!!!! Da sapere assolutamente che sotto agli asciugamani bisogna mettere la stuoia per evitare di portare via la sabbia dalla spiaggia. Girano le guardie per controllare le persone singolarmente e la multa è di 100 euro circa.... ma nella via ci sono molti ambulanti che vendono stuoie di tutti i tipi a poco prezzo!',
	null,
	'MI_PIACE',
	1,
	100,
	'1',
	STR_TO_DATE('08/08/2018 12:22', '%d/%m/%Y %T')
);
insert into foto values (200, '/discover/resources/dist/img/attrazione100/1.jpg', STR_TO_DATE('08/08/2018 12:22', '%d/%m/%Y %T'), 200, 100, 1);
insert into foto values (201, '/discover/resources/dist/img/attrazione100/2.jpg', STR_TO_DATE('08/08/2018 12:22', '%d/%m/%Y %T'), 200, 100, 1);
insert into recensioni values (
	201, 
	'Una piscina naturale ma...',
	'Siamo arrivati in mattina per le 11 ed era impossibile starci, nessun parcheggio nei dintorni e spiaggia super affollata, quindi siamo tornati sul tardo pomeriggio verso le 18, parcheggi disponibili al prezzo di 2€ ogni ora e spiaggia finalmente utilizzabile, ricordatevi di comprare in precedenza le stuoie perché sono OBBLIGATORIE, mare semplicemente fantastico, una piscina naturale.',
	null,
	'MI_PIACE',
	1,
	100,
	'4',
	STR_TO_DATE('18/08/2018 13:33', '%d/%m/%Y %T')
);
insert into foto values (202, '/discover/resources/dist/img/attrazione100/3.jpg', STR_TO_DATE('18/08/2018 13:33', '%d/%m/%Y %T'), 201, 100, 1);

insert into marker_posizione values (101, '45.681577', '9.4512902', 'Paderno d''Adda (LC), Italy, 23899');
insert into attrazioni values (
	101,
	'Ponte San Michele',
	'Il ponte San Michele, noto anche come ponte di Calusco, ponte di Paderno o ponte Rothlisberger è un ponte ad arco in ferro, a traffico misto ferroviario-stradale, che collega i paesi di Paderno d''Adda e Calusco d''Adda attraversando una gola del fiume Adda appunto. Capolavoro riconosciuto come uno dei simboli di archeologia industriale del Paese, nel 2017 il ponte è stato candidato per essere inserito nella lista UNESCO dei patrimoni dell''umanità',
	7,
	'A_PAGAMENTO',
	101,
	3,
	'2',
	STR_TO_DATE('17/07/2018 10:12', '%d/%m/%Y %T')
);
insert into recensioni values (
	202, 
	'Una grande opera architettonica nascosta',
	'Dalla ciclabile sottostante si può ammirare in tutto il suo splendore. Davvero impressionante. Quando poi si viene a conoscere la storia da chi ha studiato bene l''opera, lo si apprezza ancora di più. Peccato che la ciclabile non sia tenuta bene in tutti i chilometri di percorrenza.',
	5,
	null,
	1,
	101,
	'2',
	STR_TO_DATE('17/07/2018 10:12', '%d/%m/%Y %T')
);
insert into foto values (204, '/discover/resources/dist/img/attrazione101/1.jpg', STR_TO_DATE('17/07/2018 10:12', '%d/%m/%Y %T'), 202, 101, 1);
insert into foto values (205, '/discover/resources/dist/img/attrazione101/2.jpg', STR_TO_DATE('17/07/2018 10:12', '%d/%m/%Y %T'), 202, 101, 1);
insert into recensioni values (
	203, 
	'Passaggio pendolare',
	'...la protezione che hanno messo x evitare i suicidi non serve a nulla e in più hanno reso la struttura orribile. Per il traffico già si sa xk è da sempre a senso alternato. Puntualizzazione x i vigili di paderno: date le multe a chi passa con il rossa da Paderno a Calusco altrimenti fate a meno di far funzionare il semaforo. Non passa una sola macchina con il rosso ma anche fino a 9-10!...quelli di Calusco la danno la multa..eccome se le danno.',
	4,
	null,
	1,
	101,
	'3',
	STR_TO_DATE('19/07/2018 09:12', '%d/%m/%Y %T')
);
insert into foto values (206, '/discover/resources/dist/img/attrazione101/3.jpg', STR_TO_DATE('19/07/2018 09:12', '%d/%m/%Y %T'), 203, 101, 1);

#DUOMO

insert into marker_posizione values (150, '45.46416835', '9.19162177501733000000', 'Duomo di Milano, Piazza del attrazione150, Milano, MI');

insert into attrazioni values (
	150, #codice
	'Duomo di Milano', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'MISTO', #accesso
	150, #POS
	3, #stato
	'1', #utente
	STR_TO_DATE('13/07/2015 10:12', '%d/%m/%Y %T') #data
);

insert into foto values (301, '/discover/resources/dist/img/attrazione150/1.jpg', null, null, 150, 1);
insert into foto values (302, '/discover/resources/dist/img/attrazione150/2.jpg', null, null, 150, 1);
insert into foto values (303, '/discover/resources/dist/img/attrazione150/3.jpg', null, null, 150, 1);
insert into foto values (304, '/discover/resources/dist/img/attrazione150/1.jpg', null, null, 150, 1);
insert into foto values (305, '/discover/resources/dist/img/attrazione150/2.jpg', null, null, 150, 1);
insert into foto values (306, '/discover/resources/dist/img/attrazione150/4.jpg', null, null, 150, 1);


#SCALA
insert into marker_posizione values (151, '45.46760475', '9.18911952312957', 'Teatro alla Scala, Via Filodrammatici, Milano, MI');

insert into attrazioni values (
	151,
	'Teatro alla Scala',
    'Descrizione',
	4,
	'A_PAGAMENTO',
	151,
	3,
	'1',
	STR_TO_DATE('12/05/2016 11:12', '%d/%m/%Y %T')
);

insert into foto values (307, '/discover/resources/dist/img/attrazione151/1.jpg', null, null, 151, 1);
insert into foto values (308, '/discover/resources/dist/img/attrazione151/2.jpg', null, null, 151, 1);
insert into foto values (309, '/discover/resources/dist/img/attrazione151/3.jpg', null, null, 151, 1);


#CASTELLO SFORZESCO

insert into marker_posizione values (152, '45.47029675', '9.17810314326426', 'Castello Sforzesco, Piazza Castello, Milano, MI');

insert into attrazioni values (
	152, #codice
	'Castello Sforzesco', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'MISTO', #accesso
	152, #POS
	3, #stato
	'2', #utente
	STR_TO_DATE('28/02/2015 18:12', '%d/%m/%Y %T') #data
);

insert into foto values (310, '/discover/resources/dist/img/attrazione152/1.jpg', null, null, 152, 1);
insert into foto values (311, '/discover/resources/dist/img/attrazione152/2.jpg', null, null, 152, 1);
insert into foto values (312, '/discover/resources/dist/img/attrazione152/3.jpg', null, null, 152, 1);
insert into foto values (313, '/discover/resources/dist/img/attrazione152/4.jpg', null, null, 152, 1);


#PARCO SEMPIONE

insert into marker_posizione values (153, '45.47303965', '9.17697104408925', 'Parco Sempione, Piazza Sempione, Milano, MI');

insert into attrazioni values (
	153, #codice
	'Parco Sempione', #nome
	'Descrizione', #descrizione
	8, #tipologia
	'LIBERO', #accesso
	153, #POS
	3, #stato
	'3', #utente
	STR_TO_DATE('28/03/2018 16:15', '%d/%m/%Y %T') #data
);

insert into foto values (314, '/discover/resources/dist/img/attrazione153/1.jpg', null, null, 153, 1);
insert into foto values (315, '/discover/resources/dist/img/attrazione153/2.jpg', null, null, 153, 1);


#ARCO DELLA PACE

insert into marker_posizione values (154, '45.47568115', '9.17241040283426', 'Arco della Pace, Piazza Sempione, Milano, MI');

insert into attrazioni values (
	154, #codice
	'Arco della Pace', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'LIBERO', #accesso
	154, #POS
	3, #stato
	'4', #utente
	STR_TO_DATE('14/08/2018 12:15', '%d/%m/%Y %T') #data
);

insert into foto values (316, '/discover/resources/dist/img/attrazione154/1.jpg', null, null, 154, 1);
insert into foto values (317, '/discover/resources/dist/img/attrazione154/2.jpg', null, null, 154, 1);


#attrazione155

insert into marker_posizione values (155, '45.4511621', '9.1765214', 'Darsena del Naviglio, Milano, MI');

insert into attrazioni values (
	155, #codice
	'Darsena di Milano', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'LIBERO', #accesso
	155, #POS
	3, #stato
	'1', #utente
	STR_TO_DATE('01/01/2018 19:15', '%d/%m/%Y %T') #data
);

insert into foto values (318, '/discover/resources/dist/img/attrazione155/1.jpg', null, null, 155, 1);
insert into foto values (319, '/discover/resources/dist/img/attrazione155/2.jpg', null, null, 155, 1);
insert into foto values (320, '/discover/resources/dist/img/attrazione155/3.jpg', null, null, 155, 1);
insert into foto values (321, '/discover/resources/dist/img/attrazione155/4.jpg', null, null, 155, 1);


#PIAZZA GAE AULENTI

insert into marker_posizione values (156, '45.4834640499999', '9.19031888691276', 'Piazza Gae Aulenti, Milano, MI');

insert into attrazioni values (
	156, #codice
	'Piazza Gae Aulenti', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'LIBERO', #accesso
	156, #POS
	2, #stato
	'2', #utente
	STR_TO_DATE('30/06/2018 09:15', '%d/%m/%Y %T') #data
);

insert into foto values (322, '/discover/resources/dist/img/attrazione156/1.jpg', null, null, 156, 1);
insert into foto values (323, '/discover/resources/dist/img/attrazione156/2.jpg', null, null, 156, 1);
insert into foto values (324, '/discover/resources/dist/img/attrazione156/3.jpg', null, null, 156, 1);
insert into foto values (325, '/discover/resources/dist/img/attrazione156/4.jpg', null, null, 156, 1);


#CORSO COMO

insert into marker_posizione values (157, '45.4828439', '9.187391', 'Corso Como, Milano, MI');

insert into attrazioni values (
	157, #codice
	'Corso Como', #nome
	'Descrizione', #descrizione
	4, #tipologia
	'LIBERO', #accesso
	157, #POS
	2, #stato
	'3', #utente
	STR_TO_DATE('30/08/2018 17:24', '%d/%m/%Y %T') #data
);

insert into foto values (326, '/discover/resources/dist/img/attrazione157/1.jpg', null, null, 157, 1);
insert into foto values (327, '/discover/resources/dist/img/attrazione157/2.jpg', null, null, 157, 1);


#SAN SIRO

insert into marker_posizione values (158, '45.4782003499999', '9.12396399069144', 'Stadio Giuseppe Meazza, Piazzale Angelo Moratti, Milano, MI');

insert into attrazioni values (
	158, #codice
	'Stadio Giuseppe Meazza', #nome
	'Descrizione', #descrizione
	6, #tipologia
	'LIBERO', #accesso
	158, #POS
	3, #stato
	'4', #utente
	STR_TO_DATE('30/10/2014 20:24', '%d/%m/%Y %T') #data
);

insert into foto values (328, '/discover/resources/dist/img/attrazione158/1.jpg', null, null, 158, 1);
insert into foto values (329, '/discover/resources/dist/img/attrazione158/2.jpg', null, null, 158, 1);
insert into foto values (330, '/discover/resources/dist/img/attrazione158/3.jpg', null, null, 158, 1);
insert into foto values (331, '/discover/resources/dist/img/attrazione158/4.jpg', null, null, 158, 1);
insert into foto values (332, '/discover/resources/dist/img/attrazione158/5.jpg', null, null, 158, 1);
insert into foto values (333, '/discover/resources/dist/img/attrazione158/6.jpg', null, null, 158, 1);


insert into wishlist values (401, 'Wishlist Milano', STR_TO_DATE('10/09/2018 17:00', '%d/%m/%Y %T'), '5', 0);
insert into wishlist values (402, 'Test', STR_TO_DATE('10/09/2018 17:00', '%d/%m/%Y %T'), '5', 0);
insert into rel_wishlist_attrazione values (501, 401, 150);
insert into rel_wishlist_attrazione values (502, 401, 151);
insert into rel_wishlist_attrazione values (503, 401, 152);
insert into rel_wishlist_attrazione values (504, 401, 155);
insert into rel_wishlist_attrazione values (505, 401, 158);

set foreign_key_checks=1;
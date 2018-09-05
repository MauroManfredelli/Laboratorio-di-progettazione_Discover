delete from USER_ACCOUNTS;
insert into USER_ACCOUNTS values ('f9d91768-50ae-477d-8284-f338e940b5d2','m.manfredelli@campus.unimib.it', 'Mauro Manfredelli', 'Mauro','Manfredelli','password','ROLE_USER','Y',null,1,'DISCOVER',0,'m',null);
insert into USER_ACCOUNTS values ('utenzaesempioSB', 'susanna.bella@esempio.at', 'Susanna Bella', 'Susanna', 'Bella', 'password', 'ROLE_USER', 'Y', '/discover/resources/dist/img/user3-128x128.jpg', 100, 'DISCOVER', 100000, 'f', null);
insert into USER_ACCOUNTS values ('utenzaesempioRB', 'rebecca.brambilla@esempio.at', 'Rebecca Brambilla', 'Rebecca', 'Brambilla', 'password', 'ROLE_USER', 'Y', '/discover/resources/dist/img/user4-128x128.jpg', 60, 'DISCOVER', 60000, 'f', null);
insert into USER_ACCOUNTS values ('utenzaesempioAR', 'alessandro.rossi@esempio.at', 'Alessandro Rossi', 'Alessandro', 'Rossi', 'password', 'ROLE_USER', 'Y', '/discover/resources/dist/img/user2-160x160.jpg', 56, 'DISCOVER', 56000, 'm', null);
insert into USER_ACCOUNTS values ('utenzaesempioHY', 'hichame.yessou@esempio.at', 'Hichame Yessou', 'Hichame', 'Yessou', 'password', 'ROLE_USER', 'Y', '/discover/resources/dist/img/hic.jpg', 2, 'DISCOVER', 2000, 'm', null);

DELETE FROM TIPO_ATTRAZIONE;
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Spiaggia');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Punto panoramico');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Percorso');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Monumento');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Museo');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Costruzione');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Borgo');
INSERT INTO TIPO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione naturalistica');

DELETE FROM STATO_ATTRAZIONE;
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Nuova attrazione');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione da scoprire');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione verificata');
INSERT INTO STATO_ATTRAZIONE(DESCRIZIONE) VALUES ('Attrazione secondaria');

create or replace view vw_liste_utente as
	(select CONCAT('wishlist',w.id) ID, w.id ID_WISHLIST, null ID_ITINERARIO, w.nome, w.DATA_CREAZIONE, w.USER_PROPRIETARIO, w.ARCHIVIATA, null DATA_INIZIO, null DATA_FINE, null NUMERO_GIORNI,
		(select count(*)
		 from rel_wishlist_attrazione rwa
		 where rwa.ID_WISHLIST=w.ID) NUMERO_ATTRAZIONI
	from wishlist w) 
	union
	(select CONCAT('itinerario',i.ID) ID, null ID_WISHLIST, i.ID ID_ITINERARIO, i.NOME, i.DATA_CREAZIONE, i.USER_PROPRIETARIO, i.ARCHIVIATA, i.DATA_INIZIO, i.DATA_FINE, i.NUMERO_GIORNI,
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
	'ACCESSO_LIBERO',
	100,
	2,
	'utenzaesempioSB',
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
	'utenzaesempioSB',
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
	'utenzaesempioHY',
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
	'utenzaesempioRB',
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
	'utenzaesempioRB',
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
	'utenzaesempioAR',
	STR_TO_DATE('19/07/2018 09:12', '%d/%m/%Y %T')
);
insert into foto values (206, '/discover/resources/dist/img/attrazione101/3.jpg', STR_TO_DATE('19/07/2018 09:12', '%d/%m/%Y %T'), 203, 101, 1);
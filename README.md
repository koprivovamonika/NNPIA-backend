## NNPIA semestrální práce - backend

Napsáno v spring boot. Slouží jako REST API rozhranní pro React frontend aplikaci.
Nasazeno na platformě [heroku](https://nnpia-backend.herokuapp.com/)

#### Téma
Tématem semestrální práce bylo napsání aplikace pro kosmetický salon.
Cílem bylo vytvoření takové aplikace, která dovolí nepřihlášenému uživateli vytvoření rezervace v kosmetickém salonu. 
Komunikace mezi zákazníky a salonem je řešena přes email. Z tohoto důvodu backend zasílá emailové zprávy zákazníkům, aby byli informováni o stavu jejich rezervace. 
Přihlášený uživatel (salon) může spravovat rezervace a také je mu umožněna správa kosmetických procedur, které salon nabízí.

#### Spuštění
K lokálnímu spuštění je potřeba si vytvořit databázi a její konfiguraci napsat do souboru `application-local.properties`.
Pak stačí jen zbuildovat maven projekt.

#### Kód
Rozdělní kódu do balíčků 
- configuration - konfigurační soubory (především pro JWT autentizaci)
- controller - API rozhranní
- repository- obsahuje JPA repositories
- dto - datové modely (dto)
- entity - datové modely (entity) a pomocné třídy (např. enumy)
- service - obsahuje servisní třídy

#### Služby:

/public - dostupné bez autentizace, slouží především k vytváření rezervací

/token - slouží k loginu 
      
/api - nutné s autentizací, k práci s rezervacemi a procedurami

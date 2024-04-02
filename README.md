Banka izdaje fizičkim osobama kreditne kartice. Osobe za to apliciraju banci. Za potrebe te evidencije treba napraviti mini-aplikaciju kojom će se evidentirati osoba(O) ili više njih predstavljenih Imenom, Prezimenom, OIB-om i Statusom za koje se treba izraditi kartica. Tip kartice nije bitan, već je samo jedan, tako da o tome ne trebaš razmišljati.
Osobe se moraju zapisati permanentno,  a način izaberi sam po volji. Preferira se baza podataka (bilo koja, može i H2) ili datoteka.

Kako bi proces za proizvodnju/tiskanje kreditnih kartica znao čiju/koju karticu napraviti, treba mu proslijediti podatke preko restful APIa. Definiciju APIa možeš naći u priloženoj YAML datoteci.

Napomena: Proces proizvodnje kartica ovdje je zamišljen da bi dao neki smisao, ali njime se nećeš baviti. (Osim ako imaš želju i volju, a u tom slučaju slobodno doradi API da radi i neku pamet sa snimanjem i provjerom postojanja kartice za osobu i sl.)


Aplikacija treba omogućiti:

- Upisivanje osobe(O) u skup osoba sa svim pripadajućim atributima(Ime, Prezime, OIB, Status),
- Pretraživanje skupa osoba(O) prema OIBu(ručni upis korisnika) i ako osoba(O) postoji, vratiti Ime, Prezime, OIB i Status za istu; Inače ne vrati ništa.
- Za pronađenu osobu(O) treba poslati podatke na spomenuti API sa svim popunjenim atributima(Ime, Prezime, OIB, Status).


Jedan poziv APIa treba sadržavati podatke samo za jednu osobu(O).
Osoba(O) se treba moći obrisati na zahtjev prema OIBu(ručni upis korisnika).

Metode treba napraviti da rade kao RESTfull.
Bonus Feature I:
Napraviti zaprimanje podataka o statusu izrade kartice sa APIa preko KAFKA topica. (Status može biti izmišljen, mock, po izboru, osim ako si se odlučio da API ima neku pamet koju odrađuje)

Bonus feature II:
Složi malu formu koja će pružiti korisničku podršku za rad sa gornjim procesom. Sloboda je dozvoljena, ali preferiramo React.

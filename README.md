# Operaciona-istrazivanja
Seminarski rad treba da sadrži sljedeće komponente za problem koji ste dobili:
1) Definiciju prostora pretrage, funkcije cilja, funckija prilagodljivosti (po mogućnosti), komponentu rješenja.
2) Pohlepni pristup rješavanju problema gdje treba definisati pohlepnu funkciju, dodavanje komponente u rješenje.
3) Genetski ili VNS pristup (odaberite jeda od ova dva) 
   - Za genentski: definisati jedinku, operator selekcije, ukrštanja, operator mutacije. 
   - Za VNS: definisati lokalnu pretragu, strukture okolina, objasniti zašto su baš takve uzete.
4) Implementirati model u CPLEX-u (ili PuLp, ako se odlučite za jezik python za implementiranje). 

Poređenje rezultata:
1. Prvo nađite instance za vaš problem. Ako postoje problemi u nalaženju istih, javite se meni pa ćemo zajedno da ih potražimo.
2. Odvojite maksimalno 50 instanci (30 je neki okvir) na kojima ćete da testirate. Neka budu raznovrsne po svojoj dimenziji (dakle, nekoliko malih instanci, zatim nekoliko
srednjih i nekoliko velikih).
3. Pohlepni algoritam puštate samo jednom po instanci. Bilježite vrijednost rezultata i vrijeme potrebno da se ovaj pristup izvrši. Genetski/VNS pristup puštate 10 puta na 
jednoj instanci. Bilježite srednju vrijednost rezultata (za 10 puštanja date instance), maksimalnu vrijednost (u 10 puštanja), standardnu devijaciju rezultata i srednju vrijednost
izvršavanja (na 10 puštanja). CPLEX puštate jednom po instanci gdje bilježite rezultat koji je vraćen i vrijeme izvršavanja. Sve ovo prikažite u jednoj (ili više) tabela 
-- možete recimo imati 3 tabele, jednu za rezultate na malim instancama, jednu tabelu za rezultate na srednjim instancama i jednu za rezultate za velike instance. najbolji
rezultat za svaku instancu među ova tri algoritma boldovati radi preglednosti.
4. Prodiskutovati rezultate: na koliko instanci jedan algoritam daje bolja rješenja, na koliko 
drugi, itd. Brzinu izvršanja algoritma, koliko je instanci CPLEX riješio do optimalnosti itd. 
5. Vrijeme za svaki od algoritama ograničiti na (maksimalno) 10 minuta.
6. Po slobodnom izboru birajte programski jezik u kom ćete da implementirate algoritme. Jedini uslov je da sva tri algoritma moraju da budu implementirani u istom programskom 
jeziku. 
7. Dostaviti report sa implementacijama algoritama meni na mejl bilo kada u toku akademske godine.

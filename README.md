# Titlu proiect: Apartment Building Administration App
### Student: Daniel Banci

## Descriere:
  Aplicația asigură o comunicare eficientă între locatari și administratorii acestora, utilizatorii având acces rapid la toate 
  informațiile necesare în ceea ce privește administrarea imobilului. 
  Aplicația este concepută pentru a manageria asociațiile de locatari în privința
  cheltuielilor lunare.
  Fluxurile principale ale aplicației:
    -sunt 2 tipuri de cont: locatar si administrator
    -Opțiuni principale locatar:
        -vizualizare cheltuieli
        -setări cont
        -actualizare contoare
        -sesizări
    -Opțiuni principale administrator:
        -actualizare cheltuieli
        -actualizare preturi
        -vizualizare/modificare detalii apartamente
        -sesizări

## Arhitectura
  Diagramele UML: ![image](https://user-images.githubusercontent.com/93344852/210967885-5ee70ce4-2ac7-43e9-8b54-6ffee8d43c1e.png)
  
  -User: clasă abstractă pentru utilizatori. 
  -Apartment: apartamentul dintr-o asociație.Moștenește User fiind folosit drept User pentru proprietarul/chiriașul unui apartament.
  -Admin: administratorul unei asociații. Moștenește User fiind folosit drept User pentru administrator.
  -Association: asociația din care fac parte apartamentele.
  
 Tabele baza de date: ![image](https://user-images.githubusercontent.com/93344852/210970248-5f6a1b5b-6e90-4534-9f94-2755b42b95c8.png)
  Associations: -informațiile legate de asociații
  Admins: -informațiile legate de admini
  Prices: -prețutile pentru fiecare asociație
  Apartments: -informațiile legate de apartamente
  

## Obiective/Functionalitati
  Interfata grafică și funcționalități:
    Login: -fereastra de deschidere a aplicației.
            -utilă pentru logarea utilizatorilor
            -redirecționează noii utilizatori spre fereastra de creare cont
    AdminAcc: -fereastră pentru contul administrator
      -conține un meniu vertical ce permite utilizatorilor să navigheze în aplicație
          -Acasă: -afișează o fereastră ce prezintă detaliile asociației administrate
          -Avizier: -deschide fereastra ce conține tabelul de avizier pentru asociația administrată
                  -pentru afișarea tabelului se folosește un scroll panel
          -Actualizare Avizier: -deschide o fereastră ce permite administratorului să modifice prețurile utilităților sau să introducă 
                          sumele de pe facturile acestora în vederea calculării cheltuielilor
                          -setarea datei limită de plată a cheltuielilor/zilelor de plată
          -Detalii apartamente: -fereastră ce permite vizualizarea detaliilor legate de apartamente și modificarea acestora de către administrator
                          -conține un buton cu opțiuni din care administratorul alege apartamentul pentru care vrea să vadă/modifice informațiile
          -Sesizări: -dialog între administrator și locatari
          -Setări cont:-arată detaliile contului de administrator
                      -permite schimbarea parolei
                      -deconectare
    ApartmentAcc: -fereastră pentru contul de proprietar/chiriaș
                -conține un meniu vertical ce permite utilizatorului să navigheze în aplicație
                    -Acasă: -fereastră ce afișează detaliile contului și a asociației din care face parte
                    -Contoare: -permite actualizarea contoarelor de apă
                    -Cheltuieli: -fereastră cu tabelul de avizier ce conține prețurile pentru fiecare utilitate (unde e cazul),
                            cheltuielile pentru apartamentul proprietarului curent și totalele/asociație/fiecare utilitate
                            -calendar cu data/datele de plată
                    -Sesizări: -dialog între administrator și locatari
                    -Setări cont: -permite modificarea parolei
                                -deconectare
    NewAccount: -fereastră de informare cu privire la crearea contului
                -conturile sunt create de administrator, datele conturilor urmând să fie distribuite de acesta către locatari
    NewAccountAdmin: -permite crearea unui cont de administrator
      NewAssociation: -permite crearea unei asociații

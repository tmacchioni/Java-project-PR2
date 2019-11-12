# Java-project-PR2
Bachelor Course: Programmazione 2

Le due classi _HashMapSecureDataContainer_ e _ArrayListSecureData_ descritte qui di seguito sono state pensate per operare analogamente ad un Data Storage per la memorizzazione e condivisione di dati di qualsiasi tipo tra utenti.
Entrambe implementano l'interfaccia _SecureDataContainer_ e garantiscono un meccanismo di sicurezza dei dati, opportunamente criptati, e di gestione e controllo delle identità degli utenti affinché ogni proprietario possa eseguire una restrizione selettiva di accesso ai propri dati inseriti nella collezione.
Lo scopo di questa relazione non sarà tanto quello di descrivere i singoli metodi che accomunano le due implementazioni (visionabili nei files allegati), quanto quello di fornire una loro visione complessiva e le loro principali differenze.

## Eccezioni
I principali errori che si possono riscontrare a tempo di esecuzione sono stati gestiti realizzando, per entrambe le implementazioni, tre classi che estendono la classe _Exception_ (fornita dalla libreria di Java).
Le eccezioni (_Checked_) che potrebbero essere sollevate sono le seguenti :
* **IncorrectDataException** : qualora si cerchi un utente non presente nella collezione, la password non sia corretta oppure si ricerchi un dato non presente nella collezione di un utente (solo in alcuni metodi). Viene lanciata l'eccezione anche nel caso in cui si condivida un dato con se stessi (metodo share).
* **NullFieldException** : qualora i parametri di un metodo siano stringhe vuote oppure oggetti null.
* **UserAlreadyExistException** : qualora si provi ad aggiungere un utente già presente nella collezione.

## HashMapSecureDataContainer
In questa prima implementazione si è scelto di utilizzare la struttura dati _HashMap_ associando chiavi di tipo _String_ per memorizzare i nomi dei singoli utenti con valori di tipo _Data<E>_.
  
  La classe _Data<E>_ ha il compito di memorizzare la password e la collezione dei dati dell'utente tramite due variabili di istanza: _password_ (di tipo _String_) e _storage_ (di tipo _ArrayList_).
  
Quest'ultimo ("_storage_") merita particolare attenzione in quanto consente di estrapolare, seppur superficialmente, il metodo con il quale è stato realizzato il meccanismo di cifratura dei dati: la variabile infatti è stata dichiarata di tipo _ArrayList<EncryptedData<E>>_ .
  
Si desume quindi che i singoli oggetti non sono accessibili direttamente tramite una semplice estrazione dall' array ma sono "incapsulati" all'interno di un oggetto di tipo _EncryptedData<E>_.
  
Un'istanza di tipo _EncryptedData<E>_ ha il mero compito di memorizzare un oggetto di qualsiasi tipo (_E obj_) e di associare a questo una chiave (_String key_).
Per la memorizzazione verrà fornita una stringa e l'oggetto da "incapsulare" ad un metodo della classe in questione. Tramite poi un opportuno algoritmo (nel nostro caso abbiamo optato per il cifrario di Cesare con uno shift di 3) la stringa verrà cifrata ed utilizzata come chiave.
  
Per la lettura verrà fornita una stringa e se a seguito della decifratura di questa sarà equivalente alla chiave della variabile d'istanza _key_ allora verrà restituito l'oggetto "incapsulato".

La classe _EncryptedData<E>_ è stata dichiarata _private_ e _final_ ed implementata come inner-class all'interno di _Data<E>_ in modo da poter preservare un certo grado di sicurezza e al contempo poter essere utilizzata da quest'ultima.
  
Anche per la classe _Data<E>_, per le solite ragioni sopra evidenziate, è stata dichiarata private ed inserita all'interno di _HashMapSecureDataContainer_.
  
Infine è bene notare che per realizzare il metodo getIterator in _HashMapSecureDataContainer_ si è fatto uso di un iteratore personalizzato (senza _remove_) dichiarato all'interno di _Data<E>_.
  
## ArrayListSecureDataContainer
In questa implementazione si è scelto di utilizzare la struttura dati _ArrayList_, la quale contiene un numero finito di istanze di classe _User<E>_.
  
E' facile intuire che quest'ultima conterrà i dati di un singolo utente: id, password e la personale collezione di dati.

Affinché venga realizzato un meccanismo di cifratura dei dati e per garantire una certa sicurezza, la collezione dei dati è stata dichiarata di tipo _ArrayList<EncryptedData<E>>_ e la classe _User<E>_ è stata dichiarata all'interno della classe _ArrayListSecureDataContainer_.
  
Dunque il processo di cifratura dei dati e l'utilizzo di un iteratore personalizzato avviene in modo speculare all'implementazione _HashMapSecureData_, precedentemente esposta.

## Considerazioni finali
In entrambe le implementazioni sono state effettuate scelte progettuali in aggiunta a quelle finora descritte senza violare il contratto d'uso col cliente:
* L'inserimento dell'id in lettere maiuscole, minuscole o miste come parametro di un qualsiasi metodo non è vincolante ai fini dell'inserimento o della ricerca di un utente nella collezione in quanto, prima di qualsiasi operazione, la stringa relativa al nome utente viene forzatamente convertita in lettere minuscole.
* La condivisione di dati tra utenti avviene aggiungendo l'oggetto nella collezione dell'utente destinatario.

Se volessimo argomentare le differenze tra le due scelte, sorge spontaneo evidenziare che l'utilizzo dell'implementazione tramite _HashMap_ pone maggior attenzione alla sicurezza grazie alla funzione di hashing realizzata nativamente dalla libreria di Java, ma è altrettanto vero che per quanto concerne la progettazione la seconda scelta risulta più semplice ed intuitiva.

## Batterie di Test
Le due classi _TestHashMap_ e _TestArrayList_ operano in egual modo: dopo aver inserito due utenti ed aggiunto alcuni oggetti di vario tipo nelle rispettive collezioni, mostrano il corretto funzionamento di ogni singolo metodo e la corretta gestione dell'eccezioni, il tutto opportunamente commentato in modo da esplicitare la singola operazione.
Da notare infine che a prescindere da quale implementazione viene scelta dal cliente, l'utilizzo dei metodi ed il risultato ottenuto rimane invariato.

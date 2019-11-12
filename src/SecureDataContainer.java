import java.util.Iterator;

public interface SecureDataContainer<E> {
    //Overview: Tipo modificabile che rappresenta una collezione per memorizzare e condividere dati tra utenti
    //          con un proprio meccanismo di accesso e sicurezza.
    //
    //Typical Element: { (<id_0, psw_0>, data_0) , (<id_1, psw_1>, data_1) ... (<id_n, psw_n>, data_n) }
    //                  insieme di coppie formate da una coppia (id, password) ed una collezione di oggetti di
    //                  qualsiasi tipo tale che for all i, j. 0 <= i < j < size() ==> id_i != id_j



    // Crea l’identità un nuovo utente della collezione
    public void createUser(String id, String psw) throws UserAlreadyExistException, NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (id NON presente nella collezione)
        THROWS: NullFieldException se (id || psw  == null) || (id || psw == stringa vuota) (eccezione checked)
                UserAlreadyExistException se l'id è già presente nella collezione (eccezione checked)
        MODIFIES: this
        EFFECTS: inserisce la coppia (<id, psw>, data)
     */

    // Rimuove l’utente dalla collezione
    public void removeUser(String id, String psw) throws IncorrectDataException, NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota)  (eccezione checked)
                IncorrectDataException se l'id NON è presente || le password NON coincidono (eccezione checked)
        MODIFIES: this
        EFFECTS: rimuove la coppia (<id, psw>, data)
     */

    // Restituisce il numero degli elementi di un utente presenti nella collezione
    public int getSize(String id, String psw) throws IncorrectDataException, NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota)  (eccezione checked)
                IncorrectDataException se l'id NON è presente || le password NON coincidono (eccezione checked)
        EFFECTS: restituisce data.size() della coppia (<id, psw>, data)
     */

    // Inserisce il valore del dato nella collezione se vengono rispettati i controlli di identità
    public boolean put(String id, String psw, E data) throws NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (data != null) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota) || (data == null) (eccezione checked)
        MODIFIES: this
        EFFECTS: aggiunge l'elemento 'data' nella collezione dell'utente id
     */

    // Ottiene una copia del valore del dato nella collezione se vengono rispettati i controlli di identità
    public E get(String id, String psw, E data) throws NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (data != null) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota) || (data == null) (eccezione checked)
        EFFECTS: restituisce una copia del dato nella collezione dell'utente id
     */

    // Rimuove il dato nella collezione se vengono rispettati i controlli di identità
    public E remove(String id, String psw, E data) throws NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (data != null) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota) || (data == null) (eccezione checked)
        MODIFIES: this
        EFFECTS: rimuove il dato nella collezione dell'utente id
     */

    // Crea una copia del dato nella collezione se vengono rispettati i controlli di identità
    public void copy(String id, String psw, E data) throws NullFieldException, IncorrectDataException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vuota) & (data != null) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota) || (data == null) (eccezione checked)
                IncorrectDataException se l'id NON è presente || le password NON coincidono || il dato NON è presente (eccezione checked)
        MODIFIES: this
        EFFECTS: aggiunge una copia dell'elemento 'data' nella collezione dell'utente id
     */

    // Condivide il dato nella collezione con un altro utente se vengono rispettati i controlli di identità
    public void share(String id, String psw, String Other, E data) throws NullFieldException, IncorrectDataException;
    /*  REQUIRES: (id & psw & Other != null) & (id & psw & Other != stringa vuota) & (data != null) & (id presente nella collezione) & (psw coincidono) & (Other presente nella collezione)
        THROWS: NullFieldException se (id || psw || Other == null) || (id || psw || Other == stringa vuota) || (data == null)(eccezione checked)
                IncorrectDataException se l'id NON è presente || Other NON è presente || le password NON coincidono || il dato NON è presente (eccezione checked)
        MODIFIES: this
        EFFECTS: condivide il dato 'data' appartente a 'id' nella collezione dell'utente 'Other'
     */

    // restituisce un iteratore (senza remove) che genera tutti i dati dell’utente in ordine arbitrario se vengono rispettati i controlli di identità
    public Iterator<E> getIterator(String id, String psw) throws IncorrectDataException, NullFieldException;
    /*  REQUIRES: (id & psw != null) & (id & psw != stringa vutota) & (id presente nella collezione) & (psw coincidono)
        THROWS: NullFieldException se (id || psw == null) || (id || psw == stringa vuota)) (eccezione checked)
                IncorrectDataException se l'id non è presente || le password NON coincidono (eccezione checked)
        EFFECTS: restituisce un iteratore (senza remove) che genera tutti i dati dell’utente id
     */

}

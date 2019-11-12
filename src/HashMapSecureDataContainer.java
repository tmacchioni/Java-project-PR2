import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapSecureDataContainer<E> implements SecureDataContainer<E> {
    //AF: a(HashMapSecureDataContainer this) = f : K -> V tale che per ogni k se AccountContainer.get(k) != null => f(k) = v con v=AccountContainer.get(k) e indefinita altrimenti
    // e dove v = (password, storage) tale che storage = g : [0...storage.size()-1] -> E tale che per ogni i se storage.get(i) != null per 0 <= i < storage.size() => g(i) = e con e=storage.get(i)
    // e dove e = (key, obj)
    //
    //IR: (AccountContainer != null) && ( per ogni k t.c. AccountContainer.get(k) != null ==> ( k.isBlank() == false ) && ( k.equals(k.toLowerCase()) )


    private HashMap<String, Data<E>> AccountContainer;


    public HashMapSecureDataContainer(){
        this.AccountContainer = new HashMap<>();
    }

    public void createUser(String id, String psw) throws UserAlreadyExistException, NullFieldException {

        if(id == null || id.isBlank() || psw == null || psw.isBlank() )
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase(); //ogni utente verrà salvato in lettere minuscole

        //se l'username esiste già nella collezione => lancia un'eccezione
        //altrimenti lo aggiunge
        if(AccountContainer.containsKey(newId)) {
            throw new UserAlreadyExistException("'"+newId+"' is already exist.");
        } else {
            AccountContainer.put(newId, new Data<>(psw)); //aggiunge il nuovo account
        }
    }

    public void removeUser(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String delId = id.toLowerCase();
        Data delData = AccountContainer.get(delId); //se l'id è stato trovato delData != null

        //se delData != null & le password coincidono => viene rimosso l'account ed i suoi dati dalla collezione
        //altrimenti avvisa che l'username o la password non sono corretti
        if((delData != null) && (delData.pswIsEqual(psw))){
            AccountContainer.remove(delId);
            System.out.println("'"+delId+"' has been successfully deleted from the collection.");
        } else {
            throw new IncorrectDataException("Username or Password are not correct.");
        }

    }

    public int getSize(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato tmpData != null

        //se tmpData != null & le password coincidono => restituisce il numero di dati salvati dall'utente
        //altrimenti avvisa che l'username o la password non sono corretti
        if((tmpData != null) && (tmpData.pswIsEqual(psw))){
            return tmpData.getStorageSize(psw);
        } else {
            throw new IncorrectDataException("Username or Password are not correct.");
        }
    }

    public boolean put(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data<E> tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato delData != null

        //se tmpData != null & le password coincidono => inserisce il dato nella collezione & return true
        //altrimenti return false
        if((tmpData != null) && (tmpData.pswIsEqual(psw))){
            AccountContainer.get(tmpId).put(data);
            return true;
        } else {
            return false;
        }

    }

    public E get(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data<E> tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato tmpData != null
        E elm = tmpData.getElement(psw, data); //se 'data' è stato trovato & password coincidono -> elm != null

        if(elm != null) return elm;
        else {
            return null;
        }

    }

    public E remove(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data<E> tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato tmpData != null
        E delElm = tmpData.getElement(psw, data); //se 'data' è stato trovato tmpElm != null

        //se tmpData != null & le password coincidono & tmpElm != null => rimuove il dato nella collezione
        //altrimenti return null
        if ((tmpData != null) && (tmpData.pswIsEqual(psw)) && (delElm != null)) {
            return AccountContainer.get(tmpId).remove(psw, delElm);
        } else {
            return null;
        }
    }

    public void copy(String id, String psw, E data) throws NullFieldException, IncorrectDataException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data<E> tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato tmpData != null
        E tmpElm = tmpData.getElement(psw, data); //se 'data' è stato trovato tmpElm != null

        //se tmpData != null & le password coincidono & tmpElm != null => copia il dato nella collezione
        //altrimenti lancia un'eccezione
        if((tmpData != null) && (tmpData.pswIsEqual(psw)) && (tmpElm != null)){
            tmpData.put(tmpElm);
        } else {
            throw new IncorrectDataException("One or more fields are not correct.");
        }

    }

    public void share(String id, String psw, String Other, E data) throws NullFieldException, IncorrectDataException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || Other == null || Other.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId1 = id.toLowerCase();
        String tmpId2 = Other.toLowerCase();

        Data<E> tmpData1 = AccountContainer.get(tmpId1); //se l'id è stato trovato tmpData1 != null
        Data<E> tmpData2 = AccountContainer.get(tmpId2); //se l'id è stato trovato tmpData2 != null

        E tmpElm = tmpData1.getElement(psw, data); //se 'data' è stato trovato tmpElm != null

        //se tmpData1 != null & tmpData2 != null & le password coincidono
        // & tmpElm != null & id diversi => aggiunge il dato nella collezione di 'Other'
        //altrimenti lancia un'eccezione
        if((tmpData1 != null) && (tmpData1.pswIsEqual(psw)) && (tmpElm != null) && (tmpId1.compareTo(tmpId2) != 0)){
            tmpData2.put(tmpElm);
        } else {
            throw new IncorrectDataException("One or more fields are not correct.");
        }

    }

    public Iterator<E> getIterator(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String tmpId = id.toLowerCase();
        Data tmpData = AccountContainer.get(tmpId); //se l'id è stato trovato tmpData != null

        //se tmpData != null e le password corrispondono, restituisce il numero di dati salvati dall'utente
        //altrimenti avvisa che l'username o la password non sono corretti
        if((tmpData != null) && (tmpData.pswIsEqual(psw))){
            return tmpData.getIterator(psw);
        } else {
            throw new IncorrectDataException("Username or Password are not correct.");
        }
    }


    private class Data<E>{
        //IR: (password != null) && (password.isBlank() == false) && (storage != null)

        private String password;
        private ArrayList<EncryptedData<E>> storage;

        public Data(String psw){
            this.password = psw;
            this.storage = new ArrayList<>();
        }


        public boolean pswIsEqual(String psw) {

            if(psw == null || psw.isBlank())
                throw new IllegalArgumentException();

            return (this.password.compareTo(psw) == 0) ? true : false ;
        }

        public boolean put(E elm){

            if(elm == null)
                return false;

            EncryptedData e = new EncryptedData(this.password, elm);
            this.storage.add(e);
            return true;
        }

        public E remove(String psw, E elm){

            if(psw == null || psw.isBlank() || elm == null)
                throw new IllegalArgumentException();

            if(this.password.compareTo(psw) == 0 ){
                Iterator itr = this.storage.iterator();

                while(itr.hasNext()){
                    EncryptedData<E> cElm = (EncryptedData<E>) itr.next();
                    E dElm = cElm.unlock(psw);
                    if(dElm.equals(elm)){
                        this.storage.remove(cElm);
                        return dElm;
                    }
                }

                return null;
            }

            return null;
        }

        public int getStorageSize(String psw) {

            if(psw == null || psw.isBlank())
                throw new IllegalArgumentException();

            return (this.password.compareTo(psw) == 0) ? storage.size() : -1;

        }

        public E getElement(String psw, E elm){

            if(psw == null || psw.isBlank() || elm == null)
                throw new IllegalArgumentException();

            if(this.password.compareTo(psw) == 0 ){

                Iterator itr = this.storage.iterator();
                while(itr.hasNext()){
                    EncryptedData<E> cElm = (EncryptedData<E>) itr.next();
                    E dElm = cElm.unlock(psw);
                    if(dElm.equals(elm)){
                        return dElm;
                    }
                }

                return null;
            }

            return null;

        }

        public Itr<E> getIterator(String psw){

            if(psw == null || psw.isBlank())
                throw new IllegalArgumentException();

            if(this.password.compareTo(psw) == 0 ) {

                return new Itr<E>(this.password, this.storage);
            }

            return null;
        }

        private class Itr<E> implements Iterator<E> {
            private int cursor;       // index of next element to return
            private String key;
            private ArrayList<EncryptedData<E>> tmpStorage;


            // prevent creating a synthetic constructor
            Itr(String key, ArrayList<EncryptedData<E>> tmpStorage) {
                this.key = key;
                this.tmpStorage = tmpStorage;
            }

            public boolean hasNext() {
                return cursor != tmpStorage.size();
            }

            @SuppressWarnings("unchecked")
            public E next() {
                int i = cursor;
                if (i >= tmpStorage.size())
                    throw new NoSuchElementException();
                EncryptedData<E> elementData =  tmpStorage.get(i);
                E tmpData = elementData.unlock(key);
                cursor = i + 1;
                return tmpData;
            }

        }

        private final class EncryptedData<E> {
            //IR: key != null && key.isBlank() == false && obj != null

            private final String key;
            private final E obj;

            public EncryptedData(String key, E obj){

                if(key == null || key.isBlank() || obj == null)
                    throw new IllegalArgumentException();

                this.key = this.encryptKey(key);
                this.obj = obj;

            }

            public E unlock(String key){

                if(key == null || key.isBlank())
                    throw new IllegalArgumentException();

                return (this.decryptKey(this.key).compareTo(key) == 0) ? this.obj : null;

            }


            //Cripta key utilizzando il cifrario di Cesare con uno shift di 3
            private String encryptKey(String key){

                StringBuffer newKey= new StringBuffer();

                for (int i=0; i<key.length(); i++)
                {
                    char ch = (char)(((int)key.charAt(i) + 3));
                    newKey.append(ch);
                }
                return newKey.toString();
            }

            //Decripta key utilizzando il cifrario di Cesare con uno shift di 3
            private String decryptKey(String key){

                StringBuffer newKey = new StringBuffer();

                for (int i=0; i<key.length(); i++)
                {
                    char ch = (char)(((int)key.charAt(i) - 3));
                    newKey.append(ch);
                }
                return newKey.toString();
            }


        }

    }





}

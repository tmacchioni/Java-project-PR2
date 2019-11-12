import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListSecureDataContainer<E> implements SecureDataContainer<E>{
    //AF: a(ArrayListSecureDataContainer this) = f : [0...AccountContainer.size()-1] -> V tale che f(i) = AccountContainer.get(i) se AccountContainer.get(i) != null, e indefinito altrimenti
    // e dove AccountContainer.get(i) = (id, psw, storage) tale che storage = g : [0...storage.size()-1] -> E tale che per ogni j se storage.get(j) != null per 0 <= j < storage.size() => g(j) = e con e=storage.get(j)
    // e dove e = (key, obj)
    //
    //IR: (AccountContainer != null) && (forall i,j. 0 <= i,j < AccountContainer.size() && i != j => AccountContainer.get(i) != AccountContainer.get(j) )


    private ArrayList<User> AccountContainer;


    public ArrayListSecureDataContainer() {
        this.AccountContainer = new ArrayList<>();
    }

    public void createUser(String id, String psw) throws UserAlreadyExistException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        if (this.indexOfId(newId) == -1)
            AccountContainer.add(new User(newId, psw));
        else {
            throw new UserAlreadyExistException("'" + newId + "' is already exist.");
        }

    }

    public void removeUser(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String delId = id.toLowerCase();

        int ind = this.indexOfId(delId);

        if(ind != -1){
            User<E> tmpUser = this.AccountContainer.get(ind);
            if(tmpUser.pswIsEqual(psw) == true){
                System.out.println("'" + delId + "' has been successfully deleted from the collection.");
                this.AccountContainer.remove(ind);
            } else{
                throw new IncorrectDataException("Username or Password are not correct.");
            }
        } else{
            throw new IncorrectDataException("Username or Password are not correct.");
        }

    }

    public int getSize(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1){
            User<E> tmpUser = this.AccountContainer.get(ind);
            if(tmpUser.pswIsEqual(psw) == true){
                return tmpUser.getStorageSize(psw);
            } else{
                throw new IncorrectDataException("Username or Password are not correct.");
            }
        } else{
            throw new IncorrectDataException("Username or Password are not correct.");
        }

    }

    public boolean put(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1) {
            User<E> tmpUser = this.AccountContainer.get(ind);
            if (tmpUser.pswIsEqual(psw) == true) {
                tmpUser.put(data);
                return true;
            }
        }

        return false;

    }

    public E get(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1) {
            User<E> tmpUser = this.AccountContainer.get(ind);
            if (tmpUser.pswIsEqual(psw) == true) {
                E elm = tmpUser.getElement(psw, data);
                if(elm != null) return elm;
            }
        }

        return null;

    }

    public E remove(String id, String psw, E data) throws NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1) {
            User<E> tmpUser = this.AccountContainer.get(ind);
            if (tmpUser.pswIsEqual(psw) == true) {
                E elm = tmpUser.getElement(psw, data);
                if(elm != null) {
                    tmpUser.remove(psw, elm);
                    return elm;
                }
            }
        }

        return null;

    }

    public void copy(String id, String psw, E data) throws NullFieldException, IncorrectDataException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1) {
            User<E> tmpUser = this.AccountContainer.get(ind);
            if (tmpUser.pswIsEqual(psw) == true) {
                E elm = tmpUser.getElement(psw, data);
                if(elm != null) {
                    tmpUser.put(elm);
                } else{
                    throw new IncorrectDataException("One or more fields are not correct.");
                }
            } else{
                throw new IncorrectDataException("One or more fields are not correct.");
            }
        } else {
            throw new IncorrectDataException("One or more fields are not correct.");
        }
    }

    public void share(String id, String psw, String Other, E data) throws NullFieldException, IncorrectDataException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank() || Other == null || Other.isBlank() || data == null)
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();
        String otherId = Other.toLowerCase();

        if(newId.compareTo(otherId) == 0 ) throw new IncorrectDataException("One or more fields are not correct.");

        int indInd = this.indexOfId(newId);
        int indOther = this.indexOfId(otherId);


        if(indInd != -1) {
            User<E> tmpUser = this.AccountContainer.get(indInd);
            if (tmpUser.pswIsEqual(psw) == true) {
                E elm = tmpUser.getElement(psw, data);
                if(elm != null) {
                    if(indOther != -1){
                        tmpUser = this.AccountContainer.get(indOther);
                        tmpUser.put(elm);
                    } else{
                        throw new IncorrectDataException("One or more fields are not correct.");
                    }
                } else{
                    throw new IncorrectDataException("One or more fields are not correct.");
                }
            } else {
                throw new IncorrectDataException("One or more fields are not correct.");
            }
        } else{
            throw new IncorrectDataException("One or more fields are not correct.");
        }

    }

    public Iterator<E> getIterator(String id, String psw) throws IncorrectDataException, NullFieldException {

        if ((id == null) || id.isBlank() || (psw == null) || psw.isBlank())
            throw new NullFieldException("All parameters must be different than 'null' and empty string.");

        String newId = id.toLowerCase();

        int ind = this.indexOfId(newId);

        if(ind != -1) {
            User<E> tmpUser = this.AccountContainer.get(ind);
            if (tmpUser.pswIsEqual(psw) == true) {
                return tmpUser.getIterator(psw);
            }
        }

        throw new IncorrectDataException("One or more fields are not correct.");

    }

    //Restituisce l'indice nella collezione dell'utente 'id', -1 se non è presente
    private int indexOfId(String id){

        if(id == null || id.isBlank())
            throw new IllegalArgumentException();

        Iterator itr = AccountContainer.iterator();
        User<E> tmpUser;
        int i = 0;

        while (itr.hasNext()) {
            tmpUser = (User) itr.next();
            if(tmpUser.idIsEqual(id)) return i;
            i++;
        }

        return -1;
    }


    private class User<E> {
        //IR: (id != null) && (id.isBlank() == false) && (psw != null) && (psw.isBlank() == false) && (storage != null)

        private String id;
        private String psw;
        private ArrayList<EncryptedData<E>> storage;

        public User(String id, String psw) throws NullFieldException {

            if(id == null || id.isBlank() || psw == null || psw.isBlank())
                throw new NullFieldException("All parameters must be different than 'null' and empty string.");

            this.id = id;
            this.psw = psw;
            this.storage = new ArrayList<>();
        }

        public boolean idIsEqual(String id) {

            if(id == null || id.isBlank() )
                throw new IllegalArgumentException();

            return (this.id.compareTo(id) == 0) ? true : false ;
        }

        public boolean pswIsEqual(String psw) {

            if(psw == null || psw.isBlank())
                throw new IllegalArgumentException();

            return (this.psw.compareTo(psw) == 0) ? true : false ;
        }

        public boolean put(E elm){

            if(elm == null)
                return false;

            EncryptedData e = new EncryptedData(this.psw, elm);
            this.storage.add(e);
            return true;
        }

        public E remove(String psw, E elm){

            if(psw == null || psw.isBlank() || elm == null)
                throw new IllegalArgumentException();

            if(this.psw.compareTo(psw) == 0 ){
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

            if(psw == null || psw.isBlank() )
                throw new IllegalArgumentException();

            return (this.psw.compareTo(psw) == 0) ? storage.size() : -1;

        }

        public E getElement(String psw, E elm){

            if(psw == null || psw.isBlank() || elm == null)
                throw new IllegalArgumentException();

            if(this.psw.compareTo(psw) == 0 ){

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

        public Iterator<E> getIterator(String psw){

            if(psw == null || psw.isBlank())
                throw new IllegalArgumentException();

            if(this.psw.compareTo(psw) == 0 ) {

                return new Itr<E>(this.psw, this.storage);
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



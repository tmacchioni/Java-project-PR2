import java.util.Arrays;
import java.util.Iterator;

public class TestArrayList {

    public static void main(String[] args) {


        /********************  ArrayListSecureDataContainer ********************/

         ArrayListSecureDataContainer arrayCnt = new ArrayListSecureDataContainer();

        //Aggiunta di 3 Utenti in arrayCnt
        try {
            arrayCnt.createUser("Utente1","J2eC=[#)Bf");
            System.out.println("Utente1 inserito correttamente");
            arrayCnt.createUser("Utente2","XpK@3^x#Xq9dRX}");
            System.out.println("Utente2 inserito correttamente\n");
        } catch(Exception e) {
            System.out.println(e);
        }

        //'Utente1' è già presente in arrayCnt => lancia eccezione UserAlreadyExistException
        try {
            arrayCnt.createUser("Utente1","G38LVtn07u");
        } catch (Exception e) {
            System.out.println("**** Si prova ad aggiungere un utente già presente ****");
            System.out.println(e);
            System.out.println("*******************************************************\n");
        }

        //Creazione di 5 dati qualsiasi
        String obj1 = "Sono una stringa";
        Integer obj2 = 42;
        char obj3 = 'x';
        int[] obj4 = {1, 2, 3, 4};
        String obj5 = null;

        //'Utente1' = (obj1,obj3) ; 'Utente2' = (obj2,obj4)
        try{
            arrayCnt.put("Utente1","J2eC=[#)Bf",obj1);
            arrayCnt.put("Utente1","J2eC=[#)Bf",obj3);
            arrayCnt.put("Utente2","XpK@3^x#Xq9dRX}",obj2);
            arrayCnt.put("Utente2","XpK@3^x#Xq9dRX}",obj4);
        } catch (Exception e){
            System.out.println(e);
        }

        //Se un utente aggiunge un elemento 'null' => lancia eccezione NullFieldException
        try{
            arrayCnt.put("Utente1","J2eC=[#)Bf",obj5);
        } catch (Exception e){
            System.out.println("*** 'Utente1' prova ad aggiungere un elemento 'null' ***");
            System.out.println(e);
            System.out.println("********************************************************\n");

        }

        //Se un campo di un metodo è una stringa vuota => lancia eccezione NullFieldException (da notare che accetta il nome utente in minuscolo)
        try{
            arrayCnt.put("utente1","",obj2);
        } catch (Exception e){
            System.out.println("*** Se un campo di un metodo è una stringa vuota ***");
            System.out.println(e);
            System.out.println("****************************************************\n");
        }

        //Copiamo un elemento di 'Utente1'
        try{
            arrayCnt.copy("Utente1","J2eC=[#)Bf",obj1);
        } catch (Exception e){
            System.out.println(e);
        }

        //Iteratore su 'Utente1' per stampare ogni elemento
        try{
            Iterator itr = arrayCnt.getIterator("Utente1","J2eC=[#)Bf");
            System.out.println("******** Oggetti di 'Utente1' ********");
            while(itr.hasNext()){
                System.out.println(itr.next());
            }
            System.out.println("**************************************\n");
        }catch(Exception e){
            System.out.println(e);
        }

        //Stampa numeri degli elementi nella collezione di 'Utente1'
        try{
            System.out.println("****** Numero dati di 'Utente1' ******");
            System.out.println(arrayCnt.getSize("Utente1","J2eC=[#)Bf"));
            System.out.println("**************************************\n");
        } catch(Exception e){
            System.out.println(e);
        }

        //Rimuove un dato presente nella collezione di 'Utente2'
        try{
            arrayCnt.remove("utente2","XpK@3^x#Xq9dRX}",obj2);
        } catch (Exception e){
            System.out.println(e);
        }

        //Se proviamo a rimuoverlo di nuovo, non essendo più presente, restituisce 'null'
        try{
            System.out.println("**** Se rimuovo un dato che non ha 'Utente2' ****");
            System.out.println(arrayCnt.remove("utente2","XpK@3^x#Xq9dRX}",obj2));
            System.out.println("*************************************************\n");
        } catch (Exception e){
            System.out.println(e);
        }

        //Otteniamo una copia del valore del dato nella collezione e per prova stampiamo la lunghezza di 'obj4' (è un Double[])
        try{
            System.out.println("******* Dopo una get verifico che mi restituisca l'oggetto desiderato *******");
            int[] array = (int[]) arrayCnt.get("utente2","XpK@3^x#Xq9dRX}", obj4);
            System.out.println(Arrays.toString(array));
            System.out.println("*****************************************************************************\n");
        } catch (Exception e){
            System.out.println(e);
        }

        //Condividiamo un elemento di 'Utente1' in 'Utente2' e verifichiamo che sia effettivamente presente
        try{
            System.out.println("****** Verifica che funzioni la share di un dato di 'Utente1' in 'Utente2' ******");
            arrayCnt.share("Utente1","J2eC=[#)Bf","utente2",obj1);
            System.out.println(arrayCnt.get("utente2","XpK@3^x#Xq9dRX}", obj1));
            System.out.println("*********************************************************************************\n");
        } catch (Exception e){
            System.out.println(e);
        }

        //Se proviamo a condividere un elemento di un utente con se stesso => lancia eccezione NullFieldException
        try{
            arrayCnt.share("Utente1","J2eC=[#)Bf","utente1",obj1);
        } catch (Exception e){
            System.out.println("******* Condivido un dato di 'Utente1' in 'Utente1' *******");
            System.out.println(e);
            System.out.println("***********************************************************\n");
        }

        //Rimuovo 'Utente1' e 'Utente2' dalla collezione
        try{
            System.out.println("****** Rimuovo 'Utente1' e 'Utente2' dalla collezione ******");
            arrayCnt.removeUser("utente1","J2eC=[#)Bf");
            arrayCnt.removeUser("utente2","XpK@3^x#Xq9dRX}");
            System.out.println("************************************************************\n");
        } catch (Exception e){
            System.out.println(e);
        }

        //Posso reinserire un utente con id='Utente1' con la solita psw
        try{
            System.out.println("****** Verifico che possa reinserire un utente con id ='Utente1' ******");
            arrayCnt.createUser("utente1","J2eC=[#)Bf");
            System.out.println("Utente1 inserito correttamente");
            System.out.println("***********************************************************************\n");

        } catch (Exception e){
            System.out.println(e);
        }




    }
}

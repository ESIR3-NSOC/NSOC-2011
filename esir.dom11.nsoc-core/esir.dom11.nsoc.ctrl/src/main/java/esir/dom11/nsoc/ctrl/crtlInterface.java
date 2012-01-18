package esir.dom11.nsoc.ctrl;

/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 16/01/12
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public interface crtlInterface {
    // Ctrl qui envoie vers l'IHM
	//Send2... vont permettre d'envoyer les donn�es vers les diff�rents composants
	
	//IHM besoin de data + ...
    void send2IHM(Data data);
    
    //Envoie de tout ce qui a pu �tre modifi�.
    void send2DAO(Data data );
    void send2DAO(Command command);
    
    //Envoie d'une liste d'actions (= command) et Conflict s'occupe de voir si il n'y a pas de conflict
    void send2Conflict(Command command);
    
    // Demande d'une variable aupr�s du contexte (ex : capteurs temperature chambre )
    Data send2Context(DataType datatype);
    
    //Receive... vont permettre de recevoir les donn�es des diff�rents composants
    //De type listeners (automatiquement)
    
    //On re�oit une requ�te de la part des IHM pour avoir des donn�es
    void receiveIHM();
    //On re�oit une requ�te de la part du conflict manager pour d'autres donn�es.
    void receiveConflict();
    
    //getContext = demande de notre part du contexte
    void getContext();

}

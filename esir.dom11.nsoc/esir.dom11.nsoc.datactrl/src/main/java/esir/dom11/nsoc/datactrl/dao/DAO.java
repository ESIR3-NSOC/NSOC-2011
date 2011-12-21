package esir.dom11.nsoc.datactrl.dao;

public interface DAO<TId, TObject> {

    public TObject create(TObject tObject);

    public TObject retrieve(TId tId);

    public TObject update(TObject tObject);

    public boolean delete(TId tId);
}
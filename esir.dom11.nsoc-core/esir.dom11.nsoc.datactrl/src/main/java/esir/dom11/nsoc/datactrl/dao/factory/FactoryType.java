package esir.dom11.nsoc.datactrl.dao.factory;

public enum FactoryType {
    DAO_MYSQL,
    DAO_MONGODB,
    DAO_SQLITE,
    DAO_DB4O;

    public String getValue() {
        return this.name();
    }
}

package esir.dom11.nsoc.datactrl.dao.factory;

public enum FactoryType {
    DAO_MYSQL,
    DAO_MONGODB;

    public String getValue() {
        return this.name();
    }
}

package esir.dom11.nsoc.datactrl.dao.factory;

public enum FactoryType {
    DAO_MYSQL;

    public String getValue() {
        return this.name();
    }
}

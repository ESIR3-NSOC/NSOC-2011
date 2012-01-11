package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.CategoryDAO;
import esir.dom11.nsoc.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CategoryDAOMySQL implements CategoryDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CategoryDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public CategoryDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Category create(Category category) {
        Category newCategory = retrieve(category.getId());
        if (newCategory.getId()==null) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(
                                "INSERT INTO categories (id, name, lock)"
                                        + " VALUES('" + category.getId() + "',"
                                        + " '" + category.getName() + "',"
                                        + " '" + category.getLock() + "')"
                        );
                prepare.executeUpdate();
                newCategory = retrieve(category.getId());
            } catch (SQLException exception) {
                logger.error("Data insert error", exception);
            }
        }
        return newCategory;
    }

    @Override
    public Category retrieve(UUID id) {
        Category category = new Category();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM categories WHERE id = '" + id + "'");
            if(result.first()) {
                category = new Category(id, result.getString("name"),result.getInt("lock"));
            }
        } catch (SQLException exception) {
            logger.error("Category retrieve error", exception);
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(
                            "UPDATE categories SET name = '" + category.getName() + "', lock = '" + category.getLock() + "'"
                    );

            category = this.retrieve(category.getId());
        } catch (SQLException exception) {
            logger.error("Category update error",exception);
        }
        return category;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM categories WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Category delete error",exception);
        }
        return false;
    }
}

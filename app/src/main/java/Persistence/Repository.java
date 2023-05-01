package Persistence;
import com.j256.ormlite.dao.DaoManager;
import Domain_Layer.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;


public abstract class Repository<T> {
    private static ConnectionSource connectionSource;
    private Dao<T, Integer> dao;

    public void init(Class myclass, ConnectionSource c) {
        try {
            connectionSource = c;
            dao = DaoManager.createDao(connectionSource, myclass);

        } catch (Exception e) {
            System.out.println(e);

        }
    }
        public Dao<T, Integer> getDao(){
            return dao;
        }
        public ConnectionSource getConnectionSource() {
            return connectionSource;
        }
        public T guardar(T entity){
            try {
                this.getDao().create(entity);
                return entity;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        public List<T> obtenerTodos(){
            try {
                return this.getDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        public void delete(int id){
            try {
                this.getDao().deleteById(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public T obtener(int id){
            try {
                return this.getDao().queryForId(id);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void actualizar(T t){
            try {
                this.getDao().update(t);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

package Persistence;

import com.j256.ormlite.support.ConnectionSource;
import com.slumdogsustainable.MainActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Ahorcado;
import Domain_Layer.Frase;

public class FraseRepository extends Repository<Frase>{

    public FraseRepository(ConnectionSource c){
        init(Frase.class, c);
    }

    public List<Frase> getListadoFrase() throws SQLException{
        List<Frase> list = this.getDao().queryForAll();
        List<Frase> resultlist = new ArrayList<>();
        for(Frase q : list) resultlist.add(q);
        return resultlist;
    }
}

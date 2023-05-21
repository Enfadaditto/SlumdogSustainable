package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.Frase;

public class FraseRepository extends Repository<Frase>{

    public FraseRepository(ConnectionSource c){
        init(Frase.class, c);
    }

}

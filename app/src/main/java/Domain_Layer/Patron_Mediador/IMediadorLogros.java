package Domain_Layer.Patron_Mediador;

import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User;

public interface IMediadorLogros {

    public void notificarLogroDesbloqueado(Logro logro);

    public void addEnlacesToUser();
}

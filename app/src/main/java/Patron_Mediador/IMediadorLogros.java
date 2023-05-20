package Patron_Mediador;

import Domain_Layer.Logro;
import Domain_Layer.User;

public interface IMediadorLogros {

    public void notificarLogroDesbloqueado(User usuario, Logro logro);

    public void addEnlaces(User user);
}

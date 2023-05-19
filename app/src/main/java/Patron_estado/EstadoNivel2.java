package Patron_estado;

import Domain_Layer.User;

public class EstadoNivel2 extends EstadoNivelJugador{

    User usuarioActual;

    EstadoNivel2(){
        usuarioActual = super.usuarioActual;
    }

    public int subirNivel1(){

        if(usuarioActual.getPointsAchieved() > 500){
           /// usuarioActual.changeEstate(new EstadoNivel1());
            return 1;
        }else{
            System.out.println("------No subio de nivel-----");
        }

        return 0;
    }


    public int subirNivel2(){return -1;}

    public int subirNivel3(){return  -1;}

    public int getNivelActual(){
        return 0;
    }
}

package Patron_estado;

import Domain_Layer.User;

public class EstadoNivel1 extends EstadoNivelJugador{


    User usuarioActual;

    EstadoNivel1(){
        usuarioActual = super.usuarioActual;
    }


    public int subirNivel1(){

       if(usuarioActual.getPointsAchieved() > 1000){

           System.out.println("-------------Tiene mas de 1000 puntos___________________");

           //usuarioActual.changeEstate(new EstadoNivel1());
           return 2;
       }else{
           System.out.println("------No subio de nivel-----");
       }

       return 0;



   }



   public int subirNivel2(){return 2;}

    public int subirNivel3(){return  3;}

    public int getNivelActual(){
       return 1;
    }
}

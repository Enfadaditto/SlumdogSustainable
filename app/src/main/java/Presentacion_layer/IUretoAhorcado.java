package Presentacion_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Domain_Layer.Ahorcado;
import Persistence.SingletonConnection;
import Persistence.UserRepository;


public class IUretoAhorcado extends AppCompatActivity {

    Ahorcado ahorcado;
    String fraseAhorcado;
    String fraseEnBarrasBajas = "";
    TextView texto_fraseADescubir;
    Button botonSeleccionado;
    Button botonSeleccionado1;
    Button botonSeleccionado2;
    Button botonSeleccionado3;
    Button botonSeleccionado4;
    Button botonSeleccionado5;
    Button botonSeleccionado6;
    Button botonSeleccionado7;
    Button botonSeleccionado8;
    Button botonSeleccionado9;
    Button botonSeleccionado10;
    Button botonSeleccionado11;
    Button botonSeleccionado12;
    Button botonSeleccionado13;
    Button botonSeleccionado19;
    Button botonSeleccionado14;
    Button botonSeleccionado15;
    Button botonSeleccionado16;
    Button botonSeleccionado17;
    Button botonSeleccionado18;
    Button botonSeleccionado20;
    Button botonSeleccionado21;
    Button botonSeleccionado22;
    Button botonSeleccionado23;
    Button botonSeleccionado24;
    Button botonSeleccionado25;
    Button botonSeleccionado26;
    ImageView imagenAhorcado;
    ImageView imagenPista;
    ProgressBar timeBar2;
    CountDownTimer mCountDownTimer;
    TextView textoPuntosAcumulados;
    TextView textoPuntosConsolidados;
    TextView textoEnunciado;
    TextView contadorBombillas;
    TextView enunciado;
    ImageView imagenOds;

    TextView textoPuntosAhorcado;
    int vida;
    ProgressBar timeBar;
    TextView textoNumeroDeReto;
    int TiempoOpcion;
    int imax =0;
    int timeCount = 0;
    int Tiempo = 120000;
    int errores = 0;
    char[] letrasEncontradas = new char[20];
    char[] fraseACompletar;
    int cantidadRetosContestados;
    boolean haConsolidado;
    int nivel;
    int SonidoFallo;

    int SonidoAcierto;
    int numeroOds;
    boolean Acierto;
    int puntosTotales;
    Button botonConsolidar;
    ImageView acierto_fallo;
    Button botonSiguientePregunta;
    ImageView imagenPantallaFinal;
    TextView textoPuntosFinales;
    ConstraintLayout contenedor_principal;
    ConstraintLayout lim;
    RelativeLayout contenedor;
    RelativeLayout pantalla_final;
    List<Character> noEncontradas = new ArrayList<>();
    String enunciadoString;
    int puntosConsolidados;
    int contadorAciertos=0;
    TextView textoPuntosGanados;
    TextView textoPuntosTotal;
    ImageView imagenCorazon;
    ImageView botonAbandonar;
    int pistas;
    boolean abandonado;
    boolean haConsolidadoLocal = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto_ahorcado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MainActivity.background.start();
        contenedor_principal = findViewById(R.id.contenedor_principal);
        textoPuntosAhorcado = findViewById(R.id.puntosAhorcado);
        enunciado = (TextView)findViewById(R.id.textoEnunciado);
        imagenAhorcado = findViewById(R.id.imagenAhorcado);
        botonSeleccionado1 = (Button)findViewById(R.id.botonA);
        botonSeleccionado2= (Button)findViewById(R.id.botonB);
        botonSeleccionado3 = (Button)findViewById(R.id.botonC);
        botonSeleccionado4= (Button)findViewById(R.id.botonD);
        botonSeleccionado5 = (Button)findViewById(R.id.botonE);
        botonSeleccionado6= (Button)findViewById(R.id.botonF);
        botonSeleccionado7 = (Button)findViewById(R.id.botonG);
        botonSeleccionado8 = (Button)findViewById(R.id.botonH);
        botonSeleccionado9 = (Button)findViewById(R.id.botonI);
        botonSeleccionado10 = (Button)findViewById(R.id.botonJ);
        botonSeleccionado11 = (Button)findViewById(R.id.botonK);
        botonSeleccionado12 = (Button)findViewById(R.id.botonL);
        botonSeleccionado13 = (Button)findViewById(R.id.botonM);
        botonSeleccionado14 = (Button)findViewById(R.id.botonN);
        botonSeleccionado15 = (Button)findViewById(R.id.botonO);
        botonSeleccionado16 = (Button)findViewById(R.id.botonP);
        botonSeleccionado17 = (Button)findViewById(R.id.botonQ);
        botonSeleccionado18 = (Button)findViewById(R.id.botonR);
        botonSeleccionado19 = (Button)findViewById(R.id.botonS);
        botonSeleccionado20 = (Button)findViewById(R.id.botonT);
        botonSeleccionado21 = (Button)findViewById(R.id.botonU);
        botonSeleccionado22 = (Button)findViewById(R.id.botonV);
        botonSeleccionado23 = (Button)findViewById(R.id.botonW);
        botonSeleccionado24 = (Button)findViewById(R.id.botonX);
        botonSeleccionado25 = (Button)findViewById(R.id.botonY);
        botonSeleccionado26 = (Button)findViewById(R.id.botonZ);
        contadorBombillas = findViewById(R.id.contadorBombillas);
        textoPuntosAcumulados = findViewById(R.id.textoPuntosAcumulados);
        textoEnunciado = findViewById(R.id.textoEnunciado);
        textoPuntosConsolidados = findViewById(R.id.textoPuntosConsolidados);
        textoNumeroDeReto = findViewById(R.id.textoNumeroDeReto);
        imagenOds = findViewById(R.id.imagenOds);
        //imagenPista = findViewById(R.id.imagenPistaPregunta);
        imagenCorazon = findViewById(R.id.imagenVida);
        botonAbandonar = findViewById(R.id.botonAbadonar);
        imagenPista = findViewById(R.id.imagenPista);
        botonConsolidar = findViewById(R.id.botonConsolidar);
        acierto_fallo = findViewById(R.id.imagen_acierto);
        botonSiguientePregunta = findViewById(R.id.botonSiguientePregunta);
        imagenPantallaFinal = findViewById(R.id.imagenPantallaFinal);
        textoPuntosFinales = findViewById(R.id.textoPuntosFinales);
        botonSeleccionado = (Button)findViewById(R.id.botonA);
        texto_fraseADescubir = findViewById(R.id.texto_fraseADescubir);
        contenedor = findViewById(R.id.contenedor_resp);
        imagenPantallaFinal = findViewById(R.id.imagenPantallaFinal);
        pantalla_final = findViewById(R.id.contenedor_final);
        textoPuntosGanados = findViewById(R.id.puntosGanados);
        textoPuntosTotal = findViewById(R.id.puntosTotal);

        cargarDatos();
        System.out.println("Me han creado");
        startTimer(Tiempo);

        ponerTextosEnPantalla();
    }

    private void ponerTextosEnPantalla() {
        botonAbandonar.setVisibility(View.GONE);
        //textoPuntosConsolidados.setVisibility(View.INVISIBLE);
        textoPuntosAhorcado.setText("Puntos reto: " + 100*nivel);
        fraseACompletar = new char [fraseAhorcado.length()];
        for(int i = 0; i<fraseAhorcado.length();i++){
            if(fraseAhorcado.charAt(i) == ' '){
                fraseACompletar[i]= ' ';
            }else{
                fraseACompletar[i]= '_';
            }

           fraseEnBarrasBajas += fraseACompletar[i]+" ";
        }
        texto_fraseADescubir.setText(fraseEnBarrasBajas.trim());

        if(vida==0){
            imagenCorazon.setImageDrawable(getDrawable(R.drawable.corazon_roto));
        }
        if(haConsolidado){
            botonAbandonar.setVisibility(View.VISIBLE);
            textoPuntosConsolidados.setText("Puntos Consolidados: "+ puntosConsolidados);

        }
        enunciado.setText(enunciadoString);
        textoPuntosAcumulados.setText("Puntos: "+puntosTotales);
        ponerFondoPorDificultad();
        textoNumeroDeReto.setText(cantidadRetosContestados+"/10");
       // textoPuntosConsolidados.setText("Puntos consolidados: "+ puntosConsolidados);
        letrasNoEncontradas();
        if(errores > 0){
            ponerImagenAhorcado();
        }
        longitudTexto();
    }
    private void longitudTexto() {
        if (texto_fraseADescubir.length() > 40) {
            texto_fraseADescubir.setTextSize(20);
        }
    }

    public void ponerFondoPorDificultad(){

        if(cantidadRetosContestados>4 && cantidadRetosContestados <= 7){
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_reto_ahorcado_medio));
        }else if (cantidadRetosContestados>7){
            System.out.println("-----------Estuve aqui------------");
            contenedor_principal.setBackground(getDrawable(R.drawable.fondo_reto_ahorcado_dificil));
        }
    }

    public void linkInfoODS(View v) {
        Uri uri = Uri.parse(getResources().getStringArray(R.array.linkODS)[numeroOds]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void startTimer(int t) {
        int tiempo = t;
        MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
        MainActivity.music.start();
        timeCount = tiempo;
        timeBar = findViewById(R.id.timeBar2);
        timeBar.bringToFront();
        timeBar.setMax(tiempo);
        timeBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        timeBar.setProgress(tiempo);
        mCountDownTimer = new CountDownTimer(tiempo, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeCount -= 100;
                if (timeCount == tiempo / 3) {
                    timeBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
                if (timeCount == tiempo * 2 / 3) {
                    timeBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                }
                timeBar.setProgress(timeCount);
            }

            @Override
            public void onFinish() {
                timeCount++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        MainActivity.music.stop();

                        if (tiempo == Tiempo) {
                            //Se acabo el tiempo, marcar como mala
                            terminoMal(1, true);
                        } else if (tiempo == TiempoOpcion) {
                            //puntosTotales = 0;
                           // puntosConsolidados = 0;
                           // imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
                           // textoPuntosFinales.setText("Puntos totales: 0");
                            //quitarPantallaAciertoFallo();
                            botonSiguientePregunta.performClick();


                        }
                    }
                });
            }
        };
        mCountDownTimer.start();
    }

    public void cargarDatos() {
        Bundle extras = getIntent().getExtras();

        fraseAhorcado = extras.getString("palabraAhorcado").toUpperCase();;
        enunciadoString = extras.getString("enunciadoAhorcado");
        puntosTotales = extras.getInt("PuntosTotales");
        puntosConsolidados = extras.getInt("PuntosConsolidados");
        vida = extras.getInt("Vidas");
        cantidadRetosContestados = extras.getInt("Ronda");
        haConsolidado = extras.getBoolean("haConsolidado");
        TiempoOpcion = extras.getInt("TiempoOpcion");
        Tiempo = extras.getInt("Tiempo");
        nivel = extras.getInt("Nivel");
        SonidoFallo = extras.getInt("SonidoFallo");
        SonidoAcierto = extras.getInt("SonidoAcierto");
        errores = extras.getInt("erroresRetoAhorcado");
        numeroOds = extras.getInt("odsAhorcado");
        pistas = extras.getInt("Pistas");
        poner_imagen_ods();
    }
    public void poner_imagen_ods() {
        int imagenId = getResources().getIdentifier("ods_" + numeroOds, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(imagenId);
        imagenOds.setImageDrawable(imagen);
    }

    public void botonSelecionado(View v){
        String letraSeleccionada= "";
        Button b = (Button) v;
        letraSeleccionada = String.valueOf(((Button) v).getText());
        validarLetraSeleccionada(letraSeleccionada, b);
    }

    public void validarLetraSeleccionada(String letraSeleccionada, Button botonSelccionado){
        if (fraseAhorcado.contains(letraSeleccionada)){
            //letra correcta
            MainActivity.music.stop();
            MainActivity.music = MediaPlayer.create(getApplicationContext(), SonidoAcierto);
            MainActivity.music.start();

            botonSelccionado.setBackground(getDrawable(R.drawable.boton_teclado_verde));
            letrasEncontradas[imax++]= StringToChar(letraSeleccionada);
            sustituirLetra(letraSeleccionada);
            terminoBien(comprobarSiTermino());
        }else {
            //letra incorrecta
            MainActivity.music.stop();
            MainActivity.music = MediaPlayer.create(getApplicationContext(), SonidoFallo);
            MainActivity.music.start();

            botonSelccionado.setBackground(getDrawable(R.drawable.boton_teclado_rojo));
            errores++;
            ponerImagenAhorcado();

            terminoMal(0, revisarCantidadErrores());
        }
        botonSelccionado.setEnabled(false);
    }
    public void ponerImagenAhorcado(){
        int Ahorcado = getResources().getIdentifier("ahorcado_" + errores, "drawable", getPackageName());
        Drawable imagen = getResources().getDrawable(Ahorcado);
        imagenAhorcado.setImageDrawable(imagen);
    }

    public void sustituirLetra(String letraSeleccionada){

        String textoEnPantalla= "";
        for(int i = 0; i<letrasEncontradas.length;i++){
            for(int j =0 ;j<fraseAhorcado.length(); j++){

                if(letrasEncontradas[i]==fraseAhorcado.charAt(j)){
                    fraseACompletar[j] = fraseAhorcado.charAt(j);
                }

            }
        }
            fraseEnBarrasBajas="";
       for(int i =0 ;i<fraseAhorcado.length(); i++){
            fraseEnBarrasBajas += fraseACompletar[i]+" ";
        }

        texto_fraseADescubir.setText(fraseEnBarrasBajas.trim());
    }
    public void utilizarPista(View view){

        if(pistas != 0) {
            //falta modificar puntuación
            Random aleatorio = new Random();

            String letraAleatoria; //= String.valueOf(fraseAhorcado.charAt(aleatorios));
            int aleatorios = aleatorio.nextInt(noEncontradas.size());
            letraAleatoria = String.valueOf(noEncontradas.get(aleatorios));
            noEncontradas.remove(letraAleatoria);

            int buttonId = getResources().getIdentifier("boton" + letraAleatoria, "id", getPackageName()); // get the resource id dynamically
            Button botonSeleccionado = findViewById(buttonId);
            validarLetraSeleccionada(letraAleatoria, botonSeleccionado);
            /*imagenPista.setClickable(false);
            imagenPista.setImageDrawable(getDrawable(R.drawable.pista2));
            pistas--;
            contadorBombillas.setText(pistas + "/3");
            Intent t = new Intent();
            t.putExtra("Pistas", pistas);*/
        }

    }
    private void letrasNoEncontradas() {
        for (int i = 0; i < fraseAhorcado.length(); i++) {
            if((! noEncontradas.contains(fraseAhorcado.charAt(i)) && (!Character.isWhitespace(fraseAhorcado.charAt(i))))) {
                noEncontradas.add(fraseAhorcado.charAt(i));
            }
        }
    }




    public static char StringToChar(String s) {
        return s.charAt(0);
    }

    public void metodoBotonSiguiente(View v) throws SQLException {
        mCountDownTimer.cancel();
        MainActivity.music.stop();
        Intent t = new Intent();
        t.putExtra("Acierto", Acierto);
        if(haConsolidado && haConsolidadoLocal) {
            setResult(RESULT_FIRST_USER);
           // haConsolidado = false;
        }
        else if (Acierto) {setResult(RESULT_OK);}
        else {setResult(RESULT_CANCELED);}

        finish();
    }


    public void pantallaAciertoFallo() {
        contenedor.setVisibility(View.VISIBLE);
    }

    public void visualizacionBotonConsolidar(Boolean respuestaCorrecta) {

        if (respuestaCorrecta && !haConsolidado) {
            botonConsolidar.setVisibility(View.VISIBLE);
        } else {
            botonConsolidar.setVisibility(View.INVISIBLE);
        }

    }
    public void pantalla_final() {
        pantalla_final.setVisibility(View.VISIBLE);
        timeBar.setVisibility(View.INVISIBLE);

    }

    public boolean comprobarSiTermino(){

        for (int i = 0; i < fraseACompletar.length; i++) {
            if(fraseACompletar[i]=='_'){
               return false;

            }

        }
       return true;
    }
    public void terminoBien(boolean haTerminado){
        if(haTerminado){
            MainActivity.background.pause();
            Acierto = true;


            cantidadRetosContestados++;
            puntosTotales += 100 * nivel;
            textoPuntosGanados.setText("+" +puntosTotales + " puntos ganados!");
             textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);


            visualizacionBotonConsolidar(true);
            acierto_fallo.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));

            if (haConsolidado) {
                botonSiguientePregunta.setText("CONTINUAR");
            } else {
                botonSiguientePregunta.setText("CONTINUAR SIN CONSOLIDAR");
            }

            if (cantidadRetosContestados > 10) {
                imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.felicitaciones_2));
                textoPuntosFinales.setText("Tu puntuacion final es de: " + puntosTotales);
                MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.winner);
                MainActivity.music.start();
                pantalla_final();
                return;
            } else {

                pantallaAciertoFallo();
            }

            startTimer(TiempoOpcion);

        }
    }
    public boolean revisarCantidadErrores(){

        return errores == 10;

    }

    public void terminoMal(int tipo, boolean haTerminado) {


        if (haTerminado) {
            MainActivity.background.pause();
            vida--;
            if (vida < 0) {
                puntosTotales = 0;
                puntosConsolidados = 0;
                imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.game_over));
                textoPuntosFinales.setText("Puntos totales: 0");
                MainActivity.music = MediaPlayer.create(getApplicationContext(), R.raw.loser);
                MainActivity.music.start();
                pantalla_final();
                return;
            }
            startTimer(TiempoOpcion);

            puntosTotales -= 100 * nivel * 2;
            if (puntosTotales < 0) puntosTotales = 0;
            textoPuntosGanados.setText(100 * nivel * (-2) + " puntos perdidos ");
            if (tipo == 2) {
                textoPuntosGanados.setText("Se acabo el tiempo");
            }

            botonSiguientePregunta.setText("CONTINUAR");
            textoPuntosTotal.setText("Puntos Totales: " + puntosTotales);
            visualizacionBotonConsolidar(false);

            acierto_fallo.setImageDrawable(getDrawable(R.drawable.vuelve_intentar));
            pantallaAciertoFallo();
        }
    }

    public void clickBotonTerminarPartida(View v) {
        Intent t = new Intent();
        t.putExtra("Acierto", Acierto);
        guardarPuntuacion();
        if(abandonado)
        {setResult(MediadorDeRetos.ABANDON);
        } else if (Acierto) {setResult(RESULT_OK);
        } else if(haConsolidado) {
            setResult(RESULT_FIRST_USER);
        }
        else setResult(RESULT_CANCELED);
        finish();
    }

    public void clickBotonConsolidar(View v) {
        haConsolidado = true;
        haConsolidadoLocal = true;
        botonSiguientePregunta.performClick();

    }

    public void abandonOnClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Estas seguro que quieres abandonar? Obtendras los puntos consolidados")
                .setCancelable(true)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCountDownTimer.cancel();
                        MainActivity.music.stop();
                        imagenPantallaFinal.setImageDrawable(getDrawable(R.drawable.no_esta_mal));
                        textoPuntosFinales.setText("Tu puntuacion final es de: " + puntosConsolidados);
                        abandonado = true;
                        pantalla_final();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void SavePoints(int Points) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                MainActivity.user.setPointsAchieved(MainActivity.user.getPointsAchieved() + Points);
                u.actualizar(MainActivity.user);
            }
        }).start();
    }

    public void guardarPuntuacion() {
        MainActivity.music.stop();
        if (cantidadRetosContestados > 10) {
            SavePoints(puntosTotales);
        } else {
            SavePoints(puntosConsolidados);
        }
        mCountDownTimer.cancel();

    }
    @Override
    public void onBackPressed() {}
}



package Presentacion_layer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import Domain_Layer.Partida;
import Domain_Layer.Ranking;
import Domain_Layer.User;
import Persistence.PartidaRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class IURanking extends AppCompatActivity {
    ListView listView;
    Spinner selector;

    TextView userPosition;

    List<Ranking> listaRanking = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_usuarios);
        listView = findViewById(R.id.listview);
        selector = findViewById(R.id.selectorRanking);
        userPosition = findViewById(R.id.textViewposicion);
        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Your code here
                if (position == 0) {
                    cargarDatos("Global");
                }
                if (position == 1) {
                    cargarDatos("Mensual");
                }

                if (position == 2) {
                    cargarDatos("Diario");
                }
                muestraDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
       });

    }
    public void muestraDatos() {
        Collections.sort(listaRanking, new Comparator<Ranking>(){
            public int compare(Ranking s1,Ranking s2){
                return Integer.compare(s1.getPuntos(), s2.getPuntos());
            }});
        Collections.reverse(listaRanking);
        RankingAdapter adapter = new RankingAdapter(this, listaRanking.subList(0,10));
        int posicion = -1;
        for (int i = 0; i < listaRanking.size(); i++) {
            if (listaRanking.get(i).getNombre().equals(MainActivity.user.getNickname())) {
                posicion = i + 1;
                break;
            }
        }
        userPosition.setText("Tu posicion en el ranking : " + posicion);
        listView.setAdapter(adapter);
    }
    public void cargarDatos(String s){
        try {
            listaRanking = new ArrayList<>();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    PartidaRepository p = new PartidaRepository(SingletonConnection.getSingletonInstance());
                    List<Partida> partidas = p.obtenerTodos();
                    UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                    List<User> usuarios = u.obtenerTodos();

                    for(User usuario: usuarios){
                        Ranking aux = new Ranking(0,usuario.getNickname());
                        for(Partida paux : partidas) {
                            if(paux.getUsuario().equals(usuario.getNickname())) {
                                switch(s) {
                                    case "Diario":
                                        if(paux.getFecha().getDay() == new Date().getDay()) {
                                            aux.setPuntos(aux.getPuntos() + paux.getPuntos());
                                        }
                                        break;
                                    case "Global":
                                        aux.setPuntos(aux.getPuntos() + paux.getPuntos());
                                        break;
                                    case "Mensual":
                                        if(paux.getFecha().getMonth() == new Date().getMonth()) {
                                            aux.setPuntos(aux.getPuntos() + paux.getPuntos());
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        listaRanking.add(aux);
                    }
                }
            });
            t.start();t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public class RankingAdapter extends ArrayAdapter<Ranking> {

        private List<Ranking> usuarios;

        public RankingAdapter(Context context, List<Ranking> usuarios) {
            super(context, 0, usuarios);
            this.usuarios = usuarios;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ranking, parent, false);
            }

            Ranking usuario = usuarios.get(position);
            TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);
            TextView puntosTextView = convertView.findViewById(R.id.puntosTextView);

            nombreTextView.setText((position + 1) + ".   " + usuario.getNombre());
            puntosTextView.setText(String.valueOf(usuario.getPuntos()));

            return convertView;
        }


    }

    public void abandonarRanking(View v){

        Intent intent = new Intent(IURanking.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}

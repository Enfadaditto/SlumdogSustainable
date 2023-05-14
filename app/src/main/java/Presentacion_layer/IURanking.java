package Presentacion_layer;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slumdogsustainable.MainActivity;
import com.slumdogsustainable.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Domain_Layer.Partida;
import Domain_Layer.Ranking;
import Domain_Layer.User;
import Persistence.PartidaRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

public class IURanking extends AppCompatActivity {
    ListView listView;

    List<Ranking> listaRanking = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_usuarios);
        listView = findViewById(R.id.listview);
        cargarDatos();
        Collections.sort(listaRanking, new Comparator<Ranking>(){
            public int compare(Ranking s1,Ranking s2){
                return Integer.compare(s1.getPuntos(), s2.getPuntos());
            }});
        Collections.reverse(listaRanking);
        RankingAdapter adapter = new RankingAdapter(this, listaRanking);

        listView.setAdapter(adapter);
    }

    public void cargarDatos(){
        try {
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
                                aux.setPuntos(aux.getPuntos() + paux.getPuntos());
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

            nombreTextView.setText(usuario.getNombre());
            puntosTextView.setText(String.valueOf(usuario.getPuntos()));

            return convertView;
        }
    }
}

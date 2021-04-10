package daoRepository;

import entities.Genero;
import entities.Usuario;

import java.util.List;

public interface GeneroDao {

    List<Genero> getGeneros();
    List<Genero> getGenerosNaoFav(Usuario usuario);
    void addGeneroFav(Usuario usuario, Genero genero);
    void rmvGeneroFav(Usuario usuario, Genero genero);
    List<Genero> getGeneroFav (Usuario usuario);
    Genero getGeneroById(Integer a);
}

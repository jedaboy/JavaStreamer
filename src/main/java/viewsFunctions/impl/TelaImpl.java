/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package viewsFunctions.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import viewsFunctions.TelaInterface;
import daoRepository.DaoFactory;
import daoRepository.MusicaDao;
import entities.Musica;
import entities.Usuario;
import java.awt.Color;
import static java.lang.Integer.parseInt;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.DefaultListModel;

public class TelaImpl implements TelaInterface {

    Connection connection = null;

    public TelaImpl() {

    }

    //Lista todos os generos disponiveis no DB.
    @Override
    public void metodoTeste() {

    }

    @Override
    public void onlyNum(javax.swing.JTextField notaText) {

        notaText.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() != KeyEvent.VK_BACK_SPACE) {

                    String value = notaText.getText();
                    int l = value.length();
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        notaText.setEditable(true);

                    } else {
                        notaText.setEditable(false);

                    }

                } else {
                    notaText.setEditable(true);
                }
            }

        });
    }

    @Override
    public void sortLista(DefaultListModel lista) {
        //cria uma lista dinamica "L", que recebe todos os elementos da DefaultListModel, lista do jlist
        // "L" então é ordenada de acordo com a media geral, e em seguida invertida para a ordem nao crescente
        List<Musica> list = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            list.add((Musica) lista.get(i));
        }
        list.sort(Comparator.comparing(Musica::getNotaGeral));
        Collections.reverse(list);
        lista.removeAllElements();
        for (Musica s : list) {
            lista.addElement(s);

        }
    }
    // ordenador da lista2, cuja as musicas são de generos favoritos, so inicia caso o usuario aperte o botao recomendacao

    @Override
    public void sortLista2(DefaultListModel lista2) {
        //cria uma lista dinamica "L", que recebe todos os elementos da DefaultListModel, lista do jlist
        // "L" então é ordenada de acordo com a media geral, e em seguida invertida para a ordem nao crescente
        List<Musica> list = new ArrayList<>();
        for (int i = 0; i < lista2.size(); i++) {
            list.add((Musica) lista2.get(i));
        }
        list.sort(Comparator.comparing(Musica::getNotaGeral));
        Collections.reverse(list);
        lista2.removeAllElements();
        for (Musica s : list) {
            lista2.addElement(s);

        }
    }

    //exibe o alerta de entrada invalida em vermelho
    @Override
    public void paintItRed(javax.swing.JLabel alertaLabel) {
        alertaLabel.setVisible(true);
        alertaLabel.setForeground(Color.red);
    }

    @Override
    public void prencheLista(DefaultListModel lista,DefaultListModel lista2, Usuario usuario, boolean recomendacao) {
     
       if(recomendacao)
       embaralhador2( lista2, usuario);
       else
       embaralhador1( lista, usuario);
        
    }

    private void embaralhador2(DefaultListModel lista2, Usuario usuario) {
        
        MusicaDao musicaDao = DaoFactory.createMusicaDao();
        List<Musica> musicas = new ArrayList<>();
        musicas = musicaDao.musicasFavoritasNaoAvaliadas(usuario);

        Random r = new Random();
        int cont = 0;

        for (Musica val : musicas) {
            int i = r.nextInt(4);
            if (i == 0 || i == 1) {
                cont++;
                if (i == 0 || i == 1 && cont < 2) {
                    lista2.addElement(val);
                }
            }
        }

    }

     private void embaralhador1(DefaultListModel lista, Usuario usuario) {
        
         MusicaDao musicaDao = DaoFactory.createMusicaDao();

        //  MusicaDao musicaDao = DaoFactory.createMusicaDao();
        List<Musica> musicas = new ArrayList<>();

        musicas = musicaDao.musicasNaoAvaliadas(usuario);

        SecureRandom r = new SecureRandom();
        int cont = 0;
        //aqui todas as musicas não avaliadas estao sendo inicialmente passadas,
        //em seguida um rand faz com que  3 musicas em ordem pseudo-aleatorias sejam exibidas
        for (Musica val : musicas) {
            int i = r.nextInt(7);
            if (i == 0) {
                cont++;
                if (i == 0 && cont < 4) {
                    lista.addElement(val);
                }
            }
        }
    }
     
      public void avaliaMusicas(javax.swing.JLabel confirmacaoLabel, Usuario usuario , javax.swing.JLabel alertaLabel, DefaultListModel lista,DefaultListModel lista2,javax.swing.JTextField notaText,javax.swing.JList<Musica> listaMusicas,javax.swing.JList<Musica> listaRecomendacoes, boolean recomendacao){
        MusicaDao musicaDao = DaoFactory.createMusicaDao();

        if (!recomendacao) {
            int IndexMusica = listaMusicas.getSelectedIndex();
            if (IndexMusica == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma musica");
            }
            Musica musica = listaMusicas.getModel().getElementAt(IndexMusica);
            String nota = notaText.getText();
            int notaInt = parseInt(nota);

            if (notaInt > 5 || notaInt < 1) {

                System.out.println("Passeiaqui1");
                paintItRed(alertaLabel);

            } else {
                musicaDao.avaliarMusica(usuario, musica, notaInt);
                notaText.setText("");
                alertaLabel.setVisible(false);
                confirmacaoLabel.setVisible(true);
                lista.remove(IndexMusica);
            }
        } else {
            int IndexMusica = listaRecomendacoes.getSelectedIndex();
            if (IndexMusica == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma musica");
            }
            Musica musica = listaRecomendacoes.getModel().getElementAt(IndexMusica);
            String nota = notaText.getText();
            int notaInt = parseInt(nota);

            if (notaInt > 5 || notaInt < 1) {

                System.out.println("Passeiaqui1");
                paintItRed(alertaLabel);

            } else {
                musicaDao.avaliarMusica(usuario, musica, notaInt);
                notaText.setText("");
                alertaLabel.setVisible(false);
                confirmacaoLabel.setVisible(true);
                lista2.remove(IndexMusica);
            }

        }
    }                               
  }
     


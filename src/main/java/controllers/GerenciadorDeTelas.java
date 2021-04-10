/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package controllers;

import daoRepository.Mp3DataDao;
import views.TelaCadastro;
import views.TelaDashBoard;
import views.TelaLogin;
import views.TelaGenerosFavoritos;
import views.TelaAvaliacaoMusica;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import views.TestFrame;

/**
 *
 * @author jedaboy
 */
public class GerenciadorDeTelas {

    //secção de continuidade
    private TestFrame miniTela;
    private boolean playing = false;
    private long frameCount;
    private double duration;
    private AudioFormat format;
    private Clip clip;
    boolean botaoNext = false;
    private Timer playTimer;
    private boolean ignoreStateChange = false;
    Mp3DataDao mp3DataDao;
    //imagem que acompanha a musica
    ImageIcon image;

    //variavel que determina o usuario em questão
    private Usuario usuario;
    //variavel que determina qual lista será exibida para o usuario avaliar
    private boolean recomendacao;

    public GerenciadorDeTelas() {
    }

    public GerenciadorDeTelas(ImageIcon image, Mp3DataDao mp3DataDao, Timer playTimerr, long frameCount, double duration, AudioFormat format, Clip clip) {

        playTimer = playTimerr;
        this.clip = clip;
        this.format = format;
        this.duration = duration;
        this.image = image;
        this.frameCount = frameCount;
        this.mp3DataDao = mp3DataDao;

    }
    //construcao das telas
    TelaDashBoard dashTela = new TelaDashBoard();
    TelaLogin logTela = new TelaLogin();
    TelaCadastro cadTela = new TelaCadastro();
    TelaGenerosFavoritos GenTela = new TelaGenerosFavoritos();
    TelaAvaliacaoMusica avTela = new TelaAvaliacaoMusica();

    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }

    public void setMiniTela(TestFrame miniTela, boolean playing) {
        this.miniTela = miniTela;
        this.playing = playing;
       
    }

    public TestFrame getMiniTela() {
        return miniTela;

    }

    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;

    }

    public void chamaTelaLogin() {
        if (playing) {
            miniTela.dispose();
        }
        logTela.setVisible(true);
    }

    public void chamaTelaDashboard() {
        //esta tela é chamada após o login 
        
        if (playing) {
            
            dashTela.setMiniTela(miniTela, playing);
            dashTela.setDataAudio(image, mp3DataDao, playTimer, frameCount, duration, format, clip);
        
        }

        dashTela.setUsuAtual(usuario);
        dashTela.setVisible(true);

    }

    public void chamaTelaCadastro() {
        if (playing) {
            miniTela.dispose();
        }
        cadTela.setVisible(true);
    }

    public void chamaTelaGenero() {
        //essa classe e  chamada após dashboard tela selecionar o botao generos favoritos, daqui o usuario atual é passado para a classe generos favoritos
        if (playing) {
            miniTela.dispose();
        }
        GenTela.setUsuAtual(usuario);
        GenTela.setVisible(true);
    }

    public void chamaTelaAvaliar() {
        TelaAvaliacaoMusica avTelaCont;
        try {
            
            if (!playing) {
                avTela.setRecomendacao(recomendacao);
                avTela.setUsuAtual(usuario);
                avTela.setVisible(true);
             
            } else {
                avTelaCont = new TelaAvaliacaoMusica(image, mp3DataDao, playTimer, frameCount, duration, format, clip, playing);
                avTelaCont.setRecomendacao(recomendacao);
                avTelaCont.setUsuAtual(usuario);
                avTelaCont.setVisible(true);
                miniTela.dispose();
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(GerenciadorDeTelas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

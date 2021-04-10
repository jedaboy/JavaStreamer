/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package controllers;

import daoRepository.Mp3DataDao;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;
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
public class GerenciadorDeFluxo {

    private int contFlux = 0;
    private Usuario usuario;
    private boolean recomendacao;

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

    public GerenciadorDeFluxo() {
    }

    public GerenciadorDeFluxo(ImageIcon image, Mp3DataDao mp3DataDao, Timer playTimerr, long frameCount, double duration, AudioFormat format, Clip clip, boolean playingg) throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        playTimer = playTimerr;
        this.clip = clip;
        this.format = format;
        this.duration = duration;
        this.image = image;
        this.frameCount = frameCount;
        this.mp3DataDao = mp3DataDao;

    }

    public void setContFlux(int contFlux) {
        this.contFlux = contFlux;
    }

    public int getContFlux() {
        return contFlux;

    }

    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }

    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;

    }

    public TestFrame getMiniTela() {
        return miniTela;

    }

    public void setMiniTela(TestFrame miniTela, boolean playing) {
        this.miniTela = miniTela;
        this.playing = playing;
        
    }

    public boolean getIsPlaying() {
        return playing;

    }

    public void fluxoPrincipal() {

        GerenciadorDeTelas gTelas = new GerenciadorDeTelas(image, mp3DataDao, playTimer, frameCount, duration, format, clip);
        if (contFlux == 0) {
            gTelas.chamaTelaLogin();
        }
        if (contFlux == 1) {
            //é chamado pela classe logintela caso não tenha cadastro   
            gTelas.chamaTelaCadastro();
        }
        if (contFlux == 2) {
            //esta parte é chamada pela classe LoginTEla  após o login ser efetuado com sucesso    
            //passa o usuario atual para a classe tela, o usuario atual é sempre passado para que haja a permanencia da ciencia de quem esta logado em todas as classes após o login
            if (playing == true) {
                gTelas.setMiniTela(miniTela, playing);
                
            }
           
            gTelas.setUsuAtual(usuario);
            gTelas.chamaTelaDashboard();

        }
        if (contFlux == 3) {
            //esta parte é chamada pela classe DashboardTela e o usuario atual é passado para o gerenciador de telas     
            gTelas.setUsuAtual(usuario);
            gTelas.chamaTelaGenero();
        }
        if (contFlux == 4) {
            //esta parte é chamada pela classe DashboardTela e o usuario atual é passado para o gerenciador de telas     
            if (playing == true) {
                gTelas.setMiniTela(miniTela, playing);
               
            }

            gTelas.setRecomendacao(recomendacao);
            gTelas.setUsuAtual(usuario);
            gTelas.chamaTelaAvaliar();
        }

    }
}

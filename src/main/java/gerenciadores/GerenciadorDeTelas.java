/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadores;

import Telas.TelaCadastro;
import Telas.TelaDashBoard;
import Telas.TelaLogin;
import Telas.TelaGenerosFavoritos;
import Telas.TelaAvaliacaoMusica;
import Telas.TestFrame;
import entities.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JOptionPane;

/**
 *
 * @author jedaboy
 */
public class GerenciadorDeTelas {
    
    //construcao das telas
    TelaDashBoard dashTela = new  TelaDashBoard();
    TelaLogin logTela = new TelaLogin();
    TelaCadastro cadTela = new TelaCadastro();
    TelaGenerosFavoritos GenTela = new TelaGenerosFavoritos();
    TelaAvaliacaoMusica avTela = new TelaAvaliacaoMusica();
        
    //variavel que determina o usuario em questão
    private Usuario usuario;
    //variavel que determina qual lista será exibida para o usuario avaliar
    private boolean recomendacao;

    
    
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
    
    

    public void chamaTelaLogin() {

        logTela.setVisible(true);
    }

    public void chamaTelaDashboard() {
        //esta tela é chamada após o login 


        dashTela.setUsuAtual(usuario);
        dashTela.setVisible(true);
    }

    public void chamaTelaCadastro() {

        cadTela.setVisible(true);
    }

    public void chamaTelaGenero() {
        //essa classe e  chamada após dashboard tela selecionar o botao generos favoritos, daqui o usuario atual é passado para a classe generos favoritos
          
          GenTela.setUsuAtual(usuario);
          GenTela.setVisible(true);
    }

    public void chamaTelaAvaliar() {
        avTela.setRecomendacao(recomendacao);
        avTela.setUsuAtual(usuario);
        avTela.setVisible(true);
    }
}

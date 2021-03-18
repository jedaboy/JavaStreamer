/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciadores;

import entities.Usuario;
import javax.swing.JOptionPane;

/**
 *
 * @author jedaboy
 */
public class GerenciadorDeFluxo {

    GerenciadorDeTelas gTelas = new GerenciadorDeTelas();
   
    private int contFlux = 0;
    private Usuario usuario;
    private boolean recomendacao;

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
    
    public void fluxoPrincipal() {

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
            gTelas.setRecomendacao(recomendacao);
            gTelas.setUsuAtual(usuario);
            gTelas.chamaTelaAvaliar();
        }


    }
}

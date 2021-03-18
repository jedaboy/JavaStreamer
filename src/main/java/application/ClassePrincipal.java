/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/* Importando por garantia */
import gerenciadores.GerenciadorDeFluxo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Collections;
import javax.swing.SwingUtilities;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 *
 * @author jedaboy
 */
public class ClassePrincipal {
     public static void main(String[] args) {
   
 
    GerenciadorDeFluxo genFlux = new GerenciadorDeFluxo();
    genFlux.fluxoPrincipal();
}
}

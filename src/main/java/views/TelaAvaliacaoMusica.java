/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package views;

import controllers.GerenciadorDeFluxo;
import viewsFunctions.TelaFactory;
import daoRepository.DaoFactory;
import daoRepository.GeneroDao;
import daoRepository.MusicaDao;
import daoRepository.UsuarioDao;
import entities.Genero;
import entities.Musica;
import entities.Usuario;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import viewsFunctions.TelaInterface;
import conversor.Conversor;
import daoRepository.Mp3DataDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jedaboy
 */
public class TelaAvaliacaoMusica extends javax.swing.JFrame {

    // cria uma lista compativel com  o campo do Jlist (propriedades, model, lista)
    DefaultListModel lista = new DefaultListModel();
    // Esta lista2 é da jList que tem as musicas recomendadas
    DefaultListModel lista2 = new DefaultListModel();

    private Usuario usuario;
    boolean recomendacao;

    //variaveis de controle da reprodução de audio
    private long frameCount;
    private double duration;
    private AudioFormat format;
    private Clip clip;
    boolean botaoNext = false;
    private boolean playing = false;
    private Timer playTimer;
    private boolean ignoreStateChange = false;
    Mp3DataDao mp3DataDao;
    //imagem que acompanha a musica
    ImageIcon image;

    // getters e setters para saber qual é o usuario atual
    public void setUsuAtual(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuAtual() {
        return usuario;

    }

    // getters e setters para determinar qual a jlist a  ser exibida
    public void setRecomendacao(boolean recomendacao) {
        this.recomendacao = recomendacao;
    }

    public boolean getRecomendacao() {
        return recomendacao;

    }

    /**
     * Creates new form Tela
     */
    //construtor da classe
    public TelaAvaliacaoMusica() {
        try {
            //Estilo visual

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            mp3DataDao = DaoFactory.createMp3DataDao();
            initComponents();
            clipLoad();
            labelPic.setText("");

            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent evt) {
                    if (evt.getType().equals(LineEvent.Type.STOP)
                            || evt.getType().equals(LineEvent.Type.CLOSE)) {
                        action.setText("Play");
                        playing = false;
                        playTimer.stop();
                        updateState();
                    }
                }
            });
            playTimer = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    updateState();
                }
            });
            //delimita a entrada de avaliações apenas a caracteres numericos
            onlyNum();

        } catch (SQLException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

    }

    //construtor para continuar com a musica em curso
    public TelaAvaliacaoMusica(ImageIcon image, Mp3DataDao mp3DataDao, Timer playTimerr, long frameCount, double duration, AudioFormat format, Clip clip, boolean playingg) throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        playTimer = playTimerr;
        playing = playingg;
        this.clip = clip;
        this.format = format;
        this.duration = duration;
        this.image = image;
        this.frameCount = frameCount;
        this.mp3DataDao = mp3DataDao;

        initComponents();
        labelPic.setText("");

        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent evt) {
                if (evt.getType().equals(LineEvent.Type.STOP)
                        || evt.getType().equals(LineEvent.Type.CLOSE)) {
                    action.setText("Play");
                    playing = false;
                    playTimer.stop();
                    updateState();
                }
            }
        });

        playTimer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                updateState();
            }
        });

        //responsavel por atualizar o timer
        labelPic.setIcon(image);

        // labelFileName.setText("Playing File: " + audioFilePath); //not important
        System.out.print((int) clip.getMicrosecondLength() / 1_000_000);
        // clip lenght in seconds? this feature will set the slider lenght to the same length of the audio clip
        //clip.start();
        action.setText("Stop");
        playing = true;
        playTimer.start();

    }

    //delimita a entrada de avaliações apenas a caracteres numericos
    public void onlyNum() {

        TelaInterface tela = TelaFactory.createTesteImpl();
        tela.onlyNum(notaText);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        setaRetorno = new javax.swing.JLabel();
        ListaPainel1 = new javax.swing.JScrollPane();
        listaMusicas = new javax.swing.JList<>();
        notaText = new javax.swing.JTextField();
        confirmacaoLabel = new javax.swing.JLabel();
        alertaLabel = new javax.swing.JLabel();
        ListaPainel2 = new javax.swing.JScrollPane();
        listaRecomendacoes = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        avaliarButton = new javax.swing.JLabel();
        labelTimeCounter = new javax.swing.JLabel();
        action = new javax.swing.JButton();
        totalDuration = new javax.swing.JLabel();
        slider = new javax.swing.JSlider();
        labelPic = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/logo_redondo.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setaRetorno.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        setaRetorno.setForeground(new java.awt.Color(153, 153, 153));
        setaRetorno.setText("← ");
        setaRetorno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setaRetornoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setaRetornoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setaRetornoMouseExited(evt);
            }
        });
        jPanel1.add(setaRetorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, 25));

        ListaPainel1.setBorder(null);

        listaMusicas.setModel(lista);
        listaMusicas.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaMusicasAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaMusicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMusicasMouseClicked(evt);
            }
        });
        ListaPainel1.setViewportView(listaMusicas);

        jPanel1.add(ListaPainel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 150, 290, 215));

        notaText.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        notaText.setBorder(null);
        notaText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                notaTextKeyPressed(evt);
            }
        });
        jPanel1.add(notaText, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 485, 20, 32));
        notaText.getAccessibleContext().setAccessibleName("");

        confirmacaoLabel.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        confirmacaoLabel.setText("Musica Avaliada!");
        jPanel1.add(confirmacaoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 100, 20));

        alertaLabel.setFont(new java.awt.Font("Ink Free", 0, 14)); // NOI18N
        alertaLabel.setForeground(new java.awt.Color(255, 0, 51));
        alertaLabel.setText("entrada invalida");
        jPanel1.add(alertaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 100, 20));

        ListaPainel2.setBorder(null);

        listaRecomendacoes.setModel(lista2);
        listaRecomendacoes.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                listaRecomendacoesAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        listaRecomendacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaRecomendacoesMouseClicked(evt);
            }
        });
        ListaPainel2.setViewportView(listaRecomendacoes);

        jPanel1.add(ListaPainel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 150, 310, 215));

        jLabel3.setFont(new java.awt.Font("Ink Free", 0, 18)); // NOI18N
        jLabel3.setText("Avaliar");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 60, 40));

        avaliarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/button4.png"))); // NOI18N
        avaliarButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        avaliarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                avaliarButtonMouseExited(evt);
            }
        });
        jPanel1.add(avaliarButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, -1, 40));

        labelTimeCounter.setText("Tempo: ");
        jPanel1.add(labelTimeCounter, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 410, -1, -1));

        action.setText("Play");
        action.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actionMouseClicked(evt);
            }
        });
        jPanel1.add(action, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 460, -1, -1));

        totalDuration.setText("Duração: ");
        totalDuration.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                totalDurationAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPanel1.add(totalDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 410, -1, -1));

        slider.setValue(0);
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        jPanel1.add(slider, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 400, -1, -1));
        jPanel1.add(labelPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 100, 440, 280));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recomendacao.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void setaRetornoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseClicked
        try {  // seta de retorno para dashboard
            GerenciadorDeFluxo chamaMain;

            chamaMain = new GerenciadorDeFluxo(image, mp3DataDao, playTimer, frameCount, duration, format, clip, playing);

            if (playing) {
                TestFrame tf = new TestFrame(image, mp3DataDao, playTimer, frameCount, duration, format, clip, playing);
                tf.setVisible(true);
                chamaMain.setMiniTela(tf, playing);
            }
            
            chamaMain.setUsuAtual(usuario);
            chamaMain.setContFlux(2);
            chamaMain.fluxoPrincipal();
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_setaRetornoMouseClicked

    private void setaRetornoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseEntered
        setaRetorno.setForeground(Color.MAGENTA);

    }//GEN-LAST:event_setaRetornoMouseEntered

    private void setaRetornoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_setaRetornoMouseExited
        setaRetorno.setForeground(Color.gray);
    }//GEN-LAST:event_setaRetornoMouseExited

    //inicia a lista1  com musicas aleatorias ordenadas pela nota
    private void listaMusicasAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaMusicasAncestorAdded

        if (!recomendacao) {
            MusicaDao musicaDao = DaoFactory.createMusicaDao();
            TelaInterface tela = TelaFactory.createTesteImpl();
            tela.prencheLista(lista, lista2, usuario, recomendacao);
            tela.sortLista(lista);
            // sortLista();
            listaMusicas.setModel(lista);
        }

    }//GEN-LAST:event_listaMusicasAncestorAdded

    //cria uma lista dinamica "L", que recebe todos os elementos da DefaultListModel, lista do jlist
    // "L" então é ordenada de acordo com a media geral, e em seguida invertida para a ordem nao crescente
    // ordenador da lista1, cuja as musicas são de generos variados
    //exibe o alerta de entrada invalida em vermelho
    public void paintItRed() {
        alertaLabel.setVisible(true);
        alertaLabel.setForeground(Color.red);

    }

    //Limita a entrada para dados numericos apenas
    private void notaTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notaTextKeyPressed

        onlyNum();

    }//GEN-LAST:event_notaTextKeyPressed
    //Alerta de entrada invalida, determina o que estará visivel ao inicio da janela
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //determina o que estará visivel ao inicio da janela
        gerenciadorDeLista(recomendacao);

    }//GEN-LAST:event_formWindowOpened
    //determina o que estará visivel ao inicio da janela

    private void gerenciadorDeLista(Boolean recomendacao) {

        alertaLabel.setVisible(false);
        confirmacaoLabel.setVisible(false);
        ListaPainel2.setVisible(recomendacao);
        ListaPainel1.setVisible(!recomendacao);

    }

    //inicia a lista2  com musicas aleatorias dos generos favoritos do usuario, ordenadas pela nota
    private void listaRecomendacoesAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_listaRecomendacoesAncestorAdded

        // TODO add your handling code here:
        MusicaDao musicaDao = DaoFactory.createMusicaDao();
        TelaInterface tela = TelaFactory.createTesteImpl();
        tela.prencheLista(lista, lista2, usuario, recomendacao);
        tela.sortLista2(lista2);
        //sortLista2();
        /*  int indexPrimario = listaRecomendacoes.getFirstVisibleIndex();
        Musica musica = listaMusicas.getModel().getElementAt(indexPrimario);
        int id = musica.getId();
        mp3DataDao.setId(id);*/
        listaRecomendacoes.setModel(lista2);

    }//GEN-LAST:event_listaRecomendacoesAncestorAdded

    private void avaliarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseClicked
        MusicaDao musicaDao = DaoFactory.createMusicaDao();
        TelaInterface tela = TelaFactory.createTesteImpl();
        /*caso o usuario tenha clicado em recomendação o jList apresentado sera o que tem as musicas 
        da preferencia do mesmo caso não tenha clicado o jList em questão tera musicas de todos os generos, 
        com exceção das  musicas que ele já avaliou, este trecho também determina qual jlist esta tendo seus
        elementos selecionados pelo mouse*/
        alertaLabel.setVisible(false);
        confirmacaoLabel.setVisible(false);

        tela.avaliaMusicas(confirmacaoLabel, usuario, alertaLabel, lista, lista2, notaText, listaMusicas, listaRecomendacoes, recomendacao);

    }//GEN-LAST:event_avaliarButtonMouseClicked

    private void avaliarButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseEntered
        avaliarButton.setIcon(new ImageIcon(getClass().getResource("/pressbutton_4.png")));
    }//GEN-LAST:event_avaliarButtonMouseEntered

    private void avaliarButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avaliarButtonMouseExited
        avaliarButton.setIcon(new ImageIcon(getClass().getResource("/button4.png")));
    }//GEN-LAST:event_avaliarButtonMouseExited

    private void listaMusicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMusicasMouseClicked
        try {
            // int IndexMusica = listaMusicas.getSelectedIndex();
            // Musica musica = listaMusicas.getModel().getElementAt(IndexMusica);
            //  int id = musica.getId();
            //  mp3DataDao.setId(id);
            image = new ImageIcon(mp3DataDao.recebeImagemBlob());
            // JOptionPane.showMessageDialog(null, String.format("id: %d", musica.getId()));         
            //  mp3DataDao.setId(id);
            playing = true;
            actionMouseClicked(evt);
            //  System.out.print("id: " + mp3DataDao.getId());
            slider.setValue(0);
            clipLoad();
            setTotalDuration();
            labelPic.setIcon(image);
            botaoNext = true;

        } catch (SQLException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_listaMusicasMouseClicked


    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
        //---------------------Slider controler-----------------------------------
        Timer delayedUpdate = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                int frame = getDesiredFrame();
                clip.setFramePosition(frame);

                int time = (int) getCurrentTime();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos = converteSeg(time);
                labelTimeCounter.setText(baos.toString());

                //   currentFrame.setText("Current frame: " + frame);
                //  currentDuration.setText("Current duration: " + time);
            }
        });

        delayedUpdate.setRepeats(false);

        if (ignoreStateChange) {
            return;
        }
        delayedUpdate.restart();


    }//GEN-LAST:event_sliderStateChanged

    private void totalDurationAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_totalDurationAncestorAdded
        int time = (int) duration;
        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos = converteSeg(time);
        //  totalFrameDuration.setText("Total frame duration:" + frameCount);
        totalDuration.setText(baos.toString());
    }//GEN-LAST:event_totalDurationAncestorAdded

    private void actionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actionMouseClicked
        // labelPic.setIcon(image);
        //carrega imagemIcon com blob

        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();
        if (!playing) {

            //responsavel por atualizar o timer
            int frame = getDesiredFrame();
            if (frame >= frameCount) {
                frame = 0;
            }
            clip.setFramePosition(frame);

            // labelFileName.setText("Playing File: " + audioFilePath); //not important
            System.out.print((int) clip.getMicrosecondLength() / 1_000_000);
            // clip lenght in seconds? this feature will set the slider lenght to the same length of the audio clip
            clip.start();
            action.setText("Stop");
            playing = true;
            playTimer.start();
        } else {

            clip.stop();
            action.setText("Play");
            playing = false;
            playTimer.stop();
        }


    }//GEN-LAST:event_actionMouseClicked

    private void listaRecomendacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaRecomendacoesMouseClicked

        try {
            int IndexMusica = listaRecomendacoes.getSelectedIndex();
            Musica musica = listaRecomendacoes.getModel().getElementAt(IndexMusica);
            int id = musica.getId();
            mp3DataDao.setId(id);

            image = new ImageIcon(mp3DataDao.recebeImagemBlob());
            JOptionPane.showMessageDialog(null, String.format("id: %d", musica.getId()));

            playing = true;
            actionMouseClicked(evt);
            System.out.print("id: " + mp3DataDao.getId());
            slider.setValue(0);
            clipLoad();
            setTotalDuration();
            labelPic.setIcon(image);
            botaoNext = true;

        } catch (SQLException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(TelaAvaliacaoMusica.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_listaRecomendacoesMouseClicked

    public void updateState() {
        ignoreStateChange = true;
        int frame = clip.getFramePosition();
        int progress = (int) (((double) frame / (double) frameCount) * 100);
        slider.setValue(progress);
//        currentFrame.setText("Current frame: " + getDesiredFrame());

        int time = (int) getCurrentTime();
        int valor = time;
        int horas = valor / 3600;
        int restoHoras = valor % 3600;
        int minutos = restoHoras / 60;
        int restoMinutos = restoHoras % 60;
        int segundos = restoMinutos;

        //  System.out.printf(String.valueOf(slider.getValue()));
        // System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
// Put things back
        System.out.flush();
        System.setOut(old);
// Show what happened
        labelTimeCounter.setText(baos.toString());
        //  currentDuration.setText("Current duration: " + getCurrentTime());
        ignoreStateChange = false;
    }

    public double getCurrentTime() {
        int currentFrame = clip.getFramePosition();
        double time = (double) currentFrame / format.getFrameRate();
        return time;
    }

    public int getDesiredFrame() {
        int progress = slider.getValue();
        double frame = ((double) frameCount * ((double) progress / 100.0));
        return (int) frame;
    }

    public void clipLoad() throws SQLException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream converted = null;
        try {
            // File file = new File("C:\\Users\\jedaf\\Desktop\\meu-lugar.wav");

            // mp3DataDao = DaoFactory.createMp3DataDao();
            Conversor convert = new Conversor();
            //   mp3DataDao.InsereImageBlob();
            //mp3DataDao.atualizaBlob();
            //  mp3DataDao.InsereBlob();
            //  mp3DataDao.InsereImageBlob();
            //  InsereImageBlob();
            // mp3DataDao.InsereFramerate();

            //pega a musica do db
            InputStream iS = mp3DataDao.recebeInputStream();
            //converte mp3 em wav apaga a copia da memoria rom deixando a musica apenas no buffer 
            byte[] byteArray = convert.mp3toWav(iS);

            //passamos o array de bytes para um formato aceito pela classe AudioInpuntStream e depois pegamos o cabeçalho para montar o audio
            ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
            long frameRate = mp3DataDao.recebeFrameRate();
            //montamos o audio
            converted = new AudioInputStream(bis, convert.getAudioFormat(), frameRate);
            // converted = AudioSystem.getAudioInputStream(file);
            //print que retorna se o arquivo wav foi apagado ou nao
            System.out.println(convert.getBool());

            format = converted.getFormat();
            frameCount = converted.getFrameLength();
            duration = ((double) frameCount) / format.getFrameRate();

            clip = AudioSystem.getClip();
            clip.open(converted);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public ByteArrayOutputStream converteSeg(int time) {
        int valor = time;
        int horas = valor / 3600;
        int restoHoras = valor % 3600;
        int minutos = restoHoras / 60;
        int restoMinutos = restoHoras % 60;
        int segundos = restoMinutos;

        System.out.printf(String.valueOf(slider.getValue()));
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
// Tell Java to use your special stream
        System.setOut(ps);
// Print some output: goes to your special stream
        System.out.printf("%02d:%02d:%02d\n", horas, minutos, segundos);
// Put things back
        System.out.flush();
        System.setOut(old);

        return baos;
    }

    public void setTotalDuration() {
        int time = (int) duration;
        //Mp3DataDao mp3DataDao = DaoFactory.createMp3DataDao();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos = converteSeg(time);
        //  totalFrameDuration.setText("Total frame duration:" + frameCount);
        totalDuration.setText(baos.toString());

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ListaPainel1;
    private javax.swing.JScrollPane ListaPainel2;
    private javax.swing.JButton action;
    private javax.swing.JLabel alertaLabel;
    private javax.swing.JLabel avaliarButton;
    private javax.swing.JLabel confirmacaoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel labelPic;
    private javax.swing.JLabel labelTimeCounter;
    private javax.swing.JList<Musica> listaMusicas;
    private javax.swing.JList<Musica> listaRecomendacoes;
    private javax.swing.JTextField notaText;
    private javax.swing.JLabel setaRetorno;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel totalDuration;
    // End of variables declaration//GEN-END:variables
    //private Color color;
}

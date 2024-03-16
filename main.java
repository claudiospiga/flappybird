package prova1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JPanel implements ActionListener, KeyListener {
    // Variabili per l'uccello
    private int altezzaUccello = 200;
    private int velocitaUccello = 0;
    private int gravita = 1;

    // Variabili per i tubi
    private int distanzaTubi = 250;
    private int posXtubo = 800;
    private int larghezzaTubo = 100;
    private int altezzaTubo = 400;

    // Variabile per il punteggio
    private int punteggio = 0;

    // Timer per aggiornare il gioco ad intervalli regolari
    private Timer timer;

    // Variabile per gestire lo stato del gioco
    private boolean partitaTerminata = false;

    // Pulsanti per avviare e ricominciare il gioco
    private JButton bottoneInizia;
    private JButton bottoneRiavvia;

    // Costruttore
    public main() {
        // Imposta le dimensioni del pannello
        setPreferredSize(new Dimension(1000, 800));
        // Imposta il colore di sfondo
        setBackground(Color.CYAN);
        // Abilita il focus per gestire gli eventi da tastiera
        setFocusable(true);
        // Aggiunge i listener per i tasti
        addKeyListener(this);

        // Crea il pulsante per avviare il gioco
        bottoneInizia = new JButton("Inizia Partita");
        bottoneInizia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniziaPartita();
            }
        });
        add(bottoneInizia);

        // Crea il pulsante per ricominciare il gioco
        bottoneRiavvia = new JButton("Riavvia");
        bottoneRiavvia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                riavviaPartita();
            }
        });
        bottoneRiavvia.setVisible(false);
        add(bottoneRiavvia);

        // Crea il timer con un intervallo di 20 millisecondi e lo collega all'azione
        timer = new Timer(20, this);
    }

    // Metodo per avviare il gioco
    private void iniziaPartita() {
        // Nasconde il pulsante di inizio
        remove(bottoneInizia);
        // Nasconde il pulsante di ricominciare
        bottoneRiavvia.setVisible(false);
        // Avvia il timer
        timer.start();
        // Richiede il focus per gestire gli eventi da tastiera
        requestFocusInWindow();
    }

    // Metodo per ricominciare il gioco
    private void riavviaPartita() {
        // Azzera il punteggio
        punteggio = 0;
        // Riporta l'uccello alla posizione di partenza
        altezzaUccello = 200;
        // Riporta il tubo alla posizione iniziale
        posXtubo = 800;
        // Azzera la velocità dell'uccello
        velocitaUccello = 0;
        // Ripristina lo stato del gioco
        partitaTerminata = false;
        // Nasconde il pulsante di ricominciare
        bottoneRiavvia.setVisible(false);
        // Avvia il timer
        timer.start();
        // Richiede il focus per gestire gli eventi da tastiera
        requestFocusInWindow();
    }

    // Metodo per disegnare gli elementi del gioco
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna i tubi verdi
        g.setColor(Color.RED);
        g.fillRect(posXtubo, 0, larghezzaTubo, altezzaTubo);
        g.fillRect(posXtubo, altezzaTubo + distanzaTubi, larghezzaTubo, getHeight() - altezzaTubo - distanzaTubi);

        // Disegna l'uccello rosso
        g.setColor(Color.PINK);
        g.fillRect(100, altezzaUccello, 50, 50);

        // Disegna il punteggio
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Punteggio: " + punteggio, 20, 40);

        // Se il gioco è finito, mostra il messaggio "Partita Terminata!" e il pulsante per ricominciare
        if (partitaTerminata) {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Partita Terminata!", 250, 300);
            bottoneRiavvia.setVisible(true);
        }
    }

    // Metodo chiamato ad ogni intervallo del timer per aggiornare lo stato del gioco
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!partitaTerminata) {
            // Aggiorna la velocità verticale dell'uccello applicando l'effetto della gravità
            velocitaUccello += gravita;
            // Aggiorna la posizione verticale dell'uccello in base alla sua velocità
            altezzaUccello += velocitaUccello;
            // Muove il tubo verso sinistra
            posXtubo -= 5;

            // Se il tubo esce dallo schermo, genera un nuovo tubo e incrementa il punteggio
            if (posXtubo < -larghezzaTubo) {
                posXtubo = getWidth();
                altezzaTubo = 200 + (int) (Math.random() * 200);
                punteggio++;
            }

            // Controlla se l'uccello collide con un tubo o esce dallo schermo
            if (altezzaUccello < altezzaTubo || altezzaUccello + 50 > altezzaTubo + distanzaTubi) {
                if (posXtubo < 150 && posXtubo + larghezzaTubo > 100) {
                    partitaTerminata = true;
                }
            }

            // Ridisegna il pannello
            repaint();
        }
    }

    // Metodi per gestire gli eventi da tastiera
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Se il tasto della barra spaziatrice è premuto, l'uccello salta
            velocitaUccello = -10; // Imposta una velocità verso l'alto
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    // Metodo main per avviare il programma
    public static void main(String[] args) {
        // Crea e configura la finestra del gioco
        JFrame frame = new JFrame("Flappy Bird");
        main gioco = new main();
        frame.add(gioco);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

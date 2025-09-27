package com.example.escriturarapida;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Random;

public class HelloController {
    @FXML
    private TextField inputTexto;
    @FXML
    private Label textoAleatorio;
    @FXML
    private Label label2Error;
    @FXML
    private Label label1Error;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label levelUp;
    @FXML
    private Label countDownTime;



    //lista de palabras disponibles...
    private String[] palabras = {
            "java", "rapido", "codigo", "pantalla", "teclado", "nivel", "yuliana",
            "raton", "ventana", "monitor", "programa", "internet", "red", "router",
            "archivo", "carpeta", "juego", "clase", "objeto", "metodo", "variable",
            "compilar", "ejecutar", "error", "tiempo", "logica", "memoria", "algoritmo",
            "bucle", "condicion", "matriz", "vector", "dato", "numeros", "texto",
            "prueba", "desafio", "palabra", "frase", "linea", "editor", "proyecto",
            "funcion", "sistema", "usuario", "clave", "seguridad", "servidor", "nube",
            "mouse", "tecla", "codigo", "desarrollar", "actualizar", "crear", "jugar",
            "pintar", "dibujar", "correr", "abrir", "cerrar", "guardar", "buscar",
            "aprender", "pensar", "resolver", "ganar", "perder", "subir", "bajar",
            "lento", "rapido", "fuerza", "energia", "sol", "luna", "estrella", "planeta",
            "cielo", "tierra", "agua", "fuego", "aire", "bosque", "mar", "rio",
            "puerta", "mesa", "silla", "casa", "perro", "gato", "auto", "tren", "avion",
            "futuro", "pasado", "presente", "tiempo", "historia", "mundo", "viaje"
    };
    /** Indicates if the last input was incorrect. */
    private  boolean esError=false;
    /** Flag that indicates if the game has already started. */
    private boolean startGame=false;
    /** Random generator for word selection. */
    private Random random=new Random();
    /** Number of correct words typed in the current level. */
    private int palabrasCorrectas=0;
    /** Current game level. */
    private  int subirNivel=1;

    /** Remaining time in seconds for the countdown. */
    private  int tiempoRegresivo=20;
    /** indicates the amount of time to reduce */
    private int decremento=0;

    /** Timeline object to control the countdown animation. */
    private Timeline countdown;



    /**
     * Initializes the controller.
     * <p>
     * This method is automatically executed when the FXML is loaded.
     * It hides error labels and shows the first random word.
     * </p>
     */
    @FXML
    public void initialize(){
        mostrarPalabraAleatoria();
        label1Error.setVisible(false);
        label1Error.setManaged(false);
        label2Error.setVisible(false);
        label2Error.setManaged(false);

    }

    /**
     * Validates the user input when clicking the button or pressing Enter.
     * <p>
     * If the word is correct, the progress increases and the level may go up.
     * If it is incorrect, error messages are shown.
     * </p>
     */
    @FXML
    protected void onBotonValidarClick() {
        String respuestaUsuario = inputTexto.getText();
        String labelTexto=textoAleatorio.getText();

        if (respuestaUsuario.equals(labelTexto) ){
            palabrasCorrectas++;
            double progreso=(double) palabrasCorrectas/5.0;

            if (progreso==1.0){
                subirNivel++;
                levelUp.setText(String.valueOf(subirNivel));
                palabrasCorrectas=0;
                progreso=0.0;
                decremento=decremento+2;
                tiempoRegresivo=20-decremento;
            }
            progressBar.setProgress(progreso);



            if (!startGame){
                startGame=true;
                mostrarCuentaRegresiva();
            }

            label1Error.setText("PERFECT");
            label1Error.setStyle("-fx-text-fill:#0A7500; -fx-font-size:25;");
            label1Error.setVisible(true);
            label1Error.setManaged(true);
            inputTexto.clear();
            mostrarPalabraAleatoria();
            if (esError){
                label2Error.setVisible(false);
            }

            PauseTransition desaparecerPerfecto=new PauseTransition(Duration.seconds(1.2));
            desaparecerPerfecto.setOnFinished(envet->{
                label1Error.setVisible(false);
                label1Error.setManaged(false);
            });
            desaparecerPerfecto.play();

        }else {
            inputTexto.clear();
            label1Error.setStyle("-fx-text-fill:#750200; -fx-font-size:25;");
            label1Error.setText("Error");
            label1Error.setVisible(true);
            label1Error.setManaged(true);
            label2Error.setVisible(true);
            label2Error.setManaged(true);
            esError=true;
            label2Error.setText("se digitó "+ respuestaUsuario+ "en lugar de "+ labelTexto);

        }


    }
    /**
     * Starts and displays the countdown timer.
     * <p>
     * This method creates a {@link Timeline} that decreases
     * {@code tiempoRegresivo} every second and updates the label.
     * When the time reaches 0, the game ends.
     * </p>
     */
    protected void mostrarCuentaRegresiva() {
        tiempoRegresivo = 20;
        countDownTime.setText(tiempoRegresivo + "s");

        if (countdown != null) {
            countdown.stop();
        }

        countdown= new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    tiempoRegresivo--;
                    countDownTime.setText(tiempoRegresivo + "s");

                    if (tiempoRegresivo <= 0) {
                        countDownTime.setText("Se acabó el tiempo");

                        if (countdown != null) {
                            countdown.stop();
                        }
                        inputTexto.setDisable(true);
                    }
                })
        );
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }


    /**
     * Chooses and displays a random word from the {@code palabras} array.
     */
    protected void mostrarPalabraAleatoria(){
        int index=random.nextInt(palabras.length);
        textoAleatorio.setText(palabras[index]);

    }

    /**
     * Resets game variables to initial state.
     * <p>
     * This method stops the countdown, resets the level,
     * progress, errors and enables the input field.
     * </p>
     */
    @FXML
    protected void onResetearValores(){
        startGame=false;
       levelUp.setText(String.valueOf(1));
       tiempoRegresivo=20;
        palabrasCorrectas = 0;
        progressBar.setProgress(0.0);
        if (countdown != null) {
            countdown.stop();
            countdown = null;
        }
        countDownTime.setText(tiempoRegresivo + "s");
        inputTexto.setDisable(false);


    }





    @FXML
    private Label welcomeText;
    /**
     * Sample action for testing the Hello button.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}

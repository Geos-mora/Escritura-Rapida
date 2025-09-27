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

    private  boolean esError=false;
    private boolean startGame=false;
    private Random random=new Random();
    private int palabrasCorrectas=0;
    private  int subirNivel=1;
    private  int tiempoRegresivo=20;
    private int decremento=0;
    private Timeline countdown;




    @FXML
    public void initialize(){
        mostrarPalabraAleatoria();
        label1Error.setVisible(false);
        label1Error.setManaged(false);
        label2Error.setVisible(false);
        label2Error.setManaged(false);

    }


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



    protected void mostrarPalabraAleatoria(){
        int index=random.nextInt(palabras.length);
        textoAleatorio.setText(palabras[index]);

    }

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

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}

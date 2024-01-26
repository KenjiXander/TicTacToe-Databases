package com.example.application.views.game;

import com.example.application.TicTacToeGame;
import com.example.application.views.MainLayout;
import com.example.application.views.mymain.MyMainView;
import com.example.application.views.stats.EstadisticasView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@PageTitle("Game")
@Route(value = "game", layout = MainLayout.class)
public class GameView extends VerticalLayout implements HasUrlParameter<String> {

    private final TicTacToeGame ticTacToe = new TicTacToeGame();
    private Button[][] botonesTablero = new Button[3][3];
    private TextField textoGanador = new TextField("Ganador:");

    private int ganaX = 0;
    private int ganaO = 0;

    private TextField ganaXTexto = new TextField("");
    private TextField ganaOTexto = new TextField("");


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            String[] parts = parameter.split("-");
            if (parts.length == 3) {
                String simboloJugadorPrincipio = parts[0];
                String jugador1 = parts[1];
                String jugador2 = parts[2];

                // Establece el símbolo del jugador inicial en el juego
                ticTacToe.setSimboloJugadorPrincipio(simboloJugadorPrincipio);

                // Asume que existen métodos para establecer los nombres de los jugadores en TicTacToeGame
                // Si no existen, deberás agregarlos.
                ticTacToe.setNombreJugador1(jugador1);
                ticTacToe.setNombreJugador2(jugador2);

                // Prepara y muestra el tablero y la estructura del juego
                mostrarTablero();
                estructuraJuego();
            } else {
                // Manejar el caso en que el parámetro no tenga el formato esperado
                getUI().ifPresent(ui -> ui.navigate(MyMainView.class));
            }
        } else {
            // Manejar el caso en que no se proporcionen parámetros
            getUI().ifPresent(ui -> ui.navigate(MyMainView.class));
        }
    }


    private void mostrarTablero() {
        VerticalLayout disenoVertical = new VerticalLayout();
        disenoVertical.setWidth("min-content");
        disenoVertical.setAlignItems(Alignment.CENTER);

        for (int fila = 0; fila < 3; fila++) {
            HorizontalLayout botonFila = new HorizontalLayout();
            botonFila.setWidth("min-content");
            for (int colum = 0; colum < 3; colum++) {
                Button botonTablero = crearBotonTablero(fila, colum);
                botonesTablero[fila][colum] = botonTablero;
                botonFila.add(botonTablero);
            }
            disenoVertical.add(botonFila);
        }

        add(crearBotonRegreso());
        add(disenoVertical);
        add(textoGanador);
        add(new Hr());
        add(ganaXTexto);
        add(ganaOTexto);
//        add(reiniciarVictoriasBoton());
        add(crearBotonEstadisticas());
    }




//    private Button reiniciarVictoriasBoton() {
//        Button reiniciarVictoriasBoton = new Button("Reiniciar Victorias");
//        reiniciarVictoriasBoton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        reiniciarVictoriasBoton.addClickListener(e -> reiniciarVictorias());
//        return reiniciarVictoriasBoton;
//    }

    private void reiniciarVictorias() {
        ganaX = 0;
        ganaO = 0;
        ganaXTexto.setValue("");
        ganaOTexto.setValue("");
    }

    private Button crearBotonTablero(int fila, int colum) {
        Button boton = new Button();
        boton.setWidth("min-content");
        boton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        boton.addClickListener(e -> clicksBotonesTablero(fila, colum));
        return boton;
    }

    private void clicksBotonesTablero(int fila, int colum) {
        Button botonClick = botonesTablero[fila][colum];

        // Verifica si la celda ya está ocupada
        if (!botonClick.getText().isEmpty()) {
            // La celda ya está ocupada, puedes mostrar un mensaje o simplemente ignorar el clic.
            return;
        }

        // Realiza la jugada en el modelo del juego
        ticTacToe.hacerMovimiento(fila, colum);

        // Obtén el símbolo del jugador actual (X o O)
        String simboloJugadorActual = ticTacToe.getsimboloJugadorActual();

        simboloJugadorActual = simboloJugadorActual.equals("X") ? "O" : "X";

        // Establece el símbolo en la celda
        botonClick.setText(simboloJugadorActual);

        if (ticTacToe.ganaJuego()) {
            String simboloGanador = ticTacToe.getsimboloJugadorActual();
            mostrarGanador(simboloGanador);
            contadorVictorias(simboloGanador);
            return;
        }

        // Verifica si el juego está en empate
        if (ticTacToe.empate()) {
            empate();
            // Puedes reiniciar el juego o realizar otras acciones después de un empate.
            return;
        }

        // No es necesario cambiar al siguiente jugador aquí, ya que se maneja en TicTacToeGame.hacerMovimiento()
    }








    private void mostrarGanador(String simboloGanador) {
        String nombreGanador = "";
        String simboloFinal = "";

        // Verifica quién es el ganador y qué símbolo eligió
        if (simboloGanador.equals("X")) {
            if (ticTacToe.getSimboloJugador1().equals("X")) {
                nombreGanador = ticTacToe.getNombreJugador1();
            } else {
                nombreGanador = ticTacToe.getNombreJugador2();
            }
            simboloFinal = "X";
        } else if (simboloGanador.equals("O")) {
            if (ticTacToe.getSimboloJugador1().equals("O")) {
                nombreGanador = ticTacToe.getNombreJugador1();
            } else {
                nombreGanador = ticTacToe.getNombreJugador2();
            }
            simboloFinal = "O";
        }

        textoGanador.setValue("Ganador: " + nombreGanador + " (" + simboloFinal + ")");
        desactivarBotonesTablero();
    }









//    private void mostrarGanador(String simboloGanador) {
//        String nombreGanador = "";
//        String simboloFinal = "";
//
//        textoGanador.setValue(ticTacToe.getsimboloJugadorActual());
//
//        if (ticTacToe.getsimboloJugadorActual().equals("O")) {
//            nombreGanador = simboloGanador.equals("O") ? ticTacToe.getNombreJugador1() : ticTacToe.getNombreJugador2();
//            simboloFinal = simboloGanador.equals("X") ? "X" : "O";
//        } else {
//            nombreGanador = simboloGanador.equals("O") ? ticTacToe.getNombreJugador2() : ticTacToe.getNombreJugador1();
//            simboloFinal = simboloGanador.equals("X") ? "X" : "O";
//        }
//
//
////        simboloFinal = simboloGanador.equals("X") ? "O" : "X";
//        textoGanador.setValue("Ganador: " + nombreGanador + " (" + simboloGanador + ")");
//        desactivarBotonesTablero();
//    }







//    private void mostrarGanador(String simboloGanador) {
//        String nombreGanador = "";
//        String simboloFinal = "";
//
//        if (simboloGanador.equals("X")) {
//            nombreGanador = simboloGanador.equals("X") ? ticTacToe.getNombreJugador2() : ticTacToe.getNombreJugador1();
//
//        } else if (simboloGanador.equals("O")) {
//            nombreGanador = simboloGanador.equals("O") ? ticTacToe.getNombreJugador1() : ticTacToe.getNombreJugador2();
//        }
//
//        simboloFinal = simboloGanador.equals("X") ? "O" : "X";
//        textoGanador.setValue("Ganador: " + nombreGanador + " (" + simboloFinal + ")");
//        desactivarBotonesTablero();
//    }















    private void contadorVictorias(String simboloGanador) {
        if (simboloGanador.equals("X")) {
            ganaX++;
            ganaXTexto.setValue("Victorias " + ticTacToe.getNombreJugador2() + ": " + ganaX);
        } else if (simboloGanador.equals("O")) {
            ganaO++;
            ganaOTexto.setValue("Victorias " + ticTacToe.getNombreJugador1() + ": " + ganaO);
        }
    }


    private void empate() {
        textoGanador.setValue("Empate");

        // Deshabilita todos los botones después de un empate
        desactivarBotonesTablero();
    }

    private void desactivarBotonesTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int colum = 0; colum < 3; colum++) {
                botonesTablero[fila][colum].setEnabled(false);
            }
        }
    }

    private void estructuraJuego() {
        setWidth("100%");
        setAlignItems(Alignment.CENTER);

        HorizontalLayout disenoHorizontal = new HorizontalLayout();
        disenoHorizontal.setWidthFull();
        disenoHorizontal.setAlignItems(Alignment.CENTER);

        // Crea un componente vacío para hacer crecer flexiblemente el espacio a la izquierda
        VerticalLayout espacioIzquierda = new VerticalLayout();
        espacioIzquierda.setFlexGrow(1, espacioIzquierda);
        disenoHorizontal.add(espacioIzquierda);

        // Agrega tu componente principal (formLayout3Col) al centro
        FormLayout formLayout3Colum = new FormLayout();
        formLayout3Colum.setWidth("100%");
        disenoHorizontal.add(formLayout3Colum);

        // Crea otro componente vacío para hacer crecer flexiblemente el espacio a la derecha
        VerticalLayout espacioDerecha = new VerticalLayout();
        espacioDerecha.setFlexGrow(1, espacioDerecha);
        disenoHorizontal.add(espacioDerecha);

        // Agrega el botón de reinicio
        Button botonReiniciar = new Button("Reiniciar");
        botonReiniciar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonReiniciar.addClickListener(e -> reiniciarJuego());
        disenoHorizontal.add(botonReiniciar);

        add(disenoHorizontal);
    }


    private void reiniciarJuego() {
        // Restablecer el modelo del juego
        ticTacToe.reiniciarJuego();

//         Limpiar los textos en los botones
        for (int fila = 0; fila < 3; fila++) {
            for (int colum = 0; colum < 3; colum++) {
                botonesTablero[fila][colum].setText("");
                botonesTablero[fila][colum].setEnabled(true);
            }
        }

        // Limpiar el TextField
        textoGanador.setValue("");
    }

    private Button crearBotonEstadisticas() {
        Button botonEstadisticas = new Button("Ver estadísticas");
        botonEstadisticas.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonEstadisticas.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(EstadisticasView.class))); // Reemplaza "ruta-de-estadisticas" con la ruta real
        return botonEstadisticas;
    }

    private Button crearBotonRegreso() {
        Button botonRegreso = new Button("Regresar a escoger jugador");
        botonRegreso.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(MyMainView.class)));
        return botonRegreso;
    }

}
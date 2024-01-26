package com.example.application.views.mymain;

import com.example.application.model.Jugador;
import com.example.application.service.JugadorService;
import com.example.application.views.MainLayout;
import com.example.application.views.game.GameView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Tic Tac Toe")
@Component
public class MyMainView extends VerticalLayout {

    private TextField nombreJugador1;
    private TextField nombreJugador2;
    private String simboloJugador1;
    private VerticalLayout contentLayout;
    private final JugadorService jugadorService;

    // Declarar las variables necesarias
    private Grid<Jugador> gridJugadores;
    private Jugador jugadorSeleccionado1;
    private Jugador jugadorSeleccionado2;

    @Autowired
    public MyMainView(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H2 titulo = new H2("Tic Tac Toe");
        titulo.getStyle().set("margin-bottom", "20px");

        contentLayout = new VerticalLayout();
        contentLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        contentLayout.setSpacing(true);

        nombreJugador1 = new TextField("Nombre del Jugador 1");
        Button registrarJugador1 = new Button("Registrar", e -> elegirSimbolo());

        contentLayout.add(nombreJugador1, registrarJugador1);
        add(titulo, contentLayout);


        gridJugadores = new Grid<>(Jugador.class);
        gridJugadores.setColumns("nombre"); // Asegúrate de que solo se muestren las columnas relevantes
        gridJugadores.asSingleSelect().addValueChangeListener(event -> seleccionarJugador(event.getValue()));

        contentLayout.add(gridJugadores);
        actualizarListaJugadores();
    }

    private void elegirSimbolo() {
        if (!nombreJugador1.getValue().trim().isEmpty()) {
            contentLayout.removeAll();

            Button escogerX = new Button("Jugar con X", e -> {
                simboloJugador1 = "X";
                ingresarJugador2();
            });
            escogerX.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Button escogerO = new Button("Jugar con O", e -> {
                simboloJugador1 = "O";
                ingresarJugador2();
            });
            escogerO.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            contentLayout.add(escogerX, escogerO);
        }
    }

    private void ingresarJugador2() {
        contentLayout.removeAll();

        nombreJugador2 = new TextField("Nombre del Jugador 2");
        Button registrarJugador2 = new Button("Registrar", e -> empezarJuego());

//        Button escogerX = new Button("Jugar con X", e -> {
//            simboloJugador1 = "X";
//            ingresarJugador2();
//        });
//        escogerX.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//
//        Button escogerO = new Button("Jugar con O", e -> {
//            simboloJugador1 = "O";
//            ingresarJugador2();
//        });
//        escogerO.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        contentLayout.add(nombreJugador2, registrarJugador2);
//        contentLayout.add(nombreJugador2, registrarJugador2, escogerX, escogerO);
    }


//    private void empezarJuego() {
//        if (!nombreJugador2.getValue().trim().isEmpty()) {
//            String simboloJugador2 = simboloJugador1.equals("X") ? "O" : "X";
//            getUI().ifPresent(ui -> ui.navigate(GameView.class, simboloJugador1 + "-" + nombreJugador1.getValue() + "-" + nombreJugador2.getValue()));
//        }
//    }


    private void empezarJuego() {
        if (!nombreJugador1.getValue().trim().isEmpty() && !nombreJugador2.getValue().trim().isEmpty()) {
            String simboloJugador2 = simboloJugador1.equals("X") ? "O" : "X";

            // Registrar jugadores en la base de datos
            registrarJugadorEnBaseDatos(nombreJugador1.getValue());
            registrarJugadorEnBaseDatos(nombreJugador2.getValue());

            // Navegar a la vista del juego
            getUI().ifPresent(ui -> ui.navigate(GameView.class, simboloJugador1 + "-" + nombreJugador1.getValue() + "-" + nombreJugador2.getValue()));
        }
    }

    private void seleccionarJugador(Jugador jugador) {
        if (jugadorSeleccionado1 == null) {
            jugadorSeleccionado1 = jugador;
            nombreJugador1.setValue(jugador.getNombre());
            actualizarListaJugadores(); // Actualizar la lista para excluir al jugador seleccionado
        } else {
            jugadorSeleccionado2 = jugador;
            nombreJugador2.setValue(jugador.getNombre());
        }
    }

    private void actualizarListaJugadores() {
        List<Jugador> jugadores = jugadorService.findAllJugadores();
        if (jugadorSeleccionado1 != null) {
            jugadores.remove(jugadorSeleccionado1); // Excluir al jugador ya seleccionado
        }
        gridJugadores.setItems(jugadores);
    }

    private void registrarJugadorEnBaseDatos(String nombre) {
        Optional<Jugador> jugadorExistente = jugadorService.findJugadorPorNombre(nombre);
        if (!jugadorExistente.isPresent()) {
            Jugador nuevoJugador = new Jugador(nombre);
            jugadorService.saveJugador(nuevoJugador);
        }
    }

}

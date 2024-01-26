package com.example.application.views.stats;

import com.example.application.model.Jugador;
import com.example.application.repository.JugadorRepository;
import com.example.application.views.MainLayout;
import com.example.application.views.game.GameView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "estadisticas", layout = MainLayout.class)
@PageTitle("Estadísticas")
public class EstadisticasView extends VerticalLayout {

    private final JugadorRepository jugadorRepository;
    private Grid<Jugador> grid;

    @Autowired
    public EstadisticasView(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
        configurarGrid();
        add(crearBotonJuego());
    }

    private void configurarGrid() {
        grid = new Grid<>(Jugador.class);
        grid.setColumns("nombre", "partidasJugadas", "partidasGanadas", "partidasPerdidas", "partidasEmpatadas");
        grid.getColumnByKey("nombre").setHeader("Nombre del Jugador");
        grid.getColumnByKey("partidasJugadas").setHeader("Partidas Jugadas");
        grid.getColumnByKey("partidasGanadas").setHeader("Partidas Ganadas");
        grid.getColumnByKey("partidasPerdidas").setHeader("Partidas Perdidas");
        grid.getColumnByKey("partidasEmpatadas").setHeader("Partidas Empatadas");
        add(grid);
    }

    private Button crearBotonJuego() {
        Button botonRegresarJuego = new Button("Regresar al juego");
        botonRegresarJuego.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botonRegresarJuego.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(GameView.class))); // Reemplaza "ruta-de-estadisticas" con la ruta real
        return botonRegresarJuego;
    }

    private void actualizarLista() {
        grid.setItems(jugadorRepository.findAll());
    }
}

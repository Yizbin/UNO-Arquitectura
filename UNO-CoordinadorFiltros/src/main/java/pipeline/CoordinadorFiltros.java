/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pipeline;

import java.util.List;
import Plantilla.ContextoPipeline;
import Interfaces.IFiltro;
import Interfaces.IPump;
import Interfaces.ISink;
import java.util.ArrayList;

/**
 * Coordina la ejecución secuencial de un conjunto de filtros dentro de un
 * pipeline, procesando un contexto de entrada y enviando el resultado final a
 * un sink.
 *
 * Esta clase implementa el patrón Pipes and Filters, donde cada filtro recibe
 * un {@link ContextoPipeline}, lo procesa y devuelve un nuevo contexto que será
 * utilizado como entrada para el siguiente filtro.
 *
 * Si en cualquier punto del procesamiento el contexto es marcado como detenido,
 * la ejecución del pipeline se interrumpe y no se continúa con los filtros
 * restantes ni con el envío al sink.
 *
 * @param <I> tipo de dato de entrada del contexto inicial
 * @param <O> tipo de dato de salida esperado al finalizar el pipeline
 *
 * @author saula
 */
public class CoordinadorFiltros<I, O> implements IPump<I> {

    private final List<IFiltro<?, ?>> filtros;
    private ISink<O> sink;

    public CoordinadorFiltros() {
        this.filtros = new ArrayList<>();
    }

    /**
     * Permite ir armando la cadena de filtros dinámicamente en el Ensamblador.
     *
     * @param filtro
     */
    public void agregarFiltro(IFiltro<?, ?> filtro) {
        this.filtros.add(filtro);
    }

    public void procesar(ContextoPipeline<I> contexto) throws Exception {

        ContextoPipeline<?> contextoActual = contexto;

        for (IFiltro<?, ?> filtro : filtros) {
            if (contextoActual.estaDetenido()) {
                return;
            }

            contextoActual = procesarFiltro(filtro, contextoActual);
        }

        if (contextoActual.estaDetenido()) {
            return;
        }

        if (sink != null) {
            sink.enviar(castearContextoSalida(contextoActual));
        }
    }

    /**
     * Procesa un filtro individual realizando los casteos necesarios para
     * adaptar el tipo del contexto actual al tipo de entrada esperado por el
     * filtro.
     *
     * Este método utiliza genéricos y castings no verificados para poder
     * manejar una lista heterogénea de filtros encadenados dinámicamente.
     *
     * @param <TEntrada> tipo de entrada esperado por el filtro
     * @param <TSalida> tipo de salida producido por el filtro
     * @param filtro filtro a ejecutar
     * @param contextoActual contexto actual que será usado como entrada del
     * filtro
     * @return el nuevo contexto resultante después de aplicar el filtro
     * @throws Exception si ocurre un error durante la ejecución del filtro
     */
    @SuppressWarnings("unchecked")
    private <TEntrada, TSalida> ContextoPipeline<TSalida> procesarFiltro(
            IFiltro<?, ?> filtro,
            ContextoPipeline<?> contextoActual
    ) throws Exception {
        IFiltro<TEntrada, TSalida> filtroTipado = (IFiltro<TEntrada, TSalida>) filtro;
        ContextoPipeline<TEntrada> contextoTipado = (ContextoPipeline<TEntrada>) contextoActual;
        return filtroTipado.procesar(contextoTipado);
    }

    /**
     * Convierte el contexto final procesado al tipo de salida esperado por el
     * sink.
     *
     * @param contextoActual contexto final del pipeline
     * @return contexto casteado al tipo de salida {@code O}
     */
    @SuppressWarnings("unchecked")
    private ContextoPipeline<O> castearContextoSalida(ContextoPipeline<?> contextoActual) {
        return (ContextoPipeline<O>) contextoActual;
    }
}

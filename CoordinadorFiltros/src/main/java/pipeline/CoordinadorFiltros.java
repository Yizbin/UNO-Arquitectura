/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package pipeline;

import java.util.List;
import Interfaces.ContextoPipeline;
import Interfaces.IFiltro;
import Interfaces.ISink;

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
public class CoordinadorFiltros<I, O> {

    /**
     * Lista de filtros que se ejecutarán en orden secuencial.
     */
    private final List<IFiltro<?, ?>> filtros;
    /**
     * Destino final al que se enviará el contexto procesado una vez que todos
     * los filtros se hayan ejecutado correctamente.
     */
    private final ISink<O> sink;

    /**
     * Construye una instancia del coordinador de filtros.
     *
     * @param filtros lista de filtros que conforman el pipeline de
     * procesamiento
     * @param sink destino final que recibirá el contexto resultante
     */
    public CoordinadorFiltros(List<IFiltro<?, ?>> filtros, ISink<O> sink) {
        this.filtros = filtros;
        this.sink = sink;
    }

    /**
     * Ejecuta el pipeline completo sobre el contexto recibido.
     *
     * El contexto pasa por cada filtro en el orden definido en la lista
     * {@code filtros}. Si en algún momento el contexto indica que el proceso
     * debe detenerse, la ejecución termina inmediatamente.
     *
     * Si todos los filtros se ejecutan correctamente y el contexto final no
     * está detenido, el resultado se envía al sink configurado.
     *
     * @param contexto contexto inicial que contiene la información de entrada
     * para el pipeline
     * @throws Exception si alguno de los filtros o el sink produce un error
     * durante el procesamiento
     */
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

        sink.enviar(castearContextoSalida(contextoActual));
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

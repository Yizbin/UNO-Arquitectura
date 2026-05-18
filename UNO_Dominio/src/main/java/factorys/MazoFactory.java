package factorys;

//@author SAUL ISAAC APODACA BALDENEGRO 00000252020
import Entidades.Carta;
import Entidades.CartaAccion;
import Entidades.CartaComodin;
import Entidades.CartaNumero;
import Entidades.Mazo;
import Entidades.ConfiguracionPartida;
import Enums.Acciones;
import Enums.Comodines;
import Enums.TipoColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
/**
 * ESTA CLASE ES UNA FACTORY DE MAZO, SE ENCARGA DE CREAR EL MAZO PARA INICIAR
 * LA PARTIDA CREA LAS 108 CARTAS: 1 CARTA 0, 2 CARTAS DEL 1-9, 2 CARTAS DE CADA
 * TIPO DE ACCION, 4 CARTAS DE CADA COMODIN
 *
 * @author saula
 */
public class MazoFactory {

    public Mazo crear() {
        List<Carta> lista = new ArrayList<>(108);
        List<TipoColor> colores = List.of(TipoColor.ROJO, TipoColor.AZUL,
                TipoColor.AMARILLO, TipoColor.VERDE);

        for (TipoColor color : colores) {

            //1 CARTA 0
            lista.add(new CartaNumero(0, color, false));

            // 2 CARTAS DEL 1 AL 9
            for (int n = 1; n <= 9; n++) {
                for (int i = 1; i <= 2; i++) {
                    lista.add(new CartaNumero(n, color, false));
                }
            }

            //2 cartas por cada accion
            for (int n = 1; n <= 2; n++) {
                lista.add(new CartaAccion(Acciones.SALTA, color));
                lista.add(new CartaAccion(Acciones.REVERSA, color));
                lista.add(new CartaAccion(Acciones.TOMA_DOS, color));

            }
        }
        // 4 CAMBIO_COLOR y 4 TOMA_CUATRO
        for (int i = 0; i < 4; i++) {
            lista.add(new CartaComodin(Comodines.CAMBIO_COLOR));
            lista.add(new CartaComodin(Comodines.TOMA_CUATRO));
        }

        Collections.shuffle(lista);

        Stack<Carta> stack = new Stack<>();
        stack.addAll(lista);

        return new Mazo(stack);
    }
    
    
    public Mazo crear(ConfiguracionPartida configuracion){
        
        if (configuracion == null) {
        throw new IllegalArgumentException("La configuración no puede ser nula.");
        }

        List<Carta> lista = new ArrayList<>();
        List<TipoColor> colores = List.of(
                TipoColor.ROJO,
                TipoColor.AZUL,
                TipoColor.AMARILLO,
                TipoColor.VERDE
        );

        for (TipoColor color : colores) {
            for (int n = configuracion.getNumeroInicio(); n <= configuracion.getNumeroFin(); n++) {
                if (n == 0) {
                    lista.add(new CartaNumero(0, color, false));
                } else {
                    lista.add(new CartaNumero(n, color, false));
                    lista.add(new CartaNumero(n, color, false));
                }
            }

            for (int i = 1; i <= 2; i++) {
                lista.add(new CartaAccion(Acciones.SALTA, color));
                lista.add(new CartaAccion(Acciones.REVERSA, color));
                lista.add(new CartaAccion(Acciones.TOMA_DOS, color));
            }
        }

        for (int i = 0; i < configuracion.getNumComodines(); i++) {
            if (i % 2 == 0) {
                lista.add(new CartaComodin(Comodines.CAMBIO_COLOR));
            } else {
                lista.add(new CartaComodin(Comodines.TOMA_CUATRO));
            }
        }

        Collections.shuffle(lista);

        Stack<Carta> stack = new Stack<>();
        stack.addAll(lista);

        return new Mazo(stack);
    }

}

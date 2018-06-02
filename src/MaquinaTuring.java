import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MaquinaTuring {
    HashMap<String, Estado> estados;
    HashSet<String> alfabeto;
    ArrayList<String> cinta;
    Estado estadoInicial = null, estadoActual;
    int lector = 4;  // Iniciar a la mitad de la cinta

    public MaquinaTuring() {
        estados = new HashMap<>();
        alfabeto = new HashSet<>();
        cinta = new ArrayList<>(20);

        // Ingresar 10 casillas en blanco
        for (int i = 0; i < 10; i++) {
            cinta.add("B");
        }
    }

    public boolean addEstado(String estado){
        if (estados.get(estado) == null){
            estados.put(estado, new Estado(estado));

            if (estadoInicial == null){
                estadoInicial = estados.get(estado);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean addQuadrupla(String estado, String nextChar, String action, String nextState){
        Estado estado1 = estados.get(estado);

        if (estado1 == null) {
            System.err.println("No existe el estado " + estado);
            return false;
        }

        return estado1.addAction(nextChar, action, nextState);
    }

    public ArrayList<String> simular(String[] input){
        // Copiar el input a la cinta
        int contadorInput = 4;
        for (int i = 0; i < input.length; i++) {
            cinta.add(contadorInput, input[i]);
            contadorInput++;
        }

        estadoActual = estadoInicial;
        String inputActual;
        int loopContador = 0;
        while(loopContador < 1000000){
            // Obtener el input bajo el lector
            inputActual = cinta.get(lector);

            // Obtener quadrupla
            String[] quadrupla = estadoActual.getAction(inputActual);

            if (quadrupla == null){
                System.out.println("¡HE TERMINADO AMO! Ya no existen estados a los que me pueda mover");
                return cinta;
            }

            // Realizar accion
            String accion = quadrupla[0];
            switch (accion) {
                case "R":
                    lector++;
                    break;
                case "L":
                    lector--;
                    break;
                case "B":
                    cinta.set(lector, "B");
                    break;
                default:
                    // Verificar si el signo a escribir esta en el alfabeto
                    if (alfabeto.contains(accion)){
                        cinta.set(lector, accion);
                    } else {
                        System.err.println("ME HAZ FALLADO! El simbolo <" + accion + "> no es del alfabeto.");
                        return new ArrayList<>();
                    }
                    break;
            }

            // Nuevo estado
            String nuevoEstado = quadrupla[1];
            Estado estado = estados.get(nuevoEstado);

            if (estado == null){
                System.err.println("ME HAZ FALLADO! El estado <" + nuevoEstado + "> no me pertenece.");
                return new ArrayList<>();
            }

            estadoActual = estado;
            loopContador++;
        }

        System.err.println("OH NO! He quedado en un loop.");
        return new ArrayList<>();
    }

    public Estado getEstado(String estado){
        return estados.get(estado);
    }
}

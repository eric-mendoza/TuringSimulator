import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("TuringNator\n" +
                "Bienvenido al mejor simulador de máquinas de Turing que va a ver en su vida.\n" +
                "Asegurese de haber ingresado la especificacion de la maquina en el documento de texto que se encuentra " +
                "en la carpeta del proyecto.\n " +
                "Las reglas para especificar la máquina son las siguientes: \n" +
                    "\t1) Los titulos indican el inicio de cada una de las secciones. Deben estar en ese orden o no va a funcionar. (No fancy things this time)\n" +
                    "\t2) Para cada uno de los apartados, se debe ingresar la informacion separados por espacions. E.g. q1 q3 q4\n" +
                    "\t3) Para las cuadruplas, se debe escribir una cuadrupla por linea.\n" +
                    "\t4) Las reglas para escribir en la cinta son: R: moverse derecha, L: Moverse izquierda, B: espacio en blanco\n" +
                    "\t5) Los comentarios solo pueden estar en lineas solitos y se colocan con '#' en el primer caracter.\n" +
                    "\t6) El input debe de estar solamente en una linea y se asume que el estado inicial es el primero que se ingresa\n\n");

        System.out.println("________________________________________________________________________________________________________________________________");

        List<String> lines = Files.readAllLines(Paths.get("maquina.turing"), StandardCharsets.UTF_8);
        MaquinaTuring mt = new MaquinaTuring();
        String[] input = null;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // Verificar que no sea un comentario
            if (!line.equals("") && line.charAt(0) != '#'){

                // Guardar estados
                if (line.trim().equals("ESTADOS")){
                    // Obtener siguiente linea y seguir hasta que se encuentre el inicio de alfabeto
                    i++;
                    line = lines.get(i);

                    // ESTADOS
                    while(!line.equals("ALFABETO")){

                        // Verificar que no este vacia la linea
                        if (!line.equals("")){
                            // Dividir linea en estados
                            String[] estados = line.split(" ");

                            // Agregar estados
                            for (int j = 0; j < estados.length; j++) {
                                String nuevoEstado = estados[j];
                                mt.addEstado(nuevoEstado);
                            }
                        }

                        i++;
                        line = lines.get(i);
                    }

                    // Avanzar a siguiente linea
                    i++;
                    line = lines.get(i);

                    // ALFABETO
                    while(!line.equals("CUADRUPLAS")){

                        // Verificar que no este vacia la linea
                        if (!line.equals("")){
                            // Dividir linea en inputs
                            String[] alfabeto = line.split(" ");

                            // Agregar el alfabeto a la maquina de turing
                            mt.alfabeto.addAll(Arrays.asList(alfabeto));
                        }

                        i++;
                        line = lines.get(i);
                    }

                    // Avanzar a siguiente linea
                    i++;
                    line = lines.get(i);


                    // CUADRUPLAS
                    while(!line.equals("INPUT")){

                        // Verificar que no este vacia la linea
                        if (!line.equals("")){
                            // Dividir linea en cuadruplas
                            String[] cuadrupla = line.split(" ");

                            // Obtener el estado inicial
                            mt.addQuadrupla(cuadrupla[0], cuadrupla[1], cuadrupla[2], cuadrupla[3]);
                        }

                        i++;
                        line = lines.get(i);
                    }

                    // Avanzar al siguiente linea
                    i++;
                    line = lines.get(i);

                    // INPUT
                    while(true){

                        // Verificar que no este vacia la linea
                        if (!line.equals("")){
                            // Dividir linea en casillas
                            input = line.split(" ");
                            break;
                        }

                        i++;
                        line = lines.get(i);
                    }
                }
            }
        }


        // Realizar simulacion en la maquina de turing
        if (input == null){
            System.err.println("OH NO! Ha ocurrido un error desconocido.");
        }

        System.out.println("La cinta resultante es: \n");
        ArrayList<String> resultado = mt.simular(input);
        System.out.println(resultado.toString());

    }
}

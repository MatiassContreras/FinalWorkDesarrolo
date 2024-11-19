
//import java.io.*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class main {

    public static void main(String[] args) {
        // Inicializo Scanner
        Scanner sc = new Scanner(System.in);

        // Variables
        String rutaArchivoAviones = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/aviones.txt";
        String RutaArchivoVuelos = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/vuelos.txt";
        String RutaArchivoRutas = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/rutas.txt";
        int cantAviones = contarLineas(rutaArchivoAviones);
        int cantVuelos = contarLineas(RutaArchivoVuelos);
        int cantRuta = contarLineas(RutaArchivoRutas);
        avion[] ArrAviones = new avion[cantAviones];
        vuelo[] ArrVuelos = new vuelo[cantVuelos];
        ruta[] ArrRuta = new ruta[cantRuta];
        ArrAviones = retornarArregloAviones(cantAviones);
        ArrVuelos = retornarArregloVuelos(cantVuelos);
        ArrRuta = retornarArregloRuta(cantRuta);

        // Menú
        System.out.println("\nBienvenido al sistema de monitoreo y control de rutas, viajes y aviones \n"
                + "Que desea hacer hoy? \n"
                + "1 - Cargar un nuevo avion\n"
                + "2 - Cargar un nuevo vuelo\n"
                + "3 - Registrar un vuelo como terminado\n"
                + "");

        // Input de eleccion
        int opcion = sc.nextInt();
        sc.nextLine();

        // Operando la opción elegida
        switch (opcion) {
            case 1: // Carga un nuevo avion
                crearNuevoAvion(ArrAviones);
                cantAviones = contarLineas(rutaArchivoAviones);
                ArrAviones = new avion[cantAviones];
                ArrAviones = retornarArregloAviones(cantAviones);
                break;
            case 2: // Agrega un nuevo vuelo
                agregarVuelo(ArrAviones, ArrVuelos, ArrRuta);
                cantVuelos = contarLineas(RutaArchivoVuelos);
                ArrVuelos = new vuelo[cantVuelos];
                ArrVuelos = retornarArregloVuelos(cantVuelos);
                break;
            case 3: // Carga una ruta nueva
                llamarModuloRealizacion(ArrVuelos, ArrAviones, ArrRuta);
                break;
            default: // Default en caso de que no haga ninguna operacion anterior
                System.out.println("Entrada erronea o inexistente!");
                break;
        }

        // Cierro Scanner
        sc.close();

    }

    // Archivo AVIONES (Crear avion, cargar Aviones en el arreglo)
    public static void crearNuevoAvion(avion[] arregloAviones) {
        // Declaro Scanner
        Scanner sc = new Scanner(System.in);

        // Variables
        String newAvion = "";
        String id;
        boolean Cargado = true;

        // Input de Id
        System.out.println("Ingrese el identificador del avion que desea ingresar:");
        id = sc.nextLine();

        // Verificar si el ID ya existe en el arreglo
        for (int i = 0; i < arregloAviones.length; i++) {

            if (arregloAviones[i] != null && arregloAviones[i].getIdentificacion().equalsIgnoreCase(id)) {
                System.out.println("El identificador ya existe.");
                Cargado = false;
            }
        }

        // Validar que el ID cumple con las condiciones
        if (id.matches("^(LV|LQ)-([A-Z]{3}|X[0-9]{3}|S[0-9]{3}|[0-9]{3}|SX[0-9]{3})$") && Cargado == true) {
            String Aviones = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/aviones.txt";
            try {
                // FileWriter tiene como parametro true para habilitar el modo append
                // De edicion de archivo
                FileWriter escritorAvion = new FileWriter(Aviones, true);
                BufferedWriter bufferEscritura = new BufferedWriter(escritorAvion);
                // Llama a la funcion para ingresar el nuevo avion
                newAvion = ingresarAvion(id);
                // Lo escribe en el archivo
                bufferEscritura.write(newAvion + System.lineSeparator());
                // Cierra la escritura
                bufferEscritura.close();

                System.out.println("Avion cargado con exito!");
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                        + "que queriamos leer no existe.");
            } catch (IOException ex) {
                System.err.println("Error leyendo o escribiendo en algun archivo.");
            }
        } else
            System.out.println("El avion no pudo ser creado, el ID no cumple las estandarizaciones o ya existe su ID");

        // Cierro Scanner
        sc.close();
    }

    public static String ingresarAvion(String id) {
        // Declaro Scanner
        Scanner sc = new Scanner(System.in);

        // Variables
        String Avion = id + ";";
        String mod, cantAsientos, cantVuelos, cantKM;

        // Input de Modelo
        System.out.println("Ingrese el Modelo del avion que desea ingresar:");
        mod = sc.nextLine();

        // Input de Cantidad de vuelos
        System.out.println("Ingrese la cantidad de vuelos del avion que desea ingresar:");
        cantVuelos = sc.nextLine();

        // Input de Cantidad de asientos
        System.out.println("Ingrese la cantidad de asientos del avion que desea ingresar:");
        cantAsientos = sc.nextLine();

        // Input de Cantidad de KM recorridos
        System.out.println("Ingrese la cantidad de KM recorridos del avion que desea ingresar:");
        cantKM = sc.nextLine();

        // Formo String con los datos recopilados
        Avion += mod + ";" + cantVuelos + ";" + cantAsientos + ";" + cantKM + ";";

        // Cierro Scanner
        sc.close();

        // Retorno de variable
        return Avion;
    }

    public static avion[] retornarArregloAviones(int cantAviones) {

        // Crea la variable de arreglo que se retorna
        avion[] ArrAviones = new avion[cantAviones];

        // Aqui se define donde esta guardado del archivo.txt de los aviones
        String Aviones = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/aviones.txt";

        // Inicia la variable linea, que es la encargada de almacenar las lineas del
        // .txt
        String linea = null;

        try {
            // Lee el archivo de la variable Aviones
            FileReader lectorArchivo = new FileReader(Aviones);

            // Esto no entiendo que hace , calculo que activa la lectura de una formas mas
            // limpia o algo por el estilo jdaiawdnj
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);

            int i = 0;
            avion[] arregloAviones = new avion[cantAviones];
            while ((linea = bufferLectura.readLine()) != null) {
                // Separar cada línea en partes usando el punto y coma
                String[] parts = linea.split(";");
                // Crea dentro de la posicion 'i' el nuevo avion mediante la separacion del
                // metodo split
                arregloAviones[i] = new avion(parts[0], parts[1], parts[2], parts[3], parts[4]);
                i++;
            }

            ArrAviones = arregloAviones;
            bufferLectura.close();
            // bufferEscritura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                    + "que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }

        return ArrAviones;
    }

    // Archivo VUELOS (Agregar nuevo vuelo)
    public static void agregarVuelo(avion[] arregloAviones, vuelo[] arregloVuelos, ruta[] arregloRutas) {
        // Declaro Scanner
        Scanner sc = new Scanner(System.in);

        // Variables
        boolean Cargado = false;
        String idVuelo = "", dia = "", hora = "", idRuta = "", newVuelo = "";
        String idAvion;
        int j = 0, p = 0;

        // Input de id Avion
        System.out.println("Ingrese el identificador del avion que hace el vuelo:");
        idAvion = sc.nextLine();

        // Verifico si el avion ya existe en el archivo
        while (!Cargado && j < arregloAviones.length) {
            Cargado = (idAvion.equalsIgnoreCase(arregloAviones[j].getIdentificacion()) ? true : false);
            j++;
        }

        // Verifico si la ruta ya existe en el archivo
        if (Cargado) {
            System.out.println("Ingrese la ruta del vuelo"); // Falta comprobar si la ruta existe
            idRuta = sc.nextLine();
            Cargado = false;
            while (!Cargado && p < arregloRutas.length) {
                Cargado = (idRuta.equalsIgnoreCase(arregloRutas[p].getNroRuta()) ? true : false);
                p++;
            }
        } else {
            System.out.println("El avion ingresado no existe");
        }

        // Si el avion ya se encuentra cargado, verifica que la hora sea correcta
        if (Cargado) {
            System.out.println("Ingrese el dia del vuelo");
            dia = sc.nextLine();
            System.out.println("Ingrese la hora del vuelo");
            hora = sc.nextLine();

            // Validar que la hora esté en el rango permitido y sea exacta
            if (!hora.matches("\\d{2}:00")) { // Asegurarse de que la hora tenga el formato correcto HH:00
                System.out.println("La hora ingresada no tiene el formato correcto (HH:00).");
                Cargado = false;
            } else {
                int horaEntera = Integer.parseInt(hora.split(":")[0]); // Extraer la parte de la hora
                if (horaEntera < 8 || horaEntera > 22) {
                    System.out.println("La hora ingresada no está entre las 08:00 y las 22:00.");
                    Cargado = false;
                }
            }

            // Verifica si es un dia válido
            Cargado = ((dia.equalsIgnoreCase("Lunes") || dia.equalsIgnoreCase("Martes")
                    || dia.equalsIgnoreCase("Miércoles") || dia.equalsIgnoreCase("Jueves")
                    || dia.equalsIgnoreCase("Viernes") || dia.equalsIgnoreCase("Sábado")
                    || dia.equalsIgnoreCase("Domingo")) ? true : false);

            // Si el dia es válido, verifica condiciones del vuelo
            if (Cargado) {
                System.out.println("Ingrese la identificacion del vuelo");
                idVuelo = sc.nextLine();

                for (int i = 0; i < arregloVuelos.length; i++) {
                    if (arregloVuelos[i].getidAvion().equalsIgnoreCase(idAvion)
                            && arregloVuelos[i].getDia().equalsIgnoreCase(dia)) {
                        Cargado = false;
                        System.out.println("El avion ya tiene asignado otro vuelo ese mismo dia.");
                    }
                    if (arregloVuelos[i].getDia().equalsIgnoreCase(dia)
                            && arregloVuelos[i].getHora().equalsIgnoreCase(hora)) {
                        Cargado = false;
                        System.out.println("El dia y la hora ya tiene asignado otro vuelo");
                    }
                    if (arregloVuelos[i].getNumVuelo().equalsIgnoreCase(idVuelo)) {
                        Cargado = false;
                        System.out.println("La identificacion del vuelo coincide con la de otro vuelo");
                    }
                }

            } else {
                System.out.println("El dia ingresado no corresponde a un dia de la semana");
            }
        } else {
            System.out.println("La ruta no existe");
        }

        // En caso de que las condiciones sean válidas, escribe el nuevo vuelo en el
        // archivo.
        if (Cargado) {
            newVuelo += idVuelo + ";" + idAvion + ";" + idRuta + ";" + dia + ";" + hora;

            String Aviones = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/vuelos.txt";
            try {
                // FileWriter tiene como parametro true para habilitar el modo append
                // De edicion de archivo
                FileWriter escritorAvion = new FileWriter(Aviones, true);
                BufferedWriter bufferEscritura = new BufferedWriter(escritorAvion);
                // Llama a la funcion para ingresar el nuevo avion
                // Lo escribe en el archivo
                bufferEscritura.write(newVuelo + System.lineSeparator());
                // Cierra la escritura
                bufferEscritura.close();

                System.out.println("Vuelo cargado con exito!");
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                        + "que queriamos leer no existe.");
            } catch (IOException ex) {
                System.err.println("Error leyendo o escribiendo en algun archivo.");
            }
        }

    }

    // Funcion para cargar los vuelos en un arreglo
    public static vuelo[] retornarArregloVuelos(int cantVuelos) {

        // Crea la variable de arreglo que se retorna
        vuelo[] ArrVuelo = new vuelo[cantVuelos];
        // Aqui se define donde esta guardado del archivo.txt de los aviones
        String Vuelos = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/vuelos.txt";

        // Inicia la variable linea, que es la encargada de almacenar las lineas del
        // .txt
        String linea = null;

        try {
            // Lee el archivo de la variable Aviones
            FileReader lectorArchivo = new FileReader(Vuelos);

            // Esto no entiendo que hace , calculo que activa la lectura de una formas mas
            // limpia o algo por el estilo jdaiawdnj
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);

            int i = 0;
            vuelo[] arregloVuelos = new vuelo[cantVuelos];
            while ((linea = bufferLectura.readLine()) != null) {
                // Separar cada línea en partes usando el punto y coma
                String[] parts = linea.split(";");
                // Crea dentro de la posicion 'i' el nuevo avion mediante la separacion del
                // metodo split
                arregloVuelos[i] = new vuelo(parts[0], parts[1], parts[4], parts[3], parts[2]);
                i++;
            }

            ArrVuelo = arregloVuelos;
            bufferLectura.close();
            // bufferEscritura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                    + "que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }

        return ArrVuelo;
    }

    // ARCHIVO RUTAS
    public static ruta[] retornarArregloRuta(int cantRutas) {
        // Crea la variable de arreglo que se retorna
        ruta[] ArrRuta = new ruta[cantRutas];
        // Aqui se define donde esta guardado del archivo.txt de los aviones
        String Rutas = "/export/home/matias.contreras/Escritorio/EjercicioFInal/Archivos/rutas.txt";

        // Inicia la variable linea, que es la encargada de almacenar las lineas del
        // .txt
        String linea = null;

        try {
            // Lee el archivo de la variable Aviones
            FileReader lectorArchivo = new FileReader(Rutas);

            // Esto no entiendo que hace , calculo que activa la lectura de una formas mas
            // limpia o algo por el estilo jdaiawdnj
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);

            int i = 0;
            ruta[] arregloRutas = new ruta[cantRutas];
            while ((linea = bufferLectura.readLine()) != null) {
                // Separar cada línea en partes usando el punto y coma
                String[] parts = linea.split(";");
                // Crea dentro de la posicion 'i' el nuevo avion mediante la separacion del
                // metodo split
                arregloRutas[i] = new ruta(parts[0], parts[1], parts[2], parts[3], parts[4]);
                i++;
            }

            ArrRuta = arregloRutas;
            bufferLectura.close();
            // bufferEscritura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                    + "que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }

        return ArrRuta;
    }

    // Método estático para contar las líneas de un archivo
    public static int contarLineas(String rutaArchivo) {
        int contadorLineas = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            while (lector.readLine() != null) { // Lee línea por línea
                contadorLineas++;
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }

        return contadorLineas; // Retorna el total de líneas
    }


    //EJERCICIO 3 
    public static void marcarRealizacion(vuelo unVuelo, avion unAvion, ruta[] arrRutas) {
        unVuelo.setVueloTerminado(true);
        String cantKm = "";
        int i = 0, kmActuales, cantKmNuevos, resultadoKm, cantVuelos;
        boolean rutaEncontrada = false;
    
        // Buscar la ruta correspondiente
        while (i < arrRutas.length && !rutaEncontrada) {
            if (arrRutas[i].getNroRuta().equalsIgnoreCase(unVuelo.getIdRuta())) {
                cantKm = arrRutas[i].getKmDistancia();
                rutaEncontrada = true;
            }
            i++;
        }
    
        // Verificar si la ruta fue encontrada
        if (!rutaEncontrada) {
            System.out.println("Error: Ruta no encontrada.");
        } else {
            // Calcular los kilómetros recorridos y actualizar el avión
            kmActuales = Integer.parseInt(unAvion.getKMRecorridos());
            cantKmNuevos = Integer.parseInt(cantKm);
            resultadoKm = kmActuales + cantKmNuevos;
    
            // Actualizar cantidad de vuelos
            cantVuelos = Integer.parseInt(unAvion.getCantVuelo());
            unAvion.setCantVuelos(String.valueOf(cantVuelos + 1));
            unAvion.setKMRecorridos(String.valueOf(resultadoKm));
        }
    }

    public static void llamarModuloRealizacion(vuelo[] ArrVuelos,avion[] ArrAviones, ruta[] ArrRuta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el ID del vuelo que desea marcar como terminado");        
        String idVuelo = sc.nextLine();
        int posAvion = -1, posVuelo = -1, i = 0, j = 0;
        boolean vueloEncontrado = false, avionEncontrado = false;

        // Buscar el vuelo por ID
        while (i < ArrVuelos.length && !vueloEncontrado) {
            if (idVuelo.equalsIgnoreCase(ArrVuelos[i].getNumVuelo())) {
                posVuelo = i;
                vueloEncontrado = true;
            }
            i++;
        }

        // Buscar el avión correspondiente al vuelo si el vuelo fue encontrado
        if (vueloEncontrado) {
            while (j < ArrAviones.length && !avionEncontrado) {
                if (ArrVuelos[posVuelo].getidAvion().equalsIgnoreCase(ArrAviones[j].getIdentificacion())) {
                    posAvion = j;
                    avionEncontrado = true;
                }
                j++;
            }
        }

        // Mostrar los resultados
        if (vueloEncontrado && avionEncontrado) {
            marcarRealizacion(ArrVuelos[posVuelo], ArrAviones[posAvion], ArrRuta);
        } else if (!vueloEncontrado) {
            System.out.println("El vuelo con ID " + idVuelo + " no se encontró.");
        } else {
            System.out.println("El avión correspondiente al vuelo no se encontró.");
        }

        System.out.println(ArrAviones[posAvion].getKMRecorridos());
    }
    //FIN EJERCICIO 3
}
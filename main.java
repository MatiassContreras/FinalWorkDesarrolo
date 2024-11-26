
//import java.io.*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) {
        // Inicializo Scanner
        Scanner sc = new Scanner(System.in);

        // Variables
        String rutaArchivoAviones = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\aviones.txt";
        String RutaArchivoVuelos = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\vuelos.txt";
        String RutaArchivoRutas = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\rutas.txt";
        int cantAviones = contarLineas(rutaArchivoAviones);
        int cantVuelos = contarLineas(RutaArchivoVuelos);
        int cantRuta = contarLineas(RutaArchivoRutas);
        avion[] ArrAviones = new avion[cantAviones];
        vuelo[] ArrVuelos = new vuelo[cantVuelos];
        ruta[] ArrRuta = new ruta[cantRuta];
        ArrAviones = retornarArregloAviones(cantAviones);
        ArrVuelos = retornarArregloVuelos(cantVuelos);
        ArrRuta = retornarArregloRuta(cantRuta);
        String repetir = "Si";

        while (repetir.equalsIgnoreCase("Si")) {
            // Mostrar menú
            System.out.println("\nBienvenido al sistema de monitoreo y control de rutas, viajes y aviones \n"
                    + "Qué desea hacer hoy? \n"
                    + "1 - Cargar un nuevo avión\n"
                    + "2 - Cargar un nuevo vuelo\n"
                    + "3 - Registrar un vuelo como terminado\n"
                    + "4 - Mostrar el promedio de personas en vuelos terminados\n"
                    + "5 - Crear archivo de aviones ordenados según un día seleccionado\n"
                    + "6 - Mostrar los datos de un avión\n"
                    + "7 - Filtrar vuelos por distancia y retornar un arreglo\n");
        
            // Input de elección con validación
            int opcion = -1;
            boolean opcionValida = false;
        
            while (!opcionValida) {
                System.out.print("Seleccione una opción (1-8): ");
                if (sc.hasNextInt()) { // Verifica si la entrada es un número entero
                    opcion = sc.nextInt();
                    sc.nextLine(); // Limpia el buffer
                    if (opcion >= 1 && opcion <= 8) {
                        opcionValida = true; // La opción es válida
                    } else {
                        System.out.println("Opción fuera de rango. Intente nuevamente.");
                    }
                } else {
                    System.out.println("Entrada no válida. Por favor ingrese un número.");
                    sc.nextLine(); // Limpia el buffer para evitar un bucle infinito
                }
            }
        
            // Operando la opción elegida
            switch (opcion) {
                case 1: // Carga un nuevo avión
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
                case 3: // Marcar vuelo como realizado
                    llamarModuloRealizacion(ArrVuelos, ArrAviones, ArrRuta);
                    cantAviones = contarLineas(rutaArchivoAviones);
                    ArrAviones = new avion[cantAviones];
                    ArrAviones = retornarArregloAviones(cantAviones);
                    break;
                case 4: // Mostrar promedio de personas en vuelos terminados
                    System.out.println(promedioRecursivo(ArrVuelos, ArrAviones));
                    break;
                case 5: // Crear archivo de aviones
                    listaVuelos(ArrVuelos, ArrRuta);
                    ArrAviones = new avion[cantAviones];
                    ArrAviones = retornarArregloAviones(cantAviones);
                    break;
                case 6: // Mostrar datos de un avión
                    mostrarDatosAvion(ArrAviones);
                    break;
                case 7: // Mostrar datos de un avión
                    vuelo[] arregloVuelosFiltrado=filtrarVuelosPorDistancia(ArrVuelos, ArrRuta);
                    System.out.println("El id de los vuelos del arreglo filtrado son " );
                    for (int i = 0; i < arregloVuelosFiltrado.length; i++) {
                        System.out.println(arregloVuelosFiltrado[i].getNumVuelo());
                    }
                    break;
                case 8:
                    int horariosSinVuelos = calcularHorariosSinVuelosRecursivo(ArrVuelos, 8, 0);    
                    System.out.println("Cantidad de horarios sin vuelos en la semana: " + horariosSinVuelos);
                    break;
                    
            }
        
            // Preguntar si desea continuar
            System.out.println("¿Desea hacer otra operación? 'Si' para repetir, 'No' para terminar la ejecución.");
            repetir = sc.nextLine();
        }
        
        // Cierro Scanner
        sc.close();

    }

    // $ Funciones de Aviones (Agregar nuevo avion y Cargar arreglo avion)
    // ? Archivo AVIONES (Crear avion, cargar Aviones en el arreglo)
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
            String Aviones = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\aviones.txt";
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
        String Aviones = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\aviones.txt";

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
    // $-------

    // $ Funciones de Vuelos (Agregar nuevo vuelo y Cargar arreglo vuelos)
    // ? Archivo VUELOS (Agregar nuevo vuelo)
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

            String Aviones = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\vuelos.txt";
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

    // ? Funcion para cargar los vuelos en un arreglo
    public static vuelo[] retornarArregloVuelos(int cantVuelos) {

        // Crea la variable de arreglo que se retorna
        vuelo[] ArrVuelo = new vuelo[cantVuelos];
        // Aqui se define donde esta guardado del archivo.txt de los aviones
        String Vuelos = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\vuelos.txt";

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
                if (parts.length > 5 && parts[5].equalsIgnoreCase("true")) {
                    arregloVuelos[i].setVueloTerminado(true);
                }
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
    // $-------

    // $ Funciones de RUTA (Cargar Arreglo ruta)
    // ? ARCHIVO RUTAS
    public static ruta[] retornarArregloRuta(int cantRutas) {
        // Crea la variable de arreglo que se retorna
        ruta[] ArrRuta = new ruta[cantRutas];
        // Aqui se define donde esta guardado del archivo.txt de los aviones
        String Rutas = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\rutas.txt";

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
    // $-------

    // ! Método estático para contar las líneas de un archivo
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
    // !-------

    // $ EJERCICIO 4
    // ? Función para actualizar los datos de un avión
    public static void actualizarDatosAvion(String idAvion, int kmRecorridos, avion[] arregloAviones) {
        String rutaArchivoAviones = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\aviones.txt";
        try {
            // Leer el archivo original
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoAviones));
            StringBuilder contenidoActualizado = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes[0].equalsIgnoreCase(idAvion)) {
                    // Actualizar los kilómetros recorridos
                    int kmActuales = Integer.parseInt(partes[4]);
                    int vuelosActuales = Integer.parseInt(partes[3]);
                    partes[4] = String.valueOf(kmActuales + kmRecorridos);// Sumar los nuevos km
                    partes[3] = String.valueOf(vuelosActuales + 1); // Suma uno a los vuelos recorridos
                    linea = String.join(";", partes);
                }
                contenidoActualizado.append(linea).append(System.lineSeparator());
            }
            lector.close();

            // Escribir el archivo con los datos actualizados
            BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivoAviones));
            escritor.write(contenidoActualizado.toString());
            escritor.close();
        } catch (IOException ex) {
            System.err.println("Error al actualizar los datos del avión: " + ex.getMessage());
        }
    }

    // ? Función para marcar un vuelo como realizado
    public static void llamarModuloRealizacion(vuelo[] arregloVuelos, avion[] arregloAviones, ruta[] arregloRutas) {
        Scanner sc = new Scanner(System.in);

        // Solicitar ID del vuelo a marcar como realizado
        System.out.println("Ingrese el identificador del vuelo que desea marcar como realizado:");
        String idVuelo = sc.nextLine();
        boolean vueloEncontrado = false;
        boolean vueloHecho = false;
        String rutaArchivoVuelos = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\vuelos.txt";
        try {
            // Leer el archivo original de vuelos
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoVuelos));
            StringBuilder contenidoActualizado = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");

                // Asegurar que partes tenga al menos 6 elementos
                if (partes.length < 6) {
                    partes = Arrays.copyOf(partes, 6);
                    for (int i = 0; i < partes.length; i++) {
                        if (partes[i] == null) {
                            partes[i] = ""; // Rellenar valores faltantes con cadena vacía
                        }
                    }
                }

                if (partes[0].equalsIgnoreCase(idVuelo)) {
                    if (!partes[5].equalsIgnoreCase("true")) {

                        for (int i = 0; i < arregloVuelos.length; i++) {
                            if (arregloVuelos[i].getNumVuelo().equalsIgnoreCase(idVuelo)) {
                                arregloVuelos[i].setVueloTerminado(true);

                                // System.out.println("Entro aca papi");
                            }
                        }

                        vueloEncontrado = true;
                        partes[5] = "true"; // Marcar como realizado

                        // Obtener los kilómetros de la ruta asociada
                        String idRuta = partes[2];
                        int kmRuta = 0;
                        for (ruta r : arregloRutas) {
                            if (r.getNroRuta().equalsIgnoreCase(idRuta)) {
                                kmRuta = Integer.parseInt(r.getKmDistancia());
                                break;
                            }
                        }

                        // Actualizar los datos del avión
                        String idAvion = partes[1];
                        actualizarDatosAvion(idAvion, kmRuta, arregloAviones);

                        // Reconstruir la línea con el vuelo actualizado
                        linea = String.join(";", partes);
                    } else {
                        System.out.println("El vuelo ya ah sido completado");
                        vueloHecho = true;
                    }
                }
                contenidoActualizado.append(linea).append(System.lineSeparator());
            }
            lector.close();

            // Escribir el archivo actualizado
            BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivoVuelos));
            escritor.write(contenidoActualizado.toString());
            escritor.close();

            if (vueloEncontrado && !vueloHecho) {
                System.out.println(
                        "El vuelo ha sido marcado como realizado y los kilómetros actualizados correctamente.");
            } else {
                if (!vueloHecho) {
                    System.out.println("No se encontró el vuelo con el identificador proporcionado.");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al marcar el vuelo como realizado: " + ex.getMessage());
        }
    }

    // $ FIN EJERCICIO 4

    // $ EJERCICIO 5
    public static int promedioRecursivo(vuelo[] arregloVuelos, avion[] arrAvions) {
        String rutaArchivoVuelos = "C:\\Users\\usuario\\Desktop\\FinalWork\\Archivos\\vuelos.txt";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivoVuelos));
            StringBuilder contenidoActualizado = new StringBuilder();

            int[] resultado = promedioHelper(lector, contenidoActualizado, arrAvions, 0, 0);

            // Escribir el archivo actualizado
            BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivoVuelos));
            escritor.write(contenidoActualizado.toString());
            escritor.close();

            return resultado[1] == 0 ? 0 : resultado[0] / resultado[1]; // Evitar división por cero
        } catch (IOException ex) {
            System.err.println("Error al procesar el archivo: " + ex.getMessage());
            return 0;
        }
    }

    private static int[] promedioHelper(BufferedReader lector, StringBuilder contenidoActualizado, avion[] arrAvions,
            int cantPasajeros, int cantVuelos) throws IOException {
        String linea = lector.readLine();
        int[] resultado; // Variable para almacenar el resultado

        if (linea == null) { // Caso base
            resultado = new int[] { cantPasajeros, cantVuelos };
        } else {
            String[] partes = linea.split(";");
            if (partes.length < 6) { // Asegurar que partes tenga al menos 6 elementos
                partes = Arrays.copyOf(partes, 6);
                for (int i = 0; i < partes.length; i++) {
                    if (partes[i] == null)
                        partes[i] = "";
                }
            }

            if (partes[5].equalsIgnoreCase("true")) {
                for (avion avion : arrAvions) {
                    if (avion.getIdentificacion().equalsIgnoreCase(partes[1])) {
                        cantPasajeros += Integer.parseInt(avion.getCantAsientos());
                        cantVuelos++;
                        break;
                    }
                }
            }

            contenidoActualizado.append(linea).append(System.lineSeparator());
            resultado = promedioHelper(lector, contenidoActualizado, arrAvions, cantPasajeros, cantVuelos); // Llamada
                                                                                                            // recursiva
        }

        return resultado; // Único return del método
    }

    // $ FIN EJERCICIO 5

    // $ EJERCICIO 6
    public static void listaVuelos(vuelo[] arrVuelos, ruta[] arrRutas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el dia de los aviones para la lista de vuelos ordenada");
        String dia = sc.nextLine();
        try {
            // Crear un objeto FileWriter para el archivo "AvionesOrdenados.txt"
            FileWriter writer = new FileWriter("AvionesOrdenados.txt");

            int h = 0;
            writer.write("Lista de aviones ordenados:\n");
            while (h < arrVuelos.length) {
                int indexMenor = -1; // Guardamos el índice del vuelo con la menor distancia
                int distanciaMenor = Integer.MAX_VALUE; // Inicializamos la menor distancia como un valor muy grande

                // Buscar el vuelo con la menor distancia en esta vuelta
                for (int i = 0; i < arrVuelos.length; i++) {
                    // Solo procesar el vuelo si no ha sido excluido (no es null)
                    if (arrVuelos[i] != null) { // Aseguramos que no se haya "excluido" el vuelo
                        for (int j = 0; j < arrRutas.length; j++) {
                            if (arrVuelos[i].getIdRuta().equalsIgnoreCase(arrRutas[j].getNroRuta())
                                    && arrVuelos[i].getDia().equalsIgnoreCase(dia)) {
                                int distanciaRuta = Integer.parseInt(arrRutas[j].getKmDistancia());
                                // Si encontramos un vuelo con menor distancia, lo marcamos
                                if (distanciaRuta < distanciaMenor) {
                                    distanciaMenor = distanciaRuta;
                                    indexMenor = i; // Guardamos el índice del vuelo con la menor distancia
                                }
                            }
                        }
                    }
                }

                // Si encontramos un vuelo con la menor distancia, lo imprimimos
                if (indexMenor != -1) {
                    // Escribe en el archivo
                    writer.write(arrVuelos[indexMenor].getNumVuelo() + ";" + arrVuelos[indexMenor].getidAvion() + ";"
                            + arrVuelos[indexMenor].getIdRuta() + ";" + arrVuelos[indexMenor].getDia() + ";"
                            + arrVuelos[indexMenor].getHora() + ";" + arrVuelos[indexMenor].getVueloTerminado() + "\n");

                    // Excluir el vuelo para futuras iteraciones
                    arrVuelos[indexMenor] = null; // "Eliminamos" el vuelo de las futuras búsquedas
                }

                h++;
            }

            // Cerrar el archivo
            writer.close();

            System.out.println("Archivo creado exitosamente.");
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // $ FIN EJERCICIO 6

    // $ EJERCICIO 7
    public static void mostrarDatosAvion(avion[] arrAvions) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la id de un Avion para mostrar sus datos ");
        String idAvion = sc.nextLine();

        for (int i = 0; i < arrAvions.length; i++) {
            if (idAvion.equalsIgnoreCase(arrAvions[i].getIdentificacion())) {
                System.out.println("El id del avion " + arrAvions[i].getIdentificacion());
                System.out.println("Es de Modelo: " + arrAvions[i].getModelo());
                System.out.println("Tiene " + arrAvions[i].getCantVuelos() + " vuelos completados ");
                System.out.println("Tiene " + arrAvions[i].getCantAsientos() + " Asientos ");
                System.out.println("Tiene " + arrAvions[i].getKMRecorridos() + "KM hechos en total " );
            }
        }
      
    }
     // $ FIN EJERCICIO 7

     
    // $ EJERCICIO 8
     public static vuelo[] filtrarVuelosPorDistancia(vuelo[] ArrVuelos, ruta[] arrRutas) {
        Scanner sc = new Scanner(System.in);

        // Solicitar rango al usuario
        System.out.println("Ingrese el rango de distancia:");
        System.out.print("Distancia mínima: ");
        int distanciaMinima = sc.nextInt();
        System.out.print("Distancia máxima: ");
        int distanciaMaxima = sc.nextInt();

        // Lista para almacenar los vuelos dentro del rango
        ArrayList<vuelo> vuelosEnRango = new ArrayList<>();

        // Filtrar vuelos dentro del rango según la distancia de las rutas
        for (int i = 0; i < ArrVuelos.length; i++) {
            for (int j = 0; j < arrRutas.length; j++) {
                // Comparar si el vuelo tiene la ruta correspondiente y si la distancia está en el rango
                if (ArrVuelos[i].getIdRuta().equalsIgnoreCase(arrRutas[j].getNroRuta())
                        && Integer.parseInt(arrRutas[j].getKmDistancia()) >= distanciaMinima
                        && Integer.parseInt(arrRutas[j].getKmDistancia()) <= distanciaMaxima) {

                    // Agregar el vuelo a la lista si cumple la condición
                    vuelosEnRango.add(ArrVuelos[i]);
                }
            }
        }

        // Convertir la lista a un arreglo y retornarlo
        return vuelosEnRango.toArray(new vuelo[0]);
    }
    // $ FIN EJERCICIO 8

    // $ EJERCICIO 9
    public static int calcularHorariosSinVuelosRecursivo(vuelo[] arrVuelos, int horaActual, int totalHoras) {
        // Caso base: si se han recorrido todas las horas del rango, retornar el total
        if (horaActual > 22) {
            return totalHoras;
        }

        // Verificar si hay un vuelo programado para la hora actual
        boolean hayVueloEnHora = false;
        for (vuelo v : arrVuelos) {
            // Convertir la hora del vuelo a entero
            int horaVuelo = Integer.parseInt(v.getHora().split(":")[0]);
            if (horaVuelo == horaActual) {
                hayVueloEnHora = true;
                break;
            }
        }

        // Si no hay vuelos en esta hora, sumar 1 al total de horarios sin vuelos
        if (!hayVueloEnHora) {
            totalHoras++;
        }

        // Llamada recursiva para la siguiente hora
        return calcularHorariosSinVuelosRecursivo(arrVuelos, horaActual + 1, totalHoras);
    }


    // $ FIN EJERCICIO 9

}
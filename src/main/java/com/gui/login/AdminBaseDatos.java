package com.gui.login;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.gui.login.model.*;
import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AdminBaseDatos {



   /*static double leerSensorTemperatura() {

    static double leerSensorLuz() {


       // return HelloApplication.sensorLuz.leerValor();
   } */

    public static void serializarArrayAJson(Usuario nuevoUsuario) {
        // Leer los usuarios existentes
        Vector<Usuario> usuariosExistentes = desserializarJsonAArray();

        // Agregar el nuevo usuario a la lista
        usuariosExistentes.add(nuevoUsuario);

        // Usamos un Vector de JsonObjects para incluir manualmente el campo "type"
        Vector<JsonObject> usuariosConTipo = new Vector<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (Usuario u : usuariosExistentes) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nombre", u.getNombre());
            jsonObject.addProperty("edad", u.getEdad());
            jsonObject.addProperty("correo", u.getCorreo());
            jsonObject.addProperty("password", u.getPassword());
            jsonObject.addProperty("type", u.getTipo()); // Añadir el tipo desde el atributo de la clase
            jsonObject.addProperty("nombreComunidad", u.getNombreComunidad());
            usuariosConTipo.add(jsonObject);
        }

        // Guardar en el archivo empleados.json
        try (FileWriter writer = new FileWriter("empleados.json")) {
            gson.toJson(usuariosConTipo, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Usuario> desserializarJsonAArray() {
        Vector<Usuario> usuarios = new Vector<>();

        try (Reader reader = new FileReader("usuarios.json")) {
            // Leer el archivo JSON como un array de JsonObjects
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elemento : jsonArray) {
                JsonObject jsonObject = elemento.getAsJsonObject();


                String tipo = jsonObject.get("type").getAsString(); // Leer el campo "type"

                // Crear instancia del tipo correspondiente según el valor de "type"
                Usuario usuario = null;
                try {
                    switch (tipo.toLowerCase()) {
                        case "presidente":
                            usuario = new Presidente(
                                    jsonObject.get("nombre").getAsString(),
                                    jsonObject.get("edad").getAsInt(),
                                    jsonObject.get("correo").getAsString(),
                                    jsonObject.get("password").getAsString(),
                                    jsonObject.get("nombreComunidad").getAsString()
                            );
                            break;
                        case "tecnico":
                            usuario = new Tecnico(
                                    jsonObject.get("nombre").getAsString(),
                                    jsonObject.get("edad").getAsInt(),
                                    jsonObject.get("correo").getAsString(),
                                    jsonObject.get("password").getAsString(),
                                    jsonObject.get("nombreComunidad").getAsString()

                            );
                            break;
                        case "portero":
                            usuario = new Portero(
                                    jsonObject.get("nombre").getAsString(),
                                    jsonObject.get("edad").getAsInt(),
                                    jsonObject.get("correo").getAsString(),
                                    jsonObject.get("password").getAsString(),
                                    jsonObject.get("nombreComunidad").getAsString()
                            );
                            break;
                        case "residente":
                            usuario = new Residente(
                                    jsonObject.get("nombre").getAsString(),
                                    jsonObject.get("edad").getAsInt(),
                                    jsonObject.get("correo").getAsString(),
                                    jsonObject.get("password").getAsString(),
                                    jsonObject.get("nombreComunidad").getAsString()
                            );
                            break;
                        default:
                            System.out.println("Tipo desconocido: " + tipo);
                    }
                } catch (NullPointerException | IllegalStateException e) {
                    System.out.println("Error al procesar el elemento: " + e.getMessage());
                    continue; // Ignorar este elemento problemático
                }

                if (usuario != null) {
                    usuarios.add(usuario); // Agregar el usuario deserializado al vector
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    /*public static Vector<String> deserializarJsonAArrayRegistrosMov() {
        Vector<String> registrosMovimiento = new Vector<>();
        try (Reader reader = new FileReader("registrosDeMovimiento.json")) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elemento : jsonArray) {
                String registro = elemento.getAsString();
                registrosMovimiento.add(registro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registrosMovimiento;
    }*/

    public static void serializarRegistrosDeMov(ListaEnlazada<String> listaEnlazada) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("registrosDeMovimiento.json")) {
            // Convertir ListaEnlazada a una lista simple para serializar
            List<String> listaParaGuardar = listaEnlazada.toList();
            prettyGson.toJson(listaParaGuardar, writer);
            writer.flush();
            System.out.println("RegistrosDeMovimiento guardados exitosamente en registros.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void serializarChat(String chat) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<String> listaParaGuardar;

        try {
            // Leer el contenido existente del archivo, si existe
            File file = new File("chat.json");
            if (file.exists() && file.length() > 0) {
                try (Reader reader = new FileReader(file)) {
                    listaParaGuardar = new Gson().fromJson(reader, new TypeToken<ArrayList<String>>() {}.getType());
                    if (listaParaGuardar == null) {
                        listaParaGuardar = new ArrayList<>(); // Manejar caso de archivo vacío o malformado
                    }
                }
            } else {
                listaParaGuardar = new ArrayList<>(); // Crear nueva lista si el archivo no existe
            }

            // Agregar el nuevo chat
            listaParaGuardar.add(chat);

            // Escribir toda la lista nuevamente en el archivo
            try (FileWriter writer = new FileWriter("chat.json")) {
                prettyGson.toJson(listaParaGuardar, writer);
                System.out.println("Registro guardado con éxito en chat.json");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<String> desserializarChat() {
        Vector<String> mensajes = new Vector<>();
        try (Reader reader = new FileReader("chat.json")) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elemento : jsonArray) {
                String registro = elemento.getAsString();
                mensajes.add(registro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mensajes;
    }


    //Pasar una lista enlazada de registros y pasarlo a formato json es decir de stringAJson
    public static void serializarRegistros(ListaEnlazada<String> listaEnlazada) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("registros.json")) {
            // Convertir ListaEnlazada a una lista simple para serializar
            List<String> listaParaGuardar = listaEnlazada.toList();
            prettyGson.toJson(listaParaGuardar, writer);
            System.out.println("Registros guardados exitosamente en registros.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //Pasar un archivo formato json a una lista/vector a un vector de tipo Json y luego recorrer ese array y pasarlo a string y meterlo en un vector de strings, es decir jsonAString
    public static Vector<String> deserializarJsonAArray() {
        Vector<String> registros = new Vector<>();
        try (Reader reader = new FileReader("registros.json")) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elemento : jsonArray) {
                String registro = elemento.getAsString();
                registros.add(registro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public static boolean isPorteroAlreadyRegistered (String nombreComunidad) {
        // Obtener todos los usuarios deserializados
        Vector<Usuario> usuariosExistentes = desserializarJsonAArray();

        // Verificar si ya existe un portero en la comunidad
        for (Usuario usuario : usuariosExistentes) {
            if (usuario instanceof Portero && usuario.getNombreComunidad().equalsIgnoreCase(nombreComunidad)) {
                return true;
            }
        }
        return false; // No hay portero registrado en esta comunidad
    }
    public static boolean isPresidenteAlreadyRegistered(String nombreComunidad) {
        // Obtener todos los usuarios deserializados
        Vector<Usuario> usuariosExistentes = desserializarJsonAArray();

        // Verificar si ya existe un presidente en la comunidad
        for (Usuario usuario : usuariosExistentes) {
            if (usuario instanceof Presidente && usuario.getNombreComunidad().equalsIgnoreCase(nombreComunidad)) {
                return true; // Ya existe un presidente en esta comunidad
            }
        }
        return false; // No hay presidente registrado en esta comunidad
    }

    public static boolean isAddressAlreadyRegistered(String correo) {
        // Obtener todos los usuarios deserializados
        Vector<Usuario> usuariosExistentes = desserializarJsonAArray();

        // Verificar si ya existe un presidente en la comunidad
        for (Usuario usuario : usuariosExistentes) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                return true; // Ya existe un presidente en esta comunidad
            }
        }
        return false; // No hay presidente registrado en esta comunidad
    }
    public static boolean isTecnicoAlreadyRegistered(String nombreComunidad) {
        // Obtener todos los usuarios deserializados
        Vector<Usuario> usuariosExistentes = desserializarJsonAArray();

        // Verificar si ya existe un tecnico en la comunidad
        for (Usuario usuario : usuariosExistentes) {
            if (usuario instanceof Tecnico && usuario.getNombreComunidad().equalsIgnoreCase(nombreComunidad)) {
                return true; // Ya existe un tecnico en esta comunidad
            }
        }
        return false; // No hay tecnico registrado en esta comunidad

    }

    public static void serializarSaldos(Vector<Double> saldos, Vector<Double> maxSaldos) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonObject = new JsonObject();
        JsonArray saldosArray = new JsonArray();
        JsonArray maxSaldosArray = new JsonArray();

        for (Double saldo : saldos) {
            saldosArray.add(saldo);
        }
        for (Double maxSaldo : maxSaldos) {
            maxSaldosArray.add(maxSaldo);
        }

        jsonObject.add("saldos", saldosArray);
        jsonObject.add("maxSaldos", maxSaldosArray);

        try (FileWriter writer = new FileWriter("saldos.json")) {
            prettyGson.toJson(jsonObject, writer);
            System.out.println("Saldos guardados exitosamente en saldos.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Pair<Vector<Double>, Vector<Double>> deserializarSaldos() {
        Vector<Double> saldos = new Vector<>();
        Vector<Double> maxSaldos = new Vector<>();

        try (Reader reader = new FileReader("saldos.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            JsonArray saldosArray = jsonObject.getAsJsonArray("saldos");
            JsonArray maxSaldosArray = jsonObject.getAsJsonArray("maxSaldos");

            if (saldosArray == null || maxSaldosArray == null) {
                throw new IOException("El archivo JSON no contiene los datos esperados.");
            }

            for (JsonElement elemento : saldosArray) {
                saldos.add(elemento.getAsDouble());
            }
            for (JsonElement elemento : maxSaldosArray) {
                maxSaldos.add(elemento.getAsDouble());
            }

            // Imprimir los valores leídos para depurar
            System.out.println("Saldos: " + saldos);
            System.out.println("Max Saldos: " + maxSaldos);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair<>(saldos, maxSaldos);
    }
}
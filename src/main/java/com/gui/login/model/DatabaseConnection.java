package com.gui.login.model;

import com.gui.login.Registro;

import java.sql.*;
import java.util.Vector;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/pii2_domotechexamen2";

    private static final String USER = "root";
    private static final String PASSWORD = ""; // Pon tu contraseña si tiene

    // Establece la conexión
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            return null;
        }
    }
    public Vector<String> obtenerMensajesPorComunidad(String nombreComunidad) {
        Vector<String> mensajes = new Vector<>();
        try (Connection conn = this.getConnection()) {
            String sql = "SELECT m.mensaje, u.nombre " +
                    "FROM mensaje m " +
                    "JOIN usuario u ON m.id_Usuario = u.id_Usuario " +
                    "WHERE u.comunidad = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreComunidad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String contenido = rs.getString("mensaje");
                String autor = rs.getString("nombre");
                mensajes.add(autor + ": " + contenido);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }



    public boolean generarEInsertarDatoPulsometro(int idSensor) {
        // Generación de datos aleatorios
        int pulso = (int)(60 + Math.random() * 40);           // entre 60 y 100
        int sistolica = (int)(100 + Math.random() * 40);      // entre 100 y 140
        int diastolica = (int)(60 + Math.random() * 20);      // entre 60 y 80

        String datoFormateado = pulso + " - " + sistolica + "/" + diastolica;

        String sql = "INSERT INTO sensores (id_sensor, dato, fecha) VALUES (?, ?, NOW())";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSensor);
            pstmt.setString(2, datoFormateado);

            pstmt.executeUpdate();
            System.out.println("✅ Dato generado del pulsómetro insertado: " + datoFormateado);
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar dato del pulsómetro: " + e.getMessage());
            return false;
        }
    }



    public boolean insertarMensaje(Mensaje mensaje) {
        String sql = "INSERT INTO mensaje (mensaje, fecha, id_Usuario) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mensaje.getMensaje());
            pstmt.setTimestamp(2, mensaje.getFecha());
            pstmt.setInt(3, mensaje.getIdUsuario());

            pstmt.executeUpdate();
            System.out.println("✅ Mensaje insertado correctamente.");
            return true;


        } catch (SQLException e) {
            System.out.println("❌ Error al insertar mensaje: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public Vector<String> obtenerDatosDeSensores() {
        Vector<String> datosSensores = new Vector<>();
        String sql = "SELECT id_sensor, dato, fecha FROM sensores";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_sensor");
                float dato = rs.getFloat("dato");
                Timestamp fecha = rs.getTimestamp("fecha");

                String registro = "ID Sensor: " + id + ", Dato: " + dato + ", Fecha: " + fecha;
                datosSensores.add(registro);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener datos de sensores: " + e.getMessage());
            e.printStackTrace();
        }

        return datosSensores;
    }


    public void mostrarMensaje() {
        String sql = "SELECT * FROM mensaje";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_Mensaje");
                String mensaje = rs.getString("mensaje");
                String fecha = rs.getString("fecha");
                int idUsuario = rs.getInt("id_Usuario");
                int idComunidad = rs.getInt("id_comunidad");

                System.out.println("ID: " + id + ", Mensaje: " + mensaje + ", Fecha: " + fecha + ", ID Usuario: " + idUsuario + ", ID Comunidad: " + idComunidad);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al mostrar mensajes: " + e.getMessage());
            e.printStackTrace();
        }
    }




    public Float obtenerUltimoDatoSensor(int idSensor) {
        String sql = "SELECT dato FROM sensores WHERE id_sensor = ? ORDER BY fecha DESC LIMIT 1";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSensor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("dato");
                } else {
                    System.out.println("⚠ No hay datos para el sensor con ID: " + idSensor);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el último dato del sensor " + idSensor + ": " + e.getMessage());
            return null;
        }
    }



    public Vector<String> obtenerRegistrosPorUsuario(int idUsuario, String comunidad) {
        Vector<String> registros = new Vector<>();
        String sql = "SELECT r.dato FROM registro r JOIN usuario u ON r.id_Usuario = u.id_Usuario WHERE r.id_Usuario = ? AND u.comunidad = ?";
        try(Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, comunidad);
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    String dato = rs.getString("dato");
                    registros.add(dato);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;
    }

    public Vector<String> obtenerTodosLosRegistros(String comunidad) {
        Vector<String> registros = new Vector<>();
        String sql = "SELECT r.dato FROM registro r JOIN usuario u ON r.id_Usuario = u.id_Usuario WHERE u.comunidad = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comunidad);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(rs.getString("dato"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;
    }




    // Inserta un usuario en la tabla
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (edad, rol, nombre, contraseña, correo, comunidad) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuario.getEdad());
            pstmt.setString(2, usuario.getTipo());
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getNombreComunidad());

            pstmt.executeUpdate();
            System.out.println("✅ Usuario insertado correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }
    public boolean existeUsuario(String nombreUsuario) {
        String sql = "SELECT 1 FROM usuario WHERE nombre = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true si existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeRolEnComunidad(String rol, String comunidad) {
        String sql = "SELECT 1 FROM usuario WHERE rol = ? AND comunidad = ? LIMIT 1";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rol.toLowerCase());
            stmt.setString(2, comunidad);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true si ya hay uno
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Muestra todos los usuarios en consola
    public void mostrarUsuarios() {
        String sql = "SELECT * FROM usuario";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_Usuario");
                int edad = rs.getInt("edad");
                String rol = rs.getString("rol");
                String nombre = rs.getString("nombre");
                String contraseña = rs.getString("contraseña");
                String correo = rs.getString("correo");
                String comunidad = rs.getString("comunidad");

                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Rol: " + rol + ", Edad: " + edad + ", Correo: " + correo + ", Comunidad: " + comunidad);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al mostrar usuarios: " + e.getMessage());
        }
    }



    public void eliminarUsuario(String nombreUsuario, String rolUsuario, String comunidadUsuario) {
        String obtenerIdsSQL = "SELECT id_Usuario FROM usuario WHERE nombre = ? AND rol = ? AND comunidad = ?";
        String eliminarAsignacionesSQL = "DELETE FROM asignacion_sensor WHERE id_Usuario = ?";
        String eliminarUsuariosSQL = "DELETE FROM usuario WHERE id_Usuario = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);  // Desactiva autocommit para transacción segura

            try (PreparedStatement obtenerIdsStmt = conn.prepareStatement(obtenerIdsSQL)) {
                obtenerIdsStmt.setString(1, nombreUsuario);
                obtenerIdsStmt.setString(2, rolUsuario);
                obtenerIdsStmt.setString(3, comunidadUsuario);
                ResultSet rs = obtenerIdsStmt.executeQuery();

                boolean existeUsuario = false;

                while (rs.next()) {
                    existeUsuario = true;
                    int idUsuario = rs.getInt("id_Usuario");

                    try (PreparedStatement stmt1 = conn.prepareStatement(eliminarAsignacionesSQL);
                         PreparedStatement stmt2 = conn.prepareStatement(eliminarUsuariosSQL)) {

                        stmt1.setInt(1, idUsuario);
                        stmt1.executeUpdate();

                        stmt2.setInt(1, idUsuario);
                        stmt2.executeUpdate();
                    }
                }

                if (existeUsuario) {
                    conn.commit();  // Confirma los cambios si hubo usuarios eliminados
                    System.out.println("✅ Todos los usuarios con nombre '" + nombreUsuario + "', rol '" + rolUsuario + "' y comunidad '" + comunidadUsuario + "' han sido eliminados.");
                } else {
                    System.out.println("⚠ No se encontraron usuarios con los datos proporcionados.");
                }

            } catch (SQLException e) {
                conn.rollback();  // Revierte cambios en caso de error
                System.out.println("❌ Error al eliminar usuarios: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void consultarSensor() throws SQLException {
        String sql = "SELECT * FROM asignacion_sensor";
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_Sensor");
                String ubicacion = rs.getString("ubicacion");
                int idUsuario = rs.getInt("id_Usuario");

                System.out.println("ID: " + id + ", Ubicación: " + ubicacion + ", id UsuarioAsignado: " + idUsuario);
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al mostrar el sensor: " + ex.getMessage());
        }
    }
    private boolean verificarExistenciaUsuarioPorNombre(String nombreUsuario) {
        try (Connection conn = getConnection()) {
            String query = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombreUsuario);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar existencia de usuario: " + e.getMessage());
            return false;
        }
    }


    public void insertarSensor(Registro nuevoRegistro) {
        if (!verificarExistenciaUsuarioPorNombre(nuevoRegistro.getNombreUsuario())) {
            System.err.println("❌ Error: Usuario con nombre " + nuevoRegistro.getNombreUsuario() + " no existe en la base de datos.");
            return;
        }

        try (Connection conn = getConnection()) {
            if (conn == null) return;

            String checkQuery = "SELECT COUNT(*) FROM asignacion_sensor WHERE id_Sensor = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, nuevoRegistro.getIdSensor());
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count > 0) {
                    String updateQuery = "UPDATE asignacion_sensor SET ubicacion = ?, nombre_usuario = ? WHERE id_Sensor = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, nuevoRegistro.getDescripcion());
                        updateStmt.setString(2, nuevoRegistro.getNombreUsuario());
                        updateStmt.setInt(3, nuevoRegistro.getIdSensor());
                        updateStmt.executeUpdate();
                        System.out.println("Registro actualizado con éxito.");
                    }
                } else {
                    String insertQuery = "INSERT INTO asignacion_sensor (id_Sensor, ubicacion, nombre_usuario) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, nuevoRegistro.getIdSensor());
                        insertStmt.setString(2, nuevoRegistro.getDescripcion());
                        insertStmt.setString(3, nuevoRegistro.getNombreUsuario());  // Ahora usa el nombre
                        insertStmt.executeUpdate();
                        System.out.println("Registro insertado con éxito.");
                    }
                }
                String insertQuery = "INSERT INTO registro (id_Sensor, dato, tiempo, id_Usuario) VALUES (?, ?, ?, ?)";
                try(PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    // Esta es la info que devolverá el registro
                    String dato = nuevoRegistro.getNombreUsuario() + " ha abierto la puerta " + nuevoRegistro.getDescripcion() + " el dia " + nuevoRegistro.getFecha();

                    insertStmt.setInt(1, nuevoRegistro.getIdSensor());
                    insertStmt.setString(2, dato);
                    insertStmt.setTimestamp(3, nuevoRegistro.getFecha());
                    insertStmt.setInt(4, nuevoRegistro.getIdUsuario());
                    insertStmt.executeUpdate();
                    System.out.println("Registro insertado con éxito.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar o actualizar en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean eliminarSensorPorId(int idSensor) {
        String sql = "DELETE FROM asignacion_sensor WHERE id_Sensor = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSensor);
            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Sensor con ID " + idSensor + " eliminado correctamente.");
                return true;
            } else {
                System.out.println("⚠ No se encontró sensor con ID " + idSensor);
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar sensor: " + e.getMessage());
            return false;
        }
    }


    public boolean eliminarSensorPorUbicacion(String ubicacion) {
        String sql = "DELETE FROM asignacion_sensor WHERE ubicacion = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ubicacion);
            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Sensor(es) en '" + ubicacion + "' eliminado(s).");
                return true;
            } else {
                System.out.println("⚠ No hay sensores en esa ubicación.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar sensor: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminarSensorPorUsuario(int idUsuario) {
        String sql = "DELETE FROM asignacion_sensor WHERE id_Usuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);
            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Sensor(es) del usuario con ID " + idUsuario + " eliminado(s).");
                return true;
            } else {
                System.out.println("⚠ No hay sensores asignados a ese usuario.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar sensor: " + e.getMessage());
            return false;
        }
    }


    public boolean eliminarSensorPorIdUbicacionYUsuario(int idSensor, String ubicacion, int idUsuario) {
        String sql = "DELETE FROM asignacion_sensor WHERE id_Sensor = ? AND ubicacion = ? AND id_Usuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSensor);
            pstmt.setString(2, ubicacion);
            pstmt.setInt(3, idUsuario);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Sensor eliminado: ID = " + idSensor + ", Ubicación = '" + ubicacion + "', ID Usuario = " + idUsuario);
                return true;
            } else {
                System.out.println("⚠ No se encontró ningún sensor con esos datos.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar sensor: " + e.getMessage());
            return false;
        }
    }

    public void mostrarSensores() throws SQLException {
        String sql = "SELECT * FROM asignacion_sensor";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idSensor = rs.getInt("id_Sensor");
                String ubicacion = rs.getString("ubicacion");
                int idUsuario = rs.getInt("id_Usuario");


                System.out.println("ID: " + idSensor + ", ubicacion: "+ ubicacion + ", id_Usuario: " + idUsuario);
            }

        }


    }
    public boolean insertarGasto(Gasto gasto) {
        String sql = "INSERT INTO gastos (id_usuario, tipo_gasto, cantidad, fecha) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gasto.getIdUsuario());
            pstmt.setString(2, gasto.getTipoGasto());
            pstmt.setDouble(3, gasto.getCantidad());
            pstmt.setDate(4, gasto.getFecha());

            pstmt.executeUpdate();
            System.out.println("✅ Gasto insertado correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar gasto: " + e.getMessage());
            return false;
        }
    }


}
package com.example.salus.datosImpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.salus.datos.IUsuarioDao;
import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.ICondicionNeg;
import com.example.salus.negocioImpl.CondicionNegImpl;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoImpl implements IUsuarioDao {
    private SQLiteDatabase cn;
    private ICondicionNeg conNI;
    @Override
    public List<Usuario> obtenerTodos(Context context) {
        List<Usuario> lista = new ArrayList<Usuario>();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery(
                    "SELECT DNI_Us, Nombre_Us, Apellido_Us, Direccion_Us, Ciudad_Us, Telefono_Us, Estado, CodCondicion_Us FROM Usuario;",
                    null
            );
            if(c.moveToFirst()) {
                conNI = new CondicionNegImpl();
                do {
                    Usuario usuario = new Usuario();
                    usuario.setDni(c.getInt(0));
                    usuario.setNombre(c.getString(1));
                    usuario.setApellido(c.getString(2));
                    usuario.setDireccion(c.getString(3));
                    usuario.setCiudad(c.getString(4));
                    usuario.setTelefono(c.getString(5));
                    usuario.setEstado(c.getString(6).equals("1"));
                    usuario.setCondicion(conNI.listarUno(c.getInt(7),context));
                    lista.add(usuario);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return lista;
    }
    @Override
    public Usuario obtenerUno(int id, Context context) {
        Usuario usuario = new Usuario();
        try {
            cn = new ConexionSQLiteHelper(context).getReadableDatabase();
            Cursor c = cn.rawQuery(
                    "SELECT DNI_Us, Nombre_Us, Apellido_Us, Direccion_Us, Ciudad_Us, Telefono_Us, Estado, CodCondicion_Us " +
                            "FROM Usuario WHERE DNI_Us = " + id + ";",
                    null
            );
            if(c.moveToFirst()) {
                conNI = new CondicionNegImpl();
                do {
                    usuario.setDni(c.getInt(0));
                    usuario.setNombre(c.getString(1));
                    usuario.setApellido(c.getString(2));
                    usuario.setDireccion(c.getString(3));
                    usuario.setCiudad(c.getString(4));
                    usuario.setTelefono(c.getString(5));
                    usuario.setEstado(c.getString(6).equals("1"));
                    usuario.setCondicion(conNI.listarUno(c.getInt(7),context));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cn.close();
        }
        return usuario;
    }
    @Override
    public boolean insertar(Usuario usuario, Context context) {
        boolean insert = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("INSERT INTO Usuario (Nombre_Us) " +
                    "VALUES ('" + usuario.getNombre() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("DNI_Us",usuario.getDni());
            nuevoRegistro.put("Nombre_Us",usuario.getNombre());
            nuevoRegistro.put("Apellido_Us",usuario.getApellido());
            nuevoRegistro.put("Direccion_Us",usuario.getDireccion());
            nuevoRegistro.put("Ciudad_Us",usuario.getCiudad());
            nuevoRegistro.put("Telefono_Us",usuario.getTelefono());
            //nuevoRegistro.put("Estado",usuario.getEstado());
            nuevoRegistro.put("CodCondicion_Us",usuario.getCondicion().getCodCondicion());
            cn.insert("Usuario",null,nuevoRegistro);
        } catch (Exception e) {
            e.printStackTrace();
            insert = false;
        } finally {
            cn.close();
        }
        return insert;
    }
    @Override
    public boolean editar(Usuario usuario, Context context) {
        boolean update = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("UPDATE Usuario SET Nombre_Us = '"+ usuario.getNombre()
                    +"' WHERE DNI_Us = '" + usuario.getDni() + "');");
            */
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("Nombre_Us",usuario.getNombre());
            nuevoRegistro.put("Apellido_Us",usuario.getApellido());
            nuevoRegistro.put("Direccion_Us",usuario.getDireccion());
            nuevoRegistro.put("Ciudad_Us",usuario.getCiudad());
            nuevoRegistro.put("Telefono_Us",usuario.getTelefono());
            nuevoRegistro.put("Estado",usuario.getEstado());
            nuevoRegistro.put("CodCondicion_Us",usuario.getCondicion().getCodCondicion());
            cn.update("Usuario",
                    nuevoRegistro,
                    "DNI_Us = " + Integer.toString(usuario.getDni()),
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            update = false;
        } finally {
            cn.close();
        }
        return update;
    }
    @Override
    public boolean borrar(int id, Context context) {
        boolean delete = true;
        try {
            cn = new ConexionSQLiteHelper(context).getWritableDatabase();
            /*
            cn.execSQL("DELETE FROM Usuario WHERE DNI_Us = " + id + ";");
            */
            cn.delete("Usuario","DNI_Us = " + id,null);
        } catch (Exception e) {
            e.printStackTrace();
            delete = false;
        } finally {
            cn.close();
        }
        return delete;
    }
}

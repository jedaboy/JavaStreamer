package dao;

import dao.impl.GeneroDaoJDBC;
import dao.impl.Mp3DataDaoJDBC;
import dao.impl.MusicaDaoJDBC;
import dao.impl.UsuarioDaoJDBC;
import db.DB;
import java.io.IOException;
import java.sql.SQLException;

public class DaoFactory {

    
   
    public static UsuarioDao createUsuarioDao(){
        return new UsuarioDaoJDBC(DB.getConnection());
    }

    public static GeneroDao createGeneroDao(){
        return new GeneroDaoJDBC(DB.getConnection());
    }

    public static MusicaDao createMusicaDao(){
        return new MusicaDaoJDBC(DB.getConnection());
    }
    
   public static Mp3DataDao createMp3DataDao() throws IOException, SQLException{
       

       return new Mp3DataDaoJDBC(DB.getConnection());
    }
}


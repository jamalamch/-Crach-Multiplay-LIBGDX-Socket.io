package kalima.crach.games.basedonne;

import java.io.IOException;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;
import com.badlogic.gdx.utils.Array;

public abstract class BaseDonnes {
    private  static Database dbHandler ;
    private  static DatabaseCursor rsql = null;

    private static String DATABASE_NAME = "baseDonne.db";
    private static final int DATABASE_VERSION = 2;
    
    public static void connectToDB() throws IOException {
        if(Gdx.app.getType() == ApplicationType.Desktop)
        DATABASE_NAME = "baseDonne.db";
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME, DATABASE_VERSION, null, null);
        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            Gdx.app.log("DatabaseTest", "connected successfully \t " + DATABASE_NAME);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }
    public static void diconnect() {
        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }


    public static void setMotTrouve(int idMot) {
        try {
                    dbHandler.execSQL("UPDATE mot SET trouve = 1 WHERE id= " + idMot + " ;");
                } catch (SQLiteGdxException e1) {
                    e1.printStackTrace();
        }
    }
    
    
	public static boolean chercherlesmots(String mots) throws  SQLiteGdxException{
		String nBase = nomebasedone(mots.charAt(0), mots.length());
	    if(dbHandler.rawQuery("SELECT MOT FROM `"+nBase+"` WHERE `mot` LIKE '"+mots+"' ").next())
	    	return true;
	    else
            return false;
	}
	
	public static boolean chercherlesmotshamza(char [] mot) throws  SQLiteGdxException{
		String nBase = nomebasedone(mot[0], mot.length);
		Array<String> mots = new Array<String>();
		char [] charspesialalf = new char[]{'أ','آ','إ'};
		char [] charspesialhamza = new char[]{'ئ','ؤ','إ','ا'};
		int J = 0;
		char [] Chars ;
		mots.add(String.valueOf(mot).replaceAll("Ø¡", "_").replaceAll("Ø§", "_"));		
		mots.add(String.valueOf(mot));		


		for(String MOT : mots) {

			Chars = MOT.toCharArray();

			for( int i = J ; i< Chars.length ; i++){
				if(Chars[i] == 'ا') {
					J=i;
					for(char c : charspesialalf ) {
						Chars[i]=c;
						mots.add(String.valueOf(Chars));
					}
					Chars[i] = 'ا';
				}
				else if(Chars[i] == 'ء'){
					J=i;
					for(char c : charspesialhamza) {
						Chars[i]=c;
						mots.add(String.valueOf(Chars));
					}
					Chars[i] = 'ء';
				}					
			}
		}
		
		  String Query = "SELECT MOT FROM ( SELECT MOT FROM `"+nBase+"` WHERE `mot` LIKE '"+mots.get(0)+"' ) WHERE MOT IN ('"+mots.get(1)+"' ";
		
		  mots.removeIndex(0); 
		  mots.removeIndex(0); 
		  
		  for(String S : mots)
		  Query +=",'"+S+"'";
		  Query +=")";
		  
		  if(dbHandler.rawQuery(Query).next()){ return true; } else{ return false; }
	}

	public static String nomebasedone(char C, int L) {
		switch (C) {
		case 'ب':
			return "BAA" + L;
		case 'ت':
			return "TAA" + L;
		case 'ث':
			return "LAM" + L;
		case 'ج':
			return "GEM" + L;
		case 'ح':
			return "HAA" + L;
		case 'خ':
			return "KHA" + L;
		case 'د':
			return "DAL" + L;
		case 'ذ':
			return "ZAL" + L;
		case 'ر':
			return "RAA" + L;
		case 'ز':
			return "ZAY" + L;
		case 'س':
			return "SEN" + L;
		case 'ش':
			return "SHN" + L;
		case 'ص':
			return "SAD" + L;
		case 'ض':
			return "DAD" + L;
		case 'ط':
			return "TAA" + L;
		case 'ظ':
			return "DAD" + L;
		case 'ع':
			return "AIN" + L;
		case 'غ':
			return "GHN" + L;
		case 'ف':
			return "FAA" + L;
		case 'ق':
			return "QAF" + L;
		case 'ك':
			return "KAF" + L;
		case 'ل':
			return "LAM" + L;
		case 'م':
			return "MEM" + L;
		case 'ن':
			return "NON" + L;
		case 'ه':
			return "HAA" + L;
		case 'و':
			return "WAW" + L;
		case 'ي':
			return "YAA" + L;
		}
		return "ALF" + L;	
	}


}


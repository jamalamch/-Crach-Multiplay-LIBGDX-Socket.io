package kalima.crach.games.MotAndChar;

import java.util.Random;

public class Arabicchar {
    
	private static Random random = new Random();
	
    private final static char[] Letrres= new char[]{'ذ','ض','ص','ث','ق','ف','غ','ع','ه','خ','ح','ج','د','ش',
    		                                         'س','ي','ب','ل','ا','ت','ن','م','ك','ط','ء','ر','ى','ة','و','ز','ظ'};
    public static char GetCharArabic() {
		int i = random.nextInt(Letrres.length);
    	return Letrres[i];
    }
	
	
}

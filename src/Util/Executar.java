package Util;

import java.io.IOException;

public class Executar {
	
	public static void executarComando(String comando)    
	 {
			try {
				Process executar = Runtime.getRuntime().exec(comando);
				executar.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.escrever("[Executar:executarComando] :"+e.getMessage());
			}
	 }

}

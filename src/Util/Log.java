package Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {
	
	public static void escrever(String mensagem) {
		
		Date date = new Date();
		
		Util.Propriedades.escreverStringEmArquivo("["+date.toString()+"] "+mensagem, "log/erros.log");
	}
	
	public static void reset() {
		Util.Propriedades.sobrescreverArquivo(" ", "log/erros.log");
		
	}
	
	public List getLogs(String arquivo) {
		
		return new ArrayList();
	}

}

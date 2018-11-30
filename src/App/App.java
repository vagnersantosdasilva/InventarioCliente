package App;

import Controle.MonitorDeMudancas;
import DAO.MaquinaDAO;
import Util.Log;

public class App 
{
	public static void main(String[] args) throws InterruptedException
	{
		//Servidor servidor = new Servidor(1050);
		//servidor.start();
		try {
			MaquinaDAO dao = new MaquinaDAO();
			MonitorDeMudancas monitor = new MonitorDeMudancas(dao);
			monitor.iniciarSistema();
			monitor.start();
		}catch(Exception e) {
			System.out.println("Erro[App:main]"+e.getMessage());
			e.printStackTrace();
			Log.escrever("[App:main] :"+e.getMessage());
		}
	}		

}

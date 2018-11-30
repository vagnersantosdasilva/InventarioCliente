package App;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import Entidades.LogErro;
import Entidades.Maquina;

public class Servidor extends  Thread 
{
	private int porta;
	
	public Servidor (int porta)
	{
		this.porta=porta;
	}
	
	@Override
	public void run() 
	{
		try 
		{
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(porta);
			while(true)
			{
				System.out.println("Aguardando requisição...");
				Socket clienteSocket = serverSocket.accept();
				InputStream socketIn = clienteSocket.getInputStream();
				ObjectInputStream objetoEntrada = new ObjectInputStream(socketIn);
				Maquina maquina = (Maquina)objetoEntrada.readObject();
				
				System.out.println("Usou porta :"+porta);
				
				if (maquina.getCodigoMaquina()!=null)System.out.println("Recebeu CodigoMaquina :"+maquina.getCodigoMaquina());
				if (maquina.getHostname()!=null)System.out.println("Recebeu Hostname :"+maquina.getHostname());
				if (maquina.getSistemaOperacional()!=null)System.out.println("Recebeu Sistema Operacional :"+maquina.getSistemaOperacional().getNome());
				if (maquina.getListaDeLogsDeErro()!=null) {
					List listaDeLogs = maquina.getListaDeLogsDeErro();
					
					System.out.println("Logs recuperados");
					for(int i=0;i<listaDeLogs.size();i++) {
						LogErro log = new LogErro();
						log = (LogErro)listaDeLogs.get(i);
						System.out.println(log.getMessage());
					}
				}
				OutputStream socketOut = clienteSocket.getOutputStream();
				DataOutputStream dout = new DataOutputStream(socketOut);
				dout.writeInt(1);
				
			} 
		}
		catch (Exception e) 
		{
			System.out.println("Erro[Servidor:run] "+e.getMessage());
			
			e.printStackTrace();
		}
	}
	
}

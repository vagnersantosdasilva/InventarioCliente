package Controle;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import Entidades.Hardware;
import Entidades.Maquina;
import Entidades.Memoria;
import Entidades.Propriedades;
import Util.Log;

public class Conexao 
{	
	public void solicitarConexao(Propriedades propriedades,Maquina maquina)
	{
		try
		{
			String servidor =propriedades.getServidor();
			int porta =Integer.parseInt(propriedades.getPorta());
			Socket socket = new Socket(servidor,porta);
			OutputStream socketOut = socket.getOutputStream();
			ObjectOutputStream objOut =new ObjectOutputStream(socketOut);
			objOut.writeObject(maquina);
		}
		catch(Exception e)
		{
			System.out.println("Erro ao solicitar conexão");
			System.out.println(e.getMessage());
			Log.escrever("Erro[Conexao:solicitarConexao] :"+e.getMessage());
		}
	}
	public boolean enviar(Propriedades propriedades,Maquina maquina)
	{
		try 
		{
			if (maquina==null) {Log.escrever("[Conexao:enviar] : Objeto 'Maquina' em estado null"); return false;}
			String servidor =propriedades.getServidor();
			int porta =Integer.parseInt(propriedades.getPorta());
			Socket socket = new Socket(servidor,porta); 
			System.out.println("Cliente: conectado ao servidor");
			
			OutputStream socketOut = socket.getOutputStream();
			ObjectOutputStream dout = new ObjectOutputStream(socketOut);
			dout.writeObject(maquina);
			
			InputStream socketIn = socket.getInputStream();
			DataInputStream din = new DataInputStream(socketIn);
			int resposta = din.readInt();
			if (resposta ==1) 
			{
				System.out.println("Cliente: recebido do servidor o valor " + resposta);
				dout.close();
				socketOut.close();
				socket.close();
				return true;
			}
			else
			{
				dout.close();
				socketOut.close();
				socket.close();
				System.out.println("Servidor enviou outra resposta "+resposta);
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Controle.Conexao[enviar] :"+e.getMessage());
			e.printStackTrace();
			Log.escrever("[Conexao:enviar] :"+e.getMessage());
		}
		return false;
	}
	
}

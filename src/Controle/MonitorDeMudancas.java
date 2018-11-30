package Controle;
import java.util.List;
import DAO.HotFixDAO;
import DAO.MaquinaDAO;
import DAO.PropriedadesDAO;
import DAO.SoftwareDAO;
import Entidades.Hardware;
import Entidades.Maquina;
import Entidades.Propriedades;
import Entidades.SO;
import Util.Log;
public class MonitorDeMudancas extends  Thread 
{
	private MaquinaDAO dao;
	HotFixDAO hotDAO = new HotFixDAO();
	SoftwareDAO softDAO = new SoftwareDAO();
	Conexao conexao = new Conexao();
	PropriedadesDAO propriedadesDAO  = new PropriedadesDAO();
	Propriedades propriedades = propriedadesDAO.getPropriedades();
	Maquina maquina = new Maquina();
	public MonitorDeMudancas(MaquinaDAO dao)
	{
		this.dao = dao;
	}
	public void iniciarSistema()
	{
		dao.gerarRelatorioDeHardware();
		dao.gerarRelatorioDeSoftware();
		maquina.setCodigoMaquina(dao.obterCodigoMaquina());
		maquina.setHostname(dao.obterHostname());
	    boolean enviar=false;
		if(dao.relatoriosDeHardwareDiferente())
		{
			Hardware hardware=dao.gerarListasDeAcoesEmHardware();
			maquina.setHardware(hardware);
			enviar=true;
		}
		if(dao.relatorioDeSoftwareDiferente())
		{
			List listaDeSoftware=dao.gerarListasDeAcoesEmSoftwares();
			maquina.setListaDeSoftwares(listaDeSoftware);
			enviar=true;
		}
		if (dao.relatorioDeHotFixDiferente())
		{
			List listaAtualizacoes = dao.gerarListaDeAcoesHotFix();
			maquina.setListaDeAtualizacoes(listaAtualizacoes);
			enviar=true;
		}
		if(dao.relatorioDeSistemaOperacionalDiferente())
		{
			SO so = dao.gerarListaDeAcoesSistemaOperacional();
			maquina.setSistemaOperacional(so);
			enviar=true;
		}
		if(dao.logsDeErroDiferentes()) 
		{
			List listaLogs = dao.gerarListaDeAcoesLogsErro();
			maquina.setListaDeLogsDeErro(listaLogs);
			enviar=true;
		}
		
		if (enviar==true)
		{
			if (conexao.enviar(propriedades, maquina))
			{
				dao.atualizarRelatoriosConsolidados();
				propriedadesDAO.gravarDatasDeUltimaAtualizacao();
			}
			enviar=false;
		}
		
	}
	public boolean modificado(String variavel)
	{
		return propriedadesDAO.modificado(variavel);
	}
	public boolean tempoDeRefresh()
	{
		try
		{
			String tempo=propriedades.getTempoDeAtualizacao();
			if (tempo!=null)
			{
				long intervalo=(6000*Integer.parseInt(tempo));
				System.out.println("Tempo Decorrido :"+intervalo+" mili");
				sleep(intervalo);
			}else
			{
				System.out.println("Tempo Decorrido :"+100000+" mili");
				sleep(1000);
			}
		return true;
		
		}
		catch(InterruptedException e)
		{
			System.out.println("Erro[MonitorDeMudancas:tempoDeRefresh]"+e.getMessage());
			e.printStackTrace();
			Log.escrever("[MonitorDeMudancas:tempoDeRefresh] :"+e.getMessage());
		}
		
		return false;
	}
	@Override
	public void run() 
	{
		try 
		{
			boolean enviado=false;
			while(true)
			{
				if (tempoDeRefresh())
				{
					if (modificado("winUpdate"))
					{
						//dao.gerarRelatorioCompleto();
						System.out.println("Modificado WinUpdate");
						hotDAO.gerarRelatorio();
						Maquina maquina = dao.gerarListasDeAcoes();
						if(conexao.enviar(propriedades,maquina))
						{
							dao.atualizarRelatoriosConsolidados();
							enviado=true;
						}
					}
					if (modificado("setupAct"))
					{
						System.out.println("Modificado setupAct");
						hotDAO.gerarRelatorio();
						Maquina maquina = dao.gerarListasDeAcoes();
						if(conexao.enviar(propriedades,maquina))
						{
							dao.atualizarRelatoriosConsolidados();
							enviado=true;
						}
					}
					if (modificado("programFiles"))
					{
						System.out.println("programfiles");
						softDAO.gerarRelatorio();
						Maquina maquina = dao.gerarListasDeAcoes();
						if(conexao.enviar(propriedades,maquina))
						{
							dao.atualizarRelatoriosConsolidados();
							enviado=true;
						}
					}
					if (modificado("programFilesx86"))
					{
						System.out.println("programFilesx86");
						softDAO.gerarRelatorio();
						Maquina maquina = dao.gerarListasDeAcoes();
						if(conexao.enviar(propriedades,maquina))
						{
							dao.atualizarRelatoriosConsolidados();
							enviado=true;
						}
					}
					if (enviado) propriedadesDAO.gravarDatasDeUltimaAtualizacao();
					enviado=false;
				  }	
			   }	 
			}
			catch (Exception e) 
			{
				System.out.println("Erro[MonitorDeMudancas:run] "+e.getMessage());
				e.printStackTrace();
				Log.escrever("[MonitorDeMudancas:run ]:"+e.getMessage());
			}
	 }
}

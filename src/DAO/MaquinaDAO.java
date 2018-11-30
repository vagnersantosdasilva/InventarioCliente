package DAO;

import java.util.List;
import DAO.AdaptadoresDeRedeDAO;
import DAO.CPUDAO;
import DAO.CdromDAO;
import DAO.HotFixDAO;
import DAO.MemoriasDAO;
import DAO.PlacaMaeDAO;
import DAO.SODAO;
import DAO.SoftwareDAO;
import DAO.SomDAO;
import DAO.UnidadeArmazenamentoDAO;
import DAO.VideoDAO;
import Entidades.CPU;
import Entidades.Hardware;
import Entidades.Maquina;
import Entidades.PlacaMae;
import Entidades.SO;
import Entidades.Som;
import Entidades.Video;
import Seguranca.Identidade;

public class MaquinaDAO 
{
	private AdaptadoresDeRedeDAO adapterDAO = new AdaptadoresDeRedeDAO();
	private UnidadeArmazenamentoDAO unidadesDAO = new UnidadeArmazenamentoDAO();
	private CPUDAO cpuDAO  = new CPUDAO();
	private CdromDAO cdDAO = new CdromDAO();
	private HotFixDAO hotfixDAO = new HotFixDAO();
	private MemoriasDAO memDAO = new MemoriasDAO();
	private PlacaMaeDAO placaDAO = new PlacaMaeDAO();
	private SODAO soDAO = new SODAO();
	private SoftwareDAO softDAO = new SoftwareDAO();
	private SomDAO somDAO = new SomDAO();
	private VideoDAO videoDAO = new VideoDAO();
	private LogErroDAO logDAO = new LogErroDAO();
	Identidade id =Identidade.getInstance();
	
	public String obterCodigoMaquina()
	{
		return id.getIdentidade();
	}
	public String obterHostname()
	{
		return soDAO.getRelatorioSO().getHostname();
	}
	public void gerarRelatorioCompleto()
	{
		adapterDAO.gerarRelatorioAdaptadores();
		cpuDAO.gerarRelatorioCPU();
		cdDAO.gerarRelatorioDeDiscos();
		hotfixDAO.gerarRelatorio();
		memDAO.gerarRelatorioMemorias();
		placaDAO.gerarRelatorioPlacaMae();
		soDAO.gerarRelatorioSO();
		softDAO.gerarRelatorio();
		somDAO.gerarRelatorioSom();
		unidadesDAO.gerarRelatorioDeDiscos();
		videoDAO.gerarRelatorioVideo();
		logDAO.executarScriptDeLeituraDeLogs();
		
	}
	
	@SuppressWarnings("unused")
	public Maquina gerarListasDeAcoes()
	{
		List listaDeAdaptadores =adapterDAO.gerarListaDeAcoes();
		List listaDeProgramas=softDAO.gerarListaDeAcoes();
		List listaDeLogs =logDAO.gerarListaDeAcoes();
		List listaDeCdrom =cdDAO.gerarListaDeAcoes();
		List listaDeHotFix = hotfixDAO.gerarListaDeAcoes();
		List listaDeMemorias =memDAO.gerarListaDeAcoes();
		List listaDeDiscos = unidadesDAO.getListaDeAcoes();
		
		CPU processador =cpuDAO.getComando();
		PlacaMae placa=placaDAO.getComando();
		SO sistemaOperacional=soDAO.getComando();
		Som som =somDAO.getComando();
		Video driveVideo=videoDAO.getComando();
		
		
		Hardware hardware = new Hardware();
		hardware.setCpu(processador);
		hardware.setPlacamae(placa);
		hardware.setSom(som);
		hardware.setVideo(driveVideo);
		hardware.setListaDeUnidadesDeArmazenamento(listaDeDiscos);
		hardware.setListaDeUnidadesDeCDDVD(listaDeCdrom);
		hardware.setListaDeMemorias(listaDeMemorias);
	
		Maquina maquina = new Maquina();
		maquina.setCodigoMaquina(id.getIdentidade());
		maquina.setHostname(sistemaOperacional.getHostname());
		maquina.setListaDeSoftwares(listaDeProgramas);
		maquina.setSistemaOperacional(sistemaOperacional);
		maquina.setHardware(hardware);
		maquina.setListaDeAtualizacoes(listaDeHotFix);
		maquina.setListaDeLogsDeErro(listaDeLogs);
		
		return maquina;
	}
	public void atualizarRelatoriosConsolidados()
	{
		adapterDAO.atualizarListaConsolidada();
		cpuDAO.atualizarListaConsolidada();
		cdDAO.atualizarListaConsolidada();
		hotfixDAO.atualizarListaConsolidada();
		memDAO.atualizarListaConsolidada();
		placaDAO.atualizarListaConsolidada();
		soDAO.atualizarListaConsolidada();
		softDAO.atualizarListaConsolidada();
		somDAO.atualizarListaConsolidada();
		unidadesDAO.atualizarListaConsolidada();
		videoDAO.atualizarListaConsolidada();
		logDAO.atualizarListaConsolidada();
		
	}

	public void gerarRelatorioDeHardware() 
	{
		adapterDAO.gerarRelatorioAdaptadores();
		cpuDAO.gerarRelatorioCPU();
		cdDAO.gerarRelatorioDeDiscos();
		memDAO.gerarRelatorioMemorias();
		placaDAO.gerarRelatorioPlacaMae();
		somDAO.gerarRelatorioSom();
		unidadesDAO.gerarRelatorioDeDiscos();
		videoDAO.gerarRelatorioVideo();
	}

	public void gerarRelatorioDeSoftware() 
	{
		soDAO.gerarRelatorioSO();
		softDAO.gerarRelatorio();
		hotfixDAO.gerarRelatorio();
		logDAO.executarScriptDeLeituraDeLogs();
	}

	public boolean relatoriosDeHardwareDiferente() {
		
		if ((Util.CoparadorDeArquivos.equals("repositorio/adaptadoresDeRede.ivt", "repositorioConsolidado/adaptadoresDeRede.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/driveCDROM.ivt", "repositorioConsolidado/driveCDROM.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/memorias.ivt", "repositorioConsolidado/memorias.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/placaMae.ivt", "repositorioConsolidado/placaMae.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/processadores.ivt", "repositorioConsolidado/processadores.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/som.ivt", "repositorioConsolidado/som.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/unidadeArmazenamento.ivt", "repositorioConsolidado/unidadeArmazenamento.ivt")==false)) return true;
		if ((Util.CoparadorDeArquivos.equals("repositorio/video.ivt", "repositorioConsolidado/video.ivt")==false)) return true;
		return false;
	}

	public Hardware gerarListasDeAcoesEmHardware() 
	{
		List listaDeAdaptadores =adapterDAO.gerarListaDeAcoes();
		CPU processador =cpuDAO.getComando();
		List listaDeCdrom =cdDAO.gerarListaDeAcoes();
		List listaDeMemorias =memDAO.gerarListaDeAcoes();
		PlacaMae placa=placaDAO.getComando();
		Som som =somDAO.getComando();
		List listaDeDiscos = unidadesDAO.getListaDeAcoes();
		Video driveVideo=videoDAO.getComando();
		
		Hardware hardware = new Hardware();
		hardware.setCpu(processador);
		hardware.setPlacamae(placa);
		hardware.setSom(som);
		hardware.setVideo(driveVideo);
		hardware.setListaDeUnidadesDeArmazenamento(listaDeDiscos);
		hardware.setListaDeUnidadesDeCDDVD(listaDeCdrom);
		hardware.setListaDeMemorias(listaDeMemorias);
		
		return hardware;
	
	}

	public boolean relatorioDeSoftwareDiferente() {
		
		if (Util.CoparadorDeArquivos.equals("repositorio/programas.ivt", "repositorioConsolidado/programas.ivt")) return false;
		return true;
	}

	public List gerarListasDeAcoesEmSoftwares() {
		
		return softDAO.gerarListaDeAcoes();
	}
	
	public List gerarListaDeAcoesHotFix()
	{
		return hotfixDAO.gerarListaDeAcoes();
	}
	public List gerarListaDeAcoesLogsErro() 
	{
		return logDAO.gerarListaDeAcoes();
	}

	public boolean relatorioDeHotFixDiferente() {
		if (Util.CoparadorDeArquivos.equals("repositorio/hotfix.ivt", "repositorioConsolidado/hotfix.ivt")) return false;
		return true;
	}
	public boolean logsDeErroDiferentes() {
		if (Util.CoparadorDeArquivos.equals("repositorio/erros.ivt", "repositorioConsolidado/erros.ivt")) return false;
		return true;
	}

	public boolean relatorioDeSistemaOperacionalDiferente() {
		
		if (Util.CoparadorDeArquivos.equals("repositorio/so.ivt", "repositorioConsolidado/so.ivt")) return false;
		return true;
	}

	public SO gerarListaDeAcoesSistemaOperacional() {
		
		return soDAO.getComando();
	}
	
}

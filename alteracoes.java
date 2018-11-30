Alterações em MaquinaDAO

public class MaquinaDAO 
{
	private LogErroDAO logDAO = new LogErroDAO();

	public void gerarRelatorioCompleto()
	{
		logDAO.executarScriptDeLeituraDeLogs();
	}

	public Maquina gerarListasDeAcoes()
	{
		List listaDeLogs =logDAO.gerarListaDeAcoes();
		maquina.setListaDeLogsDeErro(listaDeLogs);
	}
	
	public void atualizarRelatoriosConsolidados()
	{	
		logDAO.atualizarListaConsolidada();
	}

	public void gerarRelatorioDeSoftware() 
	{
		logDAO.executarScriptDeLeituraDeLogs();	
	}
	
	public boolean logsDeErroDiferentes() {
		if (Util.CoparadorDeArquivos.equals("repositorio/erros.ivt", "repositorioConsolidado/erros.ivt")) return false;
		return true;
	}
	
}	
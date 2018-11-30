package DAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import Entidades.AdaptadorDeRede;
import Entidades.LogErro;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

public class LogErroDAO 
{
	private List listaTemporaria =new ArrayList();
	private List listaConsolidada=new ArrayList();
	String categoria,eventCode,logFile,message,recordNumber,sourceName,timeGenerated,type,user=null;
	boolean inicio = false;
	boolean fim = false;
	
	public void executarScriptDeLeituraDeLogs()
	{	
		try 
		{
			Process relatorioMemoria = Runtime.getRuntime().exec("wscript scripts/logErro.vbs");
			relatorioMemoria.waitFor();
		} 
		catch (Exception e) 
		{
			System.out.println("Erro ao executar relatório de Memória RAM");
			System.out.println(e.getMessage());
		}
	  }
	@SuppressWarnings("rawtypes")
	public List getListaDeLogsDeErro()
	 {
		 return getlistaDeLogsDeErro("repositorio/erros.ivt");
	 }
		 
	 @SuppressWarnings("rawtypes")
	 public List getlistaDeLogsDeErro(String caminho)
	 {
		    Identidade id =Identidade.getInstance();
		    List listaDeLogs = new ArrayList();
			try {
				 Reader r = new FileReader(caminho);
				 BufferedReader br = new BufferedReader(r);
				 String linha;
				 while((linha=br.readLine())!=null)
				 {
					 
					 if (linha.indexOf("[")>=0) inicio =true; 
					 if (linha.indexOf("Category")>=0)  {categoria=linha;}
					 //if (linha.indexOf("CategoryString:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("CategoryString:?");};
					 //if (linha.indexOf("ComputerName:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("ComputerName:?");};
					 //if (linha.indexOf("Data:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("Data:?");};
					 if (linha.indexOf("EventCode")>=0) {eventCode=linha;} 
					 //if (linha.indexOf("EventIdentifier:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("EventIdentifier:?");};
					 //if (linha.indexOf("InsertionStrings:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("InsertionStrings:?");};
					 if (linha.indexOf("LogFile")>=0) {logFile=linha;}
					 if (linha.indexOf("Message")>=0) {message=linha;}
					 if (linha.indexOf("RecordNumber")>=0) {recordNumber=linha;}
					 if (linha.indexOf("SourceName")>=0) {sourceName=linha;}
					 if (linha.indexOf("TimeGenerated")>=0) {timeGenerated=linha;}
					 //if (linha.indexOf("TimeWritten:")>=0) {listaAuxiliar.add(linha);}else {listaAuxiliar.add("TimeWritten:?");};
					 if (linha.indexOf("Type")>=0) {type=linha;}
					 if (linha.indexOf("User")>=0) {user=linha;}
					 if (linha.indexOf("]")>=0) fim =true;
					 
					 if ((inicio==true) &&  (fim==true)) {
						 inicio=false;
						 fim=false;
						 LogErro log =obterLog(id.getIdentidade(),categoria,eventCode,logFile,message,recordNumber,sourceName,timeGenerated,type,user);
						 categoria=null;eventCode=null;logFile=null;message=null;recordNumber=null;sourceName=null;
						 timeGenerated=null;type=null;user=null;
						 listaDeLogs.add(log);
					 }
				 }
				 br.close();
				
				 	
			} catch (Exception e) {
				System.out.println("Erro EM :LogErroDAO");
				System.out.println(e.getMessage());
				e.printStackTrace();
				Log.escrever("[LogErroDAO:getlistaDeLogsDeErro]"+e.getMessage());
			}
			 return listaDeLogs;
		 }
	 
	 private LogErro obterLog(String id,String categoria, String eventCode, String logFile, String message,
			String recordNumber, String sourceName, String timeGenerated, String type, String user) {
		 LogErro log = new LogErro();
		 log.setCodigoMaquina(id);
		 if (categoria!=null) log.setCategory(categoria.split(":")[1].trim());
		 if (eventCode!=null) log.setEventCode(eventCode.split(":")[1].trim());
		 if (logFile!=null)  log.setLogFile(logFile.split(":")[1].trim());
		 if (message!=null)  log.setMessage(message.split(":")[1].trim());
		 if (recordNumber!=null) log.setRecordNumber(recordNumber.split(":")[1].trim());
		 if (sourceName!=null)  log.setSourceName(sourceName.split(":")[1].trim());
		 if (timeGenerated!=null) log.setTimeGenerated(timeGenerated.split(":")[1].trim());
		 if (type!=null)  log.setType(type.split(":")[1].trim());
		 if (user!=null)  log.setUser(user.split(":")[1].trim());
		return log;
	}
	 @SuppressWarnings("unchecked")
	public List gerarListaDeAcoes()
	 {
		 @SuppressWarnings("rawtypes")
		 List listaDefinitiva = new ArrayList();
		 listaTemporaria = getlistaDeLogsDeErro("repositorio/erros.ivt");
		 listaConsolidada =getlistaDeLogsDeErro("repositorioConsolidado/erros.ivt");
		for (int i=0;i<=listaConsolidada.size()-1;i++) 
		{
			LogErro adapt = new LogErro ();
			adapt=(LogErro)listaConsolidada.get(i);
			if (!(existe(listaTemporaria, adapt)))
			{
				adapt.setComando("excluir");
				listaDefinitiva.add(adapt);
				
			}
		}
		for(int i=0;i<=listaTemporaria.size()-1;i++)
		{
			LogErro adapt = new LogErro ();
			adapt = (LogErro)listaTemporaria.get(i);
			if(existe(listaConsolidada,adapt))
			{
				adapt.setComando("manter");
			}
			else
			{
				adapt.setComando("incluir");
				listaDefinitiva.add(adapt);
				
			}
		}
		
		return listaDefinitiva;
	}
	private boolean existe(List lista, LogErro log) 
	{
		for (int i=0; i<=lista.size()-1;i++)
		{
			LogErro adapt =new LogErro();
			adapt = (LogErro)lista.get(i);
			if (adapt.getTimeGenerated().equals(log.getTimeGenerated()))  return true;
		}
		return false;
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/erros.ivt", "repositorioConsolidado/erros.ivt")) return true;
		return false;
	}
		 
}

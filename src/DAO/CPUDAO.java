package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import Entidades.CPU;
import Seguranca.Identidade;
import Util.Log;
import Util.ManipuladorArquivos;

//deve ser sincronizado

public class CPUDAO
{
	
	public void gerarRelatorioCPU()
	{
		try {
			Process processo = Runtime.getRuntime().exec("wscript scripts/processadores.vbs");
			processo.waitFor();
		} catch (Exception e) {
			System.out.println("Erro ao tetar executar script - processador.vbs: CPUDAO");
			System.out.println(e.getMessage());
			Log.escrever("[CPUDAO:gerarRelatorioCPU] :"+e.getMessage());
		}
	}
	
	public CPU getCPU()
	{
		return getCPU("repositorio/processadores.ivt");
	}
	
	public CPU getComando() 
	{
		CPU temp = new CPU();
		CPU definitivo = new CPU();
		temp = getCPU("repositorio/processadores.ivt");
		definitivo = getCPU("repositorioConsolidado/processadores.ivt");
		if (definitivo==null) definitivo = new CPU();
		if (temp.getNome()==null)
		{
			temp.setNome("Falha");
			temp.setComando("naoCriado");
			return temp;
		}		
		if (temp.equals(definitivo)) 
		{
			temp.setComando("manter");
			return temp;
		}
		else
		{
			temp.setComando("atualizar");
			return temp;
		}
		
	}
	public CPU getCPU(String caminho)
	{
		Identidade id =Identidade.getInstance();
		CPU cpu=new CPU();
		try{
			
			cpu.setCodigoMaquina(id.getIdentidade());
			Reader r = new FileReader(caminho);
			BufferedReader br = new BufferedReader(r);
			String linha;
			while((linha=br.readLine())!=null)
			{
				if (linha.indexOf("Nome")>=0) cpu.setNome(linha.split(":")[1].trim()); 
				if (linha.indexOf("Arquitetura")>=0) cpu.setArquitetura(linha.split(":")[1].trim());
				if (linha.indexOf("ClockMaximo")>=0) cpu.setFrequenciaMaxima(linha.split(":")[1].trim());
				if (linha.indexOf("Nucleos")>=0) cpu.setNumeroNucleos(linha.split(":")[1].trim());
				if (linha.indexOf("NucleosLogicos")>=0) cpu.setNumeroProcessadoresLogicos(linha.split(":")[1].trim());
				if (linha.indexOf("Fabricante")>=0) cpu.setFabricante(linha.split(":")[1].trim());
				if (linha.indexOf("Status")>=0) cpu.setStatus(linha.split(":")[1].trim());
			}
			br.close();
			r.close();
			return cpu;
		}
		catch(Exception e){
			
				System.out.println("Erro ao tentar obter informações de CPU: CPUDAO" );
				System.out.println(e.getMessage());
				Log.escrever("[CPUDAO:getCPU] :"+e.getMessage());
		}
			
			return cpu; 
	}
	public boolean atualizarListaConsolidada()
	{
		if (ManipuladorArquivos.sobrescrever("repositorio/processadores.ivt", "repositorioConsolidado/processadores.ivt")) return true;
		return false;
	}
}
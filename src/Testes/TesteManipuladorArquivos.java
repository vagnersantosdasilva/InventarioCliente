package Testes;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import Util.ManipuladorArquivos;

public class TesteManipuladorArquivos {

	@Test
	public void testeCarregarLista() 
	{
		
		List lista = ManipuladorArquivos.carregarArquivoEmLista("repositorio/programas.ivt");
		for(int i =0 ;i<=lista.size()-1;i++)
		{
			System.out.println((String)lista.get(i));
		}
		
	}
	@Test
	public void testeCarregarArquivo()
	{
		List lista =ManipuladorArquivos.carregarArquivoEmLista("repositorio/programas.ivt");
		ManipuladorArquivos.salvarListaEmArquivos(lista, "repositorioConsolidado/programas.ivt");
	}

}

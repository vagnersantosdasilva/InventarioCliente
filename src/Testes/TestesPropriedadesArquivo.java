package Testes;

import static org.junit.Assert.*;

import org.junit.Test;

import Util.PropriedadesDeCaminho;

public class TestesPropriedadesArquivo {
	
	 ;
	 

	@Test
	public void test() 
	{
		assertEquals(Util.PropriedadesDeCaminho.getDataModificacao("c:/Saraiva/SaraivaReader/uninstall.exe"), "14-10-2013");
		assertEquals(Util.PropriedadesDeCaminho.getDataModificacao("c:/Saraiva/SaraivaReader"), "28-06-2017");
	}
	
	

}

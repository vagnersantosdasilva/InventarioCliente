package Testes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import DAO.MemoriasDAO;

public class TesteAcoesEmMemorias {

	@Test
	public void retornoEmListaDeAcoes() {
		MemoriasDAO dao = new MemoriasDAO();
		dao.gerarRelatorioMemorias();
		List listaDeMemorias =dao.gerarListaDeAcoes();
		
	}

}

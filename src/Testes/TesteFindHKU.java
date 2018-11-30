package Testes;

import static org.junit.Assert.*;

import org.junit.Test;

import DAO.SoftwareDAO;

public class TesteFindHKU {

	@Test
	public void test() {
		SoftwareDAO dao = new SoftwareDAO();
		dao.gerarRelatorio();
		
				
	}

}

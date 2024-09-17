package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection(); //create connection			
			st = conn.createStatement(); //create statement to consult in db
			rs = st.executeQuery("select \n"
					+ "       sjbicacm.cbpempenhos.numero, sjbicacm.cbpempenhos.dataempenho, sjbicacm.cbpempenhos.valorempenho, \n"
					+ "       sjbicacm.cbpliquidacoes.empenho, sjbicacm.cbpliquidacoes.liquidacao, sjbicacm.cbpliquidacoes.dataliquidacao, sjbicacm.cbpliquidacoes.valorliquidacao\n"
					+ "from \n"
					+ "       sjbicacm.cbpempenhos, sjbicacm.cbpliquidacoes\n"
					+ "where \n"
					+ "       sjbicacm.cbpempenhos.numero = sjbicacm.cbpliquidacoes.empenho and sjbicacm.cbpempenhos.ano >= '01-jan-2024' and sjbicacm.cbpliquidacoes.ano >= '01-jan-2024'\n"
					+ " order by \n"
					+ "       numero, liquidacao asc;");
					
			while (rs.next()) {
				System.out.println("Nº EMP " + rs.getInt("numero") + " | Data EMP: " + rs.getTimestamp("dataempenho") + " | Valor EMP: "
				+ rs.getDouble("valorempenho") + " | EMP: "
				+ rs.getInt("empenho") + " | Nº Liquidacao: "
				+ rs.getInt("liquidacao") + " | Data da Liquidacao: "
				+ rs.getTimestamp("dataliquidacao") + " | Valor Liquidacao: R$ "
				+ rs.getDouble("valorliquidacao"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}

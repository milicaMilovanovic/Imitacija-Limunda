package tableModeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entities.AukcijaTim3;

public class MojeAukcijeTableModel extends AbstractTableModel {

	private List<AukcijaTim3> aukcije;
	
	public MojeAukcijeTableModel() {
		aukcije = new ArrayList<>();
	}
	
	public void addAukcija(AukcijaTim3 aukcija) {
		aukcije.add(aukcija);
		fireTableDataChanged();
	}
	
	public void addAukcije(List<AukcijaTim3> lista) {
		aukcije.clear();
		aukcije.addAll(lista);
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return 6; 
	}

	@Override
	public int getRowCount() {
		return aukcije.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row < 0 ) || (row >= getRowCount())) {
			return null;
		}
		AukcijaTim3 aukcija = aukcije.get(row);
		switch (col) 
		{
		case 0:
			return aukcija.getTip();
		case 1:
			return aukcija.getCena();
		case 2:
			return aukcija.getMaterijal();
		case 3:
			return aukcija.getBoja();
		case 4:
			String stanje = "Zatvorena";
			if (aukcija.isAktuelna())
				stanje="Aktuelna";
			return stanje;
		default:
			return "Pregled";
		}
	}

	@Override
	public String getColumnName(int col) {
		switch(col)
		{
		case 0:
			return "Tip";
		case 1: 
			return "Cena";
		case 2:
			return "Materijal";
		case 3:
			return "Boja";
		case 4:
			return "Stanje";
		default:
			return " ";
		}
	}
	
	public boolean isCellEditable(int row,int cols){
		if(cols==0){return false;}	
		return true;
	} 
	
	public AukcijaTim3 getAukcija(int row){
		return aukcije.get(row);
	}


}

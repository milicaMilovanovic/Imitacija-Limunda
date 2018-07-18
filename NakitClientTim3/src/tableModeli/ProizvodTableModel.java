package tableModeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entities.AukcijaTim3;

public class ProizvodTableModel extends AbstractTableModel {
	
	private List<AukcijaTim3> proizvodi;

	public ProizvodTableModel() {
		proizvodi=new ArrayList<>();
	}
	
	public void addProizvod(AukcijaTim3 proizvod) {
		proizvodi.add(proizvod);
		fireTableDataChanged();
	}
	
	public void addProizvodi(List<AukcijaTim3> lista) {
		proizvodi.clear();
		proizvodi.addAll(lista);
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return proizvodi.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row < 0) || (row >= getRowCount())) {
			return null;
		}
		AukcijaTim3 aukcija = proizvodi.get(row);
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
			return aukcija.getKorisnik().getkIme();
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
			return "Prodavac";
		default:
			return " ";
		}
	}
	
	public boolean isCellEditable(int row,int cols){
		if(cols==0){return false;}	
		return true;
	} 
	
	public AukcijaTim3 getAukcija(int row){
		return proizvodi.get(row);
	}


	
}

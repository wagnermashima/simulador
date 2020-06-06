package br.feevale.simulador.domain;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

public class ChamadoExcelBuilder {

	private File file;

	public ChamadoExcelBuilder(File file) {
		this.file = file;
	}

	public List<Chamado> build() {
		try {
			List<Chamado> result = new ArrayList<Chamado>();

			FileInputStream arquivo = new FileInputStream(file);
			
			XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook(arquivo);
			
			XSSFSheet sheetAlunos = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheetAlunos.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				Cell nrChamadoCell = row.getCell(0);

				if (nrChamadoCell == null || nrChamadoCell.getCellType() == CellType.BLANK) continue;
				
				Chamado chamado = new Chamado();
				result.add(chamado);

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					

					
					switch (cell.getColumnIndex()) {
					case 0:
						Double numericCellValue = cell.getNumericCellValue();
						chamado.setNrChamado(numericCellValue.intValue());
						break;
					case 1:
						chamado.setDtEntradaDesenvolvimento(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 2:
						chamado.setDtSaidaDesenvolvimento(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 3:
						chamado.setDtProximoChamado(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 7:
						Double prioridadeCellValue = cell.getNumericCellValue();
						chamado.setPrioridade(prioridadeCellValue.intValue());
						break;
					case 8:
						Double cdClienteCellValue = cell.getNumericCellValue();
						chamado.setCdCliente(cdClienteCellValue.intValue());
						break;
					}

				}
				
				
			}
			arquivo.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private LocalDateTime convertLocalDateTime(Date dateCellValue) {
		if (dateCellValue == null) return null;
		LocalDateTime localDateTime = dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime;
	}
	
}

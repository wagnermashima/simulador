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
						chamado.setNrChamado(extractInteger(cell));
						break;
					case 1:
						chamado.setDtEntradaDesenvolvimento(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 2:
						chamado.setDtEntradaBacklog(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 3:
						chamado.setDtSaidaDesenvolvimento(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 4:
						chamado.setDtProximoChamado(convertLocalDateTime(cell.getDateCellValue()));
						break;
					case 7:
						chamado.setPrioridade(extractInteger(cell));
						break;
					case 8:
						chamado.setCdCliente(extractInteger(cell));
						break;
					}
					
					chamado.calcularTempoChamado();

				}
				
				
			}
			arquivo.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Integer extractInteger(Cell cell) {
		try {
			Double numericCellValue = cell.getNumericCellValue();
			return numericCellValue.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private LocalDateTime convertLocalDateTime(Date dateCellValue) {
		try {
			if (dateCellValue == null) return null;
			LocalDateTime localDateTime = dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return localDateTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

package edu.nile.os.services;

// Java program to write data in excel sheet using java code

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WriteDataToExcel {
    private final Map<String, Object[]> output;
    private final String code;

    public WriteDataToExcel(Map<String, Object[]> output, String code) {
        this.output = output;
        this.code = code;
    }

    public void generate() throws Exception
    {
        // workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();

        // spreadsheet object
        XSSFSheet spreadsheet
                = workbook.createSheet(code);

        // creating a row object
        XSSFRow row;

        // This data needs to be written (Object[])
        Map<String, Object[]> results = new TreeMap<>();

        results.put("1", new Object[] { "No of Processes", "SRTF", "PNP", "RR" });

        int count = 2;
        for (Map.Entry<String,Object[]> entry : output.entrySet()) {
            Object[] item = entry.getValue();
            results.put(String.valueOf(count), new Object[] {entry.getKey(), item[0], item[1], item[2]});
            count++;
        }

        Set<String> keyid = results.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = results.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        //TODO Customize directory
        FileOutputStream out = new FileOutputStream(String.format("/Users/adedara/Projects/NILE/cpu-scheduling-simulation/src/main/resources/%s.xlsx", code));

        workbook.write(out);
        out.close();
        workbook.close();
    }
}
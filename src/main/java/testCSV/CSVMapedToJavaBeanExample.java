package testCSV;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import testCSV.model.ThMccCodeA;
import testCSV.model.TrTypesA;

import java.io.FileReader;
import java.util.List;

public class CSVMapedToJavaBeanExample {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) throws Exception
    {
/*
        List<Employee> employees = new CsvToBeanBuilder(new FileReader("employees.csv"))
                .withType(Employee.class)
                .build()
                .parse();
        System.out.println(employees);*/




        try {

            String csvFilenameTr_mcc_codes = "tr_mcc_codes.csv";
            FileReader filereaderTr_mcc = new FileReader(csvFilenameTr_mcc_codes);

            com.opencsv.CSVParser parserTr_mcc = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReaderTr_mcc = new CSVReaderBuilder(filereaderTr_mcc)
                    .withCSVParser(parserTr_mcc)
                    .build();
            List<String[]> allDataTr_mcc = csvReaderTr_mcc.readAll();

            for (String[] row : allDataTr_mcc) {
                System.out.print(row[0] + "\t");
                /*for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();*/
            }

            String csvFilenameTr_types = "tr_types.csv";
            FileReader filereader = new FileReader(csvFilenameTr_types);

            com.opencsv.CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        /*
        CsvToBean csv = new CsvToBean();
        String csvFilenameTr_types = "src/main/java/testCSV/tr_types.csv";
        String csvFilenameTr_mcc_codes = "tr_mcc_codes.csv";
        CSVReader csvReaderTr_types = new CSVReader(new FileReader(csvFilenameTr_types), ';');
        CSVReader csvReaderTr_mcc_codes = new CSVReader(new FileReader(csvFilenameTr_mcc_codes), ';');
        //Установить стратегию сопоставления столбцов
        List listTr_types = csv.parse(setColumTrTypes(), csvReaderTr_types);///////////////
        for (Object object : listTr_types) {
            TrTypesA trTypes = (TrTypesA) object;
            System.out.println(trTypes);
        }
        List listTr_mcc_codes = csv.parse(setColumThMccCode(), csvReaderTr_mcc_codes);///////////////
        for (Object object : listTr_mcc_codes) {
            ThMccCodeA thMccCode = (ThMccCodeA) object;
            System.out.println(thMccCode);
        }*/
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumTrTypes()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(TrTypesA.class);
        String[] columnsType = new String[] {"tr_type", "tr_description"};
        strategy.setColumnMapping(columnsType);
        return strategy;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumThMccCode()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(ThMccCodeA.class);
        String[] columnsMcc = new String[] {"mcc_code", "mcc_description"};
        strategy.setColumnMapping(columnsMcc);
        return strategy;
    }
}

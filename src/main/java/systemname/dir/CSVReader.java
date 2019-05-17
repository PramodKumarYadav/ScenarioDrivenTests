package systemname.dir;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import systemname.global.CurrentTSTable;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVReader {
    private final static Logger logger = org.apache.log4j.Logger.getLogger(CurrentTSTable.class);

    private static final String SAMPLE_CSV_FILE_PATH = "C:\\SourceCodeTLM\\Juno\\ftproot\\payplaza\\payplaza_enriched_transactions_2.csv";

    @Test
    public void test2() {
        getCountOfCSVRecords(SAMPLE_CSV_FILE_PATH);
    }
    @Test
    public Integer getCountOfCSVRecords(String filePath) {

        Integer countOfCSVRecords= 0;
        CSVParser csvParser = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {

            String sourceName = getSourceName(filePath);
            if (sourceName.equalsIgnoreCase("payplaza")) {
                // We have no headers and Delimiter values are comma separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(',')
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            } else{
                // We have first record as headers and Delimiter values are semicolon separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            }

            countOfCSVRecords = csvParser.getRecords().size();
            System.out.println("countOfCSVRecords: " + countOfCSVRecords);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return countOfCSVRecords;
    }
    public static String getSourceName(String filePath) {
        String[] filePathArray = filePath.replace("\\", "/").split("/");
        String sourceName = filePathArray[filePathArray.length - 2];

        logger.info("sourceName: " + sourceName);
        return sourceName;
    }
    @Test
    public CSVParser getParsedCSV(String filePath){

        CSVParser csvParser = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {

            String sourceName = getSourceName(filePath);
            if (sourceName.equalsIgnoreCase("payplaza")) {
                // We have no headers and Delimiter values are comma separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(',')
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            } else{
                // We have first record as headers and Delimiter values are semicolon separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvParser;
    }

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\SourceCodeTLM\\Juno\\ftproot\\payplaza\\payplaza_enriched_transactions_2.csv";

        String sourceName = getSourceName(filePath);

        Integer countOfCSVRecords= 0;

        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
            CSVParser csvParser = null;
            if (sourceName.equalsIgnoreCase("payplaza")) {
                // We have no headers and Delimiter values are comma separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(',')
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            } else{
                // We have first record as headers and Delimiter values are semicolon separated
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withIgnoreEmptyLines(true)
                        .withTrim());
            }


            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                System.out.println("---------------");
                System.out.println(csvRecord);
                System.out.println(csvRecord.get(0));
                System.out.println(csvRecord.get(1));
                System.out.println(csvRecord.get(2));
                System.out.println(csvRecord.get(3));
                System.out.println(csvRecord.get(4));
                System.out.println(csvRecord.get(5));
                System.out.println(csvRecord.get(6));

                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------\n\n");
//                System.out.println("Name : " + csvRecord.get(0));
//                System.out.println("Email : " + email);
//                System.out.println("Phone : " + phone);
//                System.out.println("Country : " + country);
//                System.out.println("---------------\n\n");
            }
        }
    }
}

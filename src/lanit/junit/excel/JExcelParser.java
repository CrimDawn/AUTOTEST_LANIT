package lanit.junit.excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lanit.junit.StartAutotest;
import lanit.junit.form.fields.*;
import lanit.junit.testcase.TestCaseBasicInfo;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JExcelParser {

    private Logger log = Logger.getLogger(JExcelParser.class);
    private Workbook workbook = null;
    private ArrayList<TestCaseBasicInfo> executableListTestCaseBasicInfos = new ArrayList<TestCaseBasicInfo>();

    /**
     * Creation instance of TestSuites.xls parser
     * @param workbookPath - string path to TestSuites.xls
     */
    public JExcelParser(String workbookPath) {
        initialize(workbookPath);
    }

    /**
     * Method initialize Excel parser instance
     * @param workbookPath - string path to TestSuites.xls
     */
    private void initialize(String workbookPath) {
        try {
            this.workbook = Workbook.getWorkbook(new File(workbookPath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (BiffException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method defines scope of TestCaseBasicInfo test cases from TestSuites.xls
     * @return array list of TestCaseBasicInfo
     */
    public ArrayList<TestCaseBasicInfo> getExecutable() {
        String number = "";
        String alias = "";
        String execute = "";
        try {
            if (StartAutotest.SINGLE_TEST_CASE.equals("")) {
                log.info("Запуск автотеста в режиме: Multiple");
                Sheet mainSheet = workbook.getSheet("Тестовые планы");
                int testSuiteCount = mainSheet.getRows();
                for (int i = 1; i < testSuiteCount; i++) {
                    number = mainSheet.getCell(getColumnNumberByColumnName(mainSheet, "Номер п/п"), i).getContents();
                    alias = mainSheet.getCell(getColumnNumberByColumnName(mainSheet, "Наименование"), i).getContents();
                    execute = mainSheet.getCell(getColumnNumberByColumnName(mainSheet, "Выполнение"), i).getContents();
                    if (execute.equalsIgnoreCase("Да")) {
                        executableListTestCaseBasicInfos.add(new TestCaseBasicInfo(number, alias, execute));
                    }
                }
            } else {
                log.info("Запуск автотеста в режиме: Single");
                number = "1";
                alias = StartAutotest.SINGLE_TEST_CASE;
                execute = "Да";
                executableListTestCaseBasicInfos.add(new TestCaseBasicInfo(number, alias, execute));
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
        return executableListTestCaseBasicInfos;
    }

    /**
     * Method defines column number by it's header name
     * @param sheet - name of sheet with column
     * @param columnName - name of column header
     * @return number of column
     */
    private int getColumnNumberByColumnName(Sheet sheet, String columnName) {
        int columnIndex = 0;
        while (true) {
            try {
                String columnHeader = sheet.getCell(columnIndex,  0).getContents();
                if (columnHeader.equals(columnName)) {
                    break;
                } else {
                    columnIndex++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                log.warn("Столбец с заголовком '" + columnName + "' не существует");
                columnIndex = -1;
                break;
            }
        }
        return columnIndex;
    }

    /**
     * Method defines current test case steps for form testing
     * @param sheetName - name of sheet with steps for current test case
     * @return hash map of steps for form testing
     */
    public HashMap<Integer, FieldAction> getSheetActionContent(String sheetName) {
        HashMap<Integer, FieldAction> testPlan = new HashMap<Integer, FieldAction>();
        String fieldType = "";
        String fieldIdentification = "";
        String fieldIdentificationValue = "";
        String fieldValue = "";
        String fieldAutoValue = "";
        String fieldReadOnly = "";
        String fieldFormatFile= "";
        String fieldValidation = "";
        try {
            Sheet currentSheet = workbook.getSheet(sheetName);
            int stepsCount = currentSheet.getRows();
            for (int i = 1; i < stepsCount; i++) {
                fieldType = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Тип поля"), i).getContents();
                fieldIdentification = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Идентификатор поля"), i).getContents();
                fieldIdentificationValue = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Значение идентификатора поля"), i).getContents();
                fieldValue = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Значение поля"), i).getContents();
                fieldAutoValue = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Автозаполняемое значение поля"), i).getContents();
                fieldReadOnly = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Только для чтения"), i).getContents();
                fieldFormatFile = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Формат файла"), i).getContents();
                fieldValidation = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Валидация"), i).getContents();
                if (fieldType.equalsIgnoreCase("Текстовое")) {
                    testPlan.put(i, new TextFieldAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Мемо")) {
                    testPlan.put(i, new MemoAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Радио-кнопка")) {
                    testPlan.put(i, new RadioButtonAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Чекбокс")) {
                    testPlan.put(i, new CheckBoxAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Чекбокс связной")) {
                    testPlan.put(i, new CheckBoxLinkedAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Далее")) {
                    testPlan.put(i, new NextAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Назад")) {
                    testPlan.put(i, new PrevAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Подать заявление")) {
                    testPlan.put(i, new ApplyRequestAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Виджет 'Выбрать'")) {
                    testPlan.put(i, new WidgetChoiceAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Виджет 'Выбрать' с чекбоксом")) {
                    testPlan.put(i, new WidgetChoiceWithCheckBoxAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Виджет 'Календарь'")) {
                    testPlan.put(i, new WidgetCalendarAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Загрузка файла")) {
                    testPlan.put(i, new UploadFileAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Пропустить шаг вперед")) {
                    testPlan.put(i, new SkipStepUpAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Пропустить шаг назад")) {
                    testPlan.put(i, new SkipStepDownAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Заголовок шага")) {
                    testPlan.put(i, new StepHeaderAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                } else if (fieldType.equalsIgnoreCase("Новый сценарий")) {
                    testPlan.put(i, new NewScenarioAction(fieldType, fieldIdentification, fieldIdentificationValue, fieldValue, fieldAutoValue, fieldReadOnly, fieldFormatFile, fieldValidation));
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return testPlan;
    }

    /**
     * Method defines current test name of service for form testing
     * @param sheetName - name of sheet with name of service for current test case
     * @return name of service
     */
    public String getNameOfService(String sheetName) {
        String nameOfService = "";
        try {
            Sheet currentSheet = workbook.getSheet(sheetName);
            nameOfService = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Наименование"), 1).getContents();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return nameOfService;
    }

    /**
     * Method defines current test case path to service for form testing
     * @param sheetName - name of sheet with path to service for current test case
     * @return path to service
     */
    public String getPathToService(String sheetName) {
        String pathToService = "";
        try {
            Sheet currentSheet = workbook.getSheet(sheetName);
            pathToService = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Путь до услуги"), 1).getContents();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return pathToService;
    }

    /**
     * Method defines current test case region for service for form testing
     * @param sheetName - name of sheet with region for service for current test case
     * @return region for service
     */
    public String getRegionForService(String sheetName) {
        String regionForService = "";
        try {
            Sheet currentSheet = workbook.getSheet(sheetName);
            regionForService = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "Регион"), 1).getContents();
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e);
        }
        return regionForService;
    }

    /**
     * Method defines current test case URL for service for form testing
     * @param sheetName - name of sheet with URL for service for current test case
     * @return URL for service
     */
    public String getURLForService(String sheetName) {
        String urlForService = "";
        try {
            Sheet currentSheet = workbook.getSheet(sheetName);
            if (currentSheet != null) {
                urlForService = currentSheet.getCell(getColumnNumberByColumnName(currentSheet, "URL"), 1).getContents();
            }
        } catch (NullPointerException e) {
            log.warn(e.getMessage(), e);
        }
        return urlForService;
    }
}

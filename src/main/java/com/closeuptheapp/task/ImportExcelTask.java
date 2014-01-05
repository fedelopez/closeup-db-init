package com.closeuptheapp.task;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.domain.QuestionFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: fede
 * Date: May 22, 2010
 * Time: 1:48:45 PM
 */
public class ImportExcelTask implements Task<File, List<AbstractQuestion>> {

    private static final int QUESTION_ID_COLUMN = 0;
    private static final int DIFFICULTY_COLUMN = 1;
    private static final int TYPE_COLUMN = 2;
    private static final int QUESTION_TEXT_COLUMN = 3;
    private static final int POSSIBLE_ANSWER_COLUMN = 4;

    private File excelWorkbook;
    private TaskCallback callback;

    public void init(File initObject) {
        this.excelWorkbook = initObject;
    }

    public List<AbstractQuestion> execute(TaskCallback callback) throws Exception {
        this.callback = callback;
        XSSFWorkbook workbook = new XSSFWorkbook(excelWorkbook.getAbsolutePath());
        XSSFSheet sheet = workbook.getSheetAt(0);
        return createQuestions(workbook, sheet);
    }

    public String name() {
        return "Import from Excel file task";
    }

    private List<AbstractQuestion> createQuestions(XSSFWorkbook workbook, XSSFSheet sheet) {
        List<AbstractQuestion> questions = new ArrayList<AbstractQuestion>();

        for (int rowIndex = 3; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex += 4) {
            XSSFRow row = sheet.getRow(rowIndex);
            readQuestion(workbook, sheet, row, questions, 0);
            readQuestion(workbook, sheet, row, questions, 4);
        }
        return questions;
    }

    private void readQuestion(XSSFWorkbook workbook, XSSFSheet sheet, Row row, List<AbstractQuestion> questions, int offset) {
        String questionText;
        try {
            questionText = row.getCell(QUESTION_TEXT_COLUMN + offset).getStringCellValue();
        } catch (Exception e) {
            return;
        }
        if (StringUtils.isBlank(questionText)) {
            return;
        }

        String typeString = row.getCell(TYPE_COLUMN + offset).getStringCellValue();
        AbstractQuestion question = QuestionFactory.instanceFromType(typeString);
        questions.add(question);
        question.setQuestionIndex(questions.size());
        question.setPictureId(row.getCell(QUESTION_ID_COLUMN).getStringCellValue() + ".jpg");
        question.setDifficulty(getIntegerCellValue(row.getCell(DIFFICULTY_COLUMN + offset)));
        question.setType(AbstractQuestion.Type.questionTypeFromString(typeString));
        question.setQuestion(questionText);

        List<String> hints = new ArrayList<String>();
        int from = row.getRowNum();
        int to = row.getRowNum() + 4;
        for (int index = from; index < to; index++) {
            row = sheet.getRow(index);
            String value = getCellValue(row.getCell(POSSIBLE_ANSWER_COLUMN + offset));
            if (StringUtils.isNotBlank(value)) {
                hints.add(value);
                if (isBold(workbook, row, offset)) {
                    question.setCorrectAnswer(value);
                }
            }
        }
        question.setHints(hints);
        callback.taskDone(">> Question read: " + question);
    }

    private String getCellValue(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).toUpperCase();
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return cell.getStringCellValue().trim();
    }

    private int getIntegerCellValue(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return -1;
    }

    private boolean isBold(XSSFWorkbook workbook, Row row, int offset) {
        short fontIndex = row.getCell(POSSIBLE_ANSWER_COLUMN + offset).getCellStyle().getFontIndex();
        XSSFFont font = workbook.getFontAt(fontIndex);
        return font.getBold();
    }
}

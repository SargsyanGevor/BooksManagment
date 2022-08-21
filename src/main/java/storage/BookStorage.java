package storage;


import model.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BookStorage {

    private static Book[] array = new Book[10];
    private static int size = 0;


    public void add(Book books) {
        if (size == array.length) {
            increaseArray();
        }
        array[size++] = books;
    }

    private void increaseArray() {
        Book[] temp = new Book[size + 10];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];

        }
        array = temp;
    }


    public void print() {
        for (int i = 0; i < size; i++) {

            System.out.println(i + ". " + array[i]);
        }
    }

    public void printBooksByAuthorName(String authorName) {

        for (int i = 0; i < size; i++) {
            if (array[i].getAuthor().equals(authorName)) {
                System.out.println(array[i]);
            }

        }

    }

    public void printBooksByGenre(String bookGenre) {

        for (int i = 0; i < size; i++) {
            if (array[i].getGenre().equalsIgnoreCase(bookGenre)) {
                System.out.println(array[i]);
            }
        }
//        System.err.println("where is not found any book by this genre , try another keyword" );
    }

    public void printBooksByPriceRange(double priceRange, double priceRange1) {

        for (int i = 0; i < size; i++) {
            if (array[i].getPrice() >= priceRange && array[i].getPrice() <= priceRange1) {
                System.out.println(array[i]);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Book getStudentBayIndex(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[index];
    }

    public void writeBooksToExcel(String fileDir) throws IOException {
        File directory = new File(fileDir);
        if (directory.isFile()) {
            throw new RuntimeException("fileDir must be a directory!");
        }
        File excelFile = new File(directory, "books" + System.currentTimeMillis() + ".xlsx");
        excelFile.createNewFile();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("books");
        Row header = sheet.createRow(0);
        Cell cellTitle = header.createCell(0);
        cellTitle.setCellValue("Title");

        Cell cellPrice = header.createCell(1);
        cellPrice.setCellValue("Price");

        Cell cellCount = header.createCell(2);
        cellCount.setCellValue("Count");

        Cell cellGenre = header.createCell(3);
        cellGenre.setCellValue("Genre");



        for (int i = 0; i < size; i++) {
            Book book = array[i];
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(book.getTitle());
            row.createCell(1).setCellValue(book.getPrice());
            row.createCell(2).setCellValue(book.getCount());
            row.createCell(3).setCellValue(book.getGenre());

        }
        workbook.write(new FileOutputStream(excelFile));
        System.out.println("Excel was created");
    }
}




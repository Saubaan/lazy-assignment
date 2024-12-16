import edu.duke.DirectoryResource;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LazyAssignment {
    private XWPFDocument document;
    private User user;
    int noOfQuestions;
    int assignmentNo;

    // constructor for Lazy people @rushan
    public LazyAssignment(XWPFDocument document, User user) {
        this.document = document;
        this.user = user;
        initializeAssignment(this.document, this.user);

    }

    // initialize the document with user details @rushan / @Saubaan
    private void initializeAssignment(XWPFDocument document, User user) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Assignment no.: ");
        this.assignmentNo = scan.nextInt();

        System.out.print("Enter no. of questions : ");
        this.noOfQuestions = scan.nextInt();

        try {
            FileOutputStream outputStream = new FileOutputStream("Assignment-" + this.assignmentNo + ".docx");
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            System.out.println("File Created");
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Assignment " + this.assignmentNo);
            titleRun.setFontSize(25);
            titleRun.setCapitalized(true);
            titleRun.setBold(true);
            addSpace(titleRun);

            // @Saubaan/@rushan initialize user info
            XWPFParagraph userDetails = document.createParagraph();
            XWPFRun userDetailsRun = userDetails.createRun();
            userDetailsRun.setFontSize(14);
            userDetailsRun.setText("Name: " + user.getUserName());
            userDetailsRun.addBreak();
            userDetailsRun.setText("Class: " + user.getUserClass());
            userDetailsRun.addBreak();
            userDetailsRun.setText("Roll No.: " + user.getUserRoll());
            userDetailsRun.addBreak();
            addSpace(userDetailsRun);

            document.write(outputStream);
//          document.close();
        } catch (IOException e) {
            System.err.println("Document Error: " + e.getMessage());
        }

    }

    public void generateAssignmentBody(XWPFDocument document) {


        // @Saubaan Get question text from User
        ArrayList<String> questions = getQuestions();


        // @rushan getting file path of selected files
        ArrayList<String> selectedFiles = getJavaFilePaths();

        // @Saubaan getting image files path
//        ArrayList<String> selectedFiles = getJavaFilePaths();

        try {
            FileOutputStream outputStream = new FileOutputStream("Assignment-" + this.assignmentNo + ".docx");
            // @Saubaan create body for questions
            XWPFParagraph questionTextPara;
            XWPFRun questionTextRun;
            for (int i = 0; i < selectedFiles.size(); i++) {

                // @Saubaan Create paragraph for question
                questionTextPara = document.createParagraph();
                questionTextPara.setAlignment(ParagraphAlignment.LEFT);
                questionTextRun = questionTextPara.createRun();
                questionTextRun.setBold(true);
                questionTextRun.setFontSize(13);
                questionTextRun.setText("Q." + (i+1) + " " + questions.get(i));
                questionTextRun.addBreak();

                // @Saubaan Create title for "Program: " text
                XWPFParagraph programTitlePara = document.createParagraph();
                programTitlePara.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun programTitleRun = programTitlePara.createRun();
                programTitleRun.setBold(true);
                programTitleRun.setText("Program: ");
                programTitleRun.addBreak();

                // @Saubaan Create paragraph for the program text
                XWPFParagraph programTextPara = document.createParagraph();
                programTextPara.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun programTextRun = programTextPara.createRun();
                writeFileToDocument(programTextRun, selectedFiles.get(i));

                // @rushan create output segment
                XWPFParagraph outputTitlePara = document.createParagraph();
                programTitlePara.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun outputTitleRun = outputTitlePara.createRun();
                outputTitleRun.setBold(true);
                outputTitleRun.setText("Output: ");
                outputTitleRun.addBreak();

                // @Saubaan Create a new page for each question
                document.createParagraph().createRun().addBreak(BreakType.PAGE);
            }

            document.write(outputStream);
        } catch (IOException e) {
            System.err.println("Document Error: " + e.getMessage());
        }
    }

    //    writing the selected files to word document @rushan
    private void writeFileToDocument(XWPFRun run, String filePath) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                run.setText(line);
                run.addBreak();
            }
        } catch (IOException e) {
            System.err.println("An Error occurred: " + e.getMessage());
        }
    }

    //    get multiple file paths to manipulate as per our requirement @rushan
    private ArrayList<String> getJavaFilePaths() {
        ArrayList<String> selectedFilePaths = new ArrayList<String>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            selectedFilePaths.add(f.getAbsolutePath());
        }

        return selectedFilePaths;
    }

    // @rushan add space between segments or blocks
    private void addSpace(XWPFRun run) {
        for (int i = 0; i < 2; i++) {
            run.addBreak();
        }
    }

    // @Saubaan get questions from user using scanner
    public ArrayList<String> getQuestions(){
        ArrayList<String> questions = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < this.noOfQuestions; i++) {
            System.out.printf("Enter question no %d: ", i+1);
            String question = scan.nextLine();
            questions.add(question);
        }
        return questions;
    }

    // @rushan main initialized
    public static void main(String[] args) {
        XWPFDocument document = new XWPFDocument();
        User user = new User();
        LazyAssignment main = new LazyAssignment(document, user);
        main.generateAssignmentBody(document);
        System.out.println("Operation completed successfully! ");
    }
}

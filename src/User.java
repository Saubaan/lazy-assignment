// User class @rushan

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class User {

    private String[] information = new String[3];

    public User() {
        File file = new File(".user_info.txt");

        try {
            Path filePath = Paths.get(".user_info.txt");
            Files.setAttribute(filePath, "dos:hidden", true);
        } catch(IOException e){
            System.out.println("Error: You successfully managed to delete user_info.txt ");
        }

        try {
            if (!fileLengthZero(file)) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                information = reader.readLine().split(",");
                reader.close();
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                inputUserDetails(writer);
                try {
                    Path filePath = Paths.get(".user_info.txt");
                    Files.setAttribute(filePath, "dos:hidden", true);
                } catch(IOException e){
                    System.out.println("Error: You successfully managed to delete user_info.txt ");
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error: You have no friends to add in user_info.txt ");
        }

    }

    // Getters
//    public int getAssignmentNumber() { return Integer.parseInt(information[0]); }
//    public int getNumberOfQuestions(){ return Integer.parseInt(information[1]); }
    public String getUserName() { return information[0]; }
    public String getUserRoll() { return information[1]; }
    public String getUserClass() { return information[2]; }

    // Setters
//    public void setAssignmentNumber(String value) { information[0] = value; }
//    public void setNumberOfQuestions(String value) { information[1] = value; }
    public void setUserName(String value) { information[0] = value; }
    public void setUserRoll(String value) { information[1] = value; }
    public void setUserClass(String value) { information[2] = value; }

    // method to print details of the user @rushan
    public void getUserDetails() {
        for (String value : information) {
            System.out.println(value);
        }
    }

    private boolean fileLengthZero(File file) {
        return file.length() == 0;
    }

    private void inputUserDetails(BufferedWriter writer) {
        String[] inputInformation = new String[information.length];
        String[] questions = {"Name : ", "Roll No. : ", "Class : "};
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < information.length; i++) {
            System.out.print(questions[i]);
            inputInformation[i] = scan.nextLine();
        }

        String addThisLineToFile = String.join(",", inputInformation);
        try {
            writer.write(addThisLineToFile);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: You successfully managed to delete user_info.txt ");
        }
    }

    public void updateUserDetails(File file) {
        if (fileLengthZero(file)) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                inputUserDetails(writer);

            } catch (IOException e) {

            }
        } else {
            Scanner scan = new Scanner(System.in);
            while (true) {
                System.out.println("1.Name 2.Roll 3.Class 4.Save and Exit");
                System.out.print("Enter the value to update (1, 2, 3, 4) ; ");
                String choice = scan.next();
                switch (choice) {
                    case "1":
                        setUserName(scan.nextLine());
                        break;
                    case "2":
                        setUserRoll(scan.nextLine());
                        break;
                    case "3":
                        setUserClass(scan.nextLine());
                        break;
                    case "4":
                        writeUpdatesToFile(information);
                        System.exit(1);
                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    private void writeUpdatesToFile(String[] changedInformation) {
        String addThisLineToFile = String.join(",", changedInformation);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(".input.txt"));
            writer.write(addThisLineToFile);
            writer.close();
        } catch (IOException e) {

        }
    }

}

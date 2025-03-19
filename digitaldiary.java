import java.util.*;
import java.util.Vector;

// ------------------------------------------------------ Diary class ------------------------------------------------------------------------- //
class Diary{
    private String date;
    private String content;

    Diary(String date, String content){
        this.date = date;
        this.content = content;
    }
    public String getDate(){
        return this.date;
    }
    public String getcontent(){
        return this.content;
    }

    @Override
    public String toString(){
        return "Date: " + date + "\n" + "Entry: " + content;
    }
}
// -------------------------------------------------- Diary Manager Class --------------------------------------------------------------------- //
class DiaryManager{
    private Vector<Diary> D;
    private Scanner sc;

    public DiaryManager(){
        D = new Vector<>();
        sc = new Scanner(System.in);
    }

    // --------------------------- Add Entry ---------------------------------------- //
    public void AddEntry(){
        System.out.print("Enter date: ");
        String dat = sc.nextLine();

        System.out.println("Enter your diary entry: ");

        StringBuilder diaryEntry = new StringBuilder();
        String edit;

        //Diary entry input from the user 
        while(true){
            edit = sc.nextLine();
            if (edit.equalsIgnoreCase("exit")) {
                break; //Exit loop if user types 'exit'
            }
            diaryEntry.append(edit).append("\n");//Append each line to diaryEntry
        }
        Diary DEntry = new Diary(dat, diaryEntry.toString());
        D.add(DEntry);
    }
    
    // --------------------------- Delete Entry ---------------------------------------- //
    public void DeleteEntry(){
        int i;
        System.out.print("Enter the date of the Entry to delete: ");
        String delete_date = sc.nextLine();

        for(i=0; i<D.size(); i++){
            if(D.get(i).getDate().equalsIgnoreCase(delete_date)){
                D.remove(i);
                System.out.println("Entry Deleted");
                return;
            }
        }
    }

    // --------------------------- Display Entry ---------------------------------------- //
    public void DisplayEntries(){
        for(Diary DEntry : D){
            System.out.println(DEntry);
        }
    }
}

// ------------------------------------------------------ Journal class -----------------------------------------------------------------------
class Journal{
    private String label;
    private String title;
    private String content;
    private String date;

    Journal(String date, String title, String content, String label){
        this.date = date;
        this.title = title;
        this.label = label;
        this.content = content;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDate(){
        return this.date;
    }

    public String getContent(){
        return this.content;
    }

    public String getLabel(){
        return this.label;
    }

    public void setContent(String content){
        this.content = content; //Update the content field
    }

    @Override
    public String toString(){
        return "Label: " + label + "\n" + "Title: " + title + "\n" + "Content: " + content + "\n" + "Date: " + date + "\n";
    }
}

// ------------------------------------------------------ Journal Manager class ---------------------------------------------------------------
class JournalManager{
    private Vector<Vector<Journal>> NotesUnderLabel; //Multidimensional vector notes under labels
    private Vector<String> labels; //Vector to store label names
    private Scanner sc;
    
    // ----------------- Constructor for Journal ---------------------------------------- //  
    public JournalManager(){
        NotesUnderLabel = new Vector<>(); //Initialize notes under label vector
        labels = new Vector<>(); //Initialize labels vector
        sc = new Scanner(System.in);
    }

    // --------------------------- Create label ------------------------------------------- //
    public void createLabel(){
        System.out.print("Enter the label name: ");
        String labelName = sc.nextLine();
        
        //Create a new vector for the notes under this label
        Vector<Journal> Label = new Vector<>();
        
        //Add the new notes vector to the NotesUnderLabel
        NotesUnderLabel.add(Label);
        
        //Add the label name to the labels vector
        labels.add(labelName);
        
        System.out.println("Label '" + labelName + "' created.");
    }

    // --------------------------- Create note under label ------------------------------------------- //
    public void createNote(){
        System.out.print("Enter the name of the label in which you want to create a note: ");
        String labelName = sc.nextLine();

        int index = labels.indexOf(labelName); //Store the index of the 'label' entered by the user

        if(index == -1){ //Label not found
            System.out.println("Label '" + labelName + "' does not exist.");
            return;
        }

        System.out.print("Enter the date: ");
        String date = sc.nextLine();
        
        System.out.print("Enter the title: ");
        String title = sc.nextLine();

        System.out.println("Enter the content: ");
        StringBuilder noteEntry = new StringBuilder();
        String noteEn;
        while(true){
            noteEn = sc.nextLine();
            if(noteEn.equalsIgnoreCase("exit")){
                break;//exit loop if user enters 'exit'
            }
            noteEntry.append(noteEn).append("\n");
        }

        //Create a new Journal object
        Journal newNote = new Journal(date, title, noteEntry.toString(), labelName);
        
        //Add the note to the corresponding label's vector
        NotesUnderLabel.get(index).add(newNote);
        
        System.out.println("Note added under Label '" + labelName + "'.");
    }

    // --------------------------- Edit note under label ------------------------------------------- //
    public void editNoteContent(){
        System.out.print("Enter the name of the label where your note exists: ");
        String labelName = sc.nextLine();
    
        int index = labels.indexOf(labelName); //Store the index of the 'label' entered by the user
    
        if(index == -1){ //Label not found
            System.out.println("Label '" + labelName + "' does not exist.");
            return;
        }
    
        System.out.print("Enter the title of the note you want to edit: ");
        String titleToEdit = sc.nextLine();
    
        //Retrieve the vector of Journals for this label
        Vector<Journal> Label = NotesUnderLabel.get(index);
        
        //Find the note with the specified title
        Journal noteToEdit = null;
        for(Journal note : Label){
            if (note.getTitle().equalsIgnoreCase(titleToEdit)){
                noteToEdit = note; //Note found
                break;
            }
        }
    
        if(noteToEdit == null){
            System.out.println("Note with title '" + titleToEdit + "' not found under Label '" + labelName + "'.");
            return;
        }
    
        //Display current content
        System.out.println("Current content of the note:");
        System.out.println(noteToEdit.getContent());
    
        //Allow user to append new content
        System.out.println("Enter new content to add (type 'exit' to finish):");
        StringBuilder editedContent = new StringBuilder();
        String edit;
    
        while(true){
            edit = sc.nextLine();
            if(edit.equalsIgnoreCase("exit")){
                break; //Exit loop if user types 'exit'
            }
            editedContent.append(edit).append("\n"); //Append each line to editedContent
        }
    
        //Append new content to existing content
        String updatedContent = noteToEdit.getContent() + editedContent.toString();
        
        //Update the note's content with appended content
        noteToEdit.setContent(updatedContent); 
    
        System.out.println("Note updated successfully under Label '" + labelName + "'.");
    }

    // --------------------------- Display label -------------------------------------------------------- //
    public void displayLabels(){
        for (String label : labels){
            System.out.println(label);
        }
    }

    // --------------------------- Display notes under label ------------------------------------------- //
    public void displayNote(){
        System.out.print("Enter the label you want to view: ");
        String labelToView = sc.nextLine();
        
        //Find the index of the label
        int index = labels.indexOf(labelToView);
        
        if(index == -1){ //Label not found
            System.out.println("Label '" + labelToView + "' does not exist.");
            return;
        }
    
        //Retrieve the vector of Journals for this label
        Vector<Journal> Label = NotesUnderLabel.get(index);
        
        //Check if there are any notes under this label
        if(Label.isEmpty()){
            System.out.println("No notes found under Label '" + labelToView + "'.");
            return;
        }
    
        //Display all notes under this label
        System.out.println("Notes under Label '" + labelToView + "':");
        for (Journal note : Label){//using enhanced for loop to print all notes
            System.out.println(note); //This will use the toString() method of Journal
        }
    }

    // --------------------------- Delete note under label ------------------------------------------- //
    public void deleteNote() {
        System.out.print("Enter the label of the note you want to delete: ");
        String labelName = sc.nextLine();
    
        //Check if the label exists
        int labelIndex = labels.indexOf(labelName);
        if (labelIndex == -1) {
            System.out.println("Label '" + labelName + "' does not exist.");
            return;
        }
    
        //Get notes under the specified label
        Vector<Journal> Label = NotesUnderLabel.get(labelIndex);
    
        System.out.print("Enter the title of the note you want to delete: ");
        String noteToDelete = sc.nextLine();
    
        //Find note and then delete it
        boolean noteFound = false; 
        for (int i = 0; i < Label.size(); i++) {
            if (Label.get(i).getTitle().equalsIgnoreCase(noteToDelete)) {
                Label.remove(i);
                System.out.println("Deleted note: " + noteToDelete);
                noteFound = true; 
                break; 
            }
        }
    
        //If no note was found
        if (!noteFound) {
            System.out.println("Note '" + noteToDelete + "' not found under label '" + labelName + "'.");
        }
    }

    // ----------------------- Delete label (Deletes all notes under that label) ------------------------------ //
    public void DeleteLabel(){
        System.out.print("Enter the label you want to delete: ");
        String labelToDelete = sc.nextLine();
    
        int index = labels.indexOf(labelToDelete);
        if (index == -1) {
            System.out.println("Label '" + labelToDelete + "' does not exist.");
            return;
        }
    
        //remove the notes associated with this label
        NotesUnderLabel.remove(index); //remove vector of notes
        labels.remove(index); //remove label from labels vector
    
        System.out.println("Deleted label: " + labelToDelete);
    }
}

// ------------------------------------------------- Public class -----------------------------------------------------------------------------
public class DigitalDiary{
    public static void main(String[] args){

//--------------------------- Object Creation ----------------------------------- //
        JournalManager J = new JournalManager();
        DiaryManager DE = new DiaryManager();
        Scanner sc = new Scanner(System.in);

//------------------------ Password Authentication ------------------------------ //        
        String originalPass = "Swapnil Sir";
        int p = 1;
        while(p==1){
            System.out.print("Enter password to open your app: ");
            String userPass = sc.nextLine();
            if(userPass.equals(originalPass)){
                System.out.println("Correct Password");
                p=0;
            }
            else{
                System.out.println("Incorrect Password, please try again");
            }
        }

// ------------------------- Application open ---------------------------------- // 
        int exitProgram = 1;  //When to exit the entire program
        while(exitProgram == 1){ 
            String mainchoice;
            System.out.print("Enter your application mode ('D' for Diary, 'J' for Journal, 'E' to Exit): ");
            mainchoice = sc.nextLine();

// ------------------------------------------- Main Program Exit ------------------------------------------------------------------------------
            if(mainchoice.equals("E")){
                System.out.println("Exited app");
                exitProgram = 0;
            }

// ------------------------------------------- Diary Operations -------------------------------------------------------------------------------
            else if(mainchoice.equals("D")){
                int n = 1;
                int ch1;
                //diary mode functionalities
                System.out.println("1. Add entry\n2. Delete entry\n3. Display all entries\n4. Exit Diary mode");
                while(n == 1){
                    System.out.print("Your Operation on Diary: ");
                    ch1 = sc.nextInt();
                    sc.nextLine();  
                    switch (ch1) {
                        case 1://Adding entry
                            DE.AddEntry();
                            System.out.println("Added entry");
                            sc.nextLine();
                            break;
                        case 2://Displaying entries
                            DE.DeleteEntry();
                            System.out.println("Deleted entry");
                            sc.nextLine();
                            break;
                        case 3://Deleting entries
                            DE.DisplayEntries();
                            System.out.println("Displayed all Entries");
                            sc.nextLine();
                            break;
                        case 4://exit
                            n = 0;
                            System.out.println("Exiting Diary mode");
                            sc.nextLine();
                            break;
                        default:
                            System.out.println("Invalid input");
                            sc.nextLine();
                            break;
                    }
                }
            } 

// ------------------------------------------- Journal Operations -----------------------------------------------------------------            
            else if (mainchoice.equals("J")){
                int b = 1;
                int ch2;
                System.out.println("Following operations :");
                System.out.println("1. Create Label\n2. Create note under label\n3. Edit note under specific label\n4. Display labels\n5. Display notes under specific label\n6. Delete note \n7. Delete label\n8. Exit\n");
                while(b == 1){
                    System.out.print("Operation You want to conduct on your Journal: ");
                    ch2 = sc.nextInt();
                    sc.nextLine(); 

                    switch(ch2){
                        case 1://Create label
                            J.createLabel();
                            sc.nextLine();
                            break;
                        case 2://Create note
                            J.createNote();
                            sc.nextLine();
                            break;
                        case 3://Edit note under specific label
                            J.editNoteContent();
                            sc.nextLine();
                            break;
                        case 4://Display labels
                            J.displayLabels();
                            sc.nextLine();
                            break;
                        case 5://Display notes
                            J.displayNote();
                            sc.nextLine();
                            break;
                        case 6://Delete note
                            J.deleteNote();
                            sc.nextLine();
                            break;
                        case 7://Delete label
                            J.DeleteLabel();
                            System.out.println("Deleting labels works");
                            sc.nextLine();
                            break;
                        case 8://Exit
                            b = 0;
                            System.out.println("Exited Journal mode");
                            sc.nextLine();
                            break;
                        default:
                            System.out.println("Invalid input");
                            sc.nextLine();
                            break;
                    }
                }
            }  
            else{
                System.out.println("Invalid mode selected");
            }
        }
        sc.close();
    }
}

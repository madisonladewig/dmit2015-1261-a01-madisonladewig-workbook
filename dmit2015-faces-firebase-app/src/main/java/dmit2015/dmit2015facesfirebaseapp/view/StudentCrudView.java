package dmit2015.dmit2015facesfirebaseapp.view;

import dmit2015.dmit2015facesfirebaseapp.model.Student;
import dmit2015.dmit2015facesfirebaseapp.service.StudentService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.datafaker.Faker;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This Jakarta Faces backing bean class contains the data and event handlers
 * to perform CRUD operations using a PrimeFaces DataTable configured to perform CRUD.
 */

@Named("currentStudentCrudView")
@ViewScoped //create this object for one HTTP request and keep in memory if the next is for the same page
public class StudentCrudView implements Serializable {

    @Inject
    @Named("firebaseMultiTenantHttpClientStudentService")
    private StudentService studentService;

    //The selected Student instance to create, edit, update, or delete:
    @Getter
    @Setter
    private Student selectedStudent;

    //The unique name of the selected Student instanec:
    @Getter
    @Setter
    private String selectedId;

    //The list of Student objects fetched from the data source:
    @Getter
    private List<Student> students;

    /**
     * Fetch all Student from the data source.
     * <p>
     * If FacesContext message sent from init() method annotated with @PostConstruct in the Faces backing bean class are not shown on page:
     * 1) Remove the @PostConstruct annotation from the Faces backing bean class
     * 2) Add metadata tag shown below to the page to execute the init() method
     * <f:metadata>
     * <f:viewParam name="dummy" />
     * <f:event type="postInvokeAction" listener="#{currentBeanView.init}" />
     * </f:metadata>
     */

    @PostConstruct
    public void init() {
        try {
            students = studentService.getAllStudents();
        } catch (Exception e) {
            Messages.addGlobalError("Error getting students %s", e.getMessage());
        }
    }

    //Event handler for the New button on the Faces crud page
    //Create a new selected Student instance to enter data for:
    public void onOpenNew() {
        selectedStudent = new Student();
        selectedId = null;
    }

    //Event handler to generate fake data using DataFaker
    //     * @link <a href="https://www.datafaker.net/documentation/getting-started/">Getting started with DataFaker</a>
    public void onGeneratedData() {
        try {
            var faker = new Faker();
            selectedStudent = Student.of(faker);
            //selectedStudent.sedId(selectedId);
        } catch (Exception e) {
            Messages.addGlobalError("Error generating data {0}", e.getMessage());
        }
    }

    public void onSave() {
        try {
            //If selectedId is null, then we will create new data. Otherwise, update current id
            if (selectedId == null) {
                Student createdStudent = studentService.createStudent(selectedStudent);

                //Send a Faces info message that the creation was successful:
                Messages.addGlobalInfo("Create was successful. {0}", createdStudent.getId());

                selectedStudent = null;
            } else {
                studentService.updateStudent(selectedStudent);

                Messages.addGlobalInfo("Update was successful");
            }

            //Fetch a list of objects from the data source:
            students = studentService.getAllStudents();
            PrimeFaces.current().ajax().update("dialogs:messages", "form:dt-Students");

        } catch (RuntimeException ex) { //handle application generated exceptions
            Messages.addGlobalError(ex.getMessage());
        } catch (Exception ex) { //handle system generated exceptions
            Messages.addGlobalError("Save not successful.");
            handleException(ex);
        }
    }

    //Event handler for Delete to delete the selected data:
    public void onDelete() {
        try {
            //Get the unique name of the JSON object to delete
            selectedId = selectedStudent.getId();
            studentService.deleteStudentById(selectedId);
            Messages.addGlobalInfo("Delete was successful for id of {0}", selectedId);
            students = studentService.getAllStudents();

            PrimeFaces.current().ajax().update("dialog:messages", "form:dt-Students");
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        } catch (Exception ex) {
            Messages.addGlobalError("Delete not successful.");
            handleException(ex);
        }
    }

    //This method is used to handle exceptions and display root cause to user:
    //param ex, the exception to handle
    protected void handleException(Exception ex) {
        StringBuilder details = new StringBuilder();
        Throwable causes = ex;

        while (causes.getCause() != null) {
            details.append(ex.getMessage());
            details.append("    Caused By:");
            details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
    }
}

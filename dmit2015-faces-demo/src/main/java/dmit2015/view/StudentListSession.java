package dmit2015.view;

import dmit2015.model.StudentInfo;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped

public class StudentListSession implements Serializable {

    private List<StudentInfo> studentInfos = new ArrayList<>();     //getter

    public void addStudentInfo(StudentInfo newStudentInfo) {studentInfos.add(newStudentInfo);}

    public void removeStudentInfo(StudentInfo selectedStudentInfo) {studentInfos.remove(selectedStudentInfo);}

    public List<StudentInfo> getStudentInfos() {return studentInfos;}
}

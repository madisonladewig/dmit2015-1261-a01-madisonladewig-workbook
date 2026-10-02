package dmit2015.dmit2015facesfirebaseapp.service;

import dmit2015.dmit2015facesfirebaseapp.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudentById(String id);

    List<Student> getAllStudents();

    Student updateStudent(Student student);

    void deleteStudentById(String id);
}
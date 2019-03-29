package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentsController {

    @RequestMapping(value = "/Students")
    public String listSchoolClass(Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }
        model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
        return "studentsList";
    }

    @RequestMapping(value = "/AddStudent")
    public String displayAddStudentForm(Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }

        model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
        return "studentForm";
    }
    
    @RequestMapping(value = "/CreateStudentClass", method = RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "pesel", required = false) String pesel,
            @RequestParam(value = "schoolClass", required = false) String schoolClasslId,
            Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }

        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setPesel(pesel);

        DatabaseConnector.getInstance().addStudents(student, schoolClasslId);
        model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
        model.addAttribute("message", "Dodano nowego gościa");

        return "studentsList";
    }

    @RequestMapping(value = "/EditStudent")
    public String displayAddStudentForm(@RequestParam(value = "studentID", required = false) String studentID, Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }
        model.addAttribute("schoolClass", DatabaseConnector.getInstance().getSchoolClasses());
        model.addAttribute("student", DatabaseConnector.getInstance().getStudentByID(studentID));
        model.addAttribute("studentClass", DatabaseConnector.getInstance().getStudentClassByID(studentID));
        
        return "studentFormEdit";
    }

    @RequestMapping(value = "/ConfirmEditStudent", method = RequestMethod.POST)
    public String createSchoolClass(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "studentID", required = false) String studentID,
            @RequestParam(value = "pesel", required = false) String pesel,
            @RequestParam(value = "schoolClass", required = false) String schoolClasslId,
            Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }

        Student student = DatabaseConnector.getInstance().getStudentByID(studentID);
        student.setName(name);
        student.setSurname(surname);
        student.setPesel(pesel);

        DatabaseConnector.getInstance().editStudents(studentID);
        model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
        model.addAttribute("message", "Dodano nowego ucznia");

        return "studentsList";
    }

    @RequestMapping(value="/DeleteStudent", method=RequestMethod.POST)
    public String deleteStudent(@RequestParam(value="studentId", required=false) String studentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteStudent(studentId);    	
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Uczeń został usuniety");
         	
    	return "studentsList";
    }    

}

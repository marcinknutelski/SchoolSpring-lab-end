package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

//klasa obsługuje zapytania skierowane na webowy interfejs naszej aplikacji
@Controller
public class SchoolsController {

    @RequestMapping(value="/Schools")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	
        return "schoolsList";    
    }
    
    @RequestMapping(value="/AddSchool")
    public String displayAddSchoolForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "schoolForm";    
    }

    @RequestMapping(value="/CreateSchool", method=RequestMethod.POST)
    public String createSchool(@RequestParam(value="schoolName", required=false) String name,
    		@RequestParam(value="schoolAddress", required=false) String address,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	School school = new School();
    	school.setName(name);
    	school.setAddress(address);
    	
    	DatabaseConnector.getInstance().addSchool(school);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Nowa szkoła została dodana");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/DeleteSchool", method=RequestMethod.POST)
    public String deleteSchool(@RequestParam(value="schoolId", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchool(schoolId);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Szkoła została usunięta");
         	
    	return "schoolsList";
    }


    @RequestMapping(value = "/EditSchool")
    public String displayEditSchoolForm(@RequestParam(value = "schoolID", required = false) String schoolID, Model model, HttpSession session) {
        if (session.getAttribute("userLogin") == null) {
            return "redirect:/Login";
        }
        model.addAttribute("school", DatabaseConnector.getInstance().getSchoolByID(schoolID));
        return "schoolFormEdit";
    }

	@RequestMapping(value = "/ConfirmEditSchool", method = RequestMethod.POST)
	public String editSchool(
			@RequestParam(value = "schoolName", required = false) String name,
			@RequestParam(value = "schoolAddress", required = false) String address,
			@RequestParam(value = "schoolID", required = false) String schoolID,
		Model model, HttpSession session) {

		if (session.getAttribute("userLogin") == null) {
			return "redirect:/Login";
		}

		School school = DatabaseConnector.getInstance().getSchoolByID(schoolID);
		school.setName(name);
		school.setAddress(address);
		
		DatabaseConnector.getInstance().editSchool(schoolID);
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
		model.addAttribute("message", "Zedytowano szkołę");

		return "schoolsList";

	}


}
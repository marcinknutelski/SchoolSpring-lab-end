package pl.edu.agh.ki.mwo.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;

//realizuje wzorzec Singleton, służy jako pośrednik w dostępie do danych, jej instancja utrzymuje połączenie z bazą danych
public class DatabaseConnector {

    protected static DatabaseConnector instance = null;

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    Session session;

    protected DatabaseConnector() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void teardown() {
        session.close();
        HibernateUtil.shutdown();
        instance = null;
    }

    public Iterable<School> getSchools() {
        String hql = "FROM School";
        Query query = session.createQuery(hql);
        List schools = query.list();

        return schools;
    }

    public void addSchool(School school) {
        Transaction transaction = session.beginTransaction();
        session.save(school);
        transaction.commit();
    }

    public void deleteSchool(String schoolId) {
        String hql = "FROM School S WHERE S.id=" + schoolId;
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (School s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    public void editSchool(String schoolId) {
        String hql = "FROM School S WHERE S.id=" + schoolId;
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (School s : results) {
            session.update(s);
        }
        transaction.commit();
    }

    public School getSchoolByID(String schoolId) {
        String hql = "FROM School S WHERE S.id=" + schoolId;
        Query query = session.createQuery(hql);
        School school = (School) query.uniqueResult();
        return school;
    }
    




    public Iterable<SchoolClass> getSchoolClasses() {
        String hql = "FROM SchoolClass";
        Query query = session.createQuery(hql);
        List schoolClasses = query.list();

        return schoolClasses;
    }

    public void addSchoolClass(SchoolClass schoolClass, String schoolId) {
        String hql = "FROM School S WHERE S.id=" + schoolId;
        Query query = session.createQuery(hql);
        List<School> results = query.list();
        Transaction transaction = session.beginTransaction();
        if (results.size() == 0) {
            session.save(schoolClass);
        } else {
            School school = results.get(0);
            school.addClass(schoolClass);
            session.save(school);
        }
        transaction.commit();
    }

    public void deleteSchoolClass(String schoolClassId) {
        String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
        Query query = session.createQuery(hql);
        List<SchoolClass> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (SchoolClass s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

    public SchoolClass getSchoolClassByID(String schoolClassId) {
        String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassId;
        Query query = session.createQuery(hql);
        SchoolClass schoolClass = (SchoolClass) query.uniqueResult();
        return schoolClass;
    }


    public void editSchoolClass(String schoolClassID) {
        String hql = "FROM SchoolClass S WHERE S.id=" + schoolClassID;
        Query query = session.createQuery(hql);
        List<SchoolClass> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (SchoolClass s : results) {
            session.update(s);
        }
        transaction.commit();
    }

    // ---------------------------------------------------
    // -----------------  STUDENTS  ----------------------
    // ---------------------------------------------------
    public Iterable<Student> getStudents() {
        String hql = "FROM Student";
        Query query = session.createQuery(hql);
        List students = query.list();

        return students;
    }
    
    public Student getStudentByID(String id) {
        String hql = "FROM Student S WHERE S.id=" + id;
        Query query = session.createQuery(hql);
        Student student = (Student) query.uniqueResult();
        return student;
    }

    public SchoolClass getStudentClassByID(String id) {
        String hql = "FROM SchoolClass S WHERE S.id=" + id;
        Query query = session.createQuery(hql);
        SchoolClass student = (SchoolClass) query.uniqueResult();
        return student;
    }
        
    public void addStudents(Student student, String schoolId) {
        String hql = "FROM SchoolClass S WHERE S.id=" + schoolId;
        Query query = session.createQuery(hql);
        List<SchoolClass> results = query.list();
        Transaction transaction = session.beginTransaction();
        if (results.size() == 0) {
            session.save(student);
        } else {
            SchoolClass schoolClass = results.get(0);
            schoolClass.addStudent(student);
            session.save(schoolClass);
        }
        transaction.commit();
    }


    public void editStudents(String studentID) {
        String hql = "FROM Student S WHERE S.id=" + studentID;
        Query query = session.createQuery(hql);
        List<Student> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (Student s : results) {
            session.update(s);
        }
        transaction.commit();
    }


    public void deleteStudent(String studentId) {
        String hql = "FROM Student S WHERE S.id=" + studentId;
        Query query = session.createQuery(hql);
        List<Student> results = query.list();
        Transaction transaction = session.beginTransaction();
        for (Student s : results) {
            session.delete(s);
        }
        transaction.commit();
    }

}

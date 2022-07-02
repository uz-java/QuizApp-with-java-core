package org.example.dao.student;


import org.example.dao.GenericDAO;
import org.example.domains.users.StudentEntity;


public class StudentDAO extends GenericDAO<StudentEntity, Long> {

    private static StudentDAO instance;

    public static StudentDAO getInstance() {
        if (instance == null) {
            instance = new StudentDAO();
        }
        return instance;
    }

}
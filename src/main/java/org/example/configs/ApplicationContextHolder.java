package org.example.configs;


import org.example.dao.auth.AuthUserDAO;
import org.example.dao.question.QuestionDAO;
import org.example.dao.student.StudentDAO;
import org.example.dao.subject.SubjectDAO;
import org.example.dao.teacher.TeacherDAO;
import org.example.dao.variant.VariantDAO;
import org.example.service.*;
import org.example.service.auth.AuthUserService;
import org.example.utils.BaseUtils;
import org.example.utils.validators.authUser.AuthUserValidator;
import org.example.utils.validators.question.QuestionValidator;
import org.example.utils.validators.student.StudentValidator;
import org.example.utils.validators.subject.SubjectValidator;
import org.example.utils.validators.teacher.TeacherValidator;
import org.example.utils.validators.variant.VariantValidator;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return switch (beanName) {
            case "BaseUtils" -> (T) BaseUtils.getInstance();
            case "AuthUserService" -> (T) AuthUserService.getInstance();
            case "StudentService" -> (T) StudentService.getInstance();
            case "SubjectService" -> (T) SubjectService.getInstance();
            case "VariantService" -> (T) VariantService.getInstance();
            case "TeacherService" -> (T) TeacherService.getInstance();
            case "QuestionService" -> (T) QuestionService.getInstance();
            case "AuthUserValidator" -> (T) AuthUserValidator.getInstance();
            case "StudentValidator" -> (T) StudentValidator.getInstance();
            case "VariantValidator" -> (T) VariantValidator.getInstance();
            case "SubjectValidator" -> (T) SubjectValidator.getInstance();
            case "QuestionValidator" -> (T) QuestionValidator.getInstance();
            case "TeacherValidator" -> (T) TeacherValidator.getInstance();
            case "QuestionDAO" -> (T) QuestionDAO.getInstance();
            case "AuthUserDAO" -> (T) AuthUserDAO.getInstance();
            case "SubjectDAO" -> (T) SubjectDAO.getInstance();
            case "VariantDAO" -> (T) VariantDAO.getInstance();
            case "StudentDAO" -> (T) StudentDAO.getInstance();
            case "TeacherDAO" -> (T) TeacherDAO.getInstance();
            default -> throw new RuntimeException("Bean Not Found");
        };
    }

    public static <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);
    }

}

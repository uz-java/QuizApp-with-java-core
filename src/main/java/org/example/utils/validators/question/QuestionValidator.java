package org.example.utils.validators.question;

import org.example.configs.ApplicationContextHolder;
import org.example.dao.question.QuestionDAO;
import org.example.exceptions.ValidationException;
import org.example.utils.validators.GenericValidator;
import org.example.vo.question.QuestionCreateVO;
import org.example.vo.question.QuestionUpdateVO;

import java.util.Objects;

public class QuestionValidator extends GenericValidator<QuestionCreateVO, QuestionUpdateVO, Long> {

    private static QuestionValidator instance;

    public static QuestionValidator getInstance() {
        if (instance == null) {
            instance = new QuestionValidator();
        }
        return instance;

    }


    QuestionDAO questionDAO = ApplicationContextHolder.getBean(QuestionDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    @Override
    public void validOnCreate(QuestionCreateVO vo) throws ValidationException {
        if (Objects.isNull(vo.getBody()) ) {
            throw new ValidationException("Question body can not be null");
        }
        if(Objects.isNull(vo.getAnswers()) ){
            throw new ValidationException("Answer list can not be null");
        }
        if(Objects.isNull(vo.getSubjectName())){
            throw new ValidationException("Subject name can not be null");
        }

    }


    @Override
    public void validOnUpdate(QuestionUpdateVO vo) throws ValidationException {

    }
}

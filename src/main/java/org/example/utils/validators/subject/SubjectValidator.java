package org.example.utils.validators.subject;

import org.example.configs.ApplicationContextHolder;
import org.example.dao.subject.SubjectDAO;
import org.example.exceptions.ValidationException;
import org.example.utils.validators.GenericValidator;
import org.example.vo.subject.SubjectCreateVO;
import org.example.vo.subject.SubjectUpdateVO;

import java.util.Objects;

public class SubjectValidator extends GenericValidator<SubjectCreateVO, SubjectUpdateVO, Long> {


    private static SubjectValidator instance;


    SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);

    public static SubjectValidator getInstance() {
        if (instance == null) {
            instance = new SubjectValidator();
        }
        return instance;

    }

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    @Override
    public void validOnCreate(SubjectCreateVO vo) throws ValidationException {
        existsByTitle(vo.getTitle());
    }

    private void existsByTitle(String title) {
        if (Objects.nonNull(title)) {
            if (title.isBlank()) {
                throw new ValidationException("The subject cannot be empty ");
            } else if (Objects.nonNull(subjectDAO.findByName(title)))
                throw new ValidationException("Subject already exists ");
        }
    }

    @Override
    public void validOnUpdate(SubjectUpdateVO vo) throws ValidationException {

    }
}

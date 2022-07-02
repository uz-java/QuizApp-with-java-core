package org.example.utils.validators.variant;

import org.example.configs.ApplicationContextHolder;
import org.example.dao.auth.AuthUserDAO;
import org.example.dao.subject.SubjectDAO;
import org.example.dao.variant.VariantDAO;
import org.example.enums.QuestionStatus;
import org.example.exceptions.ValidationException;
import org.example.utils.validators.GenericValidator;
import org.example.vo.variant.VariantCreateVO;
import org.example.vo.variant.VariantUpdateVO;

import java.util.Arrays;
import java.util.Objects;

public class VariantValidator extends GenericValidator<VariantCreateVO, VariantUpdateVO, Long> {
    private static VariantValidator instance;
    VariantDAO variantDAO = ApplicationContextHolder.getBean(VariantDAO.class);
    AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);

    @Override
    public void validateKey(Long id) throws ValidationException {
        existsById(id);
    }

    @Override
    public void validOnCreate(VariantCreateVO vo) throws ValidationException {
        if (vo.getUserId() == null || authUserDAO.findById(vo.getUserId()) == null)
            throw new ValidationException("User not found");
        if (vo.getLevel() == null || !Arrays.asList(QuestionStatus.values()).contains(vo.getLevel()))
            throw new ValidationException("Level not found");
        if (vo.getNumberOfQuestions() == null || vo.getNumberOfQuestions() < 0 || vo.getNumberOfQuestions() > 100)
            throw new ValidationException("Invalid number of questions");
        if (vo.getSubjectName() == null || subjectDAO.findByName(vo.getSubjectName()) == null)
            throw new ValidationException("Invalid subject name");


    }

    @Override
    public void validOnUpdate(VariantUpdateVO vo) throws ValidationException {

    }

    private void existsById(Long id) {
        if (Objects.isNull(id) || Objects.isNull(variantDAO.findById(id)))
            throw new ValidationException("Variant not found by id");
    }

    public static VariantValidator getInstance() {
        if (instance == null) {
            instance = new VariantValidator();
        }
        return instance;
    }
}

package org.example.utils.validators.teacher;

import org.example.exceptions.ValidationException;
import org.example.utils.validators.GenericValidator;
import org.example.vo.teacher.TeacherCreateVO;
import org.example.vo.teacher.TeacherUpdateVO;

import java.util.Objects;

public class TeacherValidator extends GenericValidator<TeacherCreateVO, TeacherUpdateVO, Long> {

    private static TeacherValidator instance;

    public static TeacherValidator getInstance() {
        if (instance == null) {
            instance = new TeacherValidator();
        }
        return instance;

    }

    @Override
    public void validateKey(Long id) throws ValidationException {

    }

    public void validOnCreate(TeacherCreateVO vo) {
        existsByName(vo.getName());
        existsBySurname(vo.getSurname());

    }

    private void existsBySurname(String surname) {
        if (Objects.nonNull(surname)) {
            if (surname.isBlank())
                throw new ValidationException("The surname cannot be empty ");
        } else
            throw new ValidationException("The surname cannot be null ");


    }

    private void existsByName(String name) {
        if (Objects.nonNull(name)) {
            if (name.isBlank()) {
                throw new ValidationException("The name cannot be empty ");

            }
        } else throw new ValidationException("The name cannot be null");

    }

    @Override
    public void validOnUpdate(TeacherUpdateVO vo) throws ValidationException {

    }
}

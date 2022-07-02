package org.example.utils.validators;

import org.example.exceptions.ValidationException;
import org.example.vo.BaseVO;
import org.example.vo.GenericVO;

import java.io.Serializable;


public abstract class GenericValidator<CreateVO extends BaseVO, UpdateVO extends GenericVO, K extends Serializable> implements BaseValidator {

    public abstract void validateKey(K id) throws ValidationException;

    public abstract void validOnCreate(CreateVO vo) throws ValidationException;

    public abstract void validOnUpdate(UpdateVO vo) throws ValidationException;
}

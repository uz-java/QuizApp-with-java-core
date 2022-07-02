package org.example.service;

import lombok.NonNull;
import org.example.configs.ApplicationContextHolder;
import org.example.dao.AbstractDAO;
import org.example.dao.subject.SubjectDAO;
import org.example.domains.subject.SubjectEntity;
import org.example.exceptions.ValidationException;
import org.example.utils.BaseUtils;
import org.example.utils.validators.subject.SubjectValidator;
import org.example.vo.http.AppErrorVO;
import org.example.vo.http.DataVO;
import org.example.vo.http.Response;
import org.example.vo.subject.SubjectCreateVO;
import org.example.vo.subject.SubjectUpdateVO;
import org.example.vo.subject.SubjectVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectService extends AbstractDAO<SubjectDAO> implements GenericCRUDService<
        SubjectVO,
        SubjectCreateVO,
        SubjectUpdateVO,
        Long> {
    private final SubjectValidator validator = ApplicationContextHolder.getBean(SubjectValidator.class);
    private static SubjectService instance;

    private SubjectService() {
        super(
                ApplicationContextHolder.getBean(SubjectDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }


    @Override
    public Response<DataVO<Long>> create(@NonNull SubjectCreateVO vo) {

        try {
            validator.validOnCreate(vo);
            SubjectEntity question = SubjectEntity.childBuilder()
                    .title(vo.getTitle())
                    .createdBy(vo.getCreatedBy())
                    .build();
            SubjectEntity save = dao.save(question);

            return new Response<>(new DataVO<>(save.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }
    }


    @Override
    public Response<DataVO<Void>> update(@NonNull SubjectUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<SubjectVO>> get(@NonNull Long aLong) {
        return null;
    }

    public Response<DataVO<SubjectVO>> get(@NonNull String subjectName) {
        SubjectEntity subjectEntity = dao.findByName(subjectName);
        if (Objects.isNull(subjectEntity)) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No subject found").build()), 500);
        }
        SubjectVO subjectVO = SubjectVO.childBuilder()
                .id(subjectEntity.getId())
                .title(subjectEntity.getTitle())
                .build();


        return new Response<>(new DataVO<>(subjectVO), 200);
    }

    @Override
    public Response<DataVO<List<SubjectVO>>> getAll() {
        List<SubjectEntity> all = dao.findAll();
        if (Objects.isNull(all) || all.isEmpty())
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No subjects found")
                    .build()), 404);

        List<SubjectVO> result = new ArrayList<>();
        for (SubjectEntity subjectEntity : all) {
            SubjectVO subjectVO = SubjectVO.childBuilder()
                    .title(subjectEntity.getTitle())
                    .id(subjectEntity.getId())
                    .build();
            result.add(subjectVO);
        }

        return new Response<>(new DataVO<>(result), 200);
    }
}

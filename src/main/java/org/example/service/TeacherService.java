package org.example.service;

import lombok.NonNull;
import org.example.configs.ApplicationContextHolder;
import org.example.dao.AbstractDAO;
import org.example.dao.auth.AuthUserDAO;
import org.example.dao.subject.SubjectDAO;
import org.example.dao.teacher.TeacherDAO;
import org.example.domains.auth.AuthUser;
import org.example.domains.subject.SubjectEntity;
import org.example.domains.users.TeacherEntity;
import org.example.exceptions.ValidationException;
import org.example.utils.BaseUtils;
import org.example.utils.validators.teacher.TeacherValidator;
import org.example.vo.auth.Session;
import org.example.vo.http.AppErrorVO;
import org.example.vo.http.DataVO;
import org.example.vo.http.Response;
import org.example.vo.subject.SubjectVO;
import org.example.vo.teacher.TeacherCreateVO;
import org.example.vo.teacher.TeacherUpdateVO;
import org.example.vo.teacher.TeacherVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherService extends AbstractDAO<TeacherDAO> implements GenericCRUDService<
        TeacherVO,
        TeacherCreateVO,
        TeacherUpdateVO,
        Long> {

    private final TeacherValidator teacherValidator = ApplicationContextHolder.getBean(TeacherValidator.class);
    private static final AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    private static final SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);
    private static TeacherService instance;

    public static TeacherService getInstance() {
        if (instance == null) {
            instance = new TeacherService();
        }
        return instance;
    }

    public TeacherService() {
        super(
                ApplicationContextHolder.getBean(TeacherDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull TeacherCreateVO vo) {
        try {

            teacherValidator.validOnCreate(vo);

            AuthUser authUser = authUserDAO.findById(Session.sessionUser.getId());
            TeacherEntity teacher = TeacherEntity
                    .builder()
                    .name(vo.getName())
                    .surname(vo.getSurname())
                    .user(authUser)
                    .build();

            TeacherEntity save = dao.save(teacher);


            return new Response<>(new DataVO<>(save.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }

    }

    @Override
    public Response<DataVO<Void>> update(@NonNull TeacherUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<TeacherVO>> get(@NonNull Long userId) {
        try {
            TeacherEntity teacherEntity = dao.findByUserId(userId);
            List<SubjectEntity> subjectList = teacherEntity.getSubjectList();

            List<SubjectVO> subjectVOList = new ArrayList<>();
            for (SubjectEntity subject : subjectList) {
                SubjectVO build = SubjectVO.childBuilder()
                        .title(subject.getTitle())
                        .build();
                subjectVOList.add(build);
            }
            TeacherVO teacherVO = TeacherVO.builder()
                    .subjectList(subjectVOList).build();

            return new Response<>(new DataVO<>(teacherVO), 200);
        } catch (Exception e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);
        }
    }

    @Override
    public Response<DataVO<List<TeacherVO>>> getAll() {
        return null;
    }

    public Response<DataVO<Void>> addSubjectList(TeacherVO teacherVO) {
        try {

            TeacherEntity teacherEntity = dao.findByUserId(teacherVO.getId());

            List<SubjectEntity> teacherSubjectList = teacherEntity.getSubjectList();
            for (SubjectVO subjectVO : teacherVO.getSubjectList()) {
                SubjectEntity subjectEntity = subjectDAO.findById(subjectVO.getId());

                if (Objects.isNull(teacherSubjectList)) {
                    teacherEntity.setSubjectList(new ArrayList<>());
                    break;
                }
                if (!teacherSubjectList.contains(subjectEntity)) {
                    teacherEntity.getSubjectList().add(subjectEntity);
                }
            }
            dao.update(teacherEntity);

            return new Response<>(new DataVO<>(null), 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);

        }
    }

    public Response<DataVO<List<SubjectEntity>>> getSubjectList(Long userId) {
        try {
            TeacherEntity teacherEntity = dao.findByUserId(userId);
            if (Objects.isNull(teacherEntity) || Objects.isNull(teacherEntity.getSubjectList()))
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("You do not have any subjects")
                        .build()), 500);

            return new Response<>(new DataVO<>(teacherEntity.getSubjectList()), 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);
        }
    }
}

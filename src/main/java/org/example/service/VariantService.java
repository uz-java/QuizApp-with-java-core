package org.example.service;

import lombok.NonNull;
import org.example.configs.ApplicationContextHolder;
import org.example.dao.AbstractDAO;
import org.example.dao.auth.AuthUserDAO;
import org.example.dao.question.QuestionDAO;
import org.example.dao.subject.SubjectDAO;
import org.example.dao.variant.VariantDAO;
import org.example.domains.auth.AuthUser;
import org.example.domains.question.AnswerEntity;
import org.example.domains.question.QuestionEntity;
import org.example.domains.question.VariantEntity;
import org.example.domains.subject.SubjectEntity;
import org.example.utils.BaseUtils;
import org.example.utils.validators.variant.VariantValidator;
import org.example.vo.answer.AnswerVO;
import org.example.vo.http.AppErrorVO;
import org.example.vo.http.DataVO;
import org.example.vo.http.Response;
import org.example.vo.question.QuestionVO;
import org.example.vo.subject.SubjectVO;
import org.example.vo.variant.VariantCreateVO;
import org.example.vo.variant.VariantUpdateVO;
import org.example.vo.variant.VariantVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VariantService extends AbstractDAO<VariantDAO> implements GenericCRUDService<
        VariantVO,
        VariantCreateVO,
        VariantUpdateVO,
        Long> {
    private static VariantService instance;
    VariantValidator validator = ApplicationContextHolder.getBean(VariantValidator.class);
    private static AuthUserDAO authUserDAO = ApplicationContextHolder.getBean(AuthUserDAO.class);
    private static QuestionDAO questionDAO = ApplicationContextHolder.getBean(QuestionDAO.class);
    private static SubjectDAO subjectDAO = ApplicationContextHolder.getBean(SubjectDAO.class);

    private VariantService() {
        super(
                ApplicationContextHolder.getBean(VariantDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class));
    }

    public static VariantService getInstance() {
        if (instance == null) {
            instance = new VariantService();
        }
        return instance;
    }

    @NonNull
    public Response<DataVO<Long>> create(@NonNull VariantCreateVO vo) {
        return null;
    }

    public Response<DataVO<VariantEntity>> createAndGet(@NonNull VariantCreateVO vo) {
        try {
            validator.validOnCreate(vo);
            SubjectEntity subjectEntity = subjectDAO.findByName(vo.getSubjectName());
            AuthUser authUser = authUserDAO.findById(vo.getUserId());

            List<QuestionEntity> questionEntitiesList = questionDAO.findAllBySubjectIdAndLevel(subjectEntity.getId(), vo.getLevel(), vo.getNumberOfQuestions());
            VariantEntity variantEntity = VariantEntity.childBuilder()
                    .questions(questionEntitiesList)
                    .user(authUser)
                    .numberOfRightAnswers(0)
                    .numberOfQuestions(vo.getNumberOfQuestions())
                    .build();

            VariantEntity save = dao.save(variantEntity);


            return new Response<>(new DataVO<>(save), 200);
        } catch (Exception e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }
    }


    @Override
    public Response<DataVO<Void>> update(@NonNull VariantUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long aLong) {
        return null;
    }

    @Override
    public Response<DataVO<VariantVO>> get(@NonNull Long variantId) {
        VariantEntity variantEntity = dao.findById(variantId);
        if (Objects.isNull(variantEntity))
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Variant not found by id")
                    .build()), 400);

        VariantVO variantVO = map(variantEntity);

        return new Response<>(new DataVO<>(variantVO), 200);
    }

    @Override
    public Response<DataVO<List<VariantVO>>> getAll() {
        return null;
    }

    public void UpdateVariantEntity(VariantEntity variant) {
        dao.update(variant);
    }

    public Response<DataVO<List<VariantVO>>> getAllByStudentId(Long studentId) {
        List<VariantEntity> all = dao.findByStudentId(studentId);

        if (all.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No variants found").build()), 404);
        }

        List<VariantVO> response = new ArrayList<>();
        for (VariantEntity variantEntity : all) {
            VariantVO variantVO = map(variantEntity);
            response.add(variantVO);
        }
        return new Response<>(new DataVO<>(response), 200);
    }

    private static VariantVO map(VariantEntity variantEntity) {
        VariantVO variantVO = VariantVO.childBuilder()
                .id(variantEntity.getId())
                .createdAt(variantEntity.getCreatedAt())
                .status(variantEntity.getStatus())
                .numberOfRightAnswers(variantEntity.getNumberOfRightAnswers()).build();

        List<QuestionVO> questionVOList = new ArrayList<>();

        for (QuestionEntity questionEntity : variantEntity.getQuestions()) {
            SubjectEntity subject = questionEntity.getSubject();

            SubjectVO subjectVO = SubjectVO.childBuilder()
                    .id(subject.getId())
                    .title(subject.getTitle())
                    .build();

            QuestionVO questionVO = QuestionVO.childBuilder()
                    .id(questionEntity.getId())
                    .body(questionEntity.getBody())
                    .status(questionEntity.getStatus())
                    .subject(subjectVO)
                    .build();

            List<AnswerVO> answerVOList = new ArrayList<>();
            for (AnswerEntity answer : questionEntity.getAnswers()) {
                AnswerVO answerVO = AnswerVO.childBuilder()
                        .body(answer.getBody())
                        .id(answer.getId())
                        .status(answer.getStatus())
                        .build();
                answerVOList.add(answerVO);
            }
            questionVO.setAnswers(answerVOList);
            questionVOList.add(questionVO);
        }
        variantVO.setQuestions(questionVOList);
        return variantVO;
    }
}

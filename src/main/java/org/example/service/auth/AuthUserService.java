package org.example.service.auth;

import lombok.NonNull;
import org.example.configs.ApplicationContextHolder;
import org.example.dao.AbstractDAO;
import org.example.dao.auth.AuthUserDAO;
import org.example.domains.auth.AuthUser;
import org.example.enums.AuthRole;
import org.example.exceptions.ValidationException;
import org.example.service.GenericCRUDService;
import org.example.utils.BaseUtils;
import org.example.utils.validators.authUser.AuthUserValidator;
import org.example.vo.auth.*;
import org.example.vo.http.AppErrorVO;
import org.example.vo.http.DataVO;
import org.example.vo.http.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthUserService extends AbstractDAO<AuthUserDAO> implements GenericCRUDService<
        AuthUserVO,
        AuthUserCreateVO,
        AuthUserUpdateVO,
        Long> {

    private static AuthUserService instance;
    private final AuthUserValidator validator = ApplicationContextHolder.getBean(AuthUserValidator.class);

    private AuthUserService() {
        super(
                ApplicationContextHolder.getBean(AuthUserDAO.class),
                ApplicationContextHolder.getBean(BaseUtils.class)
        );
    }

    @Override
    public Response<DataVO<Long>> create(@NonNull AuthUserCreateVO vo) {
        try {
            validator.validOnCreate(vo);

            AuthUser authUser = AuthUser
                    .childBuilder()
                    .username(vo.getUsername())
                    .password(utils.encode(vo.getPassword()))
                    .email(vo.getEmail())
                    .build();
            System.out.println(authUser.getRole().name());
            AuthUser save = dao.save(authUser);
            Session.setSessionUser(AuthUserVO.childBuilder()
                    .id(save.getId())
                    .role(save.getRole())
                    .username(save.getUsername())
                    .createdAt(save.getCreatedAt().toLocalDateTime())
                    .build());
            return new Response<>(new DataVO<>(authUser.getId()), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }

    }

    @Override
    public Response<DataVO<Void>> update(@NonNull AuthUserUpdateVO vo) {
        return null;
    }

    @Override
    public Response<DataVO<Void>> delete(@NonNull Long id) {
        AuthUser authUser = dao.findById(id);

        if (authUser == null)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("user not find by id")
                    .build()), 404);
        authUser.setDeleted(true);

        dao.update(authUser);
        return new Response<>(new DataVO<>(null), 200);
    }

    @Override
    public Response<DataVO<AuthUserVO>> get(@NonNull Long id) {

        try {
            validator.validateKey(id);
            AuthUser authUser = dao.findById(id);
            AuthUserVO authUserVO = AuthUserVO.childBuilder()
                    .id(id)
                    .role(authUser.getRole())
                    .username(authUser.getUsername())
                    .email(authUser.getEmail())
                    .createdAt(authUser.getCreatedAt().toLocalDateTime())
                    .build();

            return new Response<>(new DataVO<>(authUserVO), 200);
        } catch (ValidationException e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage(e.getMessage())
                    .build()), 400);
        }
    }

    @Override
    public Response<DataVO<List<AuthUserVO>>> getAll() {
        return null;
    }

    public Response<DataVO<List<AuthUserVO>>> getAll(AuthRole role) {
        List<AuthUserVO> response = new ArrayList<>();
        List<AuthUser> resultList = dao.findAll(role);
        if (resultList.isEmpty()) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("No user find with role '%s'".formatted(role.name()))
                    .build()), 500);
        }
        for (AuthUser authUser : resultList) {
            AuthUserVO authUserVO = AuthUserVO.childBuilder()
                    .id(authUser.getId())
                    .email(authUser.getEmail())
                    .username(authUser.getUsername())
                    .role(authUser.getRole())
                    .email(authUser.getEmail())
                    .createdAt(authUser.getCreatedAt().toLocalDateTime())
                    .build();

            response.add(authUserVO);
        }
        return new Response<>(new DataVO<>(response), 200);
    }

    public Response<DataVO<AuthUserVO>> login(String username, String password) {
        Optional<AuthUser> userByUsername = dao.findByUserName(username);
        if (userByUsername.isEmpty() || userByUsername.get().getDeleted())
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("user not found")
                    .build()), 404);

        AuthUser authUser = userByUsername.get();

        boolean hasPasswordMatched = utils.matchPassword(password, authUser.getPassword());
        if (!hasPasswordMatched)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Bad credentials")
                    .build()), 400);

        AuthUserVO authUserVO = AuthUserVO.childBuilder()
                .username(authUser.getUsername())
                .role(authUser.getRole())
                .email(authUser.getEmail())
                .id(authUser.getId())
                .createdAt(authUser.getCreatedAt().toLocalDateTime())
                .build();

        Session.setSessionUser(authUserVO);
        return new Response<>(new DataVO<>(authUserVO), 200);
    }

    public Response<DataVO<Void>> setRole(Long user_id, AuthRole option) {
        try {
            Optional<AuthUser> findById = Optional.ofNullable(dao.findById(user_id));
            if (findById.isEmpty() || findById.get().getDeleted())
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("user not found")
                        .build()), 500);
            AuthUser authUser = findById.get();

            if (authUser.getRole().equals(AuthRole.ADMIN))
                return new Response<>(new DataVO<>(AppErrorVO.builder()
                        .friendlyMessage("You can not change admins' role")
                        .build()), 400);

            authUser.setRole(option);

            dao.update(authUser);
            return new Response<>(new DataVO<>(null), 200);
        } catch (Exception e) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Oops something went wrong")
                    .build()), 400);
        }
    }

    public Response<DataVO<Void>> changeUsername(String newUsername) {

        Optional<AuthUser> usernameCheck = dao.findByUserName(newUsername);
        if (usernameCheck.isPresent() && !usernameCheck.get().getDeleted())
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("username %s already taken".formatted(newUsername))
                    .build()), 400);

        AuthUser authUser = dao.findById(Session.sessionUser.getId());
        authUser.setUsername(newUsername);
        dao.update(authUser);
        return new Response<>(new DataVO<>(null), 200);
    }

    public Response<DataVO<Void>> changePassword(AuthUserPasswordResetVO resetVO) {
        AuthUser authUser = dao.findById(Session.sessionUser.getId());

        boolean matchPassword = utils.matchPassword(resetVO.getOldPassword(), authUser.getPassword());
        if (!matchPassword)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Old password incorrect")
                    .build()), 404);

        if (!resetVO.getNewPassword().equals(resetVO.getConfirmNewPassword()))
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("new password not confirmed")
                    .build()), 400);

        String encode = utils.encode(resetVO.getNewPassword());
        authUser.setPassword(encode);
        dao.update(authUser);
        return new Response<>(new DataVO<>(null), 200);
    }


    public static AuthUserService getInstance() {
        if (instance == null) {
            instance = new AuthUserService();
        }
        return instance;
    }

    public Response<DataVO<Void>> deleteUser(Long userId, String password) {
        AuthUser authUser = dao.findById(Session.sessionUser.getId());

        boolean matchPassword = utils.matchPassword(password, authUser.getPassword());
        if (!matchPassword) {
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("Password incorrect")
                    .build()), 404);
        }

        Response<DataVO<Void>> deleteResponse = delete(userId);

        if (deleteResponse.getStatus() != 200)
            return new Response<>(new DataVO<>(AppErrorVO.builder()
                    .friendlyMessage("oops something went")
                    .build()), 404);

        Session.setSessionUser(null);
        return new Response<>(new DataVO<>(null), 200);
    }
}

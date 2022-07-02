package org.example.dao.teacher;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.GenericDAO;
import org.example.domains.users.TeacherEntity;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeacherDAO extends GenericDAO<TeacherEntity, Long> {


    private static TeacherDAO instance;


    public static TeacherDAO getInstance() {
        if (instance == null) {
            instance = new TeacherDAO();
        }
        return instance;
    }


    public TeacherEntity findByUserId(Long userId) {
        Session session = getSession();
        session.beginTransaction();

        TeacherEntity teacherEntity = session.createQuery("select t from TeacherEntity t where t.user.id=:userId", TeacherEntity.class)
                .setParameter("userId", userId)
                .getSingleResultOrNull();

        session.getTransaction().commit();
        session.close();
        return teacherEntity;

    }
}

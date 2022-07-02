package org.example.dao.subject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dao.GenericDAO;
import org.example.domains.subject.SubjectEntity;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubjectDAO extends GenericDAO<SubjectEntity, Long> {
    private static SubjectDAO instance;

    public static SubjectDAO getInstance() {
        if (instance == null) {
            instance = new SubjectDAO();
        }
        return instance;
    }

    public SubjectEntity findByName(String name) {
        Session session = getSession();
        session.beginTransaction();

        SubjectEntity result = session.createQuery("select t from SubjectEntity t where  lower(t.title) = lower(:name) and t.deleted=false ", SubjectEntity.class)
                .setParameter("name", name)
                .getSingleResultOrNull();

        session.close();
        return result;
    }

}

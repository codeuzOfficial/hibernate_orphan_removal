package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        saveDate();
//        removeParent();
//        setParentNull();
    }

    public static void saveDate() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        PostEntity post = new PostEntity();
        post.setTitle("Yangi qiyshaygan rasm.");
        post.setContent("Bu rasmni zalda oldim. Juda zo'r rasm. ");

        CommentEntity comment1 = new CommentEntity(); // child
        comment1.setContent("Rasmga qara.");
        comment1.setCreatedDate(LocalDateTime.now());
        comment1.setPost(post);

        CommentEntity comment2 = new CommentEntity(); // child
        comment2.setContent("Qo'yni dumbasimi bu");
        comment2.setCreatedDate(LocalDateTime.now());
        comment2.setPost(post);

        post.setCommentList(List.of(comment1, comment2));

        session.persist(post); // save parent, child also will be saved

        t.commit();
        session.close();
        factory.close();
    }

    public static void removeParent() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        PostEntity post = session.get(PostEntity.class, 1); // get parent
        session.delete(post);// delete parent, child also will be deleted

        t.commit();
        session.close();
        factory.close();
    }

    public static void setParentNull() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        PostEntity post = session.get(PostEntity.class, 1); // get parent
        post.getCommentList().remove(0); // will remove first child. From db also will be deleted.

        t.commit();
        session.close();
        factory.close();
    }
}
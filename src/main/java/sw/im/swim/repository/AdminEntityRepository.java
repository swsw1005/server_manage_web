package sw.im.swim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw.im.swim.bean.entity.AdminEntity;

@Repository
public interface AdminEntityRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByEmailEquals(String email);

}
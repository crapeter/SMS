package crapeter.proj.PostgreSQL_SMS.Repo;

import crapeter.proj.PostgreSQL_SMS.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface AdminRepo extends JpaRepository<Admin, Long> {
  Admin findByEmail(String email);
  void deleteByEmail(String email);
}

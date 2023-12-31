package cadastro.pessoas.api.repository;

import cadastro.pessoas.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface PersonRepository extends JpaRepository<Person, BigInteger> {
}

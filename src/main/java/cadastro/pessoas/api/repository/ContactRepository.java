package cadastro.pessoas.api.repository;

import cadastro.pessoas.api.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ContactRepository extends JpaRepository<Contacts, BigInteger> {
}

package hello;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryDao, String> {
	public Optional<CountryDao> findCountryByName(String name);
}
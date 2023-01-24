package med.voll.api.domain.paciente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacientesRepository extends JpaRepository<Paciente, Long> {
}

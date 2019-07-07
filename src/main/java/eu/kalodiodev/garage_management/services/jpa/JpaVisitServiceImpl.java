package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.VisitCommandToVisit;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.VisitRepository;
import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaVisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitCommandToVisit visitCommandToVisit;

    public JpaVisitServiceImpl(VisitRepository visitRepository, VisitCommandToVisit visitCommandToVisit) {
        this.visitRepository = visitRepository;
        this.visitCommandToVisit = visitCommandToVisit;
    }

    @Override
    public Visit save(VisitCommand visitCommand) {
        return visitRepository.save(visitCommandToVisit.convert(visitCommand));
    }

    @Override
    public Visit findById(Long id) {
        Optional<Visit> visitOptional = visitRepository.findById(id);

        if (visitOptional.isEmpty()) {
            throw new NotFoundException("Visit not found");
        }

        return visitOptional.get();
    }

    @Override
    public VisitCommand findCommandById(Long aLong) {
        return null;
    }

    @Override
    public void update(VisitCommand visitCommand) {

    }
}

package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Visit;

public interface VisitService extends CrudService<Visit, VisitCommand, Long> {

    Visit findByCustomerIdAndCarIdAndVisitId(Long customerId, Long carId, Long visitId);

    VisitCommand findVisitCommandByCustomerIdAndCarIdAndVisitId(Long customerId, Long carId, Long visitId);
}

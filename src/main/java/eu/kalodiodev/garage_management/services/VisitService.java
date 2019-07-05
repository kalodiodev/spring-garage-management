package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Visit;

public interface VisitService extends CrudService<Visit, VisitCommand, Long> {
}

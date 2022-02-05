package com.fschool.fschool.Model.Repositories;

import com.fschool.fschool.Model.Entities.Deliverable;
import com.fschool.fschool.Model.Entities.DeliverableID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverableRepository extends JpaRepository<Deliverable,DeliverableID> {
    
}

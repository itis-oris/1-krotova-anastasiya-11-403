package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;
import java.util.List;
import java.util.Optional;

public class ProcedureService {
    private ProcedureDao procedureDao;

    public ProcedureService(ProcedureDao procedureDao) {
        this.procedureDao = procedureDao;
    }

    public List<Procedure> getProceduresByMaster(Long masterId) {
        return procedureDao.findByMasterId(masterId);
    }

}

package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;
import java.util.List;
import java.util.Optional;

public class SalonService {
    private SalonDao salonDao;
    private MasterDao masterDao;

    public SalonService(SalonDao salonDao, MasterDao masterDao) {
        this.salonDao = salonDao;
        this.masterDao = masterDao;
    }

    public List<Salon> getAllSalons() {
        return salonDao.findAll();
    }

    public Optional<Salon> getSalonById(Long id) {
        return salonDao.findById(id);
    }

    public List<Master> getMastersBySalon(Long salonId) {
        return masterDao.findBySalonId(salonId);
    }
}

package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MasterService {
    private MasterDao masterDao;
    private ProcedureDao procedureDao;
    private FeedbackDao feedbackDao;

    public MasterService(MasterDao masterDao, ProcedureDao procedureDao, FeedbackDao feedbackDao) {
        this.masterDao = masterDao;
        this.procedureDao = procedureDao;
        this.feedbackDao = feedbackDao;
    }

    public Optional<Master> getMasterById(Long id) {
        return masterDao.findById(id);
    }

    public List<Master> getMastersBySalon(Long salonId) {
        return masterDao.findBySalonId(salonId);
    }

    public Optional<Master> getMasterByUserId(Long userId) {
        return masterDao.getMasterByUserId(userId);
    }

    public List<Master> getMastersBySalonAndCategory(Long salonId, Long categoryId) {
        return masterDao.getMastersBySalonAndCategory(salonId, categoryId);
    }

    public List<Procedure> getMasterProcedures(Long masterId) {
        return procedureDao.findByMasterId(masterId);
    }

    public Double getMasterRating(Long masterId) {
        return feedbackDao.getAverageRatingByMaster(masterId);
    }

    public List<Feedback> getMasterFeedbacks(Long masterId) {
        return feedbackDao.findByMasterId(masterId);
    }

    public boolean updateMaster(Long masterId, Master updatedMaster) {
        try {

            Optional<Master> existingMaster = masterDao.findById(masterId);
            if (!existingMaster.isPresent()) {
                return false;
            }

            Master master = existingMaster.get();

            if (updatedMaster.getSalon_id() != null) {
                master.setSalon_id(updatedMaster.getSalon_id());
            }
            if (updatedMaster.getAvatarURL() != null) {
                master.setAvatarURL(updatedMaster.getAvatarURL());
            }
            if (updatedMaster.getStag() != null) {
                master.setStag(updatedMaster.getStag());
            }
            if (updatedMaster.getDescription() != null) {
                master.setDescription(updatedMaster.getDescription());
            }

            masterDao.update(master);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
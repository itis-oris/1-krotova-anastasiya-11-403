package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;

import java.time.LocalDateTime;

public class FeedbackService {
    private FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }
    public boolean createFeedback(Long userId, Long masterId, Integer rating, String comment) {

        try {
            if (feedbackDao.hasUserReviewedMaster(userId, masterId)) {
                System.out.println("Пользователь уже оставлял отзыв этому мастеру");
                return false;
            }

            Feedback feedback = new Feedback();
            feedback.setUserID(userId);
            feedback.setMaster_Id(masterId);
            feedback.setRating(rating.doubleValue());
            feedback.setText(comment);
            feedback.setCreated_at(LocalDateTime.now());

            Feedback savedFeedback = feedbackDao.save(feedback);

            if (savedFeedback != null && savedFeedback.getId() != null) {
                System.out.println("Отзыв успешно создан: " + savedFeedback.getId());
                return true;
            } else {
                System.out.println("Ошибка при создании отзыва");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Ошибка в создании отзыва: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

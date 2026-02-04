package ru.beautysalon.service;

import ru.beautysalon.dao.*;

public class ServiceLocator {
    private static volatile ServiceLocator instance;

    private UserDao userDao;
    private SalonDao salonDao;
    private CategoryDao categoryDao;
    private MasterDao masterDao;
    private ProcedureDao procedureDao;
    private BookingDao bookingDao;
    private FeedbackDao feedbackDao;

    private UserService userService;
    private SalonService salonService;
    private CategoryService categoryService;
    private MasterService masterService;
    private ProcedureService procedureService;
    private BookingService bookingService;
    private FeedbackService feedbackService;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }

    public void init() {

        userDao = new UserDao();
        salonDao = new SalonDao();
        categoryDao = new CategoryDao();
        masterDao = new MasterDao();
        procedureDao = new ProcedureDao();
        bookingDao = new BookingDao();
        feedbackDao = new FeedbackDao();

        userService = new UserService(userDao, masterDao);
        salonService = new SalonService(salonDao, masterDao);
        categoryService = new CategoryService(categoryDao);
        masterService = new MasterService(masterDao, procedureDao, feedbackDao);
        procedureService = new ProcedureService(procedureDao);
        bookingService = new BookingService(bookingDao);
        feedbackService = new FeedbackService(feedbackDao);

    }

    public UserService getUserService() { return userService; }
    public SalonService getSalonService() { return salonService; }
    public CategoryService getCategoryService() { return categoryService; }
    public MasterService getMasterService() { return masterService; }
    public ProcedureService getProcedureService() { return procedureService; }
    public BookingService getBookingService() { return bookingService; }
    public FeedbackService getFeedbackService() { return feedbackService; }
}

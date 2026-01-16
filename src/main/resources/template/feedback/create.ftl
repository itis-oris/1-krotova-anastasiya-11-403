<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Оставить отзыв</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', sans-serif;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            min-height: 100vh;
            color: #333;
            padding-bottom: 40px;
        }

        .navbar {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 1rem 0;
        }

        .navbar-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar-brand {
            font-size: 24px;
            font-weight: 700;
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .navbar-brand:hover {
            opacity: 0.9;
        }

        .navbar-back {
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
            transition: opacity 0.2s;
            cursor: pointer;
        }

        .navbar-back:hover {
            opacity: 0.8;
        }

        .breadcrumb-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 25px 20px;
        }

        .breadcrumb {
            display: flex;
            gap: 10px;
            list-style: none;
            font-size: 13px;
            flex-wrap: wrap;
        }

        .breadcrumb-item {
            color: rgba(255, 255, 255, 0.8);
        }

        .breadcrumb-item a {
            color: white;
            text-decoration: none;
            transition: opacity 0.2s;
        }

        .breadcrumb-item a:hover {
            opacity: 0.8;
        }

        .breadcrumb-item.active {
            color: white;
            font-weight: 600;
        }

        .breadcrumb-item:not(:last-child)::after {
            content: "›";
            margin-left: 10px;
            color: rgba(255, 255, 255, 0.6);
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .feedback-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            animation: slideIn 0.4s ease-out;
        }

        .feedback-header {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }

        .feedback-header h1 {
            font-size: 24px;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
        }

        .feedback-body {
            padding: 40px;
        }

        .master-info {
            text-align: center;
            margin-bottom: 35px;
            padding-bottom: 30px;
            border-bottom: 1px solid #eee;
        }

        .master-avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            margin: 0 auto 15px;
            border: 4px solid #f0f0f0;
            display: block;
        }

        .master-name {
            font-size: 22px;
            font-weight: 600;
            color: #333;
            margin: 0;
        }

        .form-group {
            margin-bottom: 28px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
        }

        textarea {
            width: 100%;
            padding: 14px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            font-family: inherit;
            resize: vertical;
            transition: all 0.2s;
            line-height: 1.6;
        }

        textarea:focus {
            outline: none;
            border-color: #f55f8c;
        }

        .rating-group {
            background: #f8f9fa;
            padding: 25px;
            border-radius: 8px;
            margin-bottom: 28px;
        }

        .rating-label {
            display: block;
            text-align: center;
            font-size: 14px;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
        }

        .rating-container {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin-bottom: 15px;
        }

        .star-input {
            display: none;
        }

        .star-button {
            font-size: 40px;
            background: none;
            border: none;
            cursor: pointer;
            color: #ddd;
            transition: all 0.2s ease;
            padding: 5px;
            transform: scale(1);
        }

        .star-button:hover {
            color: #ffc107;
            transform: scale(1.2);
        }

        .star-button.active {
            color: #ffc107;
            transform: scale(1.1);
        }

        .star-button.active ~ .star-button.active,
        .star-button.hover-active,
        .star-button.hover-active ~ .star-button {
            color: #ffc107;
        }

        .rating-value {
            text-align: center;
            font-size: 13px;
            color: #999;
        }

        .rating-value strong {
            color: #f55f8c;
            font-size: 14px;
        }

        .btn {
            width: 100%;
            padding: 14px 20px;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            transition: all 0.2s;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .btn:active {
            transform: translateY(0);
        }

        .btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }

        .success-message {
            background: #d4edda;
            border-left: 4px solid #28a745;
            color: #155724;
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .success-message i {
            color: #28a745;
            font-size: 18px;
        }

    </style>
</head>
<body>

<nav class="navbar">
    <div class="navbar-container">
        <a class="navbar-brand" href="${basePath}/salon/list">
            <i></i> BeautySalon Kazan
        </a>
        <a class="navbar-back" onclick="history.back()">
            <i></i> Назад
        </a>
    </div>
</nav>


<div class="breadcrumb-container">
    <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="${basePath}/salon/list">Салоны</a></li>
        <#if master??>
        <li class="breadcrumb-item"><a href="${basePath}/master/${master.id}">${master.user.firstname}</a></li>
    </#if>
    <li class="breadcrumb-item active">Оставить отзыв</li>
    </ul>
</div>


<div class="container">
    <div class="feedback-card">
        <div class="feedback-header">
            <h1><i></i> Оставить отзыв</h1>
        </div>

        <div class="feedback-body">
            <#if master??>
            <div class="master-info">
                <img
                        src="${master.avatarURL!'https://via.placeholder.com/100/f55f8c/ffffff?text=${master.user.firstname?substring(0,1)}'}"
                        class="master-avatar"
                        alt="${master.user.firstname} ${master.user.lastname}"
                >
                <h2 class="master-name">
                    ${master.user.firstname} ${master.user.lastname}
                </h2>
            </div>
        </#if>


        <#if success??>
        <div class="success-message">
            <span>Ваш отзыв успешно отправлен!</span>
        </div>
    </#if>

    <form id="feedbackForm" action="${basePath}/feedback" method="POST">
        <input type="hidden" name="masterId" value="${master.id}">
        <input type="hidden" id="ratingInput" name="rating" value="">

        <div class="rating-group">
            <label class="rating-label">Ваша оценка</label>
            <div class="rating-container" id="ratingContainer">
                <#list 1..5 as i>
                <button
                        type="button"
                        class="star-button"
                        data-rating="${i}"
                        title="${i} звезда"
                >
                    <i class="fas fa-star"></i>
                </button>
            </#list>
        </div>
        <div class="rating-value">
            Выбранная оценка: <strong id="ratingValue">не выбрана</strong>
        </div>
</div>

<div class="form-group">
    <label for="comment">Комментарий</label>
    <textarea
            id="comment"
            name="comment"
            rows="5"
            placeholder="Расскажите о вашем опыте посещения мастера. Что вам понравилось? Ваши впечатления?"
            required
    ></textarea>
</div>

<button type="submit" class="btn">
    <i></i> Отправить отзыв
</button>
</form>
</div>
</div>
</div>

<script>
    const ratingButtons = document.querySelectorAll('.star-button');
    const ratingInput = document.getElementById('ratingInput');
    const ratingValue = document.getElementById('ratingValue');
    const ratingTexts = ['не выбрана', '1 звезда', '2 звезды', '3 звезды', '4 звезды', '5 звёзд'];

    ratingButtons.forEach((button, index) => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const rating = this.getAttribute('data-rating');
            ratingInput.value = rating;

            ratingButtons.forEach((btn, i) => {
                if (i < rating) {
                    btn.classList.add('active');
                } else {
                    btn.classList.remove('active');
                }
            });

            ratingValue.textContent = ratingTexts[rating];
        });
    });


    document.getElementById('feedbackForm').addEventListener('submit', function(e) {
        const rating = ratingInput.value;
        const comment = document.getElementById('comment').value.trim();

        if (!rating) {
            e.preventDefault();
            alert('Пожалуйста, выберите оценку');
            return;
        }

        if (!comment) {
            e.preventDefault();
            alert('Пожалуйста, напишите комментарий');
            return;
        }

        if (comment.length < 10) {
            e.preventDefault();
            alert('Комментарий должен содержать минимум 10 символов');
            return;
        }
    });
</script>
</body>
</html>
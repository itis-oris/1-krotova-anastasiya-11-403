<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${master.user.firstname} ${master.user.lastname}</title>
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
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .main-grid {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 30px;
            margin-bottom: 30px;
        }

        .master-info-card {
            background: white;
            border-radius: 12px;
            padding: 30px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            height: fit-content;
            position: sticky;
            top: 20px;
        }

        .master-avatar {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            object-fit: cover;
            margin: 0 auto 25px;
            border: 4px solid #f0f0f0;
            display: block;
        }

        .master-name {
            font-size: 26px;
            font-weight: 700;
            color: #333;
            margin-bottom: 8px;
        }

        .master-experience {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            color: #666;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .master-experience i {
            color: #f55f8c;
        }

        .rating-container {
            margin-bottom: 25px;
            padding-bottom: 25px;
            border-bottom: 1px solid #eee;
        }

        .rating-stars {
            font-size: 24px;
            color: #ffc107;
            margin-bottom: 8px;
            letter-spacing: 4px;
        }

        .rating-score {
            font-size: 20px;
            font-weight: 700;
            color: #333;
        }

        .rating-count {
            font-size: 13px;
            color: #999;
            margin-top: 4px;
        }

        .master-description {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin: 20px 0;
        }

        .master-actions {
            display: grid;
            grid-template-columns: 1fr;
            gap: 12px;
        }

        .btn {
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            transition: all 0.2s;
        }

        .btn-book {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
        }

        .btn-book:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .btn-feedback {
            background: white;
            color: #f55f8c;
            border: 2px solid #f55f8c;
        }

        .btn-feedback:hover {
            background: #f55f8c;
            color: white;
        }

        .section-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
        }

        .section-header {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            padding: 20px 30px;
            font-size: 18px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .section-body {
            padding: 30px;
        }

        .procedures-table {
            width: 100%;
            border-collapse: collapse;
        }

        .procedures-table thead {
            background: #f8f9fa;
        }

        .procedures-table th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            color: #333;
            border-bottom: 2px solid #eee;
        }

        .procedures-table td {
            padding: 15px;
            border-bottom: 1px solid #eee;
        }

        .procedures-table tbody tr:hover {
            background: #f8f9fa;
        }

        .procedure-name {
            font-weight: 600;
            color: #333;
        }

        .procedure-price {
            font-weight: 700;
            color: #f55f8c;
            font-size: 16px;
        }

        .empty-message {
            text-align: center;
            color: #999;
            padding: 20px;
            font-size: 14px;
        }

        .feedback-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .feedback-list::-webkit-scrollbar {
            width: 8px;
        }

        .feedback-list::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }

        .feedback-list::-webkit-scrollbar-thumb {
            background: #f55f8c;
            border-radius: 10px;
        }

        .feedback-list::-webkit-scrollbar-thumb:hover {
            background: #e91e63;
        }

        .feedback-item {
            padding: 20px;
            border: 1px solid #eee;
            border-radius: 8px;
            background: #fafafa;
            transition: all 0.2s;
        }

        .feedback-item:hover {
            background: #f0f0f0;
            border-color: #ddd;
        }

        .feedback-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 12px;
            gap: 15px;
        }

        .feedback-author {
            font-weight: 600;
            color: #333;
        }

        .feedback-rating {
            color: #ffc107;
            font-size: 14px;
            letter-spacing: 2px;
        }

        .feedback-text {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 8px;
        }

        .feedback-date {
            color: #999;
            font-size: 12px;
        }

        .no-feedbacks {
            text-align: center;
            color: #999;
            padding: 30px 20px;
            font-size: 14px;
        }

    </style>
</head>
<body>
<nav class="navbar">
    <div class="navbar-container">
        <a class="navbar-brand" href="${basePath}/salon/list">
            <i class="fas fa-spa"></i> BeautySalon Kazan
        </a>
        <a class="navbar-back" href="${basePath}/master?salonId=${master.salon_id!''}&categoryId=${categoryId!''}">
            <i class="fas fa-arrow-left"></i> Назад
        </a>
    </div>
</nav>

<div class="breadcrumb-container">
    <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="${basePath}/salon/list">Салоны</a></li>
        <li class="breadcrumb-item"><a href="${basePath}/category?salonId=${master.salon_id!''}">Категории</a></li>
        <li class="breadcrumb-item"><a href="${basePath}/master?salonId=${master.salon_id!''}&categoryId=${categoryId!''}">Мастера</a></li>
        <li class="breadcrumb-item active">${master.user.firstname}</li>
    </ul>
</div>

<div class="container">
    <div class="main-grid">
        <div class="master-info-card">
            <img
                    src="${master.avatarURL!'https://via.placeholder.com/140/f55f8c/ffffff?text=${master.user.firstname?substring(0,1)}'}"
                    class="master-avatar"
                    alt="${master.user.firstname} ${master.user.lastname}"
            >

            <div class="master-name">
                ${master.user.firstname} ${master.user.lastname}
            </div>

            <#if master.stag??>
            <div class="master-experience">
                <i class="fas fa-briefcase"></i>
                <span>Стаж: ${master.stag} лет</span>
            </div>
            <div class="master-experience">
                <p><strong>Телефон для записи:</strong> ${master.user.phone!'-'}</p>
            </div>
        </#if>

        <#if rating??>
        <div class="rating-container">
            <div class="rating-stars">
                <#list 1..5 as i>
                <#if i <= rating?floor>
                <i class="fas fa-star"></i>
                <#elseif i - 0.5 <= rating>
                <i class="fas fa-star-half-alt"></i>
                <#else>
                <i class="far fa-star"></i>
            </#if>
        </#list>
    </div>
    <div class="rating-score">${rating?string("0.0")}</div>
    <div class="rating-count">(${feedbacks?size} отзывов)</div>
</div>
</#if>

<#if master.description??>
<p class="master-description">
    ${master.description}
</p>
</#if>

<div class="master-actions">
    <a href="${basePath}/feedback?masterId=${master.id}" class="btn btn-feedback">
        <i class="fas fa-comment"></i> Оставить отзыв
    </a>
</div>
</div>


<div>
    <div class="section-card">
        <div class="section-header">
            <i class="fas fa-concierge-bell"></i> Услуги и цены
        </div>
        <div class="section-body">
            <#if procedures?? && procedures?size gt 0>
            <table class="procedures-table">
                <thead>
                <tr>
                    <th>Услуга</th>
                    <th>Описание</th>
                    <th style="text-align: right;">Цена</th>
                </tr>
                </thead>
                <tbody>
                <#list procedures as procedure>
                <tr>
                    <td class="procedure-name">${procedure.procedure_name}</td>
                    <td>${procedure.description!''}</td>
                    <td style="text-align: right;" class="procedure-price">${procedure.price} ₽</td>
                </tr>
                </#list>
                </tbody>
            </table>
            <#else>
            <div class="empty-message">
                <i class="fas fa-inbox" style="font-size: 32px; color: #ddd; margin-bottom: 10px; display: block;"></i>
                У мастера пока нет доступных услуг
            </div>
        </#if>
    </div>
</div>

<div class="section-card">
    <div class="section-header">
        <i class="fas fa-comments"></i> Отзывы
    </div>
    <div class="section-body">
        <#if feedbacks?? && feedbacks?size gt 0>
        <div class="feedback-list">
            <#list feedbacks as feedback>
            <div class="feedback-item">
                <div class="feedback-header">
                    <div>
                        <div class="feedback-author">
                            ${feedback.user.firstname} ${feedback.user.lastname}
                        </div>
                        <#if feedback.rating??>
                        <div class="feedback-rating">
                            <#list 1..5 as i>
                            <#if i <= feedback.rating?floor>
                            <i class="fas fa-star"></i>
                            <#else>
                            <i class="far fa-star"></i>
                        </#if>
                    </#list>
                </div>
            </#if>
        </div>
    </div>
    <#if feedback.text??>
    <p class="feedback-text">
        ${feedback.text}
    </p>
</#if>
<div class="feedback-date">
    ${feedback.created_at}
</div>
</div>
</#list>
</div>
<#else>
<div class="no-feedbacks">
    <i class="fas fa-comments" style="font-size: 40px; color: #ddd; margin-bottom: 15px; display: block;"></i>
    Пока нет отзывов. Будьте первым!
</div>
</#if>
</div>
</div>
</div>
</div>
</div>
</body>
</html>
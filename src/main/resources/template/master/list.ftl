<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Выбор мастера</title>
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
            font-size: 14px;
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
            padding: 0 20px 40px;
        }

        .page-header {
            background: white;
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 40px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .page-header h1 {
            font-size: 32px;
            color: #333;
            display: flex;
            align-items: center;
            gap: 15px;
            margin: 0;
        }

        .page-header p {
            color: #999;
            font-size: 14px;
            margin-top: 8px;
        }

        .masters-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(380px, 1fr));
            gap: 25px;
        }

        .master-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .master-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
        }

        .master-card-body {
            padding: 25px;
            display: flex;
            gap: 20px;
        }

        .master-avatar-container {
            flex-shrink: 0;
        }

        .master-avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #f0f0f0;
        }

        .master-info {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .master-name {
            font-size: 20px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
        }

        .master-detail {
            display: flex;
            align-items: center;
            gap: 8px;
            color: #666;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .master-detail i {
            color: #f55f8c;
            width: 16px;
            text-align: center;
        }

        .master-rating {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 15px;
        }

        .rating-stars {
            color: #ffc107;
            font-size: 16px;
            display: flex;
            gap: 2px;
        }

        .rating-score {
            font-weight: 600;
            color: #333;
            margin-left: 8px;
        }

        .rating-count {
            font-size: 13px;
            color: #999;
        }

        .master-actions {
            margin-top: auto;
        }

        .btn-book {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            padding: 11px 20px;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.2s;
            width: 100%;
        }

        .btn-book:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .btn-book:active {
            transform: translateY(0);
        }

        .empty-state {
            background: white;
            border-radius: 12px;
            padding: 60px 40px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }

        .empty-state-icon {
            font-size: 64px;
            color: #ddd;
            margin-bottom: 20px;
        }

        .empty-state h3 {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }

        .empty-state p {
            color: #999;
            font-size: 16px;
        }


    </style>
</head>
<body>

<nav class="navbar">
    <div class="navbar-container">
        <a class="navbar-brand" href="${basePath}/salon/list">
            <i></i> BeautySalon Kazan
        </a>
        <a class="navbar-back" href="${basePath}/category?salonId=${salonId!''}">
            <i class="fas fa-arrow-left"></i> Назад
        </a>
    </div>
</nav>

<div class="breadcrumb-container">
    <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="${basePath}/salon/list">Салоны</a></li>
        <li class="breadcrumb-item"><a href="${basePath}/category?salonId=${salonId!''}">Категории</a></li>
        <li class="breadcrumb-item active">Мастера</li>
    </ul>
</div>

<div class="container">
    <div class="page-header">
        <h1>Наши мастера</h1>
        <p>Выбери опытного мастера для выполнения услуги</p>
    </div>

    <#if masters?? && masters?size gt 0>
    <div class="masters-list">
        <#list masters as master>
        <div class="master-card">
            <div class="master-card-body">
                <div class="master-avatar-container">
                    <img
                            src="${master.avatarURL!'https://via.placeholder.com/100/f55f8c/ffffff?text=${master.user.firstname?substring(0,1)}'}"
                            class="master-avatar"
                            alt="${master.user.firstname} ${master.user.lastname}"
                    >
                </div>

                <div class="master-info">
                    <h3 class="master-name">
                        ${master.user.firstname} ${master.user.lastname}
                    </h3>

                    <#if master.stag??>
                    <div class="master-detail">
                        <span>Стаж: ${master.stag} лет</span>
                    </div>
                </#if>

                <#if master.averageRating??>
                <div class="master-rating">
                    <div class="rating-stars">
                        <#list 1..5 as i>
                        <#if i <= master.averageRating?floor>
                        <i"></i>
                        <#elseif i - 0.5 <= master.averageRating>
                        <i></i>
                        <#else>
                        <i></i>
                    </#if>
                </#list>
            </div>
            <span class="rating-score">${master.averageRating?string("0.0")}</span>
            <span class="rating-count">(${master.feedbackCount!0} отзывов)</span>
        </div>
    </#if>

    <div class="master-actions">
        <a href="${basePath}/master-profile?masterId=${master.id}" class="btn-book">
            <i></i> Записаться
        </a>
    </div>
</div>
</div>
</div>
</#list>
</div>
<#else>
<div class="empty-state">
    <h3>Мастера не найдены</h3>
    <p>В данной категории нет доступных мастеров. Попробуй выбрать другую категорию.</p>
</div>
</#if>
</div>
</body>
</html>
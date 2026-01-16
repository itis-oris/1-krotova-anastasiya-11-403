<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Салоны красоты Казани</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', sans-serif;
            background: #f5f5f5;
            color: #333;
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

        .navbar-links {
            display: flex;
            gap: 30px;
            list-style: none;
        }

        .navbar-link {
            color: white;
            text-decoration: none;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: opacity 0.2s;
        }

        .navbar-link:hover {
            opacity: 0.8;
        }

        /* Main Container */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .page-header {
            margin-bottom: 40px;
        }

        .page-header h1 {
            font-size: 36px;
            margin-bottom: 10px;
            color: #333;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .page-header p {
            color: #999;
            font-size: 16px;
        }


        .salon-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 25px;
            margin-top: 30px;
        }


        .salon-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .salon-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
        }

        .salon-card-img {
            width: 100%;
            height: 220px;
            object-fit: cover;
            display: block;
        }

        .salon-card-body {
            padding: 25px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .salon-card-title {
            font-size: 20px;
            font-weight: 600;
            color: #333;
            margin-bottom: 12px;
        }

        .salon-card-address {
            display: flex;
            align-items: flex-start;
            gap: 8px;
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .salon-card-address i {
            margin-top: 2px;
            color: #f55f8c;
            flex-shrink: 0;
        }

        .salon-card-description {
            color: #777;
            font-size: 14px;
            line-height: 1.5;
            margin-bottom: 20px;
            flex-grow: 1;
        }

        .salon-card-buttons {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            margin-top: auto;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
            border: none;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            transition: all 0.2s;
            text-align: center;
        }

        .btn-outline {
            background: transparent;
            color: #f55f8c;
            border: 2px solid #f55f8c;
        }

        .btn-outline:hover {
            background: #f55f8c;
            color: white;
        }

        .btn-primary {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            border: none;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(245, 95, 140, 0.4);
        }


        .empty-state {
            grid-column: 1 / -1;
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }

        .empty-state i {
            font-size: 48px;
            color: #ddd;
            margin-bottom: 20px;
        }

        .empty-state h4 {
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
            <i class="fas fa-spa"></i> BeautySalon Kazan
        </a>
        <ul class="navbar-links">
            <li><a class="navbar-link" href="${basePath}/profile"><i class="fas fa-user"></i> Профиль</a></li>
            <li><a class="navbar-link" href="${basePath}/my-bookings"><i class="fas fa-user"></i> Мои записи</a></li>
            <li><a class="navbar-link" href="${basePath}/logout"><i class="fas fa-sign-out-alt"></i> Выйти</a></li>
        </ul>
    </div>
</nav>


<div class="container">
    <div class="page-header">
        <h1><i class="fas fa-spa"></i> Салоны красоты Казани</h1>
        <p>Выбери идеальный салон для себя</p>
    </div>

    <div class="salon-grid">
        <#if salons?? && salons?size gt 0>
        <#list salons as salon>
        <div class="salon-card">
            <#if salon.avatarURL??>
            <img src="${salon.avatarURL}" class="salon-card-img" alt="${salon.salon_name}">
            <#else>
            <img src="https://via.placeholder.com/300x200/f55f8c/ffffff?text=Salon" class="salon-card-img" alt="${salon.salon_name}">
        </#if>

        <div class="salon-card-body">
            <h5 class="salon-card-title">${salon.salon_name}</h5>

            <div class="salon-card-address">
                <i class="fas fa-map-marker-alt"></i>
                <span>${salon.address!''}</span>
            </div>

            <#if salon.description??>
            <p class="salon-card-description">${salon.description}</p>
        </#if>

        <div class="salon-card-buttons">
            <a href="${basePath}/salon/detail/${salon.id}" class="btn btn-outline">
                <i class="fas fa-info-circle"></i> Подробнее
            </a>
            <a href="${basePath}/category?salonId=${salon.id}" class="btn btn-primary">
                <i class="fas fa-arrow-right"></i> Услуги
            </a>
        </div>
    </div>
</div>
</#list>
<#else>
<div class="empty-state">
    <div style="font-size: 60px; color: #ddd; margin-bottom: 20px;">
        <i class="fas fa-inbox"></i>
    </div>
    <h4>Салоны не найдены</h4>
    <p>В данный момент нет доступных салонов красоты.</p>
</div>
</#if>
</div>
</div>
</body>
</html>
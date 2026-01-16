<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${salon.salon_name}</title>
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

        .navbar-links {
            display: flex;
            gap: 20px;
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
            grid-template-columns: 2fr 1fr;
            gap: 30px;
        }

        .salon-info-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }

        .salon-header-image {
            width: 100%;
            height: 300px;
            object-fit: cover;
            display: block;
        }

        .salon-info-body {
            padding: 30px;
        }

        .salon-name {
            font-size: 32px;
            font-weight: 700;
            color: #333;
            margin-bottom: 20px;
        }

        .salon-detail {
            display: flex;
            align-items: flex-start;
            gap: 12px;
            margin-bottom: 20px;
        }

        .salon-detail i {
            color: #f55f8c;
            margin-top: 2px;
            font-size: 18px;
            min-width: 24px;
        }

        .salon-detail-text {
            flex: 1;
        }

        .salon-detail-label {
            font-size: 12px;
            font-weight: 600;
            color: #999;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 4px;
        }

        .salon-detail-value {
            font-size: 15px;
            color: #333;
        }

        .salon-description {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-top: 25px;
            line-height: 1.6;
            color: #666;
        }

        .map-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            margin-top: 30px;
        }

        .map-header {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            padding: 15px 20px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        #map {
            width: 100%;
            height: 400px;
            background: #f0f0f0;
        }

        .sidebar {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .action-card {
            background: white;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }

        .action-card-header {
            font-size: 16px;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .btn-action {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            padding: 14px 20px;
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

        .btn-action:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .masters-section {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }

        .masters-header {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            padding: 15px 20px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .masters-list {
            padding: 20px;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .master-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 15px;
            border-radius: 8px;
            background: #f8f9fa;
            transition: all 0.2s;
            cursor: pointer;
        }

        .master-item:hover {
            background: #f0f0f0;
        }

        .master-avatar {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid white;
            flex-shrink: 0;
        }

        .master-info {
            flex: 1;
        }

        .master-name {
            font-weight: 600;
            color: #333;
            margin-bottom: 4px;
        }

        .master-experience {
            font-size: 13px;
            color: #999;
            display: flex;
            align-items: center;
            gap: 6px;
        }

        .master-experience i {
            color: #f55f8c;
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
            <li><a class="navbar-link" href="${basePath}/salon/list"><i class="fas fa-arrow-left"></i> Назад</a></li>
            <li><a class="navbar-link" href="${basePath}/profile"><i class="fas fa-user"></i> Профиль</a></li>
        </ul>
    </div>
</nav>


<div class="breadcrumb-container">
    <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="${basePath}/salon/list">Салоны</a></li>
        <li class="breadcrumb-item active">${salon.salon_name}</li>
    </ul>
</div>

<div class="container">
    <div class="main-grid">
        <div>
            <div class="salon-info-card">
                <#if salon.avatarURL??>
                <img src="${salon.avatarURL}" class="salon-header-image" alt="${salon.salon_name}">
                <#else>
                <img src="https://via.placeholder.com/600x300/f55f8c/ffffff?text=${salon.salon_name}" class="salon-header-image" alt="${salon.salon_name}">
            </#if>

            <div class="salon-info-body">
                <h1 class="salon-name">${salon.salon_name}</h1>

                <div class="salon-detail">
                    <i class="fas fa-map-marker-alt"></i>
                    <div class="salon-detail-text">
                        <div class="salon-detail-label">Адрес</div>
                        <div class="salon-detail-value">${salon.address!''}</div>
                    </div>
                </div>

                <#if salon.description??>
                <div class="salon-description">
                    ${salon.description}
                </div>
            </#if>
        </div>
    </div>

    <div class="map-card">
        <div class="map-header">
            <i class="fas fa-map"></i> Местоположение
        </div>
        <div id="map"></div>
    </div>
</div>

<div class="sidebar">
    <div class="action-card">
        <div class="action-card-header">
            <i class="fas fa-concierge-bell"></i> Действия
        </div>
        <a href="${basePath}/category?salonId=${salon.id}" class="btn-action">
            <i class="fas fa-arrow-right"></i> Выбрать услугу
        </a>
    </div>

    <#if masters?? && masters?size gt 0>
    <div class="masters-section">
        <div class="masters-header">
            <i class="fas fa-users"></i> Наши мастера
        </div>
        <div class="masters-list">
            <#list masters as master>
            <div class="master-item">
                <img
                        src="${master.avatarURL!'https://via.placeholder.com/80/f55f8c/ffffff?text=${master.user.firstname?substring(0,1)}'}"
                        class="master-avatar"
                        alt="${master.user.firstname}"
                >
                <div class="master-info">
                    <div class="master-name">
                        ${master.user.firstname} ${master.user.lastname}
                    </div>
                    <#if master.stag??>
                    <div class="master-experience">
                        <i class="fas fa-briefcase"></i>
                        <span>Стаж: ${master.stag} лет</span>
                    </div>
                </#if>
            </div>
        </div>
    </#list>
</div>
</div>
</#if>
</div>
</div>
</div>

<script src="https://api-maps.yandex.ru/2.1/?apikey=6be11952-067c-4b9e-bf9a-2c0a00a27a4c&lang=ru_RU"></script>
<script>
    ymaps.ready(function() {
    ymaps.geocode('${salon.address!''}').then(function(res) {
        var coordinates = res.geoObjects.get(0).geometry.getCoordinates();

        var myMap = new ymaps.Map('map', {
            center: coordinates,
            zoom: 16
        });

        var placemark = new ymaps.Placemark(
            coordinates,
            {
                balloonContent: '<strong>${salon.salon_name}</strong><br>${salon.address!''}'
            },
            {
                preset: 'islands#redIcon'
            }
        );

        myMap.geoObjects.add(placemark);
        myMap.balloon.open(coordinates);
    });
});
</script>
</body>
</html>
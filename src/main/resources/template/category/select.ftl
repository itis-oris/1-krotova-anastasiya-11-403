<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Выбор категории услуг</title>
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

        .categories-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
        }

        .category-card {
            background: white;
            border-radius: 12px;
            padding: 30px 25px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            display: flex;
            flex-direction: column;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
        }

        .category-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
        }

        .category-icon {
            font-size: 56px;
            color: #f55f8c;
            margin-bottom: 20px;
            display: block;
        }

        .category-card:hover .category-icon {
            color: #e91e63;
            transform: scale(1.1);
        }

        .category-name {
            font-size: 20px;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
            flex-grow: 1;
        }

        .category-button {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            padding: 12px 24px;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.2s;
            align-self: center;
        }

        .category-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .category-button:active {
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
            <i class="fas fa-spa"></i> BeautySalon Kazan
        </a>
        <a class="navbar-back" href="${basePath}/salon/list">
            <i class="fas fa-arrow-left"></i> Назад
        </a>
    </div>
</nav>


<div class="breadcrumb-container">
    <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="${basePath}/salon/list">Салоны</a></li>
        <li class="breadcrumb-item active">Категории услуг</li>
    </ul>
</div>


<div class="container">
    <div class="page-header">
        <h1><i class="fas fa-list-alt"></i> Выберите категорию услуг</h1>
        <p>Выбери интересующую тебя категорию, чтобы приступить к бронированию</p>
    </div>

    <#if categories?? && categories?size gt 0>
    <div class="categories-grid">
        <#list categories as category>
        <a href="${basePath}/master?salonId=${salonId!''}&categoryId=${category.id}" class="category-card">
            <i class="fas fa-cut category-icon"></i>
            <h3 class="category-name">${category.category_Name}</h3>
            <button type="button" class="category-button">
                Выбрать <i class="fas fa-arrow-right"></i>
            </button>
        </a>
    </#list>
</div>
<#else>
<div class="empty-state">
    <div class="empty-state-icon">
        <i class="fas fa-inbox"></i>
    </div>
    <h3>Категории не найдены</h3>
    <p>В данный момент нет доступных категорий услуг для этого салона.</p>
</div>
</#if>
</div>
</body>
</html>
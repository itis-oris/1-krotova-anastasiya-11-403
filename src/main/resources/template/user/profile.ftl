<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мой профиль</title>
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

        .container {
            max-width: 600px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .profile-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            animation: slideIn 0.4s ease-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .profile-header {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            padding: 30px;
            text-align: center;
            color: white;
        }

        .profile-header h1 {
            font-size: 28px;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 12px;
        }

        .profile-body {
            padding: 40px;
        }

        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .alert-success {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }

        .alert i {
            font-size: 18px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 25px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }

        input {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            transition: all 0.2s;
            font-family: inherit;
        }

        input:focus {
            outline: none;
            border-color: #f55f8c;
            box-shadow: 0 0 0 3px rgba(245, 95, 140, 0.1);
        }

        input:disabled {
            background: #f5f5f5;
            color: #999;
            cursor: not-allowed;
        }

        .form-text {
            display: block;
            font-size: 12px;
            color: #999;
            margin-top: 6px;
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
            transition: all 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.4);
        }

        .btn:active {
            transform: translateY(0);
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
            <li><a class="navbar-link" href="${basePath}/salon/list"><i class="fas fa-home"></i> Салоны</a></li>
            <li><a class="navbar-link" href="${basePath}/logout"><i class="fas fa-sign-out-alt"></i> Выйти</a></li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="profile-card">
        <div class="profile-header">
            <h1><i class="fas fa-user-circle"></i> Мой профиль</h1>
        </div>

        <div class="profile-body">

            <#if ok??>
            <div class="alert alert-success">
                <i class="fas fa-check-circle"></i>
                <span>Профиль успешно обновлен!</span>
            </div>
        </#if>

        <form action="${basePath}/profile" method="POST">
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">Имя</label>
                    <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value="${user.firstname!''}"
                            required
                            placeholder="Введи своё имя"
                    >
                </div>
                <div class="form-group">
                    <label for="lastName">Фамилия</label>
                    <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            value="${user.lastname!''}"
                            required
                            placeholder="Введи фамилию"
                    >
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input
                        type="email"
                        id="email"
                        value="${user.email!''}"
                        disabled
                >
                <span class="form-text">Email нельзя изменить</span>
            </div>

            <div class="form-group">
                <label for="phone">Телефон</label>
                <input
                        type="tel"
                        id="phone"
                        name="phone"
                        value="${user.phone!''}"
                        required
                        placeholder="+7 (999) 123-45-67"
                >
            </div>

            <button type="submit" class="btn">
                <i class="fas fa-save"></i> Сохранить изменения
            </button>
        </form>
    </div>
</div>
</div>
</body>
</html>
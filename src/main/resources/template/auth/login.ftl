<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход — Beauty Salon</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        .auth-container {
            width: 100%;
            max-width: 400px;
        }
        .auth-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
            padding: 40px;
            animation: fadeIn 0.4s ease-in-out;
        }

        h1 {
            font-size: 28px;
            color: #f55f8c;
            margin-bottom: 8px;
            text-align: center;
            font-weight: 600;
        }
        .subtitle {
            text-align: center;
            color: #999;
            font-size: 14px;
            margin-bottom: 30px;
        }
        .error-message {
            background-color: #fee;
            border-left: 4px solid #f44;
            color: #c33;
            padding: 12px 16px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-size: 14px;
            font-weight: 500;
            color: #333;
            margin-bottom: 6px;
        }

        input {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input:focus {
            outline: none;
            border-color: #f55f8c;
            box-shadow: 0 0 0 3px rgba(245, 95, 140, 0.1);
        }
        .btn-primary {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            margin-top: 10px;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(245, 95, 140, 0.3);
        }
        .btn-primary:active {
            transform: translateY(0);
        }
        .toggle-link {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #666;
        }
        .toggle-link a {
            color: #f55f8c;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.2s;
        }
        .toggle-link a:hover {
            color: #e91e63;
        }

    </style>
</head>
<body>
<div class="auth-container">
    <div class="auth-card">
        <h1>Beauty Salon</h1>
        <p class="subtitle">Вход в личный кабинет</p>

        <#if error??>
        <div class="error-message">${error}</div>
    </#if>

    <form method="POST" action="${basePath}/auth/login">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required placeholder="your@email.com">
        </div>

        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required placeholder="••••••••">
        </div>

        <button type="submit" class="btn-primary">Войти</button>
    </form>

    <p class="toggle-link">
        Нет аккаунта? <a href="${basePath}/auth/register">Создать аккаунт</a>
    </p>
</div>
</div>
</body>
</html>
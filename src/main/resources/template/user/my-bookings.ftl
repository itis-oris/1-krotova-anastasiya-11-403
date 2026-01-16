<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мои записи</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            min-height: 100vh;
            padding: 20px;
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
        }

        .back-link {
            color: white;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            transition: opacity 0.2s;
        }

        .back-link:hover {
            opacity: 0.8;
        }

        .header {
            background: white;
            border-radius: 12px;
            padding: 30px;
            margin-bottom: 25px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }

        .header h1 {
            font-size: 28px;
            margin-bottom: 10px;
            color: #333;
        }

        .header p {
            color: #666;
            font-size: 15px;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 30px;
            margin-bottom: 25px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }

        .bookings-list {
            display: grid;
            gap: 20px;
        }

        .booking-item {
            border: 1px solid #eee;
            border-radius: 8px;
            padding: 20px;
            transition: all 0.2s;
            background: #f9f9f9;
        }

        .booking-item:hover {
            background: white;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            border-color: #f55f8c;
        }

        .booking-header {
            display: flex;
            justify-content: space-between;
            align-items: start;
            margin-bottom: 15px;
        }

        .booking-title {
            font-size: 18px;
            font-weight: 600;
            color: #333;
        }

        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }

        .status-pending {
            background: #fff3cd;
            color: #856404;
        }

        .status-confirmed {
            background: #d4edda;
            color: #155724;
        }

        .status-cancelled {
            background: #f8d7da;
            color: #721c24;
        }

        .booking-details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin: 15px 0;
        }

        .detail-item {
            display: flex;
            flex-direction: column;
        }

        .detail-label {
            font-weight: 600;
            color: #f55f8c;
            font-size: 12px;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .detail-value {
            font-size: 15px;
            color: #333;
            word-wrap: break-word;
        }

        .booking-footer {
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .booking-time {
            font-size: 13px;
            color: #999;
        }

        .price {
            font-size: 18px;
            font-weight: 700;
            color: #f55f8c;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }

        .empty-state-icon {
            font-size: 64px;
            margin-bottom: 20px;
            opacity: 0.5;
        }

        .empty-state h2 {
            font-size: 20px;
            margin-bottom: 10px;
            color: #555;
        }

        .empty-state p {
            font-size: 14px;
        }

        .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            transition: all 0.2s;
        }

        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(245, 95, 140, 0.4);
        }

        @media (max-width: 600px) {
            .booking-details {
                grid-template-columns: 1fr;
            }

            .booking-header {
                flex-direction: column;
                gap: 10px;
            }

            .booking-footer {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <a class="back-link" href="${basePath}/salon/list">← Вернуться на главную</a>

    <div class="header">
        <h1>Мои записи</h1>
        <p>Здесь вы можете просмотреть все ваши записи к мастерам</p>
    </div>

    <div class="card">
        <#if bookings?? && bookings?size gt 0>
        <div class="bookings-list">
            <#list bookings as booking>
            <div class="booking-item">
                <div class="booking-header">
                    <div class="booking-title">
                        <#if booking.procedure??>
                        ${booking.procedure.procedure_name!''}
                        <#else>
                        Услуга не указана
                    </#if>
                </div>
                <#if booking.status == 'CONFIRMED'>
                <span class="status-badge status-confirmed">✓ ПОДТВЕРЖДЕНО</span>
                <#elseif booking.status == 'PENDING'>
                <span class="status-badge status-pending">⏱ В ОЖИДАНИИ</span>
                <#else>
                <span class="status-badge status-cancelled">✗ ОТМЕНЕНО</span>
            </#if>
        </div>

        <div class="booking-details">
            <div class="detail-item">
                <span class="detail-label">Мастер</span>
                <span class="detail-value">
                                    <#if booking.master?? && booking.master.user??>
                                        ${booking.master.user.firstname!''} ${booking.master.user.lastname!''}
                                    <#else>
                                        Мастер не указан
                                    </#if>
                </span>
            </div>

            <div class="detail-item">
                <span class="detail-label">Дата и время</span>
                <span class="detail-value">
                                    <#if booking.data??>
                                        ${booking.data}
                                    <#else>
                                        Дата не указана
                                    </#if>
                </span>
            </div>

            <div class="detail-item">
                <span class="detail-label">Салон</span>
                <span class="detail-value">
                                    <#if booking.master?? && booking.master.salon??>
                                        ${booking.master.salon.salon_name!''}
                                    <#else>
                                        Информация о салоне временно недоступна
                                    </#if>
                </span>
            </div>

            <div class="detail-item">
                <span class="detail-label">Стоимость</span>
                <span class="detail-value price">
                                    <#if booking.procedure?? && booking.procedure.price??>
                                        ${booking.procedure.price} ₽
                                    <#else>
                                        Не указана
                                    </#if>
                </span>
            </div>
        </div>
    </div>
</#list>
</div>
<#else>
<div class="empty-state">
    <div class="empty-state-icon"></div>
    <h2>Записей не найдено</h2>
    <p>У вас еще нет ни одной записи.</p>
    <a href="${basePath}/salon/list" class="btn-back">Вернуться на главную</a>
</div>
</#if>
</div>
</div>

</body>
</html>
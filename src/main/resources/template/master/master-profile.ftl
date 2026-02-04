<#ftl output_format="HTML">
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль мастера</title>
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
            max-width: 1200px;
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

        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 12px;
            font-weight: 600;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border-left: 4px solid #28a745;
        }

        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border-left: 4px solid #dc3545;
        }

        .card {
            background: white;
            border-radius: 12px;
            padding: 30px;
            margin-bottom: 25px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
        }

        .card h2 {
            font-size: 22px;
            margin-bottom: 20px;
            color: #333;
            border-bottom: 2px solid #f55f8c;
            padding-bottom: 10px;
        }

        .card h3 {
            font-size: 18px;
            margin-top: 25px;
            margin-bottom: 15px;
            color: #555;
        }

        .header-section {
            display: flex;
            gap: 30px;
            align-items: flex-start;
            margin-bottom: 30px;
        }

        .avatar {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            object-fit: cover;
            border: 5px solid #f55f8c;
            flex-shrink: 0;
        }

        .header-info {
            flex: 1;
        }

        .header-info p {
            margin: 8px 0;
            font-size: 15px;
            color: #666;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
            font-size: 14px;
        }

        input[type="text"],
        input[type="url"],
        input[type="date"],
        input[type="time"],
        textarea,
        select {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-family: inherit;
            font-size: 14px;
            transition: all 0.2s;
        }

        input:focus,
        textarea:focus,
        select:focus {
            outline: none;
            border-color: #f55f8c;
            box-shadow: 0 0 0 3px rgba(245, 95, 140, 0.1);
        }

        textarea {
            resize: vertical;
            min-height: 80px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .form-row-3 {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 25px;
        }

        .btn {
            padding: 12px 25px;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #faacc4 0%, #f55f8c 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(245, 95, 140, 0.4);
        }

        .btn-secondary {
            background: #f0f0f0;
            color: #333;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .btn-danger {
            background: #dc3545;
            color: white;
            padding: 8px 14px;
            font-size: 12px;
        }

        .btn-danger:hover {
            background: #c82333;
        }

        .btn-edit {
            background: #ffc107;
            color: #333;
            padding: 8px 14px;
            font-size: 12px;
        }

        .btn-edit:hover {
            background: #e0a800;
        }

        .table-container {
            overflow-x: auto;
            margin-top: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        thead {
            background: #f8f9fa;
            border-bottom: 2px solid #ddd;
        }

        th {
            padding: 12px;
            text-align: left;
            font-weight: 600;
            color: #333;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #eee;
        }

        tbody tr:hover {
            background: #f5f5f5;
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

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 1000;
            align-items: center;
            justify-content: center;
        }

        .modal.active {
            display: flex;
        }

        .modal-content {
            background: white;
            border-radius: 12px;
            padding: 30px;
            max-width: 500px;
            width: 90%;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }

        .modal-content h3 {
            margin-bottom: 20px;
            font-size: 20px;
        }

        .modal-close {
            float: right;
            background: none;
            border: none;
            font-size: 24px;
            cursor: pointer;
            color: #999;
        }

        .search-container {
            position: relative;
        }

        .search-input {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 14px;
        }

        .search-results {
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            background: white;
            border: 1px solid #ddd;
            border-radius: 6px;
            max-height: 200px;
            overflow-y: auto;
            z-index: 100;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            display: none;
        }

        .search-results.active {
            display: block;
        }

        .client-option {
            padding: 12px 14px;
            cursor: pointer;
            border-bottom: 1px solid #f0f0f0;
            transition: background-color 0.2s;
        }

        .client-option:hover {
            background-color: #f8f9fa;
        }

        .client-option:last-child {
            border-bottom: none;
        }

        .selected-client {
            background: #f8f9fa;
            padding: 12px 14px;
            border-radius: 6px;
            margin-top: 10px;
            border: 1px solid #e9ecef;
            display: none;
        }

        .selected-client.active {
            display: block;
        }

        .remove-client {
            background: none;
            border: none;
            color: #dc3545;
            cursor: pointer;
            float: right;
            font-size: 16px;
        }

    </style>
</head>
<body>

<div class="container">
    <a class="back-link" href="${basePath}/salon/list">← Вернуться</a>


    <#if success??>
    <div class="alert alert-success">
         Операция успешно выполнена!
    </div>
    <#elseif error??>
    <div class="alert alert-error">
         Ошибка: ${error}
    </div>
</#if>


<div class="card">
    <h2>Редактирование профиля</h2>

    <div class="header-section">
        <img src="${master.avatarURL!'https://via.placeholder.com/140/f55f8c/ffffff?text=M'}"
             alt="Аватар" class="avatar">
        <div class="header-info">
            <p><strong>Имя:</strong> ${master.user.firstname} ${master.user.lastname}</p>
            <p><strong>Email:</strong> ${master.user.email}</p>
            <p><strong>Телефон:</strong> ${master.user.phone!'-'}</p>
        </div>
    </div>

    <form method="POST" action="${basePath}/mas-profile">
        <input type="hidden" name="action" value="updateProfile">

        <div class="form-group">
            <label for="description">Описание</label>
            <textarea id="description" name="description"
                      placeholder="Расскажите о себе и вашем опыте...">${master.description!''}</textarea>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="stag">Стаж работы</label>
                <input type="text" id="stag" name="stag" placeholder="Например: 5 лет"
                       value="${master.stag!''}">
            </div>

            <div class="form-group">
                <label for="avatarURL">URL аватара</label>
                <input type="url" id="avatarURL" name="avatarURL"
                       placeholder="https://example.com/avatar.jpg" value="${master.avatarURL!''}">
            </div>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">✓ Сохранить профиль</button>
        </div>
    </form>
</div>

<div class="card">
    <h2>Создать новую запись для клиента</h2>

    <form method="POST" action="${basePath}/mas-profile" id="createBookingForm">
        <input type="hidden" name="action" value="createBooking">
        <input type="hidden" id="clientId" name="clientId" value="">

        <div class="form-group">
            <label for="clientSearch">Поиск клиента</label>
            <div class="search-container">
                <input type="text" id="clientSearch" class="search-input"
                       placeholder="Введите имя, фамилию или email клиента..." autocomplete="off">
                <div id="searchResults" class="search-results"></div>
            </div>
            <div id="selectedClient" class="selected-client">
                <span id="selectedClientName"></span>
                <button type="button" class="remove-client" onclick="clearClientSelection()">×</button>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="procedureId">Выберите процедуру</label>
                <select id="procedureId" name="procedureId" required>
                    <option value="">-- Выберите процедуру --</option>
                    <#if procedures??>
                    <#list procedures as proc>
                    <option value="${proc.id}">${proc.procedure_name} (${proc.price} ₽)</option>
                </#list>
            </#if>
            </select>
        </div>
</div>

<div class="form-row">
    <div class="form-group">
        <label for="date">Дата</label>
        <input type="date" id="date" name="date" required>
    </div>

    <div class="form-group">
        <label for="time">Время</label>
        <input type="time" id="time" name="time" required>
    </div>
</div>

<div class="button-group">
    <button type="submit" class="btn btn-primary" id="createBookingBtn" disabled>+ Создать запись</button>
</div>
</form>
</div>

<div class="card">
    <h2>Записи клиентов</h2>

    <#if bookings?? && bookings?size gt 0>
    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>Клиент</th>
                <th>Процедура</th>
                <th>Дата и время</th>
                <th>Статус</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <#list bookings as booking>
            <tr>
                <td>${(booking.user.firstname)!''} ${(booking.user.lastname)!''}</td>
                <td>${(booking.procedure.procedure_name)!'Неизвестная процедура'}</td>
                <td>${booking.data}</td>
                <td>
                    <#if booking.status == 'CONFIRMED'>
                    <span class="status-badge status-confirmed">ПОДТВЕРЖДЕНО</span>
                    <#elseif booking.status == 'PENDING'>
                    <span class="status-badge status-pending">В ОЖИДАНИИ</span>
                    <#else>
                    <span class="status-badge status-cancelled">ОТМЕНЕНО</span>
                </#if>
                </td>
                <td>
                    <button type="button" class="btn btn-edit"
                            onclick="openEditModal(${booking.id}, '${booking.data?substring(0,10)}', '${booking.data?substring(11,16)}', '${booking.status}')">
                        ✎ Изменить
                    </button>
                    <form method="POST" action="${basePath}/mas-profile" style="display: inline;">
                        <input type="hidden" name="action" value="deleteBooking">
                        <input type="hidden" name="bookingId" value="${booking.id}">
                        <button type="submit" class="btn btn-danger"
                                onclick="return confirm('Вы уверены?')">
                            🗑 Удалить
                        </button>
                    </form>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <#else>
    <p style="text-align: center; color: #999; padding: 30px;">Записей нет</p>
</#if>
</div>
</div>

<div id="editModal" class="modal">
    <div class="modal-content">
        <button class="modal-close" onclick="closeEditModal()">&times;</button>
        <h3>Редактировать запись</h3>

        <form method="POST" action="${basePath}/mas-profile">
            <input type="hidden" name="action" value="updateBooking">
            <input type="hidden" id="editBookingId" name="bookingId">

            <div class="form-group">
                <label for="editDate">Дата</label>
                <input type="date" id="editDate" name="date" required>
            </div>

            <div class="form-group">
                <label for="editTime">Время</label>
                <input type="time" id="editTime" name="time" required>
            </div>

            <div class="form-group">
                <label for="editStatus">Статус</label>
                <select id="editStatus" name="status" required>
                    <option value="PENDING">В ожидании</option>
                    <option value="CONFIRMED">Подтверждено</option>
                    <option value="CANCELLED">Отменено</option>
                </select>
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-primary">✓ Сохранить</button>
                <button type="button" class="btn btn-secondary" onclick="closeEditModal()">Отмена</button>
            </div>
        </form>
    </div>
</div>

<script>
    const clients = [
        <#if clients??>
            <#list clients as client>
            {
                id: ${client.id},
                name: '${client.firstname} ${client.lastname}',
                email: '${client.email}',
                phone: '${client.phone!''}'
            }<#if client_has_next>,</#if>
            </#list>
        </#if>
    ];

    function initSearch() {
        const clientSearch = document.getElementById('clientSearch');
        const searchResults = document.getElementById('searchResults');
        const selectedClient = document.getElementById('selectedClient');
        const selectedClientName = document.getElementById('selectedClientName');
        const clientIdInput = document.getElementById('clientId');
        const createBookingBtn = document.getElementById('createBookingBtn');

        clientSearch.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase().trim();
            if (searchTerm.length < 2) {
                searchResults.classList.remove('active');
                return;
            }

            const
            filteredClients = clients.filter(client =>
                client.name.toLowerCase().includes(searchTerm) ||
                client.email.toLowerCase().includes(searchTerm)
            );

            if (filteredClients.length === 0) {
                searchResults.innerHTML = '<div class="client-option">Клиенты не найдены</div>';
            } else {
                let html = '';
                filteredClients.forEach(client => {
                    let phoneText = '';
                    if (client.phone) {
                        phoneText = ' - ' + client.phone;
                    }
                    html += '<div class="client-option" onclick="selectClient(' + client.id + ', \'' + client.name + '\', \'' + client.email + '\')">' +
                            '<strong>' + client.name + '</strong><br>' +
                            '<small>' + client.email + phoneText + '</small>' +
                            '</div>';
                });
                searchResults.innerHTML = html;
            }
            searchResults.classList.add('active');
        });
    }

    function selectClient(id, name, email) {
        document.getElementById('clientId').value = id;
        document.getElementById('selectedClientName').textContent = name + ' (' + email + ')';
        document.getElementById('selectedClient').classList.add('active');
        document.getElementById('searchResults').classList.remove('active');
        document.getElementById('clientSearch').value = '';
        document.getElementById('createBookingBtn').disabled = false;
    }

    function clearClientSelection() {
        document.getElementById('clientId').value = '';
        document.getElementById('selectedClient').classList.remove('active');
        document.getElementById('createBookingBtn').disabled = true;
    }

    function openEditModal(bookingId, date, time, status) {
        document.getElementById('editBookingId').value = bookingId;
        document.getElementById('editDate').value = date;
        document.getElementById('editTime').value = time;
        document.getElementById('editStatus').value = status;
        document.getElementById('editModal').classList.add('active');
    }

    function closeEditModal() {
        document.getElementById('editModal').classList.remove('active');
    }

    document.addEventListener('DOMContentLoaded', initSearch);
</script>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Password</title>
    <link rel="stylesheet" href="${url.resourcesPath}/css/login.css">
</head>
<body>
    <div id="kc-update-password" class="login">
        <div id="kc-header" class="header">
            <img id="kc-logo" src="${url.resourcesPath}/img/keycloak-logo.png" alt="Keycloak">
        </div>
        <div id="kc-form" class="content-area">
            <h1>${msg("updatePasswordTitle")}</h1>
            <p>Your email: ${userEmail}</p>
            <form id="kc-update-password-form" action="${url.loginAction}" method="post">
                <div class="form-group">
                    <label for="password">${msg("newPassword")}</label>
                    <input type="password" id="password" name="password" required autofocus>
                </div>
                <div class="form-group">
                    <label for="password-confirm">${msg("confirmPassword")}</label>
                    <input type="password" id="password-confirm" name="password-confirm" required>
                </div>
                <div class="form-group">
                    <input type="submit" id="kc-update-password-button" value="${msg("doSubmit")}">
                </div>
            </form>
        </div>
        <div id="kc-footer" class="footer">
            <span>&copy; 2024 Keycloak</span>
        </div>
    </div>
</body>
</html>

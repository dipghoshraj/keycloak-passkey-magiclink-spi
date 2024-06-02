<!DOCTYPE html>
<html lang="${locale}">
<head>
    <meta charset="utf-8">
    <title>${msg("loginMagicLinkTitle")}</title>
    <#include "/login/head-includes.ftl"/>
</head>
<body>
    <#include "/login/header.ftl"/>
    <div class="content">
        <h1>${msg("loginMagicLinkTitle")}</h1>
        <p>${msg("loginMagicLinkInfo")}</p>
    </div>
    <#include "/login/footer.ftl"/>
</body>
</html>

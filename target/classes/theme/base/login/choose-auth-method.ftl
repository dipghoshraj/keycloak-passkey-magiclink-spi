<form id="kc-select-auth-method-form" action="${url.loginAction}" method="post">
    <input type="hidden" id="completion_method" name="auth_method">
    <button type="button" class="completion-method-btn" onclick="submitForm('webauthn')">Web Authn register</button>
    <button type="button" class="completion-method-btn" onclick="submitForm('magic-link')"> Magic link flow</button>
</form>

<script>
        function submitForm(method) {
            document.getElementById('completion_method').value = method;
            document.getElementById('kc-select-auth-method-form').submit();
        }
</script>
